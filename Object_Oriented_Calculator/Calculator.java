
import java.util.Scanner;
public class Calculator {
    //method which accepts input from the user and do the calculation to display the output on screen
    public void calculate(){
        System.out.println("This program uses OOP concepts.therefore you can edit your inputs."+"\n"+"please follow the instructions.ENTER DIGITS ONLY(i.e. 0 to 10)\n");
        
        Scanner scan = new Scanner(System.in);          //to scan input from the user
        
        Number[] number = new Number[10];               //creates an array of number objects
        number[0] = new Number("0");
        number[1] = new Number("1");
        number[2] = new Number("2");
        number[3] = new Number("3");
        number[4] = new Number("4");
        number[5] = new Number("5");
        number[6] = new Number("6");
        number[7] = new Number("7");
        number[8] = new Number("8");
        number[9] = new Number("9");
        
        Screen screen = new Screen();                              //creats the screen of the calculator       
        
        System.out.println("Enter digits of number, Enter 'Enter' to move to next digit, > to clear,< to backspace");
    
        boolean check = true;                                      //to control the loop
        String input;                                   	   //to take user's input
        
        //take a loop of digits to create the first number(as in calculator with buttons)
        while(check){                                               
            input = scan.next();                                    //scan user's input as a string
            if(input.equals("+") || input.equals("-") || input.equals("*") || input.equals("/")){  //if operator is given, assume that scanning of first number is completed
                screen.symbl= input;                
                break;
            }
            if(input.equals(">") || input.equals("<")){             //clear and backspace operations
                  Operator operator1 = new Operator(input);         //creates an operator1 button
                  operator1.doOperation(screen);                    //press the button
                  continue;                                         //restart the loop to continue scanning digits
            }
            int digit = Integer.parseInt(input);                    //convert the string input to integer
            if(digit<0 || digit>9){                                 //one digit is entered once
                System.out.println("invalid input.enter single digits only.Program will exit");
                System.exit(1);                                     //exit the program
            }    
            screen.no1 = screen.no1+ number[digit].show();          //concatenate strings              
        }
        screen.n1=1;                                                //number1 scanned succesfully
        
        //take a loop of digits to create the second number(as in calculator with buttons)
        check = true;
        while(check){
           input = scan.next();                                     //scan user's input as a string
           if(input.equals("=")){                                   //if = is pressed, assume that scanning of second number is completed
                break;
            }
            if(input.equals(">") || input.equals("<")){             //clear and backspace operations
                  Operator operator2 = new Operator(input);         //creates an operator2 button
                  operator2.doOperation(screen);                    //press the button
                  continue;                                         //restart the loop to continue scanning digits
            }
            int digit = Integer.parseInt(input);                    //convert the string input to integer
            if(digit<0 || digit>9){                                 //one digit is entered once
                System.out.println("invalid input.enter single digits only.Program will exit");
                System.exit(1);                                     //exit the program
            }    
            screen.no2 = screen.no2+ number[digit].show();          //concatenate strings             
        }  
        screen.n2=1;                                                //number2 scanned succesfully        
        
        Operator operator3 = new Operator(screen.symbl);            //creates the operator3 button to do the required operation
        operator3.doOperation(screen);                              //press the operator button  
        Operator operator4 = new Operator("=");                     //creates the operator4 button to dislay result on screen
        operator4.doOperation(screen);                              //press the display button  
    }
    
    public static void main(String[] args) {
        Calculator_NIN cal = new Calculator_NIN();                  //creates a calculator object
        cal.calculate();                                            //calls the cal for operation
    }
    
}

class Buttons{                                              //super class which represents a button in the calculator
    private String name;
    Buttons(String theName){                                //super class constructor
        name = theName;
    }
    public String show(){                                     //show the button's name
        //System.out.println("You have pressed ["+name+"] key and it is scanned succesfully\n");
        return name; 
    }
}

class Number extends Buttons{                               //sub class to represent a numerical button
    private int value;
    private String name;
    Number(String theName){                                 //subclass constructor
        super(theName);
        name = theName;
    }    
    public int convertToInt(){                              //convert the name of the number button to integer and return its integer value 
        value = Integer.parseInt(name);
        return value;
    }
    
}

class Operator extends Buttons{                             //sub class to represent a operator button
    private String name;
    Operator(String theName){                               //subclass constructor
        super(theName);
        name = theName;
    }
    public void doOperation(Screen test){                   //press the button and make its fuctionality                              
        switch(name){                                       //move throgh different operator buttons
            case "+":
                     test.result = Integer.toString(test.getNum(test.no1)+test.getNum(test.no2));
                     break;
            case "-":
                     test.result = Integer.toString(test.getNum(test.no1)-test.getNum(test.no2));
                     break;
            case "/":
                     test.result = Integer.toString(test.getNum(test.no1)/test.getNum(test.no2));
                     break;
            case "*":
                     test.result = Integer.toString(test.getNum(test.no1)*test.getNum(test.no2));
                     break;
            case ">":                                       //clear the number
                     if(test.n1==0){                        //if number1 is not scanned
                         test.no1="";
                         System.out.println("Number deleted. Enter number1 from begining");
                         break;
                     } 
                     if(test.n1==1){                        //if number2 is not scanned
                         test.no2="";
                         System.out.println("Number deleted. Enter number2 from begining");
                         break;
                     } 
            case "<":                                       //delete the last digit of the number
                    if(test.n1==0){                         //if number1 is not scanned completely
                        test.no1 = test.no1.substring(0, test.no1.length()-1);
                        System.out.println("Last digit deleted.Enter next digits");
                        break;
                    }
                 if(test.n1==1){                            //if number1 is not scanned completely
                        test.no2 = test.no2.substring(0, test.no2.length()-1);
                        System.out.println("Last digit deleted.Enter next digits");
                        break;
                    }
            case "=":
                    test.printToScreen();                    //display the result on calculator screen
        }
        
    }
}

class Screen{                                               //the screen of the calculator
    String no1="",no2="",symbl="",result="";
    int n1=0,n2=0;
    public void printToScreen(){                            //displays the result of the calculation
        System.out.println(no1+ symbl + no2 + "=" + result+"\n");
    }
    public int getNum(String n){                            //returns the operands of the calculation as integers
        return Integer.parseInt(n);
    }    
}
