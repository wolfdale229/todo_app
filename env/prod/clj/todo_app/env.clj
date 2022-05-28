(ns todo-app.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[todo_app started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[todo_app has shut down successfully]=-"))
   :middleware identity})
