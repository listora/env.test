(ns environ-test.core
  "REPL functions for testing environ based projects."
  (:require [clojure.java.io :as io]
            [clojure.test :refer [use-fixtures]]
            [environ.core :refer [env]]
            [leiningen.core.project :as project]))

(defn read-env
  "Read the environ map from the Leiningen `profile`."
  [profile]
  (merge
   (let [file (io/file (System/getenv "HOME") ".lein" "profiles.clj")]
     (if (.exists file)
       (:env (profile (read-string  (slurp (str file)))))))
   (:env (project/read "project.clj" [profile]))))

(defmacro with-env
  "Evaluate `body` with `environ.core/env` rebound to the map found
  under the :env key in the Leiningen `profile`."
  [profile & body]
  `(with-redefs [env (read-env ~profile)]
     ~@body))

(defn wrap-env
  "Call the function `f` with `environ.core/env` rebound to the map
  found under the :env key in the Leiningen `profile`. When `profile`
  is not given it defaults to :test. This function can be used with
  `clojure.test/use-fixtures`."
  [f & [profile]]
  (with-env (or profile :test)
    (f)))

(defn use-env
  "Wrap test runs in a fixture function that rebinds
  `environ.core/env` to the map found under the :env key in the
  Leiningen `profile`."
  [& [profile]]
  (use-fixtures :each wrap-env (or profile :test)))
