package com.example.mihail.taxitest

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import org.json.JSONObject


class GeoHelper(val context: Context) {

    val tag = "ApiCoder"

    val staticUrl = "https://maps.googleapis.com/maps/api/geocode/json?language=ru"
    val apiKey = "&key=${ApiConfig.TOKEN_API_GOOGLE_GEOCODING}"
    var URL = staticUrl

    fun addParamLglt(lt: Double, lg: Double): String {
        var url = URL
        url += "&latlng=$lt,$lg"
        return url
    }

    fun addParam(param: String, value: String): String {
        var url = URL
        url += "&$param=$value"
        URL = url
        return url
    }

    fun connect(urls: String, urlX: (String) -> Unit) {
        val url = urls + apiKey
        val queue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(Request.Method.GET, url,
                Response.Listener<String> { response ->
                    // Display the first 500 characters of the response string.
//                    textView.text = "Response is: ${response.substring(0, 500)}"
                    urlX(response)
                },
                Response.ErrorListener { })
        queue.add(stringRequest)
    }

    fun getStreetFromJson(jsonObject: JSONObject):String{
        val home = ((jsonObject.getJSONArray("results")[0] as JSONObject).getJSONArray("address_components")[0] as JSONObject).get("long_name")
        val street = ((jsonObject.getJSONArray("results")[0] as JSONObject).getJSONArray("address_components")[1] as JSONObject).get("long_name")
        val city = ((jsonObject.getJSONArray("results")[0] as JSONObject).getJSONArray("address_components")[2] as JSONObject).get("long_name")
        return "$street,$home,\n $city"
    }
    fun getShortStreetFromJson(jsonObject: JSONObject):String{
        val home = ((jsonObject.getJSONArray("results")[0] as JSONObject).getJSONArray("address_components")[0] as JSONObject).get("long_name")
        val street = ((jsonObject.getJSONArray("results")[0] as JSONObject).getJSONArray("address_components")[1] as JSONObject).get("long_name")
        return "$street,$home"
    }


}