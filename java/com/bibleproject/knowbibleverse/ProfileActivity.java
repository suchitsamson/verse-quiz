package com.bibleproject.knowbibleverse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    EditText username;
    EditText email;
    EditText password;
    Button update;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.empty);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();

        username = findViewById(R.id.textUsername);
        email = findViewById(R.id.textEmail);
        password = findViewById(R.id.textPassword);
        update = findViewById(R.id.btnUpdate);

        username.setText(mAuth.getCurrentUser().getDisplayName());
        email.setText(mAuth.getCurrentUser().getEmail());

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String localUserName = username.getText().toString().length()>0?username.getText().toString():"";
                String localPassword = password.getText().toString().length()>0?password.getText().toString():"";

                //UPDATE DISPLAY NAME
                if(localUserName.length()>0 && !localUserName.equalsIgnoreCase(mAuth.getCurrentUser().getDisplayName())){

                    String prevUserName = mAuth.getCurrentUser().getDisplayName();

                    try {
                        //update username
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(localUserName.toUpperCase())
                                .build();

                        mAuth.getCurrentUser().updateProfile(profileUpdates)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(ProfileActivity.this, "Username Updated.", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(ProfileActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                        final DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("LEVEL_ONE");
                        db1.orderByChild("username").equalTo(prevUserName).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                updateUsernameInDB(db1, dataSnapshot, localUserName);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        final DatabaseReference db2 = FirebaseDatabase.getInstance().getReference().child("LEVEL_TWO");
                        db2.orderByChild("username").equalTo(prevUserName).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                updateUsernameInDB(db2, dataSnapshot, localUserName);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        final DatabaseReference db3 = FirebaseDatabase.getInstance().getReference().child("LEVEL_THREE");
                        db3.orderByChild("username").equalTo(prevUserName).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                updateUsernameInDB(db3, dataSnapshot, localUserName);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    catch(Exception e){
                        Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                //UPDATE PASSWORD
                if(localPassword.length()>0){
                    mAuth.getCurrentUser().updatePassword(localPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                //Log.d("updatePassword", "Password Updated.");
                                Toast.makeText(ProfileActivity.this, "Password Updated.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ProfileActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                            password.setText("");
                        }
                    });
                }
            }
        });

    }

    public void updateUsernameInDB(DatabaseReference dbRef, DataSnapshot dataSnapshot, String localUserName){
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            Level entry = snapshot.getValue(Level.class);
            entry.setUsername(localUserName);
            dbRef.child(entry.getId()).setValue(entry);
        }
    }

    public void goToHome(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
