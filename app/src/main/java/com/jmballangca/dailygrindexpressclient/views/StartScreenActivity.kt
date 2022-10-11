package com.jmballangca.dailygrindexpressclient.views


import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.jmballangca.dailygrindexpressclient.MainActivity
import com.jmballangca.dailygrindexpressclient.R
import com.jmballangca.dailygrindexpressclient.databinding.ActivityStartScreenBinding
import com.jmballangca.dailygrindexpressclient.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StartScreenActivity : AppCompatActivity() {
    private lateinit var binding : ActivityStartScreenBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration : AppBarConfiguration
    private val authViewModel : AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        
        appBarConfiguration = AppBarConfiguration(setOf(R.id.registrationFragment,R.id.signUpFragment,R.id.verificationFragment,R.id.loginFragment))
        setupActionBarWithNavController(navController,appBarConfiguration)

        navController.addOnDestinationChangedListener{ _ : NavController, destination : NavDestination, _: Bundle ? ->
            when(destination.id) {
                R.id.registrationFragment -> {
                    supportActionBar?.hide()
                }
                else -> {
                    supportActionBar?.show()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = authViewModel.getCurrentUser()
        if (currentUser != null) {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }

}