# TODOs

- IPA translations from [wikipedia](https://en.wikipedia.org/wiki/ARPABET#Symbols)
- Syllabification ([ref](https://en.wikipedia.org/wiki/Syllable#Syllabification))
  - See also [here](https://github.com/nltk/nltk/blob/develop/nltk/tokenize/legality_principle.py)
  - Gutenberg [hyphenization corpus](https://onlinebooks.library.upenn.edu/webbin/gutbook/lookup?num=3204)?
  - Prior Clojure art: [phonetics](https://github.com/eihli/phonetics)
- Possibly use wiktionary as alternate source? ([ref](https://github.com/abuccts/wikt2pron))
  - Could extract from files from [wiktextract](https://github.com/tatuylonen/wiktextract)
  - Need something like [this](https://linguistics.stackexchange.com/questions/30933/how-to-split-ipa-spelling-into-syllables) for syllabification
- Rename `:variant` -> `:pronunciation`
- rhyming, scansion ([ref](https://github.com/M-R-Epstein/poetics))
- Alternate dictionaries? [amepd](https://github.com/rhdunn/amepd), [buckeye_dict](https://github.com/jonsafari/buckeye_dict)