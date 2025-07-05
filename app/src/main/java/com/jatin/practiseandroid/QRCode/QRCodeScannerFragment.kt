package com.jatin.practiseandroid.QRCode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.journeyapps.barcodescanner.ScanOptions
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import androidx.fragment.app.Fragment
import com.jatin.practiseandroid.R

/**
 * A simple [androidx.fragment.app.Fragment] subclass.
 * Use the [QRCodeScannerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QRCodeScannerFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var scanQrBtn: Button
    private lateinit var scannedValueTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_q_r_code_scanner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scanQrBtn = view.findViewById(R.id.scanQrBtn)
        scannedValueTv = view.findViewById(R.id.scannedValueTv)

        registerUiListener()
    }

    private fun registerUiListener() {
        scanQrBtn.setOnClickListener {
            scannerLauncher.launch(
                ScanOptions().setPrompt("Scan Qr Code")
                    .setDesiredBarcodeFormats(ScanOptions.QR_CODE)
            )
        }
    }

    private val scannerLauncher = registerForActivityResult<ScanOptions, ScanIntentResult>(
        ScanContract()
    ) { result ->

        if (result.contents == null) {
            Toast.makeText(requireActivity(), "Cancelled", Toast.LENGTH_SHORT).show()
        } else {

            Toast.makeText(requireActivity(),result.contents, Toast.LENGTH_SHORT).show()
            scannedValueTv.text = buildString {
                append("Scanned Value : ")
                append(result.contents)
            }
        }

    }
}