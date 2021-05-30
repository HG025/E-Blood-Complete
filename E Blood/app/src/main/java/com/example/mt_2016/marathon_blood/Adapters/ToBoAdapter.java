package com.example.mt_2016.marathon_blood.Adapters;

/**
 * Created by MT-2016 on 2/26/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.mt_2016.marathon_blood.Models.comit_for;
import com.example.mt_2016.marathon_blood.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by babar on 1/29/2017.
 */

//public class ToBoAdapter {
//}

public class ToBoAdapter extends BaseAdapter {
    private List<comit_for> dataList;
    private Context context;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public ToBoAdapter(List<comit_for> dataList, Context context) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.single_commit,null);

        TextView foename = (TextView) view.findViewById(R.id.tre_name);
        TextView foenamep = (TextView) view.findViewById(R.id.tre_com);
        TextView for_timestamp = (TextView) view.findViewById(R.id.for_time);





        final  comit_for data = dataList.get(position);

        String nam = data.getComit_nmae();
        String namp = data.getCommit_text();



        //to still the condition after changes
        final comit_for todoChekd = (comit_for) getItem(position);


        PrettyTime prettyTime = new PrettyTime(Locale.getDefault());
        String ago = prettyTime.format(new Date(dataList.get(position).getTimeInMillis()));

        foename.setText(nam);
        for_timestamp.setText(ago);
        foenamep.setText(namp);


        return view;
    }
}