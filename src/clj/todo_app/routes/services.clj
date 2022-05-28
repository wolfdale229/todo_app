(ns todo-app.routes.services
  (:require [todo-app.tasks :as task]
            [reitit.swagger :as swagger]
            [reitit.swagger-ui :as swagger-ui]
            [ring.util.http-response :as response]
            [todo-app.middleware.formats :as formats]
            [reitit.ring.coercion :as coercion]
            [reitit.coercion.spec :as spec-coercion]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [reitit.ring.middleware.exception :as exception]
            [reitit.ring.middleware.multipart :as multipart]
            [reitit.ring.middleware.parameters :as parameters]))

(defn task-service []
  ["/api"
   {:middleware [parameters/parameters-middleware
                 muuntaja/format-negotiate-middleware
                 muuntaja/format-response-middleware
                 exception/exception-middleware
                 muuntaja/format-request-middleware
                 coercion/coerce-request-middleware
                 coercion/coerce-response-middleware
                 multipart/multipart-middleware]
    :muuntaja formats/instance
    :coercion spec-coercion/coercion
    :swagger {:id ::api}}

   ["" {:no-doc true
        :swagger {:info {:title "To-Do Application"}}}
    ["/swagger.json" (swagger/create-swagger-handler)]
    ["/swagger-ui*" (swagger-ui/create-swagger-ui-handler
                     {:url "/api/swagger.json"})]]

   ;; View all saved tasks
   ["/tasks" {:get {:parameters nil
                    :responses {:200 {:tasks [{:title string?
                                               :description string?}]}}
                    :handler (fn [_]
                               (response/ok (task/list-tasks)))}}]

   ["/new" {:post {:parameters {:body {:title string?
                                       :description string?} }
                   :responses {
                          200 {:body map?}
                          400 {:body map?}
                          500 {:errors map?}
                          }
                   :handler (fn [{{params :body} :parameters}]
                              (try
                                (task/new-task params)
                                (response/ok {:status :ok} )
                                (catch Exception e
                                  (let [{id :error-id
                                         errors :errors}(ex-data e)]
                                    (case id
                                      :validation (response/bad-request {:errors errors})
                                      ;;else
                                      (response/internal-server-error
                                       {:errors {:server-error ["Failed to save message"]}}))))))}}]
  ])

