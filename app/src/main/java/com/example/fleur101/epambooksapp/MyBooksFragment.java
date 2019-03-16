package com.example.fleur101.epambooksapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class MyBooksFragment extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_my_books, container, false);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(view1 -> startActivityForResult(new Intent(getContext(), ScannerAcitivity.class), 1));
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {
                String barcode = data.getStringExtra("barcode");
                setText(barcode);
                //your code

            }
            if (resultCode == RESULT_CANCELED) {
                // Write your code if there's no result
                Toast.makeText(getContext(), "Try again", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setText(String text){
        TextView t = (TextView)getView().findViewById(R.id.result_text);
        t.setText(text);
    }

}
