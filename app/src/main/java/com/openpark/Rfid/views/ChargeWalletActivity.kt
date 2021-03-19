package com.openpark.Rfid.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.openpark.Rfid.R
import com.openpark.Rfid.databinding.ActivityChargeWalletBinding
import com.openpark.Rfid.views.viewmodel.ViewModelApp
import java.util.HashMap

class ChargeWalletActivity : BaseActivity<ActivityChargeWalletBinding>() {
    private var viewModelApp : ViewModelApp? = null
    private var phone=""
    private var amount=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()
        click()
    }

    private fun click() {

        binding.txtChargeCardNumber.setOnClickListener {

                   visiblecard()
        }

        binding.txtChargePhone.setOnClickListener {
           visiblePhone()
        }
        binding.btnChargeFrid.setOnClickListener {
            amount=binding.amountFrid.text.toString()
            if(amount==""){
                binding.amountFrid.error=getString(R.string.enter_ammount)
            }else{
                chargeWalletFrid("56423344544",amount)
            }

        }
        binding.btnChargePhone.setOnClickListener {
            phone=binding.phoneNumber.text.toString()
            amount=binding.amount.text.toString()
            if(phone==""){
                binding.phoneNumber.error=getString(R.string.enter_phone_number)
            }else if(amount==""){
                binding.amount.error=getString(R.string.enter_ammount)
            }else{
                chargeWallet(phone,amount)
            }

        }
    }

    private fun visiblePhone() {
        binding.phoneNumber.visibility=View.VISIBLE
        binding.amount.visibility=View.VISIBLE
        binding.txtChargeCardNumber.visibility=View.GONE
        binding.or.visibility=View.GONE
        binding.btnChargePhone.visibility=View.VISIBLE
        binding.txtChargePhone.visibility=View.GONE

    }

    private fun visiblecard() {
        binding.cardNumber.visibility=View.VISIBLE
        binding.btnChargeFrid.visibility=View.VISIBLE
        binding.amountFrid.visibility=View.VISIBLE
        binding.or.visibility=View.GONE
        binding.txtChargePhone.visibility=View.GONE
        binding.txtChargeCardNumber.visibility=View.GONE
    }

    private fun chargeWalletFrid(s: String, amount: String) {
        val map = HashMap<String, String?>()
        map["tag_id"] = s
        map["balance"] = amount
        viewModelApp!!.chargeByFrid(this, map)
    }

    private fun chargeWallet(phone: String, amount: String) {
        val map = HashMap<String, String?>()
        map["phone"] = phone
        map["balance"] = amount
        viewModelApp!!.chargeByPhone(this, map)
    }

    private fun initialize() {
        viewModelApp = ViewModelProvider(this).get(ViewModelApp::class.java)
        viewModelApp!!.chargeByPhoneLivedata.observe(this) {


            responseCheck(it.status)

        }

        viewModelApp!!.chargeByFridLivedata.observe(this) {

            responseCheck(it.status)
        }
    }

    private fun responseCheck(status: String) {
        if(status == "1"){
            Toast.makeText(this,getString(R.string.success), Toast.LENGTH_SHORT).show()
            finish()
        }else{

            Toast.makeText(this,getString(R.string.cant_charge), Toast.LENGTH_SHORT).show()
        }
    }
}