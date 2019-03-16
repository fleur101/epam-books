package com.example.fleur101.epambooksapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

/**
 * Created by Assylkhanov Aslan on 15.03.2019.
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btnGoogle = findViewById(R.id.btn_google);
        Button btnFacebook = findViewById(R.id.btn_facebook);
        btnGoogle.setOnClickListener(view -> startActivity(new Intent(this, MainActivity.class)));
        btnFacebook.setOnClickListener(view -> startActivity(new Intent(this, MainActivity.class)));
    }
}
