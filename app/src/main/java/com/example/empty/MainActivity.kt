package com.example.empty

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etInput: EditText
    private lateinit var btnOpenSecond: Button
    private lateinit var btnCallFriend: Button
    private lateinit var btnShareText: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etInput = findViewById(R.id.etInput)
        btnOpenSecond = findViewById(R.id.btnOpenSecond)
        btnCallFriend = findViewById(R.id.btnCallFriend)
        btnShareText = findViewById(R.id.btnShareText)

        savedInstanceState?.getString(KEY_INPUT_TEXT)?.let {
            etInput.setText(it)
        }

        btnOpenSecond.setOnClickListener {
            val text = getValidatedText() ?: return@setOnClickListener
            val intent = SecondActivity.newIntent(this, text)
            startActivity(intent)
        }

        btnCallFriend.setOnClickListener {
            val phone = getValidatedPhone() ?: return@setOnClickListener
            val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phone")
            }
            safeStartActivity(dialIntent)
        }

        btnShareText.setOnClickListener {
            val text = getValidatedText() ?: return@setOnClickListener
            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, text)
            }
            val chooserIntent = Intent.createChooser(
                sendIntent,
                getString(R.string.share_chooser_title)
            )
            safeStartActivity(chooserIntent)
        }
    }

    private fun getValidatedText(): String? {
        val rawText = etInput.text.toString().trim()
        if (rawText.isEmpty()) {
            etInput.error = getString(R.string.error_empty_input)
            etInput.requestFocus()
            return null
        }
        etInput.error = null
        return rawText
    }

    private fun getValidatedPhone(): String? {
        val rawPhone = getValidatedText() ?: return null
        val digitCount = rawPhone.count { it.isDigit() }
        if (!Patterns.PHONE.matcher(rawPhone).matches() || digitCount < 3) {
            etInput.error = getString(R.string.error_invalid_phone)
            etInput.requestFocus()
            return null
        }
        etInput.error = null
        return rawPhone
    }

    private fun safeStartActivity(intent: Intent) {
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, getString(R.string.error_no_app), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_INPUT_TEXT, etInput.text.toString())
    }

    companion object {
        private const val KEY_INPUT_TEXT = "key_input_text"
    }
}