package com.example.fleur101.epambooksapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.example.fleur101.epambooksapp.AppConstants.PROFILE_EMAIL_KEY;
import static com.example.fleur101.epambooksapp.AppConstants.PROFILE_FIRST_NAME_KEY;
import static com.example.fleur101.epambooksapp.AppConstants.PROFILE_IMAGE_KEY;
import static com.example.fleur101.epambooksapp.AppConstants.PROFILE_LAST_NAME_KEY;
import static com.example.fleur101.epambooksapp.AppConstants.PROFILE_NEW_USER_KEY;
import static com.example.fleur101.epambooksapp.AppConstants.PROFILE_PHONE_KEY;

/**
 * Created by Assylkhanov Aslan on 15.03.2019.
 */
public class LoginActivity extends BaseActivity {

    private static final int RC_GOOGLE_SIGN_IN = 42;

    //region ViewBinding
    @BindView(R.id.btn_google)
    Button btnGoogle;
    @BindView(R.id.btn_facebook)
    Button btnFacebook;
    @BindView(R.id.btn_facebook_original)
    LoginButton loginButton;
    //endregion

    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        googleSetup();
        if (mAuth.getCurrentUser() != null) {
            startMainActivity();
            Timber.e("Signing out");
            mGoogleSignInClient.signOut().addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    Timber.e("Success");
                } else {
                    Timber.e("Fail");
                }
            });
        }

        btnGoogle.setOnClickListener(view -> googleSignIn());
        btnFacebook.setOnClickListener(view -> loginButton.performClick());

        //region TODO: Finish the Facebook
        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();

        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Timber.e("facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
                Profile profile = Profile.getCurrentProfile();
                Timber.e("Profile. Name=%s, LastName=%s", profile.getFirstName(), profile.getLastName());
                // App code
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        (object, response) -> {
                            Log.v("LoginActivity", response.toString());
                            try {
                                String email = object.getString("email");
                                Timber.e("Email = %s", email);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                Timber.d("facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Timber.d("facebook:onError", error);
                // ...
            }
        });
        //endregion
    }

    private void googleSetup() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.id_token))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        Timber.e("onActivityResult");
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Timber.e("Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        showLoader(true);
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        checkIfUserExists(user.getUid(), acct.getGivenName(), acct.getFamilyName(), acct.getEmail(), null, acct.getPhotoUrl()   );
                    } else {
                        showLoader(false);
                        Snackbar.make(btnFacebook, R.string.google_login_error, Snackbar.LENGTH_LONG).show();
                    }
                });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        checkIfUserExists(user.getUid(), null, null, null, null, null);
                    } else {
                        Snackbar.make(btnFacebook, R.string.google_login_error, Snackbar.LENGTH_LONG).show();
                    }
                });
    }

    private void checkIfUserExists(String uid, String firstName, String lastName, String email, String phone, Uri photoUrl) {
        FirebaseFirestore.getInstance().collection("users")
                .whereEqualTo("uid", uid)
                .get()
                .addOnCompleteListener(task -> {
                    showLoader(false);
                    if (task.isSuccessful()) {
                        Timber.e("Successful");
                        if (task.getResult().getDocuments().isEmpty()) {
                            Timber.e("Doesn't exist");
                            Intent intent = new Intent(this, ProfileActivity.class);
                            intent.putExtra(PROFILE_NEW_USER_KEY, true);
                            intent.putExtra(PROFILE_FIRST_NAME_KEY, firstName);
                            intent.putExtra(PROFILE_LAST_NAME_KEY, lastName);
                            intent.putExtra(PROFILE_EMAIL_KEY, email);
                            intent.putExtra(PROFILE_PHONE_KEY, phone);
                            intent.putExtra(PROFILE_IMAGE_KEY, photoUrl.toString());
                            startActivity(intent);
                        } else {
                            Timber.e("Open the main");
                            startMainActivity();
                        }
                    }
                });
    }

    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }

}
