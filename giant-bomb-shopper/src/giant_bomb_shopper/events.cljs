(ns giant-bomb-shopper.events
  (:require
   [re-frame.core :as re-frame]
   [giant-bomb-shopper.db :as db]
   ))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))
