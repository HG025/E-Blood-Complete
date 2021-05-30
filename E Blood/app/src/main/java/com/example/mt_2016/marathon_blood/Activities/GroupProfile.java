package com.example.mt_2016.marathon_blood.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mt_2016.marathon_blood.Adapters.MyRecyclerViewAdapter;
import com.example.mt_2016.marathon_blood.Models.User;
import com.example.mt_2016.marathon_blood.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;


public class GroupProfile extends AppCompatActivity {
    ImageView imageView;
    Toolbar toolbar;
    TextView name, email, gender;
    RecyclerView group_members;
    MyRecyclerViewAdapter adapter;
    ArrayList<User> users_list;
    User data;
    String groupName;
    ArrayList<String> list;
    DatabaseReference database;
    String m_pos;
    User emai_l;
    public String group_real_admin;
    String AdminName;
    TextView addParticipents;
    String groupImage;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_profile);
        initializeviews();
        addMember();
        RecylerVIewsetData();
        EnalingBackBUtton();
        getDataFromINtent();
        CollapsingWork();
        LoadingGroupImage();
        showAddMemberlayout();
        FirebaseDatabase.getInstance().getReference().child("Groups_info").child(groupName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String, String> map = (Map) dataSnapshot.getValue();

                group_real_admin = map.get("admin_uuid");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        FirebaseDatabase
                .getInstance()
                .getReference()
                .child("My_Groups")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Log.d("Testing", " Key " + dataSnapshot.getKey() + " values " + dataSnapshot.getValue().toString());
                                String s = snapshot.getKey().toString();

                                if (snapshot.hasChild(groupName)) {

                                    FirebaseDatabase
                                            .getInstance()
                                            .getReference()
                                            .child("User_info")
                                            .child(s)
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    if (dataSnapshot != null) {
                                                        data = dataSnapshot.getValue(User.class);
                                                        list.add(data.getUID());

                                                        emai_l = new User(data.getUID(), data.getName(), data.getEmail(), data.getCountry(),
                                                                data.getCity(), data.getPassword(), data.getGEnder(), data.getBlood_Group()
                                                                , data.getProfile_image(), group_real_admin,groupName);
                                                        users_list.add(emai_l);
                                                        adapter.notifyDataSetChanged();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });
//
                                } else {

                                }
                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
//
//        ItemClickSupport.addTo(group_members).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//            @Override
//            public void onItemClicked(RecyclerView recyclerView, final int position, View v) {
//
//                m_pos =  list.get(position);
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(GroupProfile.this);
//                builder.setTitle("Do you want to remove this person ");
//                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if (Welcome_for_User.name.equals(group_real_admin)){
//
//
//                            database
//                                    .child("My_Groups")
//                                    .child(m_pos)
//                                    .child(groupName)
//                                    .removeValue();
//                            startActivity(getIntent());
//
//                        }
//                        else {
//
//                            Toast.makeText(GroupProfile.this, "You donot have rights to remove members", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                });
//                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//                builder.create().show();
////                database
//
//            }
//        });

    }

    private void LoadingGroupImage() {
        Glide.with(GroupProfile.this).load(groupImage).into(imageView);

    }

    private void EnalingBackBUtton() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void CollapsingWork() {
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.maincollapsing);
        collapsingToolbarLayout.setTitle(groupName);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.cardview_light_background));
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
    }

    private void RecylerVIewsetData() {
        group_members.addItemDecoration(new DividerItemDecoration(GroupProfile.this,
                DividerItemDecoration.VERTICAL));
        adapter = new MyRecyclerViewAdapter(this, users_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        group_members.setLayoutManager(mLayoutManager);
        group_members.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration itemDecorator = new DividerItemDecoration(GroupProfile.this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(GroupProfile.this, R.drawable.divider));
        group_members.setAdapter(adapter);


    }

    private void getDataFromINtent() {
        groupName = getIntent().getStringExtra("groupname");
        AdminName = getIntent().getStringExtra("Adminuuid");
        groupImage = getIntent().getStringExtra("groupImage");
    }

    private void initializeviews() {
        mAuth = FirebaseAuth.getInstance();
        users_list = new ArrayList<>();
        list = new ArrayList<>();
        database = FirebaseDatabase.getInstance().getReference();
        imageView = (ImageView) findViewById(R.id.mainbackdropforgroup);
        toolbar = (Toolbar) findViewById(R.id.maintoolbar);
        group_members = (RecyclerView) findViewById(R.id.rvOrderList);
        addParticipents = (TextView) findViewById(R.id.addPArticipents);
        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        gender = (TextView) findViewById(R.id.gender);

    }

    private void showAddMemberlayout() {
        if (AdminName.equals(mAuth.getCurrentUser().getUid())) {
        } else {
            addParticipents.setVisibility(View.GONE);
        }
    }


    private void addMember() {
        addParticipents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addParticipents = new Intent(GroupProfile.this, AddParticipents.class);
                addParticipents.putExtra("group_name", groupName);
                addParticipents.putExtra("group_admin_uuid", group_real_admin);
                addParticipents.putExtra("group_ima_url", groupImage);
                startActivity(addParticipents);
            }
        });
    }
}
