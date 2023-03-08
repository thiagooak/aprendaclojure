(ns app.core
  (:require [reagent.core :as r]
            [reagent.dom :as rd]
            ;; [cljs.pprint :refer [pprint]]
            [cljs.tools.reader]
            [sci.core :as sci]))

;; create cljs.user
;; (set! (.. js/window -cljs -user) #js {})

(enable-console-print!)
(sci/alter-var-root sci/print-fn (constantly *print-fn*))

(defn evaluate [input]
  (sci/eval-string input))

(defn editor [defaultValue]
  (let [input (r/atom defaultValue)
        output (r/atom "; click run to see the output")] (fn [] [:div {:className "mt-2"}
                                [:textarea {
                                            :defaultValue @input
                                            :className "border w-full"
                                            :spellCheck false
                                            :on-change #(reset! input (-> % .-target .-value))}]
                                [:div @output]
                                [:input {
                                         :type "button"
                                         :className "rounded bg-blue-600 py-1 px-2 text-sm font-semibold text-white shadow-sm hover:bg-blue-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-blue-600"
                                         :value "Run"
                                         :on-click #(reset! output (evaluate @input))}]])))

(defn template []
  [:div {:class "max-w-4xl mx-auto min-h-screen flex flex-col p-12"}
   [editor "(+ 3 4)"]
   [editor "(print 5 4)"]
   ])

(defn ^:dev/after-load start
  []
  (rd/render [template]
             (.getElementById js/document "app")))

(defn init []
  ;; (println "Hello World"); This prints to the browser console
  (start))
