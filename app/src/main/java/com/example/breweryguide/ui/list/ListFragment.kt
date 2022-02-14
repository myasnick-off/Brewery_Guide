package com.example.breweryguide.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.breweryguide.databinding.FragmentListBinding
import com.example.breweryguide.domain.model.BreweryBasic
import com.example.breweryguide.ui.AppState
import com.example.breweryguide.utils.hide
import com.example.breweryguide.utils.show

class ListFragment: Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(ListViewModel::class.java)
    }
    private val adapter by lazy {
        ListRecyclerAdapter()
    }

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        requestData()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun init() {
        binding.listRecyclerView.adapter = adapter
        val observer = Observer<AppState>() { renderData(it) }
        viewModel.getLiveData().observe(viewLifecycleOwner, observer)
    }

    private fun requestData() {
        viewModel.getBreweryList()
    }

    private fun renderData(appState: AppState) = with(binding) {
        when(appState) {
            AppState.Loading -> listProgressBar.show()
            is AppState.ListSuccess -> {
                listProgressBar.hide()
                showList(appState.breweryList)
            }
            is AppState.Error -> {
                listProgressBar.hide()
//                TODO()
            }
            else -> listProgressBar.hide()
        }
    }

    private fun showList(breweryList: List<BreweryBasic>) {
        adapter.submitList(breweryList)
    }

    companion object {
        fun newInstance() = ListFragment()
    }
}