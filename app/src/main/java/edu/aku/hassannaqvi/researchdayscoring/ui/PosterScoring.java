package edu.aku.hassannaqvi.researchdayscoring.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

import edu.aku.hassannaqvi.researchdayscoring.R;
import edu.aku.hassannaqvi.researchdayscoring.adapter.PosterScoringAdapter;
import edu.aku.hassannaqvi.researchdayscoring.databinding.ActivityPosterScoringBinding;
import edu.aku.hassannaqvi.researchdayscoring.model.Data;
import edu.aku.hassannaqvi.researchdayscoring.model.Poster;

public class PosterScoring extends AppCompatActivity {

    private static final String TAG = "PosterScoring";

    ActivityPosterScoringBinding bi;
    PosterScoringAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_poster_scoring);

        init();

    }

    private void init() {

        ArrayList<Poster> items = Data.getPosterItems(this);
        for (int i = 0; i < items.size(); i++) {
            items.get(i).score = 0;
        }

        adapter = new PosterScoringAdapter(this, items);
        bi.posterContent.setLayoutManager(new LinearLayoutManager(this));
        bi.posterContent.setHasFixedSize(true);
        bi.posterContent.setAdapter(adapter);
    }

}
