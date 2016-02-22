(ns kata.checkout.core
  (:require [clojure.tools.cli :refer [parse-opts]])
  (use clojure.java.io)
  (:gen-class))

; guide for cli-options: https://github.com/clojure/tools.cli/blob/master/src/test/clojure/clojure/tools/cli_test.clj
(def cli-options
  [["-r" "--rules FILE" "where to find the rules"]
   ["-h" "--help"]])

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
      :else (do
              (println "doing stuff")
              ;(println options)
              (let [rules-path (get-in options [:options :rules])
                    rules-content (slurp rules-path)
                    rules (eval (read-string rules-content))]
                (println rules)
                ((:expr (first (filter #(= (:name %) :side-effectful) rules)))))))))