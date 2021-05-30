package com.example.mt_2016.marathon_blood.Activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mt_2016.marathon_blood.Fragments.Login;
import com.example.mt_2016.marathon_blood.NetWorkClasses.ConnectivityStatus;
import com.example.mt_2016.marathon_blood.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangePassword extends AppCompatActivity {
    Button changePassword;
    EditText oldPassword, newPassword, newConfirmPassword;
    DatabaseReference databse;
    LinearLayout backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initializeViews();
        getSupportActionBar().hide();
        CChangePassword();
        cloaseScreen();


    }

    private void cloaseScreen() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void CChangePassword() {
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ConnectivityStatus.isConnected(ChangePassword.this)){
                    Login.showDialog(ChangePassword.this,getResources().getString(R.string.no_internet));

                }else {
                    if (newPassword.getText().toString() != null && newConfirmPassword.getText().toString() != null) {
                        Toast.makeText(ChangePassword.this, "You cant leave the fields as blank", Toast.LENGTH_SHORT).show();
                        newConfirmPassword.setError("fill this field");
                        newPassword.setError("fill this field");

                    } else {

                        if (oldPassword.getText().toString().equals(Welcome_for_User.pasword)) {


                            if (newPassword.getText().toString().equals(newConfirmPassword.getText().toString())) {
                                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                user.updatePassword(newPassword.getText().toString().trim())
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(ChangePassword.this, "Password is updated!", Toast.LENGTH_SHORT).show();
                                                    databse.child("User_info")
                                                            .child(user.getUid())
                                                            .child("Password")
                                                            .setValue(newPassword.getText().toString().trim());

                                                } else {
                                                    Toast.makeText(ChangePassword.this, "Failed to update password!", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                            } else {
                                Toast.makeText(ChangePassword.this, "Your Confirm Password is not same in both fields", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(ChangePassword.this, "Yor did not enter the right password ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }




            }
        });
    }

    private void initializeViews() {
        databse = FirebaseDatabase.getInstance().getReference();
        backButton = (LinearLayout) findViewById(R.id.backcp);
        changePassword = (Button) findViewById(R.id.changepassword);
        oldPassword = (EditText) findViewById(R.id.oldPassword);
        newPassword = (EditText) findViewById(R.id.newpasswword);
        newConfirmPassword = (EditText) findViewById(R.id.newpasswwordcnfrm);
    }
}
