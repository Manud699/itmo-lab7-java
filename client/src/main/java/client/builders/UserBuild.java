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
       return askString("nombre de usuario ","[no debe ser null/vacio]", name -> name != null && !name.isEmpty());
    }



    public String password(){
        return askString("password","[minimo 8 caracteres]", password -> password.length() >= 8);
    }



    public String loggingName(){
        return askString("name:", name -> name != null && !name.isEmpty());
    }



    public String loggingPassword(){
        String prePassword =  askString("password:", password -> password != null && !password.isEmpty());
        return  HashSecurity.getHash(prePassword);
    }
}
