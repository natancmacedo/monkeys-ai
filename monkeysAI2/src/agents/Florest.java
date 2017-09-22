/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Natan
 */
public class Florest {

    private static int symbols;
    private static int signalRadius;
    private static int visionRadius;
    private static int mapSizeX;
    private static int mapSizeY;
    private static int monkeysAmount;
    private static int predatorsAmount;

    private static Agent[][] map;
    private static Monkey[] monkeys;
    private static Predator[] predators;
    public static tableMonkey matrixLog[][];

    public Florest() { //iniciar configorações default
        Florest.symbols = 10;
        Florest.signalRadius = 6;
        Florest.visionRadius = 12;
        Florest.mapSizeX = 50;
        Florest.mapSizeY = 50;
        Florest.monkeysAmount = 3;
        Florest.predatorsAmount = 3;
        Florest.map = new Agent[mapSizeX][mapSizeY];
        Florest.monkeys = new Monkey[monkeysAmount];
        Florest.predators = new Predator[predatorsAmount];

    }

    public Florest(int symbols, int signalRadius, int visionRadius, int mapSizeX, int mapSizeY, int monkeysAmount, int predatorsAmount) {
        Florest.symbols = symbols;
        Florest.signalRadius = signalRadius;
        Florest.visionRadius = visionRadius;
        Florest.mapSizeX = mapSizeX;
        Florest.mapSizeY = mapSizeY;
        Florest.monkeysAmount = monkeysAmount;
        Florest.predatorsAmount = predatorsAmount;
        Florest.map = new Agent[mapSizeX][mapSizeY];
        Florest.monkeys = new Monkey[monkeysAmount];
        Florest.predators = new Predator[predatorsAmount];
    }

    public static int getSymbols() {
        return symbols;
    }

    public static int getSignalRadius() {
        return signalRadius;
    }

    public static int getVisionRadius() {
        return visionRadius;
    }

    public static int getMapSizeX() {
        return mapSizeX;
    }

    public static int getMapSizeY() {
        return mapSizeY;
    }

    public static int getMonkeysAmount() {
        return monkeysAmount;
    }

    public static int getPredatorsAmount() {
        return predatorsAmount;
    }

    public static Agent[][] getMap() {
        return map;
    }

    public static Monkey[] getMonkeys() {
        return monkeys;
    }

    public static void setMonkeys(Monkey[] aMonkeys) {
        monkeys = aMonkeys;
    }

    public static Predator[] getPredators() {
        return predators;
    }

    public static void setPredators(Predator[] aPredators) {
        predators = aPredators;
    }

    public int[] symbolsConverged() {

        int convergency[] = new int[Florest.predatorsAmount];
        for (int i = 0; i < convergency.length; i++) {
            convergency[i] = -1;
        }

        int monkey = 0;
        for (int predator = 0; predator < Florest.predatorsAmount; predator++) {
            for (int symbol = 0; symbol < Florest.symbols; symbol++) {
                if (Florest.monkeys[monkey].getTable().table[symbol][predator] == 1.0) {
                    convergency[predator] = symbol;

                }
            }
        }

        return convergency;
    }

    public double[] logSymbol(int monkey, int symbol, int predator) {
        double log[] = new double[Florest.matrixLog[0].length];
        for (int iteration = 0; iteration < Florest.matrixLog[0].length; iteration++) {
            log[iteration] = Florest.matrixLog[monkey][iteration].table[symbol][predator];
        }

        return log;
    }

    public void logFile() throws IOException {
        int symbolsLog[] = this.symbolsConverged();

        String nameFile;
        double logSymbol[] = new double[Florest.matrixLog[0].length];
        for (int monkey = 0; monkey < monkeysAmount; monkey++) {
            for (int symbol = 0; symbol < symbolsLog.length; symbol++) {

                try {
                    
                    nameFile = Florest.monkeys[monkey].getName() + "S" + symbolsLog[symbol];
                    nameFile = nameFile + ".txt";
                    FileWriter file = new FileWriter(nameFile);
                    BufferedWriter writeFile = new BufferedWriter(file);

                    logSymbol = logSymbol(monkey, symbolsLog[symbol], symbol);
                    for (double i : logSymbol) {
                        writeFile.append(Double.toString(i));
                        writeFile.newLine();

                    }

                    writeFile.close();
                } catch (FileNotFoundException e) {
                    System.out.println("Arquivo não pode ser criado");
                } catch (IOException e) {
                    System.out.println("Erro em entrada e saída");
                }

            }
        }

    }

    public boolean begin(int iterations) throws IOException, CloneNotSupportedException {
        Florest.matrixLog = new tableMonkey[monkeysAmount][iterations];
        for (int i = 0; i < monkeysAmount; i++) {
            for (int j = 0; j < iterations; j++) {
                matrixLog[i][j] = new tableMonkey();
            }
        }
        Initialize();
        Execute(iterations);
        ShowMonkeys();
        boolean test = hasConverged();
        if (test) {
            logFile();
        }
        return test;

    }

    public void tests(int iterations, int timesTests) throws IOException, CloneNotSupportedException {
        int FALSE = 0;
        int TRUE = 0;
        double dfalse;
        double dtrue;
        boolean result;
        for (int i = 0; i < timesTests; i++) {
            Florest teste = new Florest();
            result = teste.begin(iterations);
            if (result) {
                TRUE++;
            } else {
                FALSE++;
            }
            // System.out.println(i+1+" resultado: "+result);

            dfalse = (double) FALSE / (FALSE + TRUE) * 100;
            dtrue = (double) TRUE / (FALSE + TRUE) * 100;
            //System.out.println(i+1+" "+"False: "+FALSE+"("+dfalse+"%)"+"+ True: "+TRUE+"("+dtrue+"%)");
            System.out.printf("%d False: %d (%.2f) True: %d (%.2f)\n", i + 1, FALSE, dfalse, TRUE, dtrue);
            //System.out.println("False: "+FALSE+" \nTrue: "+TRUE);
            Florest.setMonkeys(null);
            Florest.setPredators(null);
            Monkey.setTotalOfInstances(0);
            Predator.predatorCounter = 0;

        }
    }

    public static void Initialize() {

        for (int i = 0; i < monkeys.length; i++) {
            monkeys[i] = new Monkey();
        }

        for (int i = 0; i < predators.length; i++) {
            predators[i] = new Predator();
        }
    }

    public static void copyTable(double a[][], double b[][]) {
        for (int i = 0; i < Florest.predatorsAmount; i++) {

            for (int j = 0; j < symbols; j++) {
                a[j][i] = b[j][i];
            }
        }

    }

    public static void Execute(int times) throws CloneNotSupportedException {

        for (int i = 0; i < times; i++) {
            NextTime();
            //ShowSteps(i);
            for (int j = 0; j < monkeysAmount; j++) {

                copyTable(matrixLog[j][i].table, monkeys[j].getTable().table);

            }
        }

    }

    public static void NextTime() {
        for (Monkey monkey : monkeys) {
            monkey.Move();
        }

        for (Predator predator : predators) {
            predator.Move();
        }
    }

    public static void ShowMap() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == null) {
                    System.out.print(".. ");
                } else if (map[i][j].getClass() == Monkey.class) {
                    //Console.BackgroundColor = ConsoleColor.White;
                    //Console.ForegroundColor = ConsoleColor.Black;
                    System.out.print(map[i][j].getName() + " ");
                    //Console.ResetColor();
                } else if (map[i][j].getClass() == Predator.class) {
                    //Console.BackgroundColor = ConsoleColor.Yellow;
                    //Console.ForegroundColor = ConsoleColor.Black;
                    System.out.print(map[i][j].getName() + " ");
                    //Console.ResetColor();
                }

            }
            //Console.ResetColor();
            System.out.println();
        }
    }

    public static void ShowMonkeys() {
        for (Monkey monkey : monkeys) {
            monkey.ShowTable();
            System.out.println("------------------------------------");
        }
    }

    public static void ShowSteps(int i) {
        if (i % 500 == 0) {
            System.out.println("\n\tTIME " + ((i / 500) + 1));
            ShowMap();
            ShowMonkeys();
        }
    }

    private static boolean hasConverged() {

        int convergency[] = new int[Florest.predatorsAmount];
        for (int i = 0; i < convergency.length; i++) {
            convergency[i] = -1;
        }

        int monkey = 0;

        int counter = 0;
        for (int predator = 0; predator < Florest.predatorsAmount; predator++) {
            for (int symbol = 0; symbol < Florest.symbols; symbol++) {
                if (Florest.monkeys[monkey].getTable().table[symbol][predator] == 1.0) {
                    convergency[predator] = symbol;
                    counter++;
                }
            }
        }

        if (counter > Florest.predatorsAmount) {
            return false;
        }

        for (int i = 0; i < convergency.length; i++) {
            if (convergency[i] == -1) {
                return false;
            }
        }

        for (monkey = 1; monkey < Florest.monkeysAmount; monkey++) {
            for (int predator = 0; predator < convergency.length; predator++) {
                int test = convergency[predator];
                if (Florest.monkeys[monkey].getTable().table[test][predator] != 1.0) {
                    return false;
                }
            }
        }

        return true;
    }

}
