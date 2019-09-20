package edu.aku.hassannaqvi.researchdayscoring;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import edu.aku.hassannaqvi.researchdayscoring.ui.ResearchActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void StartJudge(View view) {
        Intent i = new Intent(this, ResearchActivity.class);

        startActivity(i);

    }
}
