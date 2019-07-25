package com.mpvreeken.rpgcompanion.Dungeons;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mpvreeken.rpgcompanion.R;
import com.mpvreeken.rpgcompanion.RPGCAuthActivity;

public class EditDungeonActivity extends RPGCAuthActivity {

    public static Dungeon dungeon=null;

    private EditText title_et, dungeon_et, external_et, image_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dungeon);

        setupLoadingAnimTransparent();

        if (dungeon==null) {
            Toast.makeText(application, "An unknown error has occurred. Please try again.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }


        dungeon.setEditEventListener(new Dungeon.EditEventListener() {
            @Override
            public void onUpdatePostFail() {
                EditDungeonActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoadingAnim();
                    }
                });
            }

            @Override
            public void onUpdatePostSuccess() {
                Log.d("onUpdatePostSuccess", "EditDungeonActivity");
                EditDungeonActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoadingAnim();
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("SERIALIZED_OBJ", dungeon.getSerialized());
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                    }
                });
            }

            @Override
            public void onDeletePostSuccess() {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("DELETED", true);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
            @Override
            public void onDeletePostFail() {

            }
        });

        title_et = findViewById(R.id.edit_dungeon_title_et);
        title_et.setText(dungeon.getTitle());
        dungeon_et = findViewById(R.id.edit_dungeon_body_et);
        dungeon_et.setText(dungeon.getDescription());
        external_et = findViewById(R.id.edit_dungeon_external_et);
        external_et.setText(dungeon.getExternalLink());
        image_et = findViewById(R.id.edit_dungeon_image_et);
        image_et.setText(dungeon.getImageLink());

        Button save_btn = findViewById(R.id.edit_dungeon_submit_btn);
        Button cancel_btn = findViewById(R.id.edit_dungeon_cancel_btn);
        Button delete_btn = findViewById(R.id.edit_dungeon_delete_btn);

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoadingAnim();
                submitChanges();
            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                return;
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditDungeonActivity.this);
                builder.setMessage("Are you sure you want to delete this Unique Dungeon?")
                        .setTitle("Delete Unique Dungeon?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dungeon.deletePostOnServer();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void submitChanges() {
        dungeon.setTitle(title_et.getText().toString());
        dungeon.setDescription(dungeon_et.getText().toString());
        dungeon.setExternalLink(external_et.getText().toString());
        dungeon.setImageLink(image_et.getText().toString());
        dungeon.updatePostOnServer();
    }

}
