package YYtest;

public class SiYuanShi {
	int NO;
	String arg1;
	String arg2;
	String arg3;
	String go;
	SiYuanShi(int NO,String arg1,String arg2,String arg3,String go){
		this.NO=NO;
		this.arg1 = arg1;
		this.arg2= arg2;
		this.arg3 = arg3;
		this.go = go;
	}
	
	public String getSiYuanShi(){
		return String.valueOf(this.NO)+"("+arg1+","+arg2+","+arg3+","+go+")";
	}
	
	public String getResult(){
		return this.go;
	}
	
	
	public void setResult(String re){
		this.go = re;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
