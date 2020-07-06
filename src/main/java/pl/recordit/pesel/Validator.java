package pl.recordit.pesel;

import io.vavr.control.Either;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

class Validator<T, E> {

    static private class Phase<T, E>{
        private final Predicate<T> predicate;
        private final E error;

        public Phase(Predicate<T> predicate, E error) {
            this.predicate = predicate;
            this.error = error;
        }
    }

    private List<Phase<T, E>> predicates = new ArrayList<>();

    public static <T, E> Validator<T, E> builder(){
        return new Validator<>();
    }

    public Validator<T, E> condition(Predicate<T> predicate, E failureObject){
        predicates.add(new Phase<>(predicate, failureObject));
        return this;
    }

    public <V> Either<E, V> validate(T input, Supplier<V> objectSupplier){
        return predicates.stream()
                .filter(v -> !v.predicate.test(input))
                .findFirst()
                .<Either<E, V>> map(v -> Either.left(v.error))
                .orElseGet(()->Either.right(objectSupplier.get()));
    }
}
