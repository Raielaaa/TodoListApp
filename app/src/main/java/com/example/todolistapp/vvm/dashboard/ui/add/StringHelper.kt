package com.example.todolistapp.vvm.dashboard.ui.add

import android.annotation.SuppressLint
import android.util.Log
import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale


object StringHelper {
    private const val TAG = "StringHelper"
    fun capWords(value: String): String {
        val word = StringBuilder()
        val splitString = value.split(" ".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        for ((index, `val`) in splitString.withIndex()) {
            val strBuilder = word.append(capitalize(`val`))
            if (index < splitString.size - 1) strBuilder.append(" ")
        }
        return word.toString()
    }

    private fun capitalize(value: String): String {
        return value.substring(0, 1).uppercase(Locale.getDefault()) +
                value.substring(1).lowercase(Locale.getDefault())
    }

    private fun convertToHex(data: ByteArray): String {
        val buf = StringBuilder()
        for (b in data) {
            var halfbyte = b.toInt() ushr 4 and 0x0F
            var twoHalf = 0
            do {
                buf.append(if ((0 <= halfbyte) && halfbyte <= 9) ('0'.code + halfbyte).toChar() else ('a'.code + (halfbyte - 10)).toChar())
                halfbyte = b.toInt() and 0x0F
            } while (twoHalf++ < 1)
        }
        return buf.toString()
    }

    @Throws(NoSuchAlgorithmException::class, UnsupportedEncodingException::class)
    fun converToSHA512(text: String): String {
        val md = MessageDigest.getInstance("SHA-512")
        md.update(text.toByteArray(charset("iso-8859-1")), 0, text.length)
        val hash = md.digest()
        return convertToHex(hash)
    }

    ///just click alt enter for quick import library
    @SuppressLint("SimpleDateFormat")
    fun parseDate(inputFormat: String?, outputFormat: String?, date: String?): String {
        var output = ""
        val inFormat = SimpleDateFormat(inputFormat)
        val outFormat = SimpleDateFormat(outputFormat)
        try {
            val d = inFormat.parse(date)
            output = outFormat.format(d)
        } catch (e: ParseException) {
            Log.e(TAG, e.message!!)
        }
        return output
    }
}