(ns app.core
  (:require [reagent.core :as r]
            ["react-dom/client" :refer [createRoot]]
            ;; [reagent.dom :as rd]
            [cljs.pprint :refer [pprint]]
            [cljs.tools.reader]
            [sci.core :as sci]
            [goog.dom :as gdom]))
;; create cljs.user
(set! (.. js/window -cljs -user) #js {})

;; (enable-console-print!)
;; (sci/alter-var-root sci/print-fn (constantly *print-fn*))
;; (sci/alter-var-root sci/out (constantly *out*))


(defn evaluate [input]
  (try
    (let [output (sci/eval-string input)]
      (with-out-str (pprint output)))
    (catch js/Error e (str "caught exception: " e))))

(def repl-input (r/atom "(+ 1 2 3)"))
(def repl-output (r/atom ""))
(defn repl-print [] (let [parent (.getElementById js/document "print")
                          child-input (.createElement js/document "p")
                          child-output (.createElement js/document "p")]

                      (reset! repl-output (evaluate @repl-input))

                      (set! (.-innerHTML child-input) (str "user=> " @repl-input))
                      (set! (.-innerHTML child-output) @repl-output)
                      (.appendChild parent child-input)
                      (.appendChild parent child-output)))

(defn update-repl-input [value]
  (let [el (.getElementById js/document "repl-input")]
    (reset! repl-input value)
    (set! (.-value el) value)))

(comment
  (update-repl-input "hi"))

(defn editor []
  (fn []
    [:div [:div {:id "print" :className "px-2 py-1"}]
     [:div {:className "flex px-2"}
      "user=>"
      [:textarea {:defaultValue @repl-input
                  :id "repl-input"
                  :className "w-full h-32 p-1 bg-gray-800"
                  :spellCheck false
                  :on-change #(reset! repl-input (-> % .-target .-value))}]
      [:input {:type "button"
               :className "rounded bg-blue-600 py-1 px-2 text-sm font-semibold text-white shadow-sm hover:bg-blue-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-blue-600"
               :value "Enter"
               :on-click #((repl-print))}]]]))

(defn clojure-runnable [input]
  [:div {:className "my-2 bg-[#f5f2f0] p-1 rounded-lg"}
   [:pre [:code {:className "language-clojure"} input]]

   [:input {:type "button"
            :className "rounded bg-blue-600 py-1 px-2 ml-4 text-sm font-semibold text-white shadow-sm hover:bg-blue-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-blue-600"
            :value "Enviar para o REPL"
            :on-click #(update-repl-input input)}]])
(defn clojure-inline [input]
  [:code {:className "language-clojure"} input])

(defn basic-functions []
  [:div [:h1 {:className "text-2xl font-bold"} "Funções"]
   [:p "Clojure faz parte da família de linguagens Lisp. O nome Lisp vem do inglês List Processing (Processamento de Listas). Listas são definidas entre parênteses e uma lista é interpretada por padrão considerando que seu primeiro item é uma função e os itens a seguir são parâmetros."]
   [clojure-runnable "; 🔧 substitua \"função\" abaixo por +
(+ 1 2 3)"]

   [:p "Para criar uma nova função você pode usar a função " (clojure-inline "(defn)") " como no exemplo abaixo"]
   [clojure-runnable "; define uma função chamada soma que por sua vez aplica
; a função \"+\" aos parâmetros \"primeiro\" e \"segundo\"
(defn soma [primeiro segundo]
 (+ primeiro segundo))

; chama a função \"soma\" criada acima
(soma 10 1)"]])

(defn basic-vectors []
  [:div [:h1 {:className "text-2xl font-bold"} "Vetores"]
   [:p "Um vetor é uma coleção ordenada de itens"]
   [clojure-runnable "[\"Bulbasaur\" \"Charmander\" \"Squirtle\"]"]

   [:h2 {:className "text-1xl font-bold text-gray-600 mt-8"} "Funções básicas"]
   [:p {:className "mt-2"} [:a {:href "https://clojuredocs.org/clojure.core/count" :target "_blank"} "Count"]]
   [clojure-runnable "(def pokemon [\"Bulbasaur\" \"Charmander\" \"Squirtle\"])
(count pokemon)
"]

   [:p {:className "mt-2"} [:a {:href "https://clojuredocs.org/clojure.core/first" :target "_blank"} "First"]]
   [clojure-runnable "(def pokemon [\"Bulbasaur\" \"Charmander\" \"Squirtle\"])
(first pokemon)
"]

   [:p {:className "mt-2"} [:a {:href "https://clojuredocs.org/clojure.core/rest" :target "_blank"} "Rest"]]
   [clojure-runnable "(def pokemon [\"Bulbasaur\" \"Charmander\" \"Squirtle\"])
(rest pokemon)
"]

   [:p {:className "mt-2"} [:a {:href "https://clojuredocs.org/clojure.core/nth" :target "_blank"} "Nth"]]
   [clojure-runnable "(def pokemon [\"Bulbasaur\" \"Charmander\" \"Squirtle\"])
(nth pokemon 2)
"]

   [:p {:className "mt-2"} [:a {:href "https://clojuredocs.org/clojure.core/conj" :target "_blank"} "Conj (conjunction)"]]
   [clojure-runnable "(def pokemon [\"Bulbasaur\" \"Charmander\" \"Squirtle\"])
(conj pokemon \"Caterpie\")
"]

   [:p {:className "mt-2"} [:a {:href "https://clojuredocs.org/clojure.core/cons" :target "_blank"} "Cons (construct)"]]
   [clojure-runnable "(def pokemon [\"Bulbasaur\" \"Charmander\" \"Squirtle\"])
(cons \"Mewtwo\" pokemon)
"]

   [:h2 {:className "text-1xl font-bold text-gray-600 mt-8"} "Performance"]
   [:div "Vetores em Clojure são implementados como diversos blocos de memória contínua, por isso:"
    [:ul {:className "list-disc	pl-8"}
     [:li "Acesso ao " [:code "nth"] " item é rápido"]
     [:li [:code "conj"] " tende a ter performance melhor do que " [:code "cons"]]
     [:li "Criar cópias quase identicas de um vetor tende a ser rápido"]]]])

(defn basic-maps []
  [:div [:h1 {:className "text-2xl font-bold"} "Vetores Associativos (Maps)"]
   [:p "Um vetor associativo (em inglês, map) é um conjunto não-ordenado de pares formados por uma chave e um valor."]
   [clojure-runnable "{:nome \"Charmander\" :tipo \"Fogo\" :peso 8.5}"]
   [:p "No exemplo acima usamos keywords para definir as nossas chaves. Keywords são termos começando com dois pontos (:nome, :tipo e :peso no exemplo acima).
        Não é obrigatório usar keywords para representar as nossa chaves, mas isso nos da uma vantagem já que keywords também são funções que procuram por ela mesmas. Então podemos fazer o seguinte:"]
   [clojure-runnable "(def pokemon {:nome \"Charmander\" :tipo \"Fogo\" :peso 8.5})
(:tipo pokemon)"]

   [:h2 {:className "text-1xl font-bold text-gray-600 mt-8"} "Funções básicas"]

   [:p {:className "mt-2"} [:a {:href "https://clojuredocs.org/clojure.core/assoc" :target "_blank"} "Assoc"]]
   [clojure-runnable "(def pokemon {:nome \"Charmander\"})
(assoc pokemon :peso 8.5)"]

   [:p {:className "mt-2"} [:a {:href "https://clojuredocs.org/clojure.core/dissoc" :target "_blank"} "Dissoc"]]
   [clojure-runnable "(def pokemon {:nome \"Charmander\" :tipo \"Fogo\"})
(dissoc pokemon :tipo)"]

   [:p {:className "mt-2"} [:a {:href "https://clojuredocs.org/clojure.core/keys" :target "_blank"} "Keys"]]
   [clojure-runnable "(def pokemon {:nome \"Charmander\" :tipo \"Fogo\" :peso 8.5})
(keys pokemon)"]

   [:p {:className "mt-2"} [:a {:href "https://clojuredocs.org/clojure.core/vals" :target "_blank"} "Vals"]]
   [clojure-runnable "(def pokemon {:nome \"Charmander\" :tipo \"Fogo\" :peso 8.5})
(vals pokemon)"]])

(defn page []
  [:div {:className "flex flex-row"}
   [:div {:className "basis-2/3 max-h-screen overflow-scroll"}
    [:header {:className "flex bg-[#164489] p-4"}
     [:div {:className "mx-auto"}
      [:a {:href "/"}
       [:img
        {:width "300"
         :src "/img/1x/aprenda-clojure-logo.png"
         :alt "(Aprenda Clojure) em branco com o primeiro parêntese amarelo"
         :srcset "/img/1x/aprenda-clojure-logo.png 1x, /img/2x/aprenda-clojure-logo.png 2x"}]]
      [:a {:href "https://github.com/thiagooak/aprendaclojure" :className "text-gray-400 hover:text-gray-500"} >
       [:span {:className "sr-only"} "GitHub"]
       [:svg {:className "h-6 w-6" :fill "currentColor" :viewBox "0 0 24 24" :aria-hidden "true"} >
        [:path
         {:fill-rule "evenodd"
          :d "M12 2C6.477 2 2 6.484 2 12.017c0 4.425 2.865 8.18 6.839 9.504.5.092.682-.217.682-.483 0-.237-.008-.868-.013-1.703-2.782.605-3.369-1.343-3.369-1.343-.454-1.158-1.11-1.466-1.11-1.466-.908-.62.069-.608.069-.608 1.003.07 1.531 1.032 1.531 1.032.892 1.53 2.341 1.088 2.91.832.092-.647.35-1.088.636-1.338-2.22-.253-4.555-1.113-4.555-4.951 0-1.093.39-1.988 1.029-2.688-.103-.253-.446-1.272.098-2.65 0 0 .84-.27 2.75 1.026A9.564 9.564 0 0112 6.844c.85.004 1.705.115 2.504.337 1.909-1.296 2.747-1.027 2.747-1.027.546 1.379.202 2.398.1 2.651.64.7 1.028 1.595 1.028 2.688 0 3.848-2.339 4.695-4.566 4.943.359.309.678.92.678 1.855 0 1.338-.012 2.419-.012 2.747 0 .268.18.58.688.482A10.019 10.019 0 0022 12.017C22 6.484 17.522 2 12 2z"
          :clip-rule "evenodd"}]]]]]
    [:div {:className "px-4 py-3"} [basic-functions]
     [basic-vectors]
     [basic-maps]]]
   [:div {:className "basis-1/3 max-h-screen overflow-scroll bg-black text-white"}
    [editor]]])

(defonce root (createRoot (.getElementById js/document "app")))

(defn init
  []
  (.render root (r/as-element [page])))

(defn ^:dev/after-load re-render
  []
  ;; The `:dev/after-load` metadata causes this function to be called
  ;; after shadow-cljs hot-reloads code.
  ;; This function is called implicitly by its annotation.
  (init))
