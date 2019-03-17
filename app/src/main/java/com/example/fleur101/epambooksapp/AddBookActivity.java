package com.example.fleur101.epambooksapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by Assylkhanov Aslan on 17.03.2019.
 */
public class AddBookActivity extends BaseActivity {

    //region View Bindings
    @BindView(R.id.iv_cover)
    ImageView ivCover;
    @BindView(R.id.edt_name)
    TextInputEditText edtName;
    @BindView(R.id.edt_author)
    TextInputEditText edtAuthor;
    @BindView(R.id.edt_publisher)
    TextInputEditText edtPublisher;
    @BindView(R.id.edt_date)
    TextInputEditText edtDate;
    @BindView(R.id.edt_description)
    TextInputEditText edtDescription;
    @BindView(R.id.edt_isbn)
    TextInputEditText edtIsbn;
    @BindView(R.id.btn_cancel)
    TextView btnCancel;
    @BindView(R.id.btn_confirm)
    TextView btnConfirm;
    //endregion

    private String isbn;
    private String imageUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        ButterKnife.bind(this);

//        TODO. Mirka: For image load use
//        Glide.with(this)
//                .load("Your image url here")
//                .placeholder(R.drawable.bg_book_cover_gradient)
//                .into(ivCover);

        btnConfirm.setOnClickListener(v -> checkIfBookExists());
    }

    private void checkIfBookExists() {
        isbn = edtIsbn.getText().toString();
        showLoader(true);
        FirebaseFirestore.getInstance().collection("books")
                .whereEqualTo("isbn", isbn)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (!task.getResult().getDocuments().isEmpty()) {
                            createBookInstance(task.getResult().getDocuments().get(0).getId());
                        } else {
                            createBook();
                        }
                    }
                });
    }

    private void createBook() {
        Map<String, Object> book = new HashMap<>();
        List<String> authors = new ArrayList<>();
        authors.add(edtAuthor.getText().toString());
        book.put("authors", authors);
        book.put("description", edtDescription.getText().toString());
        book.put("imgUrl", imageUrl);
        book.put("isbn", isbn);
        book.put("publisher", edtPublisher.getText().toString());
        book.put("title", edtName.getText().toString());
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(edtDate.getText().toString()));
        Timestamp timestamp = new Timestamp(cal.getTime());
        FirebaseFirestore.getInstance().collection("books")
                .add(book)
                .addOnCompleteListener(task -> {
                   if (task.isSuccessful()) {
                       createBookInstance(task.getResult().getId());
                   }
                });
    }

    private void createBookInstance(String bookId) {
        Map<String, Object> book = new HashMap<>();
        book.put("book", "books/" + bookId);
        book.put("owner", "users/" + FirebaseAuth.getInstance().getUid());

        FirebaseFirestore.getInstance().collection("book_instances")
                .add(book)
                .addOnCompleteListener(task -> {
                    showLoader(false);
                    Timber.e("Success = %b", task.isSuccessful());
                });
    }

}
