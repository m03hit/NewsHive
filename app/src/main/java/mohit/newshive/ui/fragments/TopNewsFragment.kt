package mohit.newshive.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import mohit.newshive.NewsAdapter
import mohit.newshive.R
import mohit.newshive.databinding.FragmentSetupBinding
import mohit.newshive.databinding.FragmentTopNewsBinding
import mohit.newshive.model.Article
import mohit.newshive.model.News
import mohit.newshive.utils.Constants
import mohit.newshive.viewmodel.NewsViewModel
import java.lang.reflect.Type

@AndroidEntryPoint
class TopNewsFragment : Fragment() {
    private var _binding: FragmentTopNewsBinding? = null
    private val binding get() = _binding!!
    private val newsViewModel: NewsViewModel by viewModels()
    private lateinit var newsAdapter:NewsAdapter
    private var listOfNewsArticles = arrayListOf<Article>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel.topHeadlines.observe(viewLifecycleOwner){
            listOfNewsArticles= it.articles as ArrayList<Article>
            newsAdapter.updateList(listOfNewsArticles)
        }
        getNewsSourcesList()?.let { newsViewModel.getTopHeadlines(it.joinToString(separator = ",")) }
        initRecyclerView()
    }

    private fun getNewsSourcesList(): java.util.ArrayList<String?>? {
        val prefs = this.activity?.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE)
        val gson = Gson()
        val json: String? = prefs?.getString("newsSources", null)
        val type: Type = object : TypeToken<java.util.ArrayList<String?>?>() {}.type
        return gson.fromJson(json, type)
    }

    private fun initRecyclerView() {
        newsAdapter = NewsAdapter(listOfNewsArticles,NewsAdapter.OnClickListener {
            val bundle = Bundle()
            bundle.putString("url",it.url)
            NavHostFragment.findNavController(this).navigate(R.id.action_topNewsFragment_to_newsViewFragment,bundle)
        })
        binding.rvTopNewsFragment.adapter=newsAdapter
        binding.rvTopNewsFragment.layoutManager=LinearLayoutManager(activity)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}