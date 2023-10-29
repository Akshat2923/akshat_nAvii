package com.example.akshat_navii

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts


class homescreen : Fragment() {



private val startActivityForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result: ActivityResult ->
    if (result.resultCode == Activity.RESULT_OK) {
        val imageBitmap = result.data?.extras?.get("data") as Bitmap
    }
}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_homescreen, container, false)

        val button: ImageButton = view.findViewById(R.id.button)
        button.setOnClickListener {
            // Code to open the camera
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult.launch(intent)
        }

        return view
    }

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
    }
    }




