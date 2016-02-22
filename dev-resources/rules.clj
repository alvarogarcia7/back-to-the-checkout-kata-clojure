{:rules [
         {:name :side-effectful
          :expr (fn [] (println "in a rule"))}
         {:name :side-effectful
          :expr (fn [] (println "in a rule, v2"))}
         ]
 :applier (fn [& fs]
            (list (first fs)))}