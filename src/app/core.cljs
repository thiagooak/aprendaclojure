(ns app.core
  (:require [reagent.core :as r]
            [app.components]
            [reagent.dom :as rd]
            [app.util]
            [cljs.tools.reader]
            [secretary.core :as secretary :refer-macros [defroute]]
            [accountant.core :as accountant]))

;; create cljs.user
;; (set! (.. js/window -cljs -user) #js {})
;; (enable-console-print!)
;; (sci/alter-var-root sci/print-fn (constantly *print-fn*))
;; (sci/alter-var-root sci/out (constantly *out*))

(defn basic-vectors []
  [:div [:h1 {:className "text-2xl font-bold"} "Vetores"]
   [:p "Um vetor é uma coleção ordenada de itens"]
   [app.util/clojure-runnable "[\"Bulbasaur\" \"Charmander\" \"Squirtle\"]"]

   [:h2 {:className "text-1xl font-bold text-gray-600 mt-8"} "Funções básicas"]
   [:p {:className "mt-2"} [:a {:href "https://clojuredocs.org/clojure.core/count" :target "_blank"} "Count"]]
   [app.util/clojure-runnable "(def pokemon [\"Bulbasaur\" \"Charmander\" \"Squirtle\"])
(count pokemon)
"]

   [:p {:className "mt-2"} [:a {:href "https://clojuredocs.org/clojure.core/first" :target "_blank"} "First"]]
   [app.util/clojure-runnable "(def pokemon [\"Bulbasaur\" \"Charmander\" \"Squirtle\"])
(first pokemon)
"]

   [:p {:className "mt-2"} [:a {:href "https://clojuredocs.org/clojure.core/rest" :target "_blank"} "Rest"]]
   [app.util/clojure-runnable "(def pokemon [\"Bulbasaur\" \"Charmander\" \"Squirtle\"])
(rest pokemon)
"]

   [:p {:className "mt-2"} [:a {:href "https://clojuredocs.org/clojure.core/nth" :target "_blank"} "Nth"]]
   [app.util/clojure-runnable "(def pokemon [\"Bulbasaur\" \"Charmander\" \"Squirtle\"])
(nth pokemon 2)
"]

   [:p {:className "mt-2"} [:a {:href "https://clojuredocs.org/clojure.core/conj" :target "_blank"} "Conj (conjunction)"]]
   [app.util/clojure-runnable "(def pokemon [\"Bulbasaur\" \"Charmander\" \"Squirtle\"])
(conj pokemon \"Caterpie\")
"]

   [:p {:className "mt-2"} [:a {:href "https://clojuredocs.org/clojure.core/cons" :target "_blank"} "Cons (construct)"]]
   [app.util/clojure-runnable "(def pokemon [\"Bulbasaur\" \"Charmander\" \"Squirtle\"])
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
   [app.util/clojure-runnable "{:nome \"Charmander\" :tipo \"Fogo\" :peso 8.5}"]
   [:p "No exemplo acima usamos keywords para definir as nossas chaves. Keywords são termos começando com dois pontos (:nome, :tipo e :peso no exemplo acima).
        Não é obrigatório usar keywords para representar as nossa chaves, mas isso nos da uma vantagem já que keywords também são funções que procuram por ela mesmas. Então podemos fazer o seguinte:"]
   [app.util/clojure-runnable "(def pokemon {:nome \"Charmander\" :tipo \"Fogo\" :peso 8.5})
(:tipo pokemon)"]

   [:h2 {:className "text-1xl font-bold text-gray-600 mt-8"} "Funções básicas"]

   [:p {:className "mt-2"} [:a {:href "https://clojuredocs.org/clojure.core/assoc" :target "_blank"} "Assoc"]]
   [app.util/clojure-runnable "(def pokemon {:nome \"Charmander\"})
(assoc pokemon :peso 8.5)"]

   [:p {:className "mt-2"} [:a {:href "https://clojuredocs.org/clojure.core/dissoc" :target "_blank"} "Dissoc"]]
   [app.util/clojure-runnable "(def pokemon {:nome \"Charmander\" :tipo \"Fogo\"})
(dissoc pokemon :tipo)"]

   [:p {:className "mt-2"} [:a {:href "https://clojuredocs.org/clojure.core/keys" :target "_blank"} "Keys"]]
   [app.util/clojure-runnable "(def pokemon {:nome \"Charmander\" :tipo \"Fogo\" :peso 8.5})
(keys pokemon)"]

   [:p {:className "mt-2"} [:a {:href "https://clojuredocs.org/clojure.core/vals" :target "_blank"} "Vals"]]
   [app.util/clojure-runnable "(def pokemon {:nome \"Charmander\" :tipo \"Fogo\" :peso 8.5})
(vals pokemon)"]])

(defn basic-functions []
  [:div [:h1 {:className "text-2xl font-bold"} "Funções"]
   [:p "Clojure faz parte da família de linguagens Lisp. "
    "O nome Lisp vem do inglês List Processing (Processamento de Listas). "
    "Listas são definidas entre parênteses e uma lista é interpretada por padrão considerando que seu primeiro item é uma função e os itens a seguir são parâmetros."
    "No exemplo abaixo, \"+\" é uma função."]
   [app.util/clojure-runnable "(+ 1 2 3)"]

   [:p "Para criar uma nova função você pode usar a função "
    (app.util/clojure-inline "(defn)")
    ". No exemplo abaixo, Definimos uma função chamada soma que por sua vez aplica "
    "a função \"+\" aos parâmetros \"primeiro\" e \"segundo\""]
   [app.util/clojure-runnable "(defn soma [primeiro segundo]
                (+ primeiro segundo))

              (soma 10 1)"]])

(defn home-page [] (app.components/page basic-functions))
(defn vectors-page [] (app.components/page basic-vectors))
(defn maps-page [] (app.components/page basic-maps))


(def selected-page (r/atom home-page))

(defn page []
  [@selected-page])

(secretary/defroute "/" []
  (reset! selected-page home-page))

(secretary/defroute "/vectors" []
  (reset! selected-page vectors-page))

(secretary/defroute "/maps" []
  (reset! selected-page maps-page))

(defn mount-root []
  (rd/render [page] (.getElementById js/document "app")))

(defn init! []
  (accountant/configure-navigation!
   {:nav-handler
    (fn [path]
      (secretary/dispatch! path))
    :path-exists?
    (fn [path]
      (secretary/locate-route path))})
  (accountant/dispatch-current!)
  (mount-root))

(defn ^:dev/after-load re-render
  []
  ;; The `:dev/after-load` metadata causes this function to be called
  ;; after shadow-cljs hot-reloads code.
  ;; This function is called implicitly by its annotation.
  (init!))