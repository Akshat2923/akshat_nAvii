package com.example.akshat_navii

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class instructions : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_instructions, container, false)

        val locationText: TextView = view.findViewById(R.id.directionsText)

        // Add ValueEventListener to Firebase reference
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("to_location_text")
        //val myLocation = database.getReference("location")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val location = dataSnapshot.getValue(String::class.java)
                locationText.text = " Directions:\n\n $location"


            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.d(ContentValues.TAG,"Failed to read value.")
            }
        })



        val homeButton: Button = view.findViewById(R.id.homeButton)
        homeButton.setOnClickListener {
            // Navigate back to the destination fragment
            (activity as MainActivity).goBackToFirst()
        }

        return view
    }
}
