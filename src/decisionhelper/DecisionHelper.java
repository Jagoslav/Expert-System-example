/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionhelper;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import static java.util.Arrays.sort;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

/**
 * główna klasa programu. 
 * @author Jakub
 */
public class DecisionHelper extends JFrame{
    public enum States{
        SEARCH,    //eliminacja danych
        SCORES, //lista wyników
        START;  //okno startowe
    }
    States state=States.START;  //stan aplikacji
    JPanel mainPanel;   //główny panel frame'a
    JButton menuButton,nextButton,viewButton;   //przyciski powrotu do menu, następne pytanie/akcja i podgląd wyników
    JTextArea questionField;    //pole wyświetlające aktualne pytanie
    JList answersList;   //lista odpowiedzi na zadawane pytanie
    JScrollPane scrollList; //scrollowana lista odpowiedzi
    AL actionListener;  //reakcja na klikanie guzików
    
    DataRepository database;    //oryginalna baza danych
    ArrayList<Record> temporaryDatabase;    //kopia bazy na której operuje wyszukiwarka
    String currentQuestion; //zadawane pytanie
    int questionID; //id pytania
    String[] answers;   //lista możliwych wyników w danej kategorii
    JPanel buttonPanel; //panel na przyciski
    ArrayList<String> question; //lista z pytaniami
    public DecisionHelper() throws IOException{
        setTitle("Asystent Wyboru Restauracji");
        setSize(480,240);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        
        database=new DataRepository();
        
        menuButton=new JButton();
        viewButton=new JButton();
        nextButton=new JButton();
        actionListener=new AL();
        menuButton.addActionListener(actionListener);
        viewButton.addActionListener(actionListener);
        nextButton.addActionListener(actionListener);
        answers=new String[0];
        questionID=-1;
        createQuestion();
        initState();
    }
    /**
     * metoda tworząca listę zadawanych pytań
     */
    public void createQuestion(){
        this.question=new ArrayList<>();
        //pytanie 1 rodzaj restauracji
        String quest="Jakiego rodzaju lokalu szukasz?";
        quest+="\nzaznacz dokładnie 1 odpowiedź";
        this.question.add(quest);
        //pyatnie 2 dzielnica
        quest="W której dzielnicy ma się znajdować?";
        quest+="\nzaznacz dokładnie 1 odpowiedź";
        this.question.add(quest);
        //pytanie 3
        quest="Czy szukasz lokalu ze względu na jakąś \nszczególną okazję?";
        quest+="\nzaznacz dokładnie 1 odpowiedź";
        this.question.add(quest);
        //pytanie 4
        quest="Poszukujesz specjalnych trunków?";
        quest+="\nzaznacz dokładnie 1 odpowiedź";
        this.question.add(quest);
        //pytanie 5
        quest="Czy lokal musi posiadać konkretną ocenę?";
        quest+="\nzaznacz dokładnie 1 odpowiedź";
        this.question.add(quest);
        //PYTANIA WIELOKROTNE
        //pytanie 6
        quest="Wybierz rodzaje kuchni, na których ci zależy";
        quest+="\nzaznacz 0 lub więcej odpowiedzi\n(użyj klawisza ctrl/shift w celu zaznaczenia \nkilku elementów)";
        this.question.add(quest);
        //pytanie 7
        quest="Potrzebujesz specjalnej dostawy?";
        quest+="\nzaznacz 0 lub więcej odpowiedzi\n(użyj klawisza ctrl/shift w celu zaznaczenia \nkilku elementów)";
        this.question.add(quest);
        //pytanie 8
        quest="W jakim rejonie ma znajdować się lokal?";
        quest+="\nzaznacz 0 lub więcej odpowiedzi\n(użyj klawisza ctrl/shift w celu zaznaczenia \nkilku elementów)";
        this.question.add(quest);
        //pytanie 9
        quest="Zależy ci na specjalnych daniach?";
        quest+="\nzaznacz 0 lub więcej odpowiedzi\n(użyj klawisza ctrl/shift w celu zaznaczenia \nkilku elementów)";
        this.question.add(quest);
        //pytanie 10
        quest="Potrzebujesz specjalnego menu?";
        quest+="\nzaznacz 0 lub więcej odpowiedzi\n(użyj klawisza ctrl/shift w celu zaznaczenia kilku elementów)";
        this.question.add(quest);
        //pytanie 11
        quest="Poszukujesz lokalu ze specjalnymi \nudogodnieniami?";
        quest+="\nzaznacz 0 lub więcej odpowiedzi\n(użyj klawisza ctrl/shift w celu zaznaczenia \nkilku elementów)";
        this.question.add(quest);
        //pytanie 12
        quest="Poszukujesz dodatkowej rozrywki?";
        quest+="\nzaznacz 0 lub więcej odpowiedzi\n(użyj klawisza ctrl/shift w celu zaznaczenia \nkilku elementów)";
        this.question.add(quest);
        //pytanie 13
        quest="Wybierz dodatkowe informacje o resturacji";
        quest+="\nzaznacz 0 lub więcej odpowiedzi\n(użyj klawisza ctrl/shift w celu zaznaczenia \nkilku elementów)";
        this.question.add(quest);
    }
    /**
     * metoda modyfikująca zawartość okna aplikacji w zależności od aktualnego stanu aplikacji
     */
    public void initState(){
        getContentPane().removeAll();
        mainPanel= new JPanel();
        switch(state){
            case START: //okno tytułowe aplikacji, można z niego podejżeć całą bazę lub rozpocząć wyszukiwanie
                //reset tymczasowej bazy
                temporaryDatabase=new ArrayList<>(database.getData());
                currentQuestion=question.get(questionID=0);
                 
                mainPanel.setLayout(new BorderLayout());
                JPanel topPanel=new JPanel();
                topPanel.add(new JLabel("Asystent Wyboru Restauracji", SwingConstants.CENTER));
                mainPanel.add(topPanel,BorderLayout.CENTER);
                buttonPanel=new JPanel(new GridLayout(1,2));
                viewButton.setText("Lista restauracji");
                nextButton.setText("rozpocznij wyszukiwanie");
                buttonPanel.add(viewButton);
                buttonPanel.add(nextButton);
                mainPanel.add(buttonPanel,BorderLayout.PAGE_END);
                break;
            case SCORES:    //okno wynikowe, pozwala podejżeć wyniki z aktualnie sprawdzanej bazy (całej lub po wykluczeniach)
                mainPanel.setLayout(new BorderLayout());
                scrollList=new JScrollPane(new JList(getFinalList()));
                mainPanel.add(scrollList,BorderLayout.CENTER);
                if(getFinalList().length==database.getSize())
                    mainPanel.add(new JLabel(" pełna baza danych"),BorderLayout.PAGE_START);
                else if(getFinalList().length==0)
                    mainPanel.add(new JLabel(" żadne lokale nie spełniają żądanych kryteriów"),BorderLayout.PAGE_START);
                else if(getFinalList().length==1)
                    mainPanel.add(new JLabel(" lokal najlepiej pasujący do twoich oczekiwań to: "),BorderLayout.PAGE_START);
                else
                    mainPanel.add(new JLabel(" lokale które spełniają twoje oczekiwania to:"),BorderLayout.PAGE_START);
                buttonPanel=new JPanel(new GridLayout(1,2));
                menuButton.setText("Menu");
                nextButton.setText("rozpocznij wyszukiwanie");
                buttonPanel.add(menuButton);
                buttonPanel.add(nextButton);
                mainPanel.add(buttonPanel,BorderLayout.PAGE_END);
                break;
            case SEARCH:    //proces eliminacji rekordów, można wrócić do strony tytułowej, przerwać szukanie od razu wyświetlając wynik lub szukać dalej
                mainPanel.setLayout(new GridLayout(1,2));
                //lewa strona
                answers=answers(questionID);
                answersList=new JList(answers);
                if(questionID>4)
                    answersList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                else
                    answersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                answersList.setLayoutOrientation(JList.VERTICAL_WRAP);
                answersList.setVisibleRowCount(-1);
                scrollList=new JScrollPane(answersList);
                mainPanel.add(scrollList);
                //prawa strona
                JPanel rightPanel=new JPanel(new GridLayout(2,1));
                questionField=new JTextArea(currentQuestion);
                questionField.setEditable(false);
                questionField.setLineWrap(true);
                rightPanel.add(questionField);
                JPanel buttonList=new JPanel();
                buttonList.setLayout(new GridLayout(3,1));
                menuButton.setText("Menu Główne");
                viewButton.setText("aktualna lista");
                nextButton.setText("Nastepna kategoria");
                buttonList.add(menuButton);
                buttonList.add(viewButton);
                buttonList.add(nextButton);
                rightPanel.add(buttonList);
                mainPanel.add(rightPanel);
                break;
            default:
                System.exit(1);
                break;
        }
        add(mainPanel);
        getContentPane().validate();
    }
    /**
     * actionListener przycisków aplikacji.
     */
    public class AL implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(state==States.START){    //działanie przycisków okna startowego
                if(e.getSource()==nextButton){
                    state=States.SEARCH;
                }
                if(e.getSource()==viewButton){
                    state=States.SCORES;
                }
            }
            else if(state==States.SEARCH){   //działanie przycisków w oknie przeszukiwania bazy
                if(e.getSource()==menuButton){
                    state=States.START;
                }
                else if(e.getSource()==viewButton){
                    state=States.SCORES;
                }
                else if(e.getSource()==nextButton){ //następne pytanie
                    //blokada nie udzielenia odpowiedzi
                    List<String> selectedItems;
                    selectedItems=answersList.getSelectedValuesList();
                    if(!selectedItems.isEmpty())  //jeśli zaznaczono odpowiedzi
                        keepOnly(selectedItems); //usunięcie zbędnych rekordów
                    
                    if(temporaryDatabase.size()==1) //wymuszenie wyjścia jeśli został 1 rekord
                        state=States.SCORES;
                    //iteracja zadawanego pytania i aktualizacja wyników
                    answers=null;
                    while(answers==null){   //jeśli lista odpowiedzi jest nullem, iterujemy pytanie
                        if(questionID<12){
                            questionID++;
                            answers=answers(questionID);
                        }
                        else {
                            state=States.SCORES;
                            break;
                        }
                    }
                    currentQuestion=question.get(questionID);
                }
            }
            else if(state==States.SCORES){  //wyniki
                if(e.getSource()==menuButton){
                    state=States.START;
                }
                else if(e.getSource()==nextButton){
                    temporaryDatabase=new ArrayList<>(database.getData());
                    currentQuestion=question.get(questionID=0);
                    
                    state=States.SEARCH;
                }
            }
        initState();
        }
    }
    /**
     * zwraca listę rekordów przechowywanych w bazie tymczasowej. posortowaną alfabetycznie w porządku rosnącym
     * @return 
     */
    public String[] getFinalList(){
        String[] list=new String[temporaryDatabase.size()];
        for(int i=0;i<list.length;i++)
            list[i]=temporaryDatabase.get(i).getInfo();
        sort(list);
        return list;
    }
    /**
     * definiuje listę odpowiedzi na zadawane pytanie posortowaną alfabetycznie w porządku rosnącym
     * zwraca null jeśli możliwych odpowiedzi jest mniej niż 2
     * @param id
     * @return 
     */
    public String[] answers(int id){
        ArrayList<String> values=new ArrayList<>();
        for(int i=0;i<temporaryDatabase.size();i++){
            String pom[]=new String[(temporaryDatabase.get(i).getValue(id).length)];    //utworzenie tablicy z danymi o danym indeksie
            pom=temporaryDatabase.get(i).getValue(id);  //pobranie wartosci odpowiedzi o danym indeksie
            for(int j=0;j<pom.length;j++){  //dodanie unikalnych wartosci-odpowiedzi
                if(pom[j].equals(""))
                    continue;
                else if(!values.contains(pom[j]))
                    values.add(pom[j]);
            }
        }
        String[] tab=new String[values.size()];
        tab=values.toArray(tab);
        sort(tab);
        if(tab.length<=1)
            return null;
        else return tab;
    }
    /**
     * metoda służąca do usunięcia z bazy tymczasowej rekordów nie spełniających podanego kryterium
     * @param items 
     */
    public void keepOnly(List<String> items){
        for(int j=temporaryDatabase.size()-1;j>=0;j--){
            boolean found=false;    //flaga znalezienia przynajmniej jednego kryterium
            for(String item:items){
                if(found==true)
                    break;
                String[] s=temporaryDatabase.get(j).getValue(questionID);
                for(int i=0;i<s.length;i++){
                    if(found==true)
                        break;
                    else if(s[i].equals(item))
                        found=true;
                }
            }
            if(found==false)
                temporaryDatabase.remove(j);
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        new DecisionHelper();
    }
    
}
