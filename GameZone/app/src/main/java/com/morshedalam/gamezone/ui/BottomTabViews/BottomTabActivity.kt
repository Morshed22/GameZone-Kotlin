package com.morshedalam.gamezone.ui.BottomTabViews

import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
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
    private  var prevMenuItem: MenuItem? =  null




    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        val item = when(item.itemId){
            R.id.navigation_home -> 0
            R.id.navigation_search -> 1
            R.id.navigation_profile -> 2
            else -> 0
        }
        switchCurrentItem(item)
        true
    }

    private val mAddOnPageChangeListener = object :ViewPager.OnPageChangeListener{

        override fun onPageScrollStateChanged(state: Int) {
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        }

        override fun onPageSelected(position: Int) {
//            if (prevMenuItem != null) {
//
//                prevMenuItem!!.setChecked(false)
//            } else {
//                navigation.getMenu().getItem(0).setChecked(false)
//            }
//            Log.d("page", "onPageSelected: $position")
//            navigation.getMenu().getItem(position).setChecked(true)
//            prevMenuItem = navigation.getMenu().getItem(position)
        }


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        //viewpager.offscreenPageLimit = 3
        viewpager.addOnPageChangeListener(mAddOnPageChangeListener)

        //switchFragment(homeFragment)

        setupViewPager(viewpager)
    }





    private fun switchCurrentItem(item:Int){
        viewpager.setCurrentItem(item, false)
    }

    private  fun switchFragment(fragment:Fragment){
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.main_container, fragment).commit()
    }

    fun onClickSignInButton(view: View){

    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(homeFragment)
        adapter.addFragment(searchFragment)
        adapter.addFragment(profileFragment)
        viewPager.adapter = adapter
    }
}

