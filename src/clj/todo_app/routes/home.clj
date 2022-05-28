(ns todo-app.routes.home
  (:require
   [todo-app.layout :as layout]
   [todo-app.db.core :as db]
   [todo-app.middleware :as middleware]
   [ring.util.response]))

(defn home-page [request]
  (layout/render request "home.html" {:tasks (db/list-task)}))

(defn about-page [request]
  (layout/render request "about.html"))
 
(defn home-routes []
  [ "" 
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats]}
   ["/" {:get home-page}]
   ["/about" {:get about-page}]])

