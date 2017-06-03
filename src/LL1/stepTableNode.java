package LL1;

public class stepTableNode {
	private int NO;
	private String symStack;
	private String inputString;
	private String exp;
	
	public stepTableNode(int NO,String symStack,String inputString,String exp){
		this.NO = NO;
		this.symStack = symStack;
		this.inputString = inputString;
		this.exp = exp;
		
	}
	public int getNO(){
		return this.NO;
	}
	public String getSymStack (){
		return this.symStack;
	}
	public String getInString(){
		return this.inputString;
	}
	public String getExp(){
		return this.exp;
	}
	public String [] getNodeData(){
		String [] data = {String.valueOf(this.NO),this.symStack,this.inputString,this.exp};
		return data;
	}
	public void printStepTableNode(){
		System.out.println(String.valueOf(NO)+"\t"+symStack+"\t"+inputString+"\t"+exp);
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
