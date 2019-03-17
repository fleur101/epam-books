package com.example.fleur101.epambooksapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fleur101.epambooksapp.models.BookInstance;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by Assylkhanov Aslan on 17.03.2019.
 */
public class MyBooksAdapter extends RecyclerView.Adapter<MyBooksAdapter.MyBookHolder> {

    private List<BookInstance> data;

    @NonNull
    @Override
    public MyBookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_book, parent, false);

        return new MyBookHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyBookHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data!= null ? data.size() : 0;
    }

    public void setData(List<BookInstance> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    static class MyBookHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_cover)
        ImageView ivCover;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        @BindView(R.id.tv_info)
        TextView tvInfo;

        public MyBookHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(BookInstance instance) {
            Timber.e("Bind");
            ivCover.setBackground(null);
            FirebaseFirestore.getInstance().document(instance.getBook())
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        Timber.e("Snapshot arrived");
                        Book book = documentSnapshot.toObject(Book.class);
                        if (documentSnapshot.get("imgUrl") != null) {
                            Glide.with(itemView.getContext())
                                    .load(documentSnapshot.get("imgUrl").toString())
                                    .into(ivCover);
                        } else {
                            ivCover.setImageResource(R.drawable.bg_book_cover_gradient);
                        }
                        tvTitle.setText(documentSnapshot.get("title").toString());
                        StringBuilder builder = new StringBuilder();
                        for (String author : book.getAuthors()) {
                            builder.append(author + ", ");
                        }
                        Timber.e("Title = %s", book.getTitle());
                        Timber.e("TvTitle = %s", tvTitle.getText().toString());
                        String authors = builder.substring(0, builder.length() - 2);
                        tvAuthor.setText(authors);
                    });
        }
    }

}
