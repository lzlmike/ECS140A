public class MapIterator extends SequenceIterator{
    public MapIterator(Sequence current){
        super(current);
    }
    
    public Pair get(){
        return (Pair)super.get();
    }
}