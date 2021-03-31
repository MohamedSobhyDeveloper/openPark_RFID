package com.openpark.Rfid.views

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.interactive.ksi.propertyturkeybooking.utlitites.HelpMe
import com.interactive.ksi.propertyturkeybooking.utlitites.PrefsUtil
import com.openpark.Rfid.R
import com.openpark.Rfid.databinding.ActivityReportsBinding
import com.openpark.Rfid.views.models.ModelSearchPhone
import com.openpark.Rfid.views.viewmodel.ViewModelApp
import com.softray_solutions.newschoolproject.ui.activities.chart.adapter.ReportsAdapter
import java.util.HashMap

class ReportsActivity : BaseActivity<ActivityReportsBinding>() {
    private var viewModelApp : ViewModelApp? = null
    var cardnumber:TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        click()
        getReports()
    }

    private fun getReports() {
        val map = HashMap<String, String?>()
        map["user_id"] = PrefsUtil.with(this).get("pk","")
        viewModelApp?.getReports(this,map)
    }

    private fun initialize() {
        viewModelApp = ViewModelProvider(this).get(ViewModelApp::class.java)
        viewModelApp!!.reportslivedata.observe(this) {
            val layoutManager = LinearLayoutManager(this)
            binding.recyReports.layoutManager = layoutManager
            val adapter =
                ReportsAdapter(this,it)
            binding.recyReports.adapter = adapter

        }


        viewModelApp!!.supervisorlivedata.observe(this) {
            if(it.pk.equals("-1")){
                Toast.makeText(this,getString(R.string.invalid_pincode),Toast.LENGTH_SHORT).show()

            }else{
                val map = HashMap<String, String?>()
                map["operator_id"] = it.pk
                map["user_id"] = PrefsUtil.with(this).get("pk","")
                viewModelApp!!.redmeWallet(this,map)
            }

        }

        viewModelApp!!.redmelivedata.observe(this) {
            if(it.status.equals("-1")){
                Toast.makeText(this,getString(R.string.cant_redme_wallet),Toast.LENGTH_SHORT).show()

            }else{
                Toast.makeText(this,getString(R.string.redmee_wallet_successfully),Toast.LENGTH_SHORT).show()
                 finish()
            }

        }
    }

    private fun click() {

        binding.btnRedeem.setOnClickListener {
            codeDialog()

        }
    }

    @SuppressLint("SetTextI18n")
    fun codeDialog() {
        val dialogView = Dialog(this)
        dialogView.setContentView(R.layout.dialog_supervisor)
        dialogView.setCanceledOnTouchOutside(true)
        dialogView.setCancelable(true)
        dialogView.window?.setBackgroundDrawableResource(android.R.color.transparent)
        cardnumber = dialogView.findViewById(R.id.cardNumber)
        val pincode = dialogView.findViewById<EditText>(R.id.pinCode)

        val btn_redeem = dialogView.findViewById<Button>(R.id.btn_redeem)


        btn_redeem.setOnClickListener {
            if (cardnumber!!.text.toString().isEmpty()) {
                Toast.makeText(this, getString(R.string.enter_card_number), Toast.LENGTH_SHORT)
                        .show()
            } else if (pincode.text.toString().isEmpty()) {
                pincode.error=getString(R.string.enterpincode)
            } else {
                dialogView.dismiss()
                val map = HashMap<String, String?>()
                map["tag_id"] = cardnumber!!.text.toString()
                map["password"] = pincode.text.toString()

                viewModelApp?.loginSupervisor(this, map)
            }
        }

        dialogView.show()
    }




}