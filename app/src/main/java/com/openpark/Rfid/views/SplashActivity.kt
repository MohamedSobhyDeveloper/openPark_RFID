package com.openpark.Rfid.views

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import com.interactive.ksi.propertyturkeybooking.utlitites.PrefsUtil
import com.openpark.Rfid.MainActivity
import com.openpark.Rfid.R
import com.openpark.Rfid.databinding.ActivitySplashBinding

class SplashActivity :BaseActivity<ActivitySplashBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed({

            if (PrefsUtil.with(this).get("id","-1").equals("-1")){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else{
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }


        }, 2000)

    }
}