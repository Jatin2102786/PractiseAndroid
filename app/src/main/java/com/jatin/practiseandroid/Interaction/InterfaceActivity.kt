package com.jatin.practiseandroid.Interaction

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.jatin.practiseandroid.DrawerActivity
import com.jatin.practiseandroid.R
import com.jatin.practiseandroid.databinding.ActivityInterfaceBinding

class InterfaceActivity : AppCompatActivity() {

    var interactionInterface: InteractionInterface?=null
    private lateinit var bindingInterface: ActivityInterfaceBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingInterface = ActivityInterfaceBinding.inflate(layoutInflater)
        setContentView(bindingInterface.root)

        val navController = findNavController(R.id.nav_host_fragment_activity_interface)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)

//        interactionInterface = InteractionInterface.

        bindingInterface.blueBtn.setOnClickListener {
            interactionInterface?.changeColor("0")
        }

        bindingInterface.whiteBtn.setOnClickListener {
            interactionInterface?.changeColor("1")
        }

        bindingInterface.blackBtn.setOnClickListener {
            interactionInterface?.changeColor("2")
        }

        bindingInterface.visitButton.setOnClickListener {
            val intent = Intent(this, DrawerActivity::class.java)
            startActivity(intent)
        }


    }


    fun changeText(choice: String,text: String){

        if (choice == "0") {
            bindingInterface.blueBtn.setText(text)
        }
        else  if (choice == "1") {
            bindingInterface.whiteBtn.setText(text)
        }
        else  if (choice == "2") {
            bindingInterface.blackBtn.setText(text)
        }
    }


}


interface InteractionInterface {
    fun changeColor(color: String)
}


