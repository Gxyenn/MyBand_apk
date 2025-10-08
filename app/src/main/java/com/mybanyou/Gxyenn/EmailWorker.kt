package com.xnxxband.Gxyenn

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import jakarta.mail.Authenticator
import jakarta.mail.PasswordAuthentication
import jakarta.mail.Session
import jakarta.mail.Transport
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Properties

class EmailWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {
    override suspend fun doWork() = withContext(Dispatchers.IO) {
        try {
            val to = inputData.getString("to_email") ?: return@withContext Result.failure()
            val subject = inputData.getString("subject") ?: "(no subject)"
            val body = inputData.getString("body") ?: ""

            val props = Properties().apply {
                put("mail.smtp.auth", "true")
                put("mail.smtp.starttls.enable", "true")
                put("mail.smtp.host", BuildConfig.SMTP_HOST)
                put("mail.smtp.port", BuildConfig.SMTP_PORT.toString())
            }

            val session = Session.getInstance(props, object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(BuildConfig.SMTP_USER, BuildConfig.SMTP_PASS)
                }
            })

            val message = MimeMessage(session).apply {
                setFrom(InternetAddress(BuildConfig.SMTP_USER))
                setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(to))
                setSubject(subject)
                setText(body)
            }

            Transport.send(message)
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }
}