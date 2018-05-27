package com.example.mihail.taxitest.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.mihail.taxitest.ApiConfig
import com.example.mihail.taxitest.GeoHelper
import com.example.mihail.taxitest.R
import com.example.mihail.taxitest.repository.MainRepository
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
import java.lang.Exception


class MainActivity : FragmentActivity(), GoogleApiClient.OnConnectionFailedListener, CameraListener {

    override fun onCameraPositionChanged(p0: Map?, p1: CameraPosition?, p2: CameraUpdateSource?, p3: Boolean) {
        if (p3) showAddress(p1!!.target.latitude, p1.target.longitude)
        else labelTxt.visibility = View.GONE
        cameraPosition = p1!!
        Log.d(tag, "${p1.target.latitude}/${p1.target.longitude}")
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
    private lateinit var compasBtn: View

    private lateinit var whereTxt: EditText
    private lateinit var whenceTxt: EditText
    private lateinit var contSearch: View
    private lateinit var methodPay: TextView

    private lateinit var targetVeiw: View

    private lateinit var cameraPosition: CameraPosition

    private val geoHelper = GeoHelper(this)

    private var latitude = 0.0
    private var longitude = 0.0
    private var textStreet = ""
    private var textStreetTarget = ""

    @SuppressLint("SetTextI18n", "MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        MapKitFactory.setApiKey(ApiConfig.TOKEN_API_YANDEX_3)
        MapKitFactory.initialize(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MainRepository.instance.init(this)

        mapView = findViewById(R.id.mapview)
        searchBtn = findViewById(R.id.search)
        homeBtn = findViewById(R.id.home)
        settingsBtn = findViewById(R.id.settings)
        labelTxt = findViewById(R.id.label)
        compasBtn = findViewById(R.id.compas)
        targetVeiw = findViewById(R.id.target)

        whenceTxt = findViewById(R.id.whence)
        whereTxt = findViewById(R.id.where)
        contSearch = findViewById(R.id.cont_search)
        methodPay = findViewById(R.id.choice_pay)


        val mLocationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 10f, locationListener)
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10f, locationListener)


        compasBtn.setOnClickListener {
            try {
                mapView.map.move(
                        CameraPosition(Point(latitude, longitude), cameraPosition.zoom, cameraPosition.azimuth, cameraPosition.tilt),
                        Animation(Animation.Type.SMOOTH, 0.5f),
                        null)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        mapView.map.move(
                CameraPosition(Point(54.200, 48.3300), 10.0f, 0.0f, 0.0f),
                Animation(Animation.Type.SMOOTH, 0f),
                null)

        searchBtn.setOnClickListener {
            getStringStreet(latitude, longitude)
            hideOrShowAddress(true)
            fillTextField(textStreet,"")
        }

        homeBtn.setOnClickListener {
            getStringStreet(latitude, longitude)
            hideOrShowAddress(true)
            fillTextField(textStreet,MainRepository.instance.homeAddress)

        }
        targetVeiw.setOnLongClickListener {
            getStringStreet(latitude, longitude)
            getStreetByTarget(cameraPosition.target.latitude, cameraPosition.target.longitude)
            hideOrShowAddress(true)
            fillTextField(textStreet,textStreetTarget)
            true
        }

        settingsBtn.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        mapView.map.addCameraListener(this)

        methodPay.setOnClickListener {
            startActivity(Intent(this,SettingsActivity::class.java))
        }

    }

    private fun getStreetByTarget(latitude: Double, longitude: Double) {
        geoHelper.connect(geoHelper.addParamLglt(latitude, longitude)) {
            val json = JSONObject(it)
            labelTxt.visibility = View.VISIBLE
            textStreetTarget = geoHelper.getShortStreetFromJson(json)
        }
    }

    fun hideOrShowAddress(isShowSearch: Boolean) {
        if (isShowSearch) {
            contSearch.visibility = View.VISIBLE
            compasBtn.visibility = View.GONE
            searchBtn.visibility = View.GONE
            homeBtn.visibility = View.GONE
        } else {
            contSearch.visibility = View.GONE
            compasBtn.visibility = View.VISIBLE
            searchBtn.visibility = View.VISIBLE
            homeBtn.visibility = View.VISIBLE
        }


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
        }
    }

    fun getStringStreet(lt: Double, lg: Double) {
        geoHelper.connect(geoHelper.addParamLglt(lt, lg)) {
            val json = JSONObject(it)
            labelTxt.visibility = View.VISIBLE
            textStreet = geoHelper.getShortStreetFromJson(json)
        }
    }


    private val locationListener = object : LocationListener {
        override fun onLocationChanged(p0: Location?) {
            latitude = p0!!.latitude
            longitude = p0.longitude
        }

        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
        }

        override fun onProviderEnabled(p0: String?) {
        }

        override fun onProviderDisabled(p0: String?) {
        }

    }

    override fun onResume() {
        super.onResume()
        if (isGeoDisabled()) {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
        changeText()
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        hideOrShowAddress(false)
    }

    fun clearTextField() {
        whenceTxt.text.clear()
        whereTxt.text.clear()
    }
    fun fillTextField(whence:String,where:String){
        clearTextField()
        whereTxt.text.append(where)
        whenceTxt.text.append(whence)
    }

    private fun changeText(){
        if (MainRepository.instance.isPayCard){
            methodPay.text = "Картой"
        }else{
            methodPay.text = "Наличными"
        }
    }

}
