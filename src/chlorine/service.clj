(ns chlorine.service
  (:refer-clojure :exclude [compile])
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [compojure.core :refer [defroutes routes]]
            [compojure.route :refer [not-found resources]]
            [noir.cookies :refer [wrap-noir-cookies*]]
            [noir.util.middleware :refer [wrap-strip-trailing-slash]]
            [compojure.handler :refer [site]]
            [chlorine.service.compile :refer [compile]]))

(def handler
  (site (-> (routes compile
                    (resources "/")
                    (not-found "404 - Not found"))
            (wrap-noir-cookies*))))
(defn -main []
  (let [port (Integer/parseInt (get (System/getenv) "PORT" "8080"))]
    (run-jetty handler {:port port})))
