package com.example.mt_2016.marathon_blood.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mt_2016.marathon_blood.Activities.CommitActivity;
import com.example.mt_2016.marathon_blood.Models.comit_for;
import com.example.mt_2016.marathon_blood.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by BabarMustafa on 4/21/2017.
 */

//public class VoluntersAdapter {
//}
public class VoluntersAdapter extends BaseAdapter {
    private List<comit_for> dataList;
    private Context context;
    int remain;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public VoluntersAdapter(List<comit_for> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.single_volunter, null);

        TextView foename = (TextView) view.findViewById(R.id.tree_name);
        final CheckBox to_confirm = (CheckBox) view.findViewById(R.id.check_confirm);
        Button isdonated = (Button) view.findViewById(R.id.btn_donated);
        Button notdonated = (Button) view.findViewById(R.id.btn_not_donated);
        TextView fot_time = (TextView) view.findViewById(R.id.time_of_volunter_request);


        final comit_for data = dataList.get(position);

        final String name_volunter = data.getComit_nmae();
        String donated_text = data.getCommit_text();
        String confirmation = data.getConfirm_donation();


        //to still the condition after changes
        final comit_for todoChekd = (comit_for) getItem(position);
        PrettyTime prettyTime = new PrettyTime(Locale.getDefault());
        String ago = prettyTime.format(new Date(dataList.get(position).getTimeInMillis()));
        fot_time.setText(ago);

        foename.setText(name_volunter);
        if (donated_text.equals("Donated")) {
            isdonated.setVisibility(View.VISIBLE);
        }
        if (confirmation.equals("Confirmed")) {
            to_confirm.setChecked(true);
            to_confirm.setClickable(false);
            to_confirm.setText("Bllod Recieved Confirmed By Needer");
        }
        else if(confirmation.equals("not yet")){
            to_confirm.setChecked(false);
            to_confirm.setClickable(false);
            to_confirm.setText("Not Confirmed Yet");

        }
        else if (confirmation.equals("Canceled")){
            to_confirm.setChecked(true);
            to_confirm.setClickable(false);
            to_confirm.setText("Blood Not Recieved Confirmed By Needer");
        }

        isdonated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                String login_user = mAuth.getCurrentUser().getUid();
                String need_blood = dataList.get(position).getCommit_uuid();
                if (login_user.equals(need_blood)) {
                    String confirmation_text = dataList.get(position).getConfirm_donation();
                    if (confirmation_text.equals("not yet")) {
                        database
                                .child("Volunters_for_posts")
                                .child(dataList.get(position).getPost_push())
                                .child(dataList.get(position).getPush_of())
                                .child("confirm_donation")
                                .setValue("Confirmed");
                        to_confirm.setChecked(true);
                        ((CommitActivity)context).finish();


                    } else {
                        Toast.makeText(context, "You already Confirmed this recieving", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(context, "You are not allowed to confirm the blood recieving", Toast.LENGTH_SHORT).show();
                }
            }
        });
        notdonated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                String login_user = mAuth.getCurrentUser().getUid();
                String need_blood = dataList.get(position).getCommit_uuid();
                if (login_user.equals(need_blood)){
                    String confirmation_text = dataList.get(position).getConfirm_donation();
                    if (confirmation_text.equals("not yet")) {
                        //do the processs
                        database
                                .child("Post_By-User")
                                .child(dataList.get(position).getPost_push())
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Map<String, String> map = (Map) dataSnapshot.getValue();

                                        int remain = Integer.parseInt(map.get("remainings"));
                                        int calc = remain +1;
                                        database
                                                .child("Post_By-User")
                                                .child(dataList.get(position).getPost_push())
                                                .child("remainings")
                                                .setValue(String.valueOf(calc));
                                        String need_blood = dataList.get(position).getCommit_uuid();
                                        database
                                                .child("users_post")
                                                .child(need_blood)
                                                .child("My_post")
                                                .child(dataList.get(position).getPost_push())
                                                .child("remainings")
                                                .setValue(String.valueOf(calc));
                                        database
                                                .child("Volunters_for_posts")
                                                .child(dataList.get(position).getPost_push())
                                                .child(dataList.get(position).getPush_of())
                                                .child("confirm_donation")
                                                .setValue("Canceled");
                                        ((CommitActivity)context).finish();


                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                    }
                    else {
                        Toast.makeText(context, "You already Confirmed this recieving as cancelled", Toast.LENGTH_SHORT).show();
                    }



                }
                else {
                    Toast.makeText(context, "You are not allowed to confirm the blood recieving", Toast.LENGTH_SHORT).show();
                }




            }
        });

        return view;
    }
}