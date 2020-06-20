package com.techastrum.attendance.activities.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.techastrum.attendance.R;
import com.techastrum.attendance.activities.model.Student;
import com.techastrum.attendance.activities.student_attandence.StudentActivities;
import com.techastrum.attendance.activities.student_attandence.ViewAllStudentActivity;

import java.util.List;

public class StudentPositionAdapter extends RecyclerView.Adapter<StudentPositionAdapter.MyViewHolder>  {
    private Context context;
    private List<Student> studentList;
    private String[] arr = {"Select Position", "Position A", "Position B","Position C", "Position D"};
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth firebaseAuth;

    public StudentPositionAdapter(Context context, List<Student> studentList) {
        this.context = context;
        this.studentList = studentList;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_student_activity_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(studentList.get(position).getStudent_image())
                .placeholder(R.mipmap.sky)
                .into(holder.student_image);
        holder.txt_student_class.setText(String.format("Class: %s", studentList.get(position).getStudent_class()));
        holder.txt_student_name.setText(studentList.get(position).getStudent_name());
        holder.txt_roll_no.setText(String.format("Roll No: %s", studentList.get(position).getStudent_roll_no()));
        holder.SortBy.setText(studentList.get(position).getStudent_position());


        holder.txt_delete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setMessage("Are you sure you want to delete?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DatabaseReference dR = FirebaseDatabase.getInstance().getReference("student").child(studentList.get(position).getId());
                            dR.removeValue();
                            Toast.makeText(context,"Delete successfully",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(context, ViewAllStudentActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                        }
                    }).setNegativeButton("No", null).show();


        });


        holder.SortBy.setItems(arr);
        holder.SortBy.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                if (position==0){

                }else if (position==1){
                    UpdateStudent(position,item);
                }else if (position==2){
                    UpdateStudent(position,item);

                }else if (position==3){
                    UpdateStudent(position,item);
                }
            }
        });
        holder.SortBy.getPopupWindow();


    }



    private void UpdateStudent(int position,String position_or_activities) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = firebaseDatabase.getReference("student");
        Student user_post = new Student();
        user_post.setId(studentList.get(position).getId());
        user_post.setStudent_image(studentList.get(position).getStudent_image());
        user_post.setStudent_name(studentList.get(position).getStudent_name());
        user_post.setStudent_father_name(studentList.get(position).getStudent_father_name());
        user_post.setStudent_class(studentList.get(position).getStudent_class());
        user_post.setStudent_roll_no(studentList.get(position).getStudent_roll_no());
        user_post.setStudent_activities("position_or_activities");
        user_post.setStudent_position(position_or_activities);
        mDatabaseReference.child(studentList.get(position).getId()).setValue(user_post);
        Intent intent = new Intent(context, StudentActivities.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return  studentList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView  txt_student_name, txt_student_class,txt_roll_no,txt_delete;
        private ImageView student_image,img_edit;
        private MaterialSpinner SortBy;


        MyViewHolder(View view) {
            super(view);
            SortBy = view.findViewById(R.id.spinner_sort_by);
            txt_delete=view.findViewById(R.id.txt_delete);
            img_edit=view.findViewById(R.id.img_edit);
            student_image=view.findViewById(R.id.student_image);
            txt_student_name=view.findViewById(R.id.txt_student_name);
            txt_student_class=view.findViewById(R.id.txt_student_class);
            txt_roll_no=view.findViewById(R.id.txt_roll_no);


        }
    }

}
