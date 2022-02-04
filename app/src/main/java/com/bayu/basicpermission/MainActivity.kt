package com.bayu.basicpermission

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bayu.basicpermission.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val permissionStorageLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(this, "Permission di berikan", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission di tolak", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOpenFile.setOnClickListener {
            openFile()
        }
    }

    private fun openFile() {
        // check apkah permission sudah di berikan
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // jika sudah di berika
            Toast.makeText(this, "already has permission", Toast.LENGTH_SHORT).show()
        } else {
            // jika belum di berikan di cek lagi apakah perlu untuk menampilkan kenapa user harus memberika permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                // jisa harus, tampilkan alasannya
                    // berikan dialog
                Toast.makeText(this, "This app require permission", Toast.LENGTH_SHORT).show()
                requestPermission()
            } else {
                // jik tidak langusng request permission
                requestPermission()
            }
        }
    }

    private fun requestPermission() {
        permissionStorageLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }
}