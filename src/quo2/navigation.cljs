(ns quo2.navigation
  (:require [status-im.utils.fx :as fx]))

(fx/defn quo2-init-root
  {:events [::quo2-init-root]}
  [_ root-id]
  {:quo2-init-root-fx root-id})

(fx/defn quo2-change-tab
  {:events [::quo2-navigate-change-tab]}
  [_ tab]
  {:quo2-change-tab-fx tab})

(fx/defn quo2-open-modal
  {:events [::quo2-open-modal]}
  [_ modal]
  {:quo2-open-modal-fx modal})

(fx/defn quo2-close-modal
  {:events [::quo2-close-modal]}
  [_ modal]
  {:quo2-close-modal-fx modal})
