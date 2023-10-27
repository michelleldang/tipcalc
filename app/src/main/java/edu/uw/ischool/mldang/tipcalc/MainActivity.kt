package edu.uw.ischool.mldang.tipcalc

import android.icu.text.DecimalFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val editText = findViewById<EditText>(R.id.editText)
        button.isEnabled = false
        button.isClickable = false

        button.setOnClickListener {
            changeFormat()
            calculateTips()
        }

        editText.addTextChangedListener {
            button.isEnabled = true
            button.isClickable = true
        }
    }
    private  fun changeFormat() {
        val editText = findViewById<EditText>(R.id.editText)
        var text = editText.text.toString()
        val charge = if (text.isNotEmpty()) {
            if (text.startsWith("$")) {
                text = text.drop(1)
            }
            text.toDouble()
        } else 0.0
        editText.setText("$${formatToTwoDecimalPlaces(charge)}")

    }
        private fun calculateTips() {
            val editText = findViewById<EditText>(R.id.editText)
            val charge = if (editText.text.toString() == "$") 0.0 else editText.text.toString().drop(1).toDouble()
            val total = formatToTwoDecimalPlaces(charge * .15)
            val duration = Toast.LENGTH_SHORT

            val toast = Toast.makeText(this, "$$total", duration)
            toast.show()
        }
    private fun formatToTwoDecimalPlaces(value: Double): String {
        val decimalFormat = DecimalFormat("#0.00")
        return decimalFormat.format(value)
    }

}