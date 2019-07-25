package com.mpvreeken.rpgcompanion.PCs.PC;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Sven on 2/18/2018.
 */

public class PCClass {

    private PC pc;
    public String name, primaryAbility, saves;
    public int hitDie;
    public List<Integer> primaryAbilityIndeces, saveIndeces;


    public static final List<String> CLASSES_LOWERCASE = Arrays.asList("barbarian", "bard", "blood hunter", "cleric", "druid", "fighter", "monk", "paladin", "ranger", "rogue", "sorcerer", "warlock", "wizard");


    public PCClass(PC pc, String name) {
        this.pc = pc;

        if (CLASSES_LOWERCASE.indexOf(name.toLowerCase()) != 0) {

        }
        else {

        }


    }
}
