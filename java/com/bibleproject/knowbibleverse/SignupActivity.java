package com.bibleproject.knowbibleverse;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    EditText textUsername;
    EditText textEmail;
    EditText textPassword;
    Button btnSignup;
    Button btnHome;
    Button btnLogin;
    Button forgotPass;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTitle(R.string.empty);
        setContentView(R.layout.activity_signup);

        textUsername = findViewById(R.id.etUsername);
        textEmail = findViewById(R.id.etEmail);
        textPassword = findViewById(R.id.etPassword);
        btnSignup = findViewById(R.id.btnSignup);
        btnHome = findViewById(R.id.btnHome);
        btnLogin = findViewById(R.id.btnLogin);
        forgotPass = findViewById(R.id.buttonforgotPassword);

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() == null) {
            btnHome.setEnabled(false);
            btnHome.setBackgroundColor(Color.parseColor("#bccadf"));
        }
        else {
            btnHome.setEnabled(true);
            btnHome.setBackgroundColor(Color.parseColor("#800000"));
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = textEmail.getText().toString();
                String pwd = textPassword.getText().toString();

                if(checkEmptyFields(new String(), email, pwd, true)){
                    mAuth.signInWithEmailAndPassword(email, pwd)
                            .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        if(mAuth.getCurrentUser().isEmailVerified()){
                                            Toast.makeText(SignupActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(SignupActivity.this, HomeActivity.class));
                                        } else {
                                            Toast.makeText(SignupActivity.this, "Please verify your email!", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(SignupActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this, SignInActivity.class));
                finish();
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this, ForgotPaaswordActivity.class));
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {

                final String username = textUsername.getText().toString();
                String email = textEmail.getText().toString();
                String pwd = textPassword.getText().toString();

                if(checkEmptyFields(username, email, pwd, false)){

                    mAuth.createUserWithEmailAndPassword(email, pwd)
                            .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(SignupActivity.this, "Registered successfully. Please check your email for verification!",
                                                            Toast.LENGTH_LONG).show();

                                                    try {
                                                        //Add username
                                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                                .setDisplayName(username.toUpperCase())
                                                                .build();


                                                        mAuth.getCurrentUser().updateProfile(profileUpdates)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()) {
                                                                            //Toast.makeText(SignupActivity.this, "Username added.", Toast.LENGTH_SHORT).show();
                                                                        } else {
                                                                            Toast.makeText(SignupActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                                        }
                                                                    }
                                                                });
                                                        new Handler().postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                startActivity(new Intent(SignupActivity.this, SignInActivity.class));
                                                            }
                                                        },1000);
                                                    }
                                                    catch(Exception e){
                                                        Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(SignupActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(SignupActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    public boolean checkEmptyFields(String pUsername, String pEmail, String pPassword, boolean isLogin){
        boolean flag = true;
        if( !isLogin && TextUtils.isEmpty(pUsername)){
            flag = false;
            textUsername.setError("Required");
            textUsername.requestFocus();
        }
        if(TextUtils.isEmpty(pEmail)){
            flag = false;
            textEmail.setError("Required");
            textEmail.requestFocus();
        }
        if(TextUtils.isEmpty(pPassword)){
            flag = false;
            textPassword.setError("Required");
            textPassword.requestFocus();
        }

        return flag;
    }
}
