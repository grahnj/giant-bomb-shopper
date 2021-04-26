(ns giant-bomb-shopper.events
  (:require
   [re-frame.core :as re-frame]
   [day8.re-frame.http-fx] 
   [ajax.core :as ajax] 
   [clojure.edn :as edn]
   [giant-bomb-shopper.db :as db]
   [giant-bomb-shopper.props :as props :refer [properties]]
   ))


;;helpers
(defn search-uri
  "a fully composed search uri"
  [text page]
  (let [uri    (:api-search    props/properties)
        params {:api_key       (:api-key properties)
                :format        "json"
                :field_list    "deck,image,id,name"
                :query         text
                :page          page
                :resources     "game"}]
    (reduce (fn [u [key val]]
              (str u (name key) "=" val "&"))
            uri
            params)))


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
  (let [now       (:db ce)
        id        (-> now :checkout :current :id)
        now-clear (:db (clear-checkout {:db now} nil))]
    {:db (assoc-in now-clear [:rentals id] true)}))


(defn return-item
  [ce [_ item]]
  (let [now (:db ce)
        rentals (:rentals now)]
    {:db (assoc now :rentals (dissoc rentals (:id item)))}))


(defn update-search
  [ce [_ text]]
  (let [now (:db ce)]
    {:db (assoc-in now [:search] {:text text})}))


(defn search
  [ce _]
  (let [now         (:db ce)
        search-text (-> now :search :text)
        search-page (-> now :search :page)
        uri         (search-uri search-text search-page)]
    {:db         (assoc-in now [:search] {:searching? true})
     :http-xhrio {:method          :get
                  :uri             uri
                  :timeout         5000
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success      [:search-success]
                  :on-failure      [:search-failure]}}))


(defn search-success
  [ce [_ payload]]
  {:db (->
        ce
        (update-search [nil nil])
        (:db)
        (assoc-in [:search] {:searching? false})
        (assoc-in [:search] {:failure? false})
        (assoc-in [:aisle] {:items (-> payload :results)}))})


(defn search-failure
  [ce [_ payload]]
  {:db (->
        ce
        (:db)
        (assoc-in [:search] {:searching? false})
        (assoc-in [:search] {:failure? true}))})

;; db
(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))


;; events
(re-frame/reg-event-fx
 :checkout-item
 checkout-item)


(re-frame/reg-event-fx
 :return-item
 return-item)


(re-frame/reg-event-fx
 :clear-checkout
 clear-checkout)


(re-frame/reg-event-fx
 :rent-item
 rent-item)


(re-frame/reg-event-fx
 :update-search
 update-search)


(re-frame/reg-event-fx
 :search
 search)


(re-frame/reg-event-fx
 :search-success
 search-success)


 (re-frame/reg-event-fx
  :search-failure
  search-failure)
