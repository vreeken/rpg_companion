package com.mpvreeken.rpgcompanion.NPC;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.mpvreeken.rpgcompanion.Classes.DBHelper;
import com.mpvreeken.rpgcompanion.R;
import com.mpvreeken.rpgcompanion.RPGCActivity;
import com.mpvreeken.rpgcompanion.RPGCApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class NPCsActivity extends RPGCActivity {

    private DBHelper dbHelper;
    private ListView npcListView;
    private Spinner catSpinner;
    private SQLiteDatabase db;
    private ConstraintLayout new_cat_popup, del_cat_popup;
    private ArrayList<Category> cats;
    private int currentCatId = 0;
    private Button del_cat_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_npcs);

        Button random_btn = findViewById(R.id.npcs_random_btn);
        random_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RandomNPCActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        Button blank_btn = findViewById(R.id.npcs_blank_btn);
        blank_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RandomNPCActivity.class);
                intent.putExtra("BLANK_NPC", true);
                startActivityForResult(intent, 1);
            }
        });

        new_cat_popup = findViewById(R.id.npc_new_cat_popup);

        Button new_cat_btn = findViewById(R.id.npcs_new_category_btn);
        new_cat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_cat_popup.setVisibility(View.VISIBLE);
            }
        });

        del_cat_popup = findViewById(R.id.npc_del_cat_popup);

        del_cat_btn = findViewById(R.id.npcs_delete_category_btn);
        del_cat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView t = findViewById(R.id.npc_del_cat_tv);
                t.setText(cats.get(currentCatId).name);
                del_cat_popup.setVisibility(View.VISIBLE);
            }
        });



        Button del_cat_del_btn = findViewById(R.id.npcs_del_popup_del_btn);
        del_cat_del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCategory(currentCatId);
                del_cat_popup.setVisibility(View.GONE);
            }
        });

        Button cancel_del_cat_btn = findViewById(R.id.npcs_del_popup_cancel_btn);
        cancel_del_cat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                del_cat_popup.setVisibility(View.GONE);
            }
        });





        Button add_cat_btn = findViewById(R.id.npcs_cat_popup_save_btn);
        add_cat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et = findViewById(R.id.npc_new_cat_et);
                String input = et.getText().toString();
                if (input.length() > 0) {
                    addNewCategory(input);
                    hideKeyboard(NPCsActivity.this);
                    new_cat_popup.setVisibility(View.GONE);
                    getCategories();
                }
            }
        });

        Button cancel_cat_btn = findViewById(R.id.npcs_cat_popup_cancel_btn);
        cancel_cat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(NPCsActivity.this);
                new_cat_popup.setVisibility(View.GONE);
            }
        });


        catSpinner = findViewById(R.id.npcs_cat_spinner);

        npcListView = findViewById(R.id.npcs_listview);
        dbHelper = new DBHelper(this.getBaseContext());
        db = dbHelper.getReadableDatabase();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getCategories();
    }

    private void addNewCategory(String s) {
        ContentValues insertValues = new ContentValues();
        insertValues.put(DBHelper.NPC_CATS_COL_NAME, s);
        insertValues.put(DBHelper.NPC_CATS_COL_SORT_ID, String.valueOf(cats.size()-1));
        db.insert(DBHelper.NPC_CATEGORIES_TABLE_NAME, null, insertValues);
    }
    private void deleteCategory(int id) {
        if (id==0) {
            //ALL is undeleteable
            return;
        }

        //Remove all relations
        String query = "DELETE FROM "+DBHelper.NPC_CATEGORY_RELATIONS_TABLE_NAME+" WHERE " + DBHelper.NPC_CR_COL_CAT_ID + " = " + cats.get(id).id;
        db.execSQL(query);
        //remove category
        query = "DELETE FROM "+DBHelper.NPC_CATEGORIES_TABLE_NAME+" WHERE _id = "+cats.get(id).id;
        db.execSQL(query);
        //adjust sort_id for all categories > deleted sort_id
        query = "UPDATE "+DBHelper.NPC_CATEGORIES_TABLE_NAME+" SET "+DBHelper.NPC_CATS_COL_SORT_ID+" = " + DBHelper.NPC_CATS_COL_SORT_ID + " - 1 WHERE "+DBHelper.NPC_CATS_COL_SORT_ID+" > " + cats.get(id).sort_id;
        db.execSQL(query);

        getCategories();
    }

    private void getCategories() {
        String query = "SELECT * FROM "+DBHelper.NPC_CATEGORIES_TABLE_NAME+" ORDER BY "+DBHelper.NPC_CATS_COL_SORT_ID+" ASC";
        Cursor cursor = db.rawQuery(query, null);

        cats = new ArrayList<>();

        cats.add(new Category(-1,"ALL",0));

        try {
            while (cursor.moveToNext()) {
                cats.add(new Category(
                        cursor.getLong(cursor.getColumnIndex("_id")),
                        cursor.getString(cursor.getColumnIndex(DBHelper.NPC_CATS_COL_NAME)),
                        cursor.getInt(cursor.getColumnIndex(DBHelper.NPC_CATS_COL_SORT_ID))
                    )
                );
            }
        }
        finally {
            cursor.close();
        }

        /*
        //List View
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cats);

        npcListView.setAdapter(adapter);

        npcListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                Log.d("Click", (String) parent.getItemAtPosition(pos));
            }
        });
        */
        ArrayAdapter<Category> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cats);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        catSpinner.setAdapter(adapter);

        catSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentCatId = i;
                if (i==0) {
                    del_cat_btn.setVisibility(View.GONE);
                }
                else {
                    del_cat_btn.setVisibility(View.VISIBLE);
                }
                populateNPCList(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void populateNPCList(int id) {
        String query;
        if (id==0) {
            query = "SELECT * FROM "+DBHelper.NPCS_TABLE_NAME+" ORDER BY _id ASC";
        }
        else {
            String q = "SELECT * FROM "+DBHelper.NPC_CATEGORY_RELATIONS_TABLE_NAME+" WHERE "+DBHelper.NPC_CR_COL_CAT_ID+" = "+id;
            Cursor cursor = db.rawQuery(q, null);
            ArrayList<Integer> npc_ids = new ArrayList<>();

            try {
                while (cursor.moveToNext()) {
                    npc_ids.add(cursor.getInt(cursor.getColumnIndex(DBHelper.NPC_CR_COL_NPC_ID)));
                }
            }
            finally {
                cursor.close();
            }

            if (npc_ids.size()>0) {
                query = "SELECT * FROM " + DBHelper.NPCS_TABLE_NAME + " WHERE _id IN (";
                for (int i = 0; i < npc_ids.size(); i++) {
                    query += npc_ids.get(i) + ",";
                }
                query = query.substring(0, query.length() - 1);
                query += ")";
            }
            else {
                query = "SELECT * FROM "+DBHelper.NPCS_TABLE_NAME+" WHERE "+DBHelper.NPCS_COL_NAME+" = -1";
            }
        }


        Cursor cursor = db.rawQuery(query, null);
        NPCCursorAdapter adapter = new NPCCursorAdapter(this, R.layout.npc_list_item_layout, cursor, 0);
        npcListView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                //This should only occur if an npc was saved
                //String result=data.getStringExtra("npc_saved");
               // populateNPCList();
            }
            if (resultCode == RESULT_CANCELED) {
                //No npc was saved
            }
        }
    }

    public void deleteNPC(String id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Define 'where' part of query.
        String selection = "_id = ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { id };
        // Issue SQL statement.
        db.delete(DBHelper.NPCS_TABLE_NAME, selection, selectionArgs);

        //populateNPCList();
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }


    public class Category {
        public String name;
        public long id;
        public int sort_id;

        public Category(long id, String n, int si) {
            this.id=id;
            this.name=n;
            this.sort_id=si;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
}
