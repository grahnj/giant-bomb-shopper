(ns giant-bomb-shopper.subs
  (:require
   [re-frame.core :as re-frame]))


(re-frame/reg-sub
 ::name
 (fn [db]
   (-> db :name)))


(re-frame/reg-sub
 ::aisle-items
 (fn [db]
   (-> db :aisle :items)))


(re-frame/reg-sub
 ::checkout-item
 (fn [db]
   (-> db :checkout :current)))
