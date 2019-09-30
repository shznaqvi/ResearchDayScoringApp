package edu.aku.hassannaqvi.researchdayscoring.ui;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import java.util.List;

import edu.aku.hassannaqvi.researchdayscoring.R;
import edu.aku.hassannaqvi.researchdayscoring.adapter.AllProjectListAdapter;
import edu.aku.hassannaqvi.researchdayscoring.contracts.ProjectsContract;
import edu.aku.hassannaqvi.researchdayscoring.core.DatabaseHelper;
import edu.aku.hassannaqvi.researchdayscoring.core.MainApp;
import edu.aku.hassannaqvi.researchdayscoring.databinding.ActivityAllProjectsBinding;

public class AllPresProjectsActivity extends AppCompatActivity {

    ActivityAllProjectsBinding bi;

    DatabaseHelper db;
    List<ProjectsContract> list;
    AllProjectListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_all_projects);

        init();
    }

    private void init() {

        db = new DatabaseHelper(this);
        list = db.getAllProject(MainApp.projIDs, "1");
        adapter = new AllProjectListAdapter(this, list);
        bi.allProjects.setLayoutManager(new LinearLayoutManager(this));
        bi.allProjects.setHasFixedSize(true);
        bi.allProjects.setAdapter(adapter);


    }
}
