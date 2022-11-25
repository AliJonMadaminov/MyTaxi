package com.example.ui.android.task.junior.triphistory

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ui.android.task.junior.databinding.FragmentTripHistoryBinding
import com.example.ui.android.task.junior.models.ClientDataSource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TripHistoryFragment : Fragment() {

    lateinit var binding: FragmentTripHistoryBinding
    lateinit var viewModel: TripHistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[TripHistoryViewModel::class.java]

        binding = FragmentTripHistoryBinding.inflate(inflater)

        val tripAdapter = TripHistoryAdapter(TripHistoryListener {

        })

        binding.recyclerTrips.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = tripAdapter
        }

        //Imitating loading behavior
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            binding.shimmer.hideShimmer()
            binding.shimmer.visibility = View.GONE
            tripAdapter.submitTrips(viewModel.getTrips())
        }


        return binding.root
    }

}