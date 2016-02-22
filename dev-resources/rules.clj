{:rules [
         {:name :side-effectful
          :expr (fn [] (println "in a rule"))}
         {:name :side-effectful
          :expr (fn [] (println "in a rule, v2"))}
         {:name :side-effectful
          :expr (fn [] (println "in a rule, v3"))}
         ]
 :selector (fn [fs]
             (list (first fs)))}