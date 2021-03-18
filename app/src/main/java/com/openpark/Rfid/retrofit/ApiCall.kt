package com.quekitapp.gasloyalty.retrofit

import com.openpark.Rfid.views.models.ModelLogin
import com.openpark.Rfid.views.models.ModelNewVisitor
import retrofit2.Call
import retrofit2.http.*
import java.util.HashMap


interface ApiCall {

    @GET("User/login.php")
    fun login(@Query("username") username: String?, @Query("password") password: String?): Call<ModelLogin?>?

    @FormUrlEncoded
    @POST("User/NewUser.php")
    fun addNewVisitor(@FieldMap map: HashMap<String, String?>?): Call<ModelNewVisitor?>?
//
//    @GET("Member/ScanTank.php")
//    fun scan(@Query("tank_id") tank_id: String?): Call<ScanModel?>?
//
//    @POST("Member/RecognizePlate.php")
//    fun uploadPlateNo(@Body requestBody: VerifyPlate?): Call<ScanModel?>?
//
//    @POST("Member/ChargeGas.php")
//    fun charge(@Body requestBody: VerifyBody?): Call<ChargeModel?>?
//
//    @POST("User/VerifyOTP.php")
//    fun verify(@Body requestBody: VerifyBody?): Call<VerifyModel?>?
//
}