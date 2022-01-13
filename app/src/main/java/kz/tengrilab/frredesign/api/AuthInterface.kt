package kz.tengrilab.frredesign.api

import kz.tengrilab.frredesign.Variables
import kz.tengrilab.frredesign.data.Auth
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthInterface {
    @Headers(Variables.headers)
    @POST("/api/login")
    fun getUser(@Body body: RequestBody): Call<Auth>
}