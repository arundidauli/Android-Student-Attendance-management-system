package com.techastrum.attendance.activities.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.techastrum.attendance.R;
import com.techastrum.attendance.activities.Util.Prefs;
import com.techastrum.attendance.activities.adapter.DistAdapter;
import com.techastrum.attendance.activities.model.Dist;
import com.techastrum.attendance.activities.student_attandence.HomeActivity;

import java.util.ArrayList;
import java.util.List;

public class DistActivity extends AppCompatActivity {
    private List<Dist> distList;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jilla);
        context=DistActivity.this;
        distList = new ArrayList<>();

        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Prefs.getInstance(context).GetValue("dist")!=null){
                    Intent intent =new Intent(getApplicationContext(), HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else {
                    Toast.makeText(context,"Select Your Area",Toast.LENGTH_LONG).show();
                }

            }
        });
        setRecyclerViewCategory();

    }

    private void setRecyclerViewCategory() {

        RecyclerView recyclerView = findViewById(R.id.rv_category);
        DistAdapter distAdapter = new DistAdapter(context, distList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(distAdapter);
        DistList();
    }

    private void DistList() {
        distList.add(new Dist("1","आगरा"));
        distList.add(new Dist("2","अम्बेडकर नगर"));
        distList.add(new Dist("3","आजमगढ़"));
        distList.add(new Dist("4","बलिया"));
        distList.add(new Dist("5","बाराबंकी"));
        distList.add(new Dist("6","बिजनौर"));
        distList.add(new Dist("7","चंदौली"));
        distList.add(new Dist("8","एएटा"));
        distList.add(new Dist("9","फर्रुखाबाद"));
        distList.add(new Dist("10","गौतम बुद्ध नगर"));
        distList.add(new Dist("11","गोंडा"));
        distList.add(new Dist("12","हापुड़"));
        distList.add(new Dist("13","जालौन"));
        distList.add(new Dist("14","अमरोहा"));
        distList.add(new Dist("15","कानपुर नगर"));
        distList.add(new Dist("1","खीरी"));
        distList.add(new Dist("2","लखनऊ"));
        distList.add(new Dist("3","मैनपुरी"));
        distList.add(new Dist("4","मेरठ"));
        distList.add(new Dist("5","मुजफ्फरनगर"));
        distList.add(new Dist("6","रायबरेली"));
        distList.add(new Dist("7","सम्भल (भीम नगर)"));
        distList.add(new Dist("8","शाहजहाँपुर"));
        distList.add(new Dist("9","सिद्धार्थ नगर"));
        distList.add(new Dist("10","सुल्तानपुर"));


        distList.add(new Dist("1","अलीगढ़"));
        distList.add(new Dist("2","अमेठी"));
        distList.add(new Dist("3","बागपत"));
        distList.add(new Dist("4","बलरामपुर"));
        distList.add(new Dist("5","बरेली"));
        distList.add(new Dist("6","बदायूं"));
        distList.add(new Dist("7","चित्रकूट"));
        distList.add(new Dist("8","इटावा"));
        distList.add(new Dist("9","फतेहपुर"));
        distList.add(new Dist("10","गाजियाबाद"));
        distList.add(new Dist("11","गोरखपुर"));
        distList.add(new Dist("12","हरदोई"));
        distList.add(new Dist("13","जौनपुर"));
        distList.add(new Dist("14","कन्नौज"));
        distList.add(new Dist("15","कासगंज"));
        distList.add(new Dist("1","कुशीनगर"));
        distList.add(new Dist("2","महाराजगंज"));
        distList.add(new Dist("3","मथुरा"));
        distList.add(new Dist("4","मिर्जापुर"));
        distList.add(new Dist("5","पीलीभीत"));
        distList.add(new Dist("6","रामपुर"));
        distList.add(new Dist("7","संत कबीर नगर"));
        distList.add(new Dist("8","शामली"));
        distList.add(new Dist("9","सीतापुर"));
        distList.add(new Dist("10","उन्नाव"));

        distList.add(new Dist("1","इलाहाबाद"));
        distList.add(new Dist("2","औरैया"));
        distList.add(new Dist("3","बहराइच"));
        distList.add(new Dist("4","बांदा"));
        distList.add(new Dist("5","बस्ती"));
        distList.add(new Dist("6","बुलंदशहर"));
        distList.add(new Dist("7","देवरिया"));
        distList.add(new Dist("8","फैजाबाद"));
        distList.add(new Dist("9","फिरोजाबाद"));
        distList.add(new Dist("10","गाज़ीपुर"));
        distList.add(new Dist("11","हमीरपुर"));
        distList.add(new Dist("12","हाथरस (महामाया नगर)"));
        distList.add(new Dist("13","झाँसी"));
        distList.add(new Dist("14","कानपुर देहात"));
        distList.add(new Dist("15","कौशाम्बी"));
        distList.add(new Dist("1","ललितपुर"));
        distList.add(new Dist("2","महोबा"));
        distList.add(new Dist("3","मऊ"));
        distList.add(new Dist("4","मुरादाबाद"));
        distList.add(new Dist("5","प्रतापगढ़"));
        distList.add(new Dist("6","सहारनपुर"));
        distList.add(new Dist("7","संत रविदास नगर"));
        distList.add(new Dist("8","श्रावस्ती"));
        distList.add(new Dist("9","सोनभद्र"));
        distList.add(new Dist("10","वाराणसी"));
    }
}
