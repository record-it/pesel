/*
 * Copyright (c) 2020. record-it
 * Cezary Siwoń
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package pl.recordit.pesel.control;

import io.vavr.control.Either;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Validator<T, E> {

    static private class Phase<T, E>{
        private final Predicate<T> predicate;
        private final E error;

        public Phase(Predicate<T> predicate, E error) {
            this.predicate = predicate;
            this.error = error;
        }
    }

    private List<Phase<T, E>> predicates = new LinkedList<>();

    public static <T, E> Validator<T, E> builder(){
        return new Validator<>();
    }

    public Validator<T, E> condition(Predicate<T> predicate, E error){
        predicates.add(new Phase<>(predicate, error));
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
