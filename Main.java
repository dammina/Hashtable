/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 *
 * @author Dammina Sahabandu 100466H
 * CS 2022 Data Stuctures and Algorithms
 * Programming Project Source Code
 */
class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException{

        FileInputStream fis=new FileInputStream("input.txt");
        DataInputStream dis=new DataInputStream(fis);
        BufferedReader br = new BufferedReader(new InputStreamReader(dis));

        String line;

        HashTable1 ht1=new HashTable1(1000);//initializing the first hashtable
        HashTable2 ht2=new HashTable2(400);//initializing the second hashtable

        while((line=br.readLine())!=null){
            if(line.equals("Keyword List Start")){
                while(!((line=br.readLine()).equals("Keyword List End"))){
                    StringTokenizer st=new StringTokenizer(line);
                    while(st.hasMoreTokens()){
                        String next=st.nextToken().toLowerCase();//read the keyword and store to next
                        String pageNo2=(st.nextToken());//read the page number and store it to pageNo2
                        int pageNo=Integer.parseInt(pageNo2);//convert the page number to integer value
                        int key=ht1.hashFunc(next);//creating the key for first hashTable
                        int key2=ht2.hashFunc(pageNo2);//creating the key for second hashTable
                        DataItem1 ditem=new DataItem1(next, pageNo, key);
                        DataItem2 ditem2=new DataItem2(next, pageNo2, key2);
                        ht1.insertItem(ditem);//insert the item to table 1
                        ht2.insertItem(ditem2);//insert the item to table 2
                    }
                }
            }
            else if(line.equals("Queries Start")){
                while(!((line=br.readLine()).equals("Queries End"))){
                    StringTokenizer st=new StringTokenizer(line);
                    while(st.hasMoreTokens()){
                        String operation = st.nextToken();//understanding the operation
                        String keyWord = st.nextToken().toLowerCase();//reading the keyword
                        char letter=operation.charAt(0);//extracting the first character of the keyword
                            switch(letter){
                                case 'F'://if Find
                                    ht1.find(keyWord,'F');//call the find method in first hashtable
                                    break;
                                case 'L'://if List
                                    ht1.find(keyWord, 'L');//call the find method in s hashtable
                                    break;
                                case 'K'://if Keywords
                                    ht2.find(keyWord);//call the find method in second hashtable
                                    break;
                                default:
                                    System.out.println("Invalid entry...");//if invalid command
                        }
                    }
                }
            }
        }
    }

    public static String getString() throws IOException{
    InputStreamReader isr=new InputStreamReader(System.in);
    BufferedReader br=new BufferedReader(isr);
    String str=br.readLine();
    return str;
    }//reading a String

    public static char getChar() throws IOException{
        String str=getString();
        return str.charAt(0);
    }//reading a Character

    public static int getInt() throws IOException{
        String str=getString();
        return Integer.parseInt(str);
    }//reading a Integer

}

/**
 *
 * @author Dammina Sahabandu 100466H
 * CS 2022 Data Stuctures and Algorithms
 * Programming Project Source Code
 */

class DataItem1{

    private String keyWord;
    private int pageNo;
    private int key;

    public DataItem1(String keyWord,int pageNo,int key){
        this.keyWord = keyWord;
        this.pageNo = pageNo;
        this.key = key;
    }//DataItem1 constructor

    public String getKeyWord(){
        return keyWord;
    }//return keyword

    public int getPageNo(){
        return pageNo;
    }//return pageNo

    public int getKey(){
        return key;
    }//return key

}

/**
 *
 * @author Dammina Sahabandu 100466H
 * CS 2022 Data Stuctures and Algorithms
 * Programming Project Source Code
 */

class DataItem2{

    private String keyWord;
    private String pageNo;
    private int key;

    public DataItem2(String keyWord,String pageNo,int key){
        this.keyWord = keyWord;
        this.pageNo = pageNo;
        this.key = key;
    }//DataItem1 constructor

    public String getKeyWord(){
        return keyWord;
    }//return keyword

    public String getPageNo(){
        return pageNo;
    }//return pageNo

    public int getKey(){
        return key;
    }//return key

}

/**
 *
 * @author Dammina Sahabandu 100466H
 * CS 2022 Data Stuctures and Algorithms
 * Programming Project Source Code
 */

class HashTable1{

    private DataItem1[] hashArray;
    private int arraySize;
    private boolean check=true;

    public HashTable1(int arraySize){
        this.arraySize = arraySize;
        hashArray = new DataItem1[arraySize];
    }//HashTable1 constructor

    public int hashFunc(String key){
        int hashValue = 0;
        for(int i=0;i<key.length();i++){
            int letter = key.charAt(i)-96;//letter=(ASCII value of first character) - ((ASCII value of 'a')-1)
            hashValue = (hashValue * 27 + letter) % arraySize;
        }//calculating the hashValue
        return hashValue;
    }//return the hashValue

    public void insertItem(DataItem1 ditem){
        int key=ditem.getKey();
        while(hashArray[key]!=null){
            ++key;
            key %=arraySize;
        }//if there is any DataItem, then store to the next adjcent index
        hashArray[key]=ditem;
    }//inserting a DataItem to hash table

    public void find(String keyWord,char x){
        int hashVal=hashFunc(keyWord);//taking the hash value of the given keyword
        linkedList list=new linkedList();

        try{
        for(int i=hashVal;i<arraySize;i++){
            if(hashArray[i]==null)
                break;//check only untill a empty index found

            if(hashArray[i].getKeyWord().equals(keyWord)){
                check=false;
                list.addNew(hashArray[hashVal].getPageNo());
                ++hashVal;
                hashVal %= arraySize;
            }//checking for a matching word

        }
        if(!check){
            switch (x){
                case 'F':
                    list.showFirst();
                    check=true;
                    break;
                case 'L':
                    list.show();
                    check=true;
                    break;
                    default:
            }
        }
        else{
            System.out.println("Invalid Keyword");//if nothing found
        }
        }
        catch(NullPointerException ex){
        }
    }

}

/**
 *
 * @author Dammina Sahabandu 100466H
 * CS 2022 Data Stuctures and Algorithms
 * Programming Project Source Code
 */

class HashTable2{
    private DataItem2[] hashArray;
    private int arraySize;

    public HashTable2(int arraySize){
        this.arraySize = arraySize;
        hashArray = new DataItem2[arraySize];
    }//HashTable constructor

    public int hashFunc(String key){
        int hashValue = 0;
        for(int i=0;i<key.length();i++){
            int letter = key.charAt(i)-48;//letter=(ASCII value of first character) - ((ASCII value of '1')-1)
            hashValue = (hashValue * 9 + letter) % arraySize;
        }//calculating the hashValue
        return hashValue;
    }//return the hashValue

    public void insertItem(DataItem2 ditem){
        int key=ditem.getKey();
        while(hashArray[key]!=null){
            ++key;
            key %=arraySize;
        }//if there is any DataItem, then store to the next adjcent index
        hashArray[key]=ditem;
    }//inserting a DataItem to hash table

    public void find(String keyWord){

        boolean check=true;//to check weather any matching word found
        int hashVal=hashFunc(keyWord);//taking the hash value of the given page number

        for(int i=hashVal;i<arraySize;i++){

            if(hashArray[i]==null)
                break;//check only untill a empty index found

            if(hashArray[i].getPageNo().equals(keyWord)){
                System.out.print(hashArray[i].getKeyWord()+" ");
                ++hashVal;
                hashVal %= arraySize;
                check=false;
            }//checking for a matching word

        }
        if(check)
            System.out.print("Invalid Page Number");//if nothing found
        System.out.print("\n");
    }
}

/**
 *
 * @author Dammina Sahabandu 100466H
 * CS 2022 Data Stuctures and Algorithms
 * Programming Project Source Code
 */

class linkedList{

    private node head;

    public linkedList(){
        head=null;
    }//initialise head node as null

    public boolean isEmpty(){
        return (head==null);
    }//if linked list has no elements

public void addNew(int key){

        node link=new node(key);
        node prv=null;
        node current=head;

        while(current!=null && key>current.getData()){
            prv=current;
            current=current.getNext();
        }//inserting new element to the correct sorted position of the linked list

        if(prv==null){
           head=link;
        }//if linked list has no elemnets

        else{
             prv.setNext(link);
        }

             link.setNext(current);

    }
    public void show(){

        node current=head;

        while(current!=null){
            System.out.print(current.getData()+" ");
            current=current.getNext();
        }//display the complete linked list through calling the getData method
        System.out.println("");//finally print a new line
    }

    public void showFirst(){
        node current=head;
        System.out.println(""+current.getData());
    }//display the first element of the linked list

}

/**
 *
 * @author Dammina Sahabandu 100466H
 * CS 2022 Data Stuctures and Algorithms
 * Programming Project Source Code
 */

class node{

    private node next;
    private int data;

    public node(int data){
        next = null;
        this.data = data;
    }//node constructor

    public int getData(){
        return data;
    }//get the data in the node

    public void setData(int data){
        this.data = data;
    }//set data to a node

    public node getNext(){
        return next;
    }//get the next node

    public void setNext(node next){
        this.next = next;
    }//creting the next node

}
