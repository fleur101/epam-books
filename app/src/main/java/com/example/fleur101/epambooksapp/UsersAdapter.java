package com.example.fleur101.epambooksapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by acer on 16.03.2019.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.BooksAdapterViewHolder> {

    private static final String TAG = "USERS_ADAPTER_TAG";
    private List<Profile> mDataset;
    private Context mContext;

    UsersAdapter(List<Profile> dataset, Context context) {
        mDataset = dataset;
        mContext = context;
    }

    @NonNull
    @Override
    public UsersAdapter.BooksAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_search_book_item, parent, false);
        return new BooksAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.BooksAdapterViewHolder holder, int position) {
//        Log.d(TAG, "onBindViewHolder: 1");
        Profile user = mDataset.get(position);

        String imageURL = user.getAvatar_link();

        Glide.with(mContext)
                .load(imageURL)
                .into(holder.reqAvatarImageView);
        holder.reqNameTextView.setText(String.format("%s %s", user.getFirstname(), user.getLastname()));

//        Log.d(TAG, "onBindViewHolder: 2");
    }

    public void setData(List<Profile> dataset){
        Log.d(TAG, "setData: ");
        mDataset = dataset;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    class BooksAdapterViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_req_avatar)
        ImageView reqAvatarImageView;
        @BindView(R.id.tv_req_name)
        TextView reqNameTextView;
        @BindView(R.id.iv_req_rating0)
        TextView reqRat0TextView;
        @BindView(R.id.iv_req_rating1)
        TextView reqRat1TextView;
        @BindView(R.id.iv_req_rating2)
        TextView reqRat2TextView;
        @BindView(R.id.iv_req_rating3)
        TextView reRat3TextView;
        @BindView(R.id.iv_req_rating4)
        TextView reqRat4TextView;
        @BindView(R.id.iv_req_approve)
        TextView reqApproveTextView;
        @BindView(R.id.iv_req_decline)
        TextView reqDeclineTextView;

        private BooksAdapterViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, itemView);
        }


    }

    public void add(Profile p){
        mDataset.add(p);
        notifyDataSetChanged();
    }


}
