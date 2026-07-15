package com.varincamesaj

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Toast

class AutoClickAccessibilityService : AccessibilityService() {

    // Called when the accessibility service is successfully connected
    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.i("AutoClickService", "Accessibility Service connected.")
    }

    private var lastClickTime = 0L

    // Called when the accessibility service receives an event
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        try {
            if (event == null || event.packageName?.toString() != "com.whatsapp") return

            // Prevent spam clicking (wait at least 3 seconds between clicks)
            if (System.currentTimeMillis() - lastClickTime < 3000) return

            val rootNode = rootInActiveWindow ?: return
            
            val sendButton = findSendButton(rootNode)
            if (sendButton != null) {
                val clicked = sendButton.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                if (clicked) {
                    lastClickTime = System.currentTimeMillis()
                    Log.i("AutoClickService", "WhatsApp mesajı başarıyla gönderildi.")
                } else {
                    Log.w("AutoClickService", "Gönder butonuna tıklanamadı.")
                }
            }
        } catch (e: Exception) {
            Log.e("AutoClickService", "Otomatik tıklama sırasında hata: ${e.message}")
        }
    }

    private fun findSendButton(node: AccessibilityNodeInfo?): AccessibilityNodeInfo? {
        if (node == null) return null

        val viewId = node.viewIdResourceName
        val contentDesc = node.contentDescription?.toString()?.lowercase() ?: ""
        val text = node.text?.toString()?.lowercase() ?: ""

        // Check if this node is the send button
        val isSendButtonId = viewId == "com.whatsapp:id/send"
        val isSendDesc = contentDesc.contains("gönder") || contentDesc.contains("send")

        if (isSendButtonId || isSendDesc) {
            // Find the closest clickable node (itself or a parent)
            var clickableNode: AccessibilityNodeInfo? = node
            while (clickableNode != null) {
                if (clickableNode.isClickable) {
                    return clickableNode
                }
                clickableNode = clickableNode.parent
            }
        }

        // Recursively search children
        for (i in 0 until node.childCount) {
            val found = findSendButton(node.getChild(i))
            if (found != null) return found
        }

        return null
    }

    // Called when the accessibility service is interrupted
    override fun onInterrupt() {
        Log.w("AutoClickService", "Accessibility Service interrupted.")
    }
}
