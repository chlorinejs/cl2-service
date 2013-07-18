(defproject cl2-service "0.6.0"
  :description "Chlorine compiling service."
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [lib-noir "0.6.1"]
                 [chlorine "1.6.2"]
                 [core-cl2 "0.8.0"]]
  :resource-paths ["node_modules"]
  :plugins [[lein-ring "0.8.5"]]
  :ring {:handler cl2-service.server/handler
         :auto-reload? false}
  :aot :all
  :main cl2-service.server)
