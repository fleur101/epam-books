package com.example.fleur101.epambooksapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MyBooksFragment extends BaseFragment {

    //region ViewBindings
    @BindView(R.id.btn_add)
    FloatingActionButton btnAdd;
    @BindView(R.id.rv_my_books)
    RecyclerView rvMyBooks;
    //endregion

    private String uid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uid = FirebaseAuth.getInstance().getUid();
        setLoaderText(getString(R.string.looking_books));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_books, container, false);
        ButterKnife.bind(this, view);

        getData();
        btnAdd.setOnClickListener(view1 -> startActivity(new Intent(getContext(), AddBookActivity.class)));
        return view;
    }

    private void getData() {
        showLoader(true);
        FirebaseFirestore.getInstance().collection("book_instances")
                .whereEqualTo("owner", "users/" + uid)
                .get()
                .addOnCompleteListener(task -> {
                    showLoader(false);
                    if (task.isSuccessful()) {
                        Timber.e("books size = %d", task.getResult().getDocuments().size());
                    } else {
                        Timber.e("Fail");
                    }

                });
    }

}
