public class SequenceIterator{
    Sequence current;
    public SequenceIterator(Sequence temp){
        current=temp;
    }
    
    public SequenceIterator advance(){
        current=current.rest();
        return this;
    }
    
    public Element get(){
        return current.content;
    }
    
    public boolean equal(SequenceIterator it){
        if(current.content == it.current.content)
            return true;
        return false;
    }
}