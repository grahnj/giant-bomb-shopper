(ns giant-bomb-shopper.db)

(def default-db
  {:rentals {}
   :aisle {:items []}
   :search {:page       1
            :searching? false}})
