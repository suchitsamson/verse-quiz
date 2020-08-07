package com.bibleproject.knowbibleverse;

public class Player {

    private String id;
    private String email;
    private String scoreOne;
    private String scoreTwo;
    private String scoreThree;
    private String deck1;
    private String deck2;
    private String deck3;
    private String deck4;
    private String deck5;

    public Player (){

    }

    public Player(String id, String email, String scoreOne, String scoreTwo, String scoreThree, String deck1, String deck2, String deck3, String deck4, String deck5) {
        this.id = id;
        this.email = email;
        this.scoreOne = scoreOne;
        this.scoreTwo = scoreTwo;
        this.scoreThree = scoreThree;
        this.deck1 = deck1;
        this.deck2 = deck2;
        this.deck3 = deck3;
        this.deck4 = deck4;
        this.deck5 = deck5;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getScoreOne() {
        return scoreOne;
    }

    public void setScoreOne(String scoreOne) {
        this.scoreOne = scoreOne;
    }

    public String getScoreTwo() {
        return scoreTwo;
    }

    public void setScoreTwo(String scoreTwo) {
        this.scoreTwo = scoreTwo;
    }

    public String getScoreThree() {
        return scoreThree;
    }

    public void setScoreThree(String scoreThree) {
        this.scoreThree = scoreThree;
    }

    public String getDeck1() { return deck1; }

    public void setDeck1(String deck1) { this.deck1 = deck1; }

    public String getDeck2() { return deck2; }

    public void setDeck2(String deck2) { this.deck2 = deck2; }

    public String getDeck3() { return deck3; }

    public void setDeck3(String deck3) { this.deck3 = deck3; }

    public String getDeck4() { return deck4; }

    public void setDeck4(String deck4) { this.deck4 = deck4; }

    public String getDeck5() { return deck5; }

    public void setDeck5(String deck5) { this.deck5 = deck5; }
}
