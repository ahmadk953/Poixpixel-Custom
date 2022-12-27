package com.poixpixelcustom.util

import java.io.*

object JavaUtil {
    /**
     * Recursively check if the interface inherits the super interface. Returns
     * false if not an interface. Returns true if sup = sub.
     *
     * @param sup The class of the interface you think it is a subinterface of.
     * @param sub The possible subinterface of the super interface.
     * @return true if it is a subinterface.
     */
    fun isSubInterface(sup: Class<*>, sub: Class<*>): Boolean {
        if (sup.isInterface && sub.isInterface) {
            if (sup == sub) return true
            for (c in sub.interfaces) if (isSubInterface(sup, c)) return true
        }
        return false
    }

    @Throws(IOException::class)
    fun readTextFromJar(path: String?): List<String> {
        val fin = BufferedReader(InputStreamReader(JavaUtil::class.java.getResourceAsStream(path)))
        var line: String
        val out: MutableList<String> = ArrayList()
        try {
            while (fin.readLine().also { line = it } != null) out.add(line)
        } catch (e: IOException) {
            throw IOException(e.cause)
        } finally {
            fin.close()
        }
        return out
    }
}