/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionhelper;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 *
 * @author Jakub
 */
public class Record { 
    //lista atrybutów reprezentujących restaurację
    private String[] contactInformations;   //nazwa, telefon, adres
    private String[] commonFeatures;    //rodzaj, dzielnica, okolica
    private ArrayList<String> delivery=new ArrayList<String>(); //lista ze sposobami dostawy
    private String rating;  //ocena restauracji 
    private ArrayList<String> cuisine=new ArrayList<String>();  //lista z rodzajami kuchni
    private String event;   //możliwość dla danej restauracji 
    private ArrayList<String> menu=new ArrayList<String>(); //specjalna lista z menu 
    private ArrayList<String> dishes=new ArrayList<String>();   //lista ze specjalnymi daniami
    private ArrayList<String> facilities=new ArrayList<String>();   //lita udogodnień
    private String drink;   //alkohol
    private ArrayList<String> entertainment=new ArrayList<String>();    //lista z rozrywkami w restauracji
    private ArrayList<String> additives=new ArrayList<String>();    //dodatkowe funkcje restauracji
    
    /**
     * konstruktor
     * @param scaner
     * @param i 
     */
    public Record(Scanner scaner, int i){
        int kolo;
        if(i==14)
            kolo=0;
        int line=0;
        
        try{
            scaner.useDelimiter(";");
            this.contactInformations=new String[3];
            //wczytanie danych adresowych restauracji
            this.contactInformations[0]=scaner.next();
            line++;
            this.contactInformations[1]=scaner.next();
            line++;
            this.contactInformations[2]=scaner.next();
            line ++;
            this.commonFeatures=new String[3];
            //wczytanie rodzaju, okolicy i dzielnicy restauracji
            this.commonFeatures[0]=scaner.next();
            line ++;
            this.commonFeatures[1]=scaner.next();
            line ++;
            this.commonFeatures[2]=scaner.next();
            line ++;
            Scanner pom=new Scanner(scaner.next());
            pom.useDelimiter(",");
            //wczytanie dostaw
            while(pom.hasNext())
                this.delivery.add(pom.next());
            line ++;
            this.rating=scaner.next();
            Scanner cui=new Scanner(scaner.next());
            cui.useDelimiter(",");
            line ++;
            while(cui.hasNext())
                this.cuisine.add(cui.next());
            line ++;
            this.event=scaner.next();
            Scanner me=new Scanner(scaner.next());
            line ++;
            me.useDelimiter(",");
            while(me.hasNext())
                this.menu.add(me.next());
            Scanner di=new Scanner(scaner.next());
            di.useDelimiter(",");
            line ++;
            while(di.hasNext())
                this.dishes.add(di.next());
            Scanner fa=new Scanner(scaner.next());
            fa.useDelimiter(",");
            line ++;
            while(fa.hasNext())
                this.facilities.add(fa.next());
            line ++;
            this.drink=scaner.next();
            line ++;
            Scanner en=new Scanner(scaner.next());
            en.useDelimiter(",");
            while(en.hasNext())
                this.entertainment.add(en.next());
            Scanner ad=new Scanner(scaner.next());
            ad.useDelimiter(",");
            line ++;
            while(ad.hasNext())
                this.additives.add(ad.next());
            int k=0;
        }
        catch(NoSuchElementException e){
            System.out.println("Bład"+String.valueOf(i)+"  "+String.valueOf(line));
        }
    }
    /**
     * zwraca informacje kontaktowe rekordu: nazwę, adres, telefon(jeśli podany)
     * @return 
     */
    public String getInfo(){
        String info="";
        info+=contactInformations[0]+", ul. "+contactInformations[2];
        if(!contactInformations[1].equals(""))
            info+=", tel. "+contactInformations[1];
        return info;
    }
    /**
     * metoda pobierająca listę wartości parametru o podanym id
     * @param ID
     * @return 
     */
    public String[] getValue(int ID){
        String tab[]=null;       //tablica z danymi do zwrotu
        switch(ID){
            case 0:{            //rodzaj resturacji
                tab=new String[1];
                tab[0]=this.commonFeatures[0];
                break;
            }
            case 1:     //dzielnica
                tab=new String[1];
                tab[0]=this.commonFeatures[1];
                break;
            case 2:     //okazja
                tab=new String[1];
                tab[0]=this.event;
                break;
            case 3: //alkohol
                tab=new String[1];
                tab[0]=this.drink;
                break;
            case 4: //ocena
                tab=new String[1];
                tab[0]=this.rating;
                break;
            case 5:     //rodzaje kuchni
                tab=new String[this.cuisine.size()];
                tab=this.cuisine.toArray(tab);
                break;
            case 6:     //dostawa
                tab=new String[this.delivery.size()];
                tab=this.delivery.toArray(tab);
                break;
            case 7:     //rejon
                tab=new String[1];
                tab[0]=this.commonFeatures[2];
                break;
            case 8:     //dania
                tab=new String[this.dishes.size()];
                tab=this.dishes.toArray(tab);
                break;
            case 9: //menu
                tab=new String[this.menu.size()];
                tab=this.menu.toArray(tab);
                break;
            case 10:    //udogodnienia
                tab=new String[this.facilities.size()];
                tab=this.facilities.toArray(tab);
                break;
            case 11:        //rozrywka
                tab=new String[this.entertainment.size()];
                tab=this.entertainment.toArray(tab);
                break;
            case 12:    //dodatki
                tab=new String[this.additives.size()];
                tab=this.additives.toArray(tab);
                break;
        }
        return tab;
    }
    
}
