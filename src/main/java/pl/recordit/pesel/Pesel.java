package pl.recordit.pesel;

import io.vavr.control.Either;
import io.vavr.control.Option;

import java.time.LocalDate;

public interface Pesel {
    Option<LocalDate> getBirthDate();
    Gender getGender();
    Option<String> getPesel();

    static Either<PeselError, Pesel> of(String pesel){
        return ValidPesel.ofString(pesel);
    }

    static Pesel ofBoth(String pesel){
        return InvalidPesel.ofString(pesel);
    }

    static Pesel ofNoError(String pesel){
        return ValidPesel.ofString(pesel).toValidation().getOrElse(ValidPesel.INVALID);
    }

    static Option<InvalidPesel> mapToInvalidPesel(Pesel pesel){
        return Option.some(pesel)
                .filter(p -> p.getBirthDate().isEmpty())
                .map(p -> (InvalidPesel) p);
    }
}
