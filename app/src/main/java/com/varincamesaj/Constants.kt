package com.varincamesaj

object Constants {
    // Target location latitude (example: Istanbul)
    const val TARGET_LATITUDE = 41.0082

    // Target location longitude (example: Istanbul)
    const val TARGET_LONGITUDE = 28.9784 

    // Geofence radius in meters
    const val GEOFENCE_RADIUS_IN_METERS = 100f

    // Receiver WhatsApp phone number (with country code, e.g., +90555...)
    const val RECEIVER_PHONE_NUMBER = "+905550000000"

    // Message text to send
    const val MESSAGE_TEXT = "Geldim!"

    // WhatsApp Send Button View ID (kept separately for easy updating)
    const val WHATSAPP_SEND_BUTTON_ID = "com.whatsapp:id/send"

    // SharedPreferences Keys
    const val PREFS_NAME = "VarincaMesajPrefs"
    const val PREF_PHONE_NUMBER = "pref_phone_number"
    const val PREF_MESSAGE_TEXT = "pref_message_text"
    const val PREF_LATITUDE = "pref_latitude"
    const val PREF_LONGITUDE = "pref_longitude"
}
