package com.example.akshat_navii

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class instructions : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_instructions, container, false)

        val homeButton: Button = view.findViewById(R.id.homeButton)
        homeButton.setOnClickListener {
            // Navigate back to the destination fragment
            (activity as MainActivity).goBackToFirst()
        }

        return view
    }
}
