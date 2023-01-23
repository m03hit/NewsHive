package mohit.newshive.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import mohit.newshive.NewsAdapter
import mohit.newshive.R
import mohit.newshive.databinding.FragmentNewsViewBinding
import mohit.newshive.databinding.FragmentSearchNewsBinding
import mohit.newshive.model.Article
import mohit.newshive.viewmodel.NewsViewModel

class NewsViewFragment : Fragment() {
    private var _binding: FragmentNewsViewBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = arguments?.getString("url")
        if (url != null) {
            binding.webViewNewsViewfragment.loadUrl(url)
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
