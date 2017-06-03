package Analysis;
import java.io.*;
import java.util.ArrayList;

import javax.swing.JTextArea;
public class AnalysisCodeToWord {
	static char[] strbuf = new char[1500];//定义一个数组,用以存放从文件读取来的字符串
	public String errors = "";
	public int errors_num = 0;
	public String tokenString = "";
	public ArrayList<tokenNode> tokenMap = new ArrayList<tokenNode>(); //词法分析获得的token表字符/数字/关键字/常量...
	public String sigMap = "";
	ArrayList<String> sigList = new ArrayList<String>();
    int keywordIndex;  //取关键字的类号
    String[] keyWord = {"program","var","integer","bool","real","char","const", 	       
    		"begin","if","then","else","while","do","for",
    		"to","end","read","write","true","flase","not","and","or"
    		};
    public static void main(String[] args) throws IOException {
		AnalysisCodeToWord wa = new AnalysisCodeToWord();
		wa.readFile("C_TEST.txt");
		System.out.println("--------------------token表信息...--------------------"+"\n");
		wa.run();
		System.out.println("\n"+"--------------------完成--------------------");
//		System.out.println(errors);
	}	
    
    /**
     * 读取左边代码面板内代码存入<code>char</code>数组
     * 初始化错误信息、错误计数
     * 初始化token表中的信息
     **/
    
    public void readFromArea(JTextArea codeArea) {
		String str = codeArea.getText();
		strbuf = str.toCharArray();
		errors="";
		errors_num = 0;
		tokenString = "";
	}
    
    /**
     * 从文件中把字符串读取到一个字符数组中
     **/
	public void readFile(String url) throws IOException{
		int ch,i=0;		
		FileReader fr = new FileReader(url);
		while( (ch=fr.read())!=-1){
			strbuf[i++]=(char)ch;
		}
		fr.close();
	}	
	/**
	 * 判断是否是字符
	 **/
	private boolean isLetter(char ch) {
		if('a'<=ch&ch<='z'||'A'<=ch&ch<='Z')
			return true;
		else    return false;
	}	
	/**
	 * 判断是否是数字
	 **/
	private boolean isDigit(char ch) {
		if('0'<= ch&&ch<='9')
			return true;
		else    return false;
	}	
	/**
	 * 运行程序
	 **/
	public void run(){                             //分析整个strbuf里的字符串
		StringBuffer buf = new StringBuffer();  //定义一个缓冲区
		int j=1;
		for(int i=0; i<strbuf.length;i++){      //i既是循环变量,也是文件指针
			//当读头读到space\enter\line的时候,忽略!
			if(strbuf[i]=='\n')
				j++;
			else if(strbuf[i]==' '||strbuf[i]=='\t'||strbuf[i]=='\n'){
//				i++;
				continue;
			}
			else if(i<strbuf.length&&isLetter(strbuf[i])) {
				int k;
				buf.delete(0, buf.length()); //净空缓冲器
				while(i<strbuf.length&&(isLetter(strbuf[i])||isDigit(strbuf[i]))) {
				    buf.append(strbuf[i]);
				    i++;
				}
				//查找buf里面的字符串是否为关键字
				for(k =0; k<keyWord.length; k++){
					if(new String(buf).equals(keyWord[k])){
						keywordIndex = k;
						String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" +keywordIndex;
						System.out.println(str);	
						tokenString+=str + "\n";
						tokenMap.add(new tokenNode(buf.toString(), keywordIndex, j));
						break;
					}
				}
				if(k>=keyWord.length){//非关键字
					String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" +34;
					System.out.println(str);	
					tokenMap.add(new tokenNode(buf.toString(), 34, j));
					tokenString+=str + "\n";
					if(!sigList.contains(buf.toString())){
						sigMap += buf + "\t" + buf.length() + "\t" + 34 + "\t标识符" + "\n";
						sigList.add(buf.toString());
					}
				}
				i--;
			} 
			else if(i<strbuf.length&&isDigit(strbuf[i])) {                                                    
			     buf.delete(0, buf.length());
			     while(i<strbuf.length&&isDigit(strbuf[i])){
			    	      buf.append(strbuf[i++]);
				  }
			     if(i<strbuf.length&&isLetter(strbuf[i])){
			    	 if(strbuf[i]=='e'||strbuf[i]=='E'){
			    		 buf.append(strbuf[i++]);
		    			 if(i<strbuf.length){
		    				 if(strbuf[i]=='-'||strbuf[i]=='+'){
		    					 while(i<strbuf.length&&isDigit(strbuf[i]))
		    			    		 buf.append(strbuf[i++]);
		    				 }else if(isDigit(strbuf[i])){
		    					 while(i<strbuf.length&&isDigit(strbuf[i]))
		    			    		 buf.append(strbuf[i++]);
		    				 }
		    			 }
			    	 }else{
				    	 while(i<strbuf.length&&isLetter(strbuf[i]))
				    		 buf.append(strbuf[i++]);
				    	 errors += "line:" + String.valueOf(j) + "\t无法识别的标识符" + buf.toString() + "\n";
				    	 errors_num++;
				    	 i--;
				    	 continue;
			    	 }
			     }
			     if(i<strbuf.length&&strbuf[i]=='.'){
			    	 buf.append(strbuf[i++]);
			    	 while(i<strbuf.length&&isDigit(strbuf[i]))
			    		 buf.append(strbuf[i++]);
			    	 if(i<strbuf.length&&isLetter(strbuf[i])){
			    		 if(strbuf[i]!='e'&&strbuf[i]!='E'){
			    			 while(i<strbuf.length&&(isLetter(strbuf[i])||isDigit(strbuf[i])))	
			    				 buf.append(strbuf[i++]);
			    			 errors += "line:" + String.valueOf(j) + "\t不正常的数字" + buf.toString() + "\n";
			    			 errors_num++;
			    			 i--;
			    			 continue;
			    		 }else if(i<strbuf.length&&strbuf[i]=='e'||strbuf[i]=='E'){
			    			 buf.append(strbuf[i++]);
			    			 if(i<strbuf.length){
			    				 if(strbuf[i]=='-'||strbuf[i]=='+'){
			    					 buf.append(strbuf[i++]);
			    					 while(i<strbuf.length&&isDigit(strbuf[i]))
			    			    		 buf.append(strbuf[i++]);
			    				 }else if(i<strbuf.length&&isDigit(strbuf[i])){
			    					 while(i<strbuf.length&&isDigit(strbuf[i]))
			    			    		 buf.append(strbuf[i++]);
			    				 }
			    			 }
			    		 }
			    	 }
			     }
			     i--;
		     if(!sigList.contains(buf.toString())){
					sigMap += buf + "\t" + buf.length() + "\t" + 35 + "\t简单变量" + "\n";
					sigList.add(buf.toString());
		     }
			 String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" +35;
			 System.out.println(str);
			 tokenMap.add(new tokenNode(buf.toString(), 35, j));
			 tokenString+=str + "\n";
	  	    }
			else if(i<strbuf.length)
				switch((char)strbuf[i]){
					case',':{
						String str = "line:" + String.valueOf(j) + "\t" + strbuf[i] + "\t\t" + 41;
						System.out.println(str);
						tokenMap.add(new tokenNode(String.valueOf(strbuf[i]), 41, j));
						tokenString+=str + "\n";
						};break;
					case';':{
						String str = "line:" + String.valueOf(j) + "\t" + strbuf[i] + "\t\t" + 40;
						System.out.println(str);
						tokenMap.add(new tokenNode(String.valueOf(strbuf[i]), 40, j));
						tokenString+=str + "\n";
					}break;
					case'.':{
						String str = "line:" + String.valueOf(j) + "\t" + strbuf[i] + "\t\t" + 48;
						System.out.println(str);
						tokenMap.add(new tokenNode(String.valueOf(strbuf[i]), 48, j));
						tokenString+=str + "\n";
					}break;
					case'(':{
						String str = "line:" + String.valueOf(j) + "\t" + strbuf[i] + "\t\t" + 46;
						System.out.println(str);
						tokenMap.add(new tokenNode(String.valueOf(strbuf[i]), 46, j));
						tokenString+=str + "\n";
					}break;
					case')':{
						String str = "line:" + String.valueOf(j) + "\t" + strbuf[i] + "\t\t" + 47;
						System.out.println(str);
						tokenMap.add(new tokenNode(String.valueOf(strbuf[i]), 47, j));
						tokenString+=str + "\n";
					}break;
					case'[':{
						String str = "line:" + String.valueOf(j) + "\t" + strbuf[i] + "\t\t" + 29;
						System.out.println(str);
						tokenMap.add(new tokenNode(String.valueOf(strbuf[i]), 29, j));
						tokenString+=str + "\n";
					}break;
					case']':{
						String str = "line:" + String.valueOf(j) + "\t" + strbuf[i] + "\t\t" + 30;
						System.out.println(str);
						tokenMap.add(new tokenNode(String.valueOf(strbuf[i]), 30, j));
						tokenString+=str + "\n";
					}break;
					case'+':{
						String str = "line:" + String.valueOf(j) + "\t" + strbuf[i] + "\t\t" + 24;
						System.out.println(str);
						tokenMap.add(new tokenNode(String.valueOf(strbuf[i]), 24, j));
						tokenString+=str + "\n";
					}break;
					case'-':{
						String str = "line:" + String.valueOf(j) + "\t" + strbuf[i] + "\t\t" + 25;
						System.out.println(str);
						tokenMap.add(new tokenNode(String.valueOf(strbuf[i]), 25, j));
						tokenString+=str + "\n";
					}break;
					case'*':{
						buf.delete(0, buf.length());
						buf.append(strbuf[i]);
						i++;
						if(i<strbuf.length){
							switch(strbuf[i]){
								case '/':{
									buf.append('/');
									String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" + 44;
									System.out.println(str);
									tokenMap.add(new tokenNode(buf.toString(), 44, j));
									tokenString+=str + "\n";
								};break;
								default:{
									i--;
									String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" + 26;
									System.out.println(str);
									tokenMap.add(new tokenNode(buf.toString(), 26, j));
									tokenString+=str + "\n";									
								};break;
							}
						}
					}break;
					case'/':{
						buf.delete(0, buf.length());
						buf.append(strbuf[i]);
						i++;
						if(i<strbuf.length){
							switch(strbuf[i]){
								case '/':{
									buf.append(strbuf[i]);
									while(i<strbuf.length&&strbuf[i]!='\n')
										i++;
									String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" + 37;
									System.out.println(str);	
									tokenMap.add(new tokenNode(buf.toString(), 37, j));
									tokenString+=str + "\n";
								};break;
								case '*':{
									buf.append(strbuf[i]);
									String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" + 43;
									System.out.println(str);	
									tokenMap.add(new tokenNode(buf.toString(), 43, j));
									tokenString+=str + "\n";
									while(i<strbuf.length&&strbuf[i]!='*')
										i++;
									while(i<strbuf.length&&strbuf[i]!='/')
										i++;
									if(i<strbuf.length&&strbuf[i]=='/'){
										buf.delete(0, buf.length());
										buf.append("*/");
										str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" + 36;
										System.out.println(str);
										tokenMap.add(new tokenNode(buf.toString(), 36, j));
										tokenString+=str + "\n";
									}
								};break;
								default:{
									i--;
									String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" + 27;
									System.out.println(str);
									tokenMap.add(new tokenNode(buf.toString(), 27, j));
									tokenString+=str + "\n";
									};break;
							}
						}
					}break;
					case'=':{
						buf.delete(0, buf.length());
						buf.append(strbuf[i]);
						i++;
						if(i<strbuf.length){
							if(strbuf[i]=='='){
								buf.append(strbuf[i]);
								String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" + 32;
								System.out.println(str);
								tokenMap.add(new tokenNode(buf.toString(), 32, j));
								tokenString+=str + "\n";
							}else{
								String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" + 39;
								System.out.println(str);
								tokenMap.add(new tokenNode(buf.toString(), 39, j));
								tokenString+=str + "\n";
								i--;
							}
						}
						
					}break;	
					case':':{
						buf.delete(0, buf.length());
						buf.append(strbuf[i]);
						i++;
						if(i<strbuf.length&&strbuf[i]=='='){
							buf.append(strbuf[i]);
							String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" + 54;
							System.out.println(str);
							tokenMap.add(new tokenNode(buf.toString(), 54, j));
							tokenString+=str + "\n";
						} else{
							String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" + 45;
							System.out.println(str); 
							tokenMap.add(new tokenNode(buf.toString(), 45, j));
							tokenString+=str + "\n";
							i--;
						}					
					};break;
					case'>':{
						buf.delete(0, buf.length());
						buf.append(strbuf[i]);
						i++;
						if(strbuf[i]=='='){
							buf.append(strbuf[i]);
							String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" + 31;
							System.out.println(str);
							tokenMap.add(new tokenNode(buf.toString(), 31, j));
							tokenString+=str + "\n";
						} 
						else{
							String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" + 29;
							System.out.println(str); 
							tokenMap.add(new tokenNode(buf.toString(), 29, j));
							tokenString+=str + "\n";
							i--;
						}	
					};break;
					
					case'<':{
						buf.delete(0, buf.length());
						buf.append(strbuf[i]);
						i++;
						if(strbuf[i]=='='){
							buf.append(strbuf[i]);
							String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" + 30;
							System.out.println(str); 
							tokenMap.add(new tokenNode(buf.toString(), 30, j));
							tokenString+=str + "\n";
							i++;
						} else if(strbuf[i]=='>'){
							buf.append(strbuf[i]);
							String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" + 33;
							System.out.println(str); 
							tokenMap.add(new tokenNode(buf.toString(), 33, j));
							tokenString+=str + "\n";
							i++;
						} else{
							String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" + 28;
							System.out.println(str); 
							tokenMap.add(new tokenNode(buf.toString(), 28, j));
							tokenString+=str + "\n";
							i++;
						}	
					};break;
					case '~':  
			        case '@':  
			        case '$':  
			        case '%':  
			        case '&':  
			        case '"':
			        case '\'':
			        {
			        	String vs = "";  
			        	while (true) {  
			                vs += strbuf[i];  
			                i++; 
			                if(i>=strbuf.length||isLetter(strbuf[i])||isDigit(strbuf[i])||
			                		strbuf[i] == ' ' || strbuf[i] == '\t'||strbuf[i] == '\r'||strbuf[i] == '\n'){  
			                    break;  
			                }  
			            }
			        	String str = "line:" + String.valueOf(j) + "\t无法识别的字符" + vs;
			        	System.out.println(str);
			        	errors += str + "\n";
			        	errors_num++;
			        	i--;
			        }break;
			        default:{
			        	String str ="line:" + String.valueOf(j) + "\t严重错误：编码不同的字符";
			        	System.out.println(str);
			        	errors += str + "\n";
			        	errors_num++;
			        }
				}//switch结束
			}
	}                                                                                             
}
