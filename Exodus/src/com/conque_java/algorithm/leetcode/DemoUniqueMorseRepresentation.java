package com.conque_java.algorithm.leetcode;

import java.util.HashSet;

/**
 * International Morse Code defines a standard encoding where each letter is mapped to a series of dots and dashes, as follows: "a" maps to ".-", "b" maps to "-...", "c" maps to "-.-.", and so on.
 * For convenience, the full table for the 26 letters of the English alphabet is given below:
 * [".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--.."]
 * Now, given a list of words, each word can be written as a concatenation of the Morse code of each letter. For example, "cba" can be written as "-.-.-....-", (which is the concatenation "-.-." + "-..." + ".-").
 * We'll call such a concatenation, the transformation of a word.
 *
 * Return the number of different transformations among all words we have.
 */
public class DemoUniqueMorseRepresentation {
    private static final String[] morseCodes = {".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--.."};
    public static int uniqueMorseRepresentation(String[] words) {
        HashSet set = new HashSet();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < word.length(); j++)
                sb.append(morseCodes[word.charAt(j) - 'a']);
            set.add(sb.toString());  // StringBuffer|StringBuilder没有override重写equals()方法，因此set.add(sb)无法去重。
        }
        System.out.println(set);
        return set.size();
    }

    public static void main(String[] args) {
        String[] input = {"gin", "zen", "gig", "msg"};
        System.out.println(uniqueMorseRepresentation(input));
    }
}
