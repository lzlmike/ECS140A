public class Map extends Sequence{
    public MapIterator begin(){
        MapIterator first=new MapIterator(this);
        return first;
    }
    public MapIterator end(){
        MapIterator last=new MapIterator(super.end().current);
        return last;
    }
    
    public void Print(){
        super.Print();
    }
    
    public void add(Pair inval){
        /*Map cur=this;
        if(cur.content==null){
            super.add(inval,0);
        }
        else{
            int br=0;
            for(int i=0;i<super.length() && br==0;i++){
                if(cur!=null && cur.content!=null && ((Pair)cur.content).getKey().Get()>inval.getKey().Get()){
                    super.add(inval,i);
                    br=1;
                
                }
            }
        }*/
        MapIterator start=this.begin();
        if(start.get()==null) super.add(inval,0);
        else{
            int i=0;
            while(inval.getKey().Get()>=start.get().getKey().Get()){
                start.advance(); //iterate to the next element;
                i++;
                if(start.current==null || start.current.content==null) break;
            }
            super.add(inval,i);
        }
    }
    
    public MapIterator find(MyChar key){
        MapIterator start=this.begin();
        while(start.current!=null && start.current.content != null && key.Get() != start.get().getKey().Get()){
            start.advance();
        }
        return start;
    }
}