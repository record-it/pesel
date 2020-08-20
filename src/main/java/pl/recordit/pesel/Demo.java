package pl.recordit.pesel;

import io.vavr.control.Option;

public class Demo {
    public static void main(String[] args) {
        Pesel pesel = Pesel.ofBoth("44051401458");
        if (pesel.isInvalid()){
            pesel.toInvalidPesel().flatMap(p -> Option.of(p.getError().getErrorMessage())).toJavaOptional().ifPresent(System.out::println);
        }
        if (pesel.isValid()){
            System.out.println(pesel.getGender());
            System.out.println(pesel.getPesel().get());
            System.out.println(pesel.getBirthDate().get());
        }
        pesel = Pesel.ofNoError("44051401457");
        if (pesel.isInvalid()){
            System.out.println("Pesel is invalid!");
            System.out.println("birthdate " + pesel.getBirthDate().toJavaOptional());
            System.out.println("pesel " + pesel.getPesel().toJavaOptional());
            System.out.println("gender " + pesel.getGender());
        }
    }
}
