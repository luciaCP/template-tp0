package ar.fiuba.tdd.template.tp0;

import org.junit.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

public class RegExGeneratorTest {

    private boolean validate(String regEx, int numberOfResults) {
        RegExGenerator generator = new RegExGenerator(255);
        List<String> results = generator.generate(regEx, numberOfResults);
        // force matching the beginning and the end of the strings
        Pattern pattern = Pattern.compile("^" + regEx + "$");
        return results
                .stream()
                .reduce(true,
                    (acc, item) -> {
                        Matcher matcher = pattern.matcher(item);
                        return acc && matcher.find();
                    },
                    (item1, item2) -> item1 && item2);
    }

    @Test
    public void testAnyCharacter() {
        assertTrue(validate(".", 1));
    }

    @Test
    public void testMultipleCharacters() {
        assertTrue(validate("...", 1));
    }

    @Test
    public void testLiteral() {
        assertTrue(validate("\\@", 1));
    }

    @Test
    public void testLiteralDotCharacter() {
        assertTrue(validate("\\@..", 1));
    }

    @Test
    public void testZeroOrOneCharacter() {
        assertTrue(validate("\\@.h?", 1));
    }

    @Test
    public void testCharacterSet() {
        assertTrue(validate("[abc]", 1));
    }

    @Test
    public void testCharacterSetWithQuantifiers() {
        assertTrue(validate("[abc]+", 1));
    }

    /*
    *  New tests
    * */

    @Test
    public void testNoneString() {
        assertTrue(validate("...", 0));
    }

    @Test
    public void testAnyMultipleCharacters() {
        assertTrue(validate(".....", 6));
    }

    @Test
    public void testMultipleCharacterSet() {
        assertTrue(validate("[abc]", 3));
    }

    @Test
    public void testCharacterSet2() {
        assertTrue(validate("[!cy^]", 1));
    }

    @Test
    public void testCuantifierZeroToOneInLiterals() {
        assertTrue(validate("a?$?@?", 1));
    }

    @Test
    public void testCuantifierZeroToOneInLiteralsWithEscapeCharacter() {
        assertTrue(validate("\\a?\\$?\\@?", 1));
    }
/*
    @Test
    public void testCuantifierZeroToMaxInLiterals() {
        assertTrue(validate("a*$*@*", 1));
    }

    @Test
    public void testCuantifierZeroToMaxInLiteralsWithEscapeCharacter() {
        assertTrue(validate("\\a*\\$*\\@*", 1));
    }

    @Test
    public void testCuantifierOneToMaxInLiterals() {
        assertTrue(validate("a+$+@+", 1));
    }

    @Test
    public void testCuantifierOneToMaxInLiteralsWithEscapeCharacter() {
        assertTrue(validate("\\a+\\$+\\@+", 1));
    }
*/
    @Test
    public void testCuantifiersLikeLiterals() {
        assertTrue(validate("\\?", 1));
        assertTrue(validate("\\*", 1));
        assertTrue(validate("\\+", 1));
    }
}
