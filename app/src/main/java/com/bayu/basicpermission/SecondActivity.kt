package com.bayu.basicpermission

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bayu.basicpermission.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        val message = if (isGranted) "Aplikasi Sudah di berikan akses" else "Akses camera tidak di berikan"
        showMessage(message)

        if (isGranted) {
            Toast.makeText(this, "Open Camera", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (checkSelfPermissionCamera()) {
            showMessage("Aplikasi Sudah di berikan akses")
        } else {
            showMessage("Aplikasi tidak di berikan akses")
        }

        binding.btnOpenCamera.setOnClickListener {
            checkAppHasPermissionCamera()
        }
    }

    private fun checkAppHasPermissionCamera() {
        if (checkSelfPermissionCamera()) {
            Toast.makeText(this, "Open Camera", Toast.LENGTH_SHORT).show()
        } else {
            checkShouldShowRetionale()
        }
    }

    private fun checkShouldShowRetionale() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            createDialog()
        } else {
            requestPermissionCamera()
        }
    }

    private fun createDialog() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Perhatian!!")
            .setMessage("Untuk menggunakan aplikasi ini\nkami butuh permission anda.\nKarena aplikasi kami nantinya akan melakukan memfoto ktp anda untuk registrasi\nterimakasih")
            .setPositiveButton("Ya") { dialog, _ ->
                dialog.dismiss()
                requestPermissionCamera()
            }
            .setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialog.show()
    }

    private fun requestPermissionCamera() {
        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    private fun checkSelfPermissionCamera(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun showMessage(message: String) {
        binding.tvResult.text = message
    }
}