package edu.aku.hassannaqvi.researchdayscoring.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import edu.aku.hassannaqvi.researchdayscoring.R;
import edu.aku.hassannaqvi.researchdayscoring.adapter.PresentationScoringAdapter;
import edu.aku.hassannaqvi.researchdayscoring.contracts.ProjectsContract;
import edu.aku.hassannaqvi.researchdayscoring.core.DatabaseHelper;
import edu.aku.hassannaqvi.researchdayscoring.databinding.ActivityOralPresentationScoringBinding;
import edu.aku.hassannaqvi.researchdayscoring.databinding.DialogLayoutBinding;
import edu.aku.hassannaqvi.researchdayscoring.model.Data;
import edu.aku.hassannaqvi.researchdayscoring.model.Presentation;


public class OralPresentationScoring extends AppCompatActivity {


    private static final String TAG = "OralPresentationScoring";
    ActivityOralPresentationScoringBinding bi;
    PresentationScoringAdapter adapter;
    ProjectsContract contract;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_oral_presentation_scoring);

        OpeningDialog();

    }

    private void OpeningDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null);
        builder.setView(view);
        final DialogLayoutBinding binding = DataBindingUtil.bind(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        assert binding != null;
        binding.enterProjectIdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!binding.etProjectID.getText().toString().equals("")) {

                    String projID = binding.etProjectID.getText().toString();
                    gettingDataFromDB(projID);
                    dialog.dismiss();

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
        contract = db.getProject(projID, "2");
        if (contract.getTitle() != null) {
            init();
            bi.notFound.setVisibility(View.GONE);
        } else {
            bi.notFound.setVisibility(View.VISIBLE);
        }


    }


    private void init() {
        bi.dashboardLayout.setVisibility(View.VISIBLE);
        bi.posterContent.setVisibility(View.VISIBLE);
        bi.submitBtn.setVisibility(View.VISIBLE);
        bi.authorName.setText(contract.getAuthor());
        bi.ProjectTitle.setText(contract.getTitle());
        bi.ProjectTheme.setText(contract.getTheme());
        ArrayList<Presentation> items = new ArrayList<>();
        ArrayList<String> sectionName = new ArrayList<>();

        items = Data.getPresentationItems(this);
        for (int i = 0; i < items.size(); i++) {
            items.get(i).score = 0;
        }
        adapter = new PresentationScoringAdapter(this, items);
        bi.posterContent.setLayoutManager(new LinearLayoutManager(this));
        bi.posterContent.setHasFixedSize(true);
        bi.posterContent.setAdapter(adapter);

        bi.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

}
