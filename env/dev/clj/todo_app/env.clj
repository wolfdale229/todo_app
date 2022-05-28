(ns todo-app.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [todo-app.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[todo_app started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[todo_app has shut down successfully]=-"))
   :middleware wrap-dev})
