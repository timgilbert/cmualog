(ns cmualog.main
  (:require [taoensso.timbre :as log]
            [cli-matic.core :as cli-matic]
            [cmualog.cmudict :as ingest])
  (:gen-class))

(def logging-config
  "cf https://ptaoussanis.github.io/timbre/taoensso.timbre.html#var-*config*"
  {})

(defn run-command [command args]
  (log/merge-config! logging-config)
  (log/set-level! (if (:debug args) :debug :info))
  (log/debug "Debug logging enabled")
  (try
    ; process exit code
    (or (command args) 0)
    (catch Exception e
      (log/error e "Exception during processing!")
      1)))

(def cli-options
  {:command     "cmualog"
   :description "cmudict to datalog ingestion tool"
   :version     "0.1.0"
   :opts        [{:option "debug"
                  :as     "Show debugging messages"
                  :type   :with-flag
                  :short  "d"}]
   :subcommands
   [{:command     "ingest-cmu"
     :description "Read in metadata from BQ and save it to a file"
     :runs        (partial run-command ingest/ingest)
     :opts        [{:option  "cmu-root"
                    :as      "Root directory of the cmudict project"
                    :type    :string
                    :default "../cmudict"}
                   {:option  "db-url"
                    :as      "URL for the datalevin server (directory or dtlv:// URL)"
                    :type    :string
                    :default "./data"}]}]})


(defn -main [& args]
  (cli-matic/run-cmd args cli-options))
