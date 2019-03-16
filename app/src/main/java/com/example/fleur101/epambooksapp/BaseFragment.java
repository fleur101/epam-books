package com.example.fleur101.epambooksapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by Assylkhanov Aslan on 16.03.2019.
 */
public class BaseFragment extends Fragment {

    protected ProgressDialog dialog;
    private boolean isAttached;
    private boolean isOnPause;
    private String loaderText;

    public static BaseFragment newInstance(@Nullable Bundle args) {

        BaseFragment fragment = new BaseFragment();
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Displays a simple toast message.
     *
     * @param message message to be shown;
     */
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Displays a simple toast message.
     *
     * @param messageResourceId message to be shown;
     */
    public void showToast(int messageResourceId) {
        Toast.makeText(getActivity(), getString(messageResourceId), Toast.LENGTH_SHORT).show();
    }

    /**
     * Gets tag for stack.
     *
     * @return the tag for stack
     */
    public String getTagForStack() {
        return this.getClass().getCanonicalName();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        isAttached = true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        isAttached = false;
    }

    protected void setLoaderText(String text) {
        loaderText = text;
    }


    /**
     * Displays / Hides progress dialog.
     *
     * @param show Show dialog if true, hides it if false.
     */
    public void showLoader(boolean show) {
        if (isAttached) {
            if (show) {
                dialog = ProgressDialog
                        .show(getActivity(), null, loaderText != null ? loaderText : getString(R.string.loading_message), true);
            } else if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
        }
    }

}
