(ns cmulog.ingest
  (:require [taoensso.timbre :as log]
            [puget.printer :as puget]))

(defn ingest
  "I don't do a whole lot ... yet."
  [{:keys [cmu-root] :as args}]
  (puget/pprint args))
