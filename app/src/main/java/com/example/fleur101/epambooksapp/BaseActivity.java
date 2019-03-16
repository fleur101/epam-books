package com.example.fleur101.epambooksapp;

import android.app.ProgressDialog;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Assylkhanov Aslan on 17.03.2019.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected ProgressDialog dialog;

    /**
     * Displays / Hides progress dialog.
     *
     * @param show Show dialog if true, hides it if false.
     */
    public void showLoader(boolean show) {
        if (show) {
            dialog = ProgressDialog
                    .show(this, null, getString(R.string.searching_through_your_books), true);
        } else if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }

    }
}
