package mohit.newshive.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mohit.newshive.data.repository.NewsRepository
import mohit.newshive.model.News
import mohit.newshive.model.NewsSource
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {

    private val _topHeadlines = MutableLiveData<News>()
    val topHeadlines: LiveData<News> = _topHeadlines

    private val _searchedNews = MutableLiveData<News>()
    val searchedNews: LiveData<News> = _searchedNews

    private val _newsSources = MutableLiveData<NewsSource>()
    val newsSources: LiveData<NewsSource> = _newsSources

    private val _categoryTopNews = MutableLiveData<News>()
    val categoryTopNews: LiveData<News> = _categoryTopNews

    fun getTopHeadlines(sourcesString: String) {
        viewModelScope.launch {
            val response = newsRepository.getTopHeadlines(sourcesString)
            _topHeadlines.postValue(response.body())
        }
    }

    fun getSearchedNews(query:String) {
        viewModelScope.launch {
            val response = newsRepository.searchNews(query)
            _searchedNews.postValue(response.body())
        }
    }

    fun getNewsSources() {
        viewModelScope.launch {
            val response = newsRepository.getNewsSources()
            _newsSources.postValue(response.body())
        }
    }

    fun getCategoryTopNews(category:String){
        viewModelScope.launch {
            val response = newsRepository.getCategoryTopNews(category)
            _categoryTopNews.postValue(response.body())
        }
    }

}
