package com.openpark.Rfid.views
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.openpark.Rfid.R
import com.openpark.Rfid.databinding.ActivitySearchMobileBinding
import com.openpark.Rfid.views.viewmodel.ViewModelApp
import java.util.HashMap

class SearchMobileActivity :  BaseActivity<ActivitySearchMobileBinding>() {
    private var viewModelApp : ViewModelApp? = null
    private var search=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()
        click()
    }

    private fun click() {

        binding.btnSearch.setOnClickListener {
            search=binding.phoneNumber.text.toString()
            if(search == ""){
                binding.phoneNumber.error=getString(R.string.enter_phone_number)
            }else{
                makeSearch(search)
            }
        }


    }

    private fun makeSearch(search: String) {

        val map = HashMap<String, String?>()
        map["mobile_no"] = search
        viewModelApp!!.makeSearchPhone(this, map)
    }

    @SuppressLint("SetTextI18n")
    private fun initialize() {
        viewModelApp = ViewModelProvider(this).get(ViewModelApp::class.java)
        viewModelApp!!.searchphoneLivedata.observe(this) {

            if(it.pk.equals("-1")){

                binding.txtNotFound.visibility=View.VISIBLE

            }else if(it.name!=null){
                binding.name.text="Name: " + it.name
                binding.ssn.text="SSN: " + it.ssn
                binding.driveLicence.text="driveLicence: " + it.driver_license
                binding.balance.text="balance: " + it.balance
                binding.linData.visibility=View.VISIBLE
            }else{
                binding.txtNotFound.visibility=View.VISIBLE
            }


        }

    }

}