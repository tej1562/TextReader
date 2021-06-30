package com.example.textreader.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.textreader.R
import com.example.textreader.adapters.AppInfoAdapter
import com.example.textreader.db.TextInfoDatabase
import com.example.textreader.repository.MainRepository
import com.example.textreader.ui.MainViewModel
import com.example.textreader.ui.MainViewModelProviderFactory

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var btnStart: Button
    private lateinit var rvAppInfo: RecyclerView

    private lateinit var appInfoAdapter: AppInfoAdapter

    lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = MainRepository(TextInfoDatabase(requireActivity()))
        val viewModelProviderFactory = MainViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(
            requireActivity(),
            viewModelProviderFactory
        ).get(MainViewModel::class.java)

        btnStart = view.findViewById(R.id.btnStart)
        rvAppInfo = view.findViewById(R.id.rvAppInfo)

        setupRecyclerView()

        appInfoAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("appInfo", it)
            }
            findNavController().navigate(
                R.id.action_homeFragment_to_detailsFragment,
                bundle
            )
        }

        btnStart.setOnClickListener {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }

        viewModel.getSavedAppInfo().observe(viewLifecycleOwner, {
            appInfoAdapter.differ.submitList(it)
        })
    }

    private fun setupRecyclerView() = rvAppInfo.apply {
        appInfoAdapter = AppInfoAdapter()
        adapter = appInfoAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }
}