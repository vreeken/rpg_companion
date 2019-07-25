package com.mpvreeken.rpgcompanion.Names;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sven on 12/18/2017.
 */

public class DragonbornName extends NameGenerator {
    private List<String> f1 = new ArrayList<>(Arrays.asList("ad","ali","ar","azz","ba","bal","bel","bhar","bid","bren","caer","calu","dad","dazz","dir","do","don","dra","dur","era","fa","faer","fro","gar","ghe","gora","gorb","gre","gree","he","hes","hi","il","ior","jar","jin","kal","ker","kil","kriv","lor","lumi","maag","mar","med","mor","moz","mre","mug","na","nad","nar","nes","nith","nor","nyk","orla","oti","pa","pan","pat","pri","qel","quar","ras","rath","ravo","rho","riv","sa","seth","sha","sham","shed","sror","sul","tar","taz","to","tor","trin","trou","udo","uro","val","vor","vrak","vron","vyu","wor","wra","wu","wul","xar","yor","zed","zor","zra"));
    private List<String> f2 = new ArrayList<>(Arrays.asList("barum","bor","broth","ciar","crath","daar","dhall","dinn","diss","dorim","drex","farn","fras","gar","ghull","grax","hadur","hazar","inn","ith","ikth","jhan","jirik","jurn","kax","kan","kran","kris","kul","lasar","lin","mash","morn","naar","orean","prax","qiroth","qrin","qull","rakas","rash","rinn","roth","sashi","seth","skan","then","trin","turim","varax","vroth","vull","warum","wunax","xan","xiros","yax","ythas","zavur","zire","ziros"));
    private List<String> f3 = new ArrayList<>(Arrays.asList("a","aasa","ak","an","ari","bel","bi","blen","bur","ca","chass","cris","da","daar","drys","egg","erli","esh","fae","far","fen","fin","furr","ges","gil","gri","gur","ha","har","hav","heth","hill","hin","irie","irly","ja","jes","jez","jo","ka","kel","ko","lilo","lora","mal","meg","mi","mij","mish","na","nal","nes","nuth","nys","o","ophi","ori","per","phi","pog","qi","ques","quil","rai","rashi","rez","ru","saph","so","sor","su","sur","tha","thav","ther","uri","ushi","val","vez","vyra","welsi","wra","xis","xy","ya","yr","zen","zof","zyk"));
    private List<String> f4 = new ArrayList<>(Arrays.asList("ana","ann","ara","birith","bis","bith","coria","cys","dalynn","dar","drish","drith","faeth","fyire","gil","gissa","gwen","hime","hymm","jit","karyn","kass","kira","lar","larys","liann","loth","lyassa","meila","myse","norae","nys","patys","pora","qorel","qwen","ra","rann","ranix","ren","riel","rina","rinn","rish","rith","saadi","shann","sira","thibra","thyra","va","vayla","vyre","vys","wophyl","wyn","xiris","xora","yassa","yries","zita","zys"));
    private List<String> l1 = new ArrayList<>(Arrays.asList("arg","b","bah","ber","c","cl","cr","d","daar","dr","ess","f","g","k","ker","kl","kr","l","m","my","n","ny","pf","pr","q","sh","t","th","v","y","",""));
    private List<String> l2 = new ArrayList<>(Arrays.asList("a","a","a","a","a","a","aa","e","e","e","e","e","e","ea","i","i","i","i","i","i","ii","ia","o","o","o","u","u","u","ua","uu"));
    private List<String> l3 = new ArrayList<>(Arrays.asList("c","cc","ch","cht","llh","lm","lk","lx","ld","lr","ldr","lt","lth","mb","mm","mp","mph","mr","mt","nk","nx","nc","p","ph","r","rd","rj","rn","rrh","rth","st","tht","x"));
    private List<String> l4 = new ArrayList<>(Arrays.asList("c","cm","cn","d","j","k","km","l","n","nn","nd","ndr","nk","nsht","ngn","nth","r","s","shr","sht","shkm","st","sst","t","th","x"));
    private List<String> l5 = new ArrayList<>(Arrays.asList("d","j","l","ll","m","n","nd","nsst","rg","r","rr","rd"));
    private List<String> l6 = new ArrayList<>(Arrays.asList("aax","c","d","gh","iax","iir","is","ish","k","l","n","ost","r","s","sh","st","th","uul","yn"));



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
        if (random.nextInt(10)%2==0) {
            return cap(r(l1)+r(l2)+r(l3)+r(l6));
        }
        else if (random.nextInt(10)%2==0) {
            return cap(r(l1)+r(l2)+r(l3)+r(l2)+r(l4)+r(l2)+r(l6));
        }
        else {
            return cap(r(l1)+r(l2)+r(l3)+r(l2)+r(l4)+r(l2)+r(l5)+r(l2)+r(l6));
        }
    }
}