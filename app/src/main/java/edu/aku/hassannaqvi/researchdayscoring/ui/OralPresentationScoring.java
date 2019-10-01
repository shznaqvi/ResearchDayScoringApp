package edu.aku.hassannaqvi.researchdayscoring.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import edu.aku.hassannaqvi.researchdayscoring.R;
import edu.aku.hassannaqvi.researchdayscoring.adapter.PosterScoringAdapter;
import edu.aku.hassannaqvi.researchdayscoring.adapter.PresentationScoringAdapter;
import edu.aku.hassannaqvi.researchdayscoring.contracts.FinalScoreContract;
import edu.aku.hassannaqvi.researchdayscoring.contracts.ProjectsContract;
import edu.aku.hassannaqvi.researchdayscoring.core.DatabaseHelper;
import edu.aku.hassannaqvi.researchdayscoring.core.MainApp;
import edu.aku.hassannaqvi.researchdayscoring.databinding.ActivityOralPresentationScoringBinding;
import edu.aku.hassannaqvi.researchdayscoring.databinding.CustomDialogLayoutBinding;
import edu.aku.hassannaqvi.researchdayscoring.databinding.DialogLayoutBinding;
import edu.aku.hassannaqvi.researchdayscoring.model.Data;
import edu.aku.hassannaqvi.researchdayscoring.model.Poster;
import edu.aku.hassannaqvi.researchdayscoring.model.Presentation;


public class OralPresentationScoring extends AppCompatActivity {


    private static final String TAG = "OralPresentationScoring";
    ActivityOralPresentationScoringBinding bi;
    PresentationScoringAdapter adapter;
    PosterScoringAdapter adapter1;
    ProjectsContract contract;
    ArrayList<Presentation> list;
    ArrayList<Poster> list1;
    DecimalFormat formatter = new DecimalFormat("00");
    AlertDialog dialog;
    ArrayList<Poster> posters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_oral_presentation_scoring);

        contract = getIntent().getParcelableExtra("data");

        init();

    }

    private void init() {
        bi.authorName.setText(contract.getAuthor());
        bi.projectTheme.setText(contract.getTheme());
        bi.projectAbstract.setText(contract.getAbstract());
        if (contract.getType().equals("1")) {
            ArrayList<Presentation> items = new ArrayList<>();
            items = Data.getPresentationItems(this);
            adapter = new PresentationScoringAdapter(this, items);
            bi.posterContent.setLayoutManager(new LinearLayoutManager(this));
            bi.posterContent.setHasFixedSize(true);
            bi.posterContent.setAdapter(adapter);

            list = new ArrayList<>();

            bi.submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list.size() > 0) list.clear();
                    for (int i = 0; i < adapter.getList().size() - 2; i++) {

                        if (!adapter.getList().get(i).isSection) {
                            if (adapter.getList().get(i).score > 0) {
                                list.add(adapter.getList().get(i));
                            }
                        }
                    }
                    openDialog();
                }
            });

        } else {
            ArrayList<Poster> items = new ArrayList<>();
            posters = new ArrayList<>();
            items = Data.getPosterItems(this);
            adapter1 = new PosterScoringAdapter(this, items);
            bi.posterContent.setLayoutManager(new LinearLayoutManager(this));
            bi.posterContent.setHasFixedSize(true);
            bi.posterContent.setAdapter(adapter1);
            list1 = new ArrayList<>();
            bi.submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list1.size() > 0) list1.clear();
                    for (int i = 0; i < adapter1.getList().size(); i++) {
                        if (!adapter1.getList().get(i).isSection) {
                            list1.add(adapter1.getList().get(i));
                        }
                    }
                    openDialog();
                }
            });

        }


    }

    private void openDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.custom_dialog_layout, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        CustomDialogLayoutBinding bi = DataBindingUtil.bind(view);

        bi.btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        bi.btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    saveDraft();
                    if (updateDB()) {
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private boolean updateDB() {
        DatabaseHelper db = new DatabaseHelper(this);
        long rowID = db.addForm(MainApp.fsc);
        if (rowID != 0) {
            Toast.makeText(this, "successfully added!", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(this, "database error!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void saveDraft() throws JSONException {

        MainApp.fsc = new FinalScoreContract();
        MainApp.fsc.setAbstract(contract.getAbstract());
        MainApp.fsc.setAuthor(contract.getAuthor());
        MainApp.fsc.setProj_id(contract.getProj_id());
        MainApp.fsc.setTitle(contract.getTitle());
        MainApp.fsc.setType(contract.getType());
        MainApp.fsc.setTheme(contract.getTheme());
        MainApp.fsc.setJudgeName(MainApp.userName);
        int score = 0;

        JSONObject object = new JSONObject();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isComment) {
                object.put(contract.getType().equals("1") ? "pres" : "pos" + formatter.format(i + 1), contract.getType().equals("1") ? list.get(i).comment : list1.get(i).comment);
            } else {
                object.put(contract.getType().equals("1") ? "pres" : "pos" + formatter.format(i + 1), contract.getType().equals("1") ? list.get(i).score : list1.get(i).score);
                score += list.get(i).score;
            }

        }
        MainApp.fsc.setContent(String.valueOf(object));
        MainApp.fsc.setScore(String.valueOf(score));
    }

}
