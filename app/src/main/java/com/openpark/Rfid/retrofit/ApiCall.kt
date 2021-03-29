package com.quekitapp.gasloyalty.retrofit

import com.openpark.Rfid.views.models.*
import retrofit2.Call
import retrofit2.http.*
import java.util.HashMap


interface ApiCall {

    @POST("User/login.php")
    fun login(@Query("username") username: String?, @Query("password") password: String?): Call<ModelLogin?>?

    @POST("User/NewUser.php")
    fun addNewVisitor(@Body requestBody: ModelAddVisitor?): Call<ModelNewVisitor?>?

    @POST("User/SearchByMobile.php")
    fun searchByPhone(@Query("mobile_no") mobile_no: String?): Call<ModelSearchPhone?>?

    @POST("User/SearchByRFID.php")
    fun searchByRfid(@Query("tag_id") tag_id: String?): Call<ModelSearchPhone?>?

    @POST("User/WalletActivity.php")
    fun getWalletReports(@Query("user_id") user_id: String?): Call<ModelReports?>?


    @POST("User/ChargeWallet_mobile.php")
    fun chargeWallerWithPhone(@Body requestBody: ModelSendWallet?): Call<ModelChargeWithPhone?>?

    @POST("User/ChargeWallet_RFID.php")
    fun chargeWallerWithFrid(@Body requestBody: ModelSendWalletFrid?): Call<ModelChargeWithFrid?>?

    @POST("User/LinkRFIDCard.php")
    fun linkFridCard(@Body requestBody: ModelLinkFrid?): Call<ModelNewCardFrid?>?

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