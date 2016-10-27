(set-env!
 :source-paths    #{"src" "stylesheets" "content"}
 :resource-paths  #{"resources"}
 :dependencies '[[pandeiro/boot-http  "0.7.3" :scope "test"]
                 [adzerk/boot-reload  "0.4.12" :scope "test"]
                 [deraen/boot-sass    "0.2.1" :scope "test"]
                 [org.slf4j/slf4j-nop "1.7.21" :scope "test"]
                 [confetti/confetti   "0.1.2-SNAPSHOT" :scope "test"]
                 [perun               "0.4.0-SNAPSHOT" :scope "test"]
                 [hiccup              "1.0.5"]])

(require '[pandeiro.boot-http :refer [serve]]
         ;; '[mathias.boot-sassc  :refer [sass]]
         '[deraen.boot-sass :refer [sass]]
         '[confetti.boot-confetti :refer [sync-bucket create-site]]
         '[boot.util           :as util]
         '[clojure.string      :as string]
         '[io.perun            :as p]
         '[io.perun.core       :as perun])

(deftask build
  "Build blog"
  []
  (comp (sass)
        (p/base)
        (p/global-metadata)
        ; (p/markdown)
        (p/slug)
        (p/collection :renderer 'com.martinklepsch.site/index-page
                      :page "index.html"
                      :filterer identity)))

(deftask dev
  []
  (comp (serve :resource-root "public"
               :port 4000)
        (watch)
        (build)))

(def confetti-edn
  (read-string (slurp "martinklepsch-com.confetti.edn")))

(deftask deploy []
  (comp
   (build)
   (sift :include #{#"^public/"})
   (sift :move {#"^public/" ""})
   (sync-bucket :bucket (:bucket-name confetti-edn)
                :prune true
                :cloudfront-id (:cloudfront-id confetti-edn)
                :access-key (:access-key confetti-edn)
                :secret-key (:secret-key confetti-edn))))