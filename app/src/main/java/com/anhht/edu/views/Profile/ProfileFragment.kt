package com.anhht.edu.views.Profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anhht.edu.config.SessionManager
import com.anhht.edu.databinding.FragmentProfileBinding
import com.anhht.edu.repository.CoinAPIService
import com.anhht.edu.viewmodels.CoinViewModel
import com.anhht.edu.views.Profile.orderhistory.OrderHistoryActivity
import com.anhht.edu.views.Profile.testhistory.TestHistoryActivity
import com.anhht.edu.views.SignInActivity
import com.anhht.edu.views.learn.LearnActivity
import com.squareup.picasso.Picasso

class ProfileFragment() : Fragment() {
    lateinit var binding: FragmentProfileBinding
    private lateinit var coinViewModel: CoinViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.cardOrderHistory.setOnClickListener{
            var intent = Intent(requireContext(), OrderHistoryActivity::class.java)
            activity?.startActivity(intent)
        }
        binding.cardTestHistory.setOnClickListener{
            var intent = Intent(requireContext(), TestHistoryActivity::class.java)
            activity?.startActivity(intent)
        }

        binding.profileLogout.setOnClickListener{
            val sessionManager = SessionManager(requireContext())
            sessionManager.logout()
            var intent = Intent(requireContext(), SignInActivity::class.java)
            activity?.startActivity(intent)
        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentProfileBinding.inflate(layoutInflater)
        coinViewModel = CoinViewModel(CoinAPIService())
        activity?.let {
            coinViewModel.getUserInformation().observe(it){ d->
                if(d != null){
                    binding.profileEmail.text = d.data["email"]
                    binding.profileName.text = d.data["name"]
                    binding.progress.text = d.data["progress"]
                    binding.coin.text = d.data["coin"]
                    Picasso.get().load("https://ui-avatars.com/api/?format=png&name="+d.data["name"]).into(binding.profileImage)
                }
            }
        }

    }
}