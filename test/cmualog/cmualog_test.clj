(ns cmualog.cmualog-test
  (:require [clojure.test :refer :all]
            [cmualog.ingest :refer :all]))

(def bout "'bout B AW1 T")

(deftest a-test
  (testing "FIXME, I fail."
    (let [result (parse-dict-line bout)]
      (is (some? result)))))
