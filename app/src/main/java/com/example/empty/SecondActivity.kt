package com.example.empty

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val tvReceivedText: TextView = findViewById(R.id.tvReceivedText)
        val btnBack: Button = findViewById(R.id.btnBack)

        val receivedText = intent.getStringExtra(EXTRA_USER_TEXT)
        if (receivedText.isNullOrBlank()) {
            tvReceivedText.text = getString(R.string.error_empty_input)
        } else {
            tvReceivedText.text = receivedText
        }

        btnBack.setOnClickListener {
            finish()
        }
    }

    companion object {
        private const val EXTRA_USER_TEXT = "extra_user_text"

        fun newIntent(context: Context, text: String): Intent {
            return Intent(context, SecondActivity::class.java).apply {
                putExtra(EXTRA_USER_TEXT, text)
            }
        }
    }
}