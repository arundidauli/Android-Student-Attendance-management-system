package com.techastrum.attendance.activities.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techastrum.attendance.R;
import com.techastrum.attendance.activities.model.DailyReport;

import java.util.List;

public class DailyReportAdapter extends RecyclerView.Adapter<DailyReportAdapter.MyViewHolder> {
    private Context context;
    private List<DailyReport> studentList;


    public DailyReportAdapter(Context context, List<DailyReport> studentList) {
        this.context = context;
        this.studentList = studentList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dialy_report_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_title.setText(studentList.get(position).getTitle());
        holder.txt_des.setText(studentList.get(position).getDescription());
        holder.txt_date.setText(studentList.get(position).getDate());


    }


    @Override
    public int getItemCount() {
        return studentList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_title, txt_date, txt_des;


        MyViewHolder(View view) {
            super(view);
            txt_title = view.findViewById(R.id.txt_title);
            txt_date = view.findViewById(R.id.txt_date);
            txt_des = view.findViewById(R.id.txt_des);

        }
    }

}
