package pl.recordit.pesel;

import io.vavr.control.Validation;

public class Demo {
    public static void main(String[] args) {
        Validation<PeselError, ValidPesel> opesel = ValidPesel.of("67100808131").toValidation();
    }

}
