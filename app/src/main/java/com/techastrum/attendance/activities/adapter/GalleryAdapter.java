package com.techastrum.attendance.activities.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.techastrum.attendance.R;
import com.techastrum.attendance.activities.model.Post;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {
    private static String TAG = BottomProductAdapter.class.getSimpleName();
    private Context context;
    private List<Post> categoryList;


    public GalleryAdapter(Context context, List<Post> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Glide.with(context).load(categoryList.get(position).getPost_image())
                .placeholder(R.mipmap.fire)
                .into(holder.gallery_image);


    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_category_name;
        private ImageView gallery_image;
        private CardView card_select;
        private RelativeLayout rl_category;

        MyViewHolder(View view) {
            super(view);
            rl_category=view.findViewById(R.id.rl_category);
            gallery_image=view.findViewById(R.id.gallery_image);
            txt_category_name=view.findViewById(R.id.txt_category_name);
        }
    }
    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
