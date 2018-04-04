/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private ArrayList<String> wordList;
    private HashSet<String> wordSet;
    private  HashMap<String, ArrayList> lettersToWord;



    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        ArrayList<String> temp = new ArrayList<String>();
        wordList = new ArrayList<>();
        wordSet = new HashSet<>();
        lettersToWord = new HashMap<>();
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordSet.add(word);
            wordList.add(word);
            String sortedWord = sortLetters(word);
            if(lettersToWord.containsKey(sortedWord)){
                temp.add(word);
            }else{
                temp = new ArrayList<String>();
                temp.add(word);
            }
            lettersToWord.put(sortedWord, temp);
        }
    }

    public boolean isGoodWord(String word, String base) {
        if(wordSet.contains(word)){
            int baseLength = base.length();
            for(int i = 0; i < word.length() - baseLength; i++)
            {
                if(base.equals(word.substring(i,i+ baseLength))){
                    return false;
                }
            }
            return true;
        }
        return true;
    }

    public List<String> getAnagrams(String targetWord) {
        /*ArrayList<String> result = new ArrayList<String>();
        targetWord = sortLetters(targetWord);
        int len = targetWord.length();
        for(String words : wordList){
            if(words.length() == len && sortLetters(words).equals(targetWord)){
                result.add(words);
            }
        }*/
        return lettersToWord.get(sortLetters(targetWord));
    }
    public String sortLetters(String word){
        //Convert String to character Array
        char tempArray[] = word.toCharArray();
        //Sort the array
        Arrays.sort(tempArray);
        //return new sorted String
        return new String(tempArray);
    }
    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        char[] alphabets = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        for(char letter: alphabets){
            if(lettersToWord.containsKey(sortLetters(word+letter))){
                ArrayList<String> tempArray = lettersToWord.get(sortLetters(word+letter));
                for(String anagramWord : tempArray){
                    Log.i("game",anagramWord);
                    result.add(anagramWord);
                }
            }
        }
        return result;
    }

    public String pickGoodStarterWord() {
        return "stake";
    }
}
