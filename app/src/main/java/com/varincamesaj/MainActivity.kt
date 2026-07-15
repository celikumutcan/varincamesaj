package com.varincamesaj

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        val coarseLocationGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false

        if (fineLocationGranted || coarseLocationGranted) {
            requestBackgroundLocation()
        } else {
            Toast.makeText(this, "Konum izni reddedildi. Geofence çalışamaz.", Toast.LENGTH_LONG).show()
        }
    }

    private val backgroundLocationRequest = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            Toast.makeText(this, "Tüm konum izinleri alındı. Şimdi Erişilebilirlik ayarını açınız.", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Arka plan konum izni reddedildi.", Toast.LENGTH_LONG).show()
        }
    }

    // Initializes the main activity and sets up UI programmatically
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        try {
            setupUI()
            requestPermissions()
        } catch (e: Exception) {
            Toast.makeText(this, "İzinler kontrol edilirken bir hata oluştu: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    // Sets up a simple programmatic UI to guide the user
    private fun setupUI() {
        val prefs = getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)
        
        val scrollView = android.widget.ScrollView(this)
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
        }
        scrollView.addView(layout)

        val infoText = TextView(this).apply {
            text = "VarıncaMesaj Uygulaması\n\n" +
                   "1. Konum izinlerini verin.\n" +
                   "2. Erişilebilirlik ayarlarından 'VarincaMesaj' servisini açın.\n" +
                   "3. Aşağıdaki bilgileri doldurup Başlatın."
            textSize = 16f
            setPadding(0, 0, 0, 32)
        }

        val phoneInput = android.widget.EditText(this).apply {
            hint = "Hedef Telefon (+90...)"
            inputType = android.text.InputType.TYPE_CLASS_PHONE
            setText(prefs.getString(Constants.PREF_PHONE_NUMBER, Constants.RECEIVER_PHONE_NUMBER))
        }

        val messageInput = android.widget.EditText(this).apply {
            hint = "Gönderilecek Mesaj"
            setText(prefs.getString(Constants.PREF_MESSAGE_TEXT, Constants.MESSAGE_TEXT))
        }

        val latInput = android.widget.EditText(this).apply {
            hint = "Enlem (Latitude)"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL or android.text.InputType.TYPE_NUMBER_FLAG_SIGNED
            setText(prefs.getFloat(Constants.PREF_LATITUDE, Constants.TARGET_LATITUDE.toFloat()).toString())
        }

        val lngInput = android.widget.EditText(this).apply {
            hint = "Boylam (Longitude)"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL or android.text.InputType.TYPE_NUMBER_FLAG_SIGNED
            setText(prefs.getFloat(Constants.PREF_LONGITUDE, Constants.TARGET_LONGITUDE.toFloat()).toString())
        }

        val accessibilityButton = Button(this).apply {
            text = "Erişilebilirlik Ayarlarına Git"
            setOnClickListener {
                try {
                    startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, "Erişilebilirlik ayarları açılamadı.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val startGeofenceButton = Button(this).apply {
            text = "Ayarları Kaydet ve Geofence Başlat"
            setOnClickListener {
                val phone = phoneInput.text.toString()
                val msg = messageInput.text.toString()
                val lat = latInput.text.toString().toFloatOrNull()
                val lng = lngInput.text.toString().toFloatOrNull()

                if (phone.isBlank() || msg.isBlank() || lat == null || lng == null) {
                    Toast.makeText(this@MainActivity, "Lütfen tüm alanları doğru doldurun.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                prefs.edit().apply {
                    putString(Constants.PREF_PHONE_NUMBER, phone)
                    putString(Constants.PREF_MESSAGE_TEXT, msg)
                    putFloat(Constants.PREF_LATITUDE, lat)
                    putFloat(Constants.PREF_LONGITUDE, lng)
                    apply()
                }

                if (hasLocationPermissions()) {
                    GeofenceManager(this@MainActivity).setupGeofence()
                } else {
                    Toast.makeText(this@MainActivity, "Lütfen önce konum izinlerini verin.", Toast.LENGTH_SHORT).show()
                    requestPermissions()
                }
            }
        }

        layout.addView(infoText)
        layout.addView(phoneInput)
        layout.addView(messageInput)
        layout.addView(latInput)
        layout.addView(lngInput)
        layout.addView(accessibilityButton)
        layout.addView(startGeofenceButton)

        setContentView(scrollView)
    }

    // Checks if basic location permissions are granted
    private fun hasLocationPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // Requests required location permissions from the user
    private fun requestPermissions() {
        if (!hasLocationPermissions()) {
            locationPermissionRequest.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ))
        } else {
            requestBackgroundLocation()
        }
    }

    // Requests background location permission for Android 10+
    private fun requestBackgroundLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                backgroundLocationRequest.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            }
        }
    }
}
