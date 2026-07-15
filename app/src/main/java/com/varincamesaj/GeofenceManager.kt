package com.varincamesaj

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices

class GeofenceManager(private val context: Context) {

    private val geofencingClient: GeofencingClient = LocationServices.getGeofencingClient(context)

    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
        PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )
    }

    // Initializes and registers the geofence using Constants location and radius
    fun setupGeofence() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Konum izni verilmemiş, geofence ayarlanamadı.", Toast.LENGTH_LONG).show()
            return
        }

        val geofence = Geofence.Builder()
            .setRequestId("varinca_mesaj_geofence")
            .setCircularRegion(
                context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE).getFloat(Constants.PREF_LATITUDE, Constants.TARGET_LATITUDE.toFloat()).toDouble(),
                context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE).getFloat(Constants.PREF_LONGITUDE, Constants.TARGET_LONGITUDE.toFloat()).toDouble(),
                Constants.GEOFENCE_RADIUS_IN_METERS
            )
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
            .build()

        val geofencingRequest = GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofence(geofence)
            .build()

        try {
            geofencingClient.addGeofences(geofencingRequest, geofencePendingIntent)
                .addOnSuccessListener {
                    Toast.makeText(context, "Geofence başarıyla eklendi.", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Geofence eklenirken bir hata oluştu.", Toast.LENGTH_LONG).show()
                }
        } catch (e: SecurityException) {
            Toast.makeText(context, "Geofence için gerekli izinler eksik.", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Geofence ayarlanırken beklenmeyen bir hata oluştu.", Toast.LENGTH_LONG).show()
        }
    }

    // Handles the geofence transition events when entered
    fun handleGeofenceTransition() {
        // TODO: Handle logic upon entering geofence
    }
}
