package com.poixpixelcustom

import com.poixpixelcustom.util.Colors
import org.apache.logging.log4j.LogManager

object PoixpixelCustomMessaging {
    private val LOGGER = LogManager.getLogger("PoixpixelCustom")
    fun sendMsg(msg: String?) {
        LOGGER.info(Colors.strip(msg))
    }

    fun sendErrorMsg(msg: String) {
        LOGGER.warn(Colors.strip("Error: $msg"))
    }
}