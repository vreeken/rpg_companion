package com.mpvreeken.rpgcompanion.Names;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mpvreeken.rpgcompanion.Names.DragonbornName;
import com.mpvreeken.rpgcompanion.R;
import com.mpvreeken.rpgcompanion.RPGCActivity;

public class NameGeneratorActivity extends RPGCActivity {

    private TextView output_tv;
    private AarakocraName aarakocraName;
    private DragonbornName dragonbornName;
    private DrowName drowName;
    private DwarfName dwarfName;
    private ElfName elfName;
    private GnomeName gnomeName;
    private HalfelfName halfelfName;
    private HalforcName halforcName;
    private HumanName humanName;
    private OrcName orcName;
    private HalflingName halflingName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_generator);

        aarakocraName = new AarakocraName();
        dragonbornName = new DragonbornName();
        drowName = new DrowName();
        dwarfName = new DwarfName();
        elfName = new ElfName();
        gnomeName = new GnomeName();
        halfelfName = new HalfelfName();
        halforcName = new HalforcName();
        humanName = new HumanName();
        orcName = new OrcName();
        halflingName = new HalflingName();

        output_tv = findViewById(R.id.names_output_tv);

        Button ara_btn = findViewById(R.id.names_aarakocra_btn);
        Button db_m_btn = findViewById(R.id.names_dragonborn_m_btn);
        Button db_f_btn = findViewById(R.id.names_dragonborn_f_btn);
        Button dr_m_btn = findViewById(R.id.names_drow_m_btn);
        Button dr_f_btn = findViewById(R.id.names_drow_f_btn);
        Button dw_m_btn = findViewById(R.id.names_dwarf_m_btn);
        Button dw_f_btn = findViewById(R.id.names_dwarf_f_btn);
        Button elf_m_btn = findViewById(R.id.names_elf_m_btn);
        Button elf_f_btn = findViewById(R.id.names_elf_f_btn);
        Button gn_m_btn = findViewById(R.id.names_gnome_m_btn);
        Button gn_f_btn = findViewById(R.id.names_gnome_f_btn);
        Button he_m_btn = findViewById(R.id.names_halfelf_m_btn);
        Button he_f_btn = findViewById(R.id.names_halfelf_f_btn);
        Button ho_m_btn = findViewById(R.id.names_halforc_m_btn);
        Button ho_f_btn = findViewById(R.id.names_halforc_f_btn);
        Button hu_m_btn = findViewById(R.id.names_human_m_btn);
        Button hu_f_btn = findViewById(R.id.names_human_f_btn);
        Button orc_m_btn = findViewById(R.id.names_orc_m_btn);
        Button orc_f_btn = findViewById(R.id.names_orc_f_btn);
        Button hl_m_btn = findViewById(R.id.names_halfling_m_btn);
        Button hl_f_btn = findViewById(R.id.names_halfling_f_btn);

        View.OnClickListener clickListener = new View.OnClickListener() {
            public void onClick(View v) {
                switch(v.getId()) {
                    case R.id.names_aarakocra_btn:
                        getName(aarakocraName, "");
                        break;
                    case R.id.names_dragonborn_m_btn:
                        getName(dragonbornName, "m");
                        break;
                    case R.id.names_dragonborn_f_btn:
                        getName(dragonbornName, "m");
                        break;
                    case R.id.names_drow_m_btn:
                        getName(drowName, "m");
                        break;
                    case R.id.names_drow_f_btn:
                        getName(drowName, "f");
                        break;
                    case R.id.names_dwarf_m_btn:
                        getName(dwarfName, "m");
                        break;
                    case R.id.names_dwarf_f_btn:
                        getName(dwarfName, "f");
                        break;
                    case R.id.names_elf_m_btn:
                        getName(elfName, "m");
                        break;
                    case R.id.names_elf_f_btn:
                        getName(elfName, "f");
                        break;
                    case R.id.names_gnome_m_btn:
                        getName(gnomeName, "m");
                        break;
                    case R.id.names_gnome_f_btn:
                        getName(gnomeName, "f");
                        break;
                    case R.id.names_halfelf_m_btn:
                        getName(halfelfName, "m");
                        break;
                    case R.id.names_halfelf_f_btn:
                        getName(halfelfName, "f");
                        break;
                    case R.id.names_halforc_m_btn:
                        getName(halforcName, "m");
                        break;
                    case R.id.names_halforc_f_btn:
                        getName(halforcName, "f");
                        break;
                    case R.id.names_human_m_btn:
                        getName(humanName, "m");
                        break;
                    case R.id.names_human_f_btn:
                        getName(humanName, "f");
                        break;
                    case R.id.names_orc_m_btn:
                        getName(orcName, "m");
                        break;
                    case R.id.names_orc_f_btn:
                        getName(orcName, "f");
                        break;
                    case R.id.names_halfling_m_btn:
                        getName(halflingName, "m");
                        break;
                    case R.id.names_halfling_f_btn:
                        getName(halflingName, "f");
                        break;
                    default:

                }
            }
        };

        ara_btn.setOnClickListener(clickListener);
        db_m_btn.setOnClickListener(clickListener);
        db_f_btn.setOnClickListener(clickListener);
        dr_m_btn.setOnClickListener(clickListener);
        dr_f_btn.setOnClickListener(clickListener);
        dw_m_btn.setOnClickListener(clickListener);
        dw_f_btn.setOnClickListener(clickListener);
        elf_m_btn.setOnClickListener(clickListener);
        elf_f_btn.setOnClickListener(clickListener);
        gn_m_btn.setOnClickListener(clickListener);
        gn_f_btn.setOnClickListener(clickListener);
        he_m_btn.setOnClickListener(clickListener);
        he_f_btn.setOnClickListener(clickListener);
        ho_m_btn.setOnClickListener(clickListener);
        ho_f_btn.setOnClickListener(clickListener);
        hu_m_btn.setOnClickListener(clickListener);
        hu_f_btn.setOnClickListener(clickListener);
        orc_m_btn.setOnClickListener(clickListener);
        orc_f_btn.setOnClickListener(clickListener);
        hl_m_btn.setOnClickListener(clickListener);
        hl_f_btn.setOnClickListener(clickListener);


        output_tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Name", output_tv.getText().toString());
                if (clipboard != null) { clipboard.setPrimaryClip(clip); }
                Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void getName(NameGenerator g, String s) {
        String o;
        try {
             o = g.generate(s);
        } catch (Exception e) {
            Toast.makeText(context, "An unknown error has occurred. Please try again", Toast.LENGTH_LONG).show();
            return;
        }

        output_tv.setText(o);
    }
}