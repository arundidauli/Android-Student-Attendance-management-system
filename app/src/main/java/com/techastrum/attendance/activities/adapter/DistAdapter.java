package com.techastrum.attendance.activities.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techastrum.attendance.R;
import com.techastrum.attendance.activities.Util.Constants;
import com.techastrum.attendance.activities.Util.Prefs;
import com.techastrum.attendance.activities.model.Dist;

import java.util.List;

public class DistAdapter extends RecyclerView.Adapter<DistAdapter.MyViewHolder> {
    private Context context;
    private List<Dist> distList;
    Integer row_index = 0;


    public DistAdapter(Context context, List<Dist> distList) {
        this.context = context;
        this.distList = distList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.distt_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.dist.setText(distList.get(position).getDist_name());
       holder.dist.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               row_index =position;
               notifyDataSetChanged();
               Prefs.getInstance(context).SetValue(Constants.Dist,distList.get(position).getDist_name());
           }
       });

        if (row_index==position) {
            holder.dist.setBackgroundResource(R.drawable.green_border);
            holder.dist.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        } else {
            holder.dist.setBackgroundResource(R.drawable.gray_border);
            holder.dist.setTextColor(context.getResources().getColor(R.color.colorAccent));
        }
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView dist;


        MyViewHolder(View view) {
            super(view);
            dist=view.findViewById(R.id.dist);

        }
    }
    @Override
    public int getItemCount() {
        return distList.size();
    }
}
