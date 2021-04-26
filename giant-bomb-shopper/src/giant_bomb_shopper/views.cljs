(ns giant-bomb-shopper.views
  (:require
   [re-frame.core :as re-frame]
   [giant-bomb-shopper.subs :as subs]
   ))

;; dispatches
(defn checkout-rental
  [item]
  (re-frame/dispatch [:checkout-item item]))


(defn return-rental
  [item]
  (re-frame/dispatch [:return-item item]))


(defn clear-rental
  []
  (re-frame/dispatch [:clear-checkout]))


(defn rent-item
  []
  (re-frame/dispatch [:rent-item]))


(defn search
  []
  (re-frame/dispatch [:search]))


(defn update-search-text
  [text]
  (re-frame/dispatch [:update-search text]))


;; components
; ideally in the future this would be a form so we could leverage on-submit (i.e. search + enter)
(defn search-control
  [text]
  [:div.field.has-addons
   [:div.control
    [:input.input.is-rounded {:type        "text"
                              :placeholder "Search"
                              :value       text
                              :on-change   (fn [e] (update-search-text (-> e .-target .-value)))}]]
   [:div.control
    [:a.button.is-info {:on-click (fn [] (search))} "Search"]]])


(defn title-card
  [item & disp]
  [:div.card
   [:div.card-image
    [:figure.image.is-5by3
     [:img {:src (-> item :image :screen_url)}]]]
   [:div.card-content
    [:div.media
     [:div.media-content
      [:p.title.is-4 (:name item)]]]
    [:div.content (:deck item)]]
   [:footer.card-footer.hero.is-link
    (map (fn [[action dispatch]]
           [:a.card-footer-item {:href "#"
                :on-click (fn [e] (dispatch item))} 
            action])
         disp)
    ]])


(defn title-card-list
  "a rendered list of items for the current 'aisle' (page)"
  [items rentals]
  [:div.box
   (if (empty? items)
     [:section.section "Enter a valid search value (search by name) for the game you want to rent and see what titles are available!"]
     (map (fn [item]
            [:div.block {:key (:id item)}
             (->> (if (contains? rentals (:id item))
                    ["Return" return-rental]
                    ["Rent" checkout-rental])
                  (title-card item))])
          items))])


(defn checkout-item
  "A modal for checking out (renting) an item"
  [item]
  [:div.modal {:class [(when (not (nil? item)) "is-active")]}
   [:div.modal-background]
   [:div.modal-content
    (title-card item ["Finish Rental" rent-item] ["Cancel" clear-rental])]])


(defn main-panel []
  (let [items       (re-frame/subscribe [::subs/aisle-items])
        to-checkout (re-frame/subscribe [::subs/checkout-item])
        search-text (re-frame/subscribe [::subs/search-text])
        search-page (re-frame/subscribe [::subs/search-page])
        rentals     (re-frame/subscribe [::subs/rentals])]
    [:div
     [:p (pr-str @rentals)]
     [:div.columns
      [:div.column.is-one-quarter]
      [:div.column.is-one-half
       [:div.box.hero.is-primary
        [:h1.title
         "Find Games to Rent"]
        (search-control @search-text)]
       (title-card-list @items @rentals)
       [:section.hero.is-warning
        [:div.hero-body
         [:p.title "Warning"]
         [:p.subtitle "Searches are currently limited to 10 items (pagination is coming soon). Please be as specific about your searches as possible."]]]
       (checkout-item @to-checkout)]
      [:div.column.is-one-quarter]]
     ]))
