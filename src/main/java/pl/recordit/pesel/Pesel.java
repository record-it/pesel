package pl.recordit.pesel;

import io.vavr.control.Either;
import io.vavr.control.Option;

import java.time.LocalDate;

public interface Pesel {

    Pesel INVALID = InvalidPesel.REF_TO_INVALID;

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

    static Option<InvalidPesel> toInvalidPesel(Pesel pesel){
        return Option.some(pesel)
                .filter(p -> p instanceof InvalidPesel)
                .map(p -> (InvalidPesel) p);
    }

    static Option<ValidPesel> toValidPesel(Pesel pesel){
        return Option.some(pesel)
                .filter(p -> p instanceof ValidPesel)
                .map(p -> (ValidPesel) p);
    }

    default boolean isInvalid(){
        if (this instanceof ValidPesel) {
            return this == INVALID;
        }
        return true;
    }

    default boolean isValid(){
        if(this instanceof ValidPesel){
            return this != INVALID;
        }
        return false;
    }
}
