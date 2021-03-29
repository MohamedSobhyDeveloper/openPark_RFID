package com.openpark.Rfid.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.interactive.ksi.propertyturkeybooking.utlitites.PrefsUtil
import com.openpark.Rfid.R
import com.openpark.Rfid.databinding.ActivityReportsBinding
import com.openpark.Rfid.views.viewmodel.ViewModelApp
import com.softray_solutions.newschoolproject.ui.activities.chart.adapter.ReportsAdapter
import java.util.HashMap

class ReportsActivity : BaseActivity<ActivityReportsBinding>() {
    private var viewModelApp : ViewModelApp? = null

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
    }

    private fun click() {
    }
}