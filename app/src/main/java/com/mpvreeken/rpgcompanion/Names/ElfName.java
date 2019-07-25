package com.mpvreeken.rpgcompanion.Names;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sven on 12/18/2017.
 */

public class ElfName extends NameGenerator {
	//TODO lower-case
    private List<String> f1 = new ArrayList<>(Arrays.asList("Ad","ev","koeh","kor","thro","raer","eil","cal","ren","er","sae","Ae","Bal","Bei","Car","Cra","Dae","Dor","El","Ela","Er","Far","Fen","Gen","Glyn","Hei","Her","Ian","Ili","Kea","Kel","Leo","Lu","Mira","Mor","Nae","Nor","Olo","Oma","Pa","Per","Pet","Qi","Qin","Ralo","Ro","Sar","Syl","The","Tra","Ume","Uri","Va","Vir","Waes","Wran","Yel","Yin","Zin","Zum"));
    private List<String> f2 = new ArrayList<>(Arrays.asList("ael","aer","aith","avel","brar","imil","umil","ruil","spar","thal","thus","kash","lian","ail","balar","beros","can","ceran","dan","dithas","faren","fir","geiros","golor","hice","horn","jeon","jor","kas","kian","lamin","lar","len","maer","maris","menor","myar","nan","neiros","nelis","norin","peiros","petor","qen","quinal","ran","ren","ric","ris","ro","salor","sandoral","toris","tumal","valur","ven","warin","wraek","xalim","xidor","yarus","ydark","zeiros","zumin"));
    private List<String> f3 = new ArrayList<>(Arrays.asList("Ad","Ara","Bi","Bry","Cai","Chae","Da","Dae","Eil","En","Fa","Fae","Gil","Gre","Hele","Hola","Iar","Ina","Jo","Key","Kris","Lia","lue","mara","cas","ang","aza","nai","she","tia","tra","ver","keth","il","Lora","Mag","Mia","Neri","Ola","Ori","Phi","Pres","Qi","Qui","Rava","Rey","Sha","Syl","Tor","Tris","Ula","Uri","Val","Ven","Wyn","Wysa","Xil","Xyr","Yes","Ylla","Zin","Zyl"));
    private List<String> f4 = new ArrayList<>(Arrays.asList("aela","aera","brara","thala","kasha","ianna","lia","zara","vara","via","tria","tha","drimme","ria","reth","vela","mila","banise","bella","caryn","cyne","di","dove","fiel","fina","gella","gwyn","hana","harice","jyre","kalyn","krana","lana","lee","leth","lynn","moira","mys","na","nala","phine","phyra","qirelle","ra","ralei","rel","rie","rieth","rona","rora","roris","satra","stina","sys","thana","thyra","tris","varis","vyre","wenys","wynn","xina","xisys","ynore","yra","zana","zorwyn"));

    private List<String> l = new ArrayList<>(Arrays.asList("aranore","balderk","bofdarm","brazzik","daerdahk","dankil","daraln","drakantal","durthane","fallack","garkalan","glanhig","gorunn","grimtor","helcral","holderhek","loderr","lutgehr","markolak","morigak","rakankrak","rumnaheim","skandalor","thrahak","torevir","torunn","ungart","zarkanan"));
    private List<String> l1 = new ArrayList<>(Arrays.asList("alean","alea","arabi","arkenea","auvrea","baequi","banni","cygreen","dirth","dryear","dwin","eyllis","eyther","freani","gysse","heasi","hlae","hunith","kennyr","kille","maern","melith","myrth","norre","orle","oussea","rilynn","teasen","tyr","tyrnea"));
    private List<String> l2 = new ArrayList<>(Arrays.asList("altin","anea","annia","aear","arnith","atear","athem","dlues","elvris","eplith","ettln","ghymn","itryn","lylth","mitore","nddare","neldth","rae","raheal","rretyn","sithek","thym","tlarn","tlithar","tylar","undlin","urdrenn","valsa","virrea","zea"));


    public String generate(String gender) {
        if (gender.equals("m")) {
            return male() + " " + sur();
        }
        else {
            return female() + " " + sur();
        }
    }

    private String male() {
        return cap(r(f1) + r(f2));
    }

    private String female() {
        return cap(r(f3) + r(f4));
    }

    private String sur() {
        if (random.nextInt(5)==1) {
            return cap(r(l));
        }
        else {
            return cap(r(l1)+r(l2));
        }
    }
}
