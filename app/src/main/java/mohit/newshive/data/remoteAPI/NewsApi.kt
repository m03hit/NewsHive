package mohit.newshive.data.remoteAPI

import mohit.newshive.model.News
import mohit.newshive.model.NewsSource
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface NewsApi {

    @Headers("X-Api-Key:74bd53d65c814dd696dbacb31d8441a1")
    @GET("/v2/top-headlines/sources")
    suspend fun getNewsSources(): Response<NewsSource>


    @Headers("X-Api-Key:74bd53d65c814dd696dbacb31d8441a1")
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(@Query(value = "sources", encoded = true) sources:String,@Query("pageSize") pageSize:Int=100): Response<News>

    @Headers("X-Api-Key:74bd53d65c814dd696dbacb31d8441a1")
    @GET("v2/top-headlines")
    suspend fun getCategoryTopNews(@Query("category")category:String,@Query("pageSize") pageSize:Int=100,@Query("country") country:String="in"): Response<News>


    @Headers("X-Api-Key:74bd53d65c814dd696dbacb31d8441a1")
    @GET("v2/everything")
    suspend fun searchNews(@Query("q") searchQuery: String,@Query("language")language: String="en"): Response<News>
}