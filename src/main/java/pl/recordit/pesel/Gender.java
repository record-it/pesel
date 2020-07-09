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

import java.util.Arrays;
public enum Gender {
    MALE("13579"),
    FEMALE("02468"),
    UNKNOWN("");
    private final String codes;

    Gender(String codes) {
        this.codes = codes;
    }

    protected static Gender of(final char code) {
        return Arrays.stream(Gender.values())
                .filter(g -> g.codes.contains(String.valueOf(code)))
                .findAny()
                .orElse(UNKNOWN);
    }
}