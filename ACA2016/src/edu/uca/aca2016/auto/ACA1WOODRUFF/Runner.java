/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uca.aca2016.auto.ACA1WOODRUFF;

import edu.uca.aca2016.basics.Person;

/**
 *
 * @author awood
 */
public class Runner {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Automobile corvette = new Automobile();
        // TODO code application logic here
        corvette.getNumber_of_wheels(4);
        corvette.getHeight(48);
        corvette.getWeight(2800f);

     System.out.println("The profile of 2002 Corvette Coup has" + getNumber_of_wheels(4);
     System.out.println("at a height of" + getHeight (48);
     System.out.println ("coming in at a weight of" + getWeight + "lbs.");
     
     
    public Automobile(int number_of_wheels, double height, double weight) {
        this.number_of_wheels = number_of_wheels;
        this.height = height;
        this.weight = weight;
    }    
        
    }
    
}
