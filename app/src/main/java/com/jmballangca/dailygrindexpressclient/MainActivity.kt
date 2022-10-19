package com.jmballangca.dailygrindexpressclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.jmballangca.dailygrindexpressclient.data.response.User
import com.jmballangca.dailygrindexpressclient.databinding.ActivityMainBinding
import com.jmballangca.dailygrindexpressclient.databinding.NavHeaderBinding
import com.jmballangca.dailygrindexpressclient.utils.UiState
import com.jmballangca.dailygrindexpressclient.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val authViewModel : AuthViewModel by viewModels()
    private lateinit var header : View
    private lateinit var userProfile : ImageView
    private lateinit var fullName : TextView
    private lateinit var buttonClose : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        header = binding.navView.getHeaderView(0)
        userProfile = header.findViewById(R.id.imageUserProfile)
        fullName = header.findViewById(R.id.textFullName)
        buttonClose = header.findViewById(R.id.buttonClose)

        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.menu_order, R.id.menu_routes, R.id.menu_vouchers,R.id.menu_help_center
            ), binding.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
        binding.buttonLogout.setOnClickListener {
            logout()
        }
        buttonClose.setOnClickListener {
            binding.drawerLayout.close()
        }



    }
    private fun bindUserInfo(user: User) {
        fullName.text = user.full_name
    }
    private fun logout() {
        MaterialAlertDialogBuilder(binding.root.context).setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { dialog, _ ->
                authViewModel.logout()
                finish()
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.main, menu)
        return true
    }

}