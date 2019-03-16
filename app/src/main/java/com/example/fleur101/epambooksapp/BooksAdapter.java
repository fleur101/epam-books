package com.example.fleur101.epambooksapp;

import android.content.Context;
import android.database.Cursor;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by acer on 16.03.2019.
 */

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BooksViewHolder> {

    private static final String TAG = "BOOKS_ADAPTER_TAG";
    private Context mContext;
    private Cursor mCursor;



    public BooksAdapter(Context context, Cursor cursor, MyBooksFragment booksFragment) {
        this.mContext = context;
        mCursor = cursor;
        booksFragment = booksFragment;

    }

    public static class BooksViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

//        @BindView() ImageView bookImageView;
        TextView bookNameTextView;
        TextView authorTextView;
        TextView publisher;
        TextView publishDate;

        public BooksViewHolder(TextView v) {
            super(v);
            ButterKnife.bind(this, itemView);
        }
    }



    @NonNull
    @Override
    public BooksAdapter.BooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BooksAdapter.BooksViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
