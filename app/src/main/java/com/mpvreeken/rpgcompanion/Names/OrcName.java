package com.mpvreeken.rpgcompanion.Names;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sven on 12/18/2017.
 */

public class OrcName extends NameGenerator {
	//TODO needs more from Xanathur's guide, and lower-case?
    private List<String> f1 = new ArrayList<>(Arrays.asList("b","bh","br","er","d","dh","dr","g","gh","gr","h","j","l","m","n","p","pr","r","rh","sh","v","vr","z","zh"));
    private List<String> f2 = new ArrayList<>(Arrays.asList("a","i","o","u"));
    private List<String> f3 = new ArrayList<>(Arrays.asList("b","br","bz","d","dd","dz","dg","dr","g","gg","gr","gz","gv","hr","hz","j","kr","kz","m","mz","mv","n","ng","nd","nz","r","rt","rz","rd","rl","rz","t","tr","v","vr","z","zz"));
    private List<String> f4 = new ArrayList<>(Arrays.asList("b","d","g","g","k","k","kk","kk","l","ll","n","r","dash","dish","dush","gar","gor","gdush","lo","gdish","k","lg","nak","rag","rbag","rg","rk","ng","nk","rt","ol","urk","shnak"));
	private List<String> f5 = new ArrayList<>(Arrays.asList("","","","","b","bh","d","dh","g","gh","h","k","m","n","r","rh","s","sh","v","z"));
	private List<String> f6 = new ArrayList<>(Arrays.asList("a","e","i","o","u","a","e","i","o","u","a","e","i","o","u","a","e","i","o","u","a","e","i","o","u","a","e","i","o","u","ee","au","ye","ie","aa","ou","ua","ao"));
	private List<String> f7 = new ArrayList<>(Arrays.asList("d","dd","dk","dg","dv","g","gg","gn","gv","gz","l","ll","lv","lz","m","md","mz","mv","ng","nk","ns","nz","t","thr","th","v","vn","vr","vg","vd","wnk","wg","wn"));
	private List<String> f8 = new ArrayList<>(Arrays.asList("","","","","","f","h","k","l","m","n","ng","v","z","gga","lla","gara","gora","raga","rga","nga","rta"));
	

    private List<String> l = new ArrayList<>(Arrays.asList("Aberrant","Ancient","Angry","Anguished","Arrogant","Barbarian","Barbaric","Barren","Berserk","Bitter","Bloody","Broad","Broken","Brutal","Brute","Butcher","Coarse","Cold","Colossal","Crazy","Crooked","Cruel","Dark","Defiant","Delirious","Deranged","Disfigured","Enormous","Enraged","Fearless","Feisty","Fierce","Filthy","Forsaken","Frantic","Gargantuan","Giant","Glorious","Grand","Grave","Grim","Gross","Gruesome","Hollow","Infernal","Lethal","Lost","Loyal","Macabre","Mad","Maniac","Merciless","Mighty","Miscreant","Noxious","Outlandish","Powerful","Prime","Proud","Putrid","Radical","Reckless","Repulsive","Rotten","Ruthless","Shady","Sick","Silent","Simple","Smug","Spiteful","Swift","Turbulent","Ugly","Unsightly","Vengeful","Venomous","Vicious","Violent","Vivid","Volatile","Vulgar","Warped","Wicked","Wild","Worthless","Wrathful","Wretched"));
    private List<String> l1 = new ArrayList<>(Arrays.asList("Anger","Ankle","Ash","Battle","Beast","Bitter","Black","Blood","Bone","Brain","Brass","Breath","Chaos","Chest","Chin","Cold","Dark","Death","Dirt","Doom","Dream","Elf","Eye","Fang","Feet","Fiend","Finger","Flame","Flesh","Foot","Ghost","Giant","Gnoll","Gnome","Gore","Hand","Hate","Head","Heart","Heel","Hell","Horror","Iron","Joint","Kidney","Kill","Knee","Muscle","Nose","Pest","Poison","Power","Pride","Rib","Scale","Skin","Skull","Slave","Smoke","Sorrow","Spine","Spite","Steel","Storm","Talon","Teeth","Throat","Thunder","Toe","Tooth","Vein","Venom","Vermin","War"));
    private List<String> l2 = new ArrayList<>(Arrays.asList("Axe","Blade","Brand","Breaker","Bruiser","Burster","Butcher","Carver","Chopper","Cleaver","Clobberer","Conquerer","Cracker","Cruncher","Crusher","Cutter","Dagger","Defacer","Despoiler","Destroyer","Dissector","Ender","Flayer","Gasher","Glaive","Gouger","Hacker","Hammer","Killer","Lance","Marauder","Masher","Mutilator","Piercer","Pummel","Quasher","Quelcher","Queller","Razer","Render","Ripper","Saber","Sabre","Scalper","Shatterer","Skinner","Slayer","Slicer","Smasher","Snapper","Spear","Splitter","Squasher","Squelcher","Squisher","Strangler","Sunderer","Sword","Trampler","Trasher","Vanquisher","Wrecker"));


    public String generate(String gender) {
        if (gender.equals("m")) {
            return male() + " " + sur();
        }
        else {
            return female() + " " + sur();
        }
    }

    private String male() {
		if (random.nextInt(10)%2==0) {
			return cap(r(f1) + r(f2) + r(f4));
		}
		else {
			return cap(r(f1) + r(f2) + r(f3) + r(f2) + r(f4));
		}        
    }

    private String female() {
        if (random.nextInt(10)%2==0) {
			return cap(r(f5) + r(f6) + r(f8));
		}
		else {
			return cap(r(f5) + r(f6) + r(f7) + r(f6) + r(f8));
		}
    }

    private String sur() {
        if (random.nextInt(10)%2==0) {
            return "the "+cap(r(l));
        }
        else {
            return cap(r(l1)+r(l2));
        }
    }
}
