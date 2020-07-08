package pl.recordit.pesel;

public enum PeselError {
    NULL("Pesel can't be null!"),
    INVALID_LENGTH("Pesel length is invalid!"),
    NON_DIGIT_CHAR("Non digit sign in pesel!"),
    INVALID_CONTROL_DIGIT("Invalid control digit!"),
    INVALID_BIRTH_DATE("Invalid birth date!"),
    INVALID_FORMED("Invalid formed pesel!");

    private String errorMessage;

    PeselError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
