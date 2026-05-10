package server.commands;

import common.network.Request;
import common.network.Response;
import server.repository.UserRepository;

public class RegistrateUserCommand extends AbstractCommand {

    private final UserRepository userRepository;

    public RegistrateUserCommand(UserRepository userRepository){
        super("registrate","");
        this.userRepository = userRepository;
    }



    @Override
    public Response execute(Request request){
        return  userRepository.registrate(request.getUser());
    }
}
