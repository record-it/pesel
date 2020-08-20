# Library pesel

### version 0.9.2

## Depencies
* VAVR
## Description

Library pesel provides interface `Pesel` with static methods:
 * static method `of` for creating valid object pesel or error,
 * static method `ofNoError` returning valid object of `ValidPesel` class or special instance INVALID in case of non valid string,
 * static method `both` returning object one of two class implementing interface `Pesel` - `ValidPesel` or `InvalidPesel` with error.
 
 Method `of` takes input string with PESEL and returns `Either` object. If the string contains valid PESEL, the created object is on the right. In case of invalid string, on the left is error as one of `PeselError` enum value.
 
 Instance methods of Pesel:
 * `getBirthDate` returns Option with LocalDate,
 * `getPesel` returns Option with Pesel as string,
 * `getGender` returns one of constants from Gender enum.
 
 Class `InvalidPesel`  contains additional method `getError` returning PeselError.
 
 ## Usage
If you familiar with **Vavr** library use method `of` to create valid object containing PESEL, which return `Either`:
 ```java
Either<PeselEroor, Pesel> epesel = Pesel.of("44051401458");
```
 
Process using `Either` or get object of class `ValidPesel` if is present on right side:
 
```java
String result = Pesel.of("44444444444")
                 .map(Pesel::getBirthDate)
                 .fold(error -> "Can't extract birth date, pesel is invalid due the error: " + error.getErrorMessage(), date -> date.get().toString());
         System.out.println(result);
```   
         
If you don't like **Vavr** use `ofNoError` method, which returns a special `INVALID` instance for all non valid PESEL's. In case of `INVALID` instance you can safely call all interface methods, which returns empty `Option` and `UNKNOW` constant for gender:
```java
Pesel pesel = Pesel.ofNoError("44051401457");
        if (pesel.isInvalid()){
            System.out.println("Pesel is invalid!");
            System.out.println("birthdate " + pesel.getBirthDate().toJavaOptional());
            System.out.println("pesel " + pesel.getPesel().toJavaOptional());
            System.out.println("gender " + pesel.getGender());
        }
```
Use `ofBoth` method if you want to get the proper object (class `ValidPesel`), or the improper object (class `InvalidPesel`). An `InvalidPesel` object has additional method returning error message.
```java
Pesel pesel = Pesel.ofBoth("44051401458");
        if (pesel.isInvalid()){
            pesel.toInvalidPesel().flatMap(p -> Option.of(p.getError().getErrorMessage())).toJavaOptional().ifPresent(System.out::println);
        }
        if (pesel.isValid()){
            System.out.println(pesel.getGender());
            System.out.println(pesel.getPesel().get());
            System.out.println(pesel.getBirthDate().get());
        }
```
Choose wisely and have fun.

## License
[Apache License](http://www.apache.org/licenses/LICENSE-2.0.txt)