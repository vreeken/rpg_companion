package com.mpvreeken.rpgcompanion.Names;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sven on 12/18/2017.
 */

public class GnomeName extends NameGenerator {
	//TODO needs more from Xanathur's guide, and lower-case and surname?
    private List<String> f1 = new ArrayList<>(Arrays.asList("Al","Ari","Bil","Bri","Cal","Cor","Dav","Dor","Eni","Er","Far","Fel","Ga","Gra","His","Hor","Ian","Ipa","Je","Jor","Kas","Kel","Lan","Lo","Man","Mer","Nes","Ni","Or","Oru","Pana","Po","Qua","Quo","Ras","Ron","Sa","Sal","Sin","Tan","To","Tra","Um","Uri","Val","Vor","War","Wil","Wre","Xal","Xo","Ye","Yos","Zan","Zil"));
    private List<String> f2 = new ArrayList<>(Arrays.asList("bar","ben","bis","corin","cryn","don","dri","fan","fiz","gim","grim","hik","him","ji","jin","kas","kur","len","lin","min","mop","morn","nan","ner","ni","pip","pos","rick","ros","rug","ryn","ser","ston","tix","tor","ver","vyn","win","wor","xif","xim","ybar","yur","ziver","zu"));
    private List<String> f3 = new ArrayList<>(Arrays.asList("Alu","Ari","Ban","Bree","Car","Cel","Daphi","Do","Eili","El","Fae","Fen","Fol","Gal","Gren","Hel","Hes","Ina","Iso","Jel","Jo","Klo","Kri","Lil","Lori","Min","My","Ni","Ny","Oda","Or","Phi","Pri","Qi","Que","Re","Rosi","Sa","Sel","Spi","Ta","Tifa","Tri","Ufe","Uri","Ven","Vo","Wel","Wro","Xa","Xyro","Ylo","Yo","Zani","Zin"));
    private List<String> f4 = new ArrayList<>(Arrays.asList("bi","bys","celi","ci","dira","dysa","fi","fyx","gani","gyra","hana","hani","kasys","kini","la","li","lin","lys","mila","miphi","myn","myra","na","niana","noa","nove","phina","pine","qaryn","qys","rhana","roe","sany","ssa","sys","tina","tra","wyn","wyse","xi","xis","yaris","yore","za","zyre"));

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
