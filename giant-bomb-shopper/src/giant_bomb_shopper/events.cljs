(ns giant-bomb-shopper.events
  (:require
   [re-frame.core :as re-frame]
   [giant-bomb-shopper.db :as db]
   ))


;; handlers
(defn checkout-item
  [ce [_ item]]
  (let [now (:db ce)]
    {:db (assoc-in now [:checkout] {:current item})}))


(defn clear-checkout
  [ce _]
  (let [now (:db ce)]
    {:db (dissoc now :checkout)}))


(defn rent-item
  [ce _]
  (let [now (:db ce)
        id  (-> now :checkout :current :id)
        now-clear (:db (clear-checkout {:db now} nil))
        rentals (conj (-> now-clear :rentals) id)]
    {:db (assoc now-clear :rentals rentals)}))


(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))


;; events
(re-frame/reg-event-fx
 :checkout-item
 checkout-item)


(re-frame/reg-event-fx
 :clear-checkout
 clear-checkout)


(re-frame/reg-event-fx
 :rent-item
 rent-item)
