package com.mpvreeken.rpgcompanion.PCs.PC;

import java.io.Serializable;

/**
 * Created by Sven on 2/16/2018.
 */

public class PCAbility implements Serializable {
    public String name;
    public int score, scoreExtra, saveExtra;
    private PC pc;
    private boolean isProficient;

    public PCAbility(PC pc, String name) {
        this.pc=pc;
        this.name=name;
        this.score=0;
        this.scoreExtra=0;
        this.saveExtra=0;
        this.isProficient=false;
    }

    public PCAbility(PC pc, String name, int score, int scoreExtra, int saveExtra, boolean isProficient) {
        this.pc=pc;
        this.name=name;
        this.score=score;
        this.scoreExtra=scoreExtra;
        this.saveExtra=saveExtra;
        this.isProficient=isProficient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() { return score; }

    public void setScore(int score) { this.score = score; }
    public void setScore(String score) { this.score = Integer.valueOf(score); }

    public int getScoreExtra() {
        return scoreExtra;
    }

    public void setScoreExtra(int scoreExtra) { this.scoreExtra = scoreExtra; }
    public void setScoreExtra(String scoreExtra) { this.scoreExtra = Integer.valueOf(scoreExtra); }

    public int getSaveExtra() { return saveExtra; }

    public void setSaveExtra(int saveExtra) { this.saveExtra = saveExtra; }
    public void setSaveExtra(String saveExtra) { this.saveExtra = Integer.valueOf(saveExtra); }

    public int getScoreMod() { return ((score)/2)-5; }

    public int getScoreModTotal() { return getScore() + scoreExtra; }

    public int getSaveMod() { return ((score)/2)-5; }

    public boolean isProficient() { return isProficient; }

    public int getSaveModTotal() {
        int total = getSaveMod() + saveExtra;
        if (isProficient) {
            total += pc.proficiency;
        }
        else if (pc.hasJackOfAllTrades()) {
            total += Integer.valueOf((pc.proficiency)/2);
        }
        return total;
    }
}
