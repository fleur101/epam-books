package com.example.fleur101.epambooksapp;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.widget.Toast;

/**
 * Created by Assylkhanov Aslan on 16.03.2019.
 */
public class BaseFragment extends Fragment {

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

}
