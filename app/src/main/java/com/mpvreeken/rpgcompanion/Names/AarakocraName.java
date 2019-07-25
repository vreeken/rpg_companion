package com.mpvreeken.rpgcompanion.Names;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sven on 12/18/2017.
 */

public class AarakocraName extends NameGenerator {
    //TODO needs work
    private List<String> n1 = new ArrayList<>(Arrays.asList("c","cl","cr","d","g","gr","h","k","kh","kl","kr","q","qh","ql","qr","r","rh","s","y","z","","","","",""));
    private List<String> n2 = new ArrayList<>(Arrays.asList("a","a","a","a","a","a","a","ae","aia","e","e","e","e","e","e","e","ee","i","i","i","i","i","i","i","oo","ou","u","u","u","u","u","u","u","ua","uie"));
    private List<String> n3 = new ArrayList<>(Arrays.asList("c","cc","k","kk","l","ll","q","r","rr"));
    private List<String> n4 = new ArrayList<>(Arrays.asList("a","a","a","a","a","aa","e","e","e","e","e","ea","ee","i","i","i","i","i","ia","ie"));
    private List<String> n5 = new ArrayList<>(Arrays.asList("c","ck","d","f","g","hk","k","l","r","rr","rc","rk","rrk","s","ss","","","",""));

    public String generate(String gender) {
        if (random.nextInt(10)%2==0) {
            return cap(r(n1)+r(n2)+r(n5));
        }
        else {
            return cap(r(n1)+r(n2)+r(n3)+r(n4)+r(n5));
        }
    }
}
