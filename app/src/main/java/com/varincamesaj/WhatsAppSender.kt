package com.varincamesaj

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast

class WhatsAppSender(private val context: Context) {

    // Opens WhatsApp with a pre-filled message using ACTION_VIEW Intent and wa.me link
    fun sendWhatsAppMessage() {
        try {
            val prefs = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)
            val phone = prefs.getString(Constants.PREF_PHONE_NUMBER, Constants.RECEIVER_PHONE_NUMBER) ?: ""
            val message = prefs.getString(Constants.PREF_MESSAGE_TEXT, Constants.MESSAGE_TEXT) ?: ""
            val formattedNumber = phone.replace("+", "")
            val encodedText = Uri.encode(message)
            val url = "https://wa.me/$formattedNumber?text=$encodedText"
            
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            intent.setPackage("com.whatsapp")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            
            context.startActivity(intent)
            Log.i("WhatsAppSender", "WhatsApp başlatılıyor...")
        } catch (e: Exception) {
            Log.e("WhatsAppSender", "WhatsApp başlatılamadı: ${e.message}")
            Toast.makeText(context, "WhatsApp başlatılamadı. Uygulamanın yüklü olduğundan emin olun.", Toast.LENGTH_LONG).show()
        }
    }
}
