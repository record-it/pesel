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
        return ValidPesel.ofString(pesel).toValidation().getOrElse(INVALID);
    }

    default Option<InvalidPesel> toInvalidPesel(){
        return Option.of(this)
                .filter(p -> p instanceof InvalidPesel)
                .map(p -> (InvalidPesel) p);
    }

    default Option<ValidPesel> toValidPesel(){
        return Option.some(this)
                .filter(p -> p instanceof ValidPesel)
                .map(p -> (ValidPesel) p);
    }

    default boolean isInvalid(){
        return this instanceof InvalidPesel;
    }

    default boolean isValid(){
        return this instanceof ValidPesel;
    }
}
