public class MyChar extends Element{
    public char c;
    public MyChar(){
        c='0';
    }   
    
    public char Get(){
        return c;
    }
    
    public void Set(char val){
        c=val;
    }
    
    public void Print(){
        System.out.print("'"+c+"'");
    }
    
}
