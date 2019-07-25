package com.mpvreeken.rpgcompanion.PCs.PC;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sven on 2/16/2018.
 */

public class PCSkill implements Serializable{
    public int base, extra, total, skill_index;
    private PC pc;
    public boolean proficient, expertise;

    public PCSkill(PC pc, int skill_index) {
        this.pc=pc;
        this.base=0;
        this.extra=0;
        this.total=0;
        this.proficient=false;
        this.expertise=false;
        this.skill_index=skill_index;

        updateValues();
    }

    public void updateValues() {
        this.base=getParentAbilityModValue();
        this.total=this.base+this.extra;
        if (expertise) {
            this.total+=2*(pc.getProficiencyBonus());
        }
        else if (proficient) {
            this.total+=pc.getProficiencyBonus();
        }
    }

    public void setProficient(boolean b) { this.proficient=b; }
    public void setExpertise(boolean b) { this.expertise=b; }

    public String getName() { return PC.SKILLS.get(skill_index); }
    public String getFullName() { return getName() + " (" + getParentAbilityShort() + ")"; }
    public String getParentAbilityName() { return  PC.ABILITIES.get(PC.SKILLS_ABILITY.get(skill_index)); }
    public String getParentAbilityShort() { return  PC.ABILITIES_SHORT.get(PC.SKILLS_ABILITY.get(skill_index)); }
    public int getParentAbilityIndex() { return  PC.SKILLS_ABILITY.get(skill_index); }
    public int getParentAbilityModValue() { return pc.abilities.get(getParentAbilityIndex()).getSaveModTotal(); }

    public int getBase() {
        return base;
    }
    public int getExtra() {
        return extra;
    }
    public int getTotal() { return base + extra; }

    public void setExtra(int e) { extra=e; }
}
