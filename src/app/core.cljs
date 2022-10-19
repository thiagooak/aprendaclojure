(ns app.core
  (:require [reagent.core :as r]
            [reagent.dom :as rd]))

(defn c-to-f [c]
  (+ 32 (* 1.8 c)))

(defn f-to-c [f]
  (/ (- f 32) 1.8))

(defn c-to-emoji [c]
  (cond (< c -30) '🥶
        (< c 1) '⛄️
        (< c 15) '🧣
        (> c 40) '🔥
        (> c 27) '🥵
        :else '😀))

(defn convert-temp [value current-unit desired-unit]
  (if (= current-unit desired-unit)
    value
    (cond (= current-unit :fahrenheit) (f-to-c value)
          (= current-unit :celcius) (c-to-f value))))

(defn calc-temp [unit value]
  (let [c (convert-temp value unit :celcius)
        f (convert-temp value unit :fahrenheit)
        emoji (c-to-emoji c)]
    (hash-map :celcius c :fahrenheit f :emoji emoji)))

(def temp-data (r/atom (calc-temp :celcius 0)))

(defn slider [unit value min max]
  [:input {:type "range" :value value :min min :max max
           :class "w-full"
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
      (Math/round celcius) "° Celcius"
      [slider :celcius celcius -100 +100]]
     [:div
      (Math/round fahrenheit) "° Fahrenheit"
      [slider :fahrenheit fahrenheit -212 +212]]]))

(defn template []
  [:div {:class "max-w-4xl mx-auto min-h-screen flex flex-col justify-center p-12"}
   [temp-component]])

(defn ^:dev/after-load start
  []
  (rd/render [template]
             (.getElementById js/document "app")))

(defn init []
  (println "Hello World"); This prints to the browser console
  (start))
