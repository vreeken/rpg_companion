package com.mpvreeken.rpgcompanion.PCs.PC;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mpvreeken.rpgcompanion.RPGCActivity;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sven on 2/14/2018.
 */

public class PC implements Serializable {

    private static final String DB_NAME = "RPGCompanion";
    private static final String DB_PCS_TABLE = "pcs";
    private static final int DB_PCS_COL_ID = 0;
    private static final int DB_PCS_COL_NAME = 2;
    private static final int DB_PCS_COL_SUMMARY = 3;
    private static final int DB_PCS_COL_JSON = 4;
    private static final String DB_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + DB_PCS_TABLE +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            " campaign_id INTEGER," +
            " name VARCHAR," +
            " summary VARCHAR," +
            " chardata TEXT);";

    public static final List<String> SKILLS = Arrays.asList("Acrobatics", "Animal Handling", "Arcana", "Athletics", "Deception", "History", "Insight", "Intimidation", "Investigation", "Medicine", "Nature", "Perception", "Performance", "Persuasion", "Religion", "Sleight of Hand", "Stealth", "Survival");
    public static final List<Integer> SKILLS_ABILITY = Arrays.asList(1,4,3,0,5,3,4,5,3,4,3,4,5,5,3,3,3,4);
    public static final List<String> ABILITIES = Arrays.asList("Strength", "Dexterity", "Constitution", "Intelligence", "Wisdom", "Charisma");
    public static final List<String> ABILITIES_SHORT = Arrays.asList("Str", "Dex", "Con", "Int", "Wis", "Cha");

    public static final List<String> CLASSES = Arrays.asList("Barbarian", "Bard", "Blood Hunter", "Cleric", "Druid", "Fighter", "Monk", "Paladin", "Ranger", "Rogue", "Sorcerer", "Warlock", "Wizard");
    public static final List<String> RACES = Arrays.asList("Aarakocra", "Aasimar", "Bugbear", "Dragonborn", "Dwarf", "Elf", "Firbolg", "Genasi", "Gnome", "Goblin", "Goliath", "Half-Elf", "Halfling", "Half-Orc", "Hobgoblin", "Human", "Kenku", "Kobold", "Lizardfolk", "Orc", "Tabaxi", "Tiefling", "Tortle", "Triton", "Yuan-ti");
    public static final List<String> BACKGROUNDS = Arrays.asList();


    public String name, playerName, race, clas, background, alignment;
    public int campaign_id;
    public int hp, currenthp, temphp, level, xp, proficiency, initiative, speed, ac;
    public List<PCAbility> abilities;
    public List<PCSkill> skills;
    public int[] deathSaveSuccesses, deathSaveFails;
    public List<String> languages;


    public int id;


    public static List<PC> getPCs(RPGCActivity activity, int campaign_id) {
        SQLiteDatabase db = activity.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
        db.execSQL(DB_CREATE_TABLE);

        String query_get_pcs = "SELECT * FROM " + DB_PCS_TABLE + " WHERE `campaign_id` = " + campaign_id + " ORDER BY _id ASC";

        ArrayList<PC> pcs = new ArrayList<>();

        try (Cursor cursor = db.rawQuery(query_get_pcs, null)) {
            while (cursor.moveToNext()) {
                pcs.add(new PC(cursor.getInt(DB_PCS_COL_ID), cursor.getString(DB_PCS_COL_NAME)));
            }
        }

        return pcs;
    }

    public static List<PC> saveNewPC(RPGCActivity activity, int campaign_id, String name) {
        SQLiteDatabase db=activity.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
        db.execSQL(DB_CREATE_TABLE);
        db.execSQL("INSERT INTO " + DB_PCS_TABLE + " VALUES(null, '"+name+"');");

        return getPCs(activity, campaign_id);
    }




    public PC(int id, String name) {
        this.id=id;
        this.name=name;
    }

    public PC(JSONObject json) {

    }

    public PC() {
        this.name = "";
        this.playerName = "";
        this.ac=0;
        this.race="";
        this.clas="";
        this.background="";
        this.alignment="";
        this.campaign_id=0;
        this.hp=0;
        this.currenthp=0;
        this.temphp=0;
        this.level=0;
        this.xp=0;
        this.proficiency=0;
        this.initiative=0;
        this.speed=0;

        this.abilities = new ArrayList<>();
        for (int i=0; i<ABILITIES.size(); i++) {
            this.abilities.add(new PCAbility(this, ABILITIES.get(i)));
        }

        this.deathSaveSuccesses = new int[] {0,0,0};
        this.deathSaveFails = new int[] {0,0,0};

        this.languages = new ArrayList<>();

        this.skills = new ArrayList<>();
        for (int i=0; i< SKILLS.size(); i++) {
            this.skills.add(new PCSkill(this, i));
        }
    }

    public int getProficiencyBonus() {
        return Integer.valueOf((level-1)/4)+1;
    }

    public boolean hasJackOfAllTrades() {
        return clas.toLowerCase().indexOf("bard")!=-1 && level>1;
    }


}
