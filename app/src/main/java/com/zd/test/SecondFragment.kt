package com.zd.test

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zd.test.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    private lateinit var viewModel: ZdViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(activity as AppCompatActivity).get(ZdViewModel::class.java)

        var list = viewModel.list.value

        binding.rv.layoutManager = LinearLayoutManager(activity)
        binding.rv.adapter = list?.let { DataAdapter(it) }

    }


    class DataAdapter(private val mData: List<String>) :
        RecyclerView.Adapter<DataAdapter.CustomViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
            return CustomViewHolder(v)
        }

        override fun getItemCount(): Int {
            return mData.size
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            holder.tv.text = mData[position]
        }

        class CustomViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            val tv: TextView = v.findViewById(R.id.tv)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}