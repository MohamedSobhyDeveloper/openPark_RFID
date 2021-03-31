package com.openpark.Rfid.views

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.PendingIntent
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.MifareClassic
import android.nfc.tech.MifareUltralight
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.provider.Settings
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
import com.openpark.Rfid.readcard.NdefMessageParser
import com.openpark.Rfid.readcard.ParsedNdefRecord
import com.openpark.Rfid.views.models.ModelSearchPhone
import com.openpark.Rfid.views.viewmodel.ViewModelApp
import com.softray_solutions.newschoolproject.ui.activities.chart.adapter.ReportsAdapter
import okhttp3.internal.and
import java.util.HashMap

class ReportsActivity : BaseActivity<ActivityReportsBinding>() {
    private var viewModelApp : ViewModelApp? = null
    var cardnumber:TextView?=null
    var nfcAdapter: NfcAdapter? = null
    var pendingIntent: PendingIntent? = null

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

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        if (nfcAdapter == null) {
            Toast.makeText(this, "No NFC", Toast.LENGTH_SHORT).show()
//            finish()
//            return
        }

        pendingIntent = PendingIntent.getActivity(
                this, 0,
                Intent(this, this.javaClass)
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0
        )


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




    override fun onResume() {
        super.onResume()
        if (nfcAdapter != null) {
            if (!nfcAdapter!!.isEnabled) showWirelessSettings()
            nfcAdapter!!.enableForegroundDispatch(this, pendingIntent, null, null)
        }

    }
    //start nfc card

    private fun showWirelessSettings() {
        Toast.makeText(this, "You need to enable NFC", Toast.LENGTH_SHORT).show()
        val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
        startActivity(intent)
    }


    private fun dumpTagData(tag: Tag?): String {
        val sb = StringBuilder()
        val id = tag!!.id
        sb.append("ID (hex): ").append(toHex(id)).append('\n')
        sb.append("ID (reversed hex): ").append(toReversedHex(id)).append('\n')
        sb.append("ID (dec): ").append(toDec(id)).append('\n')
        sb.append("ID (reversed dec): ").append(toReversedDec(id)).append('\n')
        val prefix = "android.nfc.tech."
        sb.append("Technologies: ")
        for (tech in tag.techList) {
            sb.append(tech.substring(prefix.length))
            sb.append(", ")
        }
        sb.delete(sb.length - 2, sb.length)
        for (tech in tag.techList) {
            if (tech == MifareClassic::class.java.name) {
                sb.append('\n')
                var type = "Unknown"
                try {
                    val mifareTag = MifareClassic.get(tag)
                    when (mifareTag.type) {
                        MifareClassic.TYPE_CLASSIC -> type = "Classic"
                        MifareClassic.TYPE_PLUS -> type = "Plus"
                        MifareClassic.TYPE_PRO -> type = "Pro"
                    }
                    sb.append("Mifare Classic type: ")
                    sb.append(type)
                    sb.append('\n')
                    sb.append("Mifare size: ")
                    sb.append(mifareTag.size.toString() + " bytes")
                    sb.append('\n')
                    sb.append("Mifare sectors: ")
                    sb.append(mifareTag.sectorCount)
                    sb.append('\n')
                    sb.append("Mifare blocks: ")
                    sb.append(mifareTag.blockCount)
                } catch (e: Exception) {
                    sb.append("Mifare classic error: " + e.message)
                }
            }
            if (tech == MifareUltralight::class.java.name) {
                sb.append('\n')
                val mifareUlTag = MifareUltralight.get(tag)
                var type = "Unknown"
                when (mifareUlTag.type) {
                    MifareUltralight.TYPE_ULTRALIGHT -> type = "Ultralight"
                    MifareUltralight.TYPE_ULTRALIGHT_C -> type = "Ultralight C"
                }
                sb.append("Mifare Ultralight type: ")
                sb.append(type)
            }
        }
        return sb.toString()
    }


    private fun toHex(bytes: ByteArray): String? {
        val sb = StringBuilder()
        for (i in bytes.indices.reversed()) {
            val b: Int = bytes[i] and 0xff
            if (b < 0x10) sb.append('0')
            sb.append(Integer.toHexString(b))
            if (i > 0) {
                sb.append(" ")
            }
        }
        return sb.toString()
    }


    private fun toReversedHex(bytes: ByteArray): String? {
        val sb = StringBuilder()
        for (i in bytes.indices) {
            if (i > 0) {
                sb.append(" ")
            }
            val b: Int = bytes[i] and 0xff
            if (b < 0x10) sb.append('0')
            sb.append(Integer.toHexString(b))
        }
        return sb.toString()
    }

    private fun toDec(bytes: ByteArray): Long {
        var result: Long = 0
        var factor: Long = 1
        for (i in bytes.indices) {
            val value: Long = (bytes[i] and 0xff).toLong()
            result += value * factor
            factor *= 256L
        }
        return result
    }

    private fun toReversedDec(bytes: ByteArray): Long {
        var result: Long = 0
        var factor: Long = 1
        for (i in bytes.indices.reversed()) {
            val value: Long = (bytes[i] and 0xff).toLong()
            result += value * factor
            factor *= 256L
        }
        return result
    }

    @SuppressLint("MissingSuperCall")
    override fun onNewIntent(intent: Intent) {
        setIntent(intent)
        resolveIntent(intent)
    }

    private fun resolveIntent(intent: Intent) {
        val action = intent.action
        if (NfcAdapter.ACTION_TAG_DISCOVERED == action || NfcAdapter.ACTION_TECH_DISCOVERED == action || NfcAdapter.ACTION_NDEF_DISCOVERED == action) {
            val rawMsgs =
                intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            val msgs: Array<NdefMessage?>
            if (rawMsgs != null) {
                msgs = arrayOfNulls(rawMsgs.size)
                for (i in rawMsgs.indices) {
                    msgs[i] = rawMsgs[i] as NdefMessage
                }
            } else {
                val empty = ByteArray(0)
                val id = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID)
                val tag =
                    intent.getParcelableExtra<Parcelable>(NfcAdapter.EXTRA_TAG) as Tag?
                val payload = dumpTagData(tag).toByteArray()
                val record = NdefRecord(NdefRecord.TNF_UNKNOWN, empty, id, payload)
                val msg = NdefMessage(arrayOf(record))
                msgs = arrayOf(msg)
            }
            displayMsgs(msgs)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun displayMsgs(msgs: Array<NdefMessage?>?) {
        if (msgs == null || msgs.size == 0) return
        val builder = StringBuilder()
        val records: List<ParsedNdefRecord> =
            NdefMessageParser.parse(msgs[0])
        val size = records.size
        var str: String? = null
        for (i in 0 until size) {
            val record: ParsedNdefRecord = records[i]
            str = record.str()
            builder.append(str).append("\n")
        }

//        ParsedNdefRecord record = records.get(2);
//        String str = record.str();
//        str=str.replace(" ","");
//        str=str.replace("ID(dec):","");
//
//        ParsedNdefRecord record2 = records.get(3);
//        String str2 = record2.str();
//        str2=str2.replace(" ","");
//        str2=str2.replace("ID(reversed dec):","");
//
//        String memberId=str+str2;
//        memberId=memberId.replace(" ","");
        val separated = str!!.split(":".toRegex()).toTypedArray()
        var id1 = separated[3]
        id1 = id1.replace(" ", "")
        id1 = id1.replace("ID(reverseddec)", "")
        var id2 = separated[4]
        id2 = id2.replace(" ", "")
        id2 = id2.replace("Technologies", "")
        var cardId = id1 + id2
        cardId = cardId.replace("\n".toRegex(), "")
        if (cardId != "") {
            if (cardnumber!=null){
                cardnumber?.text=cardId

            }
        } else {
            Toast.makeText(this, getString(R.string.cant_read_card), Toast.LENGTH_SHORT).show()
        }


//        text.setText(memberId);
    }



}