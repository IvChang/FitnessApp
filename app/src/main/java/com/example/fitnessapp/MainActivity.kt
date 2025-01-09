package com.example.fitnessapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlin.math.log


class MainActivity : AppCompatActivity() {
    var tab_page: TabLayout? = null
    var view_pager: ViewPager2? = null
    var viewPagerAdapter: ViewPagerAdapter? = null

    var dbAuth: FirebaseAuth? = null
    var user: FirebaseUser? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tab_page = findViewById(R.id.tab_page)
        view_pager = findViewById(R.id.view_pager)
        viewPagerAdapter = ViewPagerAdapter(this)
        view_pager?.setAdapter(viewPagerAdapter)

        dbAuth = FirebaseAuth.getInstance()
        user = dbAuth!!.currentUser
        Log.d("test1", "username: ${user!!.displayName}, email: ${user!!.email}")

        // permet de sélectionner les différents tabs
        tab_page?.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                view_pager?.setCurrentItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })

        // S'assure que le tab_page affiche le bon tab sélectionner quand on swipe le view_pager
        view_pager?.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tab_page?.getTabAt(position)!!.select()
            }
        })
    }

    // affiche le menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menuprincipal, menu)
        return true
    }

    // permet de cliquer les options du menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mnu_settings) {
            val settingsActivity = Intent(this@MainActivity, SettingActivity::class.java)
            startActivity(settingsActivity)
        } else if (item.itemId == R.id.mnu_logout){
            dbAuth!!.signOut()
            val loginActivity = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(loginActivity)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}