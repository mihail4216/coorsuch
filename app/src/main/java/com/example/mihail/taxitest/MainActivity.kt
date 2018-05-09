package com.example.mihail.taxitest

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.FragmentActivity
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.PendingResults
import com.google.android.gms.location.places.PlaceReport
//import com.google.android.gms.location.places.PlaceDetectionApi
//import com.google.android.gms.location.places.Places
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.Animation
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point


class MainActivity : FragmentActivity(), GoogleApiClient.OnConnectionFailedListener {
    override fun onConnectionFailed(p0: ConnectionResult) {
        print("fail")
    }

    private lateinit var mapView: MapView
    private lateinit var searchBtn: RelativeLayout
    private lateinit var settingsBtn: RelativeLayout
    private lateinit var homeBtn: RelativeLayout
    private lateinit var labelTxt: TextView


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        MapKitFactory.setApiKey(ApiConfig.TOKEN_API_YANDEX_2)
        MapKitFactory.initialize(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mapView = findViewById(R.id.mapview)
        searchBtn = findViewById(R.id.search)
        homeBtn = findViewById(R.id.home)
        settingsBtn = findViewById(R.id.settings)
        labelTxt = findViewById(R.id.label)

        if (isGeoDisabled()){
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }

        mapView.map.move(
                CameraPosition(Point( 54.300, 48.3300), 12.0f, 0.0f, 0.0f),
                Animation(Animation.Type.SMOOTH, 0f),
                null)
        searchBtn.setOnClickListener {

            //            labelTxt.text = "${}"
//            labelTxt.text = "${mapView.map.cameraPosition.target.latitude}/${mapView.map.cameraPosition.target.longitude}"
        }
        val coder = ApiGeoCoder()
        coder.addParam("latlng","${54.300},${48.3300}")
        coder.connect()


//
    }


    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    fun isGeoDisabled(): Boolean {
        val mLocationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val mIsGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val mIsNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        return !mIsGPSEnabled && !mIsNetworkEnabled
    }
}
