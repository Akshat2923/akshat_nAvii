package com.example.akshat_navii

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.util.LogPrinter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.database.*

class destination : Fragment() {

    private lateinit var spinner: Spinner
    private lateinit var textView: TextView
    private var selectedLocation: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_destination, container, false)

        spinner = view.findViewById(R.id.spinner)
        val locationText: TextView = view.findViewById(R.id.locationText)
        textView = view.findViewById(R.id.textView)

        // Add ValueEventListener to Firebase reference
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("locationSetTimestamp")
        //val myLocation = database.getReference("location")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val locationRef = database.getReference("location")

                locationRef.get().addOnSuccessListener { dataSnapshot ->
                    val location = dataSnapshot.getValue(String::class.java)
                    locationText.text = "You are at $location"
                    // Now you can use the location value
                }.addOnFailureListener{
                    Log.w(TAG, "Failed to read value.")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.d(TAG,"Failed to read value.")
            }
        })
        val buttonMap: Button = view.findViewById(R.id.buttonMap)
        buttonMap.setOnClickListener {
            // Save the selected location to Firebase Realtime Database
            // Navigate to the next fragment
            (activity as MainActivity).replaceFragment(currentLocation())
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                selectedLocation = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action when nothing is selected
            }
        }

        val buttonGoTime: Button = view.findViewById(R.id.buttonGoTime)
        buttonGoTime.setOnClickListener {
            // Save the selected location to Firebase Realtime Database
            val myRef = database.getReference("selectedLocation")
            myRef.setValue(selectedLocation)

            // Save the current timestamp to a new Firebase Realtime Database variable
            val newTimestampRef = database.getReference("newTimestamp")
            newTimestampRef.setValue(ServerValue.TIMESTAMP)

            // Navigate to the next fragment
            (activity as MainActivity).replaceFragment(instructions())
        }


        return view
    }
}
