package pl.recordit.pesel;

import io.vavr.control.Validation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PeselTest {

    @Test
    void ofForNull(){
        //GIVEN
        String peselStr = null;
        //WHEN
        Validation<PeselError, Pesel> pesel = Pesel.of(peselStr).toValidation();
        //THEN
        assertTrue(pesel.isInvalid());
        assertEquals(PeselError.NULL, pesel.getError());
    }

    @Test
    void ofForNonDigitChar() {
        //GIVEN
        String peselStr = "4405140145L";
        //WHEN
        Validation<PeselError, Pesel> pesel = Pesel.of(peselStr).toValidation();
        //THEN
        assertTrue(pesel.isInvalid());
        assertEquals(PeselError.NON_DIGIT_CHAR, pesel.getError());
    }
    @Test
    void ofForInvalidLength() {
        //GIVEN
        String peselStr = "44051401";
        //WHEN
        Validation<PeselError, Pesel> pesel = Pesel.of(peselStr).toValidation();
        //THEN
        assertTrue(pesel.isInvalid());
        assertEquals(PeselError.INVALID_LENGTH, pesel.getError());
    }

    @Test
    void ofForInvalidControlDigitTest(){
        //GIVEN
        String peselStr = "44051401459";
        //WHEN
        Validation<PeselError, Pesel> pesel = Pesel.of(peselStr).toValidation();
        //THEN
        assertEquals(PeselError.INVALID_CONTROL_DIGIT,pesel.getError());
    }

    @Test
    void ofForValidControlDigitTest(){
        //GIVEN
        String peselStr = "44051401458";
        //WHEN
        Validation<PeselError, Pesel> pesel = Pesel.of(peselStr).toValidation();
        //THEN
        assertTrue(pesel.isValid());
    }

    @Test
    void ofForInvalidBirthDate(){
        //GIVEN
        String peselStr = "44444444444";
        //WHEN
        Validation<PeselError, Pesel> pesel = Pesel.of(peselStr).toValidation();
        //THEN
        assertTrue(pesel.isInvalid());
        assertEquals(PeselError.INVALID_BIRTH_DATE, pesel.getError());
    }

    @Test
    void ofForValid() {
        //GIVEN
        String peselStr = "44051401458";
        //WHEN
        Validation<PeselError, Pesel> pesel = Pesel.of(peselStr).toValidation();
        //THEN
        assertNotNull(pesel.get());
        assertTrue(pesel.isValid());
        assertFalse(pesel.isEmpty());
        assertEquals(peselStr, pesel.get().getPesel().get());
    }

    @Test
    void getBirthDate(){
        //GIVEN
        String peselStr = "44051401458";
        //WHEN
        Validation<PeselError, Pesel> pesel = Pesel.of(peselStr).toValidation();
        //THEN
        String result = pesel.map(Pesel::getBirthDate)
                .fold(PeselError::getErrorMessage, date -> date.get().toString());
        assertEquals("1944-05-14", result);
    }

    @Test
    void ofNoError(){
        //GIVEN
        String peselStr = "44051401458";
        //WHEN
        Pesel pesel = Pesel.ofNoError(peselStr);
        //THEN
        assertTrue(pesel.isValid());
        assertFalse(pesel.isInvalid());
        assertTrue(pesel.getBirthDate().isDefined());
        assertEquals(Gender.MALE,pesel.getGender());
        assertEquals(peselStr,pesel.getPesel().get());

        //GIVEN
        peselStr = "44051401457";
        //WHEN
        pesel = Pesel.ofNoError(peselStr);
        //THEN
        assertTrue(pesel.isInvalid());
        assertFalse(pesel.isValid());
        assertTrue(pesel.getBirthDate().isEmpty());
        assertEquals(Gender.UNKNOWN,pesel.getGender());
        assertTrue(pesel.getPesel().isEmpty());
    }

    @Test
    void ofBoth(){
        //GIVEN
        String peselStr = "44051401458";
        //WHEN
        Pesel pesel = Pesel.ofBoth(peselStr);
        //THEN
        assertTrue(pesel instanceof ValidPesel);
        assertTrue(pesel.isValid());
        assertTrue(pesel.getBirthDate().isDefined());
        assertEquals(Gender.MALE,pesel.getGender());
        assertEquals(peselStr,pesel.getPesel().get());

        //GIVEN
        peselStr = "44051401457";
        //WHEN
        pesel = Pesel.ofBoth(peselStr);
        //THEN
        assertTrue(pesel instanceof InvalidPesel);
        assertTrue(pesel.isInvalid());
        assertFalse(pesel.isValid());
        assertTrue(pesel.getBirthDate().isEmpty());
        assertEquals(Gender.UNKNOWN,pesel.getGender());
        assertEquals("44051401457",pesel.getPesel().get());
        assertEquals(PeselError.INVALID_CONTROL_DIGIT, pesel.toInvalidPesel().map(InvalidPesel::getError).get());
    }
}