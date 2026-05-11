package client.usercommands;
import client.ClientSession;
import client.builders.UserBuild;
import client.cli.Console;
import client.repository.UserRegistry;
import common.network.Result;
import common.network.User;

public class LoggingUserCommand implements CommandUser {

    private final UserBuild userBuild;
    private final UserRegistry userRegistry;
    private final Console console;
    private final ClientSession clientSession;

    public LoggingUserCommand(UserBuild userBuild,  UserRegistry userRegistry, Console console, ClientSession clientSession){
        this.userBuild = userBuild;
        this.userRegistry = userRegistry;
        this.console = console;
        this.clientSession = clientSession;
    }


    @Override
    public int execute(){
        String nameUser = userBuild.loggingName();
        String password = userBuild.loggingPassword();
        var user = new User(nameUser, password);

        Result<Boolean> result = userRegistry.logging(user);
        if(!result.isSuccess()){
            console.println(result.getErrorMessage());
            return 1;
        }
        clientSession.setUser(user);
        return 0;
    }
}
