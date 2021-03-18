package com.openpark.Rfid.views

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.interactive.ksi.propertyturkeybooking.utlitites.Constant
import com.interactive.ksi.propertyturkeybooking.utlitites.PrefsUtil
import com.openpark.Rfid.R
import com.openpark.Rfid.databinding.ActivityLoginBinding
import com.openpark.Rfid.views.viewmodel.ViewModelApp
import java.util.HashMap

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private var viewModelApp : ViewModelApp? = null
    private var username=""
    private var password=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()
        click()
    }

    private fun initialize() {
        viewModelApp = ViewModelProvider(this).get(ViewModelApp::class.java)
        viewModelApp!!.loginLivedata.observe(this) {
            PrefsUtil.with(this).add(Constant.pk,it.pk).apply()

            if(it.pk.equals("-1")){
                Toast.makeText(this,getString(R.string.invalid_username_password),Toast.LENGTH_SHORT).show()

            }else{
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                Toast.makeText(this,getString(R.string.login),Toast.LENGTH_SHORT).show()
            }


        }

    }

    private fun click() {
        binding.loginbtn.setOnClickListener {
            username=binding.username.text.toString()
            password=binding.password.text.toString()
            if(username == ""){
                binding.username.error = getString(R.string.user_name)
            }else if(password == ""){
                binding.password.error = getString(R.string.password)
            }else{
                makelogin(username,password)
            }

        }

        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    private fun makelogin(username: String, password: String) {
        val map = HashMap<String, String?>()
        map["username"] = username
        map["password"] = password
        viewModelApp!!.makeLogin(this, map)
    }
}