package com.techastrum.attendance.activities.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.techastrum.attendance.R;
import com.techastrum.attendance.activities.model.Post;

import java.util.List;

public class Postadapter extends RecyclerView.Adapter<Postadapter.MyViewHolder> {
    private static String TAG = BottomProductAdapter.class.getSimpleName();
    private Context context;
    private List<Post> postList;


    public Postadapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(postList.get(position).getPost_image())
                .placeholder(R.mipmap.sky)
                .into(holder.post_image);
        holder.post_title.setText(postList.get(position).getPost_title());
        holder.post_detail.setText(postList.get(position).getPost_detail());
        holder.post_date.setText(postList.get(position).getPost_date());
        holder.post_views.setText(postList.get(position).getViews());




    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView post_title,post_detail,post_date,post_views;
        private ImageView post_image,img_share;

        MyViewHolder(View view) {
            super(view);
            post_image=view.findViewById(R.id.post_image);
            post_title=view.findViewById(R.id.post_title);
            post_detail=view.findViewById(R.id.post_detail);
            post_date=view.findViewById(R.id.post_date);
            post_views=view.findViewById(R.id.post_views);
            img_share=view.findViewById(R.id.img_share);
        }
    }
    @Override
    public int getItemCount() {
        return postList.size();
    }
}
