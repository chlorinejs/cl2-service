(defproject chlorine/service "0.6.0-SNAPSHOT"
  :description "Chlorine compiling service."
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [lib-noir "0.6.1"]
                 [chlorine/prelude "0.1.0-SNAPSHOT"]]
  :plugins [[lein-ring "0.8.5"]]
  :ring {:handler chlorine.service/handler
         :auto-reload? false}
  :aot :all
  :main chlorine.service)
