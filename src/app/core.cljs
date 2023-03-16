(ns app.core
  (:require [reagent.core :as r]
            ["react-dom/client" :refer [createRoot]]
            ;; [reagent.dom :as rd]
            [cljs.pprint :refer [pprint]]
            [cljs.tools.reader]
            [sci.core :as sci]))

;; create cljs.user
;; (set! (.. js/window -cljs -user) #js {})
;; (enable-console-print!)
;; (sci/alter-var-root sci/print-fn (constantly *print-fn*))
;; (sci/alter-var-root sci/out (constantly *out*))


(defn evaluate [input]
  (try
    (let [output (sci/eval-string input)]
      (with-out-str (pprint output)))
    (catch js/Error e (str "caught exception: " e))))

(defn editor [defaultValue]
  (let [input (r/atom defaultValue)
        output (r/atom "")] (fn [] [:div
                                    [:textarea {:defaultValue @input
                                                :className "border-r border-b w-full h-16 px-2 py-1 bg-transparent"
                                                :spellCheck false
                                                :on-change #(reset! input (-> % .-target .-value))}]
                                    [:textarea {:className "border-b  w-full lg:w-1/2 h-16 px-2 py-1 bg-transparent"
                                                :value @output
                                                :readOnly true}]
                                    [:input {:type "button"
                                             :className "rounded bg-blue-600 py-1 px-2 text-sm font-semibold text-white shadow-sm hover:bg-blue-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-blue-600"
                                             :value "Run"
                                             :on-click #(reset! output (evaluate @input))}]])))

(defn clojure-runnable [input]
  (let [output (r/atom "")] (fn [] [:div {:className "my-2 bg-[#f5f2f0] p-1 rounded-lg"}
                                    [:pre [:code {:className "language-clojure"} input]]
                                    [:input {:type "button"
                                             :className "rounded bg-blue-600 py-1 px-2 ml-4 text-sm font-semibold text-white shadow-sm hover:bg-blue-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-blue-600"
                                             :value "Run"
                                             :on-click #(reset! output (evaluate input))}]
                                    [:div {:className "ml-4 w-full lg:w-1/2 h-16 py-1 bg-transparent"} @output]])))
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
   [:div {:className "basis-2/3 min-h-screen px-4 py-3"}
    [basic-functions]
    [basic-vectors]
    [basic-maps]]
   [:div {:className "basis-1/3 min-h-screen bg-black text-white"} [editor "Type here!"]]])

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
