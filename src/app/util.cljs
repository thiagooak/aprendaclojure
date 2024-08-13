(ns app.util
  (:require [reagent.core :as r]
            [cljs.pprint :refer [pprint]]
            [sci.core :as sci]
            [clojure.string :refer [replace]]))

(defn evaluate [input]
  (try
    (let [output (sci/eval-string input)]
      (with-out-str (pprint output)))
    (catch js/Error e (str "caught exception: " e))))

(def repl-input (r/atom "\"Hello, World!\""))
(def repl-output (r/atom ""))
(defn repl-print [] (let [parent (.getElementById js/document "print")
                          child-input (.createElement js/document "p")
                          child-output (.createElement js/document "p")]

                      (reset! repl-output (evaluate @repl-input))

                      (.remove (.-classList (.getElementById js/document "repl-input")) "animate-pulse")

                      (set! (.-innerHTML child-input) (str "<div class=\"flex\">user=> <div>" (replace @repl-input "\n" "<br>") "</div></div>"))
                      (set! (.-innerHTML child-output) (str "<div class=\"border-dashed border-gray-600 border-b mb-2 pb-2\">" @repl-output "</div>"))
                      (.appendChild parent child-input)
                      (.appendChild parent child-output)
                      (.scrollTo parent 0 (.-scrollHeight parent))))

(defn update-repl-input [value]
  (let [el (.getElementById js/document "repl-input")]
    (reset! repl-input value)
    (set! (.-value el) value)
    (.add (.-classList el) "animate-pulse")))

(defn editor []
  (fn []
    [:div {:className "h-full"}
     [:div {:id "print" :className "px-2 py-1 h-3/4 max-h-3/4 overflow-scroll scroll-smooth"} " "]
     [:div {:className "flex-row sm:flex px-2 sm:h-1/4"}
      "user=>"
      [:textarea {:defaultValue @repl-input
                  :id "repl-input"
                  :className "w-full h-full p-1 bg-gray-800"
                  :spellCheck false
                  :on-focus #(.remove (-> % .-target .-classList) "animate-pulse")
                  :on-change #(reset! repl-input (-> % .-target .-value))}]
      [:input {:type "button"
               :className "rounded bg-blue-600 py-1 px-2 text-sm font-semibold text-white shadow-sm hover:bg-blue-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-blue-600"
               :value "Enter"
               :on-click #(repl-print)}]]]))

(defn clojure-runnable [input]
  [:div {:className "mb-4"}
   [:pre [:code {:className "language-clojure"} input]]

   [:input {:type "button"
            :className "rounded bg-blue-600 py-1 px-2 text-sm font-semibold text-white shadow-sm hover:bg-blue-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-blue-600"
            :value "Enviar código para o REPL"
            :on-click #(update-repl-input input)}]])

(defn clojure-inline [input]
  [:code {:className "language-clojure"} input])