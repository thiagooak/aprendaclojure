(ns app.pages
  (:require [app.util :as u]))

(defn h1 [text] [:h1 {:className "text-2xl font-bold"} text])
(defn h2 [text] [:h2 {:className "text-1xl font-bold text-gray-600 mt-8"} text])
(defn link [href text] [:p {:className "mt-2"} [:a {:href href :target "_blank"} text]])

(defn home []
  [:div (h1 "Início")
   [:p "Clojure faz parte da família de linguagens Lisp. "
    "O nome Lisp vem do inglês List Processing (Processamento de Listas). "
    "Listas são definidas entre parênteses e uma lista é interpretada por padrão considerando que seu primeiro item é uma função e os itens a seguir são parâmetros."]
   [u/runnable "(println \"Hello, world!\")"]
   [u/runnable "(str \"Hello, \" \"again!\")"]
   (h2 "Operações aritméticas")
   [u/runnable "(+ 1 2 3)"]
   [u/runnable "(+ (* 100 (/ 9 5)) 32)"]
   (h2 "Bind")
   [u/runnable "(def temp-celcius 20)
(+ (* temp-celcius (/ 9 5)) 32)"]
   (h2 "Funções")
   [u/runnable "(defn celcius->fahrenheit [c]
  (+ (* c (/ 9 5)) 32))

(println (celcius->fahrenheit 0))
(println (celcius->fahrenheit 100))
   "]
   ])

(defn basic-vectors []
  [:div (h1 "Vetores")
   [:p "Um vetor é uma coleção ordenada de itens"]
   [u/runnable "[\"Bulbasaur\" \"Charmander\" \"Squirtle\"]"]

   (h2 "Performance")
   [:div "Vetores em Clojure são implementados como blocos de memória contínua, por isso:"
    [:ul {:className "list-disc	pl-8"}
     [:li "Acesso ao " [:code "nth"] " item é rápido"]
     [:li [:code "conj"] " tende a ter performance melhor do que " [:code "cons"]]
     [:li "Criar cópias quase identicas de um vetor tende a ser rápido"]]]

   (h2 "Funções básicas")
   (link "https://clojuredocs.org/clojure.core/count" "Count")
   [u/runnable "(def pokemon [\"Bulbasaur\" \"Charmander\" \"Squirtle\"])
(count pokemon)
"]

   (link "https://clojuredocs.org/clojure.core/first" "First")
   [u/runnable "(def pokemon [\"Bulbasaur\" \"Charmander\" \"Squirtle\"])
(first pokemon)
"]

   (link "https://clojuredocs.org/clojure.core/rest" "Rest")
   [u/runnable "(def pokemon [\"Bulbasaur\" \"Charmander\" \"Squirtle\"])
(rest pokemon)
"]

   (link "https://clojuredocs.org/clojure.core/nth" "Nth")
   [u/runnable "(def pokemon [\"Bulbasaur\" \"Charmander\" \"Squirtle\"])
(nth pokemon 2)
"]

   (link "https://clojuredocs.org/clojure.core/conj" "Conj (conjunction)")
   [u/runnable "(def pokemon [\"Bulbasaur\" \"Charmander\" \"Squirtle\"])
(conj pokemon \"Caterpie\")
"]

   (link "https://clojuredocs.org/clojure.core/cons" "Cons (construct)")
   [u/runnable "(def pokemon [\"Bulbasaur\" \"Charmander\" \"Squirtle\"])
(cons \"Mewtwo\" pokemon)
"]])

(defn basic-maps []
  [:div [:h1 {:className "text-2xl font-bold"} "Maps, ou Vetores Associativos"]
   [:p "Um vetor associativo (em inglês, map) é um conjunto não-ordenado de pares formados por uma chave e um valor."]
   [u/runnable "{:nome \"Charmander\" :tipo \"Fogo\" :peso 8.5}"]
   [:p "No exemplo acima usamos keywords para definir as nossas chaves. Keywords são termos começando com dois pontos (:nome, :tipo e :peso no exemplo acima)."
    ]
   [:p "Não é obrigatório usar keywords para representar as nossa chaves, mas isso nos da uma vantagem já que keywords também são funções que procuram por ela mesmas. Então podemos fazer o seguinte:"]
   [u/runnable "(def pokemon {:nome \"Charmander\" :tipo \"Fogo\" :peso 8.5})
(:tipo pokemon)"]

   [:h2 {:className "text-1xl font-bold text-gray-600 mt-8"} "Funções básicas"]

   (link "https://clojuredocs.org/clojure.core/assoc" "Assoc")
   [u/runnable "(def pokemon {:nome \"Charmander\"})
(assoc pokemon :peso 8.5)"]

   (link "https://clojuredocs.org/clojure.core/dissoc" "Dissoc")
   [u/runnable "(def pokemon {:nome \"Charmander\" :tipo \"Fogo\"})
(dissoc pokemon :tipo)"]

   (link "https://clojuredocs.org/clojure.core/keys" "Keys")
   [u/runnable "(def pokemon {:nome \"Charmander\" :tipo \"Fogo\" :peso 8.5})
(keys pokemon)"]

   (link "https://clojuredocs.org/clojure.core/vals" "Vals")
   [u/runnable "(def pokemon {:nome \"Charmander\" :tipo \"Fogo\" :peso 8.5})
(vals pokemon)"]])

(defn basic-functions []
  [:div (h1 "Funções")
   [:p "Para criar uma nova função você pode usar a função "
    (u/inline "(defn)")
    ". No exemplo abaixo, Definimos uma função chamada soma que por sua vez aplica "
    "a função \"+\" aos parâmetros \"primeiro\" e \"segundo\""]
   [u/runnable "(defn soma [primeiro segundo]
           (+ primeiro segundo))
           (soma 10 1)"]])