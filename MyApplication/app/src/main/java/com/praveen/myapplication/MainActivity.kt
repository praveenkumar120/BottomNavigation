package com.praveen.myapplication

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.rangeTo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ReportFragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.praveen.myapplication.fragment.DetailedRepoFragment
import com.praveen.myapplication.fragment.RepoHomeFragment
import com.praveen.myapplication.fragment.TestFragment
import com.praveen.myapplication.service.utils.Constants

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var navController: NavController
    private var navHostFragment: NavHostFragment? = null
    private val repoHomeFragment = RepoHomeFragment()
    private val detailedRepoFragment = DetailedRepoFragment()
    private val testFragmentFragment = TestFragment()
    private val fragmentManager = supportFragmentManager
    private var activeFragment: Fragment = repoHomeFragment
    private var isFirstTime = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setBottomNavigation()
    }

    private fun setBottomNavigation() {
        /* navHostFragment = supportFragmentManager
             .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment?
         navController = findNavController(R.id.nav_host_fragment_container)
         bottomNavigation = findViewById(R.id.nav_bottom)
         bottomNavigation.apply {
             setupWithNavController(navController)
         }*/

        fragmentManager.beginTransaction().apply {
            add(R.id.container, detailedRepoFragment, getString(R.string.detail_repo)).hide(
                detailedRepoFragment
            )
            add(R.id.container, testFragmentFragment, getString(R.string.test_frag)).hide(
                testFragmentFragment
            )
            add(R.id.container, repoHomeFragment, getString(R.string.title_repo))
        }.commit()


        bottomNavigation = findViewById(R.id.nav_bottom)

        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(repoHomeFragment)
                        .commit()
                    activeFragment = repoHomeFragment
                    true
                }
                R.id.navigation_detail_repo -> {
                    fragmentManager.beginTransaction().hide(activeFragment)
                        .show(detailedRepoFragment).commit()
                    activeFragment = detailedRepoFragment
                    true
                }
                R.id.navigation_test -> {
                    fragmentManager.beginTransaction().hide(activeFragment)
                        .show(testFragmentFragment).commit()
                    activeFragment = testFragmentFragment
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

}