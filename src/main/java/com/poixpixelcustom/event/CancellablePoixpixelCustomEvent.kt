package com.poixpixelcustom.event

import org.bukkit.event.Cancellable
import org.bukkit.event.Event

abstract class CancellablePoixpixelCustomEvent : Event(), Cancellable {
    private var isCancelled = false

    /**
     * @return true if this event is cancelled.
     */
    override fun isCancelled(): Boolean {
        return isCancelled
    }

    /**
     * Sets the event to be cancelled or not.
     *
     * @param cancelled true will cancel the event.
     */
    override fun setCancelled(cancelled: Boolean) {
        isCancelled = cancelled
    }
}