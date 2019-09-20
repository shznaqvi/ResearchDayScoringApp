package edu.aku.hassannaqvi.researchdayscoring.contracts;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hassan.naqvi on 11/30/2016.
 */

public class UsersContract {

    private static final String TAG = "Users_CONTRACT";
    Long _ID;
    String COLUMN_USERNAME;
    String COLUMN_PASSWORD;
    String COUNTRY_ID;

    public UsersContract() {
        // Default Constructor
    }

    public UsersContract(String username, String password) {
        this.COLUMN_PASSWORD = password;
        this.COLUMN_USERNAME = username;


    }

    public Long getUserID() {
        return this._ID;
    }

    public void setId(int id) {
        this._ID = Long.valueOf(id);
    }

    public String getUserName() {
        return this.COLUMN_USERNAME;
    }

    public void setUserName(String username) {
        this.COLUMN_USERNAME = username;
    }

    public String getPassword() {
        return this.COLUMN_PASSWORD;
    }

    public void setPassword(String password) {
        this.COLUMN_PASSWORD = password;
    }

    public String getCOUNTRY_ID() {
        return COUNTRY_ID;
    }

    public void setCOUNTRY_ID(String COUNTRY_ID) {
        this.COUNTRY_ID = COUNTRY_ID;
    }

    public UsersContract Sync(JSONObject jsonObject) throws JSONException {
        this.COLUMN_USERNAME = jsonObject.getString(UsersTable.COLUMN_USERNAME);
        this.COLUMN_PASSWORD = jsonObject.getString(UsersTable.COLUMN_PASSWORD);
        this.COUNTRY_ID = jsonObject.getString(UsersTable.COUNTRY_ID);
        return this;

    }

    public UsersContract Hydrate(Cursor cursor) {
        this._ID = cursor.getLong(cursor.getColumnIndex(UsersTable._ID));
        this.COLUMN_USERNAME = cursor.getString(cursor.getColumnIndex(UsersTable.COLUMN_USERNAME));
        this.COLUMN_PASSWORD = cursor.getString(cursor.getColumnIndex(UsersTable.COLUMN_PASSWORD));
        this.COUNTRY_ID = cursor.getString(cursor.getColumnIndex(UsersTable.COUNTRY_ID));
        return this;

    }


    public JSONObject toJSONObject() throws JSONException {

        JSONObject json = new JSONObject();
        json.put(UsersTable._ID, this._ID == null ? JSONObject.NULL : this._ID);
        json.put(UsersTable.COLUMN_USERNAME, this.COLUMN_USERNAME == null ? JSONObject.NULL : this.COLUMN_USERNAME);
        json.put(UsersTable.COLUMN_PASSWORD, this.COLUMN_PASSWORD == null ? JSONObject.NULL : this.COLUMN_PASSWORD);
        json.put(UsersTable.COUNTRY_ID, this.COUNTRY_ID == null ? JSONObject.NULL : this.COUNTRY_ID);
        return json;
    }

    public static abstract class UsersTable implements BaseColumns {

        public static final String TABLE_NAME = "users";
        public static final String _ID = "id";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COUNTRY_ID = "country_id";
        public static final String FULL_NAME = "full_name";


        public static final String _URI = "users.php";

    }
}