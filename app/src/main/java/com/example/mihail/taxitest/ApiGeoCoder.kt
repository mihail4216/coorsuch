package com.example.mihail.taxitest

import com.google.api.client.http.HttpRequest
import com.google.api.client.http.HttpResponse
import com.google.api.client.http.HttpTransport
import com.google.api.client.http.LowLevelHttpRequest
import org.apache.http.client.methods.HttpGet


class ApiGeoCoder {


    val staticUrl = "https://maps.googleapis.com/maps/api/geocode/json?language=ru"
    var URL = staticUrl
    val apiKey = "&key=${ApiConfig.TOKEN_API_GOOGLE_GEOCODING}"

    fun addParam(param: String, value: String): String {
        var url = URL
        url += "&$param=$value"
        URL = url
        return url
    }

    fun connect(){
        var url = URL + apiKey

    }



}