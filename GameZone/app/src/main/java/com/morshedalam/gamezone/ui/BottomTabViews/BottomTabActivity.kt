package com.morshedalam.gamezone.ui.BottomTabViews

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.morshedalam.gamezone.R
import com.morshedalam.gamezone.ui.BottomTabViews.Home.HomeFragment
import com.morshedalam.gamezone.ui.BottomTabViews.Profile.ProfileFragment
import com.morshedalam.gamezone.ui.BottomTabViews.Search.SearchFragment
import kotlinx.android.synthetic.main.activity_base.*

class BottomTabActivity : AppCompatActivity() {

    private val homeFragment = HomeFragment()
    private val searchFragment = SearchFragment()
    private val profileFragment = ProfileFragment()

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        val fragment = when(item.itemId){
            R.id.navigation_home -> homeFragment
            R.id.navigation_search -> searchFragment
            R.id.navigation_profile -> profileFragment
            else -> HomeFragment()
        }
        switchFragment(fragment)
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        switchFragment(homeFragment)
    }


    private  fun switchFragment(fragment:Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, fragment).commit()
    }

    fun onClickSignInButton(view: View){

    }
}

