(defproject checkout-kata "0.0.1-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.cli "0.3.3"]]
  :main kata.checkout.core
  :aot [kata.checkout.core]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
                :dev {:plugins [[lein-midje "3.1.3"]]
                        :dependencies [[midje "1.6.3"]]}}
  )