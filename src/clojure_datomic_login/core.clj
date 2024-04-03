(ns clojure-datomic-login.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.json :refer [wrap-json-params]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [datomic.client.api :as d])
  (:import [org.mindrot.jbcrypt BCrypt])
  (:gen-class))

(defn handler
  [request]
  (let [params (:params request)
        email (:email params)
        plain (:password params)
        conn (d/connect (d/client {:server-type :datomic-local
                                   :system "db"})
                        {:db-name "login"})
        db (d/db conn)
        hashp (-> (d/q '[:find ?password
                         :in $ ?email
                         :where [?e :auth/email ?email]
                         [?e :auth/password ?password]]
                       db
                       email)
                  ffirst)]
    {:status 200
     :body (if (BCrypt/checkpw plain hashp)
             "login berhasil!\n"
             "login gagal!\n")}))

(def app (-> handler
             wrap-keyword-params
             wrap-json-params))

(defn -main
  [& [port]]
  (let [port (or port (get (System/getenv) "PORT" 8080))
        port (cond-> port (string? port) Integer/parseInt)]
    (run-jetty app {:port port
                    :join? false})))
