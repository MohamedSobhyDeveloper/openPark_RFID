package com.openpark.Rfid.views
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
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
       // txt_watcher()
    }
    private fun txt_watcher() {
        binding.phoneNumber.addTextChangedListener(object : android.text.TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    if(s.length==11) {
                        makeSearch(s.toString())
                    }
                }
            }
        })
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
        binding.btnAddCard.setOnClickListener {
            val intent = Intent(this, AddCardActivity::class.java)
            intent.putExtra("phone",search)
            startActivity(intent)
            finish()
        }

        binding.backBtn.setOnClickListener {
           finish()
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
                binding.linData.visibility=View.GONE
                binding.btnAddCard.visibility=View.GONE

            }else {
                binding.name.text="Name: " + it.name
                binding.ssn.text="SSN: " + it.ssn
                binding.driveLicence.text="driveLicence: " + it.driver_license
                binding.balance.text="balance: " + it.balance
                binding.linData.visibility=View.VISIBLE
                binding.txtNotFound.visibility=View.GONE
                binding.btnAddCard.visibility=View.VISIBLE
            }

        }

    }

}