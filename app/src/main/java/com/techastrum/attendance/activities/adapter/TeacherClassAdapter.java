package com.techastrum.attendance.activities.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techastrum.attendance.R;
import com.techastrum.attendance.activities.Util.Constants;
import com.techastrum.attendance.activities.model.DailyReport;
import com.techastrum.attendance.activities.model.StudentClass;
import com.techastrum.attendance.activities.student_attandence.SelectDateActivity;
import com.techastrum.attendance.activities.student_attandence.StudentActivities;
import com.techastrum.attendance.activities.student_attandence.StudentPositionActivity;
import com.techastrum.attendance.activities.student_attandence.ViewAllTeacherActivity;
import com.techastrum.attendance.activities.student_attandence.ViewDailyReportActivity;
import com.techastrum.attendance.activities.student_attandence.ViewStudentsActivity;

import java.util.List;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

import static com.techastrum.attendance.activities.Util.Constants.Post_category;

public class TeacherClassAdapter extends RecyclerView.Adapter<TeacherClassAdapter.MyViewHolder> {
    private static String TAG = BottomProductAdapter.class.getSimpleName();
    private Context context;
    private List<StudentClass> categoryList;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth firebaseAuth;

    public TeacherClassAdapter(Context context, List<StudentClass> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.txt_category_name.setText(categoryList.get(position).getCategory_name());
        Glide.with(context).load(categoryList.get(position).getImage_url())
                .placeholder(R.mipmap.fire)
                .into(holder.category_icon);

        /*if (categoryList.get(position).getId().equals("15")){
            Intent intent = new Intent(context, ShopActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        }*/

       holder.rl_category.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (position==0){
                   Intent intent = new Intent(context, SelectDateActivity.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   intent.putExtra("view_attendance","no");
                   context.startActivity(intent);
               }else if (position==1){
                   Intent intent = new Intent(context, ViewStudentsActivity.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   intent.putExtra(Post_category,categoryList.get(position).getCategory_name());
                   context.startActivity(intent);
               }else if (position==2){
                   Intent intent = new Intent(context, SelectDateActivity.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   intent.putExtra("view_attendance","yes");
                   context.startActivity(intent);
               }else if (position==3){
                   show_dialog();
               }else if (position==4){
                   Intent intent = new Intent(context, ViewDailyReportActivity.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   context.startActivity(intent);
               }else if (position==5){
                   Intent intent = new Intent(context, StudentActivities.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   context.startActivity(intent);
               }else if (position==6){
                   Intent intent = new Intent(context, StudentPositionActivity.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   context.startActivity(intent);
               }


           }
       });


    }


    private void saveReport(String title,String des,String m_date) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = firebaseDatabase.getReference("daily_report");
        String id = mDatabaseReference.child("daily_report").push().getKey();
        DailyReport teacher = new DailyReport();
        teacher.setId(id);
        teacher.setDate(m_date);
        teacher.setTitle(title);
        teacher.setDescription(des);
        teacher.setId(id);

        mDatabaseReference.child(id).setValue(teacher);
        Toast.makeText(context, "Report Add successfully", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(context, ViewDailyReportActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }


    private void show_dialog() {
        final Dialog dialog = new Dialog(context, R.style.WideDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog.setCancelable(true);
        Rect displayRectangle = new Rect();
        Window window = dialog.getWindow();
        assert window != null;
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        // View layout = getLayoutInflater().inflate(R.layout.fragment_dialog, null);
        // View.inflate(context, R.layout.fragment_dialog, null);
        dialog.setContentView(View.inflate(context, R.layout.fragment_add_report, null));
        Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.PopupAnimation;

        EditText title = dialog.findViewById(R.id.txt_title);
        EditText des = dialog.findViewById(R.id.txt_des);
        EditText mdate = dialog.findViewById(R.id.txt_date);
        Button btn_save = dialog.findViewById(R.id.btn_save);

        mdate.setOnClickListener(v -> {
            Constants.showDate(context,mdate);
        });
        btn_save.setOnClickListener(v -> {
            if (title.getText().toString().length()<6){
                Toasty.info(context,"Enter a title", Toast.LENGTH_SHORT,true).show();
            }else if(des.getText().toString().length()<6){
                Toasty.info(context,"Enter a description",Toast.LENGTH_SHORT,true).show();
            }else if(mdate.getText().toString().length()<2){
                Toasty.info(context,"Enter a date",Toast.LENGTH_SHORT,true).show();
            }else {
                saveReport(title.getText().toString(),des.getText().toString(),mdate.getText().toString());
                dialog.dismiss();

            }
        });

        dialog.show();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_category_name;
        private ImageView category_icon;
        private CardView card_select;
        private RelativeLayout rl_category;

        MyViewHolder(View view) {
            super(view);
            rl_category=view.findViewById(R.id.rl_category);
            category_icon=view.findViewById(R.id.category_icon);
            txt_category_name=view.findViewById(R.id.txt_category_name);
        }
    }
    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
