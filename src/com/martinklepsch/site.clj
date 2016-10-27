(ns com.martinklepsch.site
  (:require [hiccup.page :as hp])
  (:import  java.text.SimpleDateFormat))

(defn trace [x]
  (prn x)
  x)

(def +twitter-uri+ "https://twitter.com/martinklepsch")

(defn base
  [opts & content]
  (hp/html5 {:lang "en"}
            [:head
             [:title (:title opts)]]
            [:body.system-sans-serif.dark-gray
             (into [:div.mh3] content)]))


(defn index-page [{:keys [entries]}]
  (let [[curr-page no-of-pages] (:page (first entries))]
    (base
     {}
     [:div
      [:h1 "Martin Klepsch"]
      [:span "Clojure/Script Contractor"]])))
