package com.poixpixelcustom.Confirmations

import com.poixpixelcustom.Event.CancellablePoixpixelCustomEvent

class ConfirmationBuilder {
    var cancelHandler: Runnable? = null
    private var title = "are_you_sure_you_want_to_continue"
    var pluginPrefix = "poixpixelcustom"
    var duration = 1200
    var runAsync = false
    var event: CancellablePoixpixelCustomEvent? = null

    /**
     * The code to run on cancellation.
     *
     * @param cancelHandler The runnable to run on cancellation of the confirmation.
     * @return A builder reference of this object.
     */
    fun runOnCancel(cancelHandler: Runnable?): ConfirmationBuilder {
        this.cancelHandler = cancelHandler
        return this
    }

    /**
     * Sets the title of the confirmation to be sent.
     *
     * @param title The title of the confirmation.
     * @return A builder reference of this object.
     */
    @Deprecated("since 0.97.3.0 - Use {@link#setTitle(Translatable)} instead.")
    fun setTitle(title: String): ConfirmationBuilder {
        this.title = title
        return this
    }

    /**
     * Sets the duration the confirmation will run for.
     *
     * @param duration The duration in second.
     * @return A builder reference of this object.
     */
    fun setDuration(duration: Int): ConfirmationBuilder {
        this.duration = duration
        return this
    }

    fun setCancellableEvent(event: CancellablePoixpixelCustomEvent?): ConfirmationBuilder {
        this.event = event
        return this
    }

    /**
     * Sets whether the confirmation will run it's accept handler
     * async or not.
     *
     * @param runAsync Whether to run async or not.
     * @return A builder reference of this object.
     */
    fun setAsync(runAsync: Boolean): ConfirmationBuilder {
        this.runAsync = runAsync
        return this
    }

    /**
     * Sets the base plugin command used when the cancel or confirm commands are run.
     * @param prefix String plugin name sending this Confirmation.
     * @return ConfirmationBuilder with an overriden command prefix.
     */
    fun setPluginCommandPrefix(prefix: String): ConfirmationBuilder {
        pluginPrefix = prefix
        return this
    }
    /**
     * Builds a new instance of [Confirmation] from
     * this object's state.
     *
     * @return A new Confirmation object.
     */
    /**public Confirmation build() {
     * return new Confirmation(this);
     * }
     *
     * / **
     * Builds and sends this confirmation to the given CommandSender.
     *
     * @param sender The sender to send the confirmation to.
     */
    /**public void sendTo(CommandSender sender) {
     * Confirmation confirmation = build();
     * ConfirmationHandler.sendConfirmation(sender, confirmation);
     * }
     */
}