package com.example.yuefengproject

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.yuefengproject.data.factory.MainHomeFactory
import com.example.yuefengproject.data.model.MainHomeViewModel
import com.example.yuefengproject.databinding.ActivityMainHomeBinding
import com.example.yuefengproject.ui.events.EventsFragment
import com.example.yuefengproject.ui.home.HomeFragment
import com.example.yuefengproject.ui.my.MyFragment
import com.google.firebase.analytics.FirebaseAnalytics

class MainHomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener  {

    private lateinit var binding: ActivityMainHomeBinding
    lateinit var mainModel: MainHomeViewModel
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyLog.setLogLevel(1)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main_home)
        val mainHomeFactory = MainHomeFactory(application)
        mainModel = ViewModelProvider(this, mainHomeFactory).get(MainHomeViewModel::class.java)
        binding.apply {
            mainHomeModel = mainModel
            lifecycleOwner = this@MainHomeActivity
            navView.setOnNavigationItemSelectedListener(this@MainHomeActivity)
        }

        mainModel.navId.observe(this, Observer {
            onNavItemSelected(binding.navView.menu.findItem(it))
        })

        onNavigationItemSelected(binding.navView.menu.findItem(R.id.navigation_home))
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        mainModel.navId.value = item.itemId
        return true
    }

    //bar ICON 選擇
    private fun onNavItemSelected(item: MenuItem) {
        when (item.itemId) {
            R.id.navigation_home -> {
                openNavFrag("Home", HomeFragment())
            }
            R.id.navigation_events -> {
                openNavFrag("Events", EventsFragment())
            }
            R.id.navigation_my -> {
                openNavFrag("My", MyFragment())
            }

        }
    }

    //開啟Fragment
    private fun openNavFrag(tag: String, frag: Fragment) {
        val fragTransaction = supportFragmentManager.beginTransaction()
        if (supportFragmentManager.findFragmentByTag(tag) != null)
            fragTransaction.replace(
                R.id.nav_host_fragment,
                supportFragmentManager.findFragmentByTag(tag)!!
            )
        else {
            val bundle = mainModel.navBundle.value
            frag.arguments = bundle
            fragTransaction.replace(R.id.nav_host_fragment, frag, tag)
        }
        fragTransaction.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (onBackPressed(supportFragmentManager))
            return
        showAlertDialog(getString(R.string.alert), getString(R.string.sure_to_leave), true)
    }

    private fun onBackPressed(fragmentManager: FragmentManager): Boolean {
        val fragmentList = fragmentManager.fragments
        if (fragmentList.size > 0) {
            for (frag in fragmentList) {
                if (frag == null || !frag.isVisible) {
                    continue
                }

                if (frag is HomeFragment || frag is EventsFragment || frag is MyFragment) {
                    return false
                }

                if (onBackPressed(frag.childFragmentManager)) {
                    return true
                }
            }
        }

        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
            return true
        }

        return false
    }

    private fun showAlertDialog(title: String, message: String, showNegBtn: Boolean) {
        val builder = AlertDialog.Builder(this)
            .setTitle(title).setMessage(message)
            .setPositiveButton(R.string.sure) { _, _ -> finish() }
            .setCancelable(false)

        if (showNegBtn) {
            builder.setNegativeButton(R.string.cancel) { dialog, _ -> dialog.cancel() }
        }
        val dialog = builder.show()

        if (showNegBtn)
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
    }
}