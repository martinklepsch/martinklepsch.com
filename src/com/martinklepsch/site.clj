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

(defn how-i-can-help []
  [:div.mt5
   [:p "—" [:br] "How I would love to help your organization:"]

   [:h3.mt5 "Development Tooling"]
   [:p "I enjoy working on the tools we use to build software. I've contributed to build tools,
   created a documentation platform and love to optimize feedback cycles for more fun and less
   frustration. Make your development teams happy about their workflows!"]

   [:h3.mt5 "Mentoring & Training"]
   [:p "Many organizations are reluctant to hire junior developers because they feel that they
   don't have the bandwidth needed to get them up to speed. I would love to support companies
   who otherwise wouldn't hire junior developers with candidate sourcing, onboarding and
   training."]

   [:h3.mt5 "Code & Architecture Reviews"]
   [:p "Having worked as an independent consultant over the past five years I've seen a lot of
   code and had a chance to learn from many different people and environments. This diverse experience means I
   have a good overview about what different approaches are available as well as common pitfalls."]

   [:h3.mt5 "Open Source Liason"]
   [:p "Open Source code provides significant value to most software businesses and I'd like to
   help companies build better connections to their relevant open source communities. Making
   open source more sustainable and reliable in the long run."]

   ;; code and architecture reviews
   ;; tooling support
   ;; mentoring of junior developers
   ])

(defn index-page [{:keys [entries]}]
  (let [[curr-page no-of-pages] (:page (first entries))]
    (base
     {}
     [:div.mb5.mh3.mh4-ns
      [:div.mb5
       [:h1.mt5 "Martin Klepsch"]
       [:span "People × Technology"]]
      [:div.mw6.lh-copy
       [:p "I'm a software consultant with a focus on user interfaces and development
       tooling. This interest got me into Clojure and ClojureScript and these two eventually
       became my favorite tools for most of my work."]
       [:p "Lately I've been working remotely with various companies using Clojure/Script
       shipping features, taming codebases and improving their Clojure practices."]
       [:p "If you're looking for some help with your project, just " [:a.lined {:href
        "mailto:martinklepsch@googlemail.com"} "shoot me an email"] ". While I'm most
        experienced with Clojure different language choices can also be interesting."]
       [:p "I'm also interested in the challenges of building up product teams (especially
       distributed ones) and developing workflows that are both flexible for team members and
       efficient in time and quality."]
       [:p "I'm currently in timezone UTC"
        (cond
          (pos? my-timezone/utc-offset) (str "+" my-timezone/utc-offset)
          (neg? my-timezone/utc-offset) (str my-timezone/utc-offset)) ". "
        [:span {:id "utc-offset"}]]
       [:p "You can also follow what I'm up to on "
        [:a.lined {:href "https://twitter.com/martinklepsch"}
         "Twitter"]
        " & "
        [:a.lined {:href "https://github.com/martinklepsch"}
         "GitHub"]
        "."]

       (how-i-can-help)]])))
