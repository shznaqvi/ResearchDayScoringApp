package edu.aku.hassannaqvi.researchdayscoring.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.aku.hassannaqvi.researchdayscoring.R;
import edu.aku.hassannaqvi.researchdayscoring.adapter.PosterScoringAdapter;
import edu.aku.hassannaqvi.researchdayscoring.adapter.PresentationScoringAdapter;
import edu.aku.hassannaqvi.researchdayscoring.animation.Animations;
import edu.aku.hassannaqvi.researchdayscoring.contracts.FinalScoreContract;
import edu.aku.hassannaqvi.researchdayscoring.contracts.ProjectsContract;
import edu.aku.hassannaqvi.researchdayscoring.core.DatabaseHelper;
import edu.aku.hassannaqvi.researchdayscoring.core.MainApp;
import edu.aku.hassannaqvi.researchdayscoring.databinding.ActivityOralPresentationScoringBinding;
import edu.aku.hassannaqvi.researchdayscoring.databinding.CustomDialogLayoutBinding;
import edu.aku.hassannaqvi.researchdayscoring.model.Data;
import edu.aku.hassannaqvi.researchdayscoring.model.Poster;
import edu.aku.hassannaqvi.researchdayscoring.model.Presentation;


public class ScoringActivity extends AppCompatActivity {


    private static final String TAG = "ScoringActivity";
    ActivityOralPresentationScoringBinding bi;
    PresentationScoringAdapter adapter;
    PosterScoringAdapter adapter1;
    ProjectsContract contract;
    ArrayList<Presentation> list;
    ArrayList<Poster> list1;
    DecimalFormat formatter = new DecimalFormat("00");
    AlertDialog dialog;
    ArrayList<Poster> posters;
    boolean isBackPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_oral_presentation_scoring);

        contract = getIntent().getParcelableExtra("data");

        this.setTitle(contract.getAuthor().toUpperCase() + "'s Presentation");
        init();

    }

    private void init() {
        bi.authorName.setText(contract.getAuthor().toUpperCase());
        bi.projectTheme.setText(contract.getTheme());
        bi.projTitle.setText(contract.getTitle());
        bi.projectID.setText(contract.getProj_id().split("_")[0] + " " + contract.getProj_id().split("_")[1]);
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
                            if (!adapter.getList().get(i).isComment) {
                                if (adapter.getList().get(i).score != -1) {
                                    list.add(adapter.getList().get(i));
                                } else {
                                    Toast.makeText(ScoringActivity.this, "Please score all criteria to submit!", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                            }
                        }
                    }
                    openDialog("submit");
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
                            if (!adapter1.getList().get(i).isComment) {
                                if (adapter1.getList().get(i).score != -1) {
                                    list1.add(adapter1.getList().get(i));
                                } else {
                                    Toast.makeText(ScoringActivity.this, "Please score all criteria to submit!", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                            }
                        }
                    }
                    openDialog("submit");
                }
            });

        }

        bi.discardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDialog("discard");
            }
        });

    }

    public void openDialog(final String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.custom_dialog_layout, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        CustomDialogLayoutBinding bi = DataBindingUtil.bind(view);
        bi.dialogText.setText("Are you sure you want to " + message + "?");

        bi.btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        bi.btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (message.equals("discard")) {
                    dialog.dismiss();
                    finish();
                } else {
                    try {
                        saveDraft();
                        if (updateDB()) {
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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

    private boolean toggleLayout(boolean isExpanded, View v, LinearLayout layoutExpand) {
        Animations.toggleArrow(v, isExpanded);
        if (isExpanded) {
            Animations.expand(layoutExpand);
        } else {
            Animations.collapse(layoutExpand);
        }
        return isExpanded;

    }

    private void saveDraft() throws JSONException {

        MainApp.fsc = new FinalScoreContract();
        MainApp.fsc.setProj_id(contract.getProj_id());
        MainApp.fsc.setType(contract.getType());
        MainApp.fsc.setDeviceid(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID));
        MainApp.fsc.setFormdate((new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime())));
        MainApp.fsc.setJudgeName(MainApp.userName);
        int score = 0;

        JSONObject object = new JSONObject();
        if (contract.getType().equals("1")) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isComment) {
                    object.put("pres_" + formatter.format(i + 1), list.get(i).comment);
                } else {
                    object.put("pres_" + formatter.format(i + 1), list.get(i).score);
                    score += list.get(i).score;
                }

            }
        } else {
            for (int i = 0; i < list1.size(); i++) {
                if (list1.get(i).isComment) {
                    object.put("pos_" + formatter.format(i + 1), list1.get(i).comment);
                } else {
                    object.put("pos_" + formatter.format(i + 1), list1.get(i).score);
                    score += list1.get(i).score;
                }

            }
        }

        MainApp.fsc.setContent(String.valueOf(object));
        MainApp.fsc.setScore(String.valueOf(score));
    }

    @Override
    public void onBackPressed() {
        openDialog("discard");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                openDialog("discard");
                break;
        }
        return true;
    }
}
