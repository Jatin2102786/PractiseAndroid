package com.jatin.practiseandroid

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.jatin.practiseandroid.databinding.ActivityInterfaceBinding

class InterfaceActivity : AppCompatActivity() {

    private var interactionInterface: InteractionInterface?=null
    private lateinit var bindingInterface: ActivityInterfaceBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingInterface = ActivityInterfaceBinding.inflate(layoutInflater)
        setContentView(bindingInterface.root)

        val navController = findNavController(R.id.nav_host_fragment_activity_interface)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)

        bindingInterface.blueBtn.setOnClickListener {
            interactionInterface?.changeColor("0")
        }

        bindingInterface.whiteBtn.setOnClickListener {
            interactionInterface?.changeColor("1")
        }

        bindingInterface.blackBtn.setOnClickListener {
            interactionInterface?.changeColor("2")
        }


    }


    fun changeText(choice: String,text: String){

        if (choice == "0") {
            bindingInterface.blueBtn.setText(text)
        }
        else  if (choice == "2") {
            bindingInterface.whiteBtn.setText(text)
        }
        else  if (choice == "3") {
            bindingInterface.blackBtn.setText(text)
        }
    }


}

interface InteractionInterface {
    fun changeColor(color: String)
}