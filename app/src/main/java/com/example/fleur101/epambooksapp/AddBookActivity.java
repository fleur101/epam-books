package com.example.fleur101.epambooksapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

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
    //endregion

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        ButterKnife.bind(this);

//        TODO. Mirka: For image load use
//        Glide.with(this)
//                .load("Your image url here")
//                .placeholder(R.drawable.bg_book_cover_gradient)
//                .into(ivCover);
    }

}
