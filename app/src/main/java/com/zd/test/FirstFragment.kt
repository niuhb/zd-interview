package com.zd.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.zd.test.databinding.FragmentFirstBinding
import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private lateinit var viewModel: ZdViewModel

    private var amountVal = ""
    private var timeVal = ""

    private val binding get() = _binding!!

    private var lst: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }


    private fun formatAmount(amount: String) {
        amountVal = amount
        if (amount == "") {
            binding.amountFormat.text = "${getString(R.string.amount)}"
            return
        }
        val formatter = DecimalFormat("#,###.##")
        var formattedString = formatter.format(BigDecimal(amount))
        binding.amountFormat.text = "${getString(R.string.amount)}$formattedString"
    }

    private fun formatTime(second: String) {
        timeVal = second
        if (second == "") {
            binding.timeFormat.text = "${getString(R.string.time)}"
            return
        }
        val millis = second.toLong()
        val hours = TimeUnit.SECONDS.toHours(millis) % 24
        val minutes = TimeUnit.SECONDS.toMinutes(millis) % 60
        val seconds = millis % 60

        var res = when {
            hours == 0L && minutes == 0L -> String.format("%1d", seconds)

            hours == 0L && minutes > 0L -> String.format("%1dm%1ds", minutes, seconds)

            else -> String.format("%1dh%1dm%1ds", hours, minutes, seconds)
        }

        binding.timeFormat.text = "${getString(R.string.time)} $res"
    }

    private fun getMul(): String {
        var amount = if (amountVal == "") BigDecimal.ZERO else BigDecimal(amountVal)
        var time = if (timeVal == "") BigDecimal.ZERO else BigDecimal(timeVal)

        return amount.multiply(time).toEngineeringString()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(activity as AppCompatActivity).get(ZdViewModel::class.java)

        binding.amount.doAfterTextChanged {
            formatAmount(it?.toString() ?: "0")
        }

        binding.time.doAfterTextChanged {
            formatTime(it?.toString() ?: "0")
        }

        binding.submit.setOnClickListener {
            lst.add(getMul())
            viewModel.list.postValue(lst)
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}