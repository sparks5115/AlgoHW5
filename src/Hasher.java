import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.*;
import java.util.regex.*;

public class Hasher {

    File file;
    Scanner sc;
    String[] table = new String[1000];
    int[] trueHash = new int[1000];
    Pattern word = Pattern.compile("[^a-zA-Z'-]*([a-zA-Z'-]+).*");


    Hasher(File file) throws FileNotFoundException {
        this.file = file;
        this.sc = new Scanner(file);
    }

    int hash(String s){
        char[] w = s.toCharArray();
        int h = 0;
        for(int i = 0; i<s.length(); i++){
            h=(h*123+(int)w[i])%1000;
        }
        return h;
    }

    void put(String s){
        int key = hash(s);
        if(table[key]!=null && table[key].equals(s)){return;}//removes duplicates
        if (table[key] == null) {//checks intended spot
            table[key] = s;
            trueHash[key]++;
        }else{
            for(int i = key+1; i!=key; i++) {
                if (i >= table.length - 1) {//loops to beginning of list
                    i = 0;
                }
                if (table[i] != null && table[i].equals(s)) {
                    return;
                }//removes duplicates
                if (table[i] == null) {//finds next open spot to place string
                    table[i] = s;
                    trueHash[key]++;
                    return;
                }
            }System.out.print("Table is Full!");
        }
    }

    void putAll(){
        while(sc.hasNext(word)){
            Matcher m = word.matcher(sc.next(word));
            if(m.find()) {
                //System.out.println(m.group(1));
                put(m.group(1));
            }
        }
        printA();
    }

    void printA(){
        System.out.println("Hash Address, Hashed Word, Hash Value of Word");
        int empty = 0;
        int used = 0;
        int eStreak = 0;
        int eMax=0;
        int eLoc=0;
        int cluster = 0;
        int cMax=0;
        int cLoc=0;
        for (int i = 0; i < table.length; i++) {
            String word;
            int hash;
            try{
                word = table[i];
                hash = hash(word);
                used++;
                cluster++;
                if(eStreak>eMax){
                    eMax=eStreak;
                    eLoc=i-eStreak;
                }eStreak=0;
            }
            catch(Exception e){
                word = "----";
                hash = -1;
                empty++;
                eStreak++;
                if(cluster>cMax){
                    cMax=cluster;
                    cLoc=i-cluster;
                }cluster=0;
            }
            System.out.printf("%d   %s   %d\n", i, word, hash);
        }System.out.printf("\n\nThere were %d filled spots and %d empty ones.\n", used, empty);
        System.out.println("That makes a load factor of "+ Math.round((double)used*100/table.length) + "%");
        System.out.printf("\nThe longest empty streak was %d long starting at index %d\n", eMax, eLoc);
        System.out.printf("The longest cluster was %d long starting at index %d\n", cMax, cLoc);
        maxA(trueHash);
        maxDisplacement();


    }

    void maxA(int[] a){
        int m = 0;
        int l = 0;
        for(int i=0; i<a.length; i++){
            if(a[i]>m){
                m=a[i];
                l=i;
            }
        }System.out.printf("The most common hash was %d with %d words hashing to it", l,m);
    }

    void maxDisplacement(){
        int max = -1;
        String word = "Null";
        for(int i=0; i<table.length; i++){
            if(table[i]!=null){
                int dist = i - hash(table[i]);
                if(dist>max){
                    max=dist;
                    word = table[i];
                }
            }

        }System.out.printf("\n The most displaced word was %s with displacement of %d", word, max);
    }


}
