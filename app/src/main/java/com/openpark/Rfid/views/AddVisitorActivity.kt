package com.openpark.Rfid.views

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.interactive.ksi.propertyturkeybooking.utlitites.Constant
import com.interactive.ksi.propertyturkeybooking.utlitites.PrefsUtil
import com.openpark.Rfid.R
import com.openpark.Rfid.databinding.ActivityAddVisitorBinding
import com.openpark.Rfid.views.models.ModelAddVisitor
import com.openpark.Rfid.views.models.ModelSendWalletFrid
import com.openpark.Rfid.views.viewmodel.ViewModelApp
import java.util.HashMap

class AddVisitorActivity : BaseActivity<ActivityAddVisitorBinding>() {
    private var viewModelApp : ViewModelApp? = null

    private var username=""
    private var phone=""
    private var tagNumber=""
    private var ssn=""
    private var drivingLicence=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        click()
    }

    private fun click() {
        binding.btnAdd.setOnClickListener {

            assimentVariable()
            if(username == ""){
                binding.username.error=getString(R.string.user_name)
            }else if(phone == ""){
                binding.phoneNumber.error=getString(R.string.enter_phone_number)
            }else if(ssn == ""){
                binding.ssn.error=getString(R.string.enter_ssn)
            }else if(drivingLicence == ""){
                binding.driveLicence.error=getString(R.string.enter_car_number)
            }else{
                addNewVisitor(username,phone,"454578787",ssn,drivingLicence)
            }
        }
        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    private fun addNewVisitor(username: String, phone: String, s: String, ssn: String, drivingLicence: String) {
        val map = HashMap<String, String?>()
        map["username"] = username
        map["phone"] = phone
        map["tag_id"] = s
        map["ssn"] = ssn
        map["driver_license"] = drivingLicence
        val model= ModelAddVisitor(username,phone,s,ssn,drivingLicence)
        viewModelApp!!.addNewVisitor(this, model)
    }

    private fun assimentVariable() {
        username=binding.username.text.toString()
        phone=binding.phoneNumber.text.toString()
        tagNumber=binding.cardNumber.text.toString()
        ssn=binding.ssn.text.toString()
        drivingLicence=binding.driveLicence.text.toString()
    }

    private fun initialize() {
        viewModelApp = ViewModelProvider(this).get(ViewModelApp::class.java)
        viewModelApp!!.newVisitorLivedata.observe(this) {

            if(it.status == "-1"){
                Toast.makeText(this,"Can't add new visitor", Toast.LENGTH_SHORT).show()

            }else{
                Toast.makeText(this,"New Visitor added Successfully", Toast.LENGTH_SHORT).show()
                finish()
            }


        }

    }
}