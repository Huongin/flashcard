package util;

import constant.DateTimeConstant;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Year;
import java.util.Scanner;

public class BirthDateUtil {

    public static int calculateAge(LocalDate birthDate){
        int currentYear = Year.now().getValue();
        int birthdayYear = birthDate.getYear();
        int age = currentYear - birthdayYear;

        LocalDate today = LocalDate.now();
        if (birthDate.plusYears(age).isAfter(today)){
            age--;
        }
        return age;
    }

}
