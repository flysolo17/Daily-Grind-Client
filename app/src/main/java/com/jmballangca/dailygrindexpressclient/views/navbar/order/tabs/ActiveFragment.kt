package com.jmballangca.dailygrindexpressclient.views.navbar.order.tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jmballangca.dailygrindexpressclient.R

import com.jmballangca.dailygrindexpressclient.databinding.FragmentActiveBinding
import com.jmballangca.dailygrindexpressclient.utils.UiState
import com.jmballangca.dailygrindexpressclient.viewmodels.AuthViewModel
import com.jmballangca.dailygrindexpressclient.viewmodels.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActiveFragment : Fragment() {

    private lateinit var binding : FragmentActiveBinding
    private val orderViewModel : OrderViewModel by viewModels()
    private val authViewModel : AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentActiveBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        orderViewModel.order.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    Toast.makeText(view.context, "Loading....", Toast.LENGTH_SHORT).show()
                }
                is UiState.Failure -> {
                    Toast.makeText(view.context, state.message, Toast.LENGTH_LONG).show()
                }
                is UiState.Success -> {
                    Toast.makeText(view.context, state.data.data.pickup_person, Toast.LENGTH_LONG).show()
                }
            }
        }


        //test endpoint
        binding.buttonCreateOrder.setOnClickListener {
            findNavController().navigate(R.id.action_menu_order_to_pickUpDetailsFragment)
            //orderViewModel.createOrder(authViewModel.getCurrentUser(TOKEN_TYPE)!!,authViewModel.getCurrentUser(TOKEN)!!,CreateOrderRequest("test","09658989436","anywhere","Lamborghini",15))
        }

    }
}