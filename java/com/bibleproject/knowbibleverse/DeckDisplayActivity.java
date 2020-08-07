package com.bibleproject.knowbibleverse;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeckDisplayActivity extends AppCompatActivity {

    TextView textView;
    Button btnShow;
    Button btnNext;

    int currentCounter;
    int lastCounter;
    int wrong;
    int deckNumber;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.empty);
        setContentView(R.layout.activity_deck_display);

        textView = findViewById(R.id.textView);
        btnShow = findViewById(R.id.btnShow);
        btnNext = findViewById(R.id.btnNext);

        deckNumber = DeckActivity.selectedDeck;
        currentCounter = (deckNumber - 1) * 10;
        lastCounter = (deckNumber * 10 - 1);
        wrong = 0;

        mAuth = FirebaseAuth.getInstance();
        setTextView();
    }

    public void setTextView(){
        textView.setText(MainActivity.verseContent[currentCounter]);
        btnShow.setText("SHOW");
        btnShow.setBackgroundColor(Color.parseColor("#800000"));
    }

    public void show(View view){
        btnShow.setText(MainActivity.books[MainActivity.verseBook[currentCounter]]+" "+MainActivity.verseReference[currentCounter]);
        btnShow.setBackgroundColor(Color.parseColor("#bccadf"));
        wrong++;
    }

    public void next(View view){
        if(currentCounter == lastCounter){
            new AlertDialog.Builder(this, R.style.MyDialogTheme)
                    .setTitle("SCORE")
                    .setMessage("Your latest score for Deck " + deckNumber + " is: " + (10 - wrong))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            setNewScore();
                        }
                    })
                    .setIcon(android.R.drawable.star_on)
                    .show();
        }
        else{
            currentCounter++;
            setTextView();
        }
    }

    public void setNewScore(){
        final String userEmail = mAuth.getCurrentUser().getEmail();
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("player");

        try {
            reference.orderByChild("email").equalTo(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Record exists
                    if (dataSnapshot.getChildrenCount() > 0) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Player p = snapshot.getValue(Player.class);
                            if (deckNumber == 1) p.setDeck1(String.valueOf(10 - wrong));
                            else if (deckNumber == 2) p.setDeck2(String.valueOf(10 - wrong));
                            else if (deckNumber == 3) p.setDeck3(String.valueOf(10 - wrong));
                            else if (deckNumber == 4) p.setDeck4(String.valueOf(10 - wrong));
                            else p.setDeck5(String.valueOf(10 - wrong));

                            reference.child(p.getId()).setValue(p);
                        }
                    } else {

                        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("player");
                        String id = dbref.push().getKey();
                        Player p;

                        if (deckNumber == 1)
                            p = new Player(id, userEmail, "NA", "NA", "NA", String.valueOf(10 - wrong), "", "", "", "");
                        else if (deckNumber == 2)
                            p = new Player(id, userEmail, "NA", "NA", "NA", "", String.valueOf(10 - wrong), "", "", "");
                        else if (deckNumber == 3)
                            p = new Player(id, userEmail, "NA", "NA", "NA", "", "", String.valueOf(10 - wrong), "", "");
                        else if (deckNumber == 4)
                            p = new Player(id, userEmail, "NA", "NA", "NA", "", "", "", String.valueOf(10 - wrong), "");
                        else
                            p = new Player(id, userEmail, "NA", "NA", "NA", "", "", "", "", String.valueOf(10 - wrong));

                        dbref.child(id).setValue(p);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(DeckDisplayActivity.this, DeckActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 500);
        }
        catch (Exception e){
            Toast.makeText(DeckDisplayActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}