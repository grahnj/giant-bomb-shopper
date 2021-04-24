(ns giant-bomb-shopper.views
  (:require
   [re-frame.core :as re-frame]
   [giant-bomb-shopper.subs :as subs]
   ))

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])
        something (re-frame/subscribe [::subs/something])]
    [:div
     [:h1.title
      "Hello from " @name]
     [:h2.subtitle
      "This should say something: " @something]
     ]))
