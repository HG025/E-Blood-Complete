package com.example.mt_2016.marathon_blood.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;

import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.StringLoader;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.mt_2016.marathon_blood.Adapters.MesagesAdapter;
import com.example.mt_2016.marathon_blood.Models.Conver;
import com.example.mt_2016.marathon_blood.Models.NotificationMessage;
import com.example.mt_2016.marathon_blood.Models.User;
import com.example.mt_2016.marathon_blood.R;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
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
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class ConversationActivity extends Activity {

    EditText for_message;
    ListView conversation;
    Button to_send;

    CircularImageView for_user_image_on_toolbar;
    TextView for_user_name_selected_for_chat;


    DatabaseReference database;
    private FirebaseAuth mAuth;


    ArrayList<String> list;

    User f_data;
    String UUID;
    String get_f_id_on_clicked;
    String get_f_pic;
    String get_f_name;
    String get_f_email;
    String get_f_gender;
    String get_f_bG;
    Intent intent_of_gallery;
    private static int Gallery_Request = 1;

    private String conversationPushRef;
    private Conver conversationData;
    private boolean isConversationOld = false;
    private static final int SAVE_REQUEST_CODE = 1;
    private static final int REQUEST_IMAGE = 11;

    public HashMap<String, String> hashObj = new HashMap<>();
    public HashMap<String, String> hashObj2 = new HashMap<>();
    NotificationMessage notificationMessage;

    MesagesAdapter listadapter;
    private ArrayList<NotificationMessage> messages;
    private FirebaseUser user;
    private StorageReference mStoarge;
    StorageReference folderRef;
    private Uri mImageUri = null;
    private Uri ImageUri = null;

    Uri downloadUrl;
    Uri downloadl;
    private long fileLenght;
    ImageButton for_file_sharing;
    ImageButton for_image_sharing;
    ImageButton for_audio_sharing;
    ImageButton for_camera_pic;
    ImageButton for_video_sharing;
    boolean tocheck = false;
    ImageButton back_to_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        initializeViews();
        setDataTOListview();
        getDataFromINtent();
        setUserNameAndIMagetoToolbar();
        ClickOnName();
        checkConversationNewOROLD();
        VideoSharing();
        FileSharing();
        ImageFromGallerySharing();
        audioSharing();
        SharingPicFromCamera();
    }

    private void SharingPicFromCamera() {
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

    private void audioSharing() {
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

    private void ImageFromGallerySharing() {
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

    private void FileSharing() {
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

    private void setUserNameAndIMagetoToolbar() {
        Glide.with(ConversationActivity.this)
                .load(get_f_pic)
                .error(R.drawable.user1)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                        progressBar.setVisibility(View.GONE);
                        return false;
//                        pcircularImageView.setImageURI(Uri.parse("https://firebasestorage.googleapis.com/v0/b/chatapplication-f99c2.appspot.com/o/grou_ico.png?alt=media&token=b664fb78-2b74-49b5-8297-6bc0e9e3c6c9"));

                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(for_user_image_on_toolbar);
        for_user_name_selected_for_chat.setText(get_f_name);
    }

    private void getDataFromINtent() {
        get_f_id_on_clicked = getIntent().getStringExtra("friend_uid");
        get_f_pic = getIntent().getStringExtra("friend_image");
        get_f_name = getIntent().getStringExtra("friend_name");
        get_f_email = getIntent().getStringExtra("friend_email");
        get_f_gender = getIntent().getStringExtra("friend_gender");
        get_f_bG = getIntent().getStringExtra("friend_bG");
    }

    private void setDataTOListview() {

        listadapter = new MesagesAdapter(messages, ConversationActivity.this);
        conversation.setAdapter(listadapter);

    }

    private void initializeViews() {
        for_message = (EditText) findViewById(R.id.editMessage);
        to_send = (Button) findViewById(R.id.button_Send);
        back_to_main = (ImageButton) findViewById(R.id.throw_back);
        conversation = (ListView) findViewById(R.id.messages_conversation);
        for_user_image_on_toolbar = (CircularImageView) findViewById(R.id.user_pic);
        for_user_name_selected_for_chat = (TextView) findViewById(R.id.selected);
        for_file_sharing = (ImageButton) findViewById(R.id.for_files);
        for_image_sharing = (ImageButton) findViewById(R.id.for_images);
        for_audio_sharing = (ImageButton) findViewById(R.id.for_sound);
        for_camera_pic = (ImageButton) findViewById(R.id.for_camera);
        for_video_sharing = (ImageButton) findViewById(R.id.for_video);
        mStoarge = FirebaseStorage.getInstance().getReference();
        folderRef = mStoarge.child("chat_files");
        database = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        list = new ArrayList<>();
        messages = new ArrayList<>();
        user = mAuth.getInstance().getCurrentUser();
        UUID = mAuth.getCurrentUser().getUid();
        conversation.setDivider(null);
        conversation.setDividerHeight(0);
    }

    private void checkConversationNewOROLD() {
        database.child("user_conv").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        Conver data = d.getValue(Conver.class);
                        if (data.getUserid().equals(get_f_id_on_clicked)) {

                            conversationData = data;
                            isConversationOld = true;
                            conversationPushRef = data.getConversationId();
                        }
                    }

                } catch (Exception ec) {
                    ec.printStackTrace();
                } finally {
                    getConvoDataOrCreateNew();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        back_to_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void getConvoDataOrCreateNew() {
        if (isConversationOld) {
            getConversationData();
        } else {
            createNewConversation();
        }
    }

    private void createNewConversation() {
        DatabaseReference pushRef = database.child("conversation").push();
        conversationPushRef = pushRef.getKey();
        final Conver tempRefObj = new Conver(conversationPushRef, get_f_id_on_clicked);


        database.child("user_conv").child(UUID).push().setValue(tempRefObj, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    tempRefObj.setUserid(mAuth.getCurrentUser().getUid());
                    database.child("user_conv").child(get_f_id_on_clicked).push().setValue(tempRefObj, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError == null) {
                                getConversationData();
                            }
                        }
                    });
                }
            }
        });
    }


    private void getConversationData() {
        database.child("conversation").child(conversationPushRef).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messages.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    NotificationMessage message = d.getValue(NotificationMessage.class);
                    messages.add(message);
                    listadapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        setButtonClick();
    }


    private void setButtonClick() {
        to_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String push = database.push().getKey();
                String eee = for_message.getText().toString();
                String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

                if (for_message.getText().length() > 1) {
                    NotificationMessage m = new NotificationMessage();
                    HashMap<String, Object> hashobj = new HashMap<String, Object>();
                    hashobj.put("message", eee);
                    hashobj.put("uuid", user.getUid());
                    hashobj.put("mTimestamp", ServerValue.TIMESTAMP);
                    database.child("conversation").child(conversationPushRef).push().setValue(hashobj);
                    HashMap<String, Object> has = new HashMap<String, Object>();
                    has.put("message", eee);
                    has.put("uuid", user.getUid());
                    has.put("mTimestamp", ServerValue.TIMESTAMP);
                    has.put("notfpush", push);
                    has.put("whoMessage", Welcome_for_User.name);
                    database.child("NotificationsForChatOfUsers").child(get_f_id_on_clicked).child(push).setValue(has);
                    for_message.setText("");
                }
            }
        });
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
                    Toast.makeText(ConversationActivity.this, "ailed to upload camera", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    Uri downrl = taskSnapshot.getDownloadUrl();
                    Toast.makeText(ConversationActivity.this, "upload camera", Toast.LENGTH_SHORT).show();
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
            AlertDialog.Builder builder = new AlertDialog.Builder(ConversationActivity.this);
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


                        Toast.makeText(ConversationActivity.this, "Upload image succesfully", Toast.LENGTH_SHORT).show();
                        for_message.setText(downloadl.toString());

                    }
                });
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }

        }


    }


    public void ClickOnName() {
        for_user_name_selected_for_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(), UserProfile.class);
                i.putExtra("username", get_f_name);
                i.putExtra("userimage", get_f_pic);
                i.putExtra("useremail", get_f_email);
                i.putExtra("usergender", get_f_gender);
                i.putExtra("friend_bG", get_f_bG);
                startActivity(i);
            }
        });
    }

    private void uploadDocOrFile(String filePath, Uri uri) {

        Date date = new Date(System.currentTimeMillis());
        File fileRef = new File(filePath);
        final String filenew = fileRef.getName();

        int dot = filenew.lastIndexOf('.');
        String base = (dot == -1) ? filenew : filenew.substring(0, dot);
        final String extension = (dot == -1) ? "" : filenew.substring(dot + 1);
        final ProgressDialog uploadPDialoge = new ProgressDialog(ConversationActivity.this);
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
                    Toast.makeText(ConversationActivity.this, "failed", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(ConversationActivity.this, "File size is too large !", Toast.LENGTH_LONG).show();
        }
    }


}


