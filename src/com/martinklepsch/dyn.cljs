(ns com.martinklepsch.dyn
  (:require [com.martinklepsch.timezone :as my-timezone]
            [goog.i18n.TimeZone :as tz]
            [goog.string :as gstring]
            [goog.style :as gstyle]
            [goog.dom :as gdom])
  (:import [goog.i18n TimeZone]
           [goog.date DateTime]))

(defn inject-timezone-offset-information! []
  (js/console.log "Adding Timezone Information...")
  (let [tz     (tz/createTimeZone (* my-timezone/utc-offset 60))
        offset (+ (/ (.getOffset tz (js/Date.)) 60)
                  (/ (.getTimezoneOffset (DateTime.)) 60))
        el     (gdom/getElement "utc-offset")
        absolute-offset (js/Math.abs offset)
        txt (case offset
              0  "Which is the same as yours."
              1  "Which is an hour ahead of you."
              -1 "Which is an hour behind you."

              (2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23)
              (gstring/buildString "Which is " absolute-offset " hours ahead of you.")

              (-2 -3 -4 -5 -6 -7 -8 -9 -10 -11 -12 -13 -14 -15 -16 -17 -18 -19 -20 -21 -22 -23)
              (gstring/buildString "Which is " absolute-offset " hours behind you.")
              nil)]
    (gdom/setTextContent el txt)
    (gstyle/setStyle el #js {:color "black"})))

(defn run []
  (js/window.setTimeout inject-timezone-offset-information! 1000))

