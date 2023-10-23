package com.example.ui.maps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.ourstoryapps.R
import com.example.ourstoryapps.data.AkunModel
import com.example.ourstoryapps.data.model.ListStoryItem

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.ourstoryapps.databinding.ActivityMapsBinding
import com.example.ourstoryapps.factory.ViewModelFactory
import com.example.ui.login.LoginViewModel
import com.google.android.gms.maps.model.LatLngBounds

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private val viewModel by viewModels<MapsViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val viewModelToken by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)




        viewModelToken.sessionGet().observe(this){
                data: AkunModel ->
            viewModel.fetchDataLatLongSotry(data.token)
        }



    }



    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        viewModel.listOurStory.observe(this){
                item ->
            if(item.isNotEmpty()){
                addManyMarker(item)

            }
        }


    }



    private val boundsBuilder = LatLngBounds.Builder()

    private fun addManyMarker(item:List<ListStoryItem>) {

        item.forEach { data ->

            Log.d("data loc","lat ${data.lat} long ${data.lon}")
            if (data.lat != null && data.lon != null){
                val latLng = LatLng(data.lat, data.lon)
                mMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(data.name)
                        .snippet(data.description)
                )
            boundsBuilder.include(latLng)
            }

        }

        val bounds: LatLngBounds = boundsBuilder.build()
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngBounds(
                bounds,
                resources.displayMetrics.widthPixels,
                resources.displayMetrics.heightPixels,
                300
            )
        )
    }
}