package com.example.fleur101.epambooksapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fleur101.epambooksapp.ApiModel.ApiModel;
import com.example.fleur101.epambooksapp.ApiModel.VolumeInfo;
import com.example.fleur101.epambooksapp.Barcode.ScannerAcitivity;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.fleur101.epambooksapp.Utils.GOOGLE_BOOKS_API_KEY;

/**
 * Created by Assylkhanov Aslan on 17.03.2019.
 */
public class AddBookActivity extends BaseActivity {

    //region View Bindings
    @BindView(R.id.iv_cover)
    ImageView ivCover;
    @BindView(R.id.edt_name)
    TextInputEditText edtName;
    @BindView(R.id.edt_author)
    TextInputEditText edtAuthor;
    @BindView(R.id.edt_publisher)
    TextInputEditText edtPublisher;
    @BindView(R.id.edt_date)
    TextInputEditText edtDate;
    @BindView(R.id.edt_description)
    TextInputEditText edtDescription;
    @BindView(R.id.btn_cancel)
    TextView btnCancel;
    @BindView(R.id.btn_confirm)
    TextView btnConfirm;
    @BindView(R.id.edt_isbn)
    TextInputEditText edtIsbn;
    //endregion

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        ButterKnife.bind(this);
        ImageButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view1 -> startActivityForResult(new Intent(this, ScannerAcitivity.class), 1));

//        TODO. Mirka: For image load use
//        Glide.with(this)
//                .load("Your image url here")
//                .placeholder(R.drawable.bg_book_cover_gradient)
//                .into(ivCover);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {
                String barcode = data.getStringExtra("barcode");
                edtIsbn.setText(barcode);

                App.getBooksApi().getData("isbn:" + "9780071636087", GOOGLE_BOOKS_API_KEY).enqueue(
                        new Callback<ApiModel>() {
                            @Override
                            public void onResponse(Call<ApiModel> call, Response<ApiModel> response) {
                                if (response.body() != null) {
                                    VolumeInfo volumeInfo = response.body().getItems().get(0).getVolumeInfo();
                                    edtAuthor.setText(volumeInfo.getAuthors().get(0));
                                    edtDescription.setText(volumeInfo.getDescription());
                                    edtDate.setText(volumeInfo.getPublishedDate());
                                    edtName.setText(volumeInfo.getTitle());
                                    edtPublisher.setText(volumeInfo.getPublisher());
                                } else {
                                    Toast.makeText(AddBookActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ApiModel> call, Throwable t) {
                                Toast.makeText(AddBookActivity.this, "you failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
                //your code

            }
            if (resultCode == RESULT_CANCELED) {
                // Write your code if there's no result
                Toast.makeText(this, "Try again", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
