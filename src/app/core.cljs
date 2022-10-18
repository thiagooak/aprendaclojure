(ns app.core
  (:require [reagent.core :as r]
            [reagent.dom :as rd]))

(defn celcius-to-fahrenheit [c]
  (+ 32 (* 9 (/ c 5))))

(defn calc-temp [c]
  (let [f (celcius-to-fahrenheit c)]
    ;; @TODO choose correct emoji
    (hash-map :celcius c :fahrenheit f :emoji "🥵")))

(def temp-data (r/atom (calc-temp 0)))

(defn slider [value min max]
  [:input {:type "range" :value value :min min :max max
           :style {:width "100%"}
           :on-change (fn [e]
                        (let [new-value (js/parseInt (.. e -target -value))]
                          (swap! temp-data
                                 (fn []
                                   (calc-temp new-value)))))}])

(defn temp-component []
  (let [{:keys [celcius fahrenheit emoji]} @temp-data]
    [:div
     [:h3 "Temperature Converter"]
     [:div
      "Celcius: " celcius "°C"
      [slider celcius -100 +100]]
     [:div
      "Fahrenheit: " fahrenheit "°F"
      ;; [slider fahrenheit -100 +100] @TODO add F sliders
      ]
     [:div
      "Emoji: " emoji]]))

(defn some-component []
  [:div {:class "max-w-4xl mx-auto min-h-screen bg-gray-50 flex flex-col justify-center py-12 sm:px-6 lg:px-8"}
   [:h3 "Hey! I'm a component!"]
   [:p
    "I have " [:span {:class "font-bold"} "bold"]
    [:span {:class "text-red-600"} " and red"]
    " text."]])

(defn ^:dev/after-load start
  []
  (rd/render [temp-component]
             (.getElementById js/document "app")))

(defn init []
  (println "Hello World"); This prints to the browser console
  (start))
