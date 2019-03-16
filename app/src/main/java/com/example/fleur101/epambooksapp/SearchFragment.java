package com.example.fleur101.epambooksapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fleur101.epambooksapp.ApiModel.ApiModel;

import java.io.IOException;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.fleur101.epambooksapp.Utils.GOOGLE_BOOKS_API_KEY;


public class SearchFragment extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        App.getBooksApi().getData("isbn:" + "9780071636087", GOOGLE_BOOKS_API_KEY).enqueue(
                new Callback<ApiModel>() {
                    @Override
                    public void onResponse(Call<ApiModel> call, Response<ApiModel> response) {
                        if (response.body() != null) {
                            setText(response.body().getItems().get(0).getVolumeInfo().getTitle());
                        } else {
                            setText("No data");
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiModel> call, Throwable t) {
                        setText("failed");
                    }
                }
        );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    public void setText(String text){
        TextView t = (TextView)getView().findViewById(R.id.api_resp);
        t.setText(text);
    }


}
