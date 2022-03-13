(ns quo2.navigation.roots
  (:require [status-im.navigation.roots :as nav-roots]))

(defn roots []
  {:quo2-tabs-stack
   {:root
    {:bottomTabs
     {:id       :quo2-tabs-stack
      :options (merge (nav-roots/default-root)
                      {:bottomTabs {:titleDisplayMode :alwaysHide
                                    :tabsAttachMode   :together
                                    :visible          false
                                    :animate          false}})
      :children [{:stack {:id       :profile-stack
                          :children [{:component {:name    :quo2-preview
                                                  :id      :quo2-preview
                                                  :options {:topBar {:visible false}}}}]
                          :options  {:bottomTab (nav-roots/bottom-tab-general :main-icons/message :home-tab-button)}}}
                 {:stack {:id       :browser-stack
                          :children [{:component {:name    :quo2-mock-screen2
                                                  :id      :empty-tab
                                                  :options {:topBar {:visible false}}}}]

                          :options  {:bottomTab (nav-roots/bottom-tab-general :main-icons/browser :dapp-tab-button)}}}
                 {:stack {:id       :wallet-stack
                          :children [{:component {:name    :quo2-mock-screen3
                                                  :id      :wallet
                                                  :options {:topBar {:visible false}}}}]
                          :options  {:bottomTab (nav-roots/bottom-tab-general :main-icons/wallet :wallet-tab-button)}}}
                 {:stack {:id       :quo2-preview-stack
                          :children [{:component {:name    :quo2-exit-screen
                                                  :id      :quo2-exit-screen
                                                  :options {:topBar {:visible false}}}}]
                          :options  {:bottomTab (nav-roots/bottom-tab-general :main-icons/user-profile :profile-tab-button)}}}]}}}})
