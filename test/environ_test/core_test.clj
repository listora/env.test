(ns environ-test.core-test
  (:require [clojure.test :refer :all]
            [environ.core :refer [env]]
            [environ-test.core :refer :all]))

(use-fixtures :each wrap-env :test )

(deftest test-read-env
  (is (= "amqp://localhost:5672/test" (:rabbitmq-uri env)))
  (let [env (read-env :dev)]
    (is (= "amqp://localhost:5672/dev" (:rabbitmq-uri env))))
  (let [env (read-env :test)]
    (is (= "amqp://localhost:5672/test" (:rabbitmq-uri env)))))

(deftest test-with-env
  (with-env :dev
    (is (= "amqp://localhost:5672/dev" (:rabbitmq-uri env))))
  (with-env :test
    (is (= "amqp://localhost:5672/test" (:rabbitmq-uri env)))))

(deftest test-wrap-env
  (wrap-env #(is (= "amqp://localhost:5672/test" (:rabbitmq-uri env)))))
