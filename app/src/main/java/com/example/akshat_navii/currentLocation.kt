package com.example.akshat_navii

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.google.firebase.database.FirebaseDatabase

class currentLocation : Fragment() {
    private var currentFloor: Int = 1
    private var location: String? = null  // Define a global variable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_current_location, container, false)

        // Add ValueEventListener to Firebase reference
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("locationSetTimestamp")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val locationRef = database.getReference("location")

                locationRef.get().addOnSuccessListener { dataSnapshot ->
                    location = dataSnapshot.getValue(String::class.java)  // Update the global variable

                    // Mapping of room names to location data for each floor
                    val roomLocationsFloor1 = mapOf(
                        "floor1_cafe_entrence" to Pair(63, 518),
                        "floor1_high_st_entrence" to Pair(214, 239),
                        "floor1_side_entrence" to Pair(212, 460),
                        "floor1_sloopys" to Pair(292, 523)
                        // Add more rooms and positions for floor 1 as needed
                    )

                    val roomLocationsFloor2 = mapOf(
                        "floor2_alumni_room_in_corner" to Pair(53, 505),
                        "floor2_front_lounge" to Pair(223, 419),
                        "floor2_hallway_learship_service" to Pair(273, 488),
                        "floor2_main_ballroom" to Pair(139, 301)
                        // Add more rooms and positions for floor 2 as needed
                    )

                    val roomLocationsFloor3 = mapOf(
                        "floor3_buckid" to Pair(242, 322),
                        "floor3_door_hall" to Pair(251, 448)
                        // Add more rooms and positions for floor 3 as needed
                    )
                    val floor2 = listOf("floor2_alumni_room_in_corner", "floor2_front_lounge", "floor2_hallway_learship_service", "floor2_main_ballroom")
                    val floor3 = listOf("floor3_buckid", "floor3_door_hall")
                    if (floor2.contains(location)) {
                        currentFloor = 2
                    } else if (floor3.contains(location)) {
                        currentFloor = 3
                    }

                    // Get the current floor's room locations
                    val roomLocations = when (currentFloor) {
                        1 -> roomLocationsFloor1
                        2 -> roomLocationsFloor2
                        3 -> roomLocationsFloor3
                        else -> emptyMap()
                    }

                    // Retrieve the position for the specified room
                    val position = roomLocations[location]

                    val locationPointer = rootView.findViewById<ImageView>(R.id.locationPointer)

                    if (position != null) {



                        // Show the location pointer

                        locationPointer.visibility = View.VISIBLE

                        // Update the position of the location pointer
                        val (x, y) = position
                        val locationPointerParams = locationPointer.layoutParams as ViewGroup.MarginLayoutParams
                        locationPointerParams.marginStart = x
                        locationPointerParams.topMargin = y
                        locationPointer.layoutParams = locationPointerParams
                    }
                }.addOnFailureListener{
                    Log.w(TAG, "Failed to read value.")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG,"Failed to read value.")
            }
        })

        return rootView
    }
}
