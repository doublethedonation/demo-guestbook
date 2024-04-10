(ns londonclj.guestbook.web.controllers.guestbook
  (:require [clojure.tools.logging :as log]
            [ring.util.http-response :as http-response]
            [londonclj.guestbook.web.routes.utils :as utils]))

(defn save-message!
  [{{:keys [name message subject]} :body-params :as request}]
  (log/debug "saving message" name message)
  (let [{:keys [query-fn]} (utils/route-data request)]
    (try
      (query-fn :save-message! {:name name :message message :subject subject})
      (http-response/ok)
      (catch Exception e
        (log/error e "failed to save message!")
        (http-response/internal-server-error (.getMessage e))))))

(defn delete-message!
  [{{:keys [id]} :body-params :as request}]
  (log/debug "deleting message" id)
  (let [{:keys [query-fn]} (utils/route-data request)]
    (try
      (query-fn :delete-message! {:id id})
      (http-response/ok)
      (catch Exception e
        (log/error e "failed to delete message!")
        (http-response/internal-server-error (.getMessage e))))))

(defn list-messages
  [request]
  (http-response/ok
   ((:query-fn (utils/route-data request)) :get-messages {})))
