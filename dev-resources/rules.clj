{:rules [
         {:name :side-effectful
          :expr (fn [] (println "in a rule"))}
         {:name :side-effectful
          :expr (fn [] (println "in a rule, v2"))}
         {:name :side-effectful
          :expr (fn [] (println "in a rule, v3"))}
         ]
 :applier (fn [& fs]
            (list (first fs)))}