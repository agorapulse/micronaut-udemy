package com.agorapulse.udemy.broker.store

import javax.inject.Singleton
import com.agorapulse.udemy.broker.model.WatchList

@Singleton
class InMemoryAccountStore {

    Map<UUID, WatchList> watchListsPerAccount = [:]

    WatchList getWatchList(final UUID uuid) {
        watchListsPerAccount[uuid] ?: new WatchList()
    }

    WatchList updateWatchList(final UUID uuid, final WatchList watchList) {
        watchListsPerAccount[uuid] = watchList
        getWatchList(uuid)
    }

    void deleteWatchList(final UUID uuid) {
        watchListsPerAccount.remove(uuid)
    }
}
