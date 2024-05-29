package com.anhht.edu.views.profile

import android.animation.ValueAnimator
import android.content.Context
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
import com.anhht.edu.views.profile.orderhistory.OrderHistoryActivity
import com.anhht.edu.views.profile.testhistory.TestHistoryActivity
import com.anhht.edu.views.SignInActivity
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
        val sessionManager = SessionManager(requireContext())

        if(sessionManager.fetchUserName() != null){
            binding.profileEmail.text = sessionManager.fetchEmail()
            binding.profileName.text = sessionManager.fetchUserName()
            Picasso.get().load("https://ui-avatars.com/api/?format=png&name="+sessionManager.fetchUserName()).into(binding.profileImage)
        }
        binding.cardOrderHistory.setOnClickListener{
            val intent = Intent(context, OrderHistoryActivity::class.java)
            activity?.startActivity(intent)
        }
        binding.cardTestHistory.setOnClickListener{
            val intent = Intent(context, TestHistoryActivity::class.java)
            activity?.startActivity(intent)
        }
        binding.profileLogout.setOnClickListener{
            val sessionManager = SessionManager(requireContext())
            sessionManager.logout()
            val intent = Intent(context, SignInActivity::class.java)
            activity?.startActivity(intent)
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        coinViewModel = CoinViewModel(CoinAPIService())
        val sessionManager = SessionManager(requireContext())
        coinViewModel.getUserInformation(context as Context).observe(viewLifecycleOwner){ d->
           if(d != null){
              if(sessionManager.fetchUserName() == null){
                 binding.profileEmail.text = d.data["email"]
                 binding.profileName.text = d.data["name"]
                 Picasso.get().load("https://ui-avatars.com/api/?format=png&name="+d.data["name"]).into(binding.profileImage)
              }
              val progress = d.data["progress"]
              val total = progress!!.split("/")[1]
              val num = progress.split("/")[0]
              val coin = d.data["coin"]
              val numCoin = coin!!.split(" ")[0]
              startCoinAnimation(numCoin.toInt())
                 startCountAnimation(num.toInt(), total.toInt())
              }
        }
    }
    private fun startCoinAnimation(end: Int){
        val coinAnimator = ValueAnimator.ofInt(0, end)
        var duration = 0
        if(end > 5000){
            duration = 8800000/end
        }
        else if(end > 1000){
            duration = 8400000/end
        }else if(end > 500){
            duration = 1300000/end
        }else{
            duration = 100000/end
        }
        coinAnimator.setDuration(duration.toLong())
        coinAnimator.addUpdateListener { anim -> binding.coin.text = String.format("%s dats", anim.animatedValue.toString()) }
        coinAnimator.start()
    }
    private fun startCountAnimation(end: Int, total: Int) {
        val animator = ValueAnimator.ofInt(0, end)
        var duration = 0
        if(end > 5000){
            duration = 150000/end
        }
        else if(end > 500){
            duration = 80000/end
        }else if(end > 50){
            duration = 40000/end
        }else{
            duration = 10000/end
        }
        animator.setDuration(duration.toLong())
        animator.addUpdateListener { animation -> binding.progress.text = String.format("%s/%s", animation.animatedValue.toString(), total) }
        animator.start()
    }

}