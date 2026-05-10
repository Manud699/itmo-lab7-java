package common.network;

import java.io.Serial;
import java.io.Serializable;



public class Result<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final T valor;
    private final String errorMessage;
    private final boolean isSuccess;


    private Result(T valor, String errorMessage, boolean isSuccess){
        this.valor = valor;
        this.errorMessage = errorMessage;
        this.isSuccess = isSuccess;
    }



    public static <T> Result<T> success(T object){
        return new Result<>(object,null,true);
    }



    public static <T> Result<T> success(){
        return new Result<>(null,null,true);
    }



    public static <T> Result<T> failure(String errorMessage){
        return new Result<>(null, errorMessage,false);
    }



    public static <T> Result<T> failure(String errorMessage, T valor){
        return new Result<>(valor, errorMessage,false);
    }



    public T getValue(){
        return valor;
    }



    public boolean isSuccess(){
        return isSuccess;
    }



    public  String getErrorMessage(){
        return  errorMessage;
    }
}
