package com.anhht.edu.views.learn

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anhht.edu.databinding.FragmentTopicBinding
import com.anhht.edu.model.data.Level
import com.anhht.edu.model.data.Topic
import com.anhht.edu.repository.TopicAPIService
import com.anhht.edu.viewmodels.TopicViewModel
import com.anhht.edu.views.Adapter.RVTopicAdapter
import com.squareup.picasso.Picasso

class TopicFragment : Fragment() {
    lateinit var rvMain: RecyclerView
    lateinit var topicApdater: RVTopicAdapter
    lateinit var topicViewModel: TopicViewModel

    private lateinit var binding: FragmentTopicBinding
    //    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        bindingLearnF = FragmentLearnBinding.inflate(in)
//    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTopicBinding.inflate(inflater, container, false)
        rvMain = binding.levelRecycleView
        rvMain.layoutManager = LinearLayoutManager(context)

        var bundbleReceive = arguments
        var level = bundbleReceive?.get("level") as Level
        Picasso.get().load("https://imgur.com/"+level.levelName.split("thaidang")[1]).into(binding.topicLevelImg)
        binding.topicLevelDesc.text = "Danh sách từ vựng "+ level.levelName.split("thaidang")[0] +" bao gồm "+level.numTopics+" bài học và "+ level.numWords + " từ vựng được phân loại theo chủ đề, độ khó và cách sử dụng theo CEFR."
        topicViewModel = TopicViewModel(TopicAPIService())
        topicViewModel.getTopicByLid(level.lid).observe(viewLifecycleOwner) { topic ->
            topicApdater = RVTopicAdapter(context as Context, topic, object : RVTopicAdapter.IClickTopicCard {
                override fun onClickItemTopicCard(topic: Topic) {
                    var intent = Intent(requireContext(), LearnActivity::class.java)
                    intent.putExtra("topic", topic)
                    Log.e("topic", topic.toString())
                    activity?.startActivity(intent)
                }
            })
            rvMain.setAdapter(topicApdater)
        }
        binding.topicBack.setOnClickListener{
            val index = requireActivity().supportFragmentManager.backStackEntryCount - 1
            val backEntry = activity?.supportFragmentManager?.getBackStackEntryAt(index)
            val tag = backEntry?.name;
            val fragment = activity?.supportFragmentManager?.findFragmentByTag(tag)
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                remove(fragment!!)
                commit()
            }
            activity?.supportFragmentManager?.popBackStack()
        }
        return binding.root
    }

}