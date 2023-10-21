package com.example.ui.maps

import android.content.Intent
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
import com.example.ui.homepage.HomepageViewModel
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


    companion object {


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

//        val token:String = intent.getStringExtra("token").toString()
//        Log.d("token cuy",token)


        viewModelToken.sessionGet().observe(this){
                data: AkunModel ->
            viewModel.fetchDataLatLongSotry(data.token)
        }



    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        viewModel.listOurStory.observe(this){
                item ->
            if(item.isNotEmpty()){
                addManyMarker(item)

            }
        }


    }

    data class TourismPlace(
        val name: String,
        val latitude: Double,
        val longitude: Double
    )

    private val boundsBuilder = LatLngBounds.Builder()

    private fun addManyMarker(item:List<ListStoryItem>) {
//        val tourismPlace = listOf(
//            TourismPlace("Floating Market Lembang", -6.8168954,107.6151046),
//            TourismPlace("The Great Asia Africa", -6.8331128,107.6048483),
//            TourismPlace("Rabbit Town", -6.8668408,107.608081),
//            TourismPlace("Alun-Alun Kota Bandung", -6.9218518,107.6025294),
//            TourismPlace("Orchid Forest Cikole", -6.780725, 107.637409),
//        )
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
//            Log.d("data foreach","${tourism.name}")
//            val latLng = LatLng(tourism.lat!!.toDouble(), tourism.lon!!.toDouble())
//            mMap.addMarker(MarkerOptions().position(latLng).title(tourism.name))
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