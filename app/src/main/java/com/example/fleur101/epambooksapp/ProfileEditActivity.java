package com.example.fleur101.epambooksapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Assylkhanov Aslan on 16.03.2019.
 */
public class ProfileEditActivity extends BaseActivity {

    //region View Bindings
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    //endregion

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new ProfileFragment());
        fragmentTransaction.commitAllowingStateLoss();

    }
}
