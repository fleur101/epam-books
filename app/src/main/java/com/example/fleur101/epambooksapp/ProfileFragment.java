package com.example.fleur101.epambooksapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fleur101.epambooksapp.models.Profile;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.example.fleur101.epambooksapp.AppConstants.PROFILE_EMAIL_KEY;
import static com.example.fleur101.epambooksapp.AppConstants.PROFILE_FIRST_NAME_KEY;
import static com.example.fleur101.epambooksapp.AppConstants.PROFILE_IMAGE_KEY;
import static com.example.fleur101.epambooksapp.AppConstants.PROFILE_LAST_NAME_KEY;
import static com.example.fleur101.epambooksapp.AppConstants.PROFILE_NEW_USER_KEY;
import static com.example.fleur101.epambooksapp.AppConstants.PROFILE_PHONE_KEY;


public class ProfileFragment extends BaseFragment {

    //region View Bindings
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
    @BindView(R.id.iv_profile)
    ImageView ivProfile;
    //endregion

    private String uid;
    private boolean newUser;
    private DocumentReference userRef;
    private String profileImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);

        uid = FirebaseAuth.getInstance().getUid();
        ivProfile.setImageResource(R.color.colorGoogle);

        Intent intent = getActivity().getIntent();
        newUser = intent.getBooleanExtra(PROFILE_NEW_USER_KEY, false);

        userRef = FirebaseFirestore.getInstance().document("users/" + uid);
        if (!newUser) {
            getProfileData();
        } else {
            FirebaseAuth.getInstance().signOut();
            prePopulateData();
        }

        btnCancel.setOnClickListener(v -> getActivity().onBackPressed());
        btnConfirm.setOnClickListener(v -> populateDataToDatabase(newUser));

        return view;
    }

    private void getProfileData() {
        showLoader(true);
        Timber.e("Uid = %s", uid);
        userRef = FirebaseFirestore.getInstance().document("users/" + uid);
        userRef.get().addOnCompleteListener(task -> {
            showLoader(false);
            if (task.isSuccessful()) {
                Profile profile = task.getResult().toObject(Profile.class);
                if (profile != null) {
                    edtEmail.setText(profile.getEmail());
                    edtFirstName.setText(profile.getFirstname());
                    edtLastName.setText(profile.getLastname());
                    edtPhone.setText(profile.getPhone());
                    tvSignInfo.setText(getString(R.string.you_signed_as, edtEmail.getText().toString()));
                    Glide.with(getContext())
                            .load(profile.getPhotoUrl())
                            .placeholder(R.color.colorGoogle)
                            .into(ivProfile);
                }
            } else {
                Toast.makeText(getActivity(), R.string.error_occurred_try_again, Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        });
    }

    private void prePopulateData() {
        Intent intent = getActivity().getIntent();
        newUser = intent.getBooleanExtra(PROFILE_NEW_USER_KEY, false);

        edtFirstName.setText(intent.getStringExtra(PROFILE_FIRST_NAME_KEY));
        edtLastName.setText(intent.getStringExtra(PROFILE_LAST_NAME_KEY));
        edtEmail.setText(intent.getStringExtra(PROFILE_EMAIL_KEY));
        edtPhone.setText(intent.getStringExtra(PROFILE_PHONE_KEY));
        profileImage = intent.getStringExtra(PROFILE_IMAGE_KEY);
        Timber.e("Profile Image = %s", profileImage);
        tvSignInfo.setText(getString(R.string.you_signed_as, edtEmail.getText().toString()));

        Glide.with(getContext())
                .load(intent.getStringExtra(PROFILE_IMAGE_KEY))
                .placeholder(R.color.colorGoogle)
                .into(ivProfile);
    }

    private void populateDataToDatabase(boolean newUser) {
        showLoader(true);
        Map<String, Object> user = new HashMap<>();
        user.put("firstname", edtFirstName.getText().toString());
        user.put("lastname", edtLastName.getText().toString());
        user.put("email", edtEmail.getText().toString());
        user.put("phone", edtPhone.getText().toString());
        user.put("uid", uid);
        user.put("photoUrl", profileImage);

        if (!newUser) {
            userRef.update(user).addOnCompleteListener(onUploadListener);
        } else {
            userRef.set(user).addOnCompleteListener(onUploadListener);
        }
    }

    private OnCompleteListener onUploadListener = task -> {
        showLoader(false);
        Timber.e("Complete");
        if (task.isSuccessful()) {
            Timber.e("Successful");
            startActivity(new Intent(getActivity(), MainActivity.class));
        } else {
            Toast.makeText(getActivity(), R.string.error_occurred_try_again, Toast.LENGTH_SHORT).show();
        }
    };

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.id_token))
                .requestEmail()
                .build();
        GoogleSignIn.getClient(getActivity(), gso).signOut().addOnCompleteListener(getActivity(), task -> {
            if (task.isSuccessful()) {
                Timber.e("Success");
            } else {
                Timber.e("Fail");
            }
        });
    }

}
