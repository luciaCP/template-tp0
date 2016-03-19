package ar.fiuba.tdd.template.tp0;

import sun.rmi.runtime.Log;

import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RegExGenerator {
    private int maxLength;

    public RegExGenerator(int maxLength) {
        this.maxLength = maxLength;
    }

    public List<String> generate(String regEx, int numberOfResults) {
        ArrayList<String> returnArray =  new ArrayList<String>();

        for (int iterator = 0; iterator < numberOfResults; iterator++){
            returnArray.add(generateStringFrom(regEx));
        }

        System.out.println(returnArray);
        return returnArray;
    }

    private String generateStringFrom(String regEx) {
        StringBuilder returnString = new StringBuilder();

        StringCharacterIterator it = new StringCharacterIterator(regEx);
        char currentChar = it.first();
        while (currentChar != it.DONE) {
            int randomInt = getRandomIntFromCuantifier(it);

            if (currentChar == '.') {
                if (randomInt != -1) {
                    for (int i = 0; i < randomInt; i++) {
                        returnString.append(getRandomChar());
                    }
                } else {
                    returnString.append(getRandomChar());
                }
            } else if (currentChar == '[') {
                ArrayList<Character> charSet = getSetWith(it);
                randomInt = getRandomIntFromCuantifier(it);
                if (randomInt != -1) {
                    for (int i = 0; i < randomInt; i++) {
                        returnString.append(getRandomCharFrom(charSet));
                    }
                } else {
                    returnString.append(getRandomCharFrom(charSet));
                }
            } else if (currentChar == '\\') {
                char literal = getLiteralFrom(it);
                if (randomInt != -1) {
                    for (int i = 0; i < randomInt; i++) {
                        returnString.append(literal);
                    }
                } else {
                    returnString.append(literal);
                }
            } else {
                if (randomInt != -1) {
                    for (int i = 0; i < randomInt; i++) {
                        returnString.append(currentChar);
                    }
                } else {
                    returnString.append(currentChar);
                }
            }

            if (randomInt != -1) {
                it.next(); // jump on cuantifier char
            }
            currentChar = it.next();
        }

        return returnString.toString();
    }

    private ArrayList<Character> getSetWith(StringCharacterIterator iterator) {
        ArrayList<Character> randomCharList = new ArrayList<Character>();
        char currentChar = iterator.next();
        while (currentChar != ']') {
            randomCharList.add(currentChar);
            currentChar = iterator.next();
        }
        return  randomCharList;
    }

    private char getRandomChar() {
        Random ran = new Random();
        int randomInt = 1 + ran.nextInt(254);
        return (char)randomInt;
    }

    private char getRandomCharFrom(ArrayList<Character> randomSet) {
        Random ran = new Random();
        return randomSet.get(ran.nextInt(randomSet.size()));
    }

    private int getRandomIntFromCuantifier(StringCharacterIterator iterator) {
        int returnValue = -1;
        if (nextCharacterIsCuantifier(iterator)) {
            char cuantifier = iterator.next();
            int max = getMaxCharacters(cuantifier);
            int min = getMinCharacters(cuantifier);
            Random ran = new Random();
            returnValue = min + ran.nextInt(max);
            iterator.previous();
        }

        return returnValue;
    }

    private int getMaxCharacters(char cuantifierChar) {
        if (cuantifierChar == '?') {
            return 1;
        } else if ((cuantifierChar == '*') || (cuantifierChar == '+')){
            return maxLength;
        }
        return 0;
    }

    private int getMinCharacters(char cuantifierChar) {
        if ((cuantifierChar == '*') ||(cuantifierChar == '?')) {
            return 0;
        } else if (cuantifierChar == '+'){
            return 1;
        }
        return 0;
    }
    private boolean nextCharacterIsCuantifier(StringCharacterIterator iterator) {
        char nextChar = iterator.next();
        iterator.previous();
        return ((nextChar == '?') || (nextChar == '*') || (nextChar == '+'));
    }

    private char getLiteralFrom(StringCharacterIterator iterator) {
        return iterator.next();
    }
}