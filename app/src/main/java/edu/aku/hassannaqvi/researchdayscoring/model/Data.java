package edu.aku.hassannaqvi.researchdayscoring.model;

import android.content.Context;

import java.util.ArrayList;

import edu.aku.hassannaqvi.researchdayscoring.R;

public class Data {

    // 0 = comment question
    // 1 = not comment question

    public static ArrayList<Presentation> getPresentationItems(Context context) {

        ArrayList<Presentation> items = new ArrayList<>();
        ArrayList<Presentation> finalArray = new ArrayList<>();
        String[] reviews = context.getResources().getStringArray(R.array.presentation_content);
        String[] secHeading = context.getResources().getStringArray(R.array.presentation_section);
        items.add(new Presentation(secHeading[0], true, 0));
        items.add(new Presentation(secHeading[1], true, 3));
        items.add(new Presentation(secHeading[2], true, 10));
        for (int j = 0; j < reviews.length; j++) {
            if (j == items.get(0).position) {
                finalArray.add(new Presentation(items.get(0).sectionTitle, true, false));
                continue;
            } else if (j == items.get(1).position) {
                finalArray.add(new Presentation(items.get(1).sectionTitle, true, false));
                continue;
            } else if (j == items.get(2).position) {
                finalArray.add(new Presentation(items.get(2).sectionTitle, true, false));
                continue;
            } else if (j == 14) {
                finalArray.add(new Presentation(reviews[j - 1], false, true));
                continue;
            }
            finalArray.add(new Presentation(reviews[j - 1], false, false));

        }
        return finalArray;
    }

    public static ArrayList<Poster> getPosterItems(Context context) {
        ArrayList<Poster> finalArray = new ArrayList<>();
        String[] reviews = context.getResources().getStringArray(R.array.poster_content);
        for (int i = 0; i < reviews.length - 1; i = i + 2) {
            if (reviews[i].equalsIgnoreCase("Comment")) {
                finalArray.add(new Poster(reviews[i], true, i));
                finalArray.add(new Poster(reviews[i + 1], false, true));
            } else {
                finalArray.add(new Poster(reviews[i], true, i));
                finalArray.add(new Poster(reviews[i + 1], false, 0));
            }


        }


        return finalArray;
    }

}
