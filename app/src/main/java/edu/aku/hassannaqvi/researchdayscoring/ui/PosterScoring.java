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
import edu.aku.hassannaqvi.researchdayscoring.contracts.FinalScoreContract;
import edu.aku.hassannaqvi.researchdayscoring.contracts.ProjectsContract;
import edu.aku.hassannaqvi.researchdayscoring.core.DatabaseHelper;
import edu.aku.hassannaqvi.researchdayscoring.core.MainApp;
import edu.aku.hassannaqvi.researchdayscoring.databinding.ActivityPosterScoringBinding;
import edu.aku.hassannaqvi.researchdayscoring.databinding.CustomDialogLayoutBinding;
import edu.aku.hassannaqvi.researchdayscoring.databinding.DialogLayoutBinding;
import edu.aku.hassannaqvi.researchdayscoring.model.Data;
import edu.aku.hassannaqvi.researchdayscoring.model.Poster;
public class PosterScoring extends AppCompatActivity {

    private static final String TAG = "PosterScoring";

    ActivityPosterScoringBinding bi;
    PosterScoringAdapter adapter;
    ProjectsContract contract;
    ArrayList<Poster> list;
    AlertDialog dialog;
    DecimalFormat formatter = new DecimalFormat("00");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_poster_scoring);

        this.setTitle("Poster Scoring");
        OpeningDialog();

    }

    private void OpeningDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null);
        builder.setView(view);
        final DialogLayoutBinding binding = DataBindingUtil.bind(view);
        dialog = builder.create();
        dialog.show();
        assert binding != null;
        binding.enterProjectIdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!binding.etProjectID.getText().toString().equals("")) {
                    String projID = binding.etProjectID.getText().toString();
                    gettingDataFromDB(projID);

                } else {
                    binding.etProjectID.setError("This field is required");
                }

            }
        });

        binding.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                finish();
            }
        });

    }

    private void gettingDataFromDB(String projID) {
        DatabaseHelper db = new DatabaseHelper(this);
        contract = db.getSingleProject(projID, "1");
        if (contract.getTitle() != null) {
            init();
//            bi.notFound.setVisibility(View.GONE);
            dialog.dismiss();
        } else {
            Toast.makeText(this, "Data not found!", Toast.LENGTH_SHORT).show();
//            bi.notFound.setVisibility(View.VISIBLE);
        }


    }

    private void init() {
        bi.dashboardLayout.setVisibility(View.VISIBLE);
        bi.posterContent.setVisibility(View.VISIBLE);
        bi.submitBtn.setVisibility(View.VISIBLE);
        bi.authorName.setText(contract.getAuthor());
        bi.ProjectTitle.setText(contract.getTitle());
        bi.ProjectTheme.setText(contract.getTheme());
//        bi.ProjectAbsatract.setText("Abstract :" + contract.getAbstract());

        ArrayList<Poster> items = Data.getPosterItems(this);
//        for (int i = 0; i < items.size(); i++) {
//            items.get(i).score = 0;
//        }
        adapter = new PosterScoringAdapter(this, items);
        bi.posterContent.setLayoutManager(new LinearLayoutManager(this));
        bi.posterContent.setHasFixedSize(true);
        bi.posterContent.setAdapter(adapter);
        list = new ArrayList<>();

        bi.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() > 0) list.clear();
                for (int i = 0; i < adapter.getList().size(); i++) {
                    if (!adapter.getList().get(i).isSection) {
                        list.add(adapter.getList().get(i));
                    }
                }
                openDialog();
            }

        });


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
                object.put("pos" + formatter.format(i + 1), list.get(i).comment);
            } else {
                object.put("pos" + formatter.format(i + 1), list.get(i).score);
                score += list.get(i).score;
            }

        }
        MainApp.fsc.setContent(String.valueOf(object));
        MainApp.fsc.setScore(String.valueOf(score));


    }

}
