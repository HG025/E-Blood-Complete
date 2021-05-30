package com.example.mt_2016.marathon_blood.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mt_2016.marathon_blood.Activities.GroupCreate;
import com.example.mt_2016.marathon_blood.Activities.Group_coversation;
import com.example.mt_2016.marathon_blood.Adapters.Groups_show_Adapter;
import com.example.mt_2016.marathon_blood.Models.groups_create_info;
import com.example.mt_2016.marathon_blood.NetWorkClasses.ConnectivityStatus;
import com.example.mt_2016.marathon_blood.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.example.mt_2016.marathon_blood.Fragments.Login.showDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class Four extends Fragment {
    private ListView emailList;
    private ArrayList<groups_create_info> messages;
    private Groups_show_Adapter listAdapter;
    ArrayList<String> list;
    groups_create_info data;
    String group_name;
    String admin_name;
    String group_image_url;
    private FloatingActionButton fabButton;
    FirebaseAuth mauth;
    View view;
    String c_login;

    public Four() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_four, container, false);
        initializeViews();
        setDatatoAdapter();
        GoToGroupConversation();
        toCreateNewGroup();



        //to show user only his groups
        FirebaseDatabase
                .getInstance()
                .getReference()
                .child("My_Groups")
                .child(c_login)
                .addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        // This method is called once with the initial value and again
                        // whenever Data at this location is updated.
                        data = dataSnapshot.getValue(groups_create_info.class);
                        list.add(data.getGroup_name());
                        // Log.v("DATA", "" + data.getId() + data.getName() + data.getCity());
                        groups_create_info email = new groups_create_info(data.getAdmin_uuid(), data.getGroup_name(), data.getG_i_url(), data.getTimeInMillis());
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
        return view;
    }

    private void toCreateNewGroup() {
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ConnectivityStatus.isConnected(getActivity())){
                    Login.showDialog(getActivity(),getResources().getString(R.string.no_internet));

                }else {
                    Intent c_group = new Intent(getActivity(), GroupCreate.class);
                    startActivity(c_group);
                }

            }
        });
    }

    private void GoToGroupConversation() {
        emailList.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent conwindow = new Intent(getActivity(), Group_coversation.class);
//


                group_name = list.get(position);
                admin_name = messages.get(position).getAdmin_uuid();
                group_image_url = messages.get(position).getG_i_url();


                conwindow.putExtra("group_name", group_name);
                conwindow.putExtra("group_admin_uuid", admin_name);
                conwindow.putExtra("group_ima_url", group_image_url);


                startActivity(conwindow);
            }
        });

    }

    private void setDatatoAdapter() {
        listAdapter = new Groups_show_Adapter(messages, getActivity());
        emailList.setAdapter(listAdapter);
    }

    private void initializeViews() {
        list = new ArrayList<>();
        mauth = FirebaseAuth.getInstance();
        fabButton = (FloatingActionButton) view.findViewById(R.id.fab);
        emailList = (ListView) view.findViewById(R.id.g_list_view);
        messages = new ArrayList<>();
        c_login = mauth.getCurrentUser().getUid();
    }

}
