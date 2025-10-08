package com.xnxxband.Gxyenn

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val regions = listOf("+62 (Indonesia)", "+1 (USA)", "+44 (UK)", "+91 (India)")
        spinnerRegion.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, regions)

        inputNumber.imeOptions = EditorInfo.IME_FLAG_NO_EXTRACT_UI

        inputNumber.addTextChangedListener { s ->
            val region = spinnerRegion.selectedItem.toString()
            previewText.text = MessageBuilder.buildMessage(this, region, s.toString())
        }

        btnSend.setOnClickListener {
            val region = spinnerRegion.selectedItem.toString()
            val number = inputNumber.text.toString().trim()
            if (number.isEmpty()) {
                status.text = "Masukkan nomor terlebih dahulu!"
                return@setOnClickListener
            }

            // Show animation and status
            lottieLoading.visibility = View.VISIBLE
            (lottieLoading as LottieAnimationView).playAnimation()
            status.text = getString(R.string.status_sending)

            val message = MessageBuilder.buildMessage(this, region, number)
            val data = Data.Builder()
                .putString("to_email", "support@whatsapp.com")
                .putString("subject", "Laporan Nomor Tidak Pantas - $number")
                .putString("body", message)
                .build()

            val sendWork = OneTimeWorkRequestBuilder<EmailWorker>()
                .setInputData(data)
                .build()

            WorkManager.getInstance(this).enqueue(sendWork)
        }
    }
}