package com.example.vineria.util

import com.google.firebase.crashlytics.FirebaseCrashlytics
import jakarta.mail.*
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeBodyPart
import jakarta.mail.internet.MimeMessage
import jakarta.mail.internet.MimeMultipart
import java.io.File
import java.io.IOException
import java.util.*


object GMailSender {
    // permitir app poco seguras en https://myaccount.google.com/lesssecureapps?pli=1&rapt=AEjHL4PmV4GehNg5rR3HDV02-8lePkrmwy9DYFwwEFzuQFeyRHgpVU0qb8WkRcqKjYXtb7M8BneKo9mcZsN7xOftefvZYsFO4w
    fun sendMail(
        from: String,
        to: String?,
        password: String,
        subject: String?,
        body: String?,
        attachFile: File?
    ): Boolean {
        // Assuming you are sending email from through gmails smtp
        val host = "smtp.gmail.com"
        val properties = getProperties(host)
        val session = getSession(from, password, properties)
        try {
            val message = MimeMessage(session)
            message.setFrom(InternetAddress(from))
            message.addRecipient(Message.RecipientType.TO, InternetAddress(to))
            message.subject = subject
            val multipart: Multipart = MimeMultipart()
            val attachmentPart = MimeBodyPart()
            val textPart = MimeBodyPart()
            try {
                attachmentPart.attachFile(attachFile)
                textPart.setText(body)
                multipart.addBodyPart(textPart)
                multipart.addBodyPart(attachmentPart)
            } catch (e: IOException) {
                e.printStackTrace()
                return false
            }
            message.setContent(multipart)
            // Send message
            Transport.send(message)
        } catch (mex: MessagingException) {
            FirebaseCrashlytics.getInstance().recordException(mex)
            return false
        }
        return true
    }

    private fun getProperties(host: String): Properties {
        // Get system properties
        val properties = System.getProperties()
        // Setup mail server
        properties["mail.smtp.host"] = host
        properties["mail.smtp.port"] = "465"
        properties["mail.smtp.ssl.enable"] = "true"
        properties["mail.smtp.auth"] = "true"
        return properties
    }

    private fun getSession(from: String, password: String, properties: Properties): Session {
        return Session.getDefaultInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(from, password)
            }
        })
    }
}