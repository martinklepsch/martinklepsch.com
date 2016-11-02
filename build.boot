(set-env!
 :source-paths    #{"src" "stylesheets" "content"}
 :resource-paths  #{"resources"}
 :dependencies '[[org.clojure/clojurescript "1.9.293" :scope "test"]
                 [pandeiro/boot-http  "0.7.3" :scope "test"]
                 [adzerk/boot-cljs "1.7.228-2" :scope "test"]
                 [adzerk/boot-reload "0.4.13" :scope "test"]
                 [deraen/boot-sass    "0.2.1" :scope "test"]
                 [org.slf4j/slf4j-nop "1.7.21" :scope "test"]
                 [confetti/confetti   "0.1.2-SNAPSHOT" :scope "test"]
                 [perun               "0.4.0-SNAPSHOT" :scope "test"]
                 [boot "2.6.0"]
                 [hiccup              "1.0.5"]
                 [enlive "1.1.6"]])

(require '[pandeiro.boot-http :refer [serve]]
         '[adzerk.boot-reload  :refer [reload]]
         '[adzerk.boot-cljs  :refer [cljs]]
         '[deraen.boot-sass :refer [sass]]
         '[confetti.boot-confetti :refer [sync-bucket create-site]]
         '[boot.util           :as util]
         '[clojure.string      :as string]
         '[io.perun            :as p]
         '[io.perun.core       :as perun]
         '[com.martinklepsch.inliner :refer [inline]])

(deftask build
  "Build blog"
  []
  (comp (sass)
        (sift :move {#"martinklepsch-com.css" "public/martinklepsch-com.css"})
        (cljs)
        (p/base)
        (p/global-metadata)
        (p/slug)
        (p/collection :renderer 'com.martinklepsch.site/index-page
                      :page "index.html"
                      :filterer identity)))

(deftask dev
  []
  (comp (serve :resource-root "public"
               :port 4000)
        (watch)
        (reload :asset-path "public"
                :on-jsload 'com.martinklepsch.dyn/run)
        (build)))

(def confetti-edn
  (read-string (slurp "martinklepsch-com.confetti.edn")))

(deftask prod []
  (task-options! cljs {:optimizations :advanced}
                 sass {:output-style :compressed}
                 inline {:mapping  {"/app.js" "public/app.js"
                                    "/martinklepsch-com.css" "public/martinklepsch-com.css"} 
                         :files #{#"index.html"}}))

(deftask deploy []
  (comp
   (build)
   (inline)
   (sift :move {#"^public/" ""})
   (sift :include #{;#"martinklepsch-com.css$"
                    #"index.html$"
                    ;#"app.js$"
                    ;#"robots.txt$"
                    })
   (sync-bucket :bucket (:bucket-name confetti-edn)
                :prune true
                :cloudfront-id (:cloudfront-id confetti-edn)
                :access-key (:access-key confetti-edn)
                :secret-key (:secret-key confetti-edn))))