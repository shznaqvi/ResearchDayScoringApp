package edu.aku.hassannaqvi.researchdayscoring.model;

public class Poster {

    public String sectionTitle;
    public String comment = "";
    public String posterReview;
    public boolean isSection;
    public boolean isComment;
    public int position;
    public int questionType = 0;
    public int score= 0;

    public Poster(boolean isSection, int position) {
        this.isSection = isSection;
        this.position = position;
    }

    public Poster(String sectionTitle, boolean isSection, int position) {
        this.sectionTitle = sectionTitle;
        this.isSection = isSection;
        this.position = position;
    }

    public Poster(String sectionTitle, boolean isSection, int position, int score) {
        this.sectionTitle = sectionTitle;
        this.isSection = isSection;
        this.position = position;
        this.score = score;
    }

    public Poster(String posterReview, boolean isSection, boolean isComment) {
        this.posterReview = posterReview;
        this.isSection = isSection;
        this.isComment = isComment;
    }
}
