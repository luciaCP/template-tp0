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

    private char getRandomChar() {
        Random ran = new Random();
        int randomInt = ran.nextInt(255);
        while (randomInt == 0) {
            randomInt = ran.nextInt(255);
        }
        return (char)randomInt;
    }

    private String generateStringFrom(String regEx) {
        StringBuilder returnString = new StringBuilder();

        StringCharacterIterator it = new StringCharacterIterator(regEx);
        char currentChar = it.first();
        while (currentChar != it.DONE) {
            if (currentChar == '.'){
                returnString.append(getRandomChar());
            }

            currentChar = it.next();
        }

        return returnString.toString();
    }
}