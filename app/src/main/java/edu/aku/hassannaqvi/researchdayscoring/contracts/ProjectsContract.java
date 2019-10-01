package edu.aku.hassannaqvi.researchdayscoring.contracts;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hassan.naqvi on 11/30/2016.
 */

public class ProjectsContract implements Parcelable {

    private static final String TAG = "PROJECTS_CONTRACT";
    private String id;
    private String proj_id;
    private String author;
    private String theme;
    private String title;
    private String abstracts;
    private String type;
    private boolean isExpanded;

    public static final Creator<ProjectsContract> CREATOR = new Creator<ProjectsContract>() {
        @Override
        public ProjectsContract createFromParcel(Parcel in) {
            return new ProjectsContract(in);
        }

        @Override
        public ProjectsContract[] newArray(int size) {
            return new ProjectsContract[size];
        }
    };

    protected ProjectsContract(Parcel in) {
        id = in.readString();
        proj_id = in.readString();
        author = in.readString();
        theme = in.readString();
        title = in.readString();
        abstracts = in.readString();
        type = in.readString();
        isExpanded = in.readByte() != 0;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public String getProj_id() {
        return proj_id;
    }

    public void setProj_id(String proj_id) {
        this.proj_id = proj_id;
    }


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
        this.proj_id = jsonObject.getString(ProjectsTable.COLUMN_PROJECT_ID);
        this.theme = jsonObject.getString(ProjectsTable.COLUMN_THEME);
        this.title = jsonObject.getString(ProjectsTable.COLUMN_TITLE);
        this.abstracts = jsonObject.getString(ProjectsTable.COLUMN_ABSTRACTS);
        this.type = jsonObject.getString(ProjectsTable.COLUMN_TYPE);
        return this;

    }

    public ProjectsContract Hydrate(Cursor cursor) {
        this.proj_id = cursor.getString(cursor.getColumnIndex(ProjectsTable.COLUMN_PROJECT_ID));
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
        json.put(ProjectsTable.COLUMN_PROJECT_ID, this.proj_id == null ? JSONObject.NULL : this.proj_id);
        json.put(ProjectsTable.COLUMN_AUTHOR, this.author == null ? JSONObject.NULL : this.author);
        json.put(ProjectsTable.COLUMN_THEME, this.theme == null ? JSONObject.NULL : this.theme);
        json.put(ProjectsTable.COLUMN_TITLE, this.title == null ? JSONObject.NULL : this.title);
        json.put(ProjectsTable.COLUMN_ABSTRACTS, this.abstracts == null ? JSONObject.NULL : this.abstracts);
        json.put(ProjectsTable.COLUMN_TYPE, this.type == null ? JSONObject.NULL : this.type);
        return json;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(proj_id);
        dest.writeString(author);
        dest.writeString(theme);
        dest.writeString(title);
        dest.writeString(abstracts);
        dest.writeString(type);
        dest.writeByte((byte) (isExpanded ? 1 : 0));
    }

    public static abstract class ProjectsTable implements BaseColumns {

        public static final String TABLE_NAME = "projects_table";
        public static final String _ID = "id";
        public static final String COLUMN_PROJECT_ID = "proj_id";
        public static final String COLUMN_AUTHOR = "proj_author";
        public static final String COLUMN_THEME = "proj_theme";
        public static final String COLUMN_TITLE = "proj_title";
        public static final String COLUMN_ABSTRACTS = "proj_abstract";
        public static final String COLUMN_TYPE = "proj_type";
        public static final String _URI = "projects.php";

    }
}