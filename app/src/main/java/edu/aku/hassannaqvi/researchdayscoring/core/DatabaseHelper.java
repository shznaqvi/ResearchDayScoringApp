package edu.aku.hassannaqvi.researchdayscoring.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import edu.aku.hassannaqvi.researchdayscoring.contracts.FinalScoreContract;
import edu.aku.hassannaqvi.researchdayscoring.contracts.FinalScoreContract.singleColumn;
import edu.aku.hassannaqvi.researchdayscoring.contracts.FormsContract;
import edu.aku.hassannaqvi.researchdayscoring.contracts.FormsContract.FormsTable;
import edu.aku.hassannaqvi.researchdayscoring.contracts.ProjectsContract;
import edu.aku.hassannaqvi.researchdayscoring.contracts.ProjectsContract.ProjectsTable;
import edu.aku.hassannaqvi.researchdayscoring.contracts.UsersContract;
import edu.aku.hassannaqvi.researchdayscoring.contracts.UsersContract.UsersTable;

import static edu.aku.hassannaqvi.researchdayscoring.core.MainApp.fc;


/**
 * Created by hassan.naqvi on 11/30/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "researchdayscoring.db";
    public static final String DB_NAME = DATABASE_NAME.replace(".", "_copy.");
    public static final String PROJECT_NAME = "researchdayscoring";
    private static final int DATABASE_VERSION = 1;

    public static final String SQL_CREATE_USERS = "CREATE TABLE " + UsersTable.TABLE_NAME + "("
            + UsersTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + UsersTable.COLUMN_USERNAME + " TEXT,"
            + UsersTable.COLUMN_PASSWORD + " TEXT,"
            + UsersTable.COLUMN_FULLNAME + " TEXT,"
            + UsersTable.COLUMN_USER_ROLE + " TEXT,"
            + UsersTable.COLUMN_USER_TYPE + " TEXT"
            + " );";
    public static final String SQL_CREATE_PROJECTS = "CREATE TABLE " + ProjectsTable.TABLE_NAME + "("
            + ProjectsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + ProjectsTable.COLUMN_PROJECT_ID + " TEXT,"
            + ProjectsTable.COLUMN_TITLE + " TEXT,"
            + ProjectsTable.COLUMN_AUTHOR + " TEXT,"
            + ProjectsTable.COLUMN_THEME + " TEXT,"
            + ProjectsTable.COLUMN_TYPE + " TEXT,"
            + ProjectsTable.COLUMN_ABSTRACTS + " TEXT"
            + " );";
    public static final String SQL_CREATE_FINAL_SCORE = "CREATE TABLE " + singleColumn.TABLE_NAME + "("
            + singleColumn._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + singleColumn.COLUMN_PROJECT_ID + " TEXT,"
            + singleColumn.COLUMN_TITLE + " TEXT,"
            + singleColumn.COLUMN_AUTHOR + " TEXT,"
            + singleColumn.COLUMN_THEME + " TEXT,"
            + singleColumn.COLUMN_TYPE + " TEXT,"
            + singleColumn.COLUMN_ABSTRACTS + " TEXT,"
            + singleColumn.COLUMN_JUDGE + " TEXT,"
            + singleColumn.COLUMN_JUDGE + " TEXT,"
            + singleColumn.COLUMN_CONTENT + " TEXT,"
            + singleColumn.COLUMN_SCORE + " TEXT"
            + " );";


//    private static final String SQL_CREATE_FORMS = "CREATE TABLE "
//            + FormsTable.TABLE_NAME + "("
//            + FormsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
//            FormsTable.COLUMN_PROJECTNAME + " TEXT," +
//            FormsTable.COLUMN_DEVICEID + " TEXT," +
//            FormsTable.COLUMN_DEVICETAGID + " TEXT," +
//            FormsTable.COLUMN_APPVERSION + " TEXT," +
//            FormsTable.COLUMN_FORMTYPE + " TEXT," +
//            FormsTable.COLUMN_SURVEYTYPE + " TEXT," +
//            FormsTable.COLUMN_FORMDATE + " TEXT," +
//            FormsTable.COLUMN__UID + " TEXT," +
//            FormsTable.COLUMN_USER + " TEXT," +
//            FormsTable.COLUMN_GPSLAT + " TEXT," +
//            FormsTable.COLUMN_GPSLNG + " TEXT," +
//            FormsTable.COLUMN_GPSDT + " TEXT," +
//            FormsTable.COLUMN_GPSACC + " TEXT," +
//            FormsTable.COLUMN_GPSALTITUDE + " TEXT," +
//            FormsTable.COLUMN_PWID + " TEXT," +
//            FormsTable.COLUMN_SCREENID + " TEXT," +
//            FormsTable.COLUMN_TALUKA + " TEXT," +
//            FormsTable.COLUMN_UC + " TEXT," +
//            FormsTable.COLUMN_VILLAGE + " TEXT," +
//            FormsTable.COLUMN_SINFO + " TEXT," +
//            FormsTable.COLUMN_SA + " TEXT," +
//            FormsTable.COLUMN_SB + " TEXT," +
//            FormsTable.COLUMN_SC + " TEXT," +
//            FormsTable.COLUMN_SD + " TEXT," +
//            FormsTable.COLUMN_SE + " TEXT," +
//            FormsTable.COLUMN_SF + " TEXT," +
//            FormsTable.COLUMN_ENDINGDATETIME + " TEXT," +
//            FormsTable.COLUMN_ISTATUS + " TEXT," +
//            FormsTable.COLUMN_ISTATUS88X + " TEXT," +
//            FormsTable.COLUMN_SYNCED + " TEXT," +
//            FormsTable.COLUMN_SYNCED_DATE + " TEXT"
//            + " );";
//
//    private static final String SQL_ALTER_FORMS = "ALTER TABLE " +
//            FormsTable.TABLE_NAME + " ADD COLUMN " +
//            FormsTable.COLUMN_SCREENID + " TEXT;";


    //    private static final String SQL_DELETE_USERS = "DROP TABLE IF EXISTS " + UsersContract.UsersTable.TABLE_NAME;
//    private static final String SQL_DELETE_USERS = "DROP TABLE IF EXISTS " + UsersContract.UsersTable.TABLE_NAME;
    private static final String SQL_DELETE_FORMS = "DROP TABLE IF EXISTS " + FormsTable.TABLE_NAME;
    private static final String SQL_DELETE_USERS = "DROP TABLE IF EXISTS " + UsersTable.TABLE_NAME;
    private static final String SQL_DELETE_PROJECTS = "DROP TABLE IF EXISTS " + ProjectsTable.TABLE_NAME;
    private static final String SQL_DELETE_FINAL_SCORE = "DROP TABLE IF EXISTS " + singleColumn.TABLE_NAME;

    private final String TAG = "DatabaseHelper";
    public String spDateT = new SimpleDateFormat("dd-MM-yy").format(new Date().getTime());

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_USERS);
        db.execSQL(SQL_CREATE_PROJECTS);
        db.execSQL(SQL_CREATE_FINAL_SCORE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        /*db.execSQL(SQL_DELETE_USERS);
        db.execSQL(SQL_DELETE_FORMS);
        db.execSQL(SQL_DELETE_TALUKA);
        db.execSQL(SQL_DELETE_UCS);
        db.execSQL(SQL_DELETE_VILLAGE);
        db.execSQL(SQL_DELETE_MWRA);
        db.execSQL(SQL_DELETE_MWRASCREENED);*/

        db.execSQL(SQL_DELETE_USERS);
        db.execSQL(SQL_DELETE_PROJECTS);
        db.execSQL(SQL_DELETE_FINAL_SCORE);

    }


    public boolean Login(String username, String password) throws SQLException {

        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        String[] columns = {
                UsersTable._ID
        };

// Which row to update, based on the ID
        String selection = UsersContract.UsersTable.COLUMN_USERNAME + " = ?" + " AND " + UsersContract.UsersTable.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(UsersContract.UsersTable.TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        return cursorCount > 0;
    }

    public Long addForm(FinalScoreContract fc) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(singleColumn.COLUMN_ABSTRACTS, fc.getAbstract());
        values.put(singleColumn.COLUMN_AUTHOR, fc.getAuthor());
        values.put(singleColumn.COLUMN_JUDGE, fc.getJudgeName());
        values.put(singleColumn.COLUMN_PROJECT_ID, fc.getProj_id());
        values.put(singleColumn.COLUMN_SCORE, fc.getScore());
        values.put(singleColumn.COLUMN_THEME, fc.getTheme());
        values.put(singleColumn.COLUMN_TITLE, fc.getTitle());
        values.put(singleColumn.COLUMN_TYPE, fc.getType());
        values.put(singleColumn.COLUMN_CONTENT, fc.getContent());


        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                singleColumn.TABLE_NAME,
                null,
                values);
        return newRowId;
    }

    public void updateSyncedForms(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_SYNCED, true);
        values.put(FormsTable.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = FormsTable._ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                FormsTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    public int updateFormID() {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN__UID, fc.getUID());

// Which row to update, based on the ID
        String selection = FormsTable.COLUMN__ID + " = ?";
        String[] selectionArgs = {String.valueOf(fc.get_ID())};

        int count = db.update(FormsTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }

    public Collection<FormsContract> getTodayForms() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                //ChildTable.COLUMN_DSSID,
                FormsTable.COLUMN_FORMDATE,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_SYNCED,

        };
        String whereClause = FormsTable.COLUMN_FORMDATE + " Like ? ";
        String[] whereArgs = new String[]{"%" + spDateT.substring(0, 8).trim() + "%"};
        String groupBy = null;
        String having = null;

        String orderBy =
                FormsTable._ID + " ASC";

        Collection<FormsContract> allFC = new ArrayList<>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                FormsContract fc = new FormsContract();
                fc.set_ID(c.getString(c.getColumnIndex(FormsTable._ID)));
                fc.setFormDate(c.getString(c.getColumnIndex(FormsTable.COLUMN_FORMDATE)));
                fc.setIstatus(c.getString(c.getColumnIndex(FormsTable.COLUMN_ISTATUS)));
                fc.setSynced(c.getString(c.getColumnIndex(FormsTable.COLUMN_SYNCED)));
                allFC.add(fc);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;
    }

    public void syncUser(JSONArray userlist) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(UsersTable.TABLE_NAME, null, null);
        try {
            JSONArray jsonArray = userlist;
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObjectUser = jsonArray.getJSONObject(i);

                UsersContract user = new UsersContract();
                user.Sync(jsonObjectUser);
                ContentValues values = new ContentValues();

                values.put(UsersTable.COLUMN_USERNAME, user.getUserName());
                values.put(UsersTable.COLUMN_PASSWORD, user.getPassword());
                values.put(UsersTable.COLUMN_FULLNAME, user.getFullname());
                values.put(UsersTable.COLUMN_USER_ROLE, user.getUserRole());
                values.put(UsersTable.COLUMN_USER_TYPE, user.getUserType());
                db.insert(UsersTable.TABLE_NAME, null, values);
            }


        } catch (Exception e) {
            Log.d(TAG, "syncUser(e): " + e);
        } finally {
            db.close();
        }
    }

    public void syncProjects(JSONArray projectsList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ProjectsTable.TABLE_NAME, null, null);
        try {
            JSONArray jsonArray = projectsList;
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObjectUser = jsonArray.getJSONObject(i);

                ProjectsContract proj = new ProjectsContract();
                proj.Sync(jsonObjectUser);
                ContentValues values = new ContentValues();

                values.put(ProjectsTable.COLUMN_PROJECT_ID, proj.getProj_id());
                values.put(ProjectsTable.COLUMN_ABSTRACTS, proj.getAbstract());
                values.put(ProjectsTable.COLUMN_AUTHOR, proj.getAuthor());
                values.put(ProjectsTable.COLUMN_THEME, proj.getTheme());
                values.put(ProjectsTable.COLUMN_TITLE, proj.getTitle());
                values.put(ProjectsTable.COLUMN_TYPE, proj.getType());
                db.insert(ProjectsTable.TABLE_NAME, null, values);
            }


        } catch (Exception e) {
            Log.d(TAG, "syncUser(e): " + e);
        } finally {
            db.close();
        }
    }

    // ANDROID DATABASE MANAGER
    public ArrayList<Cursor> getData(String Query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[]{"message"};
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2 = new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try {
            String maxQuery = Query;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[]{"Success"});

            alc.set(1, Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0, c);
                c.moveToFirst();

                return alc;
            }
            return alc;
        } catch (SQLException sqlEx) {
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + sqlEx.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        } catch (Exception ex) {

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + ex.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        }
    }


    public ProjectsContract getProject(String id, String type) {
//        List<ProjectsContract> formList = new ArrayList<>();
        ProjectsContract pc = new ProjectsContract();

        String[] columns = {
                ProjectsTable.COLUMN_ABSTRACTS,
                ProjectsTable.COLUMN_THEME,
                ProjectsTable.COLUMN_AUTHOR,
                ProjectsTable.COLUMN_TITLE,
                ProjectsTable.COLUMN_TYPE,
                ProjectsTable.COLUMN_PROJECT_ID,

        };
        String selection = ProjectsTable.COLUMN_PROJECT_ID + " = ? AND " + ProjectsTable.COLUMN_TYPE + " = ?";
        String[] selectionArgs = {String.valueOf(id), type};

//        String orderBy =
//                singleVillage.COLUMN_VILLAGE_NAME + " COLLATE NOCASE ASC;";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(
                ProjectsTable.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        if (c.moveToFirst()) {
            do {
                pc.Hydrate(c);
            } while (c.moveToNext());
        }
        return pc;
    }
}