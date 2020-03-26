package com.chalkbox.propertyfinder.views

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.lang.Double
import java.math.BigDecimal
import java.text.NumberFormat

class CurrencyTextWatcher : TextWatcher {

    var mEditing: Boolean = false

    init {
        mEditing = false
    }

    @Synchronized
    override fun afterTextChanged(s: Editable) {
        if (!mEditing) {
            mEditing = true

            val digits = s.toString().replace("\\D".toRegex(), "")
            val nf = NumberFormat.getCurrencyInstance()
            try {
                val parsed = BigDecimal(digits).setScale(2, BigDecimal.ROUND_FLOOR)
                    .divide(BigDecimal(100), BigDecimal.ROUND_FLOOR)
                val formatted = NumberFormat.getCurrencyInstance().format(parsed)
                s.replace(0, s.length, formatted)
            } catch (nfe: NumberFormatException) {
                s.clear()
            }

            mEditing = false
        }
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    companion object {
        fun getValue(editText: EditText): Int {
            val digits = editText.text.toString().replace("\\D".toRegex(), "")
            val parsed = BigDecimal(digits).setScale(2, BigDecimal.ROUND_FLOOR)
            return parsed.toInt()
        }
    }
}