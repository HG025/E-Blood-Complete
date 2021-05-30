package com.example.mt_2016.marathon_blood.Fragments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mt_2016.marathon_blood.Activities.Welcome_for_User;
import com.example.mt_2016.marathon_blood.NetWorkClasses.ConnectivityStatus;
import com.example.mt_2016.marathon_blood.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment {
    ProgressDialog progres;
    Button for_sigin_in;
    TextView jump_to_signup;
    EditText s_email;
    EditText s_passs;
    private FirebaseAuth auth;
    String usernameforlogin;
    String passwordforlogin;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    DatabaseReference databse;
    View view;
    TextView forgotPassword;

    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);

        initializeViews();
        proceedtoSignUpScreen();
        toDoSignInProcess();
        PaaswordForgot();
        return view;


    }

    private void PaaswordForgot() {
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(getActivity());
                View promptsView = li.inflate(R.layout.emaildialog, null);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setTitle("Enter Your Email");
                alertDialogBuilder.setMessage("Enter your Email to get Verification Email");
                alertDialogBuilder.setView(promptsView);
                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.emailReset);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {


                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                final AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Boolean wantToCloseDialog = false;
                        //Do stuff, possibly set wantToCloseDialog to true then...
                        if (!userInput.getText().toString().equals("")) {
                            if (android.util.Patterns.EMAIL_ADDRESS.matcher(userInput.getText().toString()).matches()) {
//                                Toast.makeText(getActivity(), "Email sentnnnnnnnnnn", Toast.LENGTH_SHORT).show();
//                                wantToCloseDialog =true;
                                auth.sendPasswordResetEmail(userInput.getText().toString())
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getActivity(), "Email sent", Toast.LENGTH_SHORT).show();
                                                }
                                                else {
                                                    Toast.makeText(getActivity(), "Email Not sent", Toast.LENGTH_SHORT).show();

                                                }
                                            }
                                        });
                                alertDialog.dismiss();
//                String emailAddress = "babarmustafaawan@gmail.com";
//


                            } else {
                                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle("Warning");
                                builder.setMessage("You Enter Invalid Email");
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                                builder.show();
                            }
                            wantToCloseDialog = false;
                        } else if (userInput.getText().toString().equals("")) {
                            userInput.setError("Can't be null");
                        }
                        if (wantToCloseDialog)
                            alertDialog.dismiss();
                    }
                });


            }
        });
    }

    private void toDoSignInProcess() {
        for_sigin_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ConnectivityStatus.isConnected(getActivity())) {
                    showDialog(getActivity(), getResources().getString(R.string.no_internet));

                } else {
                    if (s_email.getText().toString().length() != 0 && s_passs.getText().toString().length() != 0) {
                        progres.setMessage("Signing in....");
                        progres.show();
                        usernameforlogin = s_email.getText().toString();
                        passwordforlogin = s_passs.getText().toString();
                        auth.signInWithEmailAndPassword(usernameforlogin, passwordforlogin)
                                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if (!task.isSuccessful()) {
                                            Toast.makeText(getActivity(), "UserName or Password is in correct", Toast.LENGTH_SHORT).show();
                                            progres.dismiss();

                                        } else {
                                            progres.dismiss();
                                            Intent call = new Intent(getActivity(), Welcome_for_User.class);
                                            startActivity(call);
                                        }

                                    }
                                });


                    } else {

                        s_email.setError("Enter The User Name ");
                        s_passs.setError("Enter The password ");
                    }
                }
            }


        });
    }

    private void proceedtoSignUpScreen() {
        jump_to_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new Signup()).addToBackStack(null).commit();
            }
        });
    }

    public static void showDialog(Context context, String message) {
        final AlertDialog.Builder warningDialog = new AlertDialog.Builder(context);
        warningDialog.setTitle("Warning");
        warningDialog.setMessage(message);
        warningDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        warningDialog.create().show();
    }

    private void initializeViews() {
        databse = FirebaseDatabase.getInstance().getReference();
        s_email = (EditText) view.findViewById(R.id.e_email);
        s_passs = (EditText) view.findViewById(R.id.e_pass);
        for_sigin_in = (Button) view.findViewById(R.id.login_buttobn);
        jump_to_signup = (TextView) view.findViewById(R.id.singup_calling_button);
        forgotPassword = (TextView) view.findViewById(R.id.forgotPassword);
        mAuth = FirebaseAuth.getInstance();
        auth = FirebaseAuth.getInstance();
        progres = new ProgressDialog(getActivity());
    }
}
