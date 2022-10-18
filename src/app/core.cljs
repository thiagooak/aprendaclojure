(ns app.core
  (:require [reagent.dom :as rd]))

(defn some-component []
  [:div
   [:h3 "I am another component!"]
   [:p.someclass
    "I have " [:strong "bold"]
    [:span {:style {:color "red"}} " and red"]
    " text."]])

(defn ^:dev/after-load start
  []
  (rd/render [some-component]
             (.getElementById js/document "app")))

(defn init []
  (println "Hello World"); This prints to the browser console
  (start))
