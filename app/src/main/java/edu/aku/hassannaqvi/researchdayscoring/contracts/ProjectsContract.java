package edu.aku.hassannaqvi.researchdayscoring.contracts;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hassan.naqvi on 11/30/2016.
 */

public class ProjectsContract {

    private static final String TAG = "PROJECTS_CONTRACT";
    private String id;
    private String author;
    private String theme;
    private String title;
    private String abstracts;
    private String type;

    public ProjectsContract() {
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

    public ProjectsContract Sync(JSONObject jsonObject) throws JSONException {
        this.author = jsonObject.getString(ProjectsTable.COLUMN_AUTHOR);
        this.theme = jsonObject.getString(ProjectsTable.COLUMN_THEME);
        this.title = jsonObject.getString(ProjectsTable.COLUMN_TITLE);
        this.abstracts = jsonObject.getString(ProjectsTable.COLUMN_ABSTRACTS);
        this.type = jsonObject.getString(ProjectsTable.COLUMN_TYPE);
        return this;

    }

    public ProjectsContract Hydrate(Cursor cursor) {
        this.id = cursor.getString(cursor.getColumnIndex(ProjectsTable._ID));
        this.author = cursor.getString(cursor.getColumnIndex(ProjectsTable.COLUMN_AUTHOR));
        this.theme = cursor.getString(cursor.getColumnIndex(ProjectsTable.COLUMN_THEME));
        this.title = cursor.getString(cursor.getColumnIndex(ProjectsTable.COLUMN_TITLE));
        this.abstracts = cursor.getString(cursor.getColumnIndex(ProjectsTable.COLUMN_ABSTRACTS));
        this.type = cursor.getString(cursor.getColumnIndex(ProjectsTable.COLUMN_TYPE));
        return this;

    }


    public JSONObject toJSONObject() throws JSONException {

        JSONObject json = new JSONObject();
        json.put(ProjectsTable._ID, this.id == null ? JSONObject.NULL : this.id);
        json.put(ProjectsTable.COLUMN_AUTHOR, this.author == null ? JSONObject.NULL : this.author);
        json.put(ProjectsTable.COLUMN_THEME, this.theme == null ? JSONObject.NULL : this.theme);
        json.put(ProjectsTable.COLUMN_TITLE, this.title == null ? JSONObject.NULL : this.title);
        json.put(ProjectsTable.COLUMN_ABSTRACTS, this.abstracts == null ? JSONObject.NULL : this.abstracts);
        json.put(ProjectsTable.COLUMN_TYPE, this.type == null ? JSONObject.NULL : this.type);
        return json;
    }

    public static abstract class ProjectsTable implements BaseColumns {

        public static final String TABLE_NAME = "users";
        public static final String _ID = "id";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_THEME = "theme";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_ABSTRACTS = "abstracts";
        public static final String COLUMN_TYPE = "type";


        public static final String _URI = "projects.php";

    }
}