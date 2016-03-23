/* *** This file is given as part of the programming assignment. *** */
import java.util.*;

public class Parser {


    // tok is global to all these parsing methods;
    // scan just calls the scanner's scan method and saves the result in tok.
    private Token tok; // the current token
    
    public int block_num;
    //public int declare;
    public ArrayList<ArrayList<String>> blocks= new ArrayList<ArrayList<String>>();
    
    private void scan() {
	    tok = scanner.scan();
    }

    private Scan scanner;
    Parser(Scan scanner) {
	    this.scanner = scanner;
	    scan();
	    program();
	    if( tok.kind != TK.EOF )
	        parse_error("junk after logical end of program");
    }
    private void printc(String s){
        System.out.print(s);
    }
    
    private void program() {
        printc("#include<stdio.h>\n");
        printc("main(){\n");
        block_num=-1;
	    block();
	    printc("}");
    }

    private void block(){
        block_num++;
        //declare=0;
        ArrayList<String> empty=new ArrayList<String>();
        blocks.add(empty);
	    declaration_list();
	    statement_list();
	   //if(declare==1){
	        blocks.get(block_num).clear();
	        block_num--;
	    //}
	    //declare=0;
    }

    private void declaration_list() {
	// below checks whether tok is in first set of declaration.
	// here, that's easy since there's only one token kind in the set.
	// in other places, though, there might be more.
	// so, you might want to write a general function to handle that.
	    
    	while( is(TK.DECLARE) ) {
    	    //declare=1;
    	    //if(block_num!=0){
    	      //  block_num++;
    	        //declaration();
    	  //  }else{
                declaration();
    	    //}
	    }
    }

    private void declaration() {
        int level=block_num;
        int first=0;
        ArrayList<String> list=blocks.get(level);
	    mustbe(TK.DECLARE);
	    printc("int ");
	    if(is(TK.ID)){
	        if(list.size()==0){
	            list.add(tok.string);
	            printc("x_"+tok.string+Integer.toString(level));
	            first=1;
	        }
	        else{
	            for(int i=0;i<list.size();i++){
	                if(list.get(i).equals(tok.string)){
	                    System.err.println("redeclaration of variable "+tok.string);
	                    break;
	                }
	                else if(i+1==list.size()){
	                    list.add(tok.string);
	                    printc("x_"+tok.string+Integer.toString(level));
	                    first=1;
	                    break;
	                }
	            }
	        }
	    }
	    //printc("x_"+tok.string+Integer.toString(level));
	    mustbe(TK.ID);
	    
	    while( is(TK.COMMA) ) {
	        scan();
	        if(is(TK.ID)){
	            for(int i=0;i<list.size();i++){
	                if(list.get(i).equals(tok.string)){
	                    System.err.println("redeclaration of variable "+tok.string);
	                    break;
	                }
	                else if(i+1==list.size()){
	                    list.add(tok.string);
	                    if(first==1) printc(",");
	                    printc("x_"+tok.string+Integer.toString(level));
	                    first=1;
	                    break;
	                }
	            }
	        }
	    //printc("x_"+tok.string+Integer.toString(level));
	    mustbe(TK.ID);
        }
        printc(";\n");
    }

    private void statement_list() {
        while(is(TK.PRINT) || is(TK.DO) || is(TK.IF) || is(TK.ID) || is(TK.TILDE) || is(TK.LFOR)){
            statement();
            //System.out.println("error");
        }
    }
    
    private void statement(){
        if(is(TK.PRINT)) r_print();
        if(is(TK.DO)) r_do();
        if(is(TK.IF)) r_if();
        if(is(TK.LFOR)) r_for();
        if(is(TK.ID) || is(TK.TILDE)) r_assignment();
    }
    
    private void r_for(){
        int left=0;
        int right=0;
        mustbe(TK.LFOR);
        if(is(TK.NUM)){
            printc("int i;\n for(i=");
            printc(tok.string+";");
            left=Integer.parseInt(tok.string);
            scan();
        }
        mustbe(TK.COMMA);
        if(is(TK.NUM)){
            right=Integer.parseInt(tok.string);
            if(left<right) printc("i<="+right+";i++){\n");
            else printc("i>="+right+";i--){\n");
            scan();
        }
        mustbe(TK.COMMA);
        block();
        printc("\n}\n");
        mustbe(TK.RFOR);
    }
    
    private void r_assignment(){
        ref_id();
        printc("=");
        mustbe(TK.ASSIGN);
        expr();
        printc(";\n");
    }
    
    
    private void r_do(){
        printc("while");
        mustbe(TK.DO);
        guarded_commend();
        mustbe(TK.ENDDO);
    }
    
    private void r_if(){
        mustbe(TK.IF);
        printc("if");
        guarded_commend();
        while(is(TK.ELSEIF)){
            scan();
            printc("else if");
            guarded_commend();
        }
        if(is(TK.ELSE)){
            scan();
            printc("else{\n");
            block();
            printc("\n}\n");
        }
        mustbe(TK.ENDIF);
       // printc("\n}\n");
    }
    
    private void guarded_commend(){
        printc("(0>=");
        expr();
        printc("){\n");
        mustbe(TK.THEN);
        block();
        printc("\n}\n");
    }
    
    
    private void r_print(){
        mustbe(TK.PRINT);
        printc("printf(");
        printc("\"%d\\n\",");
        expr();
        printc(");\n");
    }
    
    private void expr(){
        //printc("(");
        term();
        while(is(TK.PLUS) || is(TK.MINUS)){
            printc(tok.string);
            scan();
            term();
        }
    }
    
    private void term(){
        factor();
        while(is(TK.TIMES) || is(TK.DIVIDE)){
            printc(tok.string);
            scan();
            factor();
        }
    }
    
    private void factor(){
        if(is(TK.LPAREN)){
            printc("(");
            scan();
            expr();
            printc(")");
            mustbe(TK.RPAREN);
        }
        else if(is(TK.NUM)){
            printc(tok.string);
            scan();
            //printc(tok.string);
        }
        else{
            ref_id();
        } 
    }
    
    private void ref_id(){
        int level=block_num;
        int k=-2;
        int exist=0;
        ArrayList<String> list=blocks.get(level);
        if(is(TK.TILDE)){
            k=-1;
            scan();
            if(is(TK.NUM)){
                k=Integer.parseInt(tok.string);
                if(k>level){
                    scan();
                    System.err.println("no such variable ~"+k+tok.string+" on line "+tok.lineNumber);
                    System.exit(1);
                }
                scan();
            }else {
                for(int i=0;i<blocks.get(0).size();i++){
                    if(blocks.get(0).get(i).equals(tok.string)) exist=1;
                }
                printc("x_"+tok.string+"0");
                if (exist==0){
                    System.err.println("no such variable ~"+tok.string+" on line "+tok.lineNumber);
                    System.exit(1);
                }
            } 
          
        }
        if(is(TK.ID)){
            if(k>=0){
                for(int i=0;i<blocks.get(level-k).size();i++){
                    if(blocks.get(level-k).get(i).equals(tok.string)) exist=1;
                }
                printc("x_"+tok.string+Integer.toString(level-k));
                if (exist==0){
                    System.err.println("no such variable ~"+k+tok.string+" on line "+tok.lineNumber);
                    System.exit(1);
                }
            }
            else if(k==-2){
                int m=level;
                for(m=level;m>=0;m--){
                    for(int i=0;i<blocks.get(m).size();i++){
                        if(blocks.get(m).get(i).equals(tok.string)) exist=1;
                    }
                    if(exist==1) break;
                }
                printc("x_"+tok.string+Integer.toString(m));
                if(exist==0){
                    System.err.println(tok.string+" is an undeclared variable on line "+tok.lineNumber);
                    System.exit(1);
                }
            }
        }
        mustbe(TK.ID);
    }
    
    

    // is current token what we want?
    private boolean is(TK tk) {
        return tk == tok.kind;
    }

    // ensure current token is tk and skip over it.
    private void mustbe(TK tk) {
	    if( tok.kind != tk ) {
	        System.err.println( "mustbe: want " + tk + ", got " +
				    tok);
	    parse_error( "missing token (mustbe)" );
	    }
	    scan();
    }

    private void parse_error(String msg) {
	    System.err.println( "can't parse: line "
			    + tok.lineNumber + " " + msg );
	    System.exit(1);
    }
}
