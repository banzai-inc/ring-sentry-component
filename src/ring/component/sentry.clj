(ns ring.component.sentry
  (:require [com.stuartsierra.component :as c]
            [raven-clj.ring :refer [wrap-sentry]]
            [clojure.tools.logging :refer [error]]))

(defprotocol Protect
  (protect* [sentry handler opts]))

(defrecord Sentry [dsn]
  c/Lifecycle
  (start [this] this)
  (stop [this] this)

  Protect
  (protect* [this handler opts]
    (wrap-sentry handler (:dsn this) opts)))

(defrecord SentryStub []
  c/Lifecycle
  (start [this] this)
  (stop [this] this)

  Protect
  (protect* [_ handler _]
    (try
      (fn [req] (handler req))
      (catch Exception e
        (error "Mocking exception to Sentry")
        (throw e)))))

(defn sentry-component
  "Creates a reloadable Sentry component for use within duct endpoints.
  
  Available options:
    dsn - Sentry dsn including username, password, and url to your Sentry instance."
  [config]
  (map->Sentry config))

(defn sentry-stub
  "Creates a SentryStub component."
  [_]
  (map->SentryStub {}))

(defn protect
  "Protect a route, a group of routes (wrap a handler), for exception handling.
  `sentry` argument is the component from the system."
  [handler sentry & [opts]]
  (protect* sentry handler opts))
