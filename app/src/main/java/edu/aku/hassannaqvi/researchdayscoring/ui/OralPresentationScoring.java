package edu.aku.hassannaqvi.researchdayscoring.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import edu.aku.hassannaqvi.researchdayscoring.R;
import edu.aku.hassannaqvi.researchdayscoring.adapter.PresentationScoringAdapter;
import edu.aku.hassannaqvi.researchdayscoring.databinding.ActivityOralPresentationScoringBinding;
import edu.aku.hassannaqvi.researchdayscoring.model.Data;
import edu.aku.hassannaqvi.researchdayscoring.model.Presentation;


public class OralPresentationScoring extends AppCompatActivity {


    private static final String TAG = "OralPresentationScoring";
    ActivityOralPresentationScoringBinding bi;
    PresentationScoringAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_oral_presentation_scoring);

        init();


    }

    private void init() {


        ArrayList<Presentation> items = new ArrayList<>();
        ArrayList<String> sectionName = new ArrayList<>();

        items = Data.getPresentationItems(this);
        for (int i = 0; i < items.size(); i++) {
            items.get(i).score = 0;
        }
        adapter = new PresentationScoringAdapter(this, items);
        bi.contentList.setLayoutManager(new LinearLayoutManager(this));
        bi.contentList.setHasFixedSize(true);
        bi.contentList.setAdapter(adapter);

        bi.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < adapter.getList().size() - 2; i++) {
                    if (adapter.getList().get(i).score > 0 || !adapter.getList().get(i).comment.equals("")) {
                        Toast.makeText(OralPresentationScoring.this, "completed", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {

                        Toast.makeText(OralPresentationScoring.this, "please fill all answer", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

}
