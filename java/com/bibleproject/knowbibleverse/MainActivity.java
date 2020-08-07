package com.bibleproject.knowbibleverse;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    static String verseContent[] ={"For God so loved the world that he gave his one and only Son, that whoever believes in him shall not perish but have eternal life.",
            "For I know the plans I have for you,” declares the LORD, “plans to prosper you and not to harm you, plans to give you hope and a future.",
            "And we know that in all things God works for the good of those who love him, who have been called according to his purpose.",
            "I can do everything through him who gives me strength.",
            "In the beginning God created the heavens and the earth.",
            "Trust in the LORD with all your heart and lean not on your own understanding.",
            "in all your ways acknowledge him, and he will make your paths straight.",
            "Do not conform any longer to the pattern of this world, but be transformed by the renewing of your mind. Then you will be able to test and approve what God’s will is—his good, pleasing and perfect will.",
            "Do not be anxious about anything, but in everything, by prayer and petition, with thanksgiving, present your requests to God.",
            "Therefore go and make disciples of all nations, baptizing them in the name of the Father and of the Son and of the Holy Spirit.",
            "For it is by grace you have been saved, through faith—and this not from yourselves, it is the gift of God—",
            "But the fruit of the Spirit is love, joy, peace, patience, kindness, goodness, faithfulness,",
            "Therefore, I urge you, brothers, in view of God’s mercy, to offer your bodies as living sacrifices, holy and pleasing to God—this is your spiritual act of worship.",
            "The thief comes only to steal and kill and destroy; I have come that they may have life, and have it to the full.",
            "For I am with you, and no one is going to attack and harm you, because I have many people in this city.",
            "One night the Lord spoke to Paul in a vision: “Do not be afraid; keep on speaking, do not be silent.",
            "So Paul stayed for a year and a half, teaching them the word of God.",
            "I have been crucified with Christ and I no longer live, but Christ lives in me. The life I live in the body, I live by faith in the Son of God, who loved me and gave himself for me.",
            "If we confess our sins, he is faithful and just and will forgive us our sins and purify us from all unrighteousness.",
            "for all have sinned and fall short of the glory of God,",
            "Jesus answered, “I am the way and the truth and the life. No one comes to the Father except through me.",
            "and teaching them to obey everything I have commanded you. And surely I am with you always, to the very end of the age.",
            "But God demonstrates his own love for us in this: While we were still sinners, Christ died for us.",
            "Finally, brothers, whatever is true, whatever is noble, whatever is right, whatever is pure, whatever is lovely, whatever is admirable—if anything is excellent or praiseworthy—think about such things.",
            "And the peace of God, which transcends all understanding, will guard your hearts and your minds in Christ Jesus.",
            "Have I not commanded you? Be strong and courageous. Do not be terrified; do not be discouraged, for the LORD your God will be with you wherever you go.",
            "but those who hope in the LORD will renew their strength. They will soar on wings like eagles; they will run and not grow weary, they will walk and not be faint.",
            "not by works, so that no one can boast.",
            "For the wages of sin is death, but the gift of God is eternal life in Christ Jesus our Lord.",
            "gentleness and self-control. Against such things there is no law.",
            "But he was pierced for our transgressions, he was crushed for our iniquities; the punishment that brought us peace was upon him, and by his wounds we are healed.",
            "But in your hearts set apart Christ as Lord. Always be prepared to give an answer to everyone who asks you to give the reason for the hope that you have. But do this with gentleness and respect,",
            "All Scripture is God-breathed and is useful for teaching, rebuking, correcting and training in righteousness,",
            "But seek first his kingdom and his righteousness, and all these things will be given to you as well.",
            "Let us fix our eyes on Jesus, the author and perfecter of our faith, who for the joy set before him endured the cross, scorning its shame, and sat down at the right hand of the throne of God.",
            "Cast all your anxiety on him because he cares for you.",
            "For we are God’s workmanship, created in Christ Jesus to do good works, which God prepared in advance for us to do.",
            "No temptation has seized you except what is common to man. And God is faithful; he will not let you be tempted beyond what you can bear. But when you are tempted, he will also provide a way out so that you can stand up under it.",
            "Come to me, all you who are weary and burdened, and I will give you rest.",
            "Now faith is being sure of what we hope for and certain of what we do not see.",
            "Therefore, if anyone is in Christ, he is a new creation; the old has gone, the new has come!",
            "Keep your lives free from the love of money and be content with what you have, because God has said, “Never will I leave you; never will I forsake you.",
            "But he said to me, “My grace is sufficient for you, for my power is made perfect in weakness.” Therefore I will boast all the more gladly about my weaknesses, so that Christ’s power may rest on me.",
            "That if you confess with your mouth, “Jesus is Lord,” and believe in your heart that God raised him from the dead, you will be saved.",
            "So do not fear, for I am with you; do not be dismayed, for I am your God. I will strengthen you and help you; I will uphold you with my righteous right hand.",
            "Then God said, “Let us make man in our image, in our likeness, and let them rule over the fish of the sea and the birds of the air, over the livestock, over all the earth, and over all the creatures that move along the ground.",
            "Take my yoke upon you and learn from me, for I am gentle and humble in heart, and you will find rest for your souls.",
            "I have told you these things, so that in me you may have peace. In this world you will have trouble. But take heart! I have overcome the world.",
            "But you will receive power when the Holy Spirit comes on you; and you will be my witnesses in Jerusalem, and in all Judea and Samaria, and to the ends of the earth.",
            "For God did not give us a spirit of timidity, but a spirit of power, of love and of self-discipline."};

    static String verseReference[] = {"3:16", "29:11", "8:28", "4:13", "1:1", "3:5", "3:6", "12:2", "4:6", "28:19", "2:8", "5:22", "12:1", "10:10", "18:10", "18:9", "18:11", "2:20", "1:9", "3:23", "14:6", "28:20", "5:8", "4:8", "4:7", "1:9", "40:31", "2:9", "6:23", "5:23", "53:5", "3:15", "3:16", "6:33", "12:2", "5:7", "2:10", "10:13", "11:28", "11:1", "5:17", "13:5", "12:9", "10:9", "41:10", "1:26", "11:29", "16:33", "1:8", "1:7"};

    static int verseBook[] = {42, 23, 44, 49, 0, 19, 19, 44, 49, 39, 48, 47, 44, 42, 43, 43, 47, 61, 44, 42, 39, 44, 49, 49, 5, 22, 48, 44, 47, 22, 59, 54, 39, 57, 59, 48, 45, 39, 57, 46, 57, 46, 44, 22, 0, 39, 42, 43, 54};

    static String books[] = {"Genesis", "Exodus", "Leviticus", "Numbers", "Deuteronomy", "Joshua",
            "Judges", "Ruth", "1 Samuel", "2 Samuel", "1 Kings", "2 Kings",
            "1 Chronicles", "2 Chronicles", "Ezra", "Nehemiah", "Esther", "Job",
            "Psalms", "Proverbs", "Ecclesiastes", "Song of Solomon", "Isaiah", "Jeremiah",
            "Lamentations", "Ezekiel", "Daniel", "Hosea", "Joel", "Amos",
            "Obadiah", "Jonah", "Micah", "Nahum", "Habakkuk", "Zephaniah",
            "Haggai", "Zechariah", "Malachi", "Matthew", "Mark", "Luke",
            "John", "Acts", "Romans", "1 Corinthians", "2 Corinthians", "Galatians",
            "Ephesians", "Philippians", "Colossians", "1 Thessalonians", "2 Thessalonians", "1 Timothy",
            "2 Timothy", "Titus", "Philemon", "Hebrews", "James", "1 Peter",
            "2 Peter", "1 John", "2 John", "3 John", "Jude", "Revelation"};

    int options[];
    String rightans;
    static int totalCorrect;
    boolean gameInProgress;

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    TextView textView;
    TextView textViewCorrect;
    TextView textViewTimer;

    String colorOption;
    String colorCorrect;
    String colorIncorrect;

    public void timerStart(){
        gameInProgress = true;
        CountDownTimer cdTimer = new CountDownTimer(61000, 1000) {
            @Override
            public void onTick(long l) {
                textViewTimer.setText(String.valueOf(l / 1000));
            }

            @Override
            public void onFinish() {
                gameInProgress = false;
                goToScoreActivity();
            }
        }.start();
    }

    public void goToScoreActivity() {
        Intent intent = new Intent(this, ScoreActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void startGame() {
        totalCorrect = 0;
        timerStart();
        iteration();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.empty);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.verse);
        textViewCorrect = findViewById(R.id.correct);
        textViewTimer = findViewById(R.id.timer);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);

        colorOption = "#800000";
        colorCorrect = "#f5da88";
        colorIncorrect = "#bccadf";

        //test();
        startGame();
    }

    public void findCorrectOption(int flag){
        if(flag!=1 && button1.getText().toString().equals(rightans))
            button1.setBackgroundColor(Color.parseColor(colorCorrect));
        else if(flag!=2 && button2.getText().toString().equals(rightans))
            button2.setBackgroundColor(Color.parseColor(colorCorrect));
        else if(flag!=3 && button3.getText().toString().equals(rightans))
            button3.setBackgroundColor(Color.parseColor(colorCorrect));
        else
            button4.setBackgroundColor(Color.parseColor(colorCorrect));
    }

    public void clickzero(View view){
        if(gameInProgress) {
            if (button1.getText().toString().equals(rightans)) {
                button1.setBackgroundColor(Color.parseColor(colorCorrect));
                totalCorrect++;
            } else {
                button1.setBackgroundColor(Color.parseColor(colorIncorrect));
                findCorrectOption(1);
            }
            iteration();
        }
    }

    public void clickone(View view){
        if(gameInProgress) {
            if (button2.getText().toString().equals(rightans)) {
                button2.setBackgroundColor(Color.parseColor(colorCorrect));
                totalCorrect++;
            } else {
                button2.setBackgroundColor(Color.parseColor(colorIncorrect));
                findCorrectOption(2);
            }
            iteration();
        }
    }

    public void clicktwo(View view){
        if(gameInProgress) {
            if (button3.getText().toString().equals(rightans)) {
                button3.setBackgroundColor(Color.parseColor(colorCorrect));
                totalCorrect++;
            } else {
                button3.setBackgroundColor(Color.parseColor(colorIncorrect));
                findCorrectOption(3);
            }
            iteration();
        }
    }

    public void clickthree(View view){
        if(gameInProgress) {
            if (button4.getText().toString().equals(rightans)) {
                button4.setBackgroundColor(Color.parseColor(colorCorrect));
                totalCorrect++;
            } else {
                button4.setBackgroundColor(Color.parseColor(colorIncorrect));
                findCorrectOption(4);
            }
            iteration();
        }
    }

    public void iteration() {

        Random rand = new Random();
        options = rand.ints(0, verseBook.length-1).distinct().limit(4).toArray();

        int choice, choice2, choice3, choice4;

        choice = rand.nextInt(4);
        choice2 = rand.nextInt(4);
        while (choice == choice2) {
            choice2 = rand.nextInt(4);
        }

        choice3 = rand.nextInt(4);
        while (choice == choice3 || choice2 == choice3) {
            choice3 = rand.nextInt(4);
        }

        choice4 = rand.nextInt(4);
        while (choice == choice4 || choice2 == choice4 || choice3 == choice4) {
            choice4 = rand.nextInt(4);
        }

        final int fChoice = choice;
        final int fChoice2 = choice2;
        final int fChoice3 = choice3;
        final int fChoice4 = choice4;
        final int tcorrect = totalCorrect;

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textViewCorrect.setText(String.valueOf(tcorrect));

                button1.setBackgroundColor(Color.parseColor(colorOption));
                button2.setBackgroundColor(Color.parseColor(colorOption));
                button3.setBackgroundColor(Color.parseColor(colorOption));
                button4.setBackgroundColor(Color.parseColor(colorOption));

                if(HomeActivity.LEVEL==1){
                    button1.setText("OLD TESTAMENT");
                    button2.setText("NEW TESTAMENT");
                    button3.setVisibility(View.GONE);
                    button4.setVisibility(View.GONE);

                    rightans = verseBook[options[0]] > 38 ? "NEW TESTAMENT" : "OLD TESTAMENT";
                }
                else if(HomeActivity.LEVEL==2){
                    button1.setText(books[verseBook[options[fChoice]]]);
                    button2.setText(books[verseBook[options[fChoice2]]]);
                    button3.setText(books[verseBook[options[fChoice3]]]);
                    button4.setText(books[verseBook[options[fChoice4]]]);

                    rightans = books[verseBook[options[0]]];
                }
                else {
                    button1.setText(books[verseBook[options[fChoice]]] + " " + verseReference[options[fChoice]]);
                    button2.setText(books[verseBook[options[fChoice2]]] + " " + verseReference[options[fChoice2]]);
                    button3.setText(books[verseBook[options[fChoice3]]] + " " + verseReference[options[fChoice3]]);
                    button4.setText(books[verseBook[options[fChoice4]]] + " " + verseReference[options[fChoice4]]);

                    rightans = books[verseBook[options[0]]] + " " + verseReference[options[0]];
                }
                textView.setText(verseContent[options[0]]);
            }
        }, 500);
    }

    /*
    public void test(){
        Log.i("test", "INSIDE TEST");

        String data ="John/3:16/For God so loved the world that he gave his one and only Son, that whoever believes in him shall not perish but have eternal life.\n" +
                "Jeremiah/29:11/For I know the plans I have for you,” declares the LORD, “plans to prosper you and not to harm you, plans to give you hope and a future.\n";

        String verseContent[]=new String[50];
        String verse[]=new String[50];
        int book[]=new int[50];

        int verseContentCounter = 0, verseCounter = 0, bookCounter = 0;

        for(String line: data.split("\n")){
            String content[] = line.split("/");
            verseContent[verseContentCounter++] = content[2];
            verse[verseCounter++] = content[1];
            for(int i=0;i<books.length;i++){
                if(books[i].equalsIgnoreCase(content[0]))
                    book[bookCounter++] = i;
            }
        }

        for(int i=0;i<50;i++) Log.i("book", String.valueOf(book[i])+',');
        for(int i=0;i<50;i++) Log.i("verse", '"'+verse[i]+'"'+',');
        for(int i=0;i<50;i++) Log.i("content", '"'+verseContent[i]+'"'+',');
    }
 */
}