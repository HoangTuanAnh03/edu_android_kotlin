package com.anhht.edu.views.learn

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anhht.edu.databinding.FragmentTopicBinding
import com.anhht.edu.model.data.Level
import com.anhht.edu.repository.TopicAPIService
import com.anhht.edu.viewmodels.TopicViewModel
import com.anhht.edu.views.Adapter.RVTopicAdapter
import com.squareup.picasso.Picasso

class TopicFragment : Fragment() {
    private lateinit var rvMain: RecyclerView
    private lateinit var topicApdater: RVTopicAdapter
    private lateinit var topicViewModel: TopicViewModel

    private lateinit var binding: FragmentTopicBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTopicBinding.inflate(inflater, container, false)
        rvMain = binding.levelRecycleView
        rvMain.layoutManager = LinearLayoutManager(context)
        var bundbleReceive = arguments
        var level = bundbleReceive?.get("level") as Level

        Picasso.with(context).load("https://imgur.com/"+level.levelName.split("thaidang")[1]).into(binding.topicLevelImg)
        binding.topicLevelDesc.text = "Danh sách từ vựng "+ level.levelName.split("thaidang")[0] +" bao gồm "+level.numTopics+" bài học và "+ level.numWords + " từ vựng được phân loại theo chủ đề, độ khó và cách sử dụng theo CEFR."
        topicViewModel = TopicViewModel(TopicAPIService())
        topicViewModel.getTopicByLid(level.lid).observe(viewLifecycleOwner) { topic ->
            topicApdater = RVTopicAdapter(context as Context, topic)
            rvMain.adapter = topicApdater
        }
        return binding.root
    }
}