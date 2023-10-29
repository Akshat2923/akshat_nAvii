package com.example.akshat_navii

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class currentLocation : Fragment() {
    private var currentFloor: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_current_location, container, false)

        // Get the room name from your data source (e.g., from the database)

        val roomName = "woodys"  // Replace with the actual room name

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
            "floor2_hallway_leadership_service" to Pair(273, 488),
            "floor2_main_ballroom" to Pair(139, 301)
            // Add more rooms and positions for floor 2 as needed
        )

        val roomLocationsFloor3 = mapOf(
            "floor3_buckid" to Pair(242, 322),
            "floor3_door_hall" to Pair(251, 448)
            // Add more rooms and positions for floor 3 as needed
        )

        // Get the current floor's room locations
        val roomLocations = when (currentFloor) {
            1 -> roomLocationsFloor1
            2 -> roomLocationsFloor2
            3 -> roomLocationsFloor3
            else -> emptyMap()
        }

        // Retrieve the position for the specified room
        val position = roomLocations[roomName]

        if (position != null) {
            // Show the location pointer
            val locationPointer = rootView.findViewById<ImageView>(R.id.locationPointer)
            locationPointer.visibility = View.VISIBLE

            // Update the position of the location pointer
            val (x, y) = position
            val locationPointerParams = locationPointer.layoutParams as ViewGroup.MarginLayoutParams
            locationPointerParams.marginStart = x
            locationPointerParams.topMargin = y
            locationPointer.layoutParams = locationPointerParams
        }

        return rootView
    }

    // Method to switch to a different floor
    fun switchFloor(newFloor: Int) {
        currentFloor = newFloor
        // Update the UI to display the new floor's map
        // You may need to hide/show the relevant ImageView elements based on the current floor
    }
}
