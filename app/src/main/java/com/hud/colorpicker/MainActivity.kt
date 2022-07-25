package com.hud.colorpicker

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColor
import com.hud.colorpicker.databinding.ActivityMainBinding
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import com.skydoves.colorpickerview.listeners.ColorListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var selectedView: View

    private lateinit var prefs: SharedPreferences
    //Mark:Constants
    private val COLOR_ONE = "COLOR_ONE"
    private val COLOR_TWO = "COLOR_TWO"
    private val COLOR_THREE = "COLOR_THREE"

    private val DEF_COLOR_1 = "#00c2a3"
    private val DEF_COLOR_2 = "#4ba54f"
    private val DEF_COLOR_3 = "#ff6100"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        buildSharedPreference()
        setupColorView()
        selectedView = binding.btn1
        loadColors()
        setEventListeners()
    }

    private fun setupColorView() {
        binding.colorPicker.attachAlphaSlider(binding.alphaSlider)
    }

    private fun setEventListeners() {
        binding.btn1.setOnClickListener { v ->
            selectedView = binding.btn1
            loadColors()
        }
        binding.btn2.setOnClickListener { v ->
            selectedView = binding.btn2
            loadColors()
        }
        binding.btn3.setOnClickListener { v ->
            selectedView = binding.btn3
            loadColors()
        }

        binding.colorPicker.colorListener = ColorEnvelopeListener { envelope, fromUser ->
                saveColor(envelope)
            }
    }

    private fun saveColor(color: ColorEnvelope) {
        val hexColor = "#${color.hexCode}"
        Log.e("hexColor", hexColor)
        when(selectedView) {
            binding.btn1 -> {
                setString(COLOR_ONE, hexColor)
                binding.btn1.setPaintColor(Color.parseColor(getString(COLOR_ONE, DEF_COLOR_1)))
            }
            binding.btn2 -> {
                setString(COLOR_TWO, hexColor)
                binding.btn2.setPaintColor(Color.parseColor(getString(COLOR_TWO, DEF_COLOR_2)))
            }
            binding.btn3 -> {
                setString(COLOR_THREE, hexColor)
                binding.btn3.setPaintColor(Color.parseColor(getString(COLOR_THREE, DEF_COLOR_3)))
            }
        }
    }

    private fun loadColors() {
        binding.btn1.setPaintColor(Color.parseColor(getString(COLOR_ONE, DEF_COLOR_1)))
        binding.btn2.setPaintColor(Color.parseColor(getString(COLOR_TWO, DEF_COLOR_2)))
        binding.btn3.setPaintColor(Color.parseColor(getString(COLOR_THREE, DEF_COLOR_3)))

        val selectedColors = when(selectedView) {
            binding.btn1 -> getString(COLOR_ONE, DEF_COLOR_1)
            binding.btn2 -> getString(COLOR_TWO, DEF_COLOR_2)
            binding.btn3 -> getString(COLOR_THREE, DEF_COLOR_3)
            else -> "#000000"
        }

        binding.colorPicker.setInitialColor(Color.parseColor(selectedColors))
    }

    private fun buildSharedPreference() {
        prefs = baseContext.getSharedPreferences(baseContext.packageName, Context.MODE_PRIVATE)
    }

    private fun getString(key: String, default: String): String = prefs.getString(key, default) ?: default

    private fun setString(key: String, value: String) {
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.apply()
    }
}