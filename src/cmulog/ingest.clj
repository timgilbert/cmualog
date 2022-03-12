(ns cmulog.ingest
  (:require [taoensso.timbre :as log]
            [puget.printer :as puget]
            [datalevin.core :as d]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(def cmu-schema
  "Main schema for cmudict stuff"
  {:phone/name      #:db{:valueType :db.type/keyword
                         :unique    :db.unique/identity
                         :doc       "unique name for a phoneme (as a keyword)"}
   :phone/class     #:db{:valueType :db.type/keyword
                         :doc       "Class of phoneme (fricative, etc)"}
   :symbol/name     #:db{:valueType :db.type/keyword
                         :unique    :db.unique/identity}
   :symbol/phone    #:db{:valueType :db.type/ref
                         :doc       "Phone this symbol is based on"}
   :symbol/stress   #:db{:valueType :db.type/keyword
                         :doc       "One of #{:primary :secondary :unstressed :none}"}
   :word/name       #:db{:valueType :db.type/string
                         :unique    :db.unique/identity
                         :doc       "The word in question, as a string"}
   :list/pos        #:db{:valueType :db.type/long
                         :doc       "For an item in a sorted list, its index"}
   :list/item       #:db{:valueType :db.type/ref
                         :doc       "For an item in a sorted list, the item"}
   :variant/word    #:db{:valueType :db.type/ref
                         :doc       "The word this variant refers to"}
   :variant/name    #:db{:valueType :db.type/string
                         :unique    :db.unique/identity
                         :doc       "The name of this variant, eg 'contrasts(2)'"}
   :variant/symbols #:db{:valueType   :db.type/ref
                         :cardinality :db.cardinality/many
                         :doc         (str "A set of :list entities, where each :list/item "
                                           "element is a :symbol, ordered by :list/pos")}})

(defn txt []
  (let [tx [{:db/id -1, :word/name "'bout"}
            #:variant{:name "'bout",
                      :word -1,
                      :pos  0,
                      :symbols
                      '(#:list{:pos 0, :item [:symbol/name :B]}
                         #:list{:pos 1, :item [:symbol/name :AW1]}
                         #:list{:pos 2, :item [:symbol/name :T]})}]]
    (d/transact! (d/get-conn "./data" cmu-schema) tx)))

(defn symbol-stress [symbol-str]
  (case symbol-str
    "1" :stress/primary
    "2" :stress/secondary
    "0" :stress/unstressed
    :stress/none))

(defn find-variant [word-str]
  (let [[match word pos] (re-find #"^([^(]+)\(?(\d+)?\)?" word-str)]
    {:variant/name match
     :variant/word [:word/name word]
     :variant/pos  ()}))

(defn build-symbol-list [symbols]
  (map-indexed
    (fn [i s]
      #:list{:pos  i
             :item [:symbol/name s]})
    symbols))

(defn parse-dict-line [_conn line]
  ;; Skip comments
  (when-not (re-find #"^(;;;|##)" line)
    (let [[variant & symbol-strs] (string/split line #"\s+")
          symbols (map keyword symbol-strs)
          [_match word pos] (re-find #"^([^(]+)\(?(\d+)?\)?" variant)]
      [{:db/id -1 :word/name word}
       #:variant{:name    variant
                 :word    -1
                 :pos     (if pos (Integer/parseInt pos) 0)
                 :symbols (build-symbol-list symbols)}])))

(defn parse-symbol-line [_conn line]
  (let [[match phone-name stress-str] (re-find #"^(\D+)(\d*)$" line)]
    {:symbol/name   (keyword match)
     :symbol/phone  [:phone/name (keyword phone-name)]
     :symbol/stress (symbol-stress stress-str)}))

(defn parse-phone-line [_conn line]
  (let [[name phonetic-class] (map keyword (string/split line #"\s+"))]
    {:phone/name  name
     :phone/class phonetic-class}))

(defn parse-file [conn cmu-root filename line-fn]
  (log/infof "Parsing file %s/%s" cmu-root filename)
  (try
    (with-open [reader (io/reader (io/file cmu-root filename))]
      (let [tx-data (mapv (partial line-fn conn) (line-seq reader))]
        (some->> tx-data doall log/spy (d/transact! conn))))))

(defn ingest
  "Run the main ingestion"
  [{:keys [cmu-root db-url] :as args}]
  (let [conn (d/get-conn db-url cmu-schema)]
    (parse-file conn cmu-root "cmudict.phones" parse-phone-line)
    (parse-file conn cmu-root "cmudict.symbols" parse-symbol-line)
    (parse-file conn cmu-root "cmudict.dict" parse-dict-line)
    (log/info "Finished!")))
