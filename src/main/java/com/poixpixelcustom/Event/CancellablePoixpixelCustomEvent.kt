package com.poixpixelcustom.Event

import com.poixpixelcustom.Confirmations.ConfirmationBuilder
import com.poixpixelcustom.util.BukkitTools
import org.bukkit.event.Cancellable
import org.bukkit.event.Event

/**
 * A class extended by PoixpixelCustomEvents Events which are Cancellable and which contain a
 * cancelMessage.
 *
 *
 * Having a getCancelMessage() available to us allows us to throw a
 * [CancelledEventException] which can use the cancelMessage in feedback
 * messaging or logging.
 *
 *
 * Used primarily in the
 * [ConfirmationBuilder.setCancellableEvent] and
 * [BukkitTools.ifCancelledThenThrow] code.
 *
 * @author LlmDL
 * @since 0.98.4.0.
 */
abstract class CancellablePoixpixelCustomEvent : Event(), Cancellable {
    private var isCancelled = false
    /**
     * @return cancelMessage a String which will be displayed to the player/sender
     * informing them that the action they commited is being denied, if
     * cancelMessage is blank then no message will be displayed.
     */
    /**
     * Sets the cancellation message which will display to the player. Set to "" to
     * display nothing.
     *
     * @param msg cancelMessage to display as feedback.
     */
    var cancelMessage = "Sorry, this event was cancelled."

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