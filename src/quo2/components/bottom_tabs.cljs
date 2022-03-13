(ns quo2.components.bottom-tabs
  (:require [quo.theme :as theme]
            [quo2.navigation :as quo2-navigation]
            [quo.react-native :as rn]
            [quo2.components.switcher :as switcher]
            [reagent.core :as reagent]
            [quo2.foundations.colors :as colors]
            [status-im.utils.platform :as platform]
            [status-im.ui.components.icons.icons :as icons]
            [status-im.utils.handlers :refer [>evt]]))

(def selected-tab (reagent/atom :quo2-preview))

(def themes
  {:light {:background-color colors/neutral-70-opa-90
           :non-selected-tab colors/neutral-50
           :selected-tab     colors/white}
   :dark  {:background-color colors/neutral-80-opa-90
           :non-selected-tab colors/neutral-40
           :selected-tab     colors/white}})

(defn get-color [key]
  (get-in themes [(theme/get-theme) key]))

(defn bottom-tab-pressed [tab]
  (when-not (= tab @selected-tab)
    (reset! selected-tab tab)
    (>evt [::quo2-navigation/quo2-navigate-change-tab tab])))

(defn bottom-tab [icon tab]
  [rn/touchable-opacity {:style {:padding   15}
                         :active-opacity 1
                         :on-press #(bottom-tab-pressed tab)}
   [icons/icon icon {:width  25
                     :height 25
                     :color  (get-color
                              (if (= tab @selected-tab)
                                :selected-tab
                                :non-selected-tab))}]])

(defn bottom-tabs []
  [:<>
   [rn/view {:style
             {:background-color (get-color :background-color)
              :flex-direction   :row
              :flex             1
              :align-items      :center
              :justify-content  :space-around
              :height           (if platform/android? 60 80)
              :position         :absolute
              :bottom           0
              :right            0
              :left             0}}  ;; TODO - use different height for android and ios(Confirm from Design)
    [bottom-tab :main-icons/message :quo2-preview]
    [bottom-tab :main-icons/browser :browser]
    [rn/view {:width 10}]
    [bottom-tab :main-icons/wallet :wallet]
    [bottom-tab :main-icons/user-profile :profile]]
   [switcher/switcher-button]])
