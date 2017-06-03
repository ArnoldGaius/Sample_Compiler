package Analysis;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class grammaticalAnalysis {
	public int bracket_left=0;
	public int bracket_right=0;
	public int num_err = 0;
	public String errors="";
	public HashMap<String,String> var = new HashMap<String, String>(); 
	public ArrayList<tokenNode> tokenMap = new ArrayList<tokenNode>();
	public ArrayList<String> unusedIdentifier_ArrayList = new ArrayList<String>();
	public ArrayList<String> isIdentifier_ArrayList = new ArrayList<String>();
//	public ArrayList<tokenNode> testMap = new ArrayList<tokenNode>(
//			Arrays.asList("program","example",";","const",
//					"k","=","2",",","l","=","3",
//					";","var","a",",","b",",",
//					"c",":","integer",";","x",":",
//					"char",";","begin","if","(","(","a",
//					"+","c",")","*","3",">","b",")","and",
//					"(","b",">","3",")","then","c",":=",
//					"3",";","x",":=","2","+","(","3",
//					"*","a",")","-","b","*","c","*",
//					"8",";","if","(","2","+","3",">","a",
//					")","and","(","b",">","3",")","and",
//					"(","a",">","c",")","then","c",":=",
//					"3",";","for","x",":=","1","+","2",
//					"to","3","do","b",":=","100",";","while",
//					"a",">","b","do","c",":=","5",";","for",
//					"x",":=","1","+","a","to","3","do","b",
//					":=","15",";","while","b",">","b","do",
//					"c",":=","5",";","repeat","a",":=","10",
//					";","until","a",">","b",";","end","."));
	public ArrayList<String> keyWord = new ArrayList<String>(
			Arrays.asList(
			"program","var","integer","bool","real","char","const", 	       
    		"begin","if","then","else","while","do","for",
    		"to","end","read","write","true","flase","not","and","or")); //词法分析获得的token表字符/数字/关键字/常量...
	private String token = null;
	private int index_tokenMap = 0;
	
	public String getNextToken (){
		if(index_tokenMap<tokenMap.size()){
			return tokenMap.get(index_tokenMap++).getWord();
		}
		return null;
	}	
	/**
	 * 判断是否是变量
	 * @param str
	 * @return
	 */
	private boolean isVariable(String str) {
		if(str==null)
			return false;
		if(str.equals("integer")||str.equals("char")||str.equals("real")||str.equals("bool"))
			return true;
		return false;
	}
	/**
	 * 判断是否是字符
	 * @param ch
	 * @return
	 */
	private boolean isLetter(char ch) {
		if('a'<=ch&ch<='z'||'A'<=ch&ch<='Z')
			return true;
		else    return false;
	}
	/**
	 * 判断是否是常数
	 * @param str
	 * @return true/false
	 */
	public boolean isConstantWords(String str){
         Pattern pattern = Pattern.compile("[0-9]*");
         Matcher isNum = pattern.matcher(str);
         if(!isNum.matches() ){
             return false;
         }
         return true;
	}
	/**
	 * 判断是否是标识符
	 * @param str
	 * @return true/false
	 */
	public boolean isIdentifier(String str){
		if(keyWord.contains(str))
			return false;
		if(str!=null){
			if(isLetter(str.charAt(0))||str.charAt(0)=='_'){
				return true;
			}
			return false;
		}
		return false;
	}
	/**
	 * 判断是否是运算符
	 * @param str
	 * @return true/false
	 */
	//运算符
	public boolean isOperator(String str){
		if(str==null)
			return false;
		if(str.equals("+")||str.equals("-")||str.equals("*")||str.equals("/"))
			return true;
		return false;
	}
	/**
	 * 判断是否是关系符
	 * @param str
	 * @return true/false
	 */
	//关系符
	public boolean isRelationSig(String str){
		if(str==null)
			return false;
		if(str.equals(">")||str.equals("<")||str.equals(">=")||str.equals("<=")||str.equals("<>")||str.equals("="))
			return true;
		return false;
	}
	/**
	 * const常量检测
	 */
	public void const_st(){
		if (!isIdentifier(token)) {
			error("const 后缺少常量定义");
		}else {
			unusedIdentifier_ArrayList.add(token); //定义的常量存入未使用链
			isIdentifier_ArrayList.add(token); //定义的常量存入定义字符表
			token = getNextToken();
			if (!"=".equals(token)){
				error("缺少=");
			}else {
				token = getNextToken();
				if (!isConstantWords(token)) {
					error("缺少数值");
				} else {
					token = getNextToken();
					if (!";".equals(token)){
						if(!",".equals(token)){
							error("缺少;");
						}
						else{
							token=getNextToken();
							const_st();
						}	
					} else {
						token = getNextToken();
						if ("var".equals(token)||"begin".equals(token)) {
							return;
							// var_st(iterator);
						} else {
							const_st();
							return;
						}
					}
				}
			}
		}
	}
	/**
	 * 警告记录
	 * @param Warningstr
	 */
	public void warning(String Warningstr){
		errors+=Warningstr;
	}
	/**
	 * 错误记录 
	 * <p>
	 * 在控制台输出
	 * @param errStr
	 */
	public void error(String errStr){
		num_err++;
		String err=null;
		if(index_tokenMap-1>-1&&index_tokenMap<tokenMap.size())
			err=tokenMap.get(index_tokenMap-1).getRow()+"\t"+errStr+"\n";
		else if(index_tokenMap-1<0){
			err=tokenMap.get(0).getRow()+"\t"+errStr+"\n";
		}else{
			err=tokenMap.get(index_tokenMap-2).getRow()+"\t"+errStr+"\n";
		}
		if(!errors.contains(err))
			errors+=err;
		System.out.println(err);
	}
	/**
	 * 变量定义检测
	 */
	void varst() /*处理变量说明，读入var后进入该过程*/
	{
		ArrayList<String> identfiers = new ArrayList<String>();
	    while(true) {
		    if (!isIdentifier(token))
		    		error("缺少标识符");
		    else{
		    	unusedIdentifier_ArrayList.add(token); //定义的变量存入未使用链
				isIdentifier_ArrayList.add(token); //定义的变量存入定义字符表
				identfiers.add(token);
		    	token= getNextToken(); /*取下一个token字*/
		    }
		    if (",".equals(token)) 
		    	token= getNextToken(); /*取下一个token字*/
		    else if (":".equals(token)) 
		    	break;
		    else{
		    	error("错误：变量名后只能出现:和,");
		    	break;
		    }
	    }
	    token = getNextToken(); /*取出下一个token字，放入type中*/
	    if (!isVariable(token)) 
	    	error("变量声明错误");
	    else{
	    	if(!identfiers.isEmpty()){
	    		for (String iden: identfiers){
	    			if(var.get(iden)==null)
	    				var.put(iden, token);
	    		}
	    		identfiers.clear();
	    	}
	    	token= getNextToken(); /*取出下一个token字*/
	    }
	    if (!";".equals(token))  //如果不是; 分号
	    	error("缺少 ; ");/*处理完一行变量说明*/
	    else
	    	token= getNextToken(); /*取出下一个token字*/
	    if (isIdentifier(token))
	    	varst(); 
	    	/* 如果读入是标识符，继续处理变量说明*/
	    else if ("begin".equals(token))
	    	return ; /* 结束本函数的处理,转入处理可执行程序部分*/
	    else 
	    	error("语法错误：缺少BEGIN，或变量说明出错");
	}
	/**
	 * 布尔表达式
	 * @return true/false
	 */
	//布尔表达式
	boolean bexp()  /* 逻辑表达式的处理*/
	{
		  bt();   /* 处理布尔量*/
		  if ("or".equals(token))  {   /*首先处理or*/
			  token = getNextToken();                
			 bexp();  /* 递归调用，or 后面应跟随另一个布尔表达式*/
			 return true;
		  }
		  else  
			  return false;  /*布尔表达式分析结束*/
	}
	/**
	 * 布尔项
	 * @return true/false
	 */
	//布尔项
	boolean bt()  /*处理and*/
	{
		token = getNextToken();
		bf();      /*处理布尔量*/
		if (bracket_left != bracket_right){ /*括号中的表达式处理完毕，后跟右括号*/
			error("不匹配的括号");
		}
		bracket_left = 0;
		bracket_right = 0;
		if ("and".equals(token)) {
 		   bt(); /*递归调用，and后面应跟随另一个布尔量*/
 		   return true;
      }
      else  
    	  return false;  /*布尔表达式分析结束*/
	}
	/**
	 * 运算符检测
	 */
	//运算符
	public void bds_arithmeticWords(){
		if(!isOperator(token)){
			if(isRelationSig(token)){
				return;
			}
			if(";".equals(token)){
				token = getNextToken();
				return;
			}else if("to".equals(token) ||"do".equals(token)||"until".equals(token)||"and".equals(token)||"or".equals(token)||"then".equals(token)){
				return;
			}else if(")".equals(token)){
				if(this.bracket_left<this.bracket_right){
					error("多余的 )");
				}else{
					bracket_right++;
					token = getNextToken();
					bds_arithmeticWords();
				}
			}else if("if".equals(token)|| "for".equals(token)||"while".equals(token)||"repeat".equals(token)){
				error("缺少 ;");
			}else{
				error("缺少运算符");
			}
		}else{
			token = getNextToken();
			if((!isIdentifier(token)) && (!isConstantWords(token)) &&"until".equals(token) && "(".equals(token)){
				error("缺少标识符");
			}else{
				aexpr();
			}
		}
	}
	/**
	 * 算数表达式
	 */
	//算术表达式
	void aexpr(){
		if(";".equals(token) ){
			token = getNextToken();
			return;
		}
		else if("to".equals(token)|| "do".equals(token)){
			
		}else if("(".equals(token)){
			token = getNextToken();
			bracket_left++;
			aexpr(); /*括号中仍然是表达式*/
		}
		else if((!isIdentifier(token)) && (!isConstantWords(token))){
			error("缺少标识符");
			bds_arithmeticWords();
		}else{
			token = getNextToken();
			bds_arithmeticWords();
		}
	}
	 /**
	  * 布尔量
	  */
	//布尔量
	void bf() /*处理not*/
	{
	       if ("not".equals(token)) {
	    	   token = getNextToken();
	    	   bf(); /*递归调用，not后面应跟随另一个布尔量*/
	    	   }
	       else{
	    	   if ("(".equals(token)) {/*处理括号（*/
	    		   bracket_left++;
	    		   bexp(); /*括号中仍然是表达式*/
	    	   }else {    /* 处理关系运算 */
	    		   aexpr(); /* 调用算术表达式运算*/
	    		   if (isRelationSig(token)){ /*关系运算符*/
						token=getNextToken();
						aexpr(); /*关系运算符之后是算术表达式*/
	    		   }
	    	   }
	       }
	}

	/**
	 * if语句检测
	 */
	void ifs(){
	    bexp();  /*处理布尔表达式*/
	    if(!"then".equals(token))
	    	error("缺少then");
	    else{
		    token = getNextToken();
		    ST_SORT();/*调用函数处理then后的可执行语句*/
	    }
//	    if(token!= "else") 
//	    	error("缺少else");
//	    else{
//	    	token = getNextToken();
//	    	ST_SORT();/*处理else后的可执行语句*/
//	    }
	}
	/**
	 * while语句检测
	 */
	void whiles(){
		bexp();  /*处理布尔表达式*/
	    if(!"do".equals(token))
	    	error("缺少do");
	    else{
		    token = getNextToken();
		    ST_SORT();/*调用函数处理do后的可执行语句*/
	    }
	}
	/**
	 * repeat语句检测
	 */
	void repeats(){
		token=getNextToken();
		ST_SORT();
		if(!"until".equals(token))
			error("缺少 until");
		bexp();
	}
	/**
	 * for语句检测
	 */
	void fors(){
		token=getNextToken();
		if((!isIdentifier(token)) && (!isConstantWords(token))){
			error("缺少标识符");
		}
		token=getNextToken();
		if(!":=".equals(token)){
			error("缺少 := 赋值符号");
		}
		token=getNextToken();
		aexpr();
		if("to".equals(token)||"downto".equals(token)){
			token=getNextToken();
			aexpr();
			if(!"do".equals(token))
				error("缺少 do");
			else
				token=getNextToken();
			ST_SORT();
		}
	}
	/**
	 * 执行语句检测
	 */
	void assign(){
		if((!isIdentifier(token)) && (!isConstantWords(token))){
			error("缺少标识符");
		}
		if(!isIdentifier_ArrayList.contains(token)){
			error("标识符未定义");
		}
		if(unusedIdentifier_ArrayList.contains(token))
			unusedIdentifier_ArrayList.remove(token);
		token=getNextToken();
		if(!":=".equals(token)){
			error("缺少 := 赋值符号");
		}
		token=getNextToken();
		aexpr();
	}
	/**
	 * 分类处理
	 * <p>
	 * 分为if、while、for、repeat、执行语句
	 */
	void ST_SORT() /* 可执行语句分类处理模块主程序*/
	{
		if ("if".equals(token))  
			ifs(); /*调用if语句分析模块*/
		else if ("while".equals(token)) 
			whiles();/*调用while语句分析模块*/
		else if ("repeat".equals(token)) 
			repeats();/*调用repeat语句分析模块*/
		else if ("for".equals(token)) 
			fors();/*调用for语句分析模块*/
		else assign();/*其余情况表示是赋值语句，直接调用赋值语句的分析*/
	}
	/**
	 * 运行程序
	 */
	public void run(){
		while(!"program".equals(token)&&index_tokenMap<tokenMap.size()){
			token = getNextToken();
		}
		if("program".equals(token)){
			token=getNextToken();
			isIdentifier(token);
		}
		token = getNextToken();
		if(";".equals(token)){
			token=getNextToken();
		}
		if ("const".equals(token))  {  /*表明下面是常量说明语句*/
			token = getNextToken();
	        const_st();/*调用分析常量说明的函数*/
	    }
		if ("var".equals(token))  {  /*表明下面是说明语句*/
			token = getNextToken();
	        varst();/*调用分析变量说明的函数*/
	    }
		if ("begin".equals(token)) {
			token = getNextToken();
			while(token!=null&&!"end".equals(token)){
				ST_SORT();  /*处理各种可执行语句*/
			}
		}
		if("end".equals(token)){
			token=getNextToken();
		    if (!".".equals(token)) {/*处理程序结束*/
		    	error("缺少 .");
		    }
		}else{
			error("缺少结束符号");
		}
		for(String unused_token:unusedIdentifier_ArrayList)
			warning("未使用的变量\t\""+unused_token+"\"\n");
	    System.out.println("语法分析完毕");
	    System.out.println("error(s)\t("+num_err+")");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		grammaticalAnalysis ga = new grammaticalAnalysis();
//		ga.tokenMap=ga.testMap;
		ga.run();
	}	

}
