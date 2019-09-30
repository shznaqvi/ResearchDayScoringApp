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
    String fullname;
    String userType;
    String userRole;

    String proj_ids;

    public String getProj_ids() {
        return proj_ids;
    }

    public void setProj_ids(String proj_ids) {
        this.proj_ids = proj_ids;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

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

    public UsersContract Sync(JSONObject jsonObject) throws JSONException {
        this.COLUMN_USERNAME = jsonObject.getString(UsersTable.COLUMN_USERNAME);
        this.COLUMN_PASSWORD = jsonObject.getString(UsersTable.COLUMN_PASSWORD);
        this.userRole = jsonObject.getString(UsersTable.COLUMN_USER_ROLE);
        this.userType = jsonObject.getString(UsersTable.COLUMN_USER_TYPE);
        this.fullname = jsonObject.getString(UsersTable.COLUMN_FULLNAME);
        this.proj_ids = jsonObject.getString(UsersTable.COLUMN_PROJ_IDS);
        return this;

    }

    public UsersContract Hydrate(Cursor cursor) {
        this._ID = cursor.getLong(cursor.getColumnIndex(UsersTable._ID));
        this.COLUMN_USERNAME = cursor.getString(cursor.getColumnIndex(UsersTable.COLUMN_USERNAME));
        this.COLUMN_PASSWORD = cursor.getString(cursor.getColumnIndex(UsersTable.COLUMN_PASSWORD));
        return this;

    }


    public JSONObject toJSONObject() throws JSONException {

        JSONObject json = new JSONObject();
        json.put(UsersTable._ID, this._ID == null ? JSONObject.NULL : this._ID);
        json.put(UsersTable.COLUMN_USERNAME, this.COLUMN_USERNAME == null ? JSONObject.NULL : this.COLUMN_USERNAME);
        json.put(UsersTable.COLUMN_PASSWORD, this.COLUMN_PASSWORD == null ? JSONObject.NULL : this.COLUMN_PASSWORD);
        return json;
    }

    public static abstract class UsersTable implements BaseColumns {

        public static final String TABLE_NAME = "users";
        public static final String _ID = "id";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_FULLNAME = "full_name";
        public static final String COLUMN_USER_TYPE = "user_type";
        public static final String COLUMN_USER_ROLE = "user_role";
        public static final String COLUMN_PROJ_IDS = "proj_ids";


        public static final String _URI = "users.php";

    }
}