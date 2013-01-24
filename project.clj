(defproject cl2-service "0.2.0-SNAPSHOT"
  :description "Chlorine compiling service."
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [lib-noir "0.3.5"]
                 [chlorine "1.7.0-SNAPSHOT"]
                 [core-cl2 "1.0.0-SNAPSHOT"]]
  :plugins [[lein-ring "0.8.2"]]
  :ring {:handler cl2-service.server/handler
         :auto-reload? false}
  :aot :all
  :main cl2-service.server)
