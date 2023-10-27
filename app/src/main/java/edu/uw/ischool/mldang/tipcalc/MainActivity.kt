package edu.uw.ischool.mldang.tipcalc

import android.icu.text.DecimalFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener

class MainActivity : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)
        editText = findViewById(R.id.editText)

        button.isEnabled = false
        button.isClickable = false

        button.setOnClickListener {
            calculateTips()
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                editText.removeTextChangedListener(this)
                val service = changeFormat(s.toString())
                button.isEnabled = true
                button.isClickable = true
                editText.setText("$$service")
                editText.setSelection(editText.length())
                editText.addTextChangedListener(this)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun changeFormat(s: String): String {
        val text = s.replace("""[$,.]""".toRegex(), "")
        val value = if (text.isNotEmpty() || text !== "") text.toDouble() else 0.0
        val doubleValue = value / 100.0
        return formatToTwoDecimalPlaces(doubleValue)
    }

    private fun calculateTips() {
        val charge = changeFormat(editText.text.toString())
        val total = formatToTwoDecimalPlaces(charge.toDouble() * 0.15)
        val duration = Toast.LENGTH_SHORT

        val toast = Toast.makeText(this, "$$total", duration)
        toast.show()
    }

    private fun formatToTwoDecimalPlaces(value: Double): String {
        val decimalFormat = DecimalFormat("#0.00")
        return decimalFormat.format(value)
    }
}