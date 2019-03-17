package com.example.fleur101.epambooksapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.text.TextUtils.isEmpty;

/**
 * Created by acer on 16.03.2019.
 */

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BooksAdapterViewHolder> {

    private static final String TAG = "BOOKS_ADAPTER_TAG";
    private List<Book> mDataset;


    BooksAdapter(List<Book> dataset) {
        mDataset = dataset;
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
        holder.titleTextView.setText(book.getTitle());
        for (int i=0; i<authors.size(); i++){
            authorNames = authorNames.concat(authors.get(i));
            authorNames+=", ";
        }
        holder.authorTextView.setText(authorNames);
        holder.publisher.setText(book.getPublisher());
        if (book.getPublish_date() != null) {
            SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy");
            String publicationDate = sfd.format(new Date(book.getPublish_date().getSeconds()));
            holder.publishDate.setText(publicationDate);
        }
        if (isEmpty(book.getImgURL())) {
            holder.bookImageView.setImageResource(R.drawable.bg_book_cover_gradient);
        } else {
            Glide.with(holder.itemView.getContext())
                    .load(book.getImgURL())
                    .into(holder.bookImageView);
        }
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
