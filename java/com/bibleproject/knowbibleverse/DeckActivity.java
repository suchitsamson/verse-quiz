package com.bibleproject.knowbibleverse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeckActivity extends AppCompatActivity {

    ProgressBar pBarDeck1;
    TextView textdeck1;

    ProgressBar pBarDeck2;
    TextView textdeck2;
    ProgressBar pBarDeck3;
    TextView textdeck3;
    ProgressBar pBarDeck4;
    TextView textdeck4;
    ProgressBar pBarDeck5;
    TextView textdeck5;

    static int selectedDeck;

    DatabaseReference db;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.empty);
        setContentView(R.layout.activity_deck);

        pBarDeck1 = findViewById(R.id.progressBarDeck1);
        textdeck1 = findViewById(R.id.textDeck1);
        pBarDeck2 = findViewById(R.id.progressBarDeck2);
        textdeck2 = findViewById(R.id.textDeck2);
        pBarDeck3 = findViewById(R.id.progressBarDeck3);
        textdeck3 = findViewById(R.id.textDeck3);
        pBarDeck4 = findViewById(R.id.progressBarDeck4);
        textdeck4 = findViewById(R.id.textDeck4);
        pBarDeck5 = findViewById(R.id.progressBarDeck5);
        textdeck5 = findViewById(R.id.textDeck5);

        mAuth = FirebaseAuth.getInstance();
        selectedDeck = 0;
        setAllProgressBars();
    }

    public void setAllProgressBars(){

        pBarDeck1.setMax(10);
        pBarDeck2.setMax(10);
        pBarDeck3.setMax(10);
        pBarDeck4.setMax(10);
        pBarDeck5.setMax(10);
        pBarDeck1.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#f5da88")));
        pBarDeck2.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#f5da88")));
        pBarDeck3.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#f5da88")));
        pBarDeck4.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#f5da88")));
        pBarDeck5.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#f5da88")));

        final String userEmail = mAuth.getCurrentUser().getEmail();

        try {
            FirebaseDatabase.getInstance().getReference().child("player").orderByChild("email").equalTo(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        Player p = snapshot.getValue(Player.class);

                        if (!TextUtils.isEmpty(p.getDeck1())) {
                            pBarDeck1.setProgress(Integer.parseInt(p.getDeck1()));
                            textdeck1.setText(p.getDeck1());
                        } else {
                            pBarDeck1.setProgress(0);
                            textdeck1.setText("0");
                        }
                        if (!TextUtils.isEmpty(p.getDeck2())) {
                            pBarDeck2.setProgress(Integer.parseInt(p.getDeck2()));
                            textdeck2.setText(p.getDeck2());
                        } else {
                            pBarDeck2.setProgress(0);
                            textdeck2.setText("0");
                        }
                        if (!TextUtils.isEmpty(p.getDeck3())) {
                            pBarDeck3.setProgress(Integer.parseInt(p.getDeck3()));
                            textdeck3.setText(p.getDeck3());
                        } else {
                            pBarDeck3.setProgress(0);
                            textdeck3.setText("0");
                        }
                        if (!TextUtils.isEmpty(p.getDeck4())) {
                            pBarDeck4.setProgress(Integer.parseInt(p.getDeck4()));
                            textdeck4.setText(p.getDeck4());
                        } else {
                            pBarDeck4.setProgress(0);
                            textdeck4.setText("0");
                        }
                        if (!TextUtils.isEmpty(p.getDeck5())) {
                            pBarDeck5.setProgress(Integer.parseInt(p.getDeck5()));
                            textdeck5.setText(p.getDeck5());
                        } else {
                            pBarDeck5.setProgress(0);
                            textdeck5.setText("0");
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
        catch(Exception e){
            Toast.makeText(DeckActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void displayDeck1(View vew){
        selectedDeck = 1;
        startActivity(new Intent(this, DeckDisplayActivity.class));
    }

    public void displayDeck2(View vew){
        selectedDeck = 2;
        startActivity(new Intent(this, DeckDisplayActivity.class));
    }

    public void displayDeck3(View vew){
        selectedDeck = 3;
        startActivity(new Intent(this, DeckDisplayActivity.class));
        finish();
    }

    public void displayDeck4(View vew){
        selectedDeck = 4;
        startActivity(new Intent(this, DeckDisplayActivity.class));
    }

    public void displayDeck5(View vew){
        selectedDeck = 5;
        startActivity(new Intent(this, DeckDisplayActivity.class));
    }

    public void gotoHome(View vew){
        startActivity(new Intent(this, HomeActivity.class));
    }
}