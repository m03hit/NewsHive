package mohit.newshive.ui.fragments

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import mohit.newshive.NewsAdapter
import mohit.newshive.R
import mohit.newshive.databinding.FragmentSearchNewsBinding
import mohit.newshive.model.Article
import mohit.newshive.utils.Constants
import mohit.newshive.viewmodel.NewsViewModel
import java.lang.reflect.Type


@AndroidEntryPoint
class SearchNews : Fragment() {
    private var _binding: FragmentSearchNewsBinding? = null
    private val binding get() = _binding!!
    private val newsViewModel: NewsViewModel by viewModels()
    private lateinit var newsAdapter: NewsAdapter
    private var listOfNewsArticles = arrayListOf<Article>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsViewModel.searchedNews.observe(viewLifecycleOwner){
            if(it!==null)
            {
                listOfNewsArticles= it.articles as ArrayList<Article>
                newsAdapter.updateList(listOfNewsArticles)
            }
        }
        initRecyclerView()

        val searchView: SearchView = binding.searchViewSearchNewsFragment
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null ) {
                    newsViewModel.getSearchedNews(newText)
                }
                return false
            }
        })
    }



    private fun initRecyclerView() {
        newsAdapter = NewsAdapter(listOfNewsArticles,NewsAdapter.OnClickListener {
            val bundle = Bundle()
            bundle.putString("url",it.url)
            NavHostFragment.findNavController(this).navigate(R.id.action_navigation_search_news_to_newsViewFragment,bundle)
        })
        binding.rvSearchFragment.adapter=newsAdapter
        binding.rvSearchFragment.layoutManager= LinearLayoutManager(activity)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}