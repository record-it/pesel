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

import io.vavr.control.Option;
import io.vavr.control.Validation;
import java.time.LocalDate;
import java.util.Objects;


final public class InvalidPesel implements Pesel{

    protected static final Pesel REF_TO_INVALID = new InvalidPesel("", PeselError.INVALID_FORMED);

    private final PeselError error;

    private final String pesel;

    protected static Pesel ofString(String pesel){
        Validation<PeselError, Pesel> opesel = ValidPesel.ofString(pesel).toValidation();
        return opesel.isValid() ? opesel.get() : new InvalidPesel(pesel, opesel.getError());
    }

    private InvalidPesel(String peselStr, PeselError error) {
        this.pesel = peselStr;
        this.error = error;
    }

    @Override
    public Option<LocalDate> getBirthDate() {
        return Option.none();
    }

    @Override
    public Gender getGender() {
        return Gender.UNKNOWN;
    }

    @Override
    public Option<String> getPesel() {
        if (this == REF_TO_INVALID){
            return Option.none();
        }
        return Option.some(pesel);
    }

    public PeselError getError() {
        return error;
    }

    @Override
    public String toString() {
        return "InvalidPesel{" +
                "error=" + error +
                ", pesel='" + pesel + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvalidPesel that = (InvalidPesel) o;
        return error == that.error &&
                Objects.equals(pesel, that.pesel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(error, pesel);
    }
}
