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
            if (currentChar == '.'){
                returnString.append(getRandomChar());
            } else if (currentChar == '[') {
                ArrayList<Character> charSet = getSetWith(it);
                returnString.append(getRandomCharFrom(charSet));
            } else if (currentChar == '\\') {
                returnString.append(getLiteralFrom(it));
            } else {
                returnString.append(currentChar);
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

    private char getLiteralFrom(StringCharacterIterator iterator) {
        return iterator.next();
    }
}