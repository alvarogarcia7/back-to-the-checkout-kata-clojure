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
      :else (let [rules-path (get-in options [:options :rules])
                  rules-content (slurp rules-path)
                  config (eval (read-string rules-content))

                  find-by-name #(= (:name %) :side-effectful)
                  rules (:rules config)
                  selector (:selector config)
                  matching-rules (filter find-by-name rules)
                  selected-rules (selector matching-rules)
                  rule-code (map :expr selected-rules)]
              ;(println options)
              (println "doing stuff")
              (execute rule-code)))))
