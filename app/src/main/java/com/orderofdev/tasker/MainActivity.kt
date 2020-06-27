package com.orderofdev.tasker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.orderofdev.tasker.view.calendar.CalendarFragment

class MainActivity : AppCompatActivity() {
    companion object{
        const val TAG = "MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addFragment(CalendarFragment(), CalendarFragment.TAG)
    }

    private fun addFragment(fragment: Fragment,
                            fragmentTag: String) {
        if(supportFragmentManager.fragments.size == 0) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentFrame, fragment, fragmentTag)
                .disallowAddToBackStack()
                .commit()
        }
    }

    fun replaceFragment(fragment: Fragment,
                        fragmentTag: String,
                        @Nullable backStackStateName: String?) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentFrame, fragment, fragmentTag)
            .addToBackStack(backStackStateName)
            .commit()
    }
}