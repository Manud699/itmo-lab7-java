package common.model;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * class Worker 
 * @author manu_d699
 */
public class Worker implements Comparable<Worker>, Serializable {


    private long id;
    private String name; // Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; // Поле не может быть null
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long salary; //Значение поля должно быть больше 0
    private Position position; //Поле не может быть null
    private Status status; //Поле не может быть null
    private Organization organization; //Поле не может быть nul
    private String creatorName;


    public Worker(){}
    

    public Worker(long id, String name, Coordinates coordinates, ZonedDateTime creationDate, long salary, Position position, Status status, Organization organization) {
        this.id = id; 
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;  
        this.salary = salary; 
        this.position = position;
        this.status = status; 
        this.organization = organization;
    } 



    public long getId() {
        return id; 
    } 



    public String getName() {
        return name; 
    } 



    public Coordinates getCoordinates(){
        return coordinates;
    } 



    public ZonedDateTime getCreationDate() {
        return creationDate; 
    }



    public long getSalary() {
        return salary; 
    }



    public Position getPosition() {
        return position; 
    }



    public Status getStatus() {
        return status; 
    }



    public Organization getOrganization() {
        return organization; 
    } 



    public void setId(long id) {
        this.id = id; 
    } 



    public void setName(String name){
        this.name = name;
    }



    public void setCoordinates(Coordinates  coordinates){
        this.coordinates = coordinates;
    }


    public void setCreationDate(ZonedDateTime zonedDateTime){
        this.creationDate = zonedDateTime;
    }


    public void setSalary(long salary){
        this.salary = salary;
    }



    public void setPosition(Position position){
        this.position =position;
    }



    public void setStatus(Status status){
        this.status = status;
    }



    public void setOrganization(Organization organization){
        this.organization = organization;
    }


    public void setCreatorName(String creatorName){
        this.creatorName = creatorName;
    }


    public String getCreatorName(){
        return creatorName;
    }

    @Override
    public int compareTo(Worker otherWorker){
        return (int) (this.id - otherWorker.id);
    }  



    @Override
    public boolean equals(Object object) {
        if(object instanceof Worker otherWorker) {
            return this.id == otherWorker.id; 
        } 
        return false; 
    }



    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(id);
    } 
}
