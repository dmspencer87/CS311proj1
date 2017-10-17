import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
/************************************************************************************
 *	file: fsa.java
 *	author: Daniel Spencer
 *	class: CS 311
 *
 *	assignment: Project 1
 *	date last modified: 10/17/2017
 *
 *	purpose: To develop a universal finite state automaton
 *
 *
 ************************************************************************************/
public class fsa{
    private static int totStates;
    private static int[][] transitions;
    private static boolean[] fStates;
    private static char[] alphebet;
    private static String finalStates;
    public static ArrayList<String> trans;

    public static void main(String[] args)throws IOException{
        //file input
        Scanner inFile = new Scanner(new File("input.txt"));
        //loop to next fsa
        while(inFile.hasNext()){
            totStates = Integer.parseInt(inFile.nextLine());
            System.out.println("Finite State Automaton.\n(1)number of states: " + totStates);
            finalStates = inFile.nextLine();
            System.out.println("(2)final states: " + finalStates);
            //final states and trap
            fStates = new boolean[totStates + 1];
            Arrays.fill(fStates, Boolean.FALSE);
            String[] input = finalStates.split("\\s+");
            //
            for(int i = 0; i < input.length;++i){
                fStates[Integer.parseInt(input[i])] = true;
            }
            String in = inFile.nextLine();
            System.out.println("(3)alphabet: " + in);
            setAlphebet(in);
            System.out.println("(4)transitions: ");
            trans =new ArrayList<>();
            in = inFile.nextLine();
            //loop to catch transitions and prints
            while(in.startsWith("(")){
                System.out.println("\t" + in);
                trans.add(in.substring(1 , in.length()-1));
                in = inFile.nextLine();
            }
            //Creates Transition table index using 2d array
            createTable(trans);
            trans.clear();
            System.out.println("(5)strings: ");
            //algorithm given with project
            while(!(in.equals("------"))){  //sperate fsa by  -^6
                //reset State for new String
                int firstState = 0;
                int cState = firstState;
                for(int i = 0; i < in.length(); ++i) {
                    if(!checkAlphabet(in.charAt(i))) {
                        if(in.charAt(i) == ' ') {
                            System.out.println(in + "\t\taccept");
                        }
                        else {
                            System.out.println(in + "\t\treject");
                            break;
                        }
                    }
                    else if(i == in.length()-1) {
                        cState = transitions[cState][get(in.charAt(i))];
                        if(fStates[cState] == true) {
                            System.out.println(in + "\t\taccept");
                        }
                        else {
                            System.out.println(in + "\t\treject");
                        }
                    }
                    else {
                        cState = transitions[cState][get(in.charAt(i))];
                    }

                }
                if(inFile.hasNext()) {
                    in = inFile.nextLine();
                    //System.out.println();
                }
                else{
                    break;
                }

            }

        }
    }
    public static void setAlphebet(String s){
        String[] input  = s.split("\\s+");
        alphebet = new char[input.length];
        for(int i = 0; i < alphebet.length;++i){
            alphebet[i] = input[i].charAt(0);
        }

    }
    public static boolean checkAlphabet(char c) {
        for(int i = 0; i < alphebet.length; i++) {
            if(c == alphebet[i]) {
                return true;
            }
        }
        return false;
    }
    public static void createTable(ArrayList<String> array){
        //Trap state
        transitions = new int[totStates+1][alphebet.length];
        //creates 2d array table
        for(int i = 0; i < transitions.length; ++i) {
            for(int j = 0; j < transitions[0].length; ++j) {
                transitions[i][j] = transitions.length-1;
            }
        }
        String[] in;
        for(int i = 0; i < array.size(); ++i) {
            in = array.get(i).split("\\s+");
            int p = Integer.parseInt(in[0]);
            int a = get(in[1].charAt(0));
            int q = Integer.parseInt(in[2]);
            transitions[p][a] = q;
        }
    }
    public static int get(char c) {
        for(int i = 0; i < alphebet.length; ++i) {
            if(alphebet[i] == c){
                return i;
            }
        }
        return totStates + 1;
    }


}
