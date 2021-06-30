package com.example.textreader.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.textreader.R
import com.example.textreader.adapters.TextDetailsAdapter
import com.example.textreader.db.TextInfoDatabase
import com.example.textreader.repository.MainRepository
import com.example.textreader.ui.MainActivity
import com.example.textreader.ui.MainViewModel
import com.example.textreader.ui.MainViewModelProviderFactory

class DetailsFragment : Fragment(R.layout.fragment_details) {

    val args: DetailsFragmentArgs by navArgs()
    lateinit var viewModel: MainViewModel

    private lateinit var rvTextDetails: RecyclerView

    private lateinit var textDetailsAdapter: TextDetailsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = MainRepository(TextInfoDatabase(requireActivity()))
        val viewModelProviderFactory = MainViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(
            requireActivity(),
            viewModelProviderFactory
        ).get(MainViewModel::class.java)

        viewModel = (activity as MainActivity).viewModel

        val btnBack = view.findViewById<ImageButton>(R.id.btnBack)

        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        rvTextDetails = view.findViewById(R.id.rvTextDetails)

        setupRecyclerView()

        val info = args.appInfo

        val tvAppDetails = view.findViewById<TextView>(R.id.tvAppDetails)
        val detailsString = "${info.appName} Reads"
        tvAppDetails.text = detailsString

        viewModel.getTextFromAppName(info.appName).observe(viewLifecycleOwner, {
            textDetailsAdapter.differ.submitList(it)
        })
    }

    private fun setupRecyclerView() = rvTextDetails.apply {
        textDetailsAdapter = TextDetailsAdapter()
        adapter = textDetailsAdapter
        layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }
}