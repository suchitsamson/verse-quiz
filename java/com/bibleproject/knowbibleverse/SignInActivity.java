package com.bibleproject.knowbibleverse;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;

public class SignInActivity extends AppCompatActivity {

    TextView lUsername;
    EditText EditTextEmail;
    EditText EditTextPassword;
    Button login;
    Button signup;
    Button forgotPass;
    RadioButton lUser;
    RadioButton oUser;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.empty);
        setContentView(R.layout.activity_first);

        mAuth = FirebaseAuth.getInstance();

        EditTextEmail = findViewById(R.id.textEmail);
        EditTextPassword = findViewById(R.id.textPassword);
        login = findViewById(R.id.buttonLogin);
        lUser = findViewById(R.id.rBtnLoginUser);
        oUser = findViewById(R.id.rBtnOtherUser);
        lUsername = findViewById(R.id.txtloginUser);
        signup = findViewById(R.id.buttonSignUp);
        forgotPass = findViewById(R.id.buttonforgotPassword);

        mAuth.getCurrentUser().reload();
        lUsername.setText(mAuth.getCurrentUser().getDisplayName());
        if(mAuth.getCurrentUser().getEmail()!=null && !mAuth.getCurrentUser().getEmail().isEmpty())
            lUser.setChecked(true);
        else
            oUser.setChecked(true);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, SignupActivity.class));
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, ForgotPaaswordActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lUser.isChecked()){
                    mAuth.getCurrentUser().reload();
                    if(mAuth.getCurrentUser().isEmailVerified()){
                        startActivity(new Intent(SignInActivity.this, HomeActivity.class));
                        finish();
                    } else {
                        Toast.makeText(SignInActivity.this, "Please verify your email!", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    String email = EditTextEmail.getText().toString();
                    String pwd = EditTextPassword.getText().toString();

                    if (email.isEmpty()) {
                        EditTextEmail.setError("Empty");
                        EditTextEmail.requestFocus();
                    }
                    if (pwd.isEmpty()) {
                        EditTextPassword.setError("Empty");
                        EditTextPassword.requestFocus();
                    }
                    if (!email.isEmpty() && !pwd.isEmpty()) {
                        mAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    if(mAuth.getCurrentUser().isEmailVerified()){
                                        Toast.makeText(SignInActivity.this, "Logged In.", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignInActivity.this, HomeActivity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(SignInActivity.this, "Please verify your email!", Toast.LENGTH_LONG).show();
                                    }
                                }
                                else
                                    Toast.makeText(SignInActivity.this, task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
