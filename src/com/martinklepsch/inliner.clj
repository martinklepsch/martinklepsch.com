(ns com.martinklepsch.inliner
  (:require [net.cgrand.enlive-html :as enl]
            [clojure.java.io :as io]
            [boot.core :as boot]
            [boot.util :as util]))

(defn inline' [file fs mapping]
  (enl/at
   (enl/html-resource (boot/tmp-file file))
   [:script] (fn [n]
               (let [src (get-in n [:attrs :src])
                     tgt (get mapping src)]
                 (if-let [fs-file (get-in fs [:tree tgt])]
                   (if fs-file {:tag :script :content [(slurp (boot/tmp-file fs-file))]} n)
                   (when tgt (util/warn "Mapping target not found in Fileset: %s\n" tgt)))))
   [:link] (fn [n]
             (let [src (get-in n [:attrs :href])
                   tgt (get mapping src)]
               (if-let [fs-file (get-in fs [:tree tgt])]
                 (if fs-file {:tag :style :content [(slurp (boot/tmp-file fs-file))]} n)
                 (when tgt (util/warn "Mapping target not found in Fileset: %s\n" tgt)))))))

(boot/deftask inline
  [f files   FILES   #{regex}  "Files to update"
   m mapping MAPPING {str str} "Mapping of src/href values and their respective fileset paths in the fileset"]
  (boot/with-pre-wrap fs
    (let [tmp (boot/tmp-dir!)]
      (util/info "Inlining scripts and stylesheets...\n")
      (doseq [file (boot/by-re files (boot/output-files fs))]
        (util/info "• %s\n" (:path file))
        (let [transformed (inline' file fs mapping)
              new-file    (io/file tmp (:path file))]
          (io/make-parents new-file)
          (spit new-file (apply str (enl/emit* transformed)))))
      (-> fs (boot/add-resource tmp) boot/commit!))))