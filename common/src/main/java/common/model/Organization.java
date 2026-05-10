package common.model;


import java.io.Serializable;

/**
 * class Organization 
 * @author manu_d699
 */
public class Organization implements Serializable {

    private final String fullName; //Длина строки не должна быть больше 694, Поле не может быть null
    private final float annualTurnover; //Значение поля должно быть больше 0
    private final int employeesCount; //Значение поля должно быть больше 0


    public Organization(String fullName, float anuualTurnover, int employeesCount) {
        this.fullName = fullName; 
        this.annualTurnover = anuualTurnover; 
        this.employeesCount = employeesCount; 
    } 


    public String getFullName() {
        return fullName; 
    } 


    public float getAnnualTurnover() {
        return annualTurnover; 
    } 


    public int getEmployeesCount() {
        return employeesCount;
    }


    @Override
    public String toString() {
        return "Organization{" +
                "fullName='" + fullName + '\'' +
                ", annualTurnover=" + annualTurnover +
                ", employeesCount=" + employeesCount +
                '}';
    }

}
