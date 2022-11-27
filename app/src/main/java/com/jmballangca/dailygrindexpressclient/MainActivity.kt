package com.jmballangca.dailygrindexpressclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jmballangca.dailygrindexpressclient.models.User
import com.jmballangca.dailygrindexpressclient.databinding.ActivityMainBinding
import com.jmballangca.dailygrindexpressclient.models.Profile
import com.jmballangca.dailygrindexpressclient.utils.ProgressDialog
import com.jmballangca.dailygrindexpressclient.utils.USER_FULLNAME
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
        val progressDialog = ProgressDialog(this)
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
        authViewModel.getProfile()
        authViewModel.profile.observe(this) {
            when(it){
                is UiState.Failure -> {
                    Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
                    progressDialog.stopLoading()
                }
                UiState.Loading -> {
                    progressDialog.showLoadingDialog("Fetching profile....")
                }
                is UiState.Success -> {
                    progressDialog.stopLoading()
                    showBottomDialog(it.data.full_name)
                    displayHeaderViews(it.data)
                }
            }
        }




        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
        binding.buttonLogout.setOnClickListener {
            logout()
        }
        buttonClose.setOnClickListener {
            binding.drawerLayout.close()
        }

    }
    private fun displayHeaderViews(user: User) {
        fullName.text = user.full_name
        Glide.with(this).load(user.profile_photo).into(userProfile)

    }
    private fun logout() {
        MaterialAlertDialogBuilder(binding.root.context).setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { dialog, _ ->
                authViewModel.logout().also {
                    dialog.dismiss()
                    finish()
                }
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
    private fun showBottomDialog(fullname : String) {
        val bottomDialog = BottomSheetDialog(this, R.style.BottomsheetDialogStyle)
        val view : View = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_welcome,binding.root,false)
        view.findViewById<TextView>(R.id.textFullName).text = "Welcome, $fullname"
        view.findViewById<MaterialButton>(R.id.buttonGetStarted).setOnClickListener {
            bottomDialog.dismiss()
        }
        bottomDialog.setContentView(view)
        bottomDialog.show()
    }

}