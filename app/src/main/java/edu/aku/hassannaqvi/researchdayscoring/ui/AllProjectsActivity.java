package edu.aku.hassannaqvi.researchdayscoring.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import edu.aku.hassannaqvi.researchdayscoring.R;
import edu.aku.hassannaqvi.researchdayscoring.adapter.AllProjectListAdapter;
import edu.aku.hassannaqvi.researchdayscoring.contracts.ProjectsContract;
import edu.aku.hassannaqvi.researchdayscoring.core.DatabaseHelper;
import edu.aku.hassannaqvi.researchdayscoring.core.MainApp;
import edu.aku.hassannaqvi.researchdayscoring.databinding.ActivityAllProjectsBinding;

public class AllProjectsActivity extends AppCompatActivity {

    ActivityAllProjectsBinding bi;

    DatabaseHelper db;
    List<ProjectsContract> list;
    AllProjectListAdapter adapter;
    String projType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_all_projects);


        projType = getIntent().getStringExtra("type");
        this.setTitle(projType.equals("1") ? "All Presentation" : "All Posters Presentation");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

//        init();
    }

    private void init() {

        db = new DatabaseHelper(this);
        list = db.getAllProject(MainApp.projIDs, projType);
        adapter = new AllProjectListAdapter(this, list);
        bi.allProjects.setLayoutManager(new LinearLayoutManager(this));
        bi.allProjects.setHasFixedSize(true);
        bi.allProjects.setAdapter(adapter);

        adapter.setClickListener(new AllProjectListAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(ProjectsContract contract) {

                startActivity(new Intent(AllProjectsActivity.this, ScoringActivity.class).putExtra("data", contract));

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
    }

}
