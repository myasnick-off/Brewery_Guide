package com.example.breweryguide.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.breweryguide.databinding.FragmentDetailsBinding
import com.example.breweryguide.domain.model.BreweryDetails
import com.example.breweryguide.ui.AppState
import com.example.breweryguide.utils.hide
import com.example.breweryguide.utils.show
import com.example.breweryguide.utils.showErrorDialog

class DetailsFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }

    private val breweryId by lazy {
        requireArguments().getString(KEY_BREWERY)
    }

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
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
        val observer = Observer<AppState>() { renderData(it) }
        viewModel.getLiveData().observe(viewLifecycleOwner, observer)
    }

    private fun requestData() {
        breweryId?.let { viewModel.getBreweryDetails(it) }
    }

    private fun renderData(appState: AppState) = with(binding) {
        when(appState) {
            AppState.Loading -> {
                detailsProgressBar.root.show()
                detailsLayout.hide()
            }
            is AppState.DetailsSuccess -> {
                detailsProgressBar.root.hide()
                detailsLayout.show()
                showDetails(appState.breweryDetails)
            }
            is AppState.Error -> {
                detailsProgressBar.root.hide()
                showErrorDialog(requireContext()) { _, _ -> requestData() }
                Log.e("mylog", "Error: ${appState.error.message}")
            }
            else -> detailsProgressBar.root.hide()
        }
    }

    private fun showDetails(breweryDetails: BreweryDetails) = with(binding) {
        nameTextView.text = breweryDetails.name
        typeTextView.text = breweryDetails.breweryType
        streetTextView.text = breweryDetails.street
        secondAddrTextView.text = breweryDetails.address2
        thirdAddrTextView.text = breweryDetails.address3
        cityTextView.text = breweryDetails.city
        stateTextView.text = breweryDetails.state
        provinceTextView.text = breweryDetails.countyProvince
        postTextView.text = breweryDetails.postalCode
        countryTextView.text = breweryDetails.country
        phoneTextView.text = breweryDetails.phone
        siteTextView.text = breweryDetails.websiteUrl
        createdTextView.text = breweryDetails.createdAt
        updatedTextView.text = breweryDetails.updatedAt
    }

    companion object {
        const val KEY_BREWERY = "KEY_BREWERY"

        fun newInstance(breweryId: String): DetailsFragment {
            return DetailsFragment().apply {
                this.arguments = bundleOf(KEY_BREWERY to breweryId)
            }
        }
    }
}