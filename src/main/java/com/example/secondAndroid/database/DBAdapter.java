// ------------------------------------ DBADapter.java ---------------------------------------------

// TODO: Change the package to match your project.
package com.example.secondAndroid.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.security.Timestamp;


// TO USE:
// Change the package (at top) to match your project.
// Search for "TODO", and make the appropriate changes.
public class DBAdapter {

    /////////////////////////////////////////////////////////////////////
    //	Constants & Data
    /////////////////////////////////////////////////////////////////////
    // For logging:
    private static final String TAG = "DBAdapter";

    // DB Fields
    public static final String KEY_ROWID = "_id";
    public static final String KEY_ROWID_SERVICES = "_id";
    public static final String KEY_ROWID_DEPLOYMENT = "_id";
    public static final String KEY_ROWID_SITE = "_id";
    public static final String KEY_ROWID_SETTINGS = "_id";
    public static final String KEY_ROWID_GEOPLOT = "_id";

    //public static final String KEY_ROWID_1 = "_id";
    public static final int COL_ROWID = 0;
    public static final int COL_PUMP_OUT = 1;
    //public static final int COL_ROWID_1 = 0;
    /*
     * CHANGE 1:
     */
    // TODO: Setup your fields here:
    public static final String KEY_NAME = "name";
    public static final String KEY_UNIT_ID = "unit_id";
    public static final String KEY_PUMP_OUT = "pump_out";
    public static final String KEY_WASH_BUCKET = "wash_bucket";
    public static final String KEY_SUCTION_OUT = "suction_out";
    public static final String KEY_RECHARGE_BUCKET = "recharge_bucket";
    public static final String KEY_SCRUB_FLOOR = "scrub_floor";
    public static final String KEY_CLEAN_PERIMETER = "clean_perimeter";
    public static final String KEY_REPORT_INCIDENT = "report_incident";
    public static final String KEY_DATE = "date";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGITUDE = "longitude";
    public static final String KEY_SITE = "site";
    public static final String KEY_SITE_ID = "site_id";
    public static final String KEY_SITE_NAME = "site_name";
    public static final String KEY_URL = "url";




    // TODO: Setup your field numbers here (0 = KEY_ROWID, 1=...)
    public static final int COL_NAME = 1;
    public static final int COL_SITE_NAME= 2;


    public static final String[] ALL_KEYS = new String[] {KEY_UNIT_ID};
    public static final String[] ALL_KEYS_SERVICES = new String[] {KEY_ROWID_SERVICES,KEY_UNIT_ID,KEY_PUMP_OUT,KEY_WASH_BUCKET,KEY_SUCTION_OUT,KEY_RECHARGE_BUCKET,KEY_SCRUB_FLOOR,KEY_CLEAN_PERIMETER,KEY_REPORT_INCIDENT,KEY_LATITUDE,KEY_LONGITUDE,KEY_DATE};
    public static final String[] ALL_KEYS_DEPLOYMENT = new String[] {KEY_ROWID_DEPLOYMENT,KEY_UNIT_ID,KEY_LATITUDE,KEY_LONGITUDE,KEY_SITE,KEY_DATE};
    public static final String[] ALL_KEYS_SITE = new String[] {KEY_ROWID_SITE,KEY_SITE_ID,KEY_SITE_NAME};
    public static final String[] ALL_KEYS_SETTINGS = new String[] {KEY_ROWID_SETTINGS,KEY_URL};
    public static final String[] ALL_KEYS_GEOPLOT = new String[] {KEY_ROWID_GEOPLOT,KEY_SITE_NAME,KEY_LATITUDE,KEY_LONGITUDE,KEY_DATE};

    // DB info: it's name, and the table we are using (just one).
    public static final String DATABASE_NAME = "mshengu";
    public static final String DATABASE_TABLE = "Table1";
    public static final String DATABASE_TABLE_SERVICES = "services";
    public static final String DATABASE_TABLE_DEPLOYMENT = "deployment";
    public static final String DATABASE_TABLE_SITE = "site";
    public static final String DATABASE_TABLE_SETTINGS = "settings";
    public static final String DATABASE_TABLE_GEOPLOT = "geoplot";

    // Track DB version if a new version of your app changes the format.
    public static final int DATABASE_VERSION =12;

    private static final String DATABASE_CREATE_SQL =
            "create table " + DATABASE_TABLE
                    + " (" + KEY_ROWID + " integer primary key autoincrement, "
			
			/*
			 * CHANGE 2:
			 */
                    // TODO: Place your fields here!
                    // + KEY_{...} + " {type} not null"
                    //	- Key is the column name you created above.
                    //	- {type} is one of: text, integer, real, blob
                    //		(http://www.sqlite.org/datatype3.html)
                    //  - "not null" means it is a required field (must be given a value).
                    // NOTE: All must be comma separated (end of line!) Last one must have NO comma!!
                    + KEY_NAME + " text not null "

                    // Rest  of creation:
                    + ");";


    private static final String DATABASE_CREATE_SQL_SERVICES =
            "create table " + DATABASE_TABLE_SERVICES
                    + " (" + KEY_ROWID_SERVICES + " integer primary key autoincrement, "

			/*
			 * CHANGE 2:
			 */
                    // TODO: Place your fields here!
                    + KEY_UNIT_ID + " text, "
                    + KEY_PUMP_OUT + " text, "
                    + KEY_WASH_BUCKET + " text, "
                    + KEY_SUCTION_OUT + " text, "
                    + KEY_RECHARGE_BUCKET + " text, "
                    + KEY_SCRUB_FLOOR + " text, "
                    + KEY_CLEAN_PERIMETER + " text, "
                    + KEY_REPORT_INCIDENT + " text, "
                    + KEY_LATITUDE + " text, "
                    + KEY_LONGITUDE + " text, "
                    + KEY_DATE + " text "
                    // Rest  of creation:
                    + ");";


    private static final String DATABASE_CREATE_SQL_DEPLOYMENT =
            "create table " + DATABASE_TABLE_DEPLOYMENT
                    + " (" + KEY_ROWID_DEPLOYMENT + " integer primary key autoincrement, "

			/*
			 * CHANGE 2:
			 */
                    // TODO: Place your fields here!
                    + KEY_UNIT_ID + " text, "
                    + KEY_LATITUDE + " text, "
                    + KEY_LONGITUDE + " text, "
                    + KEY_SITE + " text, "
                    + KEY_DATE + " text "
                    // Rest  of creation:
                    + ");";


    private static final String DATABASE_CREATE_SQL_SITE =
            "create table " + DATABASE_TABLE_SITE
                    + " (" + KEY_ROWID_SITE + " integer primary key autoincrement, "

			/*
			 * CHANGE 2:
			 */
                    // TODO: Place your fields here!
                    + KEY_SITE_ID + " text, "
                    + KEY_SITE_NAME + " text "
                    // Rest  of creation:
                    + ");";



    private static final String DATABASE_CREATE_SQL_SETTINGS =
            "create table " + DATABASE_TABLE_SETTINGS
                    + " (" + KEY_ROWID_SETTINGS + " integer primary key autoincrement, "
                 + KEY_URL + " text "
                    + ");";



    private static final String DATABASE_CREATE_SQL_GEOPLOT =
            "create table " + DATABASE_TABLE_GEOPLOT
                    + " (" + KEY_ROWID_GEOPLOT + " integer primary key autoincrement, "
                    + KEY_SITE_NAME + " text, "
                    + KEY_LATITUDE + " text, "
                    + KEY_LONGITUDE + " text, "
                    + KEY_DATE + " text "
                    + ");";


    // Context of application who uses us.
    private final Context context;

    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    /////////////////////////////////////////////////////////////////////
    //	Public methods:
    /////////////////////////////////////////////////////////////////////

    public DBAdapter(Context ctx) {
        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);
    }

    // Open the database connection.
    public DBAdapter open() {
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    // Close the database connection.
    public void close() {
        myDBHelper.close();
    }

    // Add a new set of values to the database.
    public long insertRowInTable1(String name) {
		/*
		 * CHANGE 3:
		 */
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);

        // Insert it into the database.
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    // Add a new set of values to the table settings.
    public long insertRowInTableSettings(String name) {
		/*
		 * CHANGE 3:
		 */
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_URL, name);

        // Insert it into the database.
        return db.insert(DATABASE_TABLE_SETTINGS, null, initialValues);
    }


    // Add a new set of values to the table Services
    public long insertRowInTableServices(String unit_id, String pump_out, String wash_bucket, String suction_out, String recharge_bucket, String scrub_floor, String clean_perimeter, String report_incident, String latitude, String longitude, String date) {
		/*
		 * CHANGE 3:
		 */
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues initialValues = new ContentValues();

        initialValues.put(KEY_UNIT_ID, unit_id);
        initialValues.put(KEY_PUMP_OUT, pump_out);
        initialValues.put(KEY_WASH_BUCKET, wash_bucket);
        initialValues.put(KEY_SUCTION_OUT,suction_out);
        initialValues.put(KEY_RECHARGE_BUCKET, recharge_bucket);
        initialValues.put(KEY_SCRUB_FLOOR, scrub_floor);
        initialValues.put(KEY_CLEAN_PERIMETER, clean_perimeter);
        initialValues.put(KEY_REPORT_INCIDENT, report_incident);
        initialValues.put(KEY_LATITUDE, latitude);
        initialValues.put(KEY_LONGITUDE, longitude);
        initialValues.put(KEY_DATE, date);
        // Insert it into the database.
        return db.insert(DATABASE_TABLE_SERVICES, null, initialValues);
    }


    // Add a new set of values to the table Deployment
    public long insertRowInTableDeployment(String unit_id,String latitude, String longitude, String site , String date) {
		/*
		 * CHANGE 3:
		 */
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues initialValues = new ContentValues();

        initialValues.put(KEY_UNIT_ID, unit_id);
        initialValues.put(KEY_LATITUDE, latitude);
        initialValues.put(KEY_LONGITUDE, longitude);
        initialValues.put(KEY_SITE,site);
        initialValues.put(KEY_DATE, date);
        // Insert it into the database.
        return db.insert(DATABASE_TABLE_DEPLOYMENT, null, initialValues);
    }


    // Add a new set of values to the table Site
    public long insertRowInTableSite(String site_id,String site_name) {
		/*
		 * CHANGE 3:
		 */
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues initialValues = new ContentValues();

        initialValues.put(KEY_SITE_ID, site_id);
        initialValues.put(KEY_SITE_NAME, site_name);
        // Insert it into the database.
        return db.insert(DATABASE_TABLE_SITE, null, initialValues);
    }

    // Add a new set of values to the table Geo Plot
    public long insertRowInTableGeoPlot(String site_name,String latitude, String longitude, String date) {
		ContentValues initialValues = new ContentValues();


        initialValues.put(KEY_SITE_NAME, site_name);
        initialValues.put(KEY_LATITUDE,latitude);
        initialValues.put(KEY_LONGITUDE,longitude);
        initialValues.put(KEY_DATE,date);

        // Insert it into the database.
        return db.insert(DATABASE_TABLE_GEOPLOT, null, initialValues);
    }


    // Delete a row from the database, by rowId (primary key)
    public boolean deleteRowFromTable1(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return db.delete(DATABASE_TABLE, where, null) != 0;
    }


    // Delete a row from the database, by rowId (primary key)
    public boolean deleteRowFromTableSettings(long rowId) {
        String where = KEY_ROWID_SETTINGS + "=" + rowId;
        return db.delete(DATABASE_TABLE_SETTINGS, where, null) != 0;
    }

    // Delete a row from Table Services, by unit_id and timestamp
    public boolean deleteRowFromTableServices(long rowId, String unit_id, String date) {
        String where;
        if(unit_id.equals("all".toString()) && date.equals("all".toString()))
        {
            where = KEY_ROWID_SERVICES + "=" + rowId;
        }
        else {   //KEY_UNIT_ID + "=" + unit_id + " AND " +
            where = KEY_DATE + "='" + date+"'";
        }
        // Toast.makeText(context,where,Toast.LENGTH_LONG).show();
        // return false;
        return db.delete(DATABASE_TABLE_SERVICES, where, null) != 0;
    }

    // Delete a row from Table Services, by unit_id and timestamp
    public boolean deleteRowFromTableGeoPlot(String date) {
        String where;
        where = KEY_DATE + "='" + date +"'";
        return db.delete(DATABASE_TABLE_GEOPLOT, where, null) != 0;
    }

    // Delete a row from Table Deployment, by unit_id and timestamp
    public boolean deleteRowFromTableDeployment(long rowId, String unit_id, String date) {
        String where;
        if(unit_id.equals("all".toString()) && date.equals("all".toString()))
        {
            where = KEY_ROWID_DEPLOYMENT + "=" + rowId;
        }
        else {   //KEY_UNIT_ID + "=" + unit_id + " AND " +
            where = KEY_DATE + "='" + date+"'";
        }
        // Toast.makeText(context,where,Toast.LENGTH_LONG).show();
        // return false;
        return db.delete(DATABASE_TABLE_DEPLOYMENT, where, null) != 0;
    }

    // Delete a row from Table Deployment, by unit_id and timestamp
    public boolean deleteRowFromTableSite(long rowId) {
        String where;

        where = KEY_ROWID_DEPLOYMENT + "=" + rowId;
        // Toast.makeText(context,where,Toast.LENGTH_LONG).show();
        // return false;
        return db.delete(DATABASE_TABLE_SITE, where, null) != 0;
    }



    public void deleteAll() {
        Cursor c = getAllRowsFromTableServices();
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
        if (c.moveToFirst()) {
            do {
                deleteRowFromTable1(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }


    public void deleteAllFromTableSettings() {
        Cursor c = getAllRowsFromTableSettings();
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID_SETTINGS);
        if (c.moveToFirst()) {
            do {
                deleteRowFromTableSettings(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }




    public void deleteAllFromTableServices() {
        Cursor c = getAllRowsFromTableServices();
        String all="all";
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID_SERVICES);
        if (c.moveToFirst()) {
            do {
                deleteRowFromTableServices(c.getLong((int) rowId),all,all);

            } while (c.moveToNext());
        }
        c.close();
    }



    public void deleteAllFromTableDeployment() {
        Cursor c = getAllRowsFromTableDeployment();
        String all="all";
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID_DEPLOYMENT);
        if (c.moveToFirst()) {
            do {
                deleteRowFromTableDeployment(c.getLong((int) rowId),all,all);

            } while (c.moveToNext());
        }
        c.close();
    }


    public void deleteAllFromTableSite() {
        Cursor c = getAllRowsFromTableSite();

        long rowId = c.getColumnIndexOrThrow(KEY_ROWID_SITE);
        if (c.moveToFirst()) {
            do {
                deleteRowFromTableSite(c.getLong((int) rowId));

            } while (c.moveToNext());
        }
        c.close();
    }


    // Return all data in the database.
    public Cursor getAllRows() {
        String where = null;
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }


    // Return all data in the database.
    public Cursor getAllRowsFromTableSettings() {

        String where = null;
        Cursor c = 	db.query(true, DATABASE_TABLE_SETTINGS, ALL_KEYS_SETTINGS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Return all data in the Table services
    public Cursor getAllRowsFromTableServices() {
        String where = null;
        Cursor c;

        c = 	db.query(DATABASE_TABLE_SERVICES,ALL_KEYS_SERVICES,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getAllRowsFromTableGeoPlot() {
        String where = null;
        Cursor c;

        c = 	db.query(DATABASE_TABLE_GEOPLOT,ALL_KEYS_GEOPLOT,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }


    // Return all data in the Table deployment
    public Cursor getAllRowsFromTableDeployment() {
        String where = null;

        Cursor c;

        c = 	db.query(DATABASE_TABLE_DEPLOYMENT,ALL_KEYS_DEPLOYMENT,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }



    // Return all data in the Table site
    public Cursor getAllRowsFromTableSite() {
        String where = null;
        Cursor c;

        c = 	db.query(DATABASE_TABLE_SITE,ALL_KEYS_SITE,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Get a specific row (by rowId)
    public Cursor getRowFromTable1(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }


    // Get a specific row (by rowId)
    public Cursor getRowFromTableSettings(int rowId) {
        String where = KEY_ROWID_SETTINGS + "=" + rowId;
        Cursor c = 	db.query(true, DATABASE_TABLE_SETTINGS, ALL_KEYS_SETTINGS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }


    // Get a specific row (by unit_id)
    public Cursor getRowFromTableServices(String unit_id, String date) {
        String where = KEY_UNIT_ID + "=" + unit_id+ " AND "+KEY_DATE + "="+date;
        Cursor c = 	db.query(true, DATABASE_TABLE_SERVICES, ALL_KEYS_SERVICES,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Change an existing row to be equal to new data.
    public boolean updateRowInTable1(long rowId, String name, int studentNum, String favColour) {
        String where = KEY_ROWID + "=" + rowId;

		/*
		 * CHANGE 4:
		 */
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_NAME, name);

        // Insert it into the database.
        return db.update(DATABASE_TABLE, newValues, where, null) != 0;
    }


    // Change an existing row to be equal to new data.
    public boolean updateRowInTableSettings(long rowId, String url) {
        String where = KEY_ROWID + "=" + rowId;

		/*
		 * CHANGE 4:
		 */
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_URL, url);

        // Insert it into the database.
        return db.update(DATABASE_TABLE_SETTINGS, newValues, where, null) != 0;
    }



    /////////////////////////////////////////////////////////////////////
    //	Private Helper Classes:
    /////////////////////////////////////////////////////////////////////

    /**
     * Private class which handles database creation and upgrading.
     * Used to handle low-level database access.
     */
    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DATABASE_CREATE_SQL_SERVICES);
            _db.execSQL(DATABASE_CREATE_SQL_DEPLOYMENT);
            _db.execSQL(DATABASE_CREATE_SQL_SITE);
            _db.execSQL(DATABASE_CREATE_SQL_SETTINGS);
            _db.execSQL(DATABASE_CREATE_SQL_GEOPLOT);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");

            // Destroy old database:
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_SERVICES);
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_DEPLOYMENT);
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_SITE);
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_SETTINGS);
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_GEOPLOT);

            // Recreate new database:
            onCreate(_db);
        }
    }
}