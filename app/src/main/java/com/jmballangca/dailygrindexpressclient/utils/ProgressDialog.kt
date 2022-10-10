package com.jmballangca.dailygrindexpressclient.utils

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jmballangca.dailygrindexpressclient.R
import kotlin.math.acos

class ProgressDialog(private val context: Context) {
    private lateinit var alertDialog: androidx.appcompat.app.AlertDialog
    fun showLoadingDialog(title : String){
        val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
        val view : View = LayoutInflater.from(context).inflate(R.layout.loading_dialog,null)
        view.findViewById<TextView>(R.id.textTitle).text = title
        materialAlertDialogBuilder.setView(view)
        materialAlertDialogBuilder.setCancelable(false)
        alertDialog = materialAlertDialogBuilder.create()
        alertDialog.show()
    }
    fun stopLoading(){
        alertDialog.dismiss()
    }
}