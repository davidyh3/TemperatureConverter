package com.example.temperatureconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var seekBarCelsius : SeekBar
    private lateinit var seekBarFahrenheit : SeekBar
    private lateinit var celsiusValue : TextView
    private lateinit var fahrenheitValue : TextView
    private lateinit var interestingMessage : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekBarCelsius = findViewById(R.id.seekBarCelsius)
        seekBarFahrenheit = findViewById(R.id.seekBarFahrenheit)
        celsiusValue = findViewById(R.id.celsiusValue)
        fahrenheitValue = findViewById(R.id.fahrenheitValue)
        interestingMessage = findViewById(R.id.interestingMessage)

        seekBarCelsius.setOnSeekBarChangeListener(
                object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean) {

                if (fromUser) {
                    val fahrenheit = celsiusToFahrenheit(progress)
                    seekBarFahrenheit.progress = fahrenheit
                    updateMessage(progress)
                }

                celsiusValue.text = String.format("%.2f", progress.toFloat())

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })

        seekBarFahrenheit.setOnSeekBarChangeListener(
                object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean) {

                if (fromUser) {
                    if (progress >= 32) {
                        val celsius = fahrenheitToCelsius(progress)
                        seekBarCelsius.progress = celsius
                        updateMessage(celsius)
                    }
                }

                fahrenheitValue.text = String.format("%.2f", progress.toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Snap back to 32°F if the progress is below 32
                if ((seekBar?.progress ?: 0) < 32) {
                    seekBar?.progress = 32
                    seekBarCelsius.progress = 0 // Update Celsius to 0°C
                    fahrenheitValue.text = "32.00"
                }
            }
        })
    }

    private fun celsiusToFahrenheit(celsius: Int): Int {
        return (celsius * 9/5) + 32
    }

    private fun fahrenheitToCelsius(fahrenheit: Int): Int {
        return (fahrenheit - 32) * 5/9
    }

    private fun updateMessage(celsius: Int) {
        interestingMessage.text =
            if (celsius <= 20) "I wish it were warmer."
            else "I wish it were colder."
    }
}