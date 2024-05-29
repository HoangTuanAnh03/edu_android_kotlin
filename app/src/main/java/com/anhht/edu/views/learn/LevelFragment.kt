package com.anhht.edu.views.learn

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.anhht.edu.adapter.RVLevelAdapter.IClickLevelCard
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anhht.edu.R
import com.anhht.edu.config.SessionManager
import com.anhht.edu.databinding.FragmentLevelBinding
import com.anhht.edu.model.data.Level
import com.anhht.edu.model.data.Product
import com.anhht.edu.repository.CoinAPIService
import com.anhht.edu.repository.LevelAPIService
import com.anhht.edu.viewmodels.CoinViewModel
import com.anhht.edu.viewmodels.LevelViewModel
import com.anhht.edu.adapter.RVLevelAdapter


class LevelFragment : Fragment() {
    lateinit var rvMain: RecyclerView
    lateinit var levelApdater: RVLevelAdapter
    lateinit var levelViewModel:LevelViewModel
    private lateinit var coinViewModel: CoinViewModel
    private var data : List<Level>?=null


    private lateinit var binding: FragmentLevelBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
            rvMain = binding.levelRecycleView
            rvMain.layoutManager = LinearLayoutManager(context)
            levelViewModel = LevelViewModel(LevelAPIService())

            levelViewModel.getAllUsers(context as Context).observe(viewLifecycleOwner) { userList ->
                data = userList
                levelApdater = RVLevelAdapter(context as Context, userList, object : IClickLevelCard {
                    override fun onClickItemLevelCard(level: Level) {
                        var bundle = Bundle()
                        bundle.putSerializable("level", level)
                        var fmt: FragmentTransaction = activity?.supportFragmentManager?.beginTransaction()!!
                        val topicFragment = TopicFragment()
                        topicFragment.arguments = bundle
                        fmt.apply {
                            replace(R.id.learnLayoutF, topicFragment, "topicF")
                            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            setReorderingAllowed(true)
                            addToBackStack("topicF")
                            activity?.supportFragmentManager!!.popBackStackImmediate()
                            commit() }
                    }
                })
                rvMain.adapter = levelApdater

                if(userList != null){
                    val sessionManager = SessionManager(requireContext())
                    if(sessionManager.fetchUserName() == null){
                        coinViewModel = CoinViewModel(CoinAPIService())
                        coinViewModel.getUserInformation(context as Context).observe(viewLifecycleOwner){d->
                            if(d != null){
                                sessionManager.saveUserName(d.data["name"].toString())
                                sessionManager.saveEmail(d.data["email"].toString())
                            }
                        }
                    }
                }
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        //levelViewModel.getAllUsers(context as Context).removeObserver(levelObserver)
        //levelViewModel.getAllUsers(context as Context).removeObservers(viewLifecycleOwner)
    }
    }
