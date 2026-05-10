package common.model;

import java.io.Serializable;

/**
 * class Coordinates 
 * @author manu_d699
 */
public class Coordinates implements Serializable {

    private float x;
    private Double y;

    
    /**
     * Constructor for the Coordinates class.
     * @param x 
     * @param y
     */
    public Coordinates(float x, Double y) {
        this.x = x; 
        this.y = y;
    } 

    public float getX() {
        return x; 
    } 
    

    public Double getY() {
        return y; 
    } 


}
