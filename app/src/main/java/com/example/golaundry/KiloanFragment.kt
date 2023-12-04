package com.example.golaundry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.golaundry.databinding.FragmentKiloanBinding
import com.example.golaundry.model.LaundryOrder

class KiloanFragment : Fragment() {
    private var _binding: FragmentKiloanBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentKiloanBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.paketCuci.setOnCheckedChangeListener { _, checkedId ->
            calculateAndDisplayCost(checkedId)
        }

        binding.jasaAntar.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.radioJasa) {
                Toast.makeText(requireContext(), "Jasa antar jemput cucian dipilih!", Toast.LENGTH_SHORT).show()
                calculateAndDisplayCost(binding.paketCuci.checkedRadioButtonId)
            }
        }

        binding.btnPayment.setOnClickListener {
            if (validateInputs()) {
                val selectedPaketId = binding.paketCuci.checkedRadioButtonId
                val paketId = getPaketIdFromRadioButtonId(selectedPaketId)

                // Retrieve calculated biaya
                val biaya = binding.biaya.text.toString().substringAfter(": ").toDouble()

                val laundryOrder = LaundryOrder(
                    berat = binding.berat.text.toString().toDouble(),
                    paketId = paketId,
                    jasaAntar = binding.jasaAntar.checkedRadioButtonId == R.id.radioJasa,
                    alamat = binding.alamat.text.toString(),
                    biaya = biaya // Include biaya in LaundryOrder
                )

                val dataBundle = Bundle().apply {
                    putParcelable("laundryOrder", laundryOrder)
                }

                val paymentFragment = PaymentFragment()
                replaceFragment(paymentFragment, dataBundle)
            } else {
                Toast.makeText(requireContext(), "Isi input yang diperlukan!", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun getPaketIdFromRadioButtonId(radioButtonId: Int): Int {
        return when (radioButtonId) {
            R.id.radioCuciKering -> 1
            R.id.radioCuciSetrika -> 2
            R.id.radioSetrika -> 3
            else -> -1 // or a default value according to your needs
        }
    }

    private fun calculateAndDisplayCost(checkedId: Int) {
        if (validateInputs()) {
            val beratInput: Double = binding.berat.text.toString().toDouble()
            var biaya: Double = when (checkedId) {
                R.id.radioCuciKering -> beratInput * 5000
                R.id.radioCuciSetrika -> beratInput * 7000
                R.id.radioSetrika -> beratInput * 3000
                else -> 0.0
            }

            // Add 10000 if jasaAntar is selected
            if (binding.jasaAntar.checkedRadioButtonId == R.id.radioJasa) {
                biaya += 10000
            }

            // Display the calculated biaya
            binding.biaya.text = "Total biaya: $biaya"
        }
    }

    private fun validateInputs(): Boolean {
        val beratInput = binding.berat.text.toString()
        val selectedPaketId = binding.paketCuci.checkedRadioButtonId

        if (beratInput.isEmpty()) {
            binding.berat.error = "Berat tidak boleh kosong!"
            return false
        } else {
            binding.berat.error = null
        }

        if (selectedPaketId == -1) {
            Toast.makeText(requireContext(), "Pilih paket cuci!", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun replaceFragment(fragment: Fragment, dataBundle: Bundle) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
