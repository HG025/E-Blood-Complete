package com.example.mt_2016.marathon_blood.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mt_2016.marathon_blood.Activities.CommitActivity;
import com.example.mt_2016.marathon_blood.Activities.PostActivity;
import com.example.mt_2016.marathon_blood.Adapters.PostAdapter;
import com.example.mt_2016.marathon_blood.Models.Post;
import com.example.mt_2016.marathon_blood.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class Two extends Fragment {
    private ListView emailList;
    private ArrayList<Post> messages;
    private PostAdapter listAdapter;
    Post data;
    ArrayList<String> list;
    String postid;
    String poster_name;
    String blood_grou;
    String location;
    String hospital;
    String urgency;
    String relate;
    String contac;
    String hidayat;
    String un;
    String country;
    String state;
    String city;
    String current;
    String Postby;
    String remaings;
    String post_imsge;
    View view;

    private FirebaseAuth mAuth;

    public Two() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_two, container, false);
        initializeViews();
        setDatatoAdapter();
        toViewDetails();
        FirebaseDatabase.getInstance().getReference()
                .child("users_post")
                .child(current)
                .child("My_post")
                .addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        // This method is called once with the initial value and again
                        // whenever Data at this location is updated.

                        data = dataSnapshot.getValue(Post.class);
                        list.add(data.getPush_c());
                        // Log.v("DATA", "" + data.getId() + data.getName() + data.getCity());
                        Post email = new Post(data.getPost_by_id(), data.getRemainings(), data.getPost_by_image(), data.getPush_c(), data.getP_naem(), data.getP_group(), data.getP_units(),
                                data.getP_urgency(), data.getP_country(), data.getP_state(), data.getP_city(), data.getP_hos(),
                                data.getP_rel(), data.getCont(), data.getAdd_i(), data.getTimeInMillis());
                        messages.add(0, email);
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

        return view;
    }

    private void toViewDetails() {
        emailList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent conwindow = new Intent(getActivity(), CommitActivity.class);
                postid = list.get(position);
                poster_name = messages.get(position).getP_naem();
                blood_grou = messages.get(position).getP_group();
                relate = messages.get(position).getP_rel();
                country = messages.get(position).getP_country();
                hospital = messages.get(position).getP_hos();
                city = messages.get(position).getP_city();
                state = messages.get(position).getP_state();
                urgency = messages.get(position).getP_urgency();
                contac = String.valueOf(messages.get(position).getCont());
                hidayat = messages.get(position).getAdd_i();
                un = messages.get(position).getP_units();
                Postby = messages.get(position).getPost_by_id();
                remaings = messages.get(position).getRemainings();
                post_imsge = messages.get(position).getPost_by_image();
                PrettyTime prettyTime = new PrettyTime(Locale.getDefault());
                String ago = prettyTime.format(new Date(messages.get(position).getTimeInMillis()));

                conwindow.putExtra("postid", postid);
                conwindow.putExtra("posternbame", poster_name);
                conwindow.putExtra("bloudgro", blood_grou);
                conwindow.putExtra("Realation", relate);
                conwindow.putExtra("country", country);
                conwindow.putExtra("city", city);
                conwindow.putExtra("state", state);
                conwindow.putExtra("hospital", hospital);
                conwindow.putExtra("urgency", urgency);
                conwindow.putExtra("contact_nm", contac);
                conwindow.putExtra("hiday", hidayat);
                conwindow.putExtra("units", un);
                conwindow.putExtra("post_by", Postby);
                conwindow.putExtra("remaings", remaings);
                conwindow.putExtra("post_image", post_imsge);
                conwindow.putExtra("timestamp", ago);


                startActivity(conwindow);

            }
        });
    }

    private void setDatatoAdapter() {
        listAdapter = new PostAdapter(messages, getActivity());
        emailList.setAdapter(listAdapter);
    }

    private void initializeViews() {
        list = new ArrayList<>();
        emailList = (ListView) view.findViewById(R.id.listviffew);
        messages = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        current = mAuth.getCurrentUser().getUid();
    }

}
