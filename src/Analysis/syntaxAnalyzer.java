package Analysis;

import java.io.FileReader;
import java.io.IOException;

public class syntaxAnalyzer {
	static char[] strbuf = new char[1500];//定义一个数组,用以存放从文件读取来的字符串
//	public String token = "";
	
	//从文件中把字符串读取到一个字符数组中
	public void readFile(String url) throws IOException{
		int ch,i=0;		
		FileReader fr = new FileReader(url);
		while( (ch=fr.read())!=-1){
			strbuf[i++]=(char)ch;
		}
		fr.close();
	}	
	public void scan(){
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
