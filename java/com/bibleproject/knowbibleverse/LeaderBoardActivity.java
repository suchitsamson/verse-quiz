package com.bibleproject.knowbibleverse;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;

public class LeaderBoardActivity extends AppCompatActivity {

    TextView rankEasy;
    TextView rankIntermediate;
    TextView rankExpert;
    TextView textEasy;
    TextView textIntermediate;
    TextView textExpert;
    TableLayout tableLeaderboard;
    TableRow row;

    private int maxScore;
    private String selectedColor;
    private String unselectedColor;
    private int topRankingSize;
    private int selectedLevel;
    private int sNo;
    private int rowPadding;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.empty);
        setContentView(R.layout.activity_leader_board);

        maxScore = 99;
        selectedColor = "#f5da88";
        unselectedColor = "#bccadf";
        topRankingSize = 15;
        rowPadding = 10;
        sNo = 0;

        rankEasy = findViewById(R.id.textEasyRank);
        rankIntermediate = findViewById(R.id.textIntermediateRank);
        rankExpert = findViewById(R.id.textExpertRank);
        tableLeaderboard = findViewById(R.id.leaderTable);
        textEasy = findViewById(R.id.textEasy);
        textIntermediate = findViewById(R.id.textIntermediate);
        textExpert = findViewById(R.id.textExpert);

        mAuth = FirebaseAuth.getInstance();
        selectedLevel = HomeActivity.LEVEL;

        getUserBestScores();
        if(selectedLevel == 1) setEasyAttributes();
        else if(selectedLevel == 2) setIntermediateAttributes();
        else setExpertAttributes();

    }

    public void clickEasy(View view){
        setEasyAttributes();
    }

    public void clickIntermediate(View view){
        setIntermediateAttributes();
    }

    public void clickExpert(View view){
        setExpertAttributes();
    }

    public void setEasyAttributes(){
        rankEasy.setBackgroundColor(Color.parseColor(selectedColor));
        rankIntermediate.setBackgroundColor(Color.parseColor(unselectedColor));
        rankExpert.setBackgroundColor(Color.parseColor(unselectedColor));
        textEasy.setBackgroundColor(Color.parseColor(selectedColor));
        textIntermediate.setBackgroundColor(Color.parseColor(unselectedColor));
        textExpert.setBackgroundColor(Color.parseColor(unselectedColor));

        selectedLevel = 1;
        if(sNo >= 1) tableLeaderboard.removeViewsInLayout(1,sNo);
        getTableValues();
    }

    public void setIntermediateAttributes(){
        rankEasy.setBackgroundColor(Color.parseColor(unselectedColor));
        rankIntermediate.setBackgroundColor(Color.parseColor(selectedColor));
        rankExpert.setBackgroundColor(Color.parseColor(unselectedColor));
        textEasy.setBackgroundColor(Color.parseColor(unselectedColor));
        textIntermediate.setBackgroundColor(Color.parseColor(selectedColor));
        textExpert.setBackgroundColor(Color.parseColor(unselectedColor));

        selectedLevel = 2;
        if(sNo >= 1) tableLeaderboard.removeViewsInLayout(1,sNo);
        getTableValues();
    }

    public void setExpertAttributes(){
        rankEasy.setBackgroundColor(Color.parseColor(unselectedColor));
        rankIntermediate.setBackgroundColor(Color.parseColor(unselectedColor));
        rankExpert.setBackgroundColor(Color.parseColor(selectedColor));
        textEasy.setBackgroundColor(Color.parseColor(unselectedColor));
        textIntermediate.setBackgroundColor(Color.parseColor(unselectedColor));
        textExpert.setBackgroundColor(Color.parseColor(selectedColor));

        selectedLevel = 3;
        if(sNo >= 1) tableLeaderboard.removeViewsInLayout(1,sNo);
        getTableValues();
    }

    public void gotoHome(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void fillTable(String rank, String name, String date, String score, String email){

        row = new TableRow(this);

        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,2,0,2);

        TextView tRank = new TextView(this);
        tRank.setText(rank);
        tRank.setTextColor(Color.BLACK);
        tRank.setTextSize(getResources().getDimension(R.dimen.textsize));
        tRank.setLayoutParams(params);
        tRank.setGravity(Gravity.CENTER);
        tRank.setPadding(rowPadding,rowPadding,rowPadding,rowPadding);

        TextView tName = new TextView(this);
        tName.setText(name);
        tName.setTextColor(Color.BLACK);
        tName.setTextSize(getResources().getDimension(R.dimen.textsize));
        tName.setLayoutParams(params);
        tName.setGravity(Gravity.CENTER);
        tName.setPadding(rowPadding,rowPadding,rowPadding,rowPadding);

        TextView tDate = new TextView(this);
        tDate.setText(date);
        tDate.setTextColor(Color.BLACK);
        tDate.setTextSize(getResources().getDimension(R.dimen.textsize));
        tDate.setLayoutParams(params);
        tDate.setGravity(Gravity.CENTER);
        tDate.setPadding(rowPadding,rowPadding,rowPadding,rowPadding);

        TextView tScore = new TextView(this);
        tScore.setText(score);
        tScore.setTextColor(Color.BLACK);
        tScore.setTextSize(getResources().getDimension(R.dimen.textsize));
        tScore.setLayoutParams(params);
        tScore.setGravity(Gravity.CENTER);
        tScore.setPadding(rowPadding,rowPadding,rowPadding,rowPadding);

        if(mAuth.getCurrentUser().getEmail().equalsIgnoreCase(email)){
            tRank.setBackgroundColor(Color.parseColor(selectedColor));
            tName.setBackgroundColor(Color.parseColor(selectedColor));
            tDate.setBackgroundColor(Color.parseColor(selectedColor));
            tScore.setBackgroundColor(Color.parseColor(selectedColor));
        }

        else{
            tRank.setBackgroundColor(Color.parseColor(unselectedColor));
            tName.setBackgroundColor(Color.parseColor(unselectedColor));
            tDate.setBackgroundColor(Color.parseColor(unselectedColor));
            tScore.setBackgroundColor(Color.parseColor(unselectedColor));
        }

        row.addView(tRank);
        row.addView(tName);
        row.addView(tDate);
        row.addView(tScore);
        tableLeaderboard.addView(row);
    }

    public void getUserBestScores(){
        final String userEmail = mAuth.getCurrentUser().getEmail();

        FirebaseDatabase.getInstance().getReference().child("player").orderByChild("email").equalTo(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Player p = snapshot.getValue(Player.class);

                    if("NA".equalsIgnoreCase(p.getScoreOne()))
                        rankEasy.setText("NA");
                    else
                        rankEasy.setText(String.valueOf(maxScore - Integer.parseInt(p.getScoreOne())));

                    if("NA".equalsIgnoreCase(p.getScoreTwo()))
                        rankIntermediate.setText("NA");
                    else
                        rankIntermediate.setText(String.valueOf(maxScore - Integer.parseInt(p.getScoreTwo())));

                    if("NA".equalsIgnoreCase(p.getScoreThree()))
                        rankExpert.setText("NA");
                    else
                        rankExpert.setText( String.valueOf(maxScore - Integer.parseInt(p.getScoreThree())));
                    break;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void getTableValues(){

        try {
            if (selectedLevel == 1) {
                FirebaseDatabase.getInstance().getReference().child("LEVEL_ONE").orderByChild("score").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        setTable(dataSnapshot);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(LeaderBoardActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }

            if (selectedLevel == 2) {
                FirebaseDatabase.getInstance().getReference().child("LEVEL_TWO").orderByChild("score").limitToFirst(topRankingSize).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        setTable(dataSnapshot);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(LeaderBoardActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }

            if (selectedLevel == 3) {
                FirebaseDatabase.getInstance().getReference().child("LEVEL_THREE").orderByChild("score").limitToFirst(topRankingSize).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        setTable(dataSnapshot);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(LeaderBoardActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
        catch(Exception e){
            Toast.makeText(LeaderBoardActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void setTable(DataSnapshot dataSnapshot){
        sNo = 0;
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            Level p = snapshot.getValue(Level.class);
            p.setScore(String.valueOf(maxScore-Integer.parseInt(p.getScore())));
            fillTable(String.valueOf(++sNo), p.getUsername(), p.getDate(), p.getScore(), p.getEmail());
        }
    }
}
