package com.example.mt_2016.marathon_blood.Adapters;

/**
 * Created by BabarMustafa on 5/18/2017.
 */


import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.mt_2016.marathon_blood.Models.NotificationMessage;
import com.example.mt_2016.marathon_blood.Models.Post;
import com.example.mt_2016.marathon_blood.R;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.mikhaellopez.circularimageview.CircularImageView;
        import com.squareup.picasso.Picasso;

        import java.util.ArrayList;


public class Notification_Adapter extends BaseAdapter {


    private ArrayList<Post> dataList;
    private Context context;

    public Notification_Adapter(ArrayList<Post> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Post getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        // final View inflater = LayoutInflater.from(MainView.this).inflate(R.layout.for_messages,null);
        View view = inflater.inflate(R.layout.single_notification, null);


        TextView forname = (TextView) view.findViewById(R.id.notifier_nme);
//        CircularImageView pcircularImageView = (CircularImageView) view.findViewById(R.id.profile_view);

        final Post data = dataList.get(position);

//        String username = data.getName();
//        String iml = data.getProfile_image();
//        Toast.makeText(context, ""+iml, Toast.LENGTH_SHORT).show();



//mAuth.getCurrentUser().toString();

        //to still the condition after changes
        final Post todoChekd = (Post) getItem(position);
        forname.setText(dataList.get(position).getP_naem() +" Requires " +dataList.get(position).getP_units() + " units of blood for "
        +dataList.get(position).getP_rel() +" at\n" +dataList.get(position).getP_hos() +" "+ dataList.get(position).getP_urgency());
//        System.out.print(""+data.getProfile_image());
//        Picasso.with(context).load(data.getProfile_image()).into(pcircularImageView);


        return view;
    }
}
