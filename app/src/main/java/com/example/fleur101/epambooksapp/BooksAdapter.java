package com.example.fleur101.epambooksapp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by acer on 16.03.2019.
 */

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BooksAdapterViewHolder> {

    private static final String TAG = "BOOKS_ADAPTER_TAG";
    private List<Book> mDataset;
    private Context mContext;


    BooksAdapter(List<Book> dataset, Context context) {
        mDataset = dataset;
        mContext = context;
    }

    @NonNull
    @Override
    public BooksAdapter.BooksAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_search_book_item, parent, false);
        return new BooksAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksAdapter.BooksAdapterViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: 1");
        Book book = mDataset.get(position);
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

        String imageURL = book.getImgURL();

        Glide.with(mContext)
                .load(imageURL)
                .into(holder.bookImageView);
        Log.d(TAG, "onBindViewHolder: "+book.getTitle());
        holder.titleTextView.setText(book.getTitle());
        holder.authorTextView.setText(authorNames);
        holder.publisher.setText(book.getPublisher());
        holder.publishDate.setText(dateString);

        Log.d(TAG, "onBindViewHolder: 2");
    }

    public void setData(List<Book> dataset){
        Log.d(TAG, "setData: ");
        mDataset = dataset;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    class BooksAdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_book)
        ImageView bookImageView;
        @BindView(R.id.tv_title)
        TextView titleTextView;
        @BindView(R.id.tv_author)
        TextView authorTextView;
        @BindView(R.id.tv_publisher)
        TextView publisher;
        @BindView(R.id.tv_publish_date)
        TextView publishDate;
        @BindView(R.id.iv_request_icon)
        ImageView requestIcon;
        @BindView(R.id.iv_info_icon)
        ImageView infoIcon;

        private BooksAdapterViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, itemView);
        }
    }


}
