package com.example.ali.shopper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * The DBHandler class is used to set up the shopper database
 * and interact with it.
 */
public class DBHandler extends SQLiteOpenHelper {

    // initialize constant for DB version and name
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "shopper.db";

    // initialize contains for shopping list table

    private static final String TABLE_SHOPPING_LIST = "shoppinglist";
    private static final String COLUMN_LIST_ID ="_id";
    private static final String COLUMN_LIST_NAME = "name";
    private static final String COLUMN_LIST_STORE = "store";
    private static final String COLUMN_LIST_DATE = "date";

    /**
     * Initializes a DBHandler. Create a version of our database
     * @param context reference to the activity that initalize the DBHandler
     * @param factory null
     */
    public DBHandler (Context context, SQLiteDatabase.CursorFactory factory){
        // call superclass constructor
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);

    }

    /**
     * This method creates database tables.
     * @param sqLiteDatabase reference to the shopper database
     */

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // define String for create statement for shopping list table
        String query = "CREATE TABLE "+TABLE_SHOPPING_LIST + "(" +
                COLUMN_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LIST_NAME + " TEXT, " +
                COLUMN_LIST_STORE + " TEXT, " +
                COLUMN_LIST_DATE + " TEST " +
                ");";
        // execute the create statement
        sqLiteDatabase.execSQL(query);
    }

    /**
     * This method get called when a new version of the database is initialized.
     * @param sqLiteDatabase reference to the shopper database
     * @param oldVersion old version of shopper database
     * @param newVersion new version of shopper database
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //execute a drop statement that drops the shopping liat table from the shopper database
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPPING_LIST );

        // call method that creates the tables

        onCreate(sqLiteDatabase);

    }

    /**
     * This method gets called when the add item in the CreateList action bar gets
     * clicked. It inserts a new row into the shopping list table.
     * @param name shopping list name typed in by user
     * @param store shopping list store typed in by user
     * @param date shopping list date typed in by user
     */
    public void addShoppingList (String name, String store, String date){

        // get writeable reference to shopper database
        SQLiteDatabase db = getWritableDatabase();

        // Inirialize an empty ContentValues object
        ContentValues values = new ContentValues();

        // put Key-value pairs in the ContentValues object. The Key must be
        // the name of a column and the value is the value to be inserted in
        // the column
        values.put(COLUMN_LIST_NAME, name);
        values.put(COLUMN_LIST_STORE, store);
        values.put(COLUMN_LIST_DATE, date);

        // insert values into the shopping list table
        db.insert(TABLE_SHOPPING_LIST,null,values);

        // close the reference to shopper database
        db.close();
    }

    /**
     * This method get called when the main activity is created.
     * @return Cursor that contains all of the rows in the shopping list table.
     */
    public Cursor getShoppingLists(){

        // get writeable reference to shopper database
        SQLiteDatabase db = getWritableDatabase();

        // execute select statement that selects all rows from the
        // shopping list table and returns them as a cursor
        return db.rawQuery("SELECT * FROM "+ TABLE_SHOPPING_LIST,null);
    }

    public  String getShoppingListName (int id){
        // get writeable reference to shopper database
        SQLiteDatabase db = getWritableDatabase();

        // initialize String that will be returned by the method
        String dbString = "";

        // define select statement that will select everyting from the
        // shopping list table for the specified id
        String query = "SELECT * FROM " + TABLE_SHOPPING_LIST +
                " WHERE " + COLUMN_LIST_ID + " = " + id;

        // execute select statement
        Cursor cursor = db.rawQuery(query, null);

        // move to the first row in the Cursor
        cursor.moveToFirst();

        // if the shopping list name in the Cursor isn't null
        if(cursor.getString(cursor.getColumnIndex("name")) !=null){
            // get the shopping list name and store it in our String
            dbString = cursor.getString(cursor.getColumnIndex("name"));
        }

        // close our reference to the shopper database
        db.close();

        // return shopping list
        return  dbString;
    }
}
