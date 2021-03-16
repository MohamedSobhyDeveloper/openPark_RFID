package com.openpark.Rfid.views

import android.content.Intent
import android.os.Bundle
import com.openpark.Rfid.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        click()
    }

    private fun click() {
        binding.loginbtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}