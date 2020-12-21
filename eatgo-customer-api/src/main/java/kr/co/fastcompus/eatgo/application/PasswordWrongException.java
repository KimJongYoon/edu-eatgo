package kr.co.fastcompus.eatgo.application;

public class PasswordWrongException extends RuntimeException{

    public PasswordWrongException(){
        super("Password is wrong");
    };
}
