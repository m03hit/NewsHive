package mohit.newshive.data.repository

import mohit.newshive.data.remoteAPI.NewsApi
import mohit.newshive.model.News
import mohit.newshive.model.NewsSource
import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsApi: NewsApi) {

    suspend fun getNewsSources(): Response<NewsSource> {
        return newsApi.getNewsSources()
    }

    suspend fun getTopHeadlines(sources: String): Response<News> {
        return newsApi.getTopHeadlines(sources)
    }

    suspend fun searchNews(searchQuery: String): Response<News> {
        return newsApi.searchNews(searchQuery)
    }

    suspend fun getCategoryTopNews(category:String) : Response<News> {
        return newsApi.getCategoryTopNews(category)
    }
}