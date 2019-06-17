/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionhelper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Jakub
 */
public class DataRepository {
    private ArrayList<Record> data=null;  //oryginalny zbiór danych w magazynie
    private String csvFilename;    //nazwa pliku z danymi
    
    /**
     * kontruktor, wypełnia bazę danych rekordami z pliku "database.csv";
     * @throws IOException
     * @throws FileNotFoundException 
     */
    public DataRepository() throws IOException,FileNotFoundException{
        csvFilename="database.csv";
        data=new ArrayList<>();
        BufferedReader br;
        String line="";
        br=new BufferedReader(new InputStreamReader(new FileInputStream(csvFilename), "UTF8"));
        if((line=br.readLine())!=null)
            line.split(";");
        else System.err.print("pusty plik");
        int i=0;
        while((line=br.readLine())!=null){//zapełnianie bazy
            Scanner scaner=new Scanner(line);
            if(!scaner.hasNext()){
                System.out.print("Koniec");
            }
            Record tempRecord=new Record(scaner,i);
            data.add(tempRecord);
            i++;
        }
    }
    /**
     * geter rozmiaru bazy danych
     * @return 
     */
    public int getSize(){ return data.size(); }
    /**
     * geter bazy danych
     * @return 
     */
    public ArrayList<Record> getData(){ return data; }
    
}
