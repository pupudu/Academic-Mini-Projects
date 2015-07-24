package SpreadSheet3;
import java.util.Observable;
import java.util.Observer;
import java.util.StringTokenizer;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
public class SpreadSheet3 {
    cell[][] X=new cell[10][5];//create several cells
    void addCell(int i,int j){
        X[i][j]=new cell();//instantiate several cases
        String name=Character.toString((char)(i+65))+Integer.toString(j);
        X[i][j].setName(name);
    }
    public static void main(String[] args) {
        
    }
}
class cell extends Observable implements Observer{
   
    static cell[][] X=new cell[10][5];
    String eqn=" ",txt="",name;
    void setName(String name){
        this.name=name;
    }
    float val=0;
    void setEntry(String input){
        if(isNum(input)){
            val=Float.parseFloat(input);
            txt=input;
            setChanged();
            notifyObservers(txt+"@"+name);
        }
        else if(input.charAt(0)=='='){
            txt=input;
            eqn=input;
        }
        else{
            txt=input;
        }
    }
    public boolean isNum( String input ){  
        try{  
            Float.parseFloat( input );  
            return true;  
        }  
        catch( NumberFormatException n ){  
            return false;  
        }  
    }
    @Override
    public void update(Observable o, Object arg) {
         setEntry(X);
    }
    void setEntry(cell[][] X){
        cell.X=X;
        String input=eqn;
        if(input.charAt(0)=='='){
            input=input.substring(1);
            String temp="";
            StringTokenizer st=new StringTokenizer(input, "-/*+",true);
            while(st.hasMoreElements()){
                String h=st.nextToken();
                if(!isNum(h)){
                    switch (h.charAt(0)){
                        case 'A':
                            h=X[0][Integer.parseInt(Character.toString(h.charAt(1)))].txt;
                            break;
                        case 'B':
                            h=X[1][Integer.parseInt(Character.toString(h.charAt(1)))].txt;
                            break;
                        case 'C':
                            h=X[2][Integer.parseInt(Character.toString(h.charAt(1)))].txt;
                            break;
                        case 'D':
                            h=X[3][Integer.parseInt(Character.toString(h.charAt(1)))].txt;
                            break;
                        case 'E':
                            h=X[4][Integer.parseInt(Character.toString(h.charAt(1)))].txt;
                            break;
                    }
                }
                temp+=h; 
            }
            compute(temp);
        }
    }
    void compute(String temp){
        temp=temp.replaceAll("=", "");
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        try {
            setEntry(engine.eval(temp).toString());
        } catch (ScriptException ex) {}
    }
}