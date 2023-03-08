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
(sci/alter-var-root sci/out (constantly *out*))


(defn evaluate [input]
  (sci/eval-string input))

(defn editor [defaultValue]
  (let [input (r/atom defaultValue)
        output (r/atom "; click run to see the output")] (fn [] [:div {:className "mt-2"}
                                                                 [:textarea {:defaultValue @input
                                                                             :className "border w-full lg:w-1/2 h-16"
                                                                             :spellCheck false
                                                                             :on-change #(reset! input (-> % .-target .-value))}]
                                                                 [:textarea {:className "border w-full lg:w-1/2 h-16"
                                                                             :value @output}]
                                                                 [:input {:type "button"
                                                                          :className "rounded bg-blue-600 py-1 px-2 text-sm font-semibold text-white shadow-sm hover:bg-blue-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-blue-600"
                                                                          :value "Run"
                                                                          :on-click #(reset! output (evaluate @input))}]])))

(defn page []
  [:div {:class "max-w-4xl mx-auto min-h-screen flex flex-col"}
   [:h1 {:className "text-2xl font-bold"} "Vetores"]
   [:p "Um vetor é uma coleção ordenada de itens"]
   [editor "[\"Bulbasaur\" \"Charmander\" \"Squirtle\"]"]

   [:h2 {:className "text-1xl font-bold text-gray-600 mt-8"} "Funções básicas"]
   [:p {:className "mt-2"} [:a {:href "https://clojuredocs.org/clojure.core/count" :target "_blank"} "Count"]]
   [editor "(def pokemon [\"Bulbasaur\" \"Charmander\" \"Squirtle\"])
(count pokemon)
"]

   [:p {:className "mt-2"} [:a {:href "https://clojuredocs.org/clojure.core/first" :target "_blank"} "First"]]
   [editor "(def pokemon [\"Bulbasaur\" \"Charmander\" \"Squirtle\"])
(first pokemon)
"]

   [:p {:className "mt-2"} [:a {:href "https://clojuredocs.org/clojure.core/rest" :target "_blank"} "Rest"]]
   [editor "(def pokemon [\"Bulbasaur\" \"Charmander\" \"Squirtle\"])
(rest pokemon)
"]

   [:p {:className "mt-2"} [:a {:href "https://clojuredocs.org/clojure.core/nth" :target "_blank"} "Nth"]]
   [editor "(def pokemon [\"Bulbasaur\" \"Charmander\" \"Squirtle\"])
(nth pokemon 2)
"]

   [:p {:className "mt-2"} [:a {:href "https://clojuredocs.org/clojure.core/conj" :target "_blank"} "Conj (conjunction)"]]
   [editor "(def pokemon [\"Bulbasaur\" \"Charmander\" \"Squirtle\"])
(conj pokemon \"Caterpie\")
"]

   [:p {:className "mt-2"} [:a {:href "https://clojuredocs.org/clojure.core/cons" :target "_blank"} "Cons (construct)"]]
   [editor "(def pokemon [\"Bulbasaur\" \"Charmander\" \"Squirtle\"])
(cons \"Mewtwo\" pokemon)
"]

   [:h2 {:className "text-1xl font-bold text-gray-600 mt-8"} "Performance"]
   [:div "Vetores em Clojure são implementados como diversos blocos de memória contínua, por isso:"
    [:ul {:className "list-disc	pl-8"}
     [:li "Acesso ao " [:code "nth"] " item é rápido"]
     [:li [:code "conj"] " tende a ter performance melhor do que " [:code "cons"]]
     [:li "Criar cópias quase identicas de um vetor tende a ser rápido"]]]])

(defn ^:dev/after-load start
  []
  (rd/render [page]
             (.getElementById js/document "app")))

(defn init []
  ;; (println "Hello World"); This prints to the browser console
  (start))
