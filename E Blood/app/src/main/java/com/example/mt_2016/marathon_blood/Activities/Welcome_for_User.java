package com.example.mt_2016.marathon_blood.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.mt_2016.marathon_blood.Adapters.TabAdapter;
import com.example.mt_2016.marathon_blood.Defaults.Defaults_Links;
import com.example.mt_2016.marathon_blood.Fragments.Five;
import com.example.mt_2016.marathon_blood.Fragments.Four;
import com.example.mt_2016.marathon_blood.Fragments.Login;
import com.example.mt_2016.marathon_blood.Fragments.One;
import com.example.mt_2016.marathon_blood.Fragments.Two;
import com.example.mt_2016.marathon_blood.Fragments.Members;
import com.example.mt_2016.marathon_blood.MainActivity;
import com.example.mt_2016.marathon_blood.Models.User;
import com.example.mt_2016.marathon_blood.NetWorkClasses.ConnectivityStatus;
import com.example.mt_2016.marathon_blood.R;
import com.example.mt_2016.marathon_blood.Services.Chat_Notification;
import com.example.mt_2016.marathon_blood.Services.PostBloodService;
import com.example.mt_2016.marathon_blood.Services.groupChatNotificationService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Map;

public class Welcome_for_User extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private TabAdapter adapter;
    private One mTab1;
    private Two mTab2;
    private Members mTab3;
    private Four mTab4;
    private Five mTab5;
    public static String name;
    TextView show_name;
    TextView show_blodd;
    CircularImageView userimage;
    private ArrayList<Fragment> mFragmentArrayList;
    private DrawerLayout drawer;
    Intent intent_of_gallery;
    private static int Gallery_Request = 1;
    ProgressDialog progres;
    private StorageReference mStoarge;
    private DatabaseReference databaseReference;
    private Uri mImageUri = null;
    ProgressBar progressBar;
    public static String blood_d;
    public static String photoUrl;
    public static String email;
    public static String country;
    public static String city;
    public static String gender;
    public static String pasword;
    public static String login_user;
    View hView;
    Toolbar toolbar;
    NavigationView navigationView;
    LinearLayout linearLayout;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(!ConnectivityStatus.isConnected(Welcome_for_User.this)){
                linearLayout.setVisibility(View.VISIBLE);

            }else {
                // connected
                linearLayout.setVisibility(View.GONE);
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_for__user);

        this.registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        startServices();
        initializeviews();
        choseWayToChangeImage();
        setSupportActionBar(toolbar);

        FirebaseDatabase.getInstance().getReference().child("User_info").child(login_user).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                Map<String, String> map = (Map) dataSnapshot.getValue();
                blood_d = map.get("Blood_Group");
                name = map.get("Name");
                photoUrl = map.get("Profile_image");
                city = map.get("City");
                country = map.get("Country");
                email = map.get("Email");
                gender = map.get("GEnder");
                pasword = map.get("Password");
//                sendEmail();


                if (!TextUtils.isEmpty(name)) {
                    show_name.setText(name);
                    getSupportActionBar().setTitle(name);


                }
                if (!TextUtils.isEmpty(blood_d)) {
                    show_blodd.setText(blood_d);

                }
                if (!TextUtils.isEmpty(photoUrl)) {

//                    Picasso.with(Chat_Main_View.this).load(photoUrl).into(iv);
                    Glide.with(getApplicationContext())
                            .load(photoUrl)
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
                            .into(userimage);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mTab1 = new One();
        mTab2 = new Two();
        mTab3 = new Members();
        mTab4 = new Four();
        mTab5 = new Five();

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mFragmentArrayList = new ArrayList<>();
        mFragmentArrayList.add(mTab1);
        mFragmentArrayList.add(mTab2);
        mFragmentArrayList.add(mTab3);
        mFragmentArrayList.add(mTab4);
        mFragmentArrayList.add(mTab5);

        mTabLayout.addTab(mTabLayout.newTab().setText("All Posts"));
        mTabLayout.addTab(mTabLayout.newTab().setText("My Posts"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Members"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Groups"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Notifications"));


        adapter = new TabAdapter(getSupportFragmentManager(), mFragmentArrayList);

        mViewPager.setAdapter(adapter);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mViewPager.setOffscreenPageLimit(0);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        new One();
                        break;
                    case 1:
                        new Two();
                        break;
                    case 2:
                        new Members();
                        break;
                    case 3:
                        new Four();
                    case 4:
                        new Five();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


    }

    private void sendEmail() {
        String emailAddress = "babarmustafaawan@gmail.com";

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Welcome_for_User.this, "Email sent", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(Welcome_for_User.this, "Email Not sent", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }


    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(receiver);
    }

    private void initializeviews() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        hView = navigationView.getHeaderView(0);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        show_name = (TextView) hView.findViewById(R.id.username_view);
        show_blodd = (TextView) hView.findViewById(R.id.textView_b);
        userimage = (CircularImageView) hView.findViewById(R.id.imageView);
        progressBar = (ProgressBar) hView.findViewById(R.id.progress_us);
        linearLayout = (LinearLayout) findViewById(R.id.netstate);
        mStoarge = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User_info");
        progres = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        login_user = mAuth.getCurrentUser().getUid();

    }

    private void choseWayToChangeImage() {
        userimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ConnectivityStatus.isConnected(Welcome_for_User.this)){
                    Login.showDialog(Welcome_for_User.this,getResources().getString(R.string.no_internet));

                }else {
                    //Creating the instance of PopupMenu
                    PopupMenu popup = new PopupMenu(Welcome_for_User.this, userimage);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater().inflate(R.menu.popup_menu_for_profile_pic, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
//                                Toast.makeText(Welcome_for_User.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                            int id = item.getItemId();
                            if (id == R.id.chose_from_gallery) {
                                Toast.makeText(Welcome_for_User.this, "galley", Toast.LENGTH_SHORT).show();
                                intent_of_gallery = new Intent(Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(intent_of_gallery, Gallery_Request);

                            }
                            if (id == R.id.chose_form_camera) {
                                Toast.makeText(Welcome_for_User.this, "camera", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(Welcome_for_User.this, "Picture removed succesfully...", Toast.LENGTH_SHORT).show();

                            }

                            return true;
                        }
                    });

                    popup.show();//showing popup menu
                }


            }
        });
    }

    private void startServices() {
        Intent t = new Intent(Welcome_for_User.this, PostBloodService.class);
        startService(t);
        Intent msgService = new Intent(Welcome_for_User.this, Chat_Notification.class);
        startService(msgService);
        Intent groupmsgService = new Intent(Welcome_for_User.this, groupChatNotificationService.class);
        startService(groupmsgService);
    }

    //crop work
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


                userimage.setImageURI(mImageUri);
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

                        Toast.makeText(Welcome_for_User.this, "Upload image succesfully", Toast.LENGTH_SHORT).show();


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
                    Toast.makeText(Welcome_for_User.this, "Failed to Profile Picture", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.

                    Uri downrl = taskSnapshot.getDownloadUrl();
                    Toast.makeText(Welcome_for_User.this, "uploaded Picture Succesfully", Toast.LENGTH_SHORT).show();
                    DatabaseReference database_reference = databaseReference.child(mAuth.getCurrentUser().getUid());

                    database_reference.child("Profile_image").setValue(downrl.toString());
                    // for_message.setText(downrl.toString());
                    userimage.setImageURI(downrl);
                    progres.dismiss();
                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 1) {
            manager.popBackStack();
        } else {
            // if there is only one entry in the backstack, show the home screen

            moveTaskToBack(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.welcome_for__user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
//            mAuth= FirebaseAuth.getInstance();
//            LoginManager.getInstance().logOut();
//            mAuth.signOut();
//            mAuth=null;
            FirebaseAuth.getInstance().signOut();
            User user = new User();

            Intent call = new Intent(Welcome_for_User.this, MainActivity.class);
            startActivity(call);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.allposts) {

            drawer.closeDrawer(GravityCompat.START);
            mViewPager.setCurrentItem(0);
        } else if (id == R.id.myPosts) {

            drawer.closeDrawer(GravityCompat.START);
            mViewPager.setCurrentItem(1);
        } else if (id == R.id.chat) {

            drawer.closeDrawer(GravityCompat.START);
            mViewPager.setCurrentItem(2);
        } else if (id == R.id.groups) {
            drawer.closeDrawer(GravityCompat.START);
            mViewPager.setCurrentItem(3);
        } else if (id == R.id.pnotif) {
            drawer.closeDrawer(GravityCompat.START);
            mViewPager.setCurrentItem(4);
        } else if (id == R.id.myprofile) {
            if(!ConnectivityStatus.isConnected(Welcome_for_User.this)){
                Login.showDialog(Welcome_for_User.this,getResources().getString(R.string.no_internet));

            }else {
                drawer.closeDrawer(GravityCompat.START);

                Intent call = new Intent(this, ProfileActivity.class);
                startActivity(call);
            }

        } else if (id == R.id.changepassword) {
            if(!ConnectivityStatus.isConnected(Welcome_for_User.this)){
                Login.showDialog(Welcome_for_User.this,getResources().getString(R.string.no_internet));

            }else {
                drawer.closeDrawer(GravityCompat.START);
                Intent call = new Intent(this, ChangePassword.class);
                startActivity(call);
            }


        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
