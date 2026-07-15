package com.varincamesaj

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeofenceBroadcastReceiver : BroadcastReceiver() {

    // Receives the broadcast intent triggered by the geofence transition
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return

        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        
        if (geofencingEvent == null) {
            Log.e("GeofenceReceiver", "GeofencingEvent alınamadı.")
            return
        }

        if (geofencingEvent.hasError()) {
            val errorMessage = "Geofence hatası oluştu: ${geofencingEvent.errorCode}"
            Log.e("GeofenceReceiver", errorMessage)
            Toast.makeText(context, "Konum algılama hatası oluştu.", Toast.LENGTH_LONG).show()
            return
        }

        val geofenceTransition = geofencingEvent.geofenceTransition

        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            Log.i("GeofenceReceiver", "Hedef konuma girildi! WhatsAppSender çağrılıyor.")
            Toast.makeText(context, "Hedef konuma varıldı, mesaj gönderiliyor...", Toast.LENGTH_SHORT).show()
            
            // Trigger WhatsAppSender
            val whatsappSender = WhatsAppSender(context)
            whatsappSender.sendWhatsAppMessage()
        } else {
            Log.w("GeofenceReceiver", "Farklı bir geofence geçişi algılandı: $geofenceTransition")
        }
    }
}
