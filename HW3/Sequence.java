public class Sequence extends Element{
    public Element content;
    public Sequence next;
    
    public Sequence(){
        content=null;
        next=null;
    }
    
    //print element in the sequence;
    public void Print(){
        Sequence current=this;
        System.out.print("[");
        while(current!=null){
            System.out.print(" ");
            current.content.Print();
            current=current.next;
        }
        System.out.print(" ]");
    }
    
    public Element first(){
        return this.content;
    }// return the first element in the seq;
    
    public Sequence rest(){
        return this.next;
    }
    
    public int length(){
        int l=0;
        Sequence current=this;
        while(current!=null){
            l++;
            current=current.next;
        }
        return l;
    }
    
    public void add(Element elm,int pos){
        Sequence current=this;
        int len=current.length();
        if(len<pos || pos<0){
            System.err.println("Sorry out of Bounds");
        }
        else{
            Sequence pre=this;
            int i=0;
            while(i<pos){
                pre=current;
                current=current.next;
                i++;
            }
            if(current==null){
                Sequence temp=new Sequence();
                temp.content=elm;
                temp.next=null;
                pre.next=temp;
            }else if(current.content==null){
                current.content=elm;
                current.next=null;
            }
            else{
                Sequence temp=new Sequence();
                temp.content=current.content;
                temp.next=current.next;
                current.content=elm;
                current.next=temp;
            }
        }
        
    }
    
    public void delete(int pos){
        Sequence current=this;
        if(current.length()<=pos || pos<0){
            System.err.println("Sorry out of Bounds");
        }
        else{
            int i=0;
            while(i<pos){
                current=current.next;
                i++;
            }
            current.content=current.next.content;
            current.next=current.next.next;
        }
    }
    
    public Element index(int pos){
        Sequence current=this;
        if(pos>=current.length() || pos<0) {
            System.err.println("Sorry out of Bounds");
            System.exit(1);
        }
        for(int i=0;i<pos;i++) current=current.next;
        return current.content;
    }
    
    public Sequence flatten(){
        Sequence current=this;
        Sequence newSeq=new Sequence();
        for(int i=0;i<current.length();i++){
            Element data=current.index(i);
            if(data instanceof MyChar || data instanceof MyInteger){
                if(newSeq.content==null)
                    newSeq.add(data,newSeq.length()-1);
                else
                    newSeq.add(data,newSeq.length());
            }//if the data type is int or char;
            else{
                Sequence temp=(Sequence) data;
                temp=temp.flatten();
                for(int j=0;j<temp.length();j++){
                    if(newSeq.content==null)
                        newSeq.add(temp.index(j),newSeq.length()-1);
                    else
                        newSeq.add(temp.index(j),newSeq.length());
                }
            }
        }
        return newSeq;
    }
    
    public Sequence copy(){
        Sequence newSeq=new Sequence();
        Sequence current=this;
        for(int i=0;i<current.length();i++){
            Element data=current.index(i);
            if(data instanceof Sequence){
                if(newSeq.content==null)
                    newSeq.add(((Sequence)data).copy(),newSeq.length()-1);
                else
                    newSeq.add(((Sequence)data).copy(),newSeq.length());
            }
            else if(data instanceof MyChar){
                MyChar ch=new MyChar();
                ch.Set(((MyChar)data).Get());
                if(newSeq.content==null)
                    newSeq.add(ch,newSeq.length()-1);
                else
                    newSeq.add(ch,newSeq.length());
            }
            else{
                MyInteger Int=new MyInteger();
                Int.Set(((MyInteger)data).Get());
                if(newSeq.content==null)
                    newSeq.add(Int,newSeq.length()-1);
                else
                    newSeq.add(Int,newSeq.length());
            }
        }
        return newSeq;
    }
    
    public SequenceIterator begin(){
        SequenceIterator first=new SequenceIterator(this);
        return first;
    }
    
    public SequenceIterator end(){
        Sequence current=this;
        Sequence dumbing=new Sequence();
        while(current.next!=null) current=current.next;
        current.next=dumbing;  // add a dumbing elelment at the end;
        SequenceIterator last=new SequenceIterator(dumbing);
        return last;
    }
    
    
}