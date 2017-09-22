/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

/**
 *
 * @author Natan
 */
public class tableMonkey implements Cloneable{
     public double[][] table;
     
     public tableMonkey(){
         table=new double[Florest.getSymbols()][Florest.getPredators().length];
     }
     
    @Override
    public tableMonkey clone() throws CloneNotSupportedException{
        return (tableMonkey) super.clone();
    }
}