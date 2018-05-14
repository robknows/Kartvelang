/*Created on 30/04/18. */
package logic

import org.jnativehook.GlobalScreen
import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.NativeKeyListener

open class KeyWaiter {
    private class KeyListener(private val keyCode: Int, @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN") private val lock: Object) : NativeKeyListener {
        override fun nativeKeyPressed(e: NativeKeyEvent?) {
            if (e != null) {
                if (e.keyCode == keyCode) {
                    synchronized(lock) {
                        lock.notifyAll()
                    }
                }
            }
        }

        override fun nativeKeyReleased(e: NativeKeyEvent?) {}

        override fun nativeKeyTyped(e: NativeKeyEvent?) {}
    }

    fun await(keyCode: Int) {
        try {
            val lock = java.lang.Object()
            val keyListener = KeyListener(keyCode, lock)
            GlobalScreen.addNativeKeyListener(keyListener)
            synchronized(lock) {
                lock.wait()
            }
            GlobalScreen.removeNativeKeyListener(keyListener)
        } catch (e: Exception) {
            e.printStackTrace()
            println("Exiting")
            System.exit(1)
        }
    }
}

enum class Key(val keyCode: Int) {
    ENTER(NativeKeyEvent.VC_ENTER)
}