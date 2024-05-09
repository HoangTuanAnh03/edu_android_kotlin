package com.anhht.edu.views.learn

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.anhht.edu.views.Adapter.RVLevelAdapter.IClickLevelCard
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anhht.edu.R
import com.anhht.edu.databinding.FragmentLevelBinding
import com.anhht.edu.model.data.Level
import com.anhht.edu.repository.LevelAPIService
import com.anhht.edu.viewmodels.LevelViewModel
import com.anhht.edu.views.Adapter.RVLevelAdapter


class LevelFragment : Fragment() {
    lateinit var rvMain: RecyclerView
    lateinit var levelApdater: RVLevelAdapter
    lateinit var levelViewModel:LevelViewModel

    private lateinit var binding: FragmentLevelBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLevelBinding.inflate(inflater, container, false)
        rvMain = binding.levelRecycleView
        rvMain.layoutManager = LinearLayoutManager(context)

        //lay data tu viewmodel

        levelViewModel = LevelViewModel(LevelAPIService())
        levelViewModel.getAllUsers(context as Context).observe(viewLifecycleOwner) { userList ->
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
            rvMain.setAdapter(levelApdater)
        }
        return binding.root
    }
}