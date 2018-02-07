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

    int wordLength = DEFAULT_WORD_LENGTH;

    private Random random = new Random();

    private HashSet wordSet = new HashSet();
    private HashMap<String, ArrayList<String>> lettersToWord = new HashMap<>();
    private HashMap<Integer, ArrayList<String>> sizeToWords = new HashMap<>();

    ArrayList<String> wordList = new ArrayList<>();

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;

        wordList = new ArrayList<>();

        while((line = in.readLine()) != null) {
            String word = line.trim();

            wordList.add(word);
            wordSet.add(word);

            String sortedWord = sortLetters(word);
            ArrayList<String> hashMapValue = new ArrayList<>();

            if (lettersToWord.containsKey(sortedWord)) {

                hashMapValue = lettersToWord.get(sortedWord);
                hashMapValue.add(word);

                lettersToWord.put(sortedWord, hashMapValue);

            } else {

                hashMapValue.add(word);
                lettersToWord.put(sortedWord, hashMapValue);
            }

            if (sizeToWords.containsKey(word.length())) {

                sizeToWords.get(word.length()).add(word);
            } else {

                ArrayList<String> wordLengthList = new ArrayList<>();
                wordLengthList.add(word);

                sizeToWords.put(word.length(), wordLengthList);
            }
        }
    }

    public boolean isGoodWord(String word, String base) {

        if (word.contains(base)) {

            return false;
        }

        if (wordSet.contains(word)) {

            return true;
        }

        return false;
    }

    public List<String> getAnagrams(String targetWord) {

        ArrayList<String> result = new ArrayList<String>();

        for (String word: wordList) {

            if (word.length() == targetWord.length() && sortLetters(word).equals(sortLetters(targetWord))) {

                result.add(word);
            }
        }

        return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();

        for (char letter = 'a'; letter <= 'z'; letter++) {
            if (lettersToWord.containsKey(sortLetters(word + letter))) {

                ArrayList<String> listAnagram = lettersToWord.get(sortLetters(word + letter));

                for (String listWord : listAnagram) {

                    if (isGoodWord(word, listWord)) {

                        result.add(listWord);
                    }

                }
            }
        }

        return result;
    }

    public String pickGoodStarterWord() {

        ArrayList<String> wordLengthList = sizeToWords.get(wordLength);

        int randomIndex = random.nextInt(wordLengthList.size());

        String checkGoodWord = wordLengthList.get(randomIndex);

        if (checkGoodWord.length() < MAX_WORD_LENGTH) {

            wordLength++;

            return checkGoodWord;
        }

        return "sport";
    }

    public String sortLetters(String fullString) {

        char[] charArray = fullString.toCharArray();
        Arrays.sort(charArray);

        String sortedString = new String(charArray);

        return sortedString;
    }
}
