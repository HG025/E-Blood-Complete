package com.example.mt_2016.marathon_blood.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mt_2016.marathon_blood.Adapters.UserMemberAdapter;
import com.example.mt_2016.marathon_blood.Fragments.Login;
import com.example.mt_2016.marathon_blood.Models.User;
import com.example.mt_2016.marathon_blood.Models.groups_create_info;
import com.example.mt_2016.marathon_blood.NetWorkClasses.ConnectivityStatus;
import com.example.mt_2016.marathon_blood.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class GroupCreate extends AppCompatActivity {
    private ListView emailList;
    private ArrayList<User> messages;
    private UserMemberAdapter listAdapter;
    Button to_proce_gr_cr;
    Intent intent_of_gallery;
    private static int Gallery_Request = 1;
    private Uri ImageUri = null;
    ImageView group_image;
    private StorageReference mStoarge;
    String downloadUrl;
    String to_get_g_name;
    DatabaseReference databse;
    groups_create_info group;
    EditText group_Name;
    User data;
    ArrayList<String> list;
    String friend_uid_on_clicked;
    View view1;
    public HashMap<String, Object> hashObj = new HashMap<>();
    String g_by_default;
    FirebaseAuth mauth;
    User email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_create);
        initializeVIews();
        valuesToAdapter();
        clickOnListview();
        groupProfileSelect();

        getSupportActionBar().setTitle("Create Group");
        FirebaseDatabase.getInstance().getReference().child("User_info").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // This method is called once with the initial value and again
                // whenever Data at this location is updated.
                data = dataSnapshot.getValue(User.class);
                list.add(data.getUID());
                // Log.v("DATA", "" + data.getId() + data.getName() + data.getCity());
                email = new User(data.getUID(), data.getName(), data.getEmail(), data.getCountry(), data.getCity(),
                        data.getPassword(), data.getGEnder(), data.getBlood_Group(), data.getProfile_image());
                messages.add(email);
                listAdapter.notifyDataSetChanged();
//

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }

    private void groupProfileSelect() {
        group_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ConnectivityStatus.isConnected(GroupCreate.this)){
                    Login.showDialog(GroupCreate.this,getResources().getString(R.string.no_internet));

                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(GroupCreate.this);
                    builder.setTitle("Profile pic Menu.....");
                    builder.setMessage("Choose your image from the folloeing");
                    builder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (takePictureIntent.resolveActivity(GroupCreate.this.getBaseContext().getPackageManager()) != null) {
                                startActivityForResult(takePictureIntent, 111);
                            }
                        }
                    });

                    builder.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            intent_of_gallery = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent_of_gallery, Gallery_Request);
                        }
                    });
                    builder.create().show();
                }


            }

        });

    }

    private void clickOnListview() {
        emailList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                friend_uid_on_clicked = list.get(position);
                group_Name.getText().toString();
                if (group_Name.getText().toString().length() == 0) {
                    group_Name.setError("GROUP Name Can't be Blank");

                } else {
                    if (downloadUrl != null) {
                        String a_id = mauth.getCurrentUser().getUid();
                        hashObj.put("admin_uuid", a_id);
                        hashObj.put("group_name", group_Name.getText().toString());
                        hashObj.put("g_i_url", downloadUrl);
                        hashObj.put("mTimestamp", ServerValue.TIMESTAMP);
                        databse
                                .child("My_Groups")
                                .child(friend_uid_on_clicked)
                                .child(group_Name.getText().toString())
                                .setValue(hashObj);
                        messages.remove(position);
                        listAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(GroupCreate.this, "image cant be null", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }

    private void valuesToAdapter() {
        listAdapter = new UserMemberAdapter(this, R.layout.signle_user_show, messages);
        emailList.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
    }

    private void initializeVIews() {
        databse = FirebaseDatabase.getInstance().getReference();
        mauth = FirebaseAuth.getInstance();

        mStoarge = FirebaseStorage.getInstance().getReference();
        group_image = (ImageView) findViewById(R.id.group_image);
        group_Name = (EditText) findViewById(R.id.g_name);

        emailList = (ListView) findViewById(R.id.add_to_g);

        messages = new ArrayList<>();
        list = new ArrayList<>();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our visualizer_menu layout to this menu */
        inflater.inflate(R.menu.for_group_create, menu);
        /* Return true so that the visualizer_menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id_of_item = item.getItemId();
        if (id_of_item == R.id.action_create) {
            to_get_g_name = group_Name.getText().toString();
            if (group_Name.getText().toString().length() == 0) {
                group_Name.setError("GROUP Name Can't be Blank");
                Toast.makeText(GroupCreate.this, "Name cannot be Blank", Toast.LENGTH_LONG).show();

            } else {
                if (downloadUrl != null) {
                    if(!ConnectivityStatus.isConnected(GroupCreate.this)){
                        Login.showDialog(GroupCreate.this,getResources().getString(R.string.no_internet));

                    }else {
                        String a_uid = Welcome_for_User.name;
                        hashObj.put("admin_name", a_uid);
                        hashObj.put("group_name", to_get_g_name);
                        hashObj.put("g_i_url", downloadUrl);
                        hashObj.put("mTimestamp", ServerValue.TIMESTAMP);

                        databse
                                .child("Groups_info")
                                .child(to_get_g_name)
                                .setValue(hashObj);
                        String current_login = mauth.getCurrentUser().getUid();
                        databse
                                .child("My_Groups")
                                .child(current_login)
                                .child(group_Name.getText().toString())
                                .setValue(hashObj);
                        finish();
                    }


                } else {

                    Toast.makeText(GroupCreate.this, "You MUst SElect the Group Image", Toast.LENGTH_SHORT).show();

                }

            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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

                ImageUri = result.getUri();

                StorageReference filepath = mStoarge.child("Batteery").child(ImageUri.getLastPathSegment());

                filepath.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        downloadUrl = String.valueOf(taskSnapshot.getDownloadUrl());

                        Glide.with(GroupCreate.this)
                                .load(downloadUrl)
                                .into(group_image);

                    }
                });


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }


        }
        if (requestCode == 111 && resultCode == RESULT_OK) {
            //saves the pic locally
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] dataBAOS = baos.toByteArray();

            StorageReference imagesRef = mStoarge.child("caa.jpg");

            UploadTask uploadTask = imagesRef.putBytes(dataBAOS);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Toast.makeText(GroupCreate.this, "Failed to upload camera", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    downloadUrl = String.valueOf(taskSnapshot.getDownloadUrl());
                    Toast.makeText(GroupCreate.this, "upload camera", Toast.LENGTH_SHORT).show();
                    // DatabaseReference database_reference = databaseReference.child(mAuth.getCurrentUser().getUid());

                    //database_reference.child("Profile_image").setValue(downrl.toString());
                    // for_message.setText(downrl.toString());
                    Glide.with(GroupCreate.this)
                            .load(downloadUrl)
                            .into(group_image);
//                    group_image.setImageURI(Uri.parse(downloadUrl));
                }
            });
        }

    }
}
