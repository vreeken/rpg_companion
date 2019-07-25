package com.mpvreeken.rpgcompanion.Names;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sven on 12/18/2017.
 */

public class HalflingName extends NameGenerator {

    private List<String> fm = new ArrayList<>(Arrays.asList("Adalgrim","Adelard","Adelbert","Adelgrim","Alaric","Alberic","Alton","Andwise","Ansegar","Anson","Arnor","Athanaric","Balbo","Bandobras","Basso","Beau","Berilac","Bildad","Bill","Bingo","Bladud","Blanco","Bodo","Bolger","Bosco","Bowman","Briffo","Bruno","Brutus","Bucca","Bungo","Cade","Calkin","Caradas","Caradoc","Carambo","Carl","Cedivar","Celedor","Cerdic","Ceredic","Conrad","Cosimo","Cotman","Cottar","Dinodas","Doderic","Dodinas","Drogo","Dudo","Eldon","Elfstan","Erling","Everard","Falco","Faramir","Faramond","Fastolph","Fastred","Ferdinand","Ferumbras","Filibert","Flambard","Folcard","Folco","Fortinbras","Fosco","Fredegar","Fulvus","Garret","Genrill","Gerontius","Goodwill","Gorbadoc","Gorbulas","Gorhendad","Gormadoc","Griffo","Gringamor","Grossman","Gruffo","Guido","Gundabald","Gundahar","Gundobad","Gundolpho","Habaccuc","Haiduc","Hal","Halfred","Hamfast","Hamilcar","Harding","Hending","Heribald","Herugar","Hildibrand","Hildifons","Hildigard","Hildigrim","Hob","Hobson","Holfast","Holman","Hugo","Iago","Ilberic","Inigo","Iolo","Isembard","Isembold","Isengar","Isengrim","Isengrin","Isumbras","Jago","Jo","Jolly","Kepli","Largo","Longo","Lotho","Lyle","Magnus","Marcho","Marco","Marmadoc","Marroc","Meneaduc","Meriadoc","Merimac","Merimas","Merry","Milo","Minto","Moro","Morro","Mosco","Mungo","Nibs","Nick","Nicol","Nob","Obo","Odo","Odovacar","Olo","Orgulas","Orlando","Osborn","Otho","Paladin","Paldo","Peregrin","Pervince","Pimpernell","Polo","Ponto","Porro","Porto","Posco","Reginard","Robin","Robur","Roderic","Rollo","Ronald","Rorimac","Roscoe","Rudibert","Rudigar","Rudolph","Rufus","Sadoc","Sago","Sam","Samlad","Sancho","Saradac","Saradas","Saradoc","Scudamor","Seredic","Seredoc","Sigismond","Tango","Ted","Theadric","Theobald","Theodoric","Tobias","Tobold","Togo","Tolman","Tomacca","Uffo","Valdemar","Vigo","Wellby","Wilcome","Wilibald","Wilimar","Will"));
    private List<String> f1 = new ArrayList<>(Arrays.asList("An","Ar","Bar","Bel","Con","Cor","Dan","Dav","El","Er","Fal","Fin","Flyn","Gar","Go","Hal","Hor","Ido","Ira","Jan","Jo","Kas","Kor","La","Lin","Mar","Mer","Ne","Nor","Ori","Os","Pan","Per","Pim","Quin","Quo","Ri","Ric","San","Shar","Tar","Te","Ul","Uri","Val","Vin","Wen","Wil","Xan","Xo","Yar","Yen","Zal","Zen"));
    private List<String> f2 = new ArrayList<>(Arrays.asList("ace","amin","bin","bul","dak","dal","der","don","emin","eon","fer","fire","gin","hace","horn","kas","kin","lan","los","min","mo","nad","nan","ner","orin","os","pher","pos","ras","ret","ric","rich","rin","ry","ser","sire","ster","ton","tran","umo","ver","vias","von","wan","wrick","yas","yver","zin","zor","zu"));

    private List<String> ff = new ArrayList<>(Arrays.asList("Adaldrida","Alfrida","Amalda","Amaranth","Amaryllis","Amethyst","Amranth","Angelica","Arabella","Asphodel","Aspodel","Belba","Belinda","Bell","Belladonna","Bellisima","Bertha","Berylla","Camelia","Camellia","Cara","Caramella","Carissa","Celandine","Celendine","Charmaine","Chica","Cora","Cornelia","Crystal","Daisy","Diamanda","Diamond","Dina","Donamira","Donnamira","Dora","Duenna","Eglantine","Elanor","Esmeralda","Esmerelda","Estella","Euphemia","Fatima","Gerda","Gilly","Gloriana","Goldilocks","Grimalda","Gwiston","Hilda","Jasmine","Jemima","Jessamine","Jillian","Lalia","Laura","Lavinia","Lidda","Lily","Linda","Lobelia","Malva","Mantissa","Marigold","Matilda","Maxima","May","Melba","Melilot","Melindy","Melissa","Menegilda","Mentha","Merla","Mimosa","Mirabella","Miranda","Myrtle","Nina","Nora","Pamphila","Pandora","Pansy","Pearl","Pedderee","Peony","Pervinca","Petrilly","Pimpernel","Poppy","Portia","Prima","Primrose","Primula","Prisca","Regina","Rhoda","Robinia","Rosa","Rosamunda","Rose","Rowan","Ruby","Salvia","Savanna","Selina","Semolina","Seraphina","Susannah","Tanta","Verna","Violet","Yolanda"));
    private List<String> f3 = new ArrayList<>(Arrays.asList("An","Ari","Bel","Bre","Cal","Chen","Dar","Dia","Ei","Eo","Eli","Era","Fay","Fen","Fro","Gel","Gra","Ha","Hil","Ida","Isa","Jay","Jil","Kel","Kith","Le","Lid","Mae","Mal","Mar","Ne","Ned","Odi","Ora","Pae","Pru","Qi","Qu","Ri","Ros","Sa","Shae","Syl","Tham","Ther","Tryn","Una","Uvi","Va","Ver","Wel","Wi","Xan","Xi","Yes","Yo","Zef","Zen"));
    private List<String> f4 = new ArrayList<>(Arrays.asList("alyn","ara","brix","byn","caryn","cey","da","dove","drey","elle","eni","fice","fira","grace","gwen","haly","jen","kath","kis","leigh","la","lie","lile","lienne","lyse","mia","mita","ne","na","ni","nys","ola","ora","phina","prys","rana","ree","ri","ris","sica","sira","sys","tina","trix","ula","vira","vyre","wyn","wyse","yola","yra","zana","zira"));

    private List<String> l = new ArrayList<>(Arrays.asList("Amster","Ashworthy","Bandawax","Boffin","Bolger","Bracegirdle","Brownlock","Brushgather","Bullroarer","Bunce","Burrows","Chubb","Cotton","Dale","Dudley","Gammidge","Gamwich","Gardner","Goodbarrel","Goodbody","Greenbottle","Greenspan","Grub","Hamson","Heathertoe","Highhill","Hilltopple","Hornblower","Jallisall","Kaese","Kalliwart","Leagallow","Lindenbrook","Marmidas","Melilot","Millbridge","Milliciny","Montajay","Newtan","Oldfur","Orgulas","Ostgood","Overhill","Quettory","Shortwick","Sire","Talbot","Tealeaf","Thorngage","Tighfield","Tosscobble","Trill","Underbough","Weatherbee"));
    private List<String> l1 = new ArrayList<>(Arrays.asList("Amber","Brown","Cold","Crazy","Curly","Dew","Earth","Far","Fast","Fat","Fire","Flow","Forest","Free","Glitter","Good","Great","Green","Hairy","Honor","Healthy","Home","Hot","Laughing","Lightning","Little","Many","Moon","Night","Nimble","Pine","Plump","Pretty","Quick","Rain","Road","Running","Scatter","Shadow","Silver","Simple","Sky","Slow","Sly","Smooth","Spring","Sprout","Stout","Summer","Sun","Swift","Tall","Travelling","True","Under","Warm","Water","Wet","Wild"));
    private List<String> l2 = new ArrayList<>(Arrays.asList("ale","arrow","body","bones","bottom","bread","brother","burrow","caller","cloak","digger","drum","eye","fellow","fingers","flower","foot","fox","ghost","goat","gold","grass","grip","hand","head","heart","hearth","hill","horn","lady","leaf","letters","light","maker","man","map","mind","one","pipe","shadow","shaker","shine","sister","skin","sleep","stick","stoat","swan","sun","talker","taunt","tender","wanderer","ward","weed","will","wind","wish","wit","wolf"));


    public String generate(String gender) {
        if (gender.equals("m")) {
            return male() + " " + sur();
        }
        else {
            return female() + " " + sur();
        }
    }

    private String male() {
        if (random.nextInt(3)==1) {
            return cap(r(fm));
        }
        else {
            return cap(r(f1) + r(f2));
        }
    }

    private String female() {
        if (random.nextInt(3)==1) {
            return cap(r(ff));
        }
        else {
            return cap(r(f3) + r(f4));
        }
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
