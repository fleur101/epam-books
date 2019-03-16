package com.example.fleur101.epambooksapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.example.fleur101.epambooksapp.AppConstants.PROFILE_EMAIL_KEY;
import static com.example.fleur101.epambooksapp.AppConstants.PROFILE_FIRST_NAME_KEY;
import static com.example.fleur101.epambooksapp.AppConstants.PROFILE_LAST_NAME_KEY;
import static com.example.fleur101.epambooksapp.AppConstants.PROFILE_NEW_USER_KEY;
import static com.example.fleur101.epambooksapp.AppConstants.PROFILE_PHONE_KEY;
import static com.example.fleur101.epambooksapp.AppConstants.PROFILE_UID_KEY;

/**
 * Created by Assylkhanov Aslan on 16.03.2019.
 */
public class ProfileEditActivity extends BaseActivity {

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
    @BindView(R.id.tv_sign_info)
    TextView tvSignInfo;
    @BindView(R.id.clt_sign_info)
    ConstraintLayout cltSignInfo;
    //endregion

    private String uid;
    private boolean newUser;

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
        uid = intent.getStringExtra(PROFILE_UID_KEY);
        newUser = intent.getBooleanExtra(PROFILE_NEW_USER_KEY, false);
        if (!newUser) {
            cltSignInfo.setVisibility(View.GONE);
        } else {
            tvSignInfo.setText(getString(R.string.you_signed_as, edtEmail.getText().toString()));
        }

        btnCancel.setOnClickListener(view -> signOut());
        btnConfirm.setOnClickListener(view -> populateDataToDatabase(newUser));
    }

    private void populateDataToDatabase(boolean newUser) {
        showLoader(true);
        Map<String, Object> user = new HashMap<>();
        user.put("firstname", edtFirstName.getText().toString());
        user.put("lastname", edtLastName.getText().toString());
        user.put("email", edtEmail.getText().toString());
        user.put("phone", edtPhone.getText().toString());
        user.put("uid", uid);

        DocumentReference userRef = FirebaseFirestore.getInstance().document("users/" + uid);
        if (!newUser) {
            userRef.update(user).addOnCompleteListener(completeListener);
        } else {
            userRef.set(user).addOnCompleteListener(completeListener);
        }
    }

    private OnCompleteListener completeListener = task -> {
        showLoader(false);
        Timber.e("Complete");
        if (task.isSuccessful()) {
            Timber.e("Successful");
            startActivity(new Intent(this, MainActivity.class));
        } else {
            Toast.makeText(this, R.string.error_occurred_try_again, Toast.LENGTH_SHORT).show();
        }
    };

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.id_token))
                .requestEmail()
                .build();
        GoogleSignIn.getClient(this, gso).signOut().addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                onBackPressed();
                Timber.e("Success");
            } else {
                Timber.e("Fail");
            }
        });
    }
}
