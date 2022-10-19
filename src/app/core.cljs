(ns app.core
  (:require [reagent.core :as r]
            [reagent.dom :as rd]))

(defn c-to-f [c]
  (Math/round (+ 32 (* 1.8 c))))

(defn f-to-c [f]
  (Math/round (/ (- f 32) 1.8)))

(defn calc-temp [unit value]
  (let [c (cond (= unit :fahrenheit) (f-to-c value)
                :else value)
        f (cond (= unit :celcius) (c-to-f value)
                :else value)
        emoji (cond (< c -30) '🥶
                    (< c 1) '⛄️
                    (< c 15) '🧣
                    (> c 40) '🔥
                    (> c 27) '🥵
                    :else '😀)]
    (hash-map :celcius c :fahrenheit f :emoji emoji)))

(def temp-data (r/atom (calc-temp :celcius 0)))

(defn slider [unit value min max]
  [:input {:type "range" :value value :min min :max max
           :style {:width "100%"}
           :on-change (fn [e]
                        (let [new-value (js/parseInt (.. e -target -value))]
                          (swap! temp-data
                                 (fn []
                                   (calc-temp unit new-value)))))}])

(defn temp-component []
  (let [{:keys [celcius fahrenheit emoji]} @temp-data]
    [:div
     [:h1 {:class "text-5xl font-bold mb-4"} [:span {:class "mr-2"} emoji] "Temperature Converter"]
     [:div
      celcius "° Celcius"
      [slider :celcius celcius -100 +100]]
     [:div
      fahrenheit "° Fahrenheit"
      [slider :fahrenheit fahrenheit -212 +212]]]))

(defn template []
  [:div {:class "max-w-4xl mx-auto min-h-screen flex flex-col justify-center p-12"}
   [temp-component]
   ])

(defn ^:dev/after-load start
  []
  (rd/render [template]
             (.getElementById js/document "app")))

(defn init []
  (println "Hello World"); This prints to the browser console
  (start))
