(ns kata.checkout.core-test
  (:use midje.sweet))

(facts "canary tests"
  (fact "truthiness"
    true => true)
  
  (fact "falsiness"
  false => false))