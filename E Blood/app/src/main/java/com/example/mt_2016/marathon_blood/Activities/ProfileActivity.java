package com.example.mt_2016.marathon_blood.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.mt_2016.marathon_blood.Defaults.Defaults_Links;
import com.example.mt_2016.marathon_blood.Fragments.Login;
import com.example.mt_2016.marathon_blood.Fragments.One;
import com.example.mt_2016.marathon_blood.Fragments.Signup;
import com.example.mt_2016.marathon_blood.NetWorkClasses.ConnectivityStatus;
import com.example.mt_2016.marathon_blood.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;


import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    EditText pname, pemail, pcity, pcountry, pgender, pbloodgroup;
    CircularImageView imageView;
    Button updateInfo;
    final Context context = this;
    public HashMap<String, String> hashObj;
    private FirebaseAuth mAuth;
    boolean updateORnot = false;
    private static int Gallery_Request = 1;
    ProgressDialog progres;
    Intent intent_of_gallery;
    DatabaseReference databse;
    private DatabaseReference databaseReference;
    private Uri mImageUri = null;
    private StorageReference mStoarge;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initializeViews();
        getSupportActionBar().setTitle("Profile Update");
        setUserValues();
        setDisableAllEdittext();
        profilePicChangeMenu();
        UpdateUserInfromation();


    }

    private void UpdateUserInfromation() {
        updateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (updateORnot == false) {
                    if(!ConnectivityStatus.isConnected(ProfileActivity.this)){
                        Login.showDialog(ProfileActivity.this,getResources().getString(R.string.no_internet));

                    }else {
                        LayoutInflater li = LayoutInflater.from(context);
                        View promptsView = li.inflate(R.layout.passworddailog, null);
                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                        alertDialogBuilder.setTitle("Enter Your Password");
                        alertDialogBuilder.setMessage("Enter your Password to confirm this Account Belongs to You");
                        alertDialogBuilder.setView(promptsView);

                        final EditText userInput = (EditText) promptsView
                                .findViewById(R.id.editTextDialogUserInput);
                        alertDialogBuilder
                                .setCancelable(false)
                                .setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {


                                            }
                                        })
                                .setNegativeButton("Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                        // create alert dialog
                        final AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Boolean wantToCloseDialog = false;
                                //Do stuff, possibly set wantToCloseDialog to true then...
                                if (!userInput.getText().toString().equals("")) {
                                    if (userInput.getText().toString().equals(Welcome_for_User.pasword)) {
                                        setEnableAllEdittext();
                                        updateORnot = true;

                                    } else {
                                        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                        builder.setTitle("Warning");
                                        builder.setMessage("You Enter Wrong Password");
                                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        });
                                        builder.show();
                                    }
                                    wantToCloseDialog = true;
                                } else if (userInput.getText().toString().equals("")) {
                                    userInput.setError("Can't be null");
                                }
                                if (wantToCloseDialog)
                                    alertDialog.dismiss();
                            }
                        });
                    }



                } else if (updateORnot == true) {

                    if (isEmptyField(pname)) return;
                    if (isEmptyField(pemail)) return;
                    if (isEmptyField(pgender)) return;
                    if (isEmptyField(pcountry)) return;
                    if (isEmptyField(pcity)) return;
                    if (isEmptyField(pbloodgroup)) return;
                    getAndUpdate();
                }
            }

        });
    }

    private void profilePicChangeMenu() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ConnectivityStatus.isConnected(ProfileActivity.this)){
                    Login.showDialog(ProfileActivity.this,getResources().getString(R.string.no_internet));

                }else {
                    //Creating the instance of PopupMenu
                    PopupMenu popup = new PopupMenu(ProfileActivity.this, imageView);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater().inflate(R.menu.popup_menu_for_profile_pic, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
//                                Toast.makeText(Welcome_for_User.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                            int id = item.getItemId();
                            if (id == R.id.chose_from_gallery) {
                                Toast.makeText(ProfileActivity.this, "galley", Toast.LENGTH_SHORT).show();
                                intent_of_gallery = new Intent(Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(intent_of_gallery, Gallery_Request);

                            }
                            if (id == R.id.chose_form_camera) {
                                Toast.makeText(ProfileActivity.this, "camera", Toast.LENGTH_SHORT).show();
                                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if (takePictureIntent.resolveActivity(getBaseContext().getPackageManager()) != null) {
                                    startActivityForResult(takePictureIntent, 111);
                                }


                            }
                            if (id == R.id.chose_by_default) {
                                progres.setMessage("Removing...");
                                progres.show();
                                DatabaseReference database_reference = databaseReference.child(mAuth.getCurrentUser().getUid());
                                database_reference.child("Profile_image").setValue(Defaults_Links.default_user);
                                progres.dismiss();
                                Toast.makeText(ProfileActivity.this, "Picture removed succesfully...", Toast.LENGTH_SHORT).show();

                            }

                            return true;
                        }
                    });

                    popup.show();//showing popup menu

                }

            }
        });
    }

    private void initializeViews() {
        hashObj = new HashMap<>();
        mAuth = FirebaseAuth.getInstance();
        databse = FirebaseDatabase.getInstance().getReference();
        mStoarge = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User_info");
        progres = new ProgressDialog(this);
        progressBar = (ProgressBar) findViewById(R.id.progre);
        pname = (EditText) findViewById(R.id.p_name);
        pemail = (EditText) findViewById(R.id.p_email);
        pcity = (EditText) findViewById(R.id.p_city);
        pcountry = (EditText) findViewById(R.id.p_country);
        pgender = (EditText) findViewById(R.id.p_gender);
        pbloodgroup = (EditText) findViewById(R.id.p_bldgrp);
        imageView = (CircularImageView) findViewById(R.id.profile_image);
        updateInfo = (Button) findViewById(R.id.updateButton);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == Gallery_Request && resultCode == RESULT_OK) {
            Uri ImageUri = data.getData();
            CropImage.activity(ImageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mImageUri = result.getUri();


                imageView.setImageURI(mImageUri);
                progres.setMessage("uploading...");
                progres.show();

                StorageReference filepath = mStoarge.child("Images").child(mImageUri.getLastPathSegment());
                filepath.putFile(mImageUri).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progres.dismiss();
                    }
                });

                filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Uri downloadUrl = taskSnapshot.getDownloadUrl();

                        DatabaseReference database_reference = databaseReference.child(mAuth.getCurrentUser().getUid());

                        database_reference.child("Profile_image").setValue(downloadUrl.toString());

                        Toast.makeText(ProfileActivity.this, "Upload image succesfully", Toast.LENGTH_SHORT).show();


                        progres.dismiss();
                    }
                });


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }


        }
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            //saves the pic locally
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] dataBAOS = baos.toByteArray();

            StorageReference imagesRef = mStoarge.child("caa.jpg");

            UploadTask uploadTask = imagesRef.putBytes(dataBAOS);
            progres.setMessage("uploading...");
            progres.show();
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Toast.makeText(ProfileActivity.this, "Failed to Profile Picture", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.

                    Uri downrl = taskSnapshot.getDownloadUrl();
                    Toast.makeText(ProfileActivity.this, "uploaded Picture Succesfully", Toast.LENGTH_SHORT).show();
                    DatabaseReference database_reference = databaseReference.child(mAuth.getCurrentUser().getUid());

                    database_reference.child("Profile_image").setValue(downrl.toString());
                    // for_message.setText(downrl.toString());
                    imageView.setImageURI(downrl);
                    progres.dismiss();
                }
            });
        }

    }

    private boolean isEmptyField(EditText editText) {
        boolean result = editText.getText().toString().length() <= 0;
        if (result)
            Toast.makeText(context, "Fill all fields!", Toast.LENGTH_SHORT).show();
        return result;
    }

    private void getAndUpdate() {
        if(!ConnectivityStatus.isConnected(ProfileActivity.this)){
            Login.showDialog(ProfileActivity.this,getResources().getString(R.string.no_internet));

        }else {
            hashObj.put("UID", mAuth.getCurrentUser().getUid());
            hashObj.put("Name", pname.getText().toString());
            hashObj.put("Email", pemail.getText().toString());
            hashObj.put("Country", pcountry.getText().toString());
            hashObj.put("City", pcity.getText().toString());
            hashObj.put("Password", Welcome_for_User.pasword);
            hashObj.put("GEnder", pgender.getText().toString());
            hashObj.put("Blood_Group", pbloodgroup.getText().toString());
            hashObj.put("Profile_image", Welcome_for_User.photoUrl);
            databse
                    .child("User_info")
                    .child(mAuth.getCurrentUser().getUid())
                    .setValue(hashObj);
            Toast.makeText(context, "profile updated Succesfuly", Toast.LENGTH_SHORT).show();
            finish();
        }



    }


    private void setEnableAllEdittext() {
        pname.setEnabled(true);
        pemail.setEnabled(true);
        pcity.setEnabled(true);
        pcountry.setEnabled(true);
        pgender.setEnabled(true);
        pbloodgroup.setEnabled(true);
    }

    private void setDisableAllEdittext() {
        pname.setEnabled(false);
        pemail.setEnabled(false);
        pcity.setEnabled(false);
        pcountry.setEnabled(false);
        pgender.setEnabled(false);
        pbloodgroup.setEnabled(false);
    }

    private void setUserValues() {
        Glide.with(ProfileActivity.this)
                .load(Welcome_for_User.photoUrl)
                .error(R.drawable.user1)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);

        pname.setText("" + Welcome_for_User.name);
        pemail.setText("" + Welcome_for_User.email);
        pcity.setText("" + Welcome_for_User.city);
        pcountry.setText("" + Welcome_for_User.country);
        pgender.setText("" + Welcome_for_User.gender);
        pbloodgroup.setText("" + Welcome_for_User.blood_d);

    }
}
