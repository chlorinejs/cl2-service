(ns cl2-service.views.compile
  (:refer-clojure :exclude [compile])
  (:require [compojure.core :refer [defroutes GET context]]
            [noir.response :refer [redirect content-type]]
            [pathetic.core :refer [url-normalize]]
            [chlorine.util :refer [with-timeout]]
            [slingshot.slingshot :refer :all]
            [chlorine.prelude
             :refer [prelude now exception->msg
                     exception+->msg msg->alert]]
            [chlorine.js :refer :all]))

(defn new-session
  "Prepares starting vars for a new session"
  [strategy]
  {:temp-sym-count (ref (get-in prelude [strategy :temp-sym-count]))
   :macros         (ref (get-in prelude [strategy :macros]))})

(defroutes compile
  (GET "/:strategy/prelude.js" [strategy]
       (get-in prelude [strategy :inclusion]))
  (GET "/:strategy/:filename" [strategy filename :as request]
       (when-let [referer (get-in  request [:headers "referer"])]
         (let [session (new-session strategy)
               angular (get-in  request [:query-params "angular"])]
           (binding [*temp-sym-count* (:temp-sym-core-jscount session)
                     *macros*         (:macros session)
                     *print-pretty*   true]
             (str "console.log('Compiled at: ', " (now) ");\n"
                  (when angular (tojs' "r:/angular-cl2/lib/angular.cl2"))
                  (try+
                   (with-timeout 5000
                     (tojs' (url-normalize (str referer filename))))
                   (catch map? e
                     (->> (with-out-str
                            (println "Compilation Error: ")
                            (println (:msg e))
                            (doseq [i (range (count (:causes e)))
                                    :let [cause (nth (:causes e) i)]]
                              (print (apply str (repeat (inc i) "  ")))
                              (println "caused by " cause))
                            (when-let [trace (:trace e)]
                              (print-cause-trace trace 3)))
                          (pr-str)
                          (format "alert(%s)")))
                   (catch java.util.concurrent.TimeoutException e
                     (->> (str
                           "Error: Timeout compiling "
                           filename)
                          (pr-str)
                          (format "alert(%s)" )))
                   (catch Throwable e
                     (->> (with-out-str (print-cause-trace e 3))
                          (pr-str)
                          (format "alert(%s)"))))))))))
