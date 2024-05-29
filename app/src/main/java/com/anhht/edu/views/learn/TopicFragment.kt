package com.anhht.edu.views.learn

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anhht.edu.databinding.FragmentTopicBinding
import com.anhht.edu.model.data.Level
import com.anhht.edu.model.data.Topic
import com.anhht.edu.repository.TopicAPIService
import com.anhht.edu.viewmodels.TopicViewModel
import com.anhht.edu.adapter.RVTopicAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.Normalizer
import java.util.Locale
import java.util.regex.Pattern

class TopicFragment : Fragment() {
    private lateinit var rvMain: RecyclerView
    private lateinit var topicAdapter: RVTopicAdapter
    private lateinit var topicViewModel: TopicViewModel
    private var listTopic: List<Topic>? = null
    private var queryTextChangedJob: Job? = null
    private lateinit var binding: FragmentTopicBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTopicBinding.inflate(inflater, container, false)
        rvMain = binding.levelRecycleView
        rvMain.layoutManager = LinearLayoutManager(context)

        val bundleReceive = arguments
        val level = bundleReceive?.get("level") as Level

        binding.actionBarTopicName.text = "Chủ đề " + level.levelName.split("thaidang")[0]
        topicViewModel = TopicViewModel(TopicAPIService())
        topicViewModel.getTopicByLid(context as Context, level.lid).observe(viewLifecycleOwner) { topic ->
            listTopic = topic
            topicAdapter =
                RVTopicAdapter(context as Context, topic, object : RVTopicAdapter.IClickTopicCard {
                    override fun onClickItemTopicCard(topic: Topic) {
                        val intent = Intent(requireContext(), LearnActivity::class.java)
                        intent.putExtra("topic", topic)
                        activity?.startActivity(intent)
                    }
                })
            rvMain.adapter = topicAdapter
        }
        binding.topicBack.setOnClickListener {
            val index = requireActivity().supportFragmentManager.backStackEntryCount - 1
            val backEntry = activity?.supportFragmentManager?.getBackStackEntryAt(index)
            val tag = backEntry?.name
            val fragment = activity?.supportFragmentManager?.findFragmentByTag(tag)
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                remove(fragment!!)
                commit()
            }
            activity?.supportFragmentManager?.popBackStack()
        }

        val searchView = binding.searchTopic
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                queryTextChangedJob?.cancel()
                queryTextChangedJob = lifecycleScope.launch(Dispatchers.Main) {
                    delay(500)
                    filterTopic(changeUnicode(newText.toLowerCase(Locale.ROOT)))
                }
                return false
            }
        })
        return binding.root
    }

    private fun changeUnicode(str: String?): String {
        var temp: String = Normalizer.normalize(str, Normalizer.Form.NFD)
        val pattern: Pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
        temp = pattern.matcher(temp).replaceAll("") //loại bỏ
        return temp.replace("đ".toRegex(), "d").replace("Đ".toRegex(), "D")
    }

    fun filterTopic(text: String) {
        if (listTopic != null) {
            val filter = ArrayList<Topic>()
            for (i in listTopic!!) {
                if (changeUnicode(i.topic.toLowerCase(Locale.ROOT)).contains(text)) {
                    filter.add(i)
                }
            }

            if (filter.isEmpty()) {
                Toast.makeText(context, "Không có kết quả phù hợp.", Toast.LENGTH_SHORT).show()
            } else {
                topicAdapter.setFilterList(filter)
            }
        }
    }
}