package com.example.golaundry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.golaundry.databinding.FragmentPaymentBinding

class PaymentFragment : Fragment() {
    private lateinit var binding: FragmentPaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root

        // Set listener untuk RadioGroup
        binding.pembayaran.setOnCheckedChangeListener { group, checkedId ->
            // Handle logika ketika pilihan radio berubah
            when (checkedId) {
                binding.radioKartuKredit.id -> {
                    // Handle logika untuk radio Kartu Kredit/Debit Baru
                }
                binding.radioQRIS.id -> {
                    // Handle logika untuk radio QRIS
                }
                binding.radioDana.id -> {
                    // Handle logika untuk radio Dana
                }
                binding.radioGopay.id -> {
                    // Handle logika untuk radio Gopay
                }
            }
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLanjut.setOnClickListener {
            replaceFragment(KonfirmasiFragment())
        }

        binding.judul.setOnClickListener {
            replaceFragment(KiloanFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}
