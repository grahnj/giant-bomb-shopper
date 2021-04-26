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


(re-frame/reg-sub
 ::search-text
 (fn [db]
   (-> db :search :text)))


(re-frame/reg-sub
 ::search-page
 (fn [db]
   (-> db :search :page)))


(re-frame/reg-sub
 ::rentals
 (fn [db]
   (-> db :rentals)))
