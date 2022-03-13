(ns quo2.components.switcher
  (:require [quo.react-native :as rn]
            [quo2.navigation :as quo2-navigation]
            [status-im.utils.handlers :refer [>evt]]
            [quo2.components.button :as button]
            [status-im.utils.platform :as platform]
            [status-im.react-native.resources :as resources]))

(defn switcher-button []
  [rn/touchable-opacity {:active-opacity 1
                         :on-press       #(>evt [::quo2-navigation/quo2-open-modal
                                                 :quo2-switcher-screen])
                         :style          {:position        :absolute
                                          :bottom          (if platform/android? 35 55)
                                          :justify-content :center
                                          :align-items     :center
                                          :align-self      :center}}
   [rn/image {:source (resources/get-image :status-logo)
              :style {:width      48
                      :height     48}}]])

(defn switcher-screen []
  [rn/view {:style {:flex             1
                    :background-color "#040B14CC"
                    :justify-content :center
                    :align-items :center}}
   [rn/view {:style {:position :absolute
                     :top 100}}
    [button/button {:on-press #(>evt [::quo2-navigation/quo2-open-modal :quo2-mock-screen4])
                    :size 32} "Open New Screen"]]
   [rn/view {:style {:position :absolute
                     :bottom 50}}
    [button/button {:on-press #(>evt [::quo2-navigation/quo2-close-modal
                                      :quo2-switcher-screen])
                    :size 32} "Close"]]])
