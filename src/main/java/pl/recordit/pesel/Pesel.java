package pl.recordit.pesel;

import io.vavr.control.Option;

import java.time.LocalDate;

public interface Pesel {
    Option<LocalDate> getBirthDate();
    ValidPesel.Gender getGender();
    String getPesel();
}
