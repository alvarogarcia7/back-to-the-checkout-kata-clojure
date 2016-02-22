(ns kata.checkout.core
  (:require [clojure.tools.cli :refer [parse-opts]])
  (use clojure.java.io)
  (:gen-class))

; guide for cli-options: https://github.com/clojure/tools.cli/blob/master/src/test/clojure/clojure/tools/cli_test.clj
(def cli-options
  [["-r" "--rules FILE" "where to find the rules"]
   ["-h" "--help"]])

(defn execute
  [& rule-code]
  (map #(%) rule-code))

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
                  ;select (:applier config)
                  matching-rules (filter find-by-name rules)
                  rule-code (map :expr matching-rules)]
              ;(println options)
              (println "doing stuff")
              (println matching-rules)
              (println rule-code)
              ((first rule-code))
              ((second rule-code))
              ))))
