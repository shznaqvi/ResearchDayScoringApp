package edu.aku.hassannaqvi.researchdayscoring.core;

import android.app.Application;

import edu.aku.hassannaqvi.researchdayscoring.contracts.FinalScoreContract;

/**
 * Created by hassan.naqvi on 11/30/2016.
 */

public class MainApp extends Application {

    public static final String _IP = "43.245.131.159"; // Test PHP server
    public static final Integer _PORT = 8080; // Port - with colon (:)
    public static final String _HOST_URL_1 = "http://" + MainApp._IP + ":" + MainApp._PORT + "/researchday/api/";
    public static FinalScoreContract fsc;
    public static String userName = "0000";


}