(ns cl2-service.views.compile
  (:use [compojure.core :refer [defroutes GET context]]
        [noir.response :only [redirect content-type]]
        [pathetic.core :only [url-normalize]]
        [chlorine.js])
  (:require [noir.cookies :as cookies])
  (:import java.util.Date))

(def angular {:reserved-symbols
             [#"^\$.*"
              #"^\.\$.*"]
             :symbol-map
             (array-map
              #"^int$" "int*"
              "$"  "$USD$"
              "->" "$ARROW$"
              "=>" "$BARROW$"
              "<-" "$LARROW$"
              ">"  "$GT$"
              "<"  "$LT$"
              ">=" "$GE$"
              "=<" "$LE$"
              "-"  "_"
              "'"   "$QUOT$"
              "!"  "$EXCL$"
              "?"  "$QUEST$"
              "#"  "$HASH$"
              "%"  "$P100$"
              "&"  "$AND$"
              "*"  "$STAR$"
              "+"  "$PLUS$"
              "="  "$EQ$"
              "|"  "$PIPE$"
              )})

(def profiles {"angular" angular})

(defn now "Gets current time in miliseconds"
  [] (.getTime (Date.)))

(def ^{:doc "Pre-compiles Chlorine `dev` environment once
and saves states to this var."}
  preloaded
  (binding [*temp-sym-count* (ref 999)
            *last-sexpr*     (ref nil)
            *macros*         (ref {})
            *print-pretty*   true]
    (let [core-js (with-profile angular
                    (js (include! [:private "dev.cl2"])))]
      {:temp-sym-count @*temp-sym-count*
       :macros @*macros*
       :core-js core-js})))

(defn new-session
  "Prepares starting vars for a new session"
  []
  {:temp-sym-count (ref (:temp-sym-count preloaded))
   :macros         (ref (:macros         preloaded))
   :last-sexpr (ref nil)
   })

(defroutes compiling
  (GET "/service/:filename" [filename :as request]
       (when-let [referer (get-in  request [:headers "referer"])]
         (let [session (new-session)]
           (with-profile angular
             (binding [*temp-sym-count* (:temp-sym-count session)
                       *last-sexpr*      (:last-sexpr session)
                       *macros*          (:macros session)
                       *print-pretty*   true]
               (str "console.log('Compiled at: ', " (now) ");\n"
                    (tojs (url-normalize (str referer filename)))))))))
  (GET "/core-cl2.js" []
       (:core-js preloaded))
  )
