package com.neer.app

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    private lateinit var status: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(32, 32, 32, 32)
        }

        val title = TextView(this).apply {
            text = "Neer"
            textSize = 36f
            gravity = Gravity.CENTER
        }

        status = TextView(this).apply {
            text = "Yakınındaki insanlarla tanış."
            textSize = 16f
            gravity = Gravity.CENTER
            setPadding(0, 24, 0, 24)
        }

        val locationButton = Button(this).apply {
            text = "Konum iznini etkinleştir"
            setOnClickListener {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED
                ) {
                    status.text = "Konum izni verildi. Kullanıcı keşfi henüz kurulmadı."
                } else {
                    requestPermissions(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ),
                        100
                    )
                }
            }
        }

        layout.addView(title)
        layout.addView(status)
        layout.addView(locationButton)
        setContentView(layout)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            status.text = if (grantResults.any {
                    it == PackageManager.PERMISSION_GRANTED
                }) {
                "Konum izni verildi. Kullanıcı keşfi henüz kurulmadı."
            } else {
                "Konum izni verilmedi."
            }
        }
    }
}
