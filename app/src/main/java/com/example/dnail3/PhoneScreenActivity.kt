package com.example.dnail3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_4phone_screen.*

class PhoneScreenActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_4phone_screen)
        addEvents()
        setUpToolbar()
    }

    private fun addEvents() {
        // Tabs
        val adapter = MyTabAdapter(supportFragmentManager)
        adapter.addFragment(SearchWorkerFragment(), "Tìm thợ")
        adapter.addFragment(OtherFeaturesFragment(), "Tính năng khác")

        pager.setAdapter(adapter)
        tabs.setupWithViewPager(pager)

        // Navigation
        navigationView = findViewById(R.id.nav_view)

        val headerview = navigationView.getHeaderView(0)

        val header = headerview.findViewById(R.id.header) as View

        header.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent(this@PhoneScreenActivity, PersonalInformationActivity::class.java);
                startActivity(intent)
            }
        })

        // Item click

    }

    private fun setUpToolbar() {
        val activity = this.setSupportActionBar(app_bar)

        val actionBar : ActionBar? = getSupportActionBar()

        toggle = ActionBarDrawerToggle(this, drawer_layout, R.string.open, R.string.close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.branded_menu)

        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
//                    Toast.makeText(this, "home", Toast.LENGTH_LONG).show()
                    drawer_layout.closeDrawers()
                    true
                }
                R.id.menu_history -> {
                    val intent = Intent(this@PhoneScreenActivity, TransactionHistoryActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu_promotion -> {
                    val intent = Intent(this@PhoneScreenActivity, PromotionActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu_other -> {
                    true
                }
                else -> false
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
