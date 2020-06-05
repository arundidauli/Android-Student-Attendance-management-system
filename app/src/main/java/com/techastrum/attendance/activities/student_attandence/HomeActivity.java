package com.techastrum.attendance.activities.student_attandence;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.techastrum.attendance.R;
import com.techastrum.attendance.activities.Util.Constants;
import com.techastrum.attendance.activities.Util.Prefs;
import com.techastrum.attendance.activities.activities.AdvanceSearch;
import com.techastrum.attendance.activities.activities.SecurityActivity;
import com.techastrum.attendance.activities.activities.UserProfile;
import com.techastrum.attendance.activities.adapter.ClassAdapter;
import com.techastrum.attendance.activities.model.Category;
import com.techastrum.attendance.activities.model.StudentClass;
import com.techastrum.attendance.activities.model.Post;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static String TAG = HomeActivity.class.getSimpleName();
    private AppBarConfiguration mAppBarConfiguration;
    private RelativeLayout progressDialog;
    private Context context;
    private List<StudentClass> categoryList1;
    private List<Category> categoryList;
    private List<Category> galleryList;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabaseReference,mDatabaseReference_s;
    private FirebaseAuth firebaseAuth;
    private List<Post> latestList;
    private List<Post> user_galleryList;
    private List<Post> latestList1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = HomeActivity.this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressDialog = findViewById(R.id.rl_progress);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideProgressDialog();
            }
        }, Constants.DELAY_TIME);

        latestList=new ArrayList<>();
        user_galleryList=new ArrayList<>();
        latestList1=new ArrayList<>();

       /* FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddStudent.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });*/
        DrawerLayout drawer = findViewById(R.id. drawer_layout ) ;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer , toolbar , R.string. navigation_drawer_open ,
                R.string. navigation_drawer_close ) ;
        drawer.addDrawerListener(toggle) ;
        toggle.syncState() ;
        NavigationView navigationView = findViewById(R.id. nav_view_first ) ;
        navigationView.setNavigationItemSelectedListener( this );

        App_Permission();
        setRecyclerViewCategory();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference_s = firebaseDatabase.getReference("Security");
       // mDatabaseReference = firebaseDatabase.getReference("post");
        GetUserCode();

    }
    private void setRecyclerViewCategory() {
        categoryList1 = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.rv_category);
        ClassAdapter ridesAdapter = new ClassAdapter(context, categoryList1);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(ridesAdapter);
        Category();
    }

    private void GetUserCode() {

        ValueEventListener eventListener = new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.e(TAG,  dataSnapshot.child("code").getValue().toString());
                    Prefs.getInstance(context).SetValue(Constants.CODE,dataSnapshot.child("code").getValue().toString());



                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", databaseError.toException());

            }
        };
        mDatabaseReference_s.addListenerForSingleValueEvent(eventListener);


    }
    private void Category() {
        categoryList1.add(new StudentClass("4","Add Student",R.mipmap.abc));
        categoryList1.add(new StudentClass("4","Add Teacher",R.mipmap.abc));
        categoryList1.add(new StudentClass("4","Add Class",R.mipmap.abc));
        categoryList1.add(new StudentClass("4","View Students",R.mipmap.abc));
        categoryList1.add(new StudentClass("4","View Teachers",R.mipmap.abc));
        categoryList1.add(new StudentClass("4","View Classes",R.mipmap.abc));
        categoryList1.add(new StudentClass("4","View Attendance",R.mipmap.abc));
        categoryList1.add(new StudentClass("4","Events",R.mipmap.abc));
        categoryList1.add(new StudentClass("4","Gallery",R.mipmap.abc));

    }
    private void setProgressDialog() {
        progressDialog.setVisibility(View.VISIBLE);
    }
    private void hideProgressDialog() {
        progressDialog.setVisibility(View.GONE);
    }
    @Override
    public void onBackPressed () {
        DrawerLayout drawer = findViewById(R.id. drawer_layout ) ;
        if (drawer.isDrawerOpen(GravityCompat. START )) {
            drawer.closeDrawer(GravityCompat. START ) ;
        } else {
            super .onBackPressed() ;
        }
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu. main_menu , menu) ;
        return true;
    }
    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id = item.getItemId() ;
        if (id == R.id. action_settings ) {
            return true;
        }
        return super .onOptionsItemSelected(item) ;
    }
    @SuppressWarnings ( "StatementWithEmptyBody" )
    @Override
    public boolean onNavigationItemSelected ( @NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId() ;
        if (id == R.id. nav_home ) {
            // Handle the camera action
           // Toasty.info(getApplicationContext(),"Clicked",Toasty.LENGTH_LONG,true).show();
        } else if (id == R.id. nav_search ) {
            //Toasty.info(getApplicationContext(),"Clicked",Toasty.LENGTH_LONG,true).show();
            Intent intent = new Intent(getApplicationContext(), AdvanceSearch.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (id == R.id. nav_login ) {
            // Toasty.info(getApplicationContext(),"Clicked",Toasty.LENGTH_LONG,true).show();
            Intent intent = new Intent(getApplicationContext(), UserProfile.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else if (id == R.id. nav_about_us ) {

            Toasty.info(getApplicationContext(),"Clicked",Toasty.LENGTH_LONG,true).show();
        }else if (id == R.id. nav_shop ) {
            Intent intent = new Intent(getApplicationContext(), SecurityActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else if (id == R.id. nav_privacy_policy ) {
            Toasty.info(getApplicationContext(),"Clicked",Toasty.LENGTH_LONG,true).show();

        }else if (id == R.id. nav_share ) {
            Toasty.info(getApplicationContext(),"Clicked",Toasty.LENGTH_LONG,true).show();

        }
        DrawerLayout drawer = findViewById(R.id. drawer_layout ) ;
        drawer.closeDrawer(GravityCompat. START ) ;
        return true;
    }





    private void App_Permission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(context, "Permission granted", Toast.LENGTH_SHORT).show();

                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Permission Denied!")
                                    .setMessage("Permission permanently denied. you need to go to setting to allow the permission.")
                                    .setNegativeButton("Cancel", null)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent();
                                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            intent.setData(Uri.fromParts("package", getPackageName(), null));
                                        }
                                    })
                                    .show();

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();

    }
}
