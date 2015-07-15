(ns ring.component.sentry
  (:require [com.stuartsierra.component :as c]
            [raven-clj.ring :refer [wrap-sentry]]))

(defrecord Sentry [dsn]
  c/Lifecycle
  (start [this] this)
  (stop [this] this))

(defn sentry-component
  "Creates a reloadable Sentry component for use within duct endpoints.
  
  Available options:
    dsn - Sentry dsn including username, password, and url to your Sentry instance."
  [config]
  (map->Sentry config))

(defn protect
  "Protect a route, or group of routes (wrap a handler),
  for exception handling."
  [handler dsn & [opts]]
  (wrap-sentry handler dsn opts))
