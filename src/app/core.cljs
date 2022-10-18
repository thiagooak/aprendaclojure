(ns app.core
  (:require [reagent.dom :as rd]))

(defn some-component []
  [:div {:class "max-w-4xl mx-auto min-h-screen bg-gray-50 flex flex-col justify-center py-12 sm:px-6 lg:px-8"}
   [:h3 "Hey! I'm a component!"]
   [:p
    "I have " [:span {:class "font-bold"} "bold"]
    [:span {:class "text-red-600"} " and red"]
    " text."]])

(defn ^:dev/after-load start
  []
  (rd/render [some-component]
             (.getElementById js/document "app")))

(defn init []
  (println "Hello World"); This prints to the browser console
  (start))
