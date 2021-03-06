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

package pl.recordit.pesel;

import io.vavr.collection.Stream;
import io.vavr.control.Either;
import io.vavr.control.Option;
import pl.recordit.pesel.control.Validator;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;

final public class ValidPesel implements Pesel{
    private static final int[][] MONTH_CODES = {{1800, 80}, {1900, 0}, {2000, 20}, {2100, 40}, {2200, 60}};
    private static final int LENGTH = 11;

    private String pesel;

    private ValidPesel() {
    }

    private ValidPesel(String pesel) {
        this.pesel = pesel;
    }

    @Override
    public Option<String> getPesel() {
        return Option.of(pesel);
    }

    @Override
    public Option<LocalDate> getBirthDate() {
        return extractBirthDate(pesel);
    }

    protected static Either<PeselError, Pesel> ofString(String pesel) {
        final Validator<String, PeselError> validator = Validator.builder();
        return validator
                .condition(Objects::nonNull,
                        PeselError.NULL)
                .condition(str -> str.length() == LENGTH,
                        PeselError.INVALID_LENGTH)
                .condition(str -> str.chars().allMatch(Character::isDigit),
                        PeselError.NON_DIGIT_CHAR)
                .condition(str -> controlDigit(str) % 10 == Integer.parseInt(str.substring(10)),
                        PeselError.INVALID_CONTROL_DIGIT)
                .condition(str -> extractBirthDate(str).isDefined(),
                        PeselError.INVALID_BIRTH_DATE)
                .validate(pesel, () -> new ValidPesel(pesel));
    }

    @Override
    public Gender getGender() {
        return Gender.of(pesel.charAt(9));
    }

    private static Option<LocalDate> extractBirthDate(final String pesel) {
        final int year = Integer.parseInt(pesel.substring(0, 2));
        final int month = Integer.parseInt(pesel.substring(2, 4));
        final int day = Integer.parseInt(pesel.substring(4, 6));
        final int MONTH_INDEX = 1;
        final int YEAR_INDEX = 0;

        final Predicate<int[]> isValidMonth = arr -> (month - arr[MONTH_INDEX]) > 0 && (month - arr[MONTH_INDEX]) < 13;

        final Function<int[], Option<LocalDate>> mapToLocalDate = arr -> {
            try {
                return Option.of(LocalDate.of(year + arr[YEAR_INDEX], month - arr[MONTH_INDEX], day));
            } catch (DateTimeException e) {
                return Option.none();
            }
        };

        return Stream.of(MONTH_CODES)
                .filter(isValidMonth)
                .flatMap(mapToLocalDate)
                .lastOption();
    }

    private static int controlDigit(final String pesel) {
        final Iterator<Integer> weights = IntStream.of(9, 7, 3, 1, 9, 7, 3, 1, 9, 7).iterator();
        return pesel.substring(0, 10)
                .chars()
                .map(c -> Character.digit(c, 10))
                .reduce(0, (acu, item) -> acu + item * weights.next());
    }

    @Override
    public String toString() {
        return "Pesel{" +
                "pesel='" + pesel + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValidPesel)) return false;
        ValidPesel that = (ValidPesel) o;
        return Objects.equals(pesel, that.pesel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pesel);
    }
}

