package com.example.fleur101.epambooksapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.fleur101.epambooksapp.AppConstants.PROFILE_EMAIL_KEY;
import static com.example.fleur101.epambooksapp.AppConstants.PROFILE_FIRST_NAME_KEY;
import static com.example.fleur101.epambooksapp.AppConstants.PROFILE_LAST_NAME_KEY;
import static com.example.fleur101.epambooksapp.AppConstants.PROFILE_PHONE_KEY;

/**
 * Created by Assylkhanov Aslan on 16.03.2019.
 */
public class ProfileEditActivity extends Activity {

    //region View Bindings
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_first_name)
    TextInputEditText edtFirstName;
    @BindView(R.id.edt_last_name)
    TextInputEditText edtLastName;
    @BindView(R.id.edt_email)
    TextInputEditText edtEmail;
    @BindView(R.id.edt_phone)
    TextInputEditText edtPhone;
    @BindView(R.id.btn_cancel)
    TextView btnCancel;
    @BindView(R.id.btn_confirm)
    TextView btnConfirm;
    //endregion

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        edtFirstName.setText(intent.getStringExtra(PROFILE_FIRST_NAME_KEY));
        edtLastName.setText(intent.getStringExtra(PROFILE_LAST_NAME_KEY));
        edtEmail.setText(intent.getStringExtra(PROFILE_EMAIL_KEY));
        edtPhone.setText(intent.getStringExtra(PROFILE_PHONE_KEY));

        btnCancel.setOnClickListener(view -> onBackPressed());
        btnConfirm.setOnClickListener(view -> populateDataToDatabase(false));
    }

    private void populateDataToDatabase(boolean update) {
        if (update) {

        } else {

        }
    }
}