class Matrix extends Sequence {
// constructor for creating a matrix of specific number of rows and columns
    Sequence mat=new Sequence();
    public int row,col;
    public Matrix(int rowsize, int colsize){
        int size=rowsize*colsize;
        row=rowsize;
        col=colsize;
        for(int i=0;i<size;i++){
            MyInteger temp=new MyInteger();
            mat.add(temp,i);
        }
    }
    
    public void Set(int rowsize, int colsize, int value){
        int indx=rowsize*this.col+colsize;
        ((MyInteger)mat.index(indx)).c=value;
    } // set the value of an element
    
    public int Get(int rowsize, int colsize){
        int indx=rowsize*this.col+colsize;
        return ((MyInteger)mat.index(indx)).c;
    }// get the value of an element
    
    public Matrix Sum(Matrix m){
        Matrix result=new Matrix(this.row,this.col);
        for(int i=0;i<this.row;i++){
            for(int j=0;j<this.col;j++){
                result.Set(i,j,this.Get(i,j)+m.Get(i,j));
            }
        }
        return result;
    } // return the sum of two matrices: m & this
    
    public Matrix Product(Matrix m){
        if(this.col!=m.row){
            System.out.println("Matrix dimensions incompatible for Product");
            System.exit(1);
        }
        Matrix res=new Matrix(this.row,m.col);
        for(int i=0;i<res.row;i++){
            for(int j=0;j<res.col;j++){
                int sum=0;
                for(int k=0;k<m.row;k++){
                    sum+=this.Get(i,k)*m.Get(k,j);
                }
                res.Set(i,j,sum);
            }
        }
        return res;
        
    }// return the product of two matrices: mat & this
    
    public void Print(){
        for(int i=0;i<this.row;i++){
            System.out.print("[");
            for(int j=0;j<this.col;j++){
                System.out.print(" "+this.Get(i,j));
            }
            System.out.print(" ]\n");
        }
    }// print the matrix;
}