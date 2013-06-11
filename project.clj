(defproject cl2-service "0.5.1"
  :description "Chlorine compiling service."
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [lib-noir "0.6.1"]
                 [chlorine "1.5.4"]
                 [core-cl2 "0.7.3"]]
  :resource-paths ["node_modules"]
  :plugins [[lein-ring "0.8.5"]]
  :ring {:handler cl2-service.server/handler
         :auto-reload? false}
  :aot :all
  :main cl2-service.server)
