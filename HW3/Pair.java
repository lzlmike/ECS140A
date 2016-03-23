public class Pair extends Element{
    public MyChar key;
    public Element val;
    
    public Pair(MyChar k,Element v){
        key=k;
        val=v;
    }
    public MyChar getKey(){
        return key;
    }
    
    public void Print(){
        System.out.print("(");
        key.Print();
        System.out.print(" ");
        val.Print();
        System.out.print(")");
    }
    
    
    
}