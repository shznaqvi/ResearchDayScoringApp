package edu.aku.hassannaqvi.researchdayscoring.model;

public class Presentation {

    public String sectionTitle;
    public String comment = "";
    public String presentationReview;
    public boolean isSection;
    public boolean isComment;
    public int position;
    public int score;


    public Presentation(boolean isSection, int position) {
        this.isSection = isSection;
        this.position = position;
    }

    public Presentation(String sectionTitle, boolean isSection, int position) {
        this.sectionTitle = sectionTitle;
        this.isSection = isSection;
        this.position = position;
    }

    public Presentation(String sectionTitle, boolean isSection, int position, int score) {
        this.sectionTitle = sectionTitle;
        this.isSection = isSection;
        this.position = position;
        this.score = score;
    }

    public Presentation(String presentationReview, boolean isSection, boolean isComment) {
        this.presentationReview = presentationReview;
        this.isSection = isSection;
        this.isComment = isComment;
    }
}

