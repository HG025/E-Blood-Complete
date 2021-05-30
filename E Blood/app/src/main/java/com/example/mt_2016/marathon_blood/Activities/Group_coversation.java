package com.example.mt_2016.marathon_blood.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mt_2016.marathon_blood.Adapters.G_MeesageAdapter;
import com.example.mt_2016.marathon_blood.Models.grouop_convo;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class Group_coversation extends Activity {
    String get_grou_name;
    String get_g_pic;
    String get_a_name;
    EditText for_message;
    Button to_send;
    CircularImageView for_user_image_on_toolbar;
    TextView for_user_name_selected_for_chat;
    private FirebaseAuth mAuth;
    DatabaseReference database;
    ListView conversation;
    ArrayList<String> list;
    G_MeesageAdapter listadapter;
    private ArrayList<grouop_convo> messages;
    grouop_convo data;
    Uri downloadl;
    private long fileLenght;
    ImageButton for_file_sharing;
    ImageButton for_image_sharing;
    ImageButton for_audio_sharing;
    ImageButton for_camera_pic;
    ImageButton for_video_sharing;
    boolean tocheck = false;
    private StorageReference mStoarge;
    StorageReference folderRef;
    private Uri mImageUri = null;
    private Uri ImageUri = null;
    private static final int SAVE_REQUEST_CODE = 1;
    Intent intent_of_gallery;
    private static int Gallery_Request = 1;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_coversation);
        initializeViews();
        setValuesToAdapter();
        getDataFRomIntent();
        setDataToToolbaer();
        toViewGroupDetailsAndMembers();
        toFinish();


        database
                .child("GroupData")
                .child(get_grou_name)
                .child("Conversation")
                .addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        // This method is called once with the initial value and again
                        // whenever Data at this location is updated.
                        data = dataSnapshot.getValue(grouop_convo.class);
//                list.add(data.getUID());
                        // Log.v("DATA", "" + data.getId() + data.getName() + data.getCity());
                        grouop_convo email = new grouop_convo(data.getMsg(), data.getTimeInMillis(), data.getSendr_id(), data.getSendername());
                        messages.add(email);
                        listadapter.notifyDataSetChanged();
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
        VideoSharing();
        fileSharing();
        ShareIMageFromGallery();
        AudioSharing();
        PicFromCamera();
        SendGroupMessage();
    }

    private void SendGroupMessage() {
        to_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eee = for_message.getText().toString();
                String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                String get_u = mAuth.getCurrentUser().getUid();

                if (for_message.getText().length() > 1) {
                    grouop_convo m = new grouop_convo();
                    HashMap<String, Object> hashobj = new HashMap<String, Object>();
                    hashobj.put("msg", eee);
                    hashobj.put("sendr_id", get_u);
                    hashobj.put("sendername", Welcome_for_User.name);
                    hashobj.put("mTimestamp", ServerValue.TIMESTAMP);
                    final HashMap<String, Object> hashob = new HashMap<String, Object>();
                    final String pushForNotfoFGroupv = database.push().getKey();
                    hashob.put("msg", eee);
                    hashob.put("sendr_id", get_u);
                    hashob.put("sendername", Welcome_for_User.name);
                    hashob.put("mTimestamp", ServerValue.TIMESTAMP);
                    hashob.put("groupName", get_grou_name);
                    hashob.put("notifPush", pushForNotfoFGroupv);
                    database
                            .child("GroupData")
                            .child(get_grou_name)
                            .child("Conversation")
                            .push()
                            .setValue(hashobj);
                    FirebaseDatabase
                            .getInstance()
                            .getReference()
                            .child("My_Groups")
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot != null) {
                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                            String s = snapshot.getKey().toString();

                                            if (snapshot.hasChild(get_grou_name)) {
                                                if (mAuth.getCurrentUser().getUid().equals(s)) {

                                                } else {
                                                    database
                                                            .child("NotificationforGroupChat")
                                                            .child(s)
                                                            .child(pushForNotfoFGroupv)
                                                            .setValue(hashob);
                                                }


                                            }
                                        }

                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                    for_message.setText("");
                }
            }

        });
    }

    private void PicFromCamera() {
        for_camera_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getBaseContext().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 111);
                }
            }
        });
    }

    private void AudioSharing() {
        for_audio_sharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tocheck = true;
                Intent intent_upload = new Intent();
                intent_upload.setType("audio/*");
                intent_upload.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent_upload, SAVE_REQUEST_CODE);


            }
        });
    }

    private void ShareIMageFromGallery() {
        for_image_sharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tocheck = false;

                intent_of_gallery = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent_of_gallery, Gallery_Request);
            }
        });
    }

    private void fileSharing() {

        for_file_sharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tocheck = true;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/*");
                startActivityForResult(intent, SAVE_REQUEST_CODE);
            }
        });
    }

    private void VideoSharing() {
        for_video_sharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tocheck = true;
                Intent inte = new Intent();
                inte.setType("video/*");
                inte.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(inte, SAVE_REQUEST_CODE);
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent,"Select Video"),REQUEST_TAKE_GALLERY_VIDEO);
            }
        });
    }

    private void toFinish() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void toViewGroupDetailsAndMembers() {

        for_user_name_selected_for_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Group_coversation.this, GroupProfile.class);
                i.putExtra("groupname", get_grou_name);
                i.putExtra("groupImage", get_g_pic);
                i.putExtra("Adminuuid", get_a_name);
                startActivity(i);
            }
        });
    }

    private void setDataToToolbaer() {
        Glide.with(this)
                .load(get_g_pic)
                .into(for_user_image_on_toolbar);
//        Picasso.with(ConversationActivity.this).load(get_f_pic).into(for_user_image_on_toolbar);
        for_user_name_selected_for_chat.setText(get_grou_name);
    }

    private void getDataFRomIntent() {
        get_grou_name = getIntent().getStringExtra("group_name");
        get_g_pic = getIntent().getStringExtra("group_ima_url");
        get_a_name = getIntent().getStringExtra("group_admin_uuid");
    }

    private void setValuesToAdapter() {

        listadapter = new G_MeesageAdapter(messages, Group_coversation.this);
        conversation.setAdapter(listadapter);
    }

    private void initializeViews() {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        for_user_image_on_toolbar = (CircularImageView) findViewById(R.id.group_pic);
        for_user_name_selected_for_chat = (TextView) findViewById(R.id.g_ni);
        for_message = (EditText) findViewById(R.id.g_editMessage);
        to_send = (Button) findViewById(R.id.g_button_Send);
        conversation = (ListView) findViewById(R.id.g_messages_conversation);
        for_file_sharing = (ImageButton) findViewById(R.id.g_for_files);
        for_image_sharing = (ImageButton) findViewById(R.id.g_for_images);
        for_audio_sharing = (ImageButton) findViewById(R.id.g_for_sound);
        for_camera_pic = (ImageButton) findViewById(R.id.g_for_camera);
        for_video_sharing = (ImageButton) findViewById(R.id.g_for_video);
        back = (ImageButton) findViewById(R.id.throwbutton);
        mStoarge = FirebaseStorage.getInstance().getReference();

        folderRef = mStoarge.child("g_chat_files");
        list = new ArrayList<>();
        messages = new ArrayList<>();
    }

    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        // TODO Auto-generated method stub
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            //saves the pic locally
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] dataBAOS = baos.toByteArray();

            StorageReference imagesRef = mStoarge.child("camera_images.jpg");

            UploadTask uploadTask = imagesRef.putBytes(dataBAOS);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Toast.makeText(Group_coversation.this, "ailed to upload camera", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    Uri downrl = taskSnapshot.getDownloadUrl();
                    Toast.makeText(Group_coversation.this, "upload camera", Toast.LENGTH_SHORT).show();
                    for_message.setText(downrl.toString());
                }
            });
        }


        if (requestCode == SAVE_REQUEST_CODE && resultCode == RESULT_OK && tocheck == true) {
            final Uri uri = data.getData();
            String filePath = null;
            if (uri.getScheme().equals("content")) {
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                try {
                    if (cursor != null && cursor.moveToFirst()) {
                        filePath = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    cursor.close();
                }
            }
            if (filePath == null) {
                filePath = uri.getPath();
                int cut = filePath.lastIndexOf('/');
                if (cut != -1) {
                    filePath = filePath.substring(cut + 1);
                }
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(Group_coversation.this);
            builder.setTitle("Want to Send File or not ?");
            final String finalFilePath = filePath;
            builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    uploadDocOrFile(finalFilePath, uri);
                }
            });

            builder.create().show();

        }
        if (requestCode == Gallery_Request && resultCode == RESULT_OK && tocheck == false) {
            Uri ImageUri = data.getData();
            CropImage.activity(ImageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);


        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                ImageUri = result.getUri();
//                       for_s.setImageURI(mImageUri);
                StorageReference filath = mStoarge.child("chat_images").child(ImageUri.getLastPathSegment());
                filath.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        downloadl = taskSnapshot.getDownloadUrl();


                        Toast.makeText(Group_coversation.this, "Upload image succesfully", Toast.LENGTH_SHORT).show();
                        for_message.setText(downloadl.toString());

                    }
                });
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }

        }


    }

    private void uploadDocOrFile(String filePath, Uri uri) {

        Date date = new Date(System.currentTimeMillis());
        File fileRef = new File(filePath);
        final String filenew = fileRef.getName();

        int dot = filenew.lastIndexOf('.');
        String base = (dot == -1) ? filenew : filenew.substring(0, dot);
        final String extension = (dot == -1) ? "" : filenew.substring(dot + 1);
        final ProgressDialog uploadPDialoge = new ProgressDialog(Group_coversation.this);
        uploadPDialoge.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        uploadPDialoge.setTitle("Uploading");
        uploadPDialoge.setMessage("File Uploading Please wait !");
        uploadPDialoge.setIndeterminate(false);
        uploadPDialoge.setCancelable(false);
        uploadPDialoge.setMax(100);
        uploadPDialoge.show();
        fileLenght = fileRef.length();
        fileLenght = fileLenght / 1024;
        System.out.println("File Path : " + fileRef.getPath() + ", File size : " + fileLenght + " KB");
        Log.d("uridata", filePath);
        Log.d("uridataLastSegment", uri.getLastPathSegment());
        final long FIVE_MEGABYTE = 1024 * 1024 * 20;
        fileLenght = fileLenght * 1024;
        UploadTask uploadTask;

        if (fileLenght <= FIVE_MEGABYTE) {
            Uri file = Uri.fromFile(new File(filePath));

            mStoarge = folderRef.child(uri + "." + extension);
            uploadTask = mStoarge.putFile(uri);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(Group_coversation.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String downloadUrl = taskSnapshot.getDownloadUrl().toString();
                    Log.d("DownloadURL", downloadUrl.toString());

                    for_message.setText(downloadUrl.toString());

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = 0;
                    progress += (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    System.out.println("Upload is " + progress + "% done");
                    uploadPDialoge.setProgress((int) progress);
                    if (progress == 100) {
                        uploadPDialoge.dismiss();
                    }
                }
            }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                    System.out.println("Upload is paused");
                }
            });
        } else {
            Toast.makeText(Group_coversation.this, "File size is too large !", Toast.LENGTH_LONG).show();
        }
    }

}
