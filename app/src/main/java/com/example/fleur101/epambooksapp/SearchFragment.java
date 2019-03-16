package com.example.fleur101.epambooksapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchFragment extends BaseFragment {

    public static final String TAG = "SEARCH_FRAGMENT_TAG";
    private FirebaseFirestore db;
    private List<Book> mDataset;
    private BooksAdapter mAdapter;
    @BindView(R.id.rv_my_books) RecyclerView recyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        // using Linear Layout Manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        mDataset = new ArrayList<>();

        // using Linear Layout Manager
        mAdapter = new BooksAdapter(mDataset);
        recyclerView.setAdapter(mAdapter);

//        getData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        getData();
    }

    public void getData(){
        db = FirebaseFirestore.getInstance();
        db.collection("books")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Book> data = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Book book = document.toObject(Book.class);
//                            mDataset.add(book);
                            data.add(book);
                            Log.d(TAG, document.getId() + " => " + document.getData());
                        }

                        mAdapter.setData(data);
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }


}
