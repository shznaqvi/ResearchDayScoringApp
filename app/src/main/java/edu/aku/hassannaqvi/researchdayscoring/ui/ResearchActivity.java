package edu.aku.hassannaqvi.researchdayscoring.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import edu.aku.hassannaqvi.researchdayscoring.R;
import edu.aku.hassannaqvi.researchdayscoring.core.AndroidDatabaseManager;

public class ResearchActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_research);


        this.setTitle("Welcome To Annual Research Day!");
        dl = findViewById(R.id.activity_research);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);


        dl.addDrawerListener(t);
        t.syncState();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        nv = findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.poster_abstracts:
                        Toast.makeText(ResearchActivity.this, "Presentation Abstracts", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.presentation_abstracts:
                        startActivity(new Intent(ResearchActivity.this, AndroidDatabaseManager.class));
                        break;
                    case R.id.poster_scores:
                        startActivity(new Intent(ResearchActivity.this, PosterScoring.class));
                        dl.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.presentation_scores:
                        startActivity(new Intent(ResearchActivity.this, OralPresentationScoring.class));
                        dl.closeDrawer(GravityCompat.START);

                        break;
                    default:
                        return true;
                }


                return true;

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}