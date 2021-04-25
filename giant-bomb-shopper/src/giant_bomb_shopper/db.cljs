(ns giant-bomb-shopper.db)

(def default-db
  {:rentals []
   :aisle {:current 1
           :items [{:image       "https://giantbomb1.cbsistatic.com/uploads/scale_small/8/82063/3124242-lahd.jpg"
                    :name        "Zelda"
                    :description "Hero rescues titular princess"
                    :id          1234}
                   {:image       "https://giantbomb1.cbsistatic.com/uploads/scale_small/9/93770/2362064-nes_metroid.jpg"
                    :name        "Metroid"
                    :description "Woman saves the universe from Space Pirates"
                    :id           5678}]}})
