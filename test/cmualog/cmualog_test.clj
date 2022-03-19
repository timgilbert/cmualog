(ns cmualog.cmualog-test
  (:require [clojure.test :refer :all]
            [cmualog.cmudict :refer :all]))

(def bout "'bout B AW1 T")
(def chemistry "chemistry K EH1 M AH0 S T R IY0")
(def chemistry2 "chemistry(2) K EH1 M IH0 S T R IY0")
(def poetics "poetics P OW0 EH1 T IH0 K S")
(def poetry "poetry P OW1 AH0 T R IY0")
(def doggerel "doggerel D AA1 G ER0 AH0 L")

(deftest a-test
  (testing "FIXME, I fail."
    (let [result (parse-dict-line 1 bout)]
      (is (some? result)))))
