package org.jdc.template.model.config

/**
 * Listener for remote config updates
 */
fun interface RemoteConfigUpdateListener {
    /**
     * Called when the remote config has been activated with realtime config changs
     */
    fun onRemoteConfigUpdated()
}