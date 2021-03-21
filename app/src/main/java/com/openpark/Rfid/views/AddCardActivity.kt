package com.openpark.Rfid.views

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.openpark.Rfid.R
import com.openpark.Rfid.databinding.ActivityAddCardBinding
import com.openpark.Rfid.views.models.ModelLinkFrid
import com.openpark.Rfid.views.models.ModelSendWalletFrid
import com.openpark.Rfid.views.viewmodel.ViewModelApp

class AddCardActivity :  BaseActivity<ActivityAddCardBinding>() {
    private var viewModelApp : ViewModelApp? = null
    private var phone=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()
        click()
    }

    private fun click() {
        val intent = intent
        phone= intent.getStringExtra("phone").toString()

        binding.btnAddCard.setOnClickListener {
            addCard("56423344544",phone)
        }

        binding.backBtn.setOnClickListener {
            val intent = Intent(this, SearchMobileActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun addCard(s: String, phone: String) {

        val model= ModelLinkFrid(s,phone)
        viewModelApp!!.linkFridCard(this, model)
    }

    private fun initialize() {
        viewModelApp = ViewModelProvider(this).get(ViewModelApp::class.java)
        viewModelApp!!.linkFridLivedata.observe(this) {

            if(it.status=="-1"){
                Toast.makeText(this,getString(R.string.cant_add_card_new),Toast.LENGTH_SHORT).show()
            }else{

                Toast.makeText(this,getString(R.string.added_sucess),Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }


        }

    }

}