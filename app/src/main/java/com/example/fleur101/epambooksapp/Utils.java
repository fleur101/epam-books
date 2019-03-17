package com.example.fleur101.epambooksapp;

import com.example.fleur101.epambooksapp.ApiModel.ApiModel;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Utils {
    public static String GOOGLE_BOOKS_API_KEY = "AIzaSyDSSWtQvzns2c3lOYdMPd5rZ93MG8_jCvI";


//    public static ApiModel getBookInfo(String isbn, void callback) throws IOException {
//        App.getBooksApi().getData("isbn:" + isbn, GOOGLE_BOOKS_API_KEY).enqueue(
//                new Callback<ApiModel>() {
//                    @Override
//                    public void onResponse(Call<ApiModel> call, Response<ApiModel> response) {
//                        return (ApiModel) response.body();
//                    }
//
//                    @Override
//                    public void onFailure(Call<ApiModel> call, Throwable t) {
//                        return null;
//                    }
//                }
//        );
//    }
}
