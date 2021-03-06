# env.test

[![Build Status](https://travis-ci.org/listora/env.test.svg?branch=master)](https://travis-ci.org/listora/env.test)

[Environ](https://github.com/weavejester/environ) is a great library
when developing applications that follow the
[12 Factor App](http://12factor.net) pattern and
[store configuration in the environment](http://12factor.net/config).

When running the tests of your application via `lein test`, Leiningen
will merge in the :`test` profile, and Environ will pick up the
configuration from that profile. However, this is not happening if you
run the tests from the REPL. The REPL is started with the `:dev`
profile and Environ will read the configuration from that profile,
even when running tests. This library tries to solve this.

## Installation

To install via Leiningen add the following dependency to your `project.clj`:

``` clj
[listora/env.test "0.1.1"]
```

## Usage

You can either use the `with-env` macro to evaluate the body of the
macro with the Environ configuration of the given profile.

``` clj
(require '[environ.core :refer [env]]
         '[env.test :refer [with-env]]

(with-env :test
  (is (= "amqp://localhost:5672/test" (:rabbitmq-uri env))))
```

Or use the `wrap-env` function together with
[use-fixtures](http://richhickey.github.io/clojure/clojure.test-api.html#clojure.test/use-fixtures)
to make the configuration for the given profile available to the whole
namespace. If this is your only fixture you can also use the `use-env`
function, which does the same.

``` clj
(require '[clojure.test :refer [use-fixtures]]
         '[environ.core :refer [env]]
         '[env.test :refer [wrap-env]]

(use-fixtures :each #(wrap-env %1 :test))
```

## License

Copyright © 2014 Listora

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
