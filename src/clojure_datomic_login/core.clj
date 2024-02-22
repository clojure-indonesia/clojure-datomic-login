(ns clojure-datomic-login.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [clojure.data.json :refer [read-str]]
            [datomic.client.api :as d])
  (:import [org.mindrot.jbcrypt BCrypt])
  (:gen-class))

(defn app
  [request]
  (let [body (-> request :body slurp)
        json (read-str body)
        conn (d/connect (d/client {:server-type :datomic-local
                                   :system "db"})
                        {:db-name "login"})
        db (d/db conn)
        email (json "email")
        plain (json "password")
        hashp (ffirst (d/q '[:find ?password
                             :in $ ?email
                             :where [?e :auth/email ?email]
                             [?e :auth/password ?password]]
                           db
                           email))]
    {:status 200
     :body (if (BCrypt/checkpw plain hashp)
             "login berhasil!\n"
             "login gagal!\n")}))

(defn -main
  [& [port]]
  (let [port (or port (get (System/getenv) "PORT" 8080))
        port (cond-> port (string? port) Integer/parseInt)]
    (run-jetty app {:port port
                    :join? false})))
