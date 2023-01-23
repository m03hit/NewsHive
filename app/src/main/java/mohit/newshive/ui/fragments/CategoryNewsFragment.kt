package mohit.newshive.ui.fragments

import android.os.Build.VERSION_CODES.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import mohit.newshive.NewsAdapter
import mohit.newshive.R
import mohit.newshive.databinding.FragmentCategoryNewsBinding
import mohit.newshive.model.Article
import mohit.newshive.viewmodel.NewsViewModel

@AndroidEntryPoint
class CategoryNewsFragment : Fragment() {
    private var _binding: FragmentCategoryNewsBinding? = null
    private val binding get() = _binding!!
    private val newsViewModel: NewsViewModel by viewModels()
    private lateinit var newsAdapter: NewsAdapter
    private var listOfNewsArticles = arrayListOf<Article>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.businessNewsButtonCategoryFragment.setOnClickListener {
            newsViewModel.getCategoryTopNews("business")
        }
        binding.entertainmentNewsButtonCategoryFragment.setOnClickListener {
            newsViewModel.getCategoryTopNews("entertainment")
        }
        binding.generalNewsButtonCategoryFragment.setOnClickListener {
            newsViewModel.getCategoryTopNews("general")
        }
        binding.healthNewsButtonCategoryFragment.setOnClickListener {
            newsViewModel.getCategoryTopNews("health")
        }
        binding.scienceNewsButtonCategoryFragment.setOnClickListener {
            newsViewModel.getCategoryTopNews("science")
        }
        binding.sportsNewsButtonCategoryFragment.setOnClickListener {
            newsViewModel.getCategoryTopNews("sports")
        }
        binding.technologyNewsButtonCategoryFragment.setOnClickListener {
            newsViewModel.getCategoryTopNews("technology")
        }
        newsViewModel.categoryTopNews.observe(viewLifecycleOwner){
            listOfNewsArticles= it.articles as ArrayList<Article>
            newsAdapter.updateList(listOfNewsArticles)
        }
        initRecyclerView()
    }



    private fun initRecyclerView() {
        newsAdapter = NewsAdapter(listOfNewsArticles,NewsAdapter.OnClickListener {
            val bundle = Bundle()
            bundle.putString("url",it.url)
            NavHostFragment.findNavController(this).navigate(mohit.newshive.R.id.action_categoryNewsFragment_to_newsViewFragment,bundle)

        })
        binding.rvCategoryNewsFragment.adapter=newsAdapter
        binding.rvCategoryNewsFragment.layoutManager= LinearLayoutManager(activity)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}