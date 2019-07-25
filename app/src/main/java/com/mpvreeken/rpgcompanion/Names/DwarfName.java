package com.mpvreeken.rpgcompanion.Names;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sven on 12/18/2017.
 */

public class DwarfName extends NameGenerator {
    private List<String> f1 = new ArrayList<>(Arrays.asList("ad","al","am","arm","baer","daer","bal","ban","bar","bel","ben","ber","bhal","bhar","bhel","bram","bran","brom","brum","bun","dal","dar","del","dol","dul","eb","em","erm","far","gal","gar","ger","gim","gral","gram","gran","grem","gren","gril","gry","gul","har","hjal","hjol","hjul","hor","hul","hur","kar","khar","kram","krom","krum","mag","mal","mel","mor","muir","mur","rag","ran","reg","rot","tak","thal","thar","thel","ther","tho","thor","thul","thur","thy","tor","ty","ulf","um","urm","von"));
    private List<String> f2 = new ArrayList<>(Arrays.asList("adin","bek","brek","dahr","dain","dal","dan","dar","dek","dir","dohr","dor","drak","dram","dren","drom","drum","drus","duhr","dur","dus","garn","gram","gran","grim","grom","gron","grum","grun","gurn","gus","iggs","kahm","kam","kohm","kom","kuhm","kum","kyl","man","mand","mar","mek","miir","min","mir","mond","mor","mun","mund","mur","mus","myl","myr","nam","nar","nik","nir","nom","num","nur","nus","nyl","rak","ram","ren","rig","rigg","rik","rim","rom","ron","rum","rus","ryl","tharm","tharn","thran","thrum","thrun"));
    private List<String> f3 = new ArrayList<>(Arrays.asList("an","ar","baer","bal","bar","bel","belle","bol","bon","bonn","braen","bral","bralle","bran","bren","bret","bril","brille","brol","bron","brul","bryl","brylle","bryn","bryt","byl","bylle","dag","daer","dear","del","dim","ed","ein","el","fal","fin","gem","ger","gwan","gwen","gwin","gwyn","gym","hel","ing","jen","jenn","jin","jyn","kait","kar","kat","kath","ket","kris","las","lass","les","less","lyes","lys","lyss","maer","mar","maev","mar","mis","mist","myr","mys","myst","naer","nal","nas","nass","nes","nis","nor","nur","nys","raen","ran","red","reyn","run","ryn","sann","sar","sol","tas","taz","tis","tish","tiz","tor","tys","tysh","ursh","val","whur","yurg"));
    private List<String> f4 = new ArrayList<>(Arrays.asList("belle","bera","delle","deth","dielle","dille","dish","dora","dryn","dyl","giel","glia","glian","gwyn","la","leen","leil","len","lin","linn","lyl","lyn","lynn","ma","mera","mora","mura","myl","myla","nan","nar","nas","nera","nia","nip","nis","niss","nora","nura","nyl","nys","nyss","ra","ras","res","ri","ria","rielle","rin","ris","ros","ryl","ryn","sael","selle","sora","syl","thel","thiel","tin","tyn","va","van","via","vian","waen","win","wyn","wynn"));

    private List<String> l = new ArrayList<>(Arrays.asList("aranore","balderk","bofdarm","brazzik","daerdahk","dankil","daraln","drakantal","durthane","fallack","garkalan","glanhig","gorunn","grimtor","helcral","holderhek","loderr","lutgehr","markolak","morigak","rakankrak","rumnaheim","skandalor","thrahak","torevir","torunn","ungart","zarkanan"));
    private List<String> l1 = new ArrayList<>(Arrays.asList("amber","barrel","battle","big","bitter","black","blood","blunt","bone","bottle","brave","brawn","brick","bright","bronze","brood","coal","copper","deep","earth","ever","fire","flint","foam","forge","frost","gold","gray","hammer","hard","iron","large","mountain","rock","ruby","silver","steel","stout","strong","thunder","true","white"));
    private List<String> l2 = new ArrayList<>(Arrays.asList("anvil","axe","back","bane","beard","belt","bender","braids","brow","chin","cloak","crown","delver","digger","finder","fist","forge","grip","grog","hammer","hand","harvest","helm","hide","mace","mail","maker","master","maul","miner","river","shaper","shard","sharp","shield","stone","tarn","toe"));


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
