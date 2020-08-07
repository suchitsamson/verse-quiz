package com.bibleproject.knowbibleverse;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    RadioButton radioL1;
    RadioButton radioL2;
    RadioButton radioL3;
    Button logout;

    public static int LEVEL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.empty);
        setContentView(R.layout.activity_home);

        radioL1 = findViewById(R.id.radioLevel1);
        radioL2 = findViewById(R.id.radioLevel2);
        radioL3 = findViewById(R.id.radioLevel3);

        logout = findViewById(R.id.buttonLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        LEVEL = 3;
    }

    public void logout(){
        FirebaseAuth.getInstance().signOut();
        finish();
        Intent intent = new Intent(this, SignupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void checkLevel(View view){
        if(radioL1.isChecked()) LEVEL = 1;
        else if(radioL2.isChecked()) LEVEL = 2;
        else LEVEL = 3;
    }

    public void sendEmail(View view){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"developer.suchit@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "AndroidApp - VERSEQUIZ");
        //i.putExtra(Intent.EXTRA_TEXT   , "body of email");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(HomeActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void goToGame(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void goToProfile(View view){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void goToLeaderboard(View view){
        Intent intent = new Intent(this, LeaderBoardActivity.class);
        startActivity(intent);
    }

    public void goToDecks(View view){
        Intent intent = new Intent(this, DeckActivity.class);
        startActivity(intent);
    }
}