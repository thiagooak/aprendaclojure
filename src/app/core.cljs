(ns app.core
  (:require [app.components]
            [app.pages]
            ["react-dom/client" :refer [createRoot]]
            [app.util]
            [cljs.tools.reader]
            [secretary.core :as secretary :refer-macros [defroute]]
            [accountant.core :as accountant]
            [reagent.core :as r]))

(defonce root (createRoot (.getElementById js/document "app")))

(defn mount-root [page]
  (.render root (r/as-element [page])))

(defroute "/" []
  (mount-root #(app.components/page app.pages/basic-functions)))

(defroute "/#vectors" []
  (mount-root #(app.components/page app.pages/basic-vectors)))

(defroute "/#maps" []
  (mount-root #(app.components/page app.pages/basic-maps)))

(defn init []
  (accountant/configure-navigation!
   {:nav-handler
    (fn [path]
      (secretary/dispatch! path))
    :path-exists?
    (fn [path]
      (secretary/locate-route path))})
  (accountant/dispatch-current!))

(defn ^:dev/after-load re-render
  []
  ;; The `:dev/after-load` metadata causes this function to be called
  ;; after shadow-cljs hot-reloads code.
  ;; This function is called implicitly by its annotation.
  (init))