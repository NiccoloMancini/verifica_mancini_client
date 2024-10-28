package it.itismeucci;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws UnknownHostException, IOException {
        Scanner scanner = new Scanner(System.in);
        Socket s = new Socket("localhost", 3000);
        System.out.println("il client si è collegato");
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        DataOutputStream out = new DataOutputStream(s.getOutputStream());
        String stringaScritta;
        do {
            System.out.println("1) Invia una nota(qualsiasi testo)");
            System.out.println("2) Ricevi la lista delle note salvate");
            System.out.println("3) Rimuovi elemento dalla lista");
            System.out.println("4) Termina la connessione");
            System.out.print("Inserire uno dei seguenti numeri: ");
            stringaScritta = scanner.nextLine();
            out.writeBytes(stringaScritta + "\n");
            switch (stringaScritta) {
                case "1":
                    System.out.print("Inserisci la nota da inviare: ");
                    stringaScritta = scanner.nextLine();
                    out.writeBytes(stringaScritta + "\n");
                    if (in.readLine().equals("OK")) {
                        System.out.println("Nota salvata");
                    }
                    break;
                case "2":
                    out.writeBytes("?" + "\n");
                    System.out.println("Eccoti la lista delle note da te inserite: ");
                    String stringaRicevuta = in.readLine();
                    while (!stringaRicevuta.equals("@")) {
                        System.out.println(stringaRicevuta);
                        stringaRicevuta = in.readLine();
                    }
                    break;
                case "3":
                    out.writeBytes("x" + "\n");
                    System.out.println("Inserire elemento da rimuovere: ");
                    stringaScritta = scanner.nextLine();
                    out.writeBytes(stringaScritta + "\n");
                    if (in.readLine().equals("*")) {
                        System.out.println("elemento rimosso correttamente");
                    }else{
                        System.out.println("elemento non rimosso");
                    }
                    break;
                case "4":
                    out.writeBytes("!" + "\n");
                    System.out.println("comunicazione terminata");
                    break;
            }    
        } while (!stringaScritta.equals("4"));
        s.close();
        scanner.close();
    }
}