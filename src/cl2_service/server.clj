(ns cl2-service.server
  (:use [ring.adapter.jetty :only [run-jetty]]
        [compojure.core :only [defroutes routes]]
        [compojure.route :only [not-found resources]]
        [noir.cookies :only [wrap-noir-cookies*]]
        [noir.util.middleware :only [wrap-strip-trailing-slash]]
        [compojure.handler :only [site]]
        [cl2-service.views.compile :only [compile]]))

(def handler
  (site (-> (routes compile
                    (resources "/")
                    (not-found "404 - Not found"))
            (wrap-noir-cookies*))))
(defn -main []
  (let [port (Integer/parseInt (get (System/getenv) "PORT" "8080"))]
    (run-jetty handler {:port port})))
