package com.example.fleur101.epambooksapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fleur101.epambooksapp.ApiModel.ApiModel;
import com.example.fleur101.epambooksapp.ApiModel.VolumeInfo;
import com.example.fleur101.epambooksapp.Barcode.ScannerAcitivity;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.fleur101.epambooksapp.Utils.GOOGLE_BOOKS_API_KEY;

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
    @BindView(R.id.btn_cancel)
    TextView btnCancel;
    @BindView(R.id.btn_confirm)
    TextView btnConfirm;
    @BindView(R.id.edt_isbn)
    TextInputEditText edtIsbn;
    //endregion

    private String isbn;
    private String imageUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        ButterKnife.bind(this);
        ImageButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view1 -> startActivityForResult(new Intent(this, ScannerAcitivity.class), 1));

        btnConfirm.setOnClickListener(v -> checkIfBookExists());
        btnCancel.setOnClickListener(view -> onBackPressed());
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

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(edtDate.getText().toString()));
        Timestamp timestamp = new Timestamp(cal.getTime());

        book.put("authors", authors);
        book.put("description", edtDescription.getText().toString());
        book.put("imgUrl", imageUrl);
        book.put("isbn", isbn);
        book.put("publisher", edtPublisher.getText().toString());
        book.put("title", edtName.getText().toString());
        book.put("publish_date", timestamp);
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
                    onBackPressed();
                });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Activity activity = this;
        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {
                String barcode = data.getStringExtra("barcode");
                edtIsbn.setText(barcode);

                App.getBooksApi().getData("isbn:" + "9780071636087", GOOGLE_BOOKS_API_KEY).enqueue(
                        new Callback<ApiModel>() {
                            @Override
                            public void onResponse(Call<ApiModel> call, Response<ApiModel> response) {
                                if (response.body() != null) {
                                    VolumeInfo volumeInfo = response.body().getItems().get(0).getVolumeInfo();
                                    edtAuthor.setText(volumeInfo.getAuthors().get(0));
                                    edtDescription.setText(volumeInfo.getDescription());
                                    edtDate.setText(volumeInfo.getPublishedDate());
                                    edtName.setText(volumeInfo.getTitle());
                                    edtPublisher.setText(volumeInfo.getPublisher());
                                    imageUrl = volumeInfo.getImageLinks().getSmallThumbnail();
                                    Glide.with(activity)
                                            .load(volumeInfo.getImageLinks().getSmallThumbnail())
                                            .placeholder(R.drawable.bg_book_cover_gradient)
                                            .into(ivCover);
                                    ivCover.setBackground(null);
                                } else {
                                    Toast.makeText(AddBookActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ApiModel> call, Throwable t) {
                                Toast.makeText(AddBookActivity.this, "you failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
                //your code

            }
            if (resultCode == RESULT_CANCELED) {
                // Write your code if there's no result
                Toast.makeText(this, "Try again", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
