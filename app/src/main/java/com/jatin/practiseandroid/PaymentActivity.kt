package com.jatin.practiseandroid

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jatin.practiseandroid.databinding.ActivityPaymentBinding
import com.razorpay.Checkout
import com.razorpay.ExternalWalletListener
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import org.json.JSONObject

class PaymentActivity : AppCompatActivity(),PaymentResultWithDataListener,ExternalWalletListener {

    private lateinit var binding: ActivityPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.payBtn.setOnClickListener {
            payment()
        }
    }

    private fun payment() {
        Checkout.preload(this)


        val co = Checkout()

        co.setKeyID("rzp_test_c51XgNNFUQLpLP")

        try {

//            Option
            val options = JSONObject()
            options.put("name","Jatin")
            options.put("image",R.drawable.ic_menu_gallery)
            options.put("theme.color","#335678")
            options.put("currency","INR")
            options.put("amount","1000")


//            Retry

            val retryObj = JSONObject()
            retryObj.put("enabled",true)
            retryObj.put("max_count",4)
            retryObj.put("retry",retryObj)


//            prefill
            val prefillObj = JSONObject()
            prefillObj.put("email","jatin@gmail.com")
            prefillObj.put("contact","7658848135")
            prefillObj.put("prefill",prefillObj)

            co.open(this,options)

        }
        catch (e: Exception) {

            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        Log.d("Payment successful",p0.toString())
        Log.d("Payment Data",p1.toString())

    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        Log.d("Payment error",p1.toString())
        Log.d("Payment Data",p2.toString())

    }

    override fun onExternalWalletSelected(p0: String?, p1: PaymentData?) {
        TODO("Not yet implemented")
    }
}