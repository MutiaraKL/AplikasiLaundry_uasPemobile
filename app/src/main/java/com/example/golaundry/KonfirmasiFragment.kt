package com.example.golaundry

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.golaundry.databinding.FragmentKonfirmasiBinding
import com.example.golaundry.model.LaundryOrder
import com.example.golaundry.model.LaundryPayment

class KonfirmasiFragment : Fragment() {

    private lateinit var binding: FragmentKonfirmasiBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val laundryOrder = arguments?.getParcelable<LaundryOrder>("laundryOrder")
        binding = FragmentKonfirmasiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val laundryOrder = arguments?.getParcelable<LaundryOrder>("laundryOrder")
        val laundryPayment = arguments?.getParcelable<LaundryPayment>("laundryPayment")

        laundryOrder?.let {
            binding.paket.text = "Paket"
            binding.paketIsi.text = getPaketText(it.paketId)
            binding.berat.text = "Berat"
            binding.beratIsi.text = it.berat.toString()
            binding.tambah.text = "Tambahan"
            binding.tambahIsi.text = if (it.jasaAntar) "Jasa antar jemput" else "Tanpa jasa antar"
            binding.alamat.text = "Alamat"
            binding.alamatIsi.text = it.alamat
            binding.durasi.text = "Durasi cucian"
            binding.durasiIsi.text = getDurasiText(it.berat)
        }

        laundryPayment?.let {
            binding.metode.text = "Metode Pembayaran"
            binding.metodeIsi.text = it.metode
        }

        binding.btnKonfirmasi.setOnClickListener {
            replaceFragment(HomeFragment())
        }
    }

    private fun getPaketText(paketId: Int): String {
        return when (paketId) {
            1 -> "Paket Cuci Kering"
            2 -> "Paket Cuci Setrika"
            3 -> "Paket Setrika"
            else -> "Paket Tidak Diketahui"
        }
    }

    private fun getDurasiText(berat: Double): String {
        return if (berat <= 5) "1 hari" else "2 hari"
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}
