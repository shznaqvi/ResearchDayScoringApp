package edu.aku.hassannaqvi.researchdayscoring.contracts;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hassan.naqvi on 11/30/2016.
 */

public class FinalScoreContract {

    private static final String TAG = "PROJECTS_CONTRACT";
    private String id;
    private String proj_id;
    private String author;
    private String theme;
    private String title;
    private String abstracts;
    private String type;
    private String score;
    private String synced;
    private String sycnedDate;
    private String deviceid;
    private String formdate;

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getFormdate() {
        return formdate;
    }

    public void setFormdate(String formdate) {
        this.formdate = formdate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String content;
    private String judgeName;

    public String getScore() {
        return score;
    }

    public String getAbstracts() {
        return abstracts;
    }

    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getJudgeName() {
        return judgeName;
    }

    public void setJudgeName(String judgeName) {
        this.judgeName = judgeName;
    }

    public String getProj_id() {
        return proj_id;
    }

    public void setProj_id(String proj_id) {
        this.proj_id = proj_id;
    }


    public FinalScoreContract() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstract() {
        return abstracts;
    }

    public void setAbstract(String abstracts) {
        this.abstracts = abstracts;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FinalScoreContract Sync(JSONObject jsonObject) throws JSONException {
        this.author = jsonObject.getString(singleColumn.COLUMN_AUTHOR);
        this.proj_id = jsonObject.getString(singleColumn.COLUMN_PROJECT_ID);
        this.theme = jsonObject.getString(singleColumn.COLUMN_THEME);
//        this.title = jsonObject.getString(singleColumn.COLUMN_TITLE);
        this.abstracts = jsonObject.getString(singleColumn.COLUMN_ABSTRACTS);
        this.type = jsonObject.getString(singleColumn.COLUMN_TYPE);
        this.content = jsonObject.getString(singleColumn.COLUMN_CONTENT);
        return this;

    }

    public FinalScoreContract Hydrate(Cursor cursor) {
        this.id = cursor.getString(cursor.getColumnIndex(singleColumn._ID));
        this.proj_id = cursor.getString(cursor.getColumnIndex(singleColumn.COLUMN_PROJECT_ID));
        this.author = cursor.getString(cursor.getColumnIndex(singleColumn.COLUMN_AUTHOR));
        this.theme = cursor.getString(cursor.getColumnIndex(singleColumn.COLUMN_THEME));
//        this.title = cursor.getString(cursor.getColumnIndex(singleColumn.COLUMN_TITLE));
        this.abstracts = cursor.getString(cursor.getColumnIndex(singleColumn.COLUMN_ABSTRACTS));
        this.type = cursor.getString(cursor.getColumnIndex(singleColumn.COLUMN_TYPE));
        this.judgeName = cursor.getString(cursor.getColumnIndex(singleColumn.COLUMN_JUDGE));
        this.score = cursor.getString(cursor.getColumnIndex(singleColumn.COLUMN_SCORE));
        this.content = cursor.getString(cursor.getColumnIndex(singleColumn.COLUMN_CONTENT));
        this.synced = cursor.getString(cursor.getColumnIndex(singleColumn.COLUMN_SYNCED));
        this.sycnedDate = cursor.getString(cursor.getColumnIndex(singleColumn.COLUMN_SYNCED_DATE));
        this.deviceid = cursor.getString(cursor.getColumnIndex(singleColumn.DEVICEID));
        this.formdate = cursor.getString(cursor.getColumnIndex(singleColumn.FORMDATE));
        return this;

    }


    public JSONObject toJSONObject() throws JSONException {

        JSONObject json = new JSONObject();
        json.put(singleColumn._ID, this.id == null ? JSONObject.NULL : this.id);
        json.put(singleColumn.COLUMN_PROJECT_ID, this.proj_id == null ? JSONObject.NULL : this.proj_id);
        json.put(singleColumn.COLUMN_AUTHOR, this.author == null ? JSONObject.NULL : this.author);
        json.put(singleColumn.COLUMN_THEME, this.theme == null ? JSONObject.NULL : this.theme);
//        json.put(singleColumn.COLUMN_TITLE, this.title == null ? JSONObject.NULL : this.title);
        json.put(singleColumn.COLUMN_ABSTRACTS, this.abstracts == null ? JSONObject.NULL : this.abstracts);
        json.put(singleColumn.COLUMN_TYPE, this.type == null ? JSONObject.NULL : this.type);
        json.put(singleColumn.COLUMN_SCORE, this.score == null ? JSONObject.NULL : this.score);
        json.put(singleColumn.COLUMN_JUDGE, this.judgeName == null ? JSONObject.NULL : this.judgeName);
        json.put(singleColumn.COLUMN_SYNCED, this.synced == null ? JSONObject.NULL : this.synced);
        json.put(singleColumn.COLUMN_SYNCED_DATE, this.sycnedDate == null ? JSONObject.NULL : this.sycnedDate);
        json.put(singleColumn.DEVICEID, this.deviceid == null ? JSONObject.NULL : this.deviceid);
        json.put(singleColumn.FORMDATE, this.formdate == null ? JSONObject.NULL : this.formdate);
        return json;
    }

    public static abstract class singleColumn implements BaseColumns {

        public static final String TABLE_NAME = "final_score";
        public static final String _ID = "_id";
        public static final String DEVICEID = "deviceid";
        public static final String FORMDATE = "formdate";
        public static final String COLUMN_PROJECT_ID = "proj_id";
        public static final String COLUMN_JUDGE = "judge";
        public static final String COLUMN_SCORE = "score";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_AUTHOR = "proj_author";
        public static final String COLUMN_THEME = "proj_theme";
        //        public static final String COLUMN_TITLE = "proj_title";
        public static final String COLUMN_ABSTRACTS = "proj_abstract";
        public static final String COLUMN_TYPE = "proj_type";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNCED_DATE = "synced_date";
        public static final String _URL1 = "posterscores.php";
        public static final String _URL2 = "presentationscores.php";

    }
}