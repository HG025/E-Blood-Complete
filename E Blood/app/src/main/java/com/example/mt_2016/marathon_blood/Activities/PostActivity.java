package com.example.mt_2016.marathon_blood.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mt_2016.marathon_blood.Fragments.Login;
import com.example.mt_2016.marathon_blood.Models.Post;
import com.example.mt_2016.marathon_blood.Models.User;
import com.example.mt_2016.marathon_blood.NetWorkClasses.ConnectivityStatus;
import com.example.mt_2016.marathon_blood.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class PostActivity extends AppCompatActivity {

    Spinner for_b_group;
    EditText for_quantity;
    Spinner for_urgency;
    Spinner for_country;
    Spinner for_state;
    Spinner for_city;
    Spinner for_hospital;
    Spinner for_relation;
    EditText for_contact_number;
    EditText instructions;
    ArrayList<String> blood_groups;
    ArrayList<String> blood_urgency;
    ArrayList<String> blood_country;
    ArrayList<String> blood_state;
    ArrayList<String> blood_city;
    ArrayList<String> blood_hospital;
    ArrayList<String> blood_relation;
    Button btn;
    private ArrayAdapter<String> adapterfbgroups;
    private ArrayAdapter<String> adapterfburgency;
    private ArrayAdapter<String> adapterfbcountry;
    private ArrayAdapter<String> adapterfstate;
    private ArrayAdapter<String> adapterfbcity;
    private ArrayAdapter<String> adapterfbhospital;
    private ArrayAdapter<String> adapterfrelation;
    Post post_obj;
    DatabaseReference databse;
    String current;
    private FirebaseAuth mAuth;
    ArrayList<String> list;
    User data;
    String for_commit;
    public HashMap<String, Object> hashObj = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        getSupportActionBar().setTitle("Request Blood");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initalizeViews();
        initalizeListAndSetToSpinner();
        for_commit = databse.push().getKey();
        postBloodRequestAndSendDataForNOtifications();


    }

    private void postBloodRequestAndSendDataForNOtifications() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ConnectivityStatus.isConnected(PostActivity.this)) {
                    Login.showDialog(PostActivity.this, getResources().getString(R.string.no_internet));

                } else {
                    if (for_quantity.getText().toString().length() == 0) {
                        for_quantity.setError("Enter the Quantity");
                        return;
                    } else if (instructions.getText().toString().length() == 0) {
                        instructions.setError("Enter INstructions");
                        return;
                    } else if (for_contact_number.getText().toString().length() == 0) {
                        for_contact_number.setError("Enter INstructions");
                        return;
                    }
                    String name_of_poster = Welcome_for_User.name;
                    String postbyimage = Welcome_for_User.photoUrl;
                    String get_b_gr = for_b_group.getSelectedItem().toString();
                    String get_b_urg = for_urgency.getSelectedItem().toString();
                    String get_b_cou = for_country.getSelectedItem().toString();
                    String get_b_stat = for_state.getSelectedItem().toString();
                    String get_b_ci = for_city.getSelectedItem().toString();
                    String get_b_hos = for_hospital.getSelectedItem().toString();
                    String get_b_relate = for_relation.getSelectedItem().toString();
                    String get_quan = for_quantity.getText().toString();
                    String get_inst_for = instructions.getText().toString();
                    String get_c_inf = String.valueOf(for_contact_number.getText().toString());

                    hashObj.put("post_by_id", current);
                    hashObj.put("p_naem", name_of_poster);
                    hashObj.put("p_group", get_b_gr);
                    hashObj.put("p_units", get_quan);
                    hashObj.put("p_urgency", get_b_urg);
                    hashObj.put("p_country", get_b_cou);
                    hashObj.put("p_state", get_b_stat);
                    hashObj.put("p_city", get_b_ci);
                    hashObj.put("p_hos", get_b_hos);
                    hashObj.put("p_rel", get_b_relate);
                    hashObj.put("cont", get_c_inf);
                    hashObj.put("add_i", get_inst_for);
                    hashObj.put("push_c", for_commit);
                    hashObj.put("post_by_image", postbyimage);
                    hashObj.put("remainings", get_quan);
                    hashObj.put("mTimestamp", ServerValue.TIMESTAMP);

                    databse
                            .child("Post_By-User")
                            .child(for_commit)
                            .setValue(hashObj);
                    databse
                            .child("users_post")
                            .child(current)
                            .child("My_post")
                            .child(for_commit)
                            .setValue(hashObj);
                    FirebaseDatabase.getInstance().getReference().child("User_info").addChildEventListener(new ChildEventListener() {

                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            // This method is called once with the initial value and again
                            // whenever Data at this location is updated.
                            data = dataSnapshot.getValue(User.class);

                            // Log.v("DATA", "" + data.getId() + data.getName() + data.getCity());
                            User email = new User(data.getUID());
                            String UID_for_post_notification = data.getUID();
                            Log.d("babr_id", data.getUID());
                            if (UID_for_post_notification.equals(current)) {

                            } else {
                                databse
                                        .child("Notification_for_posts")
                                        .child(UID_for_post_notification)
                                        .child(for_commit)
                                        .setValue(hashObj);
                            }


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

                    finish();

                }
            }
        });
    }

    private void initalizeListAndSetToSpinner() {
        blood_groups = new ArrayList<String>();
        adapterfbgroups = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, blood_groups);
        blood_urgency = new ArrayList<String>();
        adapterfburgency = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, blood_urgency);
        blood_country = new ArrayList<String>();
        adapterfbcountry = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, blood_country);
        blood_state = new ArrayList<String>();
        adapterfstate = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, blood_state);
        blood_city = new ArrayList<String>();
        adapterfbcity = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, blood_city);
        blood_hospital = new ArrayList<String>();
        adapterfbhospital = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, blood_hospital);
        blood_relation = new ArrayList<String>();
        adapterfrelation = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, blood_relation);


        blood_groups.add("A Positive");
        blood_groups.add("B Positive");
        blood_groups.add("O Positive");
        blood_groups.add("A Negative");
        blood_groups.add("B Negative");
        blood_groups.add("O Negative");


        blood_urgency.add("Urgent");
        blood_urgency.add("Within 5 hours");
        blood_urgency.add("Within 12 hours");
        blood_urgency.add("Within 24 hours");
        blood_urgency.add("Within 2 days");
        blood_urgency.add("Within Week");

        blood_country.add("PAkistan");

        blood_state.add("ISlamic pakistan");

        blood_city.add("karchi");
        blood_city.add("lahore");
        blood_city.add("multan");
        blood_city.add("sukkhar");
        blood_city.add("nawabshah");

        blood_hospital.add("Indus Hospital");
        blood_hospital.add("Ziauddin Hospital");
        blood_hospital.add("Agha Khan Hospital");
        blood_hospital.add("Liaquat National Hospital");
        blood_hospital.add("OMI");
        blood_hospital.add("Jinnah Hospital");
        blood_hospital.add("Holy Family Hospital");

        blood_relation.add("Father");
        blood_relation.add("Mother");
        blood_relation.add("Son");
        blood_relation.add("Daughter");
        blood_relation.add("Aunt");
        blood_relation.add("Uncle");
        blood_relation.add("Nephew");
        blood_relation.add("Niece");
        blood_relation.add("Friend");
        blood_relation.add("Neighbour");
        blood_relation.add("None");

        for_b_group.setAdapter(adapterfbgroups);
        for_urgency.setAdapter(adapterfburgency);
        for_country.setAdapter(adapterfbcountry);
        for_state.setAdapter(adapterfstate);
        for_city.setAdapter(adapterfbcity);
        for_hospital.setAdapter(adapterfbhospital);
        for_relation.setAdapter(adapterfrelation);
    }

    private void initalizeViews() {
        databse = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        current = mAuth.getCurrentUser().getUid();
        for_b_group = (Spinner) findViewById(R.id.b_g);
        for_urgency = (Spinner) findViewById(R.id.urgency);
        for_country = (Spinner) findViewById(R.id.country);
        for_state = (Spinner) findViewById(R.id.state);
        for_city = (Spinner) findViewById(R.id.city);
        for_hospital = (Spinner) findViewById(R.id.hospital);
        for_relation = (Spinner) findViewById(R.id.relation);
        for_quantity = (EditText) findViewById(R.id.b_quantity);
        for_contact_number = (EditText) findViewById(R.id.contact_num);
        instructions = (EditText) findViewById(R.id.instruction);
        btn = (Button) findViewById(R.id.post_requires);
    }
}
