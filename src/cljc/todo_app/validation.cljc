(ns todo-app.validation
  (:require [struct.core :as st]))

(def data-schema
  [[:title
    st/required
    st/string]
   [:description
    st/string
    st/required
    {:message "Task description should have at least 10 characters"
     :validate (fn [dsp] (>= (count dsp) 10))}]])

(def id-schema
  ;; id parameter entered should start from 1, no decimal number
  [[:id
    st/required
    {:message "Id must be a number (0-9)"
     :validate #(pos-int? %)}]])


(defn validate-task [params]
  (first (st/validate params data-schema)))

(defn validate-id [params]
  (first (st/validate params id-schema)))
