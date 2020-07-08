package pl.recordit.demo;

import io.vavr.collection.List;
import io.vavr.control.Option;
import pl.recordit.pesel.Pesel;

public class Demo {
    public static void main(String[] args) {
        String result = Pesel.of("44444444444")
                .map(Pesel::getBirthDate)
                .fold(error -> "Can't extract birth date, pesel is invalid due the error: " + error.getErrorMessage(), date -> date.get().toString());
        System.out.println(result);
        List<Pesel> pesels = List.of(
                Pesel.ofBoth("67100808130"),
                Pesel.ofBoth("67100808131"),
                Pesel.ofBoth(null),
                Pesel.ofBoth("AAAAAAAAAAA")
        );
        pesels.toStream()
                .flatMap(Pesel::toInvalidPesel)
                .forEach(p -> System.out.println(p.getPesel().getOrElse("") + " " + p.getError()));

        Pesel pesel = Pesel.ofNoError("44051401457");
        if (pesel.isInvalid()){
            System.out.println("Pesel is invalid!");
            System.out.println("birthdate " + pesel.getBirthDate().toJavaOptional());
            System.out.println("pesel " + pesel.getPesel().toJavaOptional());
            System.out.println("gender " + pesel.getGender());
        }

        pesel = Pesel.ofBoth("44051401458");
        if (pesel.isInvalid()){
            Pesel.toInvalidPesel(pesel).flatMap(p -> Option.of(p.getError().getErrorMessage())).toJavaOptional().ifPresent(System.out::println);
        }
        if (pesel.isValid()){
            System.out.println(pesel.getGender());
            System.out.println(pesel.getPesel().get());
            System.out.println(pesel.getBirthDate().get());
        }
    }

}
