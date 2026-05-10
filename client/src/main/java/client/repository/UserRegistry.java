package client.repository;

import client.network.NetworkClient;
import common.network.Request;
import common.network.Response;
import common.network.Result;
import common.network.User;

public class UserRegistry {

    private final NetworkClient networkClient;

    public UserRegistry(NetworkClient networkClient){
        this.networkClient = networkClient;
    }



    @SuppressWarnings("unchecked")
    public Result<Boolean> logging(User user){
        Request request = new Request(user, "logging");
        Response response =  networkClient.execute(request);
        return (Result<Boolean>)response.result();
    }



    @SuppressWarnings("unchecked")
    public Result<Void> registrate(User user){
        Request request = new Request(user, "registrate");
        Response response = networkClient.execute(request);
        return (Result<Void>) response.result();
    }
}
