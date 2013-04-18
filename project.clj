(defproject cl2-service "0.4.0"
  :description "Chlorine compiling service."
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [lib-noir "0.3.5"]
                 [chlorine "1.5.3"]
                 [core-cl2 "0.7.1"]]
  :plugins [[lein-ring "0.8.2"]]
  :ring {:handler cl2-service.server/handler
         :auto-reload? false}
  :aot :all
  :main cl2-service.server)
