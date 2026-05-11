package client.builders;

import client.cli.InputProvider;
import client.cli.Console;
import common.network.User;
import common.security.HashSecurity;

public class UserBuild extends AbstractConsoleBuilder<User> {

    public UserBuild(InputProvider inputProvider, Console console){
        super(inputProvider, console);
    }

    @Override
    public User build(){
        String name = nameUser();
        String hashPassword = HashSecurity.getHash(password());
        return new User(name, hashPassword);
    }


    public String nameUser(){
        return askString("new username", "[username cannot be empty]", name -> name != null && !name.isEmpty());
    }

    public String password(){
        return askString("new password", "[password must be at least 8 characters]", password -> password.length() >= 8);
    }


    public String loggingName(){
        return askString("login: ", name -> name != null && !name.isEmpty());
    }


    public String loggingPassword(){
        String prePassword =  askString("Password: ", password -> password != null && !password.isEmpty());
        return HashSecurity.getHash(prePassword);
    }
}