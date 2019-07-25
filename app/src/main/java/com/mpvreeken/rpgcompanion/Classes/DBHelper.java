package com.mpvreeken.rpgcompanion.Classes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mpvreeken.rpgcompanion.NPC.NPC;

import java.util.ArrayList;

/**
 * Created by Sven on 9/3/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "MyNPCs.db";
    public static final String NPCS_TABLE_NAME = "my_npcs";
    public static final String NPCS_COL_NPC = "npc";
    public static final String NPCS_COL_SUMMARY = "summary";
    public static final String NPCS_COL_NAME = "name";

    //NPC CATEGORIES
    public static final String NPC_CATEGORIES_TABLE_NAME = "npc_categories";
    public static final String NPC_CATS_COL_NAME = "cat_name";
    public static final String NPC_CATS_COL_SORT_ID = "cat_sort_id";

    public static final String NPC_CATEGORY_RELATIONS_TABLE_NAME = "npc_category_relations";
    public static final String NPC_CR_COL_CAT_ID = "cat_id";
    public static final String NPC_CR_COL_NPC_ID = "npc_id";


    //https://thebhwgroup.com/blog/how-android-sqlite-onupgrade
    //private static final String DATABASE_ALTER_TEAM_2 = "ALTER TABLE "+ NPCS_TABLE_NAME + " ADD COLUMN " + COLUMN_STADIUM + " string;";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE IF NOT EXISTS " + NPCS_TABLE_NAME + " (" +
                "_id INTEGER PRIMARY KEY," +
                NPCS_COL_NPC + " TEXT," +
                NPCS_COL_SUMMARY + " TEXT," +
                NPCS_COL_NAME + " TEXT)";
        db.execSQL(create);

        String create_cats = "CREATE TABLE IF NOT EXISTS " + NPC_CATEGORIES_TABLE_NAME + " (" +
                "_id INTEGER PRIMARY KEY," +
                NPC_CATS_COL_NAME + " TEXT," +
                NPC_CATS_COL_SORT_ID + " INTEGER)";
        db.execSQL(create_cats);

        String create_cat_relations = "CREATE TABLE IF NOT EXISTS " + NPC_CATEGORY_RELATIONS_TABLE_NAME + " (" +
                "_id INTEGER PRIMARY KEY," +
                NPC_CR_COL_CAT_ID + " INTEGER," +
                NPC_CR_COL_NPC_ID + " INTEGER)";
        db.execSQL(create_cat_relations);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 5) {
            onCreate(db);
        }

        /*
        String delete = "DROP TABLE IF EXISTS " + NPCS_TABLE_NAME;
        db.execSQL(delete);
        onCreate(db);
        */
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
