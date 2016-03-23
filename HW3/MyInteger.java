public class MyInteger extends Element{
    public int c;
    public MyInteger(){
        c=0;
    }   
    
    public int Get(){
        return c;
    }
    
    public void Set(int val){
        c=val;
    }
    
    public void Print(){
        System.out.print(c);
    }
    
    
}