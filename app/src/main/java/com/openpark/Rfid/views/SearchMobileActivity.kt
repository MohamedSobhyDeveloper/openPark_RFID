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
            if(search == "" || search.length<10){
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

            if(it.pk == "-1"){

                binding.txtNotFound.visibility=View.VISIBLE
                binding.linData.visibility=View.GONE
                binding.btnAddCard.visibility=View.GONE
                binding.titleplate.visibility=View.GONE
                binding.layoutCar.visibility=View.GONE

            }else {
                binding.txtNotFound.visibility=View.GONE
                binding.linData.visibility=View.VISIBLE
                binding.btnAddCard.visibility=View.VISIBLE
                binding.titleplate.visibility=View.VISIBLE
                binding.layoutCar.visibility=View.VISIBLE

                binding.name.text=getString(R.string.name) +" "+ it.name
                binding.ssn.text=getString(R.string.ssn) +" "+ it.ssn
                binding.driveLicence.text=getString(R.string.driver_licence) +" "+ it.driver_license
                binding.balance.text=getString(R.string.balance) +" "+it.balance
                binding.c1.text=it.chr1
                binding.c2.text=it.chr2
                binding.c3.text=it.chr3
                binding.n1.text=it.num1
                binding.n2.text=it.num2
                binding.n3.text=it.num3
                binding.n4.text=it.num4


            }

        }

    }

}