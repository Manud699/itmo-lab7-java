package common.network;


import common.model.Worker;

import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;

public class Request implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final User user;
    private final String  commandName;
    private final String commandArgument;
    private final Worker worker;
    


    public Request(User user,String commandName) {
        this(user,commandName, "", null);
    }

    public Request(Worker worker){
        this(null,"", "", worker);
    }

    public Request(User user, String commandName, String argument) {
        this(user, commandName, argument, null );
    }

    public Request(User user, String commandName, Worker worker){
        this(user,commandName, "", worker);
    }

    public Request(User user, String commandName, String commandArgument, Worker worker){
        this.user = user;
        this.commandName = commandName;
        this.commandArgument = commandArgument;
        this.worker = worker;
    }


    public User getUser(){
        return user;
    }


    public String getCommandName(){
        return commandName;
    }

    public String getCommandArgument(){
        return commandArgument;
    }

    public Optional<Worker> getWorker(){
        return Optional.ofNullable(worker);
    }

}
