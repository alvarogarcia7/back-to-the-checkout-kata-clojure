(ns kata.checkout.core
  (:require [clojure.tools.cli :refer [parse-opts]])
  (use clojure.java.io)
  (:gen-class))

; guide for cli-options: https://github.com/clojure/tools.cli/blob/master/src/test/clojure/clojure/tools/cli_test.clj
(def cli-options
  [["-r" "--rules FILE" "where to find the rules"]
   ["-h" "--help"]])

(defn execute
  [rules]
  (loop [rule (first rules) rest-of-rules (rest rules)]
    (rule)
    (if (empty? rest-of-rules)
      '()
      (recur (first rest-of-rules) (rest rest-of-rules)))))

(defn- config
  [options]
  (->> [:options :rules]
       (get-in options)
       slurp
       read-string
       eval))

(defn- rules-matching
  [matching config]
  (let [selector (:selector config)]
    (->> config
         :rules
         (filter matching)
         selector
         (map :expr))))

(defn -main
  [& args]
  (let [options (parse-opts args cli-options :strict true :missing true)
        errors? #(not (empty? (:errors %)))
        help? #(:help %)
        print-help #(do (println "usage:") (println (:summary %)))]
    ; (println options)
    (cond
      (errors? options) (do
                          (print-help options)
                          (println (:errors options)))
      (help? options) (print-help options)
      :else (let [config (config options)
                  with-name #(= (:name %) :side-effectful)
                  matching-rules (rules-matching with-name config)]
              ;(println options)
              (println "doing stuff")
              (execute matching-rules)))))
