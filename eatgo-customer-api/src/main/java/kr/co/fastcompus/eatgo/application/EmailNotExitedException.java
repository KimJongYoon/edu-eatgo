package kr.co.fastcompus.eatgo.application;

import java.util.function.Supplier;

public class EmailNotExitedException extends RuntimeException{

    EmailNotExitedException(String email){
        super("Email is not registred: " + email);
    }
}
