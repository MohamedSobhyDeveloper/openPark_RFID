package com.openpark.Rfid.views

import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.viewbinding.ViewBinding
import com.interactive.ksi.propertyturkeybooking.utlitites.PrefsUtil
import com.openpark.Rfid.R
import com.openpark.Rfid.databinding.ActivityBaseBinding
import java.lang.reflect.ParameterizedType
import java.util.*

 open class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    protected val binding by lazy { initBinding() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )
        Log.e("CurrentScreen", this.javaClass.simpleName)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        setuplanguages()

    }


    @Suppress("UNCHECKED_CAST")
    private fun initBinding(): VB {
        val superclass = javaClass.genericSuperclass as ParameterizedType
        val method = (superclass.actualTypeArguments[0] as Class<Any>)
            .getDeclaredMethod("inflate", LayoutInflater::class.java)
        return method.invoke(null, layoutInflater) as VB
    }



    private fun setuplanguages() {
        val language: String = PrefsUtil.with(this).get("language", "")!!
        Log.e("mmmm", language)
        val locale: Locale
        locale = if (language == "" || language == "ar") {
            Locale("ar")
        } else {
            Locale(language)
        }
        Locale.setDefault(locale)
        val configuration = Configuration()
        configuration.locale = locale
        baseContext.resources.updateConfiguration(
            configuration,
            baseContext.resources.displayMetrics
        )
    }
}