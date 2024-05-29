package com.anhht.edu.views.reward

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anhht.edu.databinding.FragmentRewardBinding
import com.anhht.edu.model.data.Product
import com.anhht.edu.repository.ProductAPIService
import com.anhht.edu.viewmodels.ProductViewModel
import com.anhht.edu.adapter.RVProductAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.Normalizer
import java.util.Locale
import java.util.regex.Pattern

class RewardFragment : Fragment() {
    private lateinit var binding : FragmentRewardBinding
    private lateinit var rvProduct : RecyclerView
    private lateinit var productViewModel : ProductViewModel
    private lateinit var productAdapter : RVProductAdapter
    private var queryTextChangedJob: Job? = null
    private var products : List<Product>?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRewardBinding.inflate(inflater)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        rvProduct = binding.rewardRV

        rvProduct.layoutManager = GridLayoutManager(context, 2)

        productViewModel = ProductViewModel(ProductAPIService())
        productViewModel.getAllProducts(context as Context).observe(viewLifecycleOwner){ data->
            products = data
            productAdapter = RVProductAdapter(context as Context, data, object :
                RVProductAdapter.IClickProductCard {
                override fun onClickItemProductCard(product: Product) {
                    val intent = Intent(requireContext(), ConfirmRewardActivity::class.java)
                    intent.putExtra("product", product)
                    activity?.startActivity(intent)
                }
            })
            rvProduct.adapter = productAdapter
        }

        val searchView = binding.searchProduct
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                queryTextChangedJob?.cancel()
                queryTextChangedJob = lifecycleScope.launch(Dispatchers.Main) {
                    delay(500)
                    filterProduct(changeUnicode(newText.toLowerCase(Locale.ROOT)))
                }
                return false
            }
        })
    }
    fun changeUnicode(str: String?): String {
        var temp: String = Normalizer.normalize(str, Normalizer.Form.NFD)
        val pattern: Pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
        temp = pattern.matcher(temp).replaceAll("") //loại bỏ
        return temp.replace("đ".toRegex(), "d").replace("Đ".toRegex(), "D")
    }
    fun filterProduct(text: String){
        if(products != null){
            val filter = ArrayList<Product>()
            for( i in products!!){
                if(changeUnicode(i.name.toLowerCase(Locale.ROOT)).contains(text)){
                    filter.add(i)
                }
            }
            if(filter.isEmpty()){
                Toast.makeText(context, "Không có kết quả phù hợp.", Toast.LENGTH_SHORT).show()
            }else{
                productAdapter.setFilteredList(filter)
            }
        }

    }

}