package com.example.mihail.taxitest.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.mihail.taxitest.ApiConfig
import com.example.mihail.taxitest.GeoHelper
import com.example.mihail.taxitest.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.Animation
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraUpdateSource
import com.yandex.mapkit.map.Map
import org.json.JSONObject


class MainActivity : FragmentActivity(), GoogleApiClient.OnConnectionFailedListener, CameraListener {
    override fun onCameraPositionChanged(p0: Map?, p1: CameraPosition?, p2: CameraUpdateSource?, p3: Boolean) {
        if (p3) showAddress(p1!!.target.latitude, p1.target.longitude)
        else labelTxt.visibility = View.GONE
        Log.d(tag, "${p1!!.target.latitude}/${p1.target.longitude}")
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        print("fail")
    }

    val tag = "MainActivity"

    private lateinit var mapView: MapView
    private lateinit var searchBtn: RelativeLayout
    private lateinit var settingsBtn: RelativeLayout
    private lateinit var homeBtn: RelativeLayout
    private lateinit var labelTxt: TextView
    private val geoHelper = GeoHelper(this)

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        MapKitFactory.setApiKey(ApiConfig.TOKEN_API_YANDEX_3)
        MapKitFactory.initialize(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mapView = findViewById(R.id.mapview)
        searchBtn = findViewById(R.id.search)
        homeBtn = findViewById(R.id.home)
        settingsBtn = findViewById(R.id.settings)
        labelTxt = findViewById(R.id.label)



        if (isGeoDisabled()) {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }

        mapView.map.move(
                CameraPosition(Point(54.300, 48.3300), 10.0f, 0.0f, 0.0f),
                Animation(Animation.Type.SMOOTH, 0f),
                null)
        searchBtn.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java)
//                    .putExtra()
            )
        }
        homeBtn.setOnClickListener {
            startActivity(Intent(this,SearchActivity::class.java)
//                    .putExtra()
            )
        }
        settingsBtn.setOnClickListener {
            startActivity(Intent(this,SettingsActivity::class.java))
        }

        mapView.map.addCameraListener(this)

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

    fun showAddress(lt: Double, lg: Double) {
        geoHelper.connect(geoHelper.addParamLglt(lt, lg)) {
            val json = JSONObject(it)
            labelTxt.visibility = View.VISIBLE
            labelTxt.text = geoHelper.getStreetFromJson(json)
//            Log.d(tag,it)
        }
    }

}
