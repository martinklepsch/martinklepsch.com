(ns com.martinklepsch.site
  (:require [com.martinklepsch.timezone :as my-timezone]
            [clojure.java.io :as io]
            [hiccup.page :as hp])
  (:import  java.text.SimpleDateFormat))

(def +twitter-uri+ "https://twitter.com/martinklepsch")

(defn head
  [{:keys [title] :as opts}]
  [:head
   [:meta {:charset "utf-8"}]
   [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
   [:meta {:name "viewport" :content "width=device-width, initial-scale=1, maximum-scale=1"}]
   [:meta {:itemprop "author" :name "author" :content "Martin Klepsch (martinklepsch@googlemail.com)"}]
   [:meta {:name "description" :itemprop "description" :content "Martin Klepsch"}]
   [:title "Martin Klepsch"]
   
   [:link {:rel "shortcut icon" :href "images/favicon.ico"}]
   [:link {:rel "author" :href "humans.txt"}]
   [:link {:type "text/css" :rel "stylesheet"
           :href "/martinklepsch-com.css"}]])

(defn base
  [opts & content]
  (hp/html5 {:lang "en"}
            (head opts)
            [:body.system-sans-serif.dark-gray
             (into [:div] content)
             [:script {:src "/app.js"}]]))

(defn index-page [{:keys [entries]}]
  (let [[curr-page no-of-pages] (:page (first entries))]
    (base
     {}
     [:div.mb5.mh3.mh4-ns
      [:div.mb5
       [:h1.mt5 "Martin Klepsch"]
       [:span "People × Technology"]]
      [:div.mw6.lh-copy
       [:p "I'm a software consultant with a focus on the development of user interfaces. This interest got me into Clojure and ClojureScript and these two eventually became my favorite tools for most of the work I do."]
       [:p "Lately I've been working remotely with various companies using Clojure and ClojureScript shipping features and helping them to develop a stable and maintainable codebase."]
       [:p "If you're looking for some help with your Clojure or ClojureScript project, just "
        [:a.lined {:href "mailto:martinklepsch@googlemail.com"} "shoot me an email"] "."]
       [:p "I'm also interested in the challenges of building up product teams (especially distributed ones) and developing workflows that are both flexible for team members and efficient in time and quality."]
       [:p "I'm currently in timezone UTC"
        (cond
          (pos? my-timezone/utc-offset) (str "+" my-timezone/utc-offset)
          (neg? my-timezone/utc-offset) (str my-timezone/utc-offset)) ". "
        [:span {:id "utc-offset"}]]
       [:p "You can also follow what I'm up to on "
        [:a.lined {:href "https://twitter.com/martinklepsch"}
         "Twitter."]]]])))
