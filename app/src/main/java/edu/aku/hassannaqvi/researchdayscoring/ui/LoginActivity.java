package edu.aku.hassannaqvi.researchdayscoring.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import edu.aku.hassannaqvi.researchdayscoring.R;
import edu.aku.hassannaqvi.researchdayscoring.SyncActivity;
import edu.aku.hassannaqvi.researchdayscoring.core.DatabaseHelper;
import edu.aku.hassannaqvi.researchdayscoring.core.MainApp;
import edu.aku.hassannaqvi.researchdayscoring.databinding.ActivityLoginBinding;
import edu.aku.hassannaqvi.researchdayscoring.util.Util;

import static java.lang.Thread.sleep;

public class LoginActivity extends AppCompatActivity {


    ActivityLoginBinding bi;
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "test1234:test1234", "testS12345:testS12345", "bar@example.com:world"
    };

    DatabaseHelper db;
    private UserLoginTask mAuthTask = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_login);
        bi.setCallback(this);


    }

    public void syncData() {

        startActivity(new Intent(this, SyncActivity.class));
    }


    public void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }
        bi.userName.setError(null);
        bi.passowrd.setError(null);
        String email = bi.userName.getText().toString();
        String password = bi.passowrd.getText().toString();
        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !Util.isPasswordValid(password)) {
            bi.passowrd.setError(getString(R.string.error_invalid_password));
            focusView = bi.passowrd;
            cancel = true;
        }
        if (TextUtils.isEmpty(email)) {
            bi.userName.setError(getString(R.string.error_field_required));
            focusView = bi.userName;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }


        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                // Simulate network access.
                sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }
            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                db = new DatabaseHelper(LoginActivity.this);
                if ((mEmail.equals("dmu@aku") && mPassword.equals("aku?dmu")) || db.Login(mEmail, mPassword)
                        || (mEmail.equals("test1234") && mPassword.equals("test1234"))) {
                    MainApp.userName = mEmail;
//                    MainApp.admin = mEmail.contains("@");

                    Intent iLogin = new Intent(LoginActivity.this, ResearchActivity.class);
                    startActivity(iLogin);
                    showProgress(false);

                } else {
                    bi.passowrd.setError(getString(R.string.error_incorrect_password));
                    bi.passowrd.requestFocus();
//                    Toast.makeText(LoginActivity.this, mEmail + " " + mPassword, Toast.LENGTH_SHORT).show();
                    showProgress(false);
                }
            } else {
                showProgress(false);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        LoginActivity.this);
                alertDialogBuilder
                        .setMessage("GPS is disabled in your device. Enable it?")
                        .setCancelable(false)
                        .setPositiveButton("Enable GPS",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        Intent callGPSSettingIntent = new Intent(
                                                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                        startActivity(callGPSSettingIntent);
                                    }
                                });
                alertDialogBuilder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();
            }
        }


        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    private void showProgress(boolean b) {
        if (b) {
            bi.loginBtn.setVisibility(View.GONE);
            bi.progressBar.setVisibility(View.VISIBLE);
        } else {
            bi.loginBtn.setVisibility(View.VISIBLE);
            bi.progressBar.setVisibility(View.GONE);
        }
    }


}
