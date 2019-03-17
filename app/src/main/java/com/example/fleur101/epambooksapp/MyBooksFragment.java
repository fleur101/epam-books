package com.example.fleur101.epambooksapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fleur101.epambooksapp.models.BookInstance;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MyBooksFragment extends BaseFragment {

    //region ViewBindings
    @BindView(R.id.btn_add)
    FloatingActionButton btnAdd;
    @BindView(R.id.rv_my_books)
    RecyclerView rvMyBooks;
    //endregion

    private String uid;
    private MyBooksAdapter adapter;

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

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvMyBooks.setLayoutManager(layoutManager);
        adapter = new MyBooksAdapter();
        rvMyBooks.setAdapter(adapter);

        getData();
        btnAdd.setOnClickListener(view1 -> startActivity(new Intent(getContext(), AddBookActivity.class)));
        return view;
        // Inflate the layout for this fragment
//        View view =inflater.inflate(R.layout.fragment_my_books, container, false);
//        FloatingActionButton floatingActionButton = view.findViewById(R.id.fab);
//        floatingActionButton.setOnClickListener(view1 -> startActivityForResult(new Intent(getContext(), ScannerAcitivity.class), 1));
//        return view;
    }

    private void getData() {
        showLoader(true);
        FirebaseFirestore.getInstance().collection("book_instances")
                .whereEqualTo("owner", "users/" + uid)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    showLoader(false);
                    List<BookInstance> books = new ArrayList<>();
                    for (DocumentSnapshot book : queryDocumentSnapshots.getDocuments()) {
                        books.add(book.toObject(BookInstance.class));
                    }
                    adapter.setData(books);

                });


    }


//    public void setText(String text){
//        TextView t = (TextView)getView().findViewById(R.id.result_text);
//        t.setText(text);
//    }

}
