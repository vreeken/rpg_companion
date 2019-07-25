package com.mpvreeken.rpgcompanion.Campaigns;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mpvreeken.rpgcompanion.RPGCActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sven on 2/13/2018.
 */

public class Campaign {

    private static final String DB_NAME = "RPGCompanion";
    private static final String DB_CAMPAIGNS_TABLE = "campaigns";
    private static final int DB_CAMPAIGNS_COL_ID = 0;
    private static final int DB_CAMPAIGNS_COL_NAME = 1;
    private static final String DB_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + DB_CAMPAIGNS_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name VARCHAR);";
    private static final String DB_QUERY_GET_CAMPAIGNS = "SELECT * FROM " + DB_CAMPAIGNS_TABLE + " ORDER BY _id ASC";

    public int id;
    public String name;

    public static List<Campaign> getCampaigns(RPGCActivity activity) {
        SQLiteDatabase db = activity.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
        db.execSQL(DB_CREATE_TABLE);

        ArrayList<Campaign> campaigns = new ArrayList<>();

        try (Cursor cursor = db.rawQuery(DB_QUERY_GET_CAMPAIGNS, null)) {
            while (cursor.moveToNext()) {
                campaigns.add(new Campaign(cursor.getInt(DB_CAMPAIGNS_COL_ID), cursor.getString(DB_CAMPAIGNS_COL_NAME)));
            }
        }

        return  campaigns;
    }

    public static List<Campaign> saveNewCampaign(RPGCActivity activity, String name) {
        SQLiteDatabase db=activity.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
        db.execSQL(DB_CREATE_TABLE);
        db.execSQL("INSERT INTO " + DB_CAMPAIGNS_TABLE + " VALUES(null, '"+name+"');");

        return getCampaigns(activity);
    }

    public Campaign(int id, String name) {
        this.id=id;
        this.name=name;
    }

}
