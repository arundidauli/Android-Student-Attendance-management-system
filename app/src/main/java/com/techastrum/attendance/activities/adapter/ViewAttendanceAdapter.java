package com.techastrum.attendance.activities.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techastrum.attendance.R;
import com.techastrum.attendance.activities.model.Attendance;
import com.techastrum.attendance.activities.student_attandence.TakeAttendanceActivity;

import java.util.List;

public class ViewAttendanceAdapter extends RecyclerView.Adapter<ViewAttendanceAdapter.MyViewHolder>  {
    private Context context;
    private List<Attendance> studentList;
    private String m_date,m_time;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth firebaseAuth;
    public ViewAttendanceAdapter(Context context, List<Attendance> studentList) {
        this.context = context;
        this.studentList = studentList;
    }

    public ViewAttendanceAdapter(Context context, List<Attendance> studentList, String m_date, String m_time) {
        this.context = context;
        this.studentList = studentList;
        this.m_date = m_date;
        this.m_time = m_time;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.attendance_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(studentList.get(position).getStudent_photo())
                .placeholder(R.mipmap.sky)
                .into(holder.student_image);
        holder.txt_student_class.setText(String.format("Class: %s", studentList.get(position).getStudent_class()));
        holder.txt_student_name.setText(studentList.get(position).getStudent_name());
        holder.txt_roll_no.setText(String.format("Roll No: %s", studentList.get(position).getStudent_roll_no()));

       /* holder.txt_present.setOnClickListener(v -> {
            UpdateAttendance(position,true);
        });
        holder.txt_absent.setOnClickListener(v -> {
            UpdateAttendance(position,false);

        });*/


    }


    @Override
    public int getItemCount() {
        return  studentList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView  txt_student_name, txt_student_class,txt_roll_no,txt_present,txt_absent;
        private ImageView student_image,img_edit;

        MyViewHolder(View view) {
            super(view);
            txt_present=view.findViewById(R.id.txt_present);
          ///  txt_absent=view.findViewById(R.id.txt_absent);
            student_image=view.findViewById(R.id.student_image);
            txt_student_name=view.findViewById(R.id.txt_student_name);
            txt_student_class=view.findViewById(R.id.txt_student_class);
            txt_roll_no=view.findViewById(R.id.txt_roll_no);

        }
    }


    private void UpdateAttendance(int position,boolean is_true) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = firebaseDatabase.getReference("student");
        Attendance user_post = new Attendance();
        user_post.setId(studentList.get(position).getId());
        user_post.setStudent_photo(studentList.get(position).getStudent_photo());
        user_post.setStudent_name(studentList.get(position).getStudent_name());
        user_post.setStudent_father_name(studentList.get(position).getStudent_name());
        user_post.setStudent_class(studentList.get(position).getStudent_name());
        user_post.setStudent_roll_no(studentList.get(position).getStudent_name());
        user_post.setAttendance_date(m_date);
        user_post.setAttendance_time(m_time);
        user_post.setPresent(is_true);
        mDatabaseReference.child(studentList.get(position).getId()).setValue(user_post);
        Intent intent = new Intent(context, TakeAttendanceActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

}
