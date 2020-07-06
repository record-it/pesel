# Library pesel

## Info

Library pesel provides class Pesel with methods:
 * static method `of` for creating object with valid pesel,
 * instance method `getBirthDate` returning extracted birth date,
 * instance method `getGender` returning extracted gender.
 
 Method `of` takes input string with PESEL and returns `Either` object. If the string contains valid PESEL created object is on the right. In case of invalid string on the left is error as one of `PeselError` enum value. 
 
 ## Usage
 
To create valid object containing PESEL use `of` method, which return `Either`:
 
 `Either<PeselEroor, Pesel> epesel = Pesel.of("44051401458");`
 
 Process using `Either` or get object of class `Pesel` if is present on right side:
 
`String result = Pesel.of("44444444444")
                 .map(Pesel::getBirthDate)
                 .fold(error -> "Can't extract birth date, pesel is invalid due the error: " + error.getErrorMessage(), date -> date.get().toString());
         System.out.println(result);`
   
 
