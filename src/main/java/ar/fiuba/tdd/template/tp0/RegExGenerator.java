package ar.fiuba.tdd.template.tp0;

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

        return returnArray;
    }

    private String generateStringFrom(String regEx) {
        StringBuilder returnString = new StringBuilder();

        StringCharacterIterator it = new StringCharacterIterator(regEx);
        char currentChar = it.first();
        while (currentChar != it.DONE) {
            if (currentChar == '.'){
                returnString.append(getRandomChar());
            }

            if (currentChar == '[') {
                ArrayList<Character> charSet = getSetWith(it);
                returnString.append(getRandomCharFrom(charSet));
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
        int randomInt = ran.nextInt(255);
        while (randomInt == 0) {
            randomInt = ran.nextInt(255);
        }
        return (char)randomInt;
    }

    private char getRandomCharFrom(ArrayList<Character> randomSet) {
        Random ran = new Random();
        return randomSet.get(ran.nextInt(randomSet.size()));
    }

}