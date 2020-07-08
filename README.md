# Library pesel
### version 0.9.1

## Info

Library pesel provides interface `Pesel` with static methods:
 * static method `of` for creating valid object pesel or error,
 * static method `ofNoError` returning valid object of `ValidPesel` class or special instance INVALID in case of non valid string,
 * static method `both` returning object of one from two class implementing interface `Pesel` - `ValidPesel` or `InvalidPesel` with error.
 
 Method `of` takes input string with PESEL and returns `Either` object. If the string contains valid PESEL created object is on the right. In case of invalid string, on the left is error as one of `PeselError` enum value.
 
 Instance methods of Pesel:
 * `getBirthDate` returns Option with LocalDate,
 * `getPesel` returns Optiona with Pesel as string,
 * `getGender` returns one of constant from Gender enum.
 
 Class `InvalidPesel`  contains additional method `getError` returning PeselError
 
 ## Usage
If you familiar with **Vavr** library use method `of`: 
To create valid object containing PESEL use `of` method, which return `Either`:
 ```java
Either<PeselEroor, Pesel> epesel = Pesel.of("44051401458");
```
 
 Process using `Either` or get object of class `Pesel` if is present on right side:
 
```java
String result = Pesel.of("44444444444")
                 .map(Pesel::getBirthDate)
                 .fold(error -> "Can't extract birth date, pesel is invalid due the error: " + error.getErrorMessage(), date -> date.get().toString());
         System.out.println(result);
```
All object with in     
       
         
If you don't like **Vavr** use `ofNoError` method:
```java
Pesel pesel = Pesel.ofNoError("44051401457");
        if (pesel.isInvalid()){
            System.out.println("Pesel is invalid!");
            System.out.println("birthdate " + pesel.getBirthDate().toJavaOptional());
            System.out.println("pesel " + pesel.getPesel().toJavaOptional());
            System.out.println("gender " + pesel.getGender());
        }
```

Use `ofBoth` method if you want to get proper object (class `ValidPasel`) or inproper `Pesel` object (class `InvalidPesel`) with string and error message.
```java
pesel = Pesel.ofBoth("44051401458");
        if (pesel.isInvalid()){
            Pesel.toInvalidPesel(pesel).flatMap(p -> Option.of(p.getError().getErrorMessage())).toJavaOptional().ifPresent(error -> System.out.println(error));
        }
        if (pesel.isValid()){
            System.out.println(pesel.getGender());
            System.out.println(pesel.getPesel().get());
            System.out.println(pesel.getBirthDate().get());
        }
```

   
 
