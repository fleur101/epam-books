package com.example.fleur101.epambooksapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Assylkhanov Aslan on 15.03.2019.
 */
public class LoginActivity extends AppCompatActivity {

    //region ViewBinding
    @BindView(R.id.btn_google)
    Button btnGoogle;
    @BindView(R.id.btn_facebook)
    Button btnFacebook;
    //endregion

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        btnGoogle.setOnClickListener(view -> startActivity(new Intent(this, MainActivity.class)));
        btnFacebook.setOnClickListener(view -> startActivity(new Intent(this, MainActivity.class)));
    }
}
