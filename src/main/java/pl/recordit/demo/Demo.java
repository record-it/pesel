package pl.recordit.demo;

import io.vavr.collection.List;
import pl.recordit.pesel.InvalidPesel;
import pl.recordit.pesel.Pesel;
import pl.recordit.pesel.PeselError;


public class Demo {
    public static void main(String[] args) {
        List<Pesel> pesels = List.of(
                Pesel.ofBoth("67100808130"),
                Pesel.ofBoth("67100808131"),
                Pesel.ofBoth(null),
                Pesel.ofBoth("AAAAAAAAAAA")
        );
        pesels.toStream()
                .flatMap(Pesel::mapToInvalidPesel)
                .forEach(p -> System.out.println(p.getPesel().getOrElse("") + " " + p.getError().getErrorMessage()));
    }

}
