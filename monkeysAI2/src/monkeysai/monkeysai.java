package monkeysai;

import agents.Agent;
import agents.Monkey;
import agents.Predator;
import java.io.IOException;

/*
 * @author Natan Macedo <natantur7@gmail.com>
 * @author Brian Azevedo <>
 * @author Caroline Barroso
 */

import agents.*;
import java.util.Arrays;

import java.util.Random;


public class monkeysai
{

        
     public static void main(String[] args) throws IOException, CloneNotSupportedException {
        // TODO code application logic here
        
        Florest t=new Florest();
        
       //  System.out.println(t.begin(30000));
         boolean teste=false;
         while(!teste){
             teste=t.begin(30000);
             System.out.println(teste);
         }
        
        // System.out.println( );
        
         //System.out.println((random.nextDouble())*90/100);
       
    }
}