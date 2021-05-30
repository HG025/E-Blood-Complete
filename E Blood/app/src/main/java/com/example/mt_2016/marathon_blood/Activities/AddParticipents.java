package com.example.mt_2016.marathon_blood.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mt_2016.marathon_blood.Adapters.UserMemberAdapter;
import com.example.mt_2016.marathon_blood.Models.User;
import com.example.mt_2016.marathon_blood.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class AddParticipents extends AppCompatActivity {
    private ListView emailList;
    private ArrayList<User> messages;
    private UserMemberAdapter listAdapter;
    ArrayList<String> list;
    User data;
    User email;
    String get_grou_name;
    String get_g_pic;
    String get_a_name;
    public HashMap<String, Object> hashObj = new HashMap<>();
    int a = 0;
    LinearLayout backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_participents);
        getSupportActionBar().hide();
        initializeViews();
        setDataToAdapter();
        getDataFRomIntent();
        listViewClick();
        tofinish();

        FirebaseDatabase.getInstance().getReference().child("User_info").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // This method is called once with the initial value and again
                // whenever Data at this location is updated.
                data = dataSnapshot.getValue(User.class);
                list.add(data.getUID());
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

    private void tofinish() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddParticipents.this, "fi", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void getDataFRomIntent() {
        get_grou_name = getIntent().getStringExtra("group_name");
        get_g_pic = getIntent().getStringExtra("group_ima_url");
        get_a_name = getIntent().getStringExtra("group_admin_uuid");
    }

    private void setDataToAdapter() {

        listAdapter = new UserMemberAdapter(this, R.layout.signle_user_show, messages);
        emailList.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
    }

    private void initializeViews() {
        emailList = (ListView) findViewById(R.id.addPArticipents);
        backButton = (LinearLayout) findViewById(R.id.backmo);
        messages = new ArrayList<>();
        list = new ArrayList<>();
    }

    private void listViewClick() {
        emailList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String idof = list.get(position);
                FirebaseDatabase
                        .getInstance()
                        .getReference()
                        .child("My_Groups")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    if (dataSnapshot.child(idof).exists()) {
                                        if (data.hasChild(get_grou_name)) {
//                                            a++;
//                                            if (a== 1){}
                                            Toast.makeText(AddParticipents.this, "THis Person is Already in Group " + get_grou_name, Toast.LENGTH_SHORT).show();
                                        } else {
                                            //add krna pare ga group m
                                            hashObj.put("admin_uuid", get_a_name);
                                            hashObj.put("group_name", get_grou_name);
                                            hashObj.put("g_i_url", get_g_pic);
                                            hashObj.put("mTimestamp", ServerValue.TIMESTAMP);
                                            FirebaseDatabase
                                                    .getInstance()
                                                    .getReference()
                                                    .child("My_Groups")
                                                    .child(idof)
                                                    .child(get_grou_name)
                                                    .setValue(hashObj);
                                            Toast.makeText(AddParticipents.this, "Member Added Succesfully", Toast.LENGTH_SHORT).show();
                                            finish();

                                            Intent conwindow = new Intent(AddParticipents.this, Group_coversation.class);
//
                                            conwindow.putExtra("group_name", get_grou_name);
                                            conwindow.putExtra("group_admin_uuid", get_a_name);
                                            conwindow.putExtra("group_ima_url", get_g_pic);


                                            startActivity(conwindow);

                                        }
                                        //do ur stuff
                                    } else {
                                        //add krna pare ga group m
                                        hashObj.put("admin_uuid", get_a_name);
                                        hashObj.put("group_name", get_grou_name);
                                        hashObj.put("g_i_url", get_g_pic);
                                        hashObj.put("mTimestamp", ServerValue.TIMESTAMP);
                                        FirebaseDatabase
                                                .getInstance()
                                                .getReference()
                                                .child("My_Groups")
                                                .child(idof)
                                                .child(get_grou_name)
                                                .setValue(hashObj);
                                        Toast.makeText(AddParticipents.this, "Member Added Succesfully", Toast.LENGTH_SHORT).show();
                                        finish();
                                        //do something
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }
        });
    }
}
