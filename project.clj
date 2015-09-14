(defproject ring-sentry-component "0.3.0"
  :description "Ring component for Sentry following the Reloadable pattern"
  :url "https://github.com/banzai-inc/sentry-component"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [com.stuartsierra/component "0.2.3"]
                 [org.clojure/tools.logging "0.3.1"]
                 [raven-clj "1.3.1"]])
