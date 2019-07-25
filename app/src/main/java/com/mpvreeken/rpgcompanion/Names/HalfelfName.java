package com.mpvreeken.rpgcompanion.Names;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sven on 12/18/2017.
 */

public class HalfelfName extends NameGenerator {
    private List<String> f1 = new ArrayList<>(Arrays.asList("Al","Aro","Bar","Bel","Cor","Cra","Dav","Dor","Eir","El","Fal","Fril","Gaer","Gra","Hal","Hor","Ian","Ilo","Jam","Kev","Kri","Leo","Lor","Mar","Mei","Nil","Nor","Ori","Os","Pan","Pet","Quo","Raf","Ri","Sar","Syl","Tra","Tyr","Uan","Ul","Van","Vic","Wal","Wil","Xan","Xav","Yen","Yor","Zan","Zyl"));
    private List<String> f2 = new ArrayList<>(Arrays.asList("avor","ben","borin","coril","craes","deyr","dithas","elor","enas","faelor","faerd","finas","fyr","gotin","gretor","homin","horn","kas","koris","lamir","lanann","lumin","minar","morn","nan","neak","neiros","orin","ovar","parin","phanis","qarim","qinor","reak","ril","ros","sariph","staer","torin","tumil","valor","voril","warith","word","xian","xiron","yeras","ynor","zaphir","zaren"));
    private List<String> f3 = new ArrayList<>(Arrays.asList("Alu","Aly","Ar","Bren","Byn","Car","Co","Dar","Del","El","Eli","Fae","Fha","Gal","Gif","Haly","Ho","Ile","Iro","Jen","Jil","Kri","Kys","Les","Lora","Ma","Mar","Mare","Neri","Nor","Ol","Ophi","Phaye","Pri","Qi","Que","Rel","Res","Sael","Saf","Syl","Ther","Tyl","Una","Uri","Ven","Vyl","Win","Wol","Xil","Xyr","Yes","Yll","Zel","Zin"));
    private List<String> f4 = new ArrayList<>(Arrays.asList("aerys","anys","bellis","bwynn","cerys","charis","diane","dove","elor","enyphe","faen","fine","galyn","gwynn","hana","hophe","kaen","kilia","lahne","lynn","mae","malis","mythe","nalore","noa","nys","ona","phira","pisys","qarin","qwyn","rila","rora","seris","stine","sys","thana","theris","tihne","trana","viel","vyre","walyn","waris","xaris","xipha","yaries","yra","zenya","zira"));

    private List<String> l1 = new ArrayList<>(Arrays.asList("alpen","amber","ash","autumn","axe","barley","battle","bear","black","blade","blaze","blood","blue","bone","boulder","bright","bronze","burning","cask","chest","cinder","clan","claw","clear","cliff","cloud","cold","common","coven","crag","crest","crow","crystal","dark","dawn","day","dead","death","deep","dew","dirge","distant","doom","down","dragon","dream","dusk","dust","eagle","earth","elf","ember","even","fallen","far","farrow","feather","fern","fire","fist","flame","flat","flint","fog","fore","forest","four","free","frost","frozen","full","fuse","gloom","glory","glow","gold","gore","grand","grass","gray","great","green","grizzly","hallow","hallowed","hammer","hard","haven","hawk","haze","heart","heavy","hell","high","hill","holy","honor","horse","humble","hydra","ice","iron","keen","laughing","leaf","light","lightning","lion","lone","long","low","luna","marble","marsh","master","meadow","mild","mirth","mist","molten","monster","moon","morning","moss","mountain","mourn","mourning","nether","nickle","night","noble","nose","oat","ocean","orb","pale","peace","phoenix","pine","plain","pride","proud","pyre","rage","rain","rapid","raven","red","regal","rich","river","rock","rose","rough","rumble","rune","sacred","sage","saur","sea","serpent","shade","shadow","sharp","shield","silent","silver","simple","single","skull","sky","slate","smart","snake","snow","soft","solid","spider","spirit","spring","stag","star","steel","stern","still","stone","storm","stout","strong","summer","sun","swift","tall","tarren","terra","three","thunder","titan","tree","true","truth","tusk","twilight","two","void","war","water","wheat","whisper","whit","white","wild","willow","wind","winter","wise","wolf","wood","wooden","wyvern","young"));
    private List<String> l2 = new ArrayList<>(Arrays.asList("arm","arrow","ash","axe","bane","bash","basher","beam","beard","belly","bend","bender","binder","blade","blaze","bleeder","blight","blood","bloom","blossom","blower","bluff","bone","bough","bow","brace","braid","branch","brand","breaker","breath","breeze","brew","bringer","brook","brooke","brow","caller","chaser","chewer","claw","cleaver","cloud","crag","creek","crest","crusher","cut","cutter","dancer","dane","dew","doom","down","draft","dream","dreamer","drifter","dust","eye","eyes","fall","fallow","fang","feather","fire","fist","flame","flare","flaw","flayer","flow","flower","follower","force","forest","forge","fury","gaze","gazer","gem","glade","gleam","glide","gloom","glory","glow","grain","grip","grove","guard","gust","hair","hammer","hand","heart","hell","helm","hide","horn","hunter","jumper","keep","keeper","killer","lance","lash","leaf","less","light","mane","mantle","mark","maul","maw","might","moon","more","mourn","oak","orb","ore","peak","pelt","pike","punch","rage","reaper","reaver","rider","ridge","ripper","river","roar","rock","root","run","runner","scar","scream","scribe","seeker","shade","shadow","shaper","shard","shield","shine","shot","shout","singer","sky","slayer","snarl","snout","snow","soar","song","sorrow","spark","spear","spell","spire","spirit","splitter","sprinter","stalker","star","steam","steel","stone","stream","strength","stride","strider","strike","striker","sun","surge","swallow","swift","sword","sworn","tail","taker","talon","thorn","thorne","tide","toe","track","trap","trapper","tree","vale","valor","vigor","walker","ward","watcher","water","weaver","whirl","whisk","whisper","willow","wind","winds","wing","wolf","wood","woods","wound"));


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
        return cap(r(l1)+r(l2));
    }
}
