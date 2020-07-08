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