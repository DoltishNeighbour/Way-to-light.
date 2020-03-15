package com.company;

import java.util.Scanner;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        char[] symbols = str.toCharArray();
        Stack<Character> Bracket = new Stack<>();
        char fromStack;
        boolean right=true;
        int n=0;
        for(char c : symbols) {
            n++;
            if ((c=='(')||(c=='{')||(c=='[')) Bracket.push(c);
            if ((c==')')||(c=='}')||(c==']')) {
                if(!Bracket.isEmpty()){
                    fromStack = Bracket.peek();
                    if(((c==')')&&(fromStack=='('))||((c=='}')&&(fromStack=='{'))||((c==']')&&(fromStack=='['))) {
                        Bracket.pop();
                    } else {
                        right=false;
                        break;
                    }
                } else {
                    right=false;
                    break;
                }
            }
        }
        if (!Bracket.isEmpty()) right=false;
        System.out.println(right? "Yes":"No "+n);
    }
}