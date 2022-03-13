(ns quo2.navigation.core
  (:require [re-frame.core :as re-frame]
            [quo2.navigation.roots :as roots]
            ["react-native-navigation" :refer (Navigation)]))

(re-frame/reg-fx
 :quo2-init-root-fx
 (fn [new-root-id]
   (.setRoot Navigation (clj->js (get (roots/roots) new-root-id)))))

(def tab-key-idx {:quo2-preview      0
                  :browser 1
                  :wallet 2
                  :profile 3})

(re-frame/reg-fx
 :quo2-change-tab-fx
 (fn [tab]
   (.mergeOptions Navigation "quo2-tabs-stack" (clj->js {:bottomTabs {:currentTabIndex (get tab-key-idx tab)}}))))

(defn open-modal [comp]
  (.showModal Navigation
              (clj->js {:stack {:children
                                [{:component
                                  {:name    comp
                                   :id      comp
                                   :options   {:topBar {:visible false}
                                               :modalPresentationStyle "overCurrentContext"
                                               :layout {:backgroundColor "transparent"}}}}]}})))

(defn close-modal []
  (.dismissAllModals Navigation))

(re-frame/reg-fx :quo2-open-modal-fx open-modal)

(re-frame/reg-fx :quo2-close-modal-fx close-modal)
