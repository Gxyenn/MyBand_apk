package com.mybanyou.Gxyenn

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

object MessageBuilder {
    fun buildMessage(ctx: Context, region: String, number: String): String {
        val locale = ctx.resources.configuration.locales.get(0)
        val tanggal = getCurrentDate(locale)
        val reporterName = "Dimas Hidayat"
        val header = ctx.getString(R.string.template_header)
        val body = ctx.getString(R.string.template_body, number, tanggal, number, reporterName, number)
        return header + "\n" + body
    }

    private fun getCurrentDate(locale: Locale): String {
        val sdf = SimpleDateFormat("EEEE, dd MMMM yyyy", locale)
        return sdf.format(Date())
    }
}