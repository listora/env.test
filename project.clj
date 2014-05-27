(defproject listora/env.test "0.1.1-SNAPSHOT"
  :description "REPL functions for testing environ based projects."
  :url "https://github.com/listora/env.test"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[environ "0.5.0"]
                 [leiningen-core "2.3.4"]
                 [org.clojure/clojure "1.6.0"]]
  :profiles {:dev {:env {:rabbitmq-uri "amqp://localhost:5672/dev"}
                   :plugins [[jonase/eastwood "0.1.2"]
                             [lein-environ "0.5.0"]
                             [lein-difftest "2.0.0"]]}
             :test {:env {:rabbitmq-uri "amqp://localhost:5672/test"}}}
  :aliases {"ci" ["do" ["difftest"] ["lint"]]
            "lint" ["do" ["eastwood"]]})
