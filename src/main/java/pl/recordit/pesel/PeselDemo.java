package pl.recordit.pesel;

import io.vavr.control.Option;

import java.time.LocalDate;

public class PeselDemo {
    public static void main(String[] args) {
        String result = Pesel.of("44444444444")
                .map(Pesel::getBirthDate)
                .fold(error -> "Can't extract birth date, pesel is invalid due the error: " + error.getErrorMessage(), date -> date.get().toString());
        System.out.println(result);
    }
}
