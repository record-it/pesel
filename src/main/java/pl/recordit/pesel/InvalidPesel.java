package pl.recordit.pesel;

import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Validation;

import java.time.LocalDate;

final public class InvalidPesel<E> implements Pesel{
    private final E error;
    private final String pesel;

    public static Pesel of(String pesel){
        Validation<PeselError, Pesel> opesel = ValidPesel.of(pesel).toValidation();
        return opesel.isValid() ? opesel.get() : new InvalidPesel<>(pesel, opesel.getError());
    }

    private InvalidPesel(String peselStr, E error) {
        this.pesel = peselStr;
        this.error = error;
    }

    @Override
    public Option<LocalDate> getBirthDate() {
        return Option.none();
    }

    @Override
    public ValidPesel.Gender getGender() {
        return ValidPesel.Gender.UNKNOWN;
    }

    @Override
    public String getPesel() {
        return pesel + error;
    }
}
