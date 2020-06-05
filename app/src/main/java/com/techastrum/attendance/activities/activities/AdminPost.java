package com.techastrum.attendance.activities.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.techastrum.attendance.R;
import com.techastrum.attendance.activities.Util.Constants;
import com.techastrum.attendance.activities.Util.Helper;
import com.techastrum.attendance.activities.Util.Prefs;
import com.techastrum.attendance.activities.model.Post;
import com.techastrum.attendance.activities.student_attandence.ViewAllClasses;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.techastrum.attendance.activities.Util.Constants.REQUEST_IMAGE;

public class AdminPost extends AppCompatActivity {
    private static String TAG = AdminPost.class.getSimpleName();
    private Context context;
    private ProgressDialog progressDialog;
    private EditText et_category, et_title, et_contact, et_details,et_video_url;
    private ImageView post_image;
    private StorageReference storageRef, imageRef;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth firebaseAuth;
    private String IMAGE_URL,POST_TYPE;
    private String[] arr = {"Post Type","Pco Post", "Mera Zila", "Gallery", "Latest","Government Officers"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_post);
        context=AdminPost.this;

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = firebaseDatabase.getReference();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        // Get reference
        storageRef = storage.getReference();

        et_category = findViewById(R.id.et_category);
        et_title = findViewById(R.id.et_title);
        et_contact = findViewById(R.id.et_contact);
        et_details = findViewById(R.id.et_detail);
        post_image = findViewById(R.id.post_image);
        et_video_url = findViewById(R.id.et_video_url);


        if (getIntent().getStringExtra(Constants.Post_category) != null && getIntent().getStringExtra(Constants.Post_contact) != null) {
            et_category.setText(getIntent().getStringExtra(Constants.Post_category));
            et_title.setText(getIntent().getStringExtra(Constants.Post_category));
            et_details.setText(getIntent().getStringExtra(Constants.Post_category));
            et_contact.setText(getIntent().getStringExtra(Constants.Post_category));
            Glide.with(context).load(getIntent().getStringExtra(Constants.Post_Image)).into(post_image);
        }

        if (getIntent().getStringExtra("id") != null && getIntent().getStringExtra("name") != null) {
            et_category.setText(getIntent().getStringExtra("name"));
        }
        et_category.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ViewAllClasses.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Prefs.getInstance(context).SetValue(Constants.isAdmin,"true");
            startActivity(intent);
        });

        setting_spinner();
        findViewById(R.id.btn_back).setOnClickListener(v -> {
            super.onBackPressed();
        });
        findViewById(R.id.fab_upload).setOnClickListener(v -> {
            onProfileImageClick();
        });
        findViewById(R.id.btn_save_post).setOnClickListener(v -> {
            if (et_category.getText().toString().length() < 2) {
                Toast.makeText(context, "Please select category", Toast.LENGTH_LONG).show();
            }else if ( POST_TYPE==null){
                Toast.makeText(context, "Select Post type", Toast.LENGTH_LONG).show();
            } else if (et_title.getText().toString().length() < 3) {
                Toast.makeText(context, "Add your post heading", Toast.LENGTH_LONG).show();
            } else if (et_contact.getText().toString().length() < 3) {
                Toast.makeText(context, "Add Contact Number", Toast.LENGTH_LONG).show();
            } else if (et_details.getText().toString().length() < 30) {
                Toast.makeText(context, "Add your post details with 20 word", Toast.LENGTH_LONG).show();
            } else {
                savePost();
            }
        });


    }
    private void setting_spinner() {
        MaterialSpinner SortBy = findViewById(R.id.spinner_sort_by);
        SortBy.setItems(arr);
        SortBy.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                // Prefs.getInstance(context).SetValue("selected",item);

                if (position==0){
                    Snackbar.make(view, "Select Post type", Snackbar.LENGTH_LONG).show();
                }
                POST_TYPE=item;
                Snackbar.make(view, "Clicked " +POST_TYPE, Snackbar.LENGTH_LONG).show();

            }
        });
        SortBy.getPopupWindow();
    }

    private void onProfileImageClick() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions();
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(this, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(AdminPost.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);
        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(AdminPost.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);
        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, false);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1500);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);
        startActivityForResult(intent, REQUEST_IMAGE);
    }
    private void loadProfile(String url, String str_img) {
        Log.d(TAG, "Image cache path: " + url);
        Glide.with(this).load(url)
                .into(post_image);
        post_image.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = Objects.requireNonNull(data).getParcelableExtra("path");
                if (uri != null) {
                    getImageUrl(uri);
                }
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    // loading profile image from local cache
                    loadProfile(Objects.requireNonNull(uri).toString(), Constants.BitMapToString(bitmap));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminPost.this);
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();
    }
    //navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    private void getImageUrl(Uri file) {
        progressDialog.show();
        progressDialog.setMessage(Constants.Please_wait);
        imageRef = storageRef.child(file.getLastPathSegment());// Create Upload Task
        UploadTask uploadTask = imageRef.putFile(file);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(context).load(uri.toString()).thumbnail(0.1f).into(post_image);
                        Log.e(TAG, uri.toString());
                        IMAGE_URL = uri.toString();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                int progress = (int) ((100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount());
                progressDialog.setMessage(Constants.Please_wait+"( "+String.valueOf(progress)+"% )");
            }
        });
    }
    public String createPostID() throws Exception{
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }
    private void savePost() {


        String id=mDatabaseReference.child("post").push().getKey();
        Post user_post = new Post();
        user_post.setId(id);
        user_post.setUser_id(firebaseAuth.getUid());
        user_post.setPost_image(IMAGE_URL);
        try {
            user_post.setPost_id(createPostID());
        } catch (Exception e) {
            e.printStackTrace();
        }
        user_post.setPost_title(et_title.getText().toString());
        user_post.setPost_detail(et_details.getText().toString());
        user_post.setPost_date(Helper.get_date());
        user_post.setPost_url("Ambulance Service");
        user_post.setVideo_url(et_video_url.getText().toString());
        user_post.setPost_district(Prefs.getInstance(context).GetValue(Constants.Dist));
        user_post.setPost_type(POST_TYPE);
        user_post.setPost_category(et_category.getText().toString());
        user_post.setContact(et_contact.getText().toString());
        user_post.setViews("0");
        user_post.setIs_approve(true);
        mDatabaseReference.child("post").child(id).setValue(user_post);

        Toast.makeText(getApplicationContext(), "Post Submitted", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), AdminPanel.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    private void UpdatePost(String id) {
        Post user_post = new Post();
        user_post.setId(firebaseAuth.getUid());
        user_post.setUser_id(firebaseAuth.getUid());
        user_post.setPost_image(IMAGE_URL);
        try {
            user_post.setPost_id(createPostID());
        } catch (Exception e) {
            e.printStackTrace();
        }
        user_post.setPost_title(et_title.getText().toString());
        user_post.setPost_detail(et_details.getText().toString());
        user_post.setPost_date(Helper.get_date());
        user_post.setPost_url("Ambulance Service");
        user_post.setVideo_url("Ambulance Service");
        user_post.setPost_district(Prefs.getInstance(context).GetValue(Constants.Dist));
        user_post.setPost_type("1");
        user_post.setPost_category(et_category.getText().toString());
        user_post.setContact(et_contact.getText().toString());
        user_post.setViews("0");
        user_post.setIs_approve(true);
        mDatabaseReference.child("post").child(id).setValue(user_post);
        Toast.makeText(getApplicationContext(), "Post Updated", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), AdminPanel.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
