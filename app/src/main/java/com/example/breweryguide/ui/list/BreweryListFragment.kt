package com.example.breweryguide.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.breweryguide.R
import com.example.breweryguide.databinding.FragmentListBinding
import com.example.breweryguide.ui.AppState
import com.example.breweryguide.ui.details.DetailsFragment
import com.example.breweryguide.utils.hide
import com.example.breweryguide.utils.show
import com.example.breweryguide.utils.showErrorDialog

class BreweryListFragment: Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(BreweriesViewModel::class.java)
    }

    private val adapter by lazy {
        ListRecyclerAdapter( object : ItemClickListener {
            override fun onItemClicked(breweryId: String) { runDetails(breweryId) }
        })
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun init() {
        binding.listRecyclerView.adapter = adapter
        viewModel.getPagedListData().observe(viewLifecycleOwner, { adapter.submitList(it) })
        viewModel.getAppStateData().observe(viewLifecycleOwner, { renderAppState(it) })

    }

    // обработка состояний от AppStateLiveData
    private fun renderAppState(appState: AppState) = with(binding) {
        when(appState) {
            AppState.Loading -> listProgressBar.root.show()
            AppState.ListSuccess -> listProgressBar.root.hide()
            is AppState.Error -> {
                listProgressBar.root.hide()
                showErrorDialog(requireContext()) { _, _ -> restartList() }
                Log.e("mylog", "Error: ${appState.error.message}")
            }
            else -> listProgressBar.root.hide()
        }
    }

    // функция запуска экрана с детальной информацией о выбранной пивоварне
    private fun runDetails(breweryId: String) {
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_horizontal,
                R.anim.slide_out_horizontal,
                R.anim.slide_in_horizontal,
                R.anim.slide_out_horizontal
            )
            .add(R.id.container, DetailsFragment.newInstance(breweryId))
            .addToBackStack("")
            .commit()
    }

    // функция перезапуска экрана со списком пивоварен
    private fun restartList() {
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            .replace(R.id.container, newInstance())
            .commit()
    }

    // интерфейс для обработки кликов на элементы списка
    interface ItemClickListener {
        fun onItemClicked(breweryId: String)
    }

    companion object {
        fun newInstance() = BreweryListFragment()
    }
}