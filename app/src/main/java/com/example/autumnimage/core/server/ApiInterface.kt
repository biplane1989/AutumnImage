package kotlincodes.com.retrofitwithkotlin.retrofit

import com.unsplash.pickerandroid.photopicker.data.UnsplashPhoto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


interface ApiInterface {

    @GET("/photos/")
    suspend fun getPhotos(@Query("page") page: Int = 1, @Query("per_page") perPage: Int = 12, @Query("client_id") clientID: String = "ZCrQRuxnXBxzR_sl0WeHvj9nMEdw5y-ySr5wbWDp7Sw"): ArrayList<UnsplashPhoto>

    @GET
    suspend fun downloadPhoto(@Url url: String): ResponseBody
}