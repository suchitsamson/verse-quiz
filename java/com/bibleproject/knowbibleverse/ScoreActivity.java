package com.bibleproject.knowbibleverse;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ScoreActivity extends AppCompatActivity {

    RadioButton radioL1;
    RadioButton radioL2;
    RadioButton radioL3;

    TextView score;
    private int recordCount;
    private FirebaseAuth mAuth;

    private int maxScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.empty);
        setContentView(R.layout.activity_score);
        maxScore = 99;
        recordCount = 15;

        radioL1 = findViewById(R.id.radioLevel1);
        radioL2 = findViewById(R.id.radioLevel2);
        radioL3 = findViewById(R.id.radioLevel3);
        score = findViewById(R.id.textScore);

        score.setText(String.valueOf(MainActivity.totalCorrect));
        mAuth = FirebaseAuth.getInstance();

        if(HomeActivity.LEVEL == 1) radioL1.setChecked(true);
        else if(HomeActivity.LEVEL == 2) radioL2.setChecked(true);
        else radioL3.setChecked(true);
    }

    public void goToGame(View view){
        checkLevel();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void checkLevel(){
        if(radioL1.isChecked()) HomeActivity.LEVEL = 1;
        else if(radioL2.isChecked()) HomeActivity.LEVEL = 2;
        else HomeActivity.LEVEL = 3;
    }

    public void addOrUpdateScoreDB(DatabaseReference databaseRef, DataSnapshot dataSnapshot){

        try {
            int intScore = MainActivity.totalCorrect;

            Date currentTime = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd MMM yy");
            String formattedDate = df.format(currentTime);

            //add
            if (dataSnapshot.getChildrenCount() < recordCount) {
                String id = databaseRef.push().getKey();
                Level record = new Level(id,
                        mAuth.getCurrentUser().getDisplayName(),
                        mAuth.getCurrentUser().getEmail(),
                        String.valueOf(maxScore - intScore),
                        formattedDate);
                databaseRef.child(id).setValue(record);
            } else {
                //update
                //Check for the score of min element. if user score is greater update the last element values.
                int count = 1;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (count == recordCount) {
                        Level lastElement = snapshot.getValue(Level.class);
                        if (Integer.parseInt(lastElement.getScore()) > maxScore - intScore) {
                            lastElement.setUsername(mAuth.getCurrentUser().getDisplayName());
                            lastElement.setEmail(mAuth.getCurrentUser().getEmail());
                            lastElement.setScore(String.valueOf(maxScore - intScore));
                            lastElement.setDate(formattedDate);
                            databaseRef.child(lastElement.getId()).setValue(lastElement);
                        }
                    }
                    count++;
                }
            }
        }
        catch(Exception e){
            Toast.makeText(ScoreActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void goToLeaderboard(View view) {

        final DatabaseReference databaseRef;
        try{
            if (HomeActivity.LEVEL == 1) {
                databaseRef = FirebaseDatabase.getInstance().getReference().child("LEVEL_ONE");

                databaseRef.orderByChild("score").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        addOrUpdateScoreDB(databaseRef, dataSnapshot);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            } else if (HomeActivity.LEVEL == 2) {
                databaseRef = FirebaseDatabase.getInstance().getReference().child("LEVEL_TWO");

                databaseRef.orderByChild("score").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        addOrUpdateScoreDB(databaseRef, dataSnapshot);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            } else {
                databaseRef = FirebaseDatabase.getInstance().getReference().child("LEVEL_THREE");

                databaseRef.orderByChild("score").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        addOrUpdateScoreDB(databaseRef, dataSnapshot);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }

            final String userEmail = mAuth.getCurrentUser().getEmail();
            final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("player");

            reference.orderByChild("email").equalTo(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int intScore = MainActivity.totalCorrect;
                    //player record does not exists ie first time submitting
                    if (dataSnapshot.getChildrenCount() == 0) {

                        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("player");
                        String id = dbref.push().getKey();
                        Player p;

                        if (HomeActivity.LEVEL == 1)
                            p = new Player(id, userEmail, String.valueOf(maxScore - intScore), "NA", "NA", "", "", "", "", "");
                        else if (HomeActivity.LEVEL == 2)
                            p = new Player(id, userEmail, "NA", String.valueOf(maxScore - intScore), "NA", "", "", "", "", "");
                        else
                            p = new Player(id, userEmail, "NA", "NA", String.valueOf(maxScore - intScore), "", "", "", "", "");

                        dbref.child(id).setValue(p);
                    } else {
                        //player record exists
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            Player p = data.getValue(Player.class);
                            if (HomeActivity.LEVEL == 1 && ("NA".equalsIgnoreCase(p.getScoreOne()) || Integer.parseInt(p.getScoreOne()) > maxScore - intScore))
                                p.setScoreOne(String.valueOf(maxScore - intScore));
                            else if (HomeActivity.LEVEL == 2 && ("NA".equalsIgnoreCase(p.getScoreTwo()) || Integer.parseInt(p.getScoreTwo()) > maxScore - intScore))
                                p.setScoreTwo(String.valueOf(maxScore - intScore));
                            else if (HomeActivity.LEVEL == 3 && ("NA".equalsIgnoreCase(p.getScoreThree()) || Integer.parseInt(p.getScoreThree()) > maxScore - intScore))
                                p.setScoreThree(String.valueOf(maxScore - intScore));

                            reference.child(data.getKey()).setValue(p);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(ScoreActivity.this, LeaderBoardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }, 1000);
        }
        catch(Exception e){
            Toast.makeText(ScoreActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void gotoHome(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
