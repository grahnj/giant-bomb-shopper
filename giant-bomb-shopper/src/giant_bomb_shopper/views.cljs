(ns giant-bomb-shopper.views
  (:require
   [re-frame.core :as re-frame]
   [giant-bomb-shopper.subs :as subs]
   ))

;; dispatches
(defn checkout-rental
  [item]
  (re-frame/dispatch [:checkout-item item]))


(defn clear-rental
  []
  (re-frame/dispatch [:clear-checkout]))


(defn rent-item
  []
  (re-frame/dispatch [:rent-item]))

;; components
(defn title-card
  [item & disp]
  [:div.card
   [:div.card-image
    [:figure.image.is-128x128
     [:img {:src (:image item)}]]]
   [:div.card-content
    [:div.media
     [:div.media-content
      [:p.title.is-4 (:name item)]]]
    [:div.content (:description item)]]
   [:footer.card-footer
    (map (fn [[action dispatch]]
           [:a.card-footer-item {:href "#"
                :on-click (fn [e] (dispatch item))} 
            action])
         disp)
    ]])


(defn title-card-list
  "a rendered list of items for the current 'aisle' (page)"
  [items]
  [:div.box
   (map (fn [item]
          [:div.block {:key (:id item)}
           (title-card item ["Rent" checkout-rental])])
        items)])


(defn checkout-item
  "A modal for checking out (renting) an item"
  [item]
  [:div.modal {:class [(when (not (nil? item)) "is-active")]}
   [:div.modal-background]
   [:div.modal-content
    (title-card item ["Finish Rental" rent-item] ["Cancel" clear-rental])]])


(defn main-panel []
  (let [items       (re-frame/subscribe [::subs/aisle-items])
        to-checkout (re-frame/subscribe [::subs/checkout-item])]
    [:div
     [:h1.title
      "Giant Bomb: Shop"]
     (title-card-list @items)
     (checkout-item @to-checkout)
     ]))
