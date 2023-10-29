package com.example.akshat_navii

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.*

class homescreen : Fragment() {

    private val startActivityForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageBitmap = result.data?.extras?.get("data") as Bitmap

            // Convert the Bitmap to a ByteArray
            val stream = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            val byteArray: ByteArray = stream.toByteArray()

            // Upload the ByteArray to Firebase Storage
            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("images/${UUID.randomUUID()}")
            val uploadTask = imageRef.putBytes(byteArray)

            // Get the Download URL and add it to Realtime Database
            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                imageRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    Log.e("TEST", downloadUri.toString())
                    // Add the download URL to Realtime Database under 'image' node.
                    val databaseRef = FirebaseDatabase.getInstance("https://navii-fc6c9-default-rtdb.firebaseio.com/").getReference("image")
                    databaseRef.setValue(downloadUri.toString())

                } else {
                    // Handle failures
                }
            }

            (activity as MainActivity).replaceFragment(destination())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_homescreen, container, false)

        val button: ImageButton = view.findViewById(R.id.button2)
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
