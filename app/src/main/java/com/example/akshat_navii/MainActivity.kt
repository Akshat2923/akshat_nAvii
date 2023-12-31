package com.example.akshat_navii

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.akshat_navii.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button1.setOnClickListener{
            replaceFragment(homescreen())
        }

    }

    public fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainLayout, fragment)
        fragmentTransaction.addToBackStack(null)

        fragmentTransaction.commit()
    }
    public fun goBackToFirst() {
        val fragmentManager = supportFragmentManager
        // Clear the back stack
        for (i in 0 until fragmentManager.backStackEntryCount) {
            fragmentManager.popBackStack()
        }
    }
    public fun goBack() {
        val fragmentManager = supportFragmentManager
        // Clear the back stack
            fragmentManager.popBackStack()
    }

}