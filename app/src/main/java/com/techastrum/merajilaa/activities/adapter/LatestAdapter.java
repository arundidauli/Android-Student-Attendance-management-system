package com.techastrum.merajilaa.activities.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.techastrum.merajilaa.R;
import com.techastrum.merajilaa.activities.model.Category;

import java.util.List;

public class LatestAdapter extends RecyclerView.Adapter<LatestAdapter.MyViewHolder> {
    private static String TAG = BottomProductAdapter.class.getSimpleName();
    private Context context;
    private List<Category> categoryList;


    public LatestAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.latest_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       /* holder.work_name.setText(categoryList.get(position).getCategory_name());
        Glide.with(context).load(categoryList.get(position).getUrl())
                .placeholder(R.mipmap.sky)
                .into(holder.image_latest);*/


    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView work_name;
        private ImageView image_latest;
        private CardView card_select;

        MyViewHolder(View view) {
            super(view);

            image_latest=view.findViewById(R.id.image_latest);
            work_name=view.findViewById(R.id.work_name);
        }
    }
    @Override
    public int getItemCount() {
        return 20;
    }
}
