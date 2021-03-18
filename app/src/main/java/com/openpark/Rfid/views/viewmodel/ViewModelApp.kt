package com.openpark.Rfid.views.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.interactive.ksi.propertyturkeybooking.interfaces.HandleRetrofitResp
import com.interactive.ksi.propertyturkeybooking.retrofitconfig.HandelCalls
import com.interactive.ksi.propertyturkeybooking.utlitites.DataEnum
import com.openpark.Rfid.views.models.ModelLogin
import com.openpark.Rfid.views.models.ModelNewVisitor
import java.util.HashMap

class ViewModelApp :ViewModel(), HandleRetrofitResp {

    var loginLivedata = MutableLiveData<ModelLogin>()
    var newVisitorLivedata = MutableLiveData<ModelNewVisitor>()


    fun makeLogin(context: Context, meMap: HashMap<String, String?>?){

        HandelCalls.getInstance(context)?.call(DataEnum.login.name, meMap, true, this)

    }

    fun addNewVisitor(context: Context, meMap: HashMap<String, String?>?){

        HandelCalls.getInstance(context)?.call(DataEnum.newVisitor.name, meMap, true, this)

    }


    override fun onResponseSuccess(flag: String?, o: Any?) {
        if(flag==DataEnum.login.name){
            val modelLogin: ModelLogin = o as ModelLogin
            loginLivedata.setValue(modelLogin)

        }else if(flag==DataEnum.newVisitor.name){
            val modelNewVisitor: ModelNewVisitor = o as ModelNewVisitor
            newVisitorLivedata.setValue(modelNewVisitor)

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