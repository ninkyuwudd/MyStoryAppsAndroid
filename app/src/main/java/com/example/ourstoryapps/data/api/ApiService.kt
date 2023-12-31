package com.example.ourstoryapps.data.api


import com.example.ourstoryapps.data.model.ResponseDetailStory
import com.example.ourstoryapps.data.model.ResponseLogin
import com.example.ourstoryapps.data.model.ResponseRegister
import com.example.ourstoryapps.data.model.ResponseStory
import com.example.ourstoryapps.data.model.ResponseStoryUp
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<ResponseRegister>


    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<ResponseLogin>


    @GET("stories")
    fun getStories(

    ): Call<ResponseStory>


    @GET("stories/{id}")
    fun getDataStories(
        @Path("id") id:String
    ): Call<ResponseDetailStory>



    @Multipart
    @POST("stories")
    fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<ResponseStoryUp>


    @GET("stories")
    fun getStoriesWithLocation(
        @Query("location") location : Int = 1,
    ): Call<ResponseStory>


    @GET("stories")
    suspend fun getStoriesPagging(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): ResponseStory

}