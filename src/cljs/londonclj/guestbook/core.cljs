(ns londonclj.guestbook.core
  (:require
    [ajax.core :refer [GET POST]]
    [reagent.core :as r]
    [reagent.dom :as d]))

;; -------------------------
;; Views

(defn delete-message [id]
  (POST "/api/delete"
        {:params {:id id}
         :handler #(do
                     (js/console.log "Delete handler")
                     )}))

(defn find-my-message [messages id]
  (filter #(not= id (get % :id)) messages))

(defn message-list [messages]
  [:ul.content
   (for [{:keys [id timestamp message name subject]} @messages]
     ^{:key timestamp}
     [:<>
      [:li
       [:time (.toLocaleString timestamp)]
       [:br]
       [:p [:strong subject]]
       [:p message]
       [:p " - " name]
       [:input.button
        {:type     :button
         :on-click #(do
                      (js/console.log "delete!")
                      (delete-message id)
                      (swap! messages find-my-message id))
         :value    "delete"}]
       [:hr]]])])

(defn get-messages [messages]
  (GET "/api/list"
       {:headers {"Accept" "application/transit+json"}
        :handler #(reset! messages (vec %))}))

(defn send-message! [fields errors messages]
  (POST "/api/create"
        {:headers {"Accept"       "application/transit+json"
                   "x-csrf-token" (.-value (.getElementById js/document "token"))}
         :params @fields
         :handler #(do
                     (reset! errors nil)
                     (swap! messages conj (assoc @fields :timestamp (js/Date.))))
         :error-handler #(do
                           (.log js/console (str %))
                           (reset! errors (get-in % [:response :errors])))}))

(defn errors-component [errors id]
      (when-let [error (id @errors)]
                [:div.alert.alert-danger (clojure.string/join error)]))

(defn message-form [messages]
  (let [fields (r/atom {})
        errors (r/atom nil)]
    (fn []
      [:div.columns>div.column.is-two-thirds>div.content
       [errors-component errors :server-error]
       [:div.control
        [errors-component errors :name]
        [:p "Name:"
         [:input.input
          {:type      :text
           :name      :name
           :on-change #(swap! fields assoc :name (-> % .-target .-value))
           :value     (:name @fields)}]]
        [errors-component errors :subject]
        [:p "Subject:"
         [:input.input
          {:type      :text
           :name      :subject
           :on-change #(swap! fields assoc :subject (-> % .-target .-value))
           :value     (:subject @fields)}]]
        [errors-component errors :message]
        [:p "Message:"
         [:textarea.textarea
          {:rows      4
           :cols      50
           :name      :message
           :value     (:message @fields)
           :on-change #(swap! fields assoc :message (-> % .-target .-value))}]]
        [:input.button
         {:type     :submit
          :on-click #(send-message! fields errors messages)
          :value    "comment"}]]])))

(defn home []
  (let [messages (r/atom nil)]
    (get-messages messages)
    (fn []
      [:section.section>div.container
       [:h1.title "Guestbook"]
       #_[:div.row
        [:div.span12
         [message-list messages]]]
       [:div.card
        [:h3.card-header "Messages"]
        [:div.card-body
         [message-list messages]]]
       [:div.row
        [:div.span12
         [message-form messages]]]])))

;; -------------------------
;; Initialize app

(defn ^:dev/after-load mount-root []
      (d/render [home] (.getElementById js/document "app")))

(defn ^:export ^:dev/once init! []
      (mount-root))
