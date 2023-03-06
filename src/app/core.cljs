(ns app.core
  (:require [reagent.core :as r]
            [reagent.dom :as rd]
            [cljs.js :refer [empty-state eval-str js-eval]]
            [cljs.pprint :refer [pprint]]
            [goog.dom :as gdom]
            [cljs.tools.reader]
            [sci.core :as sci]))


;; (sci/eval-string "(inc 1)") => ;; 2
;; (sci/eval-string "(inc x)" {:namespaces {'user {'x 2}}}) ;;=> 3

;; https://github.com/swannodette/swannodette.github.com/blob/master/code/blog/src/blog/cljs_next/core.cljs

;; create cljs.user
(set! (.. js/window -cljs -user) #js {})

;; (def temp-data (r/atom (calc-temp :celcius 0)))

;; (defn slider [unit value min max]
;;   [:input {:type "range" :value value :min min :max max
;;            :class "w-full"
;;            :on-change (fn [e]
;;                         (let [new-value (js/parseInt (.. e -target -value))]
;;                           (swap! temp-data
;;                                  (fn []
;;                                    (calc-temp unit new-value)))))}])

(enable-console-print!)
(sci/alter-var-root sci/print-fn (constantly *print-fn*))

(defn evaluate [input callback]
  (callback (sci/eval-string input))
  )

(defn editor [id defaultValue]
  (let [in-id (str id "in")
        out-id (str id "out")] [:div
   [:textarea {:id in-id :defaultValue defaultValue} ]
   [:textarea {:id out-id :defaultValue "."}]
   [:input {:type "button" :value "Run" :on-click (fn []
                                                    (let [in (gdom/getElement in-id)]
                                                      (evaluate (.-value in) (fn [result]
                                                                               (let [out (gdom/getElement out-id)]
                                                                                 (set! (.-value out) result))))))}]]))

(defn template []
  [:div {:class "max-w-4xl mx-auto min-h-screen flex flex-col justify-center p-12"}
   [editor "add" "(+ 3 4)"]
   [editor "print" "(print 5 4)"]
   ])

(defn ^:dev/after-load start
  []
  (rd/render [template]
             (.getElementById js/document "app")))

(defn init []
  (println "Hello World"); This prints to the browser console
  (start))
