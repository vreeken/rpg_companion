package com.mpvreeken.rpgcompanion.Names;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sven on 12/18/2017.
 */

public class HalforcName extends NameGenerator {
	//TODO needs more from Xanathur's guide, and lower-case?
    private List<String> f1 = new ArrayList<>(Arrays.asList("Ag","Agg","Ar","Arn","As","At","Atr","B","Bar","Bel","Bor","Br","Brak","C","Cr","D","Dor","Dr","Dur","G","Gal","Gan","Gar","Gna","Gor","Got","Gr","Gram","Grim","Grom","Grum","Gul","H","Hag","Han","Har","Hog","Hon","Hor","Hun","Hur","K","Kal","Kam","Kar","Kel","Kil","Kom","Kor","Kra","Kru","Kul","Kur","Lum","M","Mag","Mahl","Mak","Mal","Mar","Mog","Mok","Mor","Mug","Muk","Mura","N","Oggu","Ogu","Ok","Oll","Or","Rek","Ren","Ron","Rona","S","Sar","Sor","T","Tan","Th","Thar","Ther","Thr","Thur","Trak","Truk","Ug","Uk","Ukr","Ull","Ur","Urth","Urtr","Z","Za","Zar","Zas","Zav","Zev","Zor","Zur","Zus"));
    private List<String> f2 = new ArrayList<>(Arrays.asList("a","a","a","o","o","e","i","u","u","u"));
    private List<String> f3 = new ArrayList<>(Arrays.asList("bak","bar","bark","bash","bur","burk","d","dak","dall","dar","dark","dash","dim","dur","durk","g","gak","gall","gar","gark","gash","glar","gul","gur","m","mak","mar","marsh","mash","mir","mur","n","nar","nars","nur","rak","rall","rash","rim","rimm","rk","rsh","rth","ruk","sk","tar","tir","tur","z","zall","zar","zur"));
    private List<String> f4 = new ArrayList<>(Arrays.asList("Al","Ar","Br","Ek","El","Fal","Fel","Fol","Ful","G","Gaj","Gar","Gij","Gor","Gr","Gry","Gyn","Hur","K","Kar","Kat","Ker","Ket","Kir","Kot","Kur","Kut","Lag","M","Mer","Mir","Mor","N","Ol","Oot","Puy","R","Rah","Rahk","Ras","Rash","Raw","Roh","Rohk","S","Sam","San","Sem","Sen","Sh","Shay","Sin","Sum","Sun","Tam","Tem","Tu","Tum","Ub","Um","Ur","Van","Zan","Zen","Zon","Zun"));
	private List<String> f5 = new ArrayList<>(Arrays.asList("a","a","o","o","e","i","i","u"));
	private List<String> f6 = new ArrayList<>(Arrays.asList("d","da","dar","dur","g","gar","gh","gri","gu","sh","sha","shi","gum","gume","gur","ki","mar","mi","mira","me","mur","ne","ner","nir","nar","nchu","ni","nur","ral","rel","ri","rook","ti","tah","tir","tar","tur","war","z","zar","zara","zi","zur","zura","zira"));
	
	//not used
	private List<String> f7 = new ArrayList<>(Arrays.asList("d","dd","dk","dg","dv","g","gg","gn","gv","gz","l","ll","lv","lz","m","md","mz","mv","ng","nk","ns","nz","t","thr","th","v","vn","vr","vg","vd","wnk","wg","wn"));
	private List<String> f8 = new ArrayList<>(Arrays.asList("","","","","","f","h","k","l","m","n","ng","v","z","gga","lla","gara","gora","raga","rga","nga","rta"));
	
	//todo
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
			return cap(r(f1) + r(f2) + r(f3));
		}
		else {
			//todo
			return cap(r(f1) + r(f2) + r(f3) + r(f2) + r(f4));
		}        
    }

    private String female() {
        if (random.nextInt(10)%2==0) {
			return cap(r(f4) + r(f5) + r(f6));
		}
		else {
			//todo
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
