package com.example.fleur101.epambooksapp;

import com.example.fleur101.epambooksapp.ApiModel.ApiModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface BooksApi {
    @GET("books/v1/volumes")
    Call<ApiModel> getData(@Query("q") String q, @Query("key") String key);
}
