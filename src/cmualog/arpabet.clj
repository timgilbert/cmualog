(ns cmualog.arpabet
  "Routines for going from ARPABET to IPA"
  (:require [puget.printer :as puget]))

(def arpabet-data
  "Table of ARPABET phones to IPA, from
  [Wikipedia](https://en.wikipedia.org/wiki/ARPABET)."
  [#:arpa{:examples [#:arpa.ex{:exemplar "al" :word "balm"}
                     #:arpa.ex{:exemplar "o" :word "bot"}]
          :ipa      "ä"
          :symbol   "AA"}
   #:arpa{:examples [#:arpa.ex{:exemplar "a" :word "bat"}]
          :ipa      "æ"
          :symbol   "AE"}
   #:arpa{:examples [#:arpa.ex{:exemplar "u" :word "butt"}]
          :ipa      "ɐ"
          :symbol   "AH"}
   #:arpa{:examples [#:arpa.ex{:exemplar "o" :word "story"}]
          :ipa      "ɔ"
          :symbol   "AO"}
   #:arpa{:examples [#:arpa.ex{:exemplar "ou" :word "bout"}]
          :ipa      "aʊ"
          :symbol   "AW"}
   #:arpa{:examples [#:arpa.ex{:exemplar "a" :word "comma"}]
          :ipa      "ə"
          :symbol   "AX"}
   #:arpa{:examples [#:arpa.ex{:exemplar "er" :word "letter"}]
          :ipa      "ɚ"
          :symbol   "AXR"}
   #:arpa{:examples [#:arpa.ex{:exemplar "i" :word "bite"}]
          :ipa      "aɪ"
          :symbol   "AY"}
   #:arpa{:examples [#:arpa.ex{:exemplar "e" :word "bet"}]
          :ipa      "ɛ"
          :symbol   "EH"}
   #:arpa{:examples [#:arpa.ex{:exemplar "ir" :word "bird"}]
          :ipa      "ɝ"
          :symbol   "ER"}
   #:arpa{:examples [#:arpa.ex{:exemplar "ai" :word "bait"}]
          :ipa      "eɪ"
          :symbol   "EY"}
   #:arpa{:examples [#:arpa.ex{:exemplar "i" :word "bit"}]
          :ipa      "ɪ"
          :symbol   "IH"}
   #:arpa{:examples [#:arpa.ex{:exemplar "i" :word "rabbit"} #:arpa.ex{:exemplar "e" :word "roses"}]
          :ipa      "ɨ"
          :symbol   "IX"}
   #:arpa{:examples [#:arpa.ex{:exemplar "ea" :word "beat"}]
          :ipa      "i"
          :symbol   "IY"}
   #:arpa{:examples [#:arpa.ex{:exemplar "oa" :word "boat"}]
          :ipa      "oʊ"
          :symbol   "OW"}
   #:arpa{:examples [#:arpa.ex{:exemplar "oy" :word "boy"}]
          :ipa      "ɔɪ"
          :symbol   "OY"}
   #:arpa{:examples [#:arpa.ex{:exemplar "oo" :word "book"}]
          :ipa      "ʊ"
          :symbol   "UH"}
   #:arpa{:examples [#:arpa.ex{:exemplar "oo" :word "boot"}]
          :ipa      "u"
          :symbol   "UW"}
   #:arpa{:examples [#:arpa.ex{:exemplar "u" :word "dude"}]
          :ipa      "ʉ"
          :symbol   "UX"}
   #:arpa{:examples [#:arpa.ex{:exemplar "b" :word "buy"}]
          :ipa      "b"
          :symbol   "B"}
   #:arpa{:examples [#:arpa.ex{:exemplar "Ch" :word "China"}]
          :ipa      "tʃ"
          :symbol   "CH"}
   #:arpa{:examples [#:arpa.ex{:exemplar "d" :word "die"}]
          :ipa      "d"
          :symbol   "D"}
   #:arpa{:examples [#:arpa.ex{:exemplar "th" :word "thy"}]
          :ipa      "ð"
          :symbol   "DH"}
   #:arpa{:examples [#:arpa.ex{:exemplar "tt" :word "butter"}]
          :ipa      "ɾ"
          :symbol   "DX"}
   #:arpa{:examples [#:arpa.ex{:exemplar "le" :word "bottle"}]
          :ipa      "l̩"
          :symbol   "EL"}
   #:arpa{:examples [#:arpa.ex{:exemplar "m" :word "rhythm"}]
          :ipa      "m̩"
          :symbol   "EM"}
   #:arpa{:examples [#:arpa.ex{:exemplar "on" :word "button"}]
          :ipa      "n̩"
          :symbol   "EN"}
   #:arpa{:examples [#:arpa.ex{:exemplar "f" :word "fight"}]
          :ipa      "f"
          :symbol   "F"}
   #:arpa{:examples [#:arpa.ex{:exemplar "g" :word "guy"}]
          :ipa      "ɡ"
          :symbol   "G"}
   #:arpa{:examples [#:arpa.ex{:exemplar "h" :word "high"}]
          :ipa      "h"
          :symbol   "HH"}
   #:arpa{:examples [#:arpa.ex{:exemplar "h" :word "high"}]
          :ipa      "h"
          :symbol   "H"}
   #:arpa{:examples [#:arpa.ex{:exemplar "j" :word "jive"}]
          :ipa      "dʒ"
          :symbol   "JH"}
   #:arpa{:examples [#:arpa.ex{:exemplar "k" :word "kite"}]
          :ipa      "k"
          :symbol   "K"}
   #:arpa{:examples [#:arpa.ex{:exemplar "l" :word "lie"}]
          :ipa      "l"
          :symbol   "L"}
   #:arpa{:examples [#:arpa.ex{:exemplar "m" :word "my"}]
          :ipa      "m"
          :symbol   "M"}
   #:arpa{:examples [#:arpa.ex{:exemplar "n" :word "nigh"}]
          :ipa      "n"
          :symbol   "N"}
   #:arpa{:examples [#:arpa.ex{:exemplar "ng" :word "sing"}]
          :ipa      "ŋ"
          :symbol   "NG"}
   #:arpa{:examples [#:arpa.ex{:exemplar "p" :word "pie"}]
          :ipa      "p"
          :symbol   "P"}
   #:arpa{:examples [#:arpa.ex{:exemplar "-" :word "uh-oh"}]
          :ipa      "ʔ"
          :symbol   "Q"}
   #:arpa{:examples [#:arpa.ex{:exemplar "r" :word "rye"}]
          :ipa      "ɹ"
          :symbol   "R"}
   #:arpa{:examples [#:arpa.ex{:exemplar "s" :word "sigh"}]
          :ipa      "s"
          :symbol   "S"}
   #:arpa{:examples [#:arpa.ex{:exemplar "sh" :word "shy"}]
          :ipa      "ʃ"
          :symbol   "SH"}
   #:arpa{:examples [#:arpa.ex{:exemplar "t" :word "tie"}]
          :ipa      "t"
          :symbol   "T"}
   #:arpa{:examples [#:arpa.ex{:exemplar "th" :word "thigh"}]
          :ipa      "θ"
          :symbol   "TH"}
   #:arpa{:examples [#:arpa.ex{:exemplar "v" :word "vie"}]
          :ipa      "v"
          :symbol   "V"}
   #:arpa{:examples [#:arpa.ex{:exemplar "w" :word "wise"}]
          :ipa      "w"
          :symbol   "W"}
   #:arpa{:examples [#:arpa.ex{:exemplar "wh" :word "why"}]
          :ipa      "ʍ"
          :symbol   "WH"}
   #:arpa{:examples [#:arpa.ex{:exemplar "y" :word "yacht"}]
          :ipa      "j"
          :symbol   "Y"}
   #:arpa{:examples [#:arpa.ex{:exemplar "z" :word "zoo"}]
          :ipa      "z"
          :symbol   "Z"}
   #:arpa{:examples [#:arpa.ex{:exemplar "s" :word "pleasure"}]
          :ipa      "ʒ"
          :symbol   "ZH"}])
  
(def arpabet-map (zipmap (map :arpa/symbol arpabet-data) arpabet-data))

(defn to-ipa []
  (puget/pprint arpabet-data))