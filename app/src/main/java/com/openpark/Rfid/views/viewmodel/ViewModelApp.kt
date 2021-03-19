package com.openpark.Rfid.views.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.interactive.ksi.propertyturkeybooking.interfaces.HandleRetrofitResp
import com.interactive.ksi.propertyturkeybooking.retrofitconfig.HandelCalls
import com.interactive.ksi.propertyturkeybooking.utlitites.DataEnum
import com.openpark.Rfid.views.models.*
import java.util.HashMap

class ViewModelApp :ViewModel(), HandleRetrofitResp {

    var loginLivedata = MutableLiveData<ModelLogin>()
    var newVisitorLivedata = MutableLiveData<ModelNewVisitor>()
    var searchphoneLivedata = MutableLiveData<ModelSearchPhone>()
    var chargeByPhoneLivedata = MutableLiveData<ModelChargeWithPhone>()
    var chargeByFridLivedata = MutableLiveData<ModelChargeWithFrid>()


    fun makeLogin(context: Context, meMap: HashMap<String, String?>?){

        HandelCalls.getInstance(context)?.call(DataEnum.login.name, meMap, true, this)

    }

    fun addNewVisitor(context: Context, meMap: HashMap<String, String?>?){

        HandelCalls.getInstance(context)?.call(DataEnum.newVisitor.name, meMap, true, this)

    }
    fun makeSearchPhone(context: Context, meMap: HashMap<String, String?>?){

        HandelCalls.getInstance(context)?.call(DataEnum.searchPhone.name, meMap, true, this)

    }
    fun chargeByPhone(context: Context, meMap: HashMap<String, String?>?){

        HandelCalls.getInstance(context)?.call(DataEnum.chargePhone.name, meMap, true, this)

    }
    fun chargeByFrid(context: Context, meMap: HashMap<String, String?>?){

        HandelCalls.getInstance(context)?.call(DataEnum.chargefrid.name, meMap, true, this)

    }


    override fun onResponseSuccess(flag: String?, o: Any?) {
        if(flag==DataEnum.login.name){
            val modelLogin: ModelLogin = o as ModelLogin
            loginLivedata.setValue(modelLogin)

        }else if(flag==DataEnum.newVisitor.name){
            val modelNewVisitor: ModelNewVisitor = o as ModelNewVisitor
            newVisitorLivedata.setValue(modelNewVisitor)

        }else if(flag==DataEnum.searchPhone.name){
            val modelSearchPhone: ModelSearchPhone = o as ModelSearchPhone
            searchphoneLivedata.setValue(modelSearchPhone)
        }else if(flag==DataEnum.chargePhone.name){
            val modelChargeWithPhone: ModelChargeWithPhone = o as ModelChargeWithPhone
            chargeByPhoneLivedata.setValue(modelChargeWithPhone)
        }else if(flag==DataEnum.chargefrid.name){
            val modelChargeWithFrid: ModelChargeWithFrid = o as ModelChargeWithFrid
            chargeByFridLivedata.setValue(modelChargeWithFrid)
        }
    }

    override fun onResponseFailure(flag: String?, o: String?) {
        TODO("Not yet implemented")
    }

    override fun onNoContent(flag: String?, code: Int) {
        TODO("Not yet implemented")
    }

    override fun onBadRequest(flag: String?, o: Any?) {
        TODO("Not yet implemented")
    }
}