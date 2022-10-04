package com.jmballangca.dailygrindexpressclient.views.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jmballangca.dailygrindexpressclient.databinding.FragmentSignUpBinding


class SignUpFragment : Fragment() {

    private lateinit var binding : FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSignUp.setOnClickListener {
            val phone = binding.inputPhone.text.toString()
            if (phone.isNotEmpty() && phone.startsWith("09") && phone.length == 11) {
                val action = SignUpFragmentDirections.actionSignUpFragmentToVerificationFragment(phone)
                findNavController().navigate(action)
            } else {
                Toast.makeText(view.context,"Invalid phone number!",Toast.LENGTH_SHORT).show()
            }
        }

    }

}