(ns status-im.multiaccounts.logout.core
  (:require [re-frame.core :as re-frame]
            [status-im.anon-metrics.core :as anon-metrics]
            [status-im.i18n.i18n :as i18n]
            [status-im.init.core :as init]
            [status-im.native-module.core :as status]
            [status-im.transport.core :as transport]
            [status-im.utils.fx :as fx]
            [status-im.multiaccounts.core :as multiaccounts]
            [status-im.utils.keychain.core :as keychain]
            [status-im.notifications.core :as notifications]
            [status-im.wallet.core :as wallet]))

(fx/defn logout-method
  {:events [::logout-method]}
  [{:keys [db] :as cofx} {:keys [auth-method logout?]}]
  (let [key-uid              (get-in db [:multiaccount :key-uid])
        should-send-metrics? (get-in db [:multiaccount :anon-metrics/should-send?])]
    (fx/merge cofx
              {:init-root-fx                         :progress
               :hide-popover                         nil
               ::logout                              nil
               ::multiaccounts/webview-debug-changed false
               :keychain/clear-user-password         key-uid
               ::init/open-multiaccounts             #(re-frame/dispatch [::init/initialize-multiaccounts % {:logout? logout?}])}
              (when should-send-metrics?
                (anon-metrics/stop-transferring))
              (keychain/save-auth-method key-uid auth-method)
              (transport/stop-whisper)
              (wallet/clear-timeouts)
              (init/initialize-app-db))))

(fx/defn logout
  {:events [:logout :multiaccounts.logout.ui/logout-confirmed :multiaccounts.update.callback/save-settings-success]}
  [cofx]
  ;; we need to disable notifications before starting the logout process
  (fx/merge cofx
            {:dispatch-later [{:ms       100
                               :dispatch [::logout-method
                                          {:auth-method keychain/auth-method-none
                                           :logout?     true}]}]}
            (notifications/logout-disable)))

(fx/defn show-logout-confirmation
  {:events [:multiaccounts.logout.ui/logout-pressed]}
  [_]
  {:ui/show-confirmation
   {:title               (i18n/label :t/logout-title)
    :content             (i18n/label :t/logout-are-you-sure)
    :confirm-button-text (i18n/label :t/logout)
    :on-accept           #(re-frame/dispatch [:multiaccounts.logout.ui/logout-confirmed])
    :on-cancel           nil}})

(fx/defn biometric-logout
  {:events [:biometric-logout]}
  [cofx]
  (fx/merge cofx
            (logout-method {:auth-method keychain/auth-method-biometric-prepare
                            :logout?     false})
            (fn [{:keys [db]}]
              {:db (assoc-in db [:multiaccounts/login :save-password?] true)})))

(re-frame/reg-fx
 ::logout
 (fn []
   (status/logout)))
