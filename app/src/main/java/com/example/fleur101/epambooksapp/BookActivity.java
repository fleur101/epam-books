package com.example.fleur101.epambooksapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BookActivity extends AppCompatActivity {

    public static final String TAG = "BOOK_ACTIVITY_TAG";

    private FirebaseFirestore db;
    private List<Profile> mDataset;
    private UsersAdapter mAdapter;
    private String ownerID;



    @BindView(R.id.img_book)
    ImageView imageView;
    @BindView(R.id.tv_title)
    TextView titleTextView;
    @BindView(R.id.tv_author)
    TextView authorTextView;
    @BindView(R.id.tv_publisher)
    TextView publisherTextView;
    @BindView(R.id.tv_publish_date)
    TextView publishDateTextView;
    @BindView(R.id.tv_description)
    TextView descriptionTextView;
    @BindView(R.id.rv_book_owners) RecyclerView recyclerView;
    private String bookId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        bookId = intent.getStringExtra("book_id");
        Log.d(TAG, "onCreate: "+bookId);
        getData(bookId);

    }

    public void getData(String bookId){
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("books").document(bookId);
        docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Book book = document.toObject(Book.class);
                        book.setId(document.getId());
                        setData(book);
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }

                } else {
                    Log.d(TAG, "get failed with ", task.getException());

                }
            });
    }

    public void setData(Book book){
        setRequestData();

        List<String> authors = book.getAuthors();
        String authorNames = "";
        for (int i=0; i<authors.size(); i++){
            authorNames = authorNames.concat(authors.get(i));
            authorNames+=", ";
        }

        Date date = book.getPublish_date().toDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String dateString = day+"."+month+"."+year;


        Glide.with(this)
                .load(book.getImgURL())
                .into(imageView);
        Log.d(TAG, "setData: "+book.getTitle());
        titleTextView.setText(book.getTitle());
        authorTextView.setText(authorNames);
        publisherTextView.setText(book.getPublisher());
        publishDateTextView.setText(dateString);
        descriptionTextView.setText(book.getDescription());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mDataset = new ArrayList<Profile>();

        // using Linear Layout Manager
        mAdapter = new UsersAdapter(mDataset, this);
        recyclerView.setAdapter(mAdapter);

    }

    public void setRequestData(){
        db.collection("book_instances").whereEqualTo("book", bookId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
//                        List<Profile> data = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                                ownerID = document.get("owner").toString();
                            addUserToAdapter(ownerID);

                            Log.d(TAG, document.getId() + " => " + document.getData());
                        }

//                        mAdapter.setData(data);
//                        mAdapter.notifyDataSetChanged();
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });


    }

    public void addUserToAdapter(String ownerID){
        DocumentReference docRef = db.collection("books/users").document(ownerID);

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Profile user = document.toObject(Profile.class);
                    mAdapter.add(user);
                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                } else {
                    Log.d(TAG, "No such document");
                }

            } else {
                Log.d(TAG, "get failed with ", task.getException());

            }
        });
    }

}
