(defproject cl2-service "0.6.0-SNAPSHOT"
  :description "Chlorine compiling service."
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [lib-noir "0.6.1"]
                 [chlorine/prelude "0.1.0-SNAPSHOT"]]
  :plugins [[lein-ring "0.8.5"]]
  :ring {:handler cl2-service.server/handler
         :auto-reload? false}
  :aot :all
  :main cl2-service.server)
