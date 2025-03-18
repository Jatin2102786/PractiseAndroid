@file:Suppress("DEPRECATION")

package com.jatin.practiseandroid.location

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.jatin.practiseandroid.R
import com.jatin.practiseandroid.databinding.ActivityLocationAccessBinding
import java.io.IOException
import java.util.Locale

class LocationAccessActivity : AppCompatActivity() {


    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_REQUEST_CODE = 1001
    private lateinit var binding: ActivityLocationAccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLocationAccessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        initializing fused location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


//        Accessing location permission at run time

        binding.locationBtn.setOnClickListener {
            checkLocationPermissionAndGetLocation()
        }
       
    }

    private fun checkLocationPermissionAndGetLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            getCurrentLocation()
        }
    }

    private fun getCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->

                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude

                    binding.longitude.text = latitude.toString()
                    binding.latitude.text = longitude.toString()
                    getCompleteAddressString(latitude,longitude)

                }
            }.addOnFailureListener {
                Toast.makeText(this, "Getting error while accessing location", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun getCompleteAddressString(LATITUDE: Double, LONGITUDE: Double) {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1)
            if (!addresses.isNullOrEmpty()) {
                val address = addresses[0]

                val addressString = address.getAddressLine(0)

                val placeIdIndex = addressString.indexOf(" ")
                if (placeIdIndex != -1) {
                    binding.location.text = addressString.substring(placeIdIndex + 1)
                } else {
                    binding.location.text = addressString
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray,
//        deviceId: Int
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)
//        when(requestCode) {
//            LOCATION_PERMISSION_REQUEST_CODE -> {
//                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    getCurrentLocation()
//
//                } else {
//                    Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }





//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
//        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                getCurrentLocation()
//            } else {
//                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
}