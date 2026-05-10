package client.usercommands;

import client.ClientSession;
import client.builders.UserBuild;
import client.cli.Console;
import client.repository.UserRegistry;
import common.network.Result;

public class RegistrateUserCommand implements CommandUser {

    private final UserBuild userBuild;
    private final UserRegistry userRegistry;
    private final Console console;
    private final ClientSession clientSession;


    public RegistrateUserCommand(UserBuild userBuild, UserRegistry userRegistry, Console console, ClientSession clientSession){
        this.userBuild = userBuild;
        this.userRegistry = userRegistry;
        this.console = console;
        this.clientSession = clientSession;
    }



    @Override
    public int execute(){
        var user = userBuild.build();
        Result<Void> result = userRegistry.registrate(user);
        if(!result.isSuccess()) {
            console.printError(result.getErrorMessage());
            return 1;
        }
        clientSession.setUser(user);
        return 0;
    }
}
