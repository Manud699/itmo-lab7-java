package server.commands;

import common.network.Request;
import common.network.Response;
import server.repository.UserRepository;

public class LoggingUserCommand extends AbstractCommand {

    private final UserRepository userRepository;


    public LoggingUserCommand(UserRepository userRepository){
        super("login", "");
        this.userRepository = userRepository;
    }




    @Override
    public Response execute(Request request){
        return  userRepository.login(request.getUser());
    }
}
