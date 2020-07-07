package pl.recordit.pesel;

import io.vavr.control.Option;
import io.vavr.control.Validation;

import java.time.LocalDate;

final public class InvalidPesel implements Pesel{
    private final PeselError error;
    private final String pesel;

    protected static Pesel ofString(String pesel){
        Validation<PeselError, Pesel> opesel = ValidPesel.ofString(pesel).toValidation();
        return opesel.isValid() ? opesel.get() : new InvalidPesel(pesel, opesel.getError());
    }

    private InvalidPesel(String peselStr, PeselError error) {
        this.pesel = peselStr;
        this.error = error;
    }

    @Override
    public Option<LocalDate> getBirthDate() {
        return Option.none();
    }

    @Override
    public Gender getGender() {
        return Gender.UNKNOWN;
    }

    @Override
    public Option<String> getPesel() {
        return Option.some(pesel);
    }

    public PeselError getError() {
        return error;
    }

    @Override
    public String toString() {
        return "InvalidPesel{" +
                "error=" + error +
                ", pesel='" + pesel + '\'' +
                '}';
    }
}
