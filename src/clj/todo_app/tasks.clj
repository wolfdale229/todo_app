(ns todo-app.tasks
  (:require [todo-app.db.core :as db]
            [todo-app.validation :refer [validate-task validate-id]]))


(defn list-tasks []
  {:tasks (db/list-task)})

(defn new-task [task]
  (if-let [errors (validate-task task)]
    (throw (ex-info "Task is invalid"
                    {:error-id :validation
                     :errors errors}))
    (db/new-task task)))
