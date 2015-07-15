# ring-sentry-component

Ring component for the Sentry exception handler. Provides middleware for wrapping routes. Intended to be used alongside [duct](https://github.com/weavejester/duct) and its endpoint components.

Uses [raven-clj](https://github.com/sethtrain/raven-clj) under the hood.

## Installation

```clojure
[ring-sentry-component "0.2.1"]
```

## Usage

```clojure

(require '[ring.component.sentry :refer [sentry-component protect]])

;; Wrap the routes in your endpoint in the Sentry
(defn my-endpoint [{:keys [exceptions]}]
  (protect
    exceptions
    (compojure/routes
      (GET ...))))

;; Create your system map
(-> (c/system-map
      :exceptions (sentry-component {:dsn "my-dsn"})
      :endpoint (endpoint-component my-endpoint)
      (c/system-using
        {:endpoint [:exceptions]
         :exceptions []})))
```

There is also a `SentryStub` component:

```clojure
(sentry-stub {})
;; => #ring.component.sentry.SentryStub{:dsn nil}
```

Calling `protect` while passing the stubbed component will print stdout using `clojure.tools.logging`:

```clojure
(protect stub handler)
;; => SEVERE: Mocking exception to Sentry
```

## License

Copyright © 2015 Banzai Inc.

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
