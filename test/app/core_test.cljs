(ns app.core-test
  (:require [cljs.test :refer-macros [deftest is testing run-tests]]
            [app.core :as a]))

(enable-console-print!)

(deftest test-convert-temp []
  (is (= -22 (a/convert-temp -30 :celcius :fahrenheit)))
  (is (= 32 (a/convert-temp 0 :celcius :fahrenheit)))
  (is (= 104 (a/convert-temp 40 :celcius :fahrenheit)))
  (is (= 212 (a/convert-temp 100 :celcius :fahrenheit)))
  (is (= -30 (a/convert-temp -22 :fahrenheit :celcius)))
  (is (= 0 (a/convert-temp 32 :fahrenheit :celcius)))
  (is (= 40 (a/convert-temp 104 :fahrenheit :celcius)))
  (is (= 100 (a/convert-temp 212 :fahrenheit :celcius))))

(run-tests)