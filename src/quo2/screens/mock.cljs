(ns quo2.screens.mock
  (:require [quo.react-native :as rn]
            [re-frame.core :as re-frame]
            [quo2.components.button :as button]
            [quo2.components.bottom-tabs :as bottom-tabs]))

(defn screen2 []
  [:<>
   [rn/text {:style {:font-size 40 :align-self :center :margin-top 100}} "Screen 2"]
   [bottom-tabs/bottom-tabs]])

(defn screen3 []
  [:<>
   [rn/text {:style {:font-size 40 :align-self :center :margin-top 100}} "Screen 3"]
   [bottom-tabs/bottom-tabs]])

(defn screen4 []
  [:<>
   [rn/text {:style {:font-size 40 :align-self :center}} "Modal Screen"]])

(defn exit-screen []
  [rn/view {:style {:flex 1
                    :justify-content :center
                    :align-items :center}}
   [rn/view {:style {:position :absolute
                     :bottom 150}}
    [button/button {:on-press #(re-frame/dispatch [:init-root :chat-stack])} "Exit Quo 2"]]
   [bottom-tabs/bottom-tabs]])
