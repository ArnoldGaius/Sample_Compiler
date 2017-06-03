package YYtest;

import java.util.ArrayList;
import java.util.HashMap;

public class semTest {
	private String SYM ;
	private ArrayList<Word> result ;
	private HashMap<Integer,String> reverse ;
	private int current = 0 ;
	private int row = 1 ;
	private boolean flag = true ;
	private int count=0;
	private int NXQ=1;
	private ArrayList<SiYuanShi> output;
	private int totalVar=0;
	private HashMap<String,String> var; 
	private boolean t=true;
	public String midInfo="";
	public String errorString = "";
	public void setVar(HashMap<String,String> newVar){
		this.var = newVar;
	}
	public HashMap<String,String>  getVar(){
		return this.var;
	}
	
	public semTest(ArrayList<Word> al){
		result = new ArrayList<Word>();
		reverse =  new HashMap<Integer,String>();
		int i;
		for(i=0;i<al.size();i++)
			if(al.get(i).getType()==0) 
				break;
		if(i==al.size())
			result=al;
		else 
			result=null;
		var=new HashMap<String,String>();
		output=new ArrayList<SiYuanShi>();
		output.add(new SiYuanShi(0,"-","-","-","0"));
		reverse.put(18,"true");
		reverse.put(19,"false");
		reverse.put(3,"bool");
		reverse.put(2,"int");
		reverse.put(8,"if");
		reverse.put(9,"then");
		reverse.put(10,"else");
		reverse.put(13,"for");
		reverse.put(14,"to");
		reverse.put(12,"do");
		reverse.put(11,"while");
		reverse.put(34, "identifier"); //标识符
		reverse.put(35, "constants"); //常量
		reverse.put(24,"+");
		reverse.put(25,"-");
		reverse.put(46,"(");
		reverse.put(47,")");
		reverse.put(39,"=");
		reverse.put(29,">");
		reverse.put(28,"<");
		reverse.put(40,";");
		reverse.put(45,":");
		reverse.put(54,":=");
		reverse.put(41,",");
		reverse.put(27, "/");
		reverse.put(26, "*");
		reverse.put(49, "{");
		reverse.put(50, "}");
		reverse.put(21,"and");
		reverse.put(22,"or");
		reverse.put(53,"!");

		if(result!=null)
		ADVANCE();
	}
	
	public void run(){
		if(result!=null){
				while(current<result.size()){
					if(flag){
						P();
						
					}else{
						break;
					}
				}
				if(t){
					genCode("sys", "", "", "");
					for(int i=1;i<output.size();i++){
						
						midInfo+=output.get(i).getSiYuanShi()+"\n";
					}
				}/*结果输出*/
		}
		else 
			System.out.println("请先除去未定义符号");
	}
	
	private int newTemp(){
		count++;
		return count;
	}
	
	private int entry(String i){
		return 0;
	}
	
	private void genCode(String op,String arg1 ,String arg2 ,String result){
		SiYuanShi a=new SiYuanShi(NXQ,op,arg1,arg2,result);
		output.add(a);
		NXQ++;
	}
	
	private void ADVANCE(){
		if(current<result.size()){
			SYM = reverse.get(result.get(current).getType());
			current ++ ;
		}	
	}
	private int merge(int p1,int p2){
		try{
			int i=p2;
			if(p1!=0&&p2!=0){
				while(Integer.parseInt(output.get(i).getResult())!=0)
					i=Integer.parseInt(output.get(i).getResult());
				
				output.get(i).setResult(Integer.toString(p1));
				return p2;
			}
		}catch(Exception e){
			System.out.println("Error1");
		}
		return p2;
	}
	private void backpatch(int p,int t){
		try{
			int i=p;
			int m=0;
			if(p!=0){
				while(Integer.parseInt(output.get(i).getResult())!=0){
					m=Integer.parseInt(output.get(i).getResult());
					output.get(i).setResult(Integer.toString(t));
					i=m;
				}
				output.get(i).setResult(Integer.toString(t));
			}
		}catch(Exception e){
			System.out.println("	Error2	");
		}
	}
	
	private void ERROR(int i){
		t&=false;
		flag = flag & false ;
		System.out.print("错误位于第"+row+"个执行语句");
		errorString +="错误位于第"+row+"个执行语句";
		switch(i){
			case 0 : 
				errorString +="语法错：缺少分号\n";
				break;
			case 1 : 
				errorString +="语法错：else关键字错误。\n";
				break;
			case 2 : 
				errorString +="语法错：then关键字缺失。\n";
				break;
			case 3 : 
				errorString +="语法错：限定非符号之后只能跟随左括号。\n";
				break;
			case 4 :
				errorString +="语法错：do关键字缺失。\n";
				break;
			case 5 : 
				errorString +="语法错：非法定义非变量\n";
				break;
			case 6 : 
				errorString +="语法错：非正常语句开头。\n";
				break;
			case 7 : 
				errorString +="语法错：赋值符号缺失。\n";
				break;
			case 8 :
				errorString +="语法错：格式错误。\n";
				break;
			case 9 : 
				errorString +="语法错：丢失右括号。\n";
				break;
			case 10 :
				errorString +="语法错：表达式格式错误。\n";
				break;
			case 11 : 
				errorString +="语法错：丢失右大括号。\n";
				break;
			case 12 : 
				errorString +="语义错：变量"+result.get(current-1).getValue()+"未定义。\n";
				break;
			case 13 : 
				errorString +="语义错：变量"+result.get(current-1).getValue()+"重定义。\n";
				break;
			case 14 : 
				errorString +="语义错：变量"+result.get(current-1).getValue()+"类型错误。\n";
				break;
			case 15 : 
				errorString +="语法错：缺少比较符号\n";
				break;
			case 16 : 
				errorString +="语法错：缺少to\n";		
				break;	
			default:
				errorString +="未知错误!\n";
				break;
		}
		if(!";".equals(SYM)){
			while( current<result.size() &&  !";".equals(reverse.get(result.get(current).getType())))
				current ++ ;
			current ++ ;
			/*if("}".equals(reverse.get(result.get(current).getType())))
				current++;*/
		}
		//current=result.size()-1;
		row ++ ;
		ADVANCE();	
	}/*报错处理，若出错则寻找下个句子，以分后做标记*/
	
	private int P(){
		int trueNxq=0;
		int falseNxq=0;
		int nextlist=0;
		if(flag){
		if("if".equals(SYM)){
			ADVANCE();
			Word r=B();
			trueNxq=r.getType();
			falseNxq=Integer.parseInt(r.getValue());
			if(flag){
			if("then".equals(SYM)){
				ADVANCE();
				backpatch(trueNxq,NXQ);
				falseNxq=trueNxq+1;
				int pnext=0;
				if("{".equals(SYM)){
					ADVANCE();
					pnext=P();
					if("}".equals(SYM)) {
						ADVANCE();
						}
					else ERROR(11);
				}
				else 
					pnext = Q();
				if(flag){
					if(";".equals(SYM)){
						if(pnext!=0)
							nextlist=merge(pnext,falseNxq);
						else 
							nextlist=falseNxq;
						ADVANCE();
						row ++ ;
					}/*无else的*/
					else if(flag){
								if("else".equals(SYM)){
									int N=NXQ;
									genCode("j","-","-","0");
									nextlist=merge(pnext,N);
									ADVANCE();
									backpatch(falseNxq,NXQ);
									if("{".equals(SYM))
									{
										ADVANCE();
										int a=P();
										if(a!=0)
											nextlist=merge(N,a);
										else 
											nextlist=N;
										if("}".equals(SYM)) {
											ADVANCE();
											}
										else ERROR(11);
									}else
										Q();
									if(flag){
										if(";".equals(SYM)){
											ADVANCE();
											row ++ ;
										}else
											ERROR(0);
									}									
								}else
									ERROR(0);
							}/*有else的*/
					}
			}else
				ERROR(2);
			}
		}/*分支语句*/
		else if("while".equals(SYM)){
				int back=NXQ;
				ADVANCE();
				Word r=B();
				trueNxq=r.getType();
				falseNxq=Integer.parseInt(r.getValue());
				if(flag){
					if("do".equals(SYM)){
						ADVANCE();
						backpatch(trueNxq,NXQ);
						int pnext=0;
						if("{".equals(SYM))
						{
							ADVANCE();
							backpatch(P(),back);
							if("}".equals(SYM)) {
								ADVANCE();
								}
							else ERROR(11);
						}
						else 
							pnext = Q();
						if(flag){
							if(";".equals(SYM)){
								ADVANCE();
								nextlist = merge(pnext,falseNxq);
								genCode("j","-","-",Integer.toString(back));
								row ++ ;
							}else
								ERROR(0);/*丢失分号*/
						}
					}else
						ERROR(4);
				}
			}/*循环语句*/
		else if("for".equals(SYM)){
			ADVANCE();
			backpatch(trueNxq,NXQ);
			falseNxq=trueNxq+1;
			String arg1 = result.get(current-1).getValue();
			Q();	
			if(flag){
				if("to".equals(SYM)){
					int back=NXQ;
					ADVANCE();
					trueNxq = NXQ;
					genCode("j<",arg1,result.get(current-1).getValue(),"0");
					genCode("j","-","-","0");
					ADVANCE();
					if(flag){
						if("do".equals(SYM)){
							ADVANCE();
							backpatch(trueNxq,NXQ);
							if("{".equals(SYM)){
								ADVANCE();
								backpatch(P(),back);
								if("}".equals(SYM)){
									ADVANCE();
								}
								else ERROR(11);
							}
							else {
								Q();
								String n="G"+newTemp();
								genCode("+",arg1,"1",n);
								genCode(":=",n,"-",arg1);
							}
							if(flag){
								if(";".equals(SYM)){
									ADVANCE();
//									nextlist=falseNxq;
									nextlist = merge(NXQ+1,trueNxq+1);									
									genCode("j","-","-",Integer.toString(back));
									row ++ ;
								}else
									ERROR(0);/*丢失分号*/
							}
						}else
							ERROR(4);
					}
				}else ERROR(16);
			}
		}//for语句
		else if("repeat".equals(SYM)){
			int back=NXQ;
			ADVANCE();
			backpatch(trueNxq,NXQ);
			falseNxq=trueNxq+1;
			String arg1 = result.get(current-1).getValue();
			Q();	
			if(flag){
				if(";".equals(SYM)){
					ADVANCE();
					nextlist=falseNxq;
					genCode("j","-","-",Integer.toString(back));
					row ++ ;
				}else
					ERROR(0);/*丢失分号*/
				if("until".equals(SYM)){
					Word r=B();
					trueNxq=r.getType();
					falseNxq=Integer.parseInt(r.getValue());
				}
			}
		}
			else{
				if("identifier".equals(SYM)){
					Q();
					if(flag){
						if(";".equals(SYM)){
							ADVANCE();
							row ++ ;
						}else
							ERROR(0);
					}
				}
				else if("bool".equals(SYM)||"int".equals(SYM)){
						String t=SYM;
						ADVANCE();
						if("identifier".equals(SYM)){
							if(var.get(result.get(current-1).getValue())==null){
								totalVar++;
								var.put(result.get(current-1).getValue(),t);								
								ADVANCE();
								if(";".equals(SYM)){
									ADVANCE();
									row ++ ;
								}else 
									ERROR(0);/*缺少分号*/
							}else 
								ERROR(13);/*变量未定义*/
						}else 
							ERROR(5);/*格式错误：非法定义非变量*/
					}else	
						ERROR(6);/*非正常语句开头*/								
			}
		}/*若已经出错则不做这部分判断*/
		return nextlist;
}
	
	private Word and(int t,int f){
		int m=NXQ;
		Word r=B();
		int tl=r.getType();
		int fl=Integer.parseInt(r.getValue());
		backpatch(t,m);
		t=tl;
		f=merge(f,fl);
		return new Word(t,Integer.toString(f));
	}
	
	private Word or(int t,int f){
		int m=NXQ;
		Word r=B();
		int tl=r.getType();
		int fl=Integer.parseInt(r.getValue());
		backpatch(f,m);
		t=merge(t,tl);
		f=fl;
		return new Word(t,Integer.toString(f));
	}
	
	private Word B(){
		int trueNxq=0;
		int falseNxq=0;
		if("(".equals(SYM)){
			ADVANCE();
		}
		if("identifier".equals(SYM)){
			if(var.get(result.get(current-1).getValue())!=null){
				String arg1=result.get(current-1).getValue();
				ADVANCE();
				if("bool".equals(var.get(arg1))){
					trueNxq=NXQ;
					genCode("j=",arg1,"true","0");
					falseNxq=NXQ;
					genCode("j","-","-","0");
					if("and".equals(SYM)){
						ADVANCE();
						Word r=and(trueNxq,falseNxq);
						trueNxq=r.getType();
						falseNxq=Integer.parseInt(r.getValue());
					}/*B->B and B*/
					else if("or".equals(SYM)){
						ADVANCE();
						Word r=or(trueNxq,falseNxq);
						trueNxq=r.getType();
						falseNxq=Integer.parseInt(r.getValue());
					}/*B->B or B*/
				}else if(">".equals(SYM)||"<".equals(SYM)){
					if(!"integer".equals(var.get(arg1)))
						ERROR(14);
					String o=SYM;
					ADVANCE();
					if("identifier".equals(SYM)||"constants".equals(SYM)){
						if("identifier".equals(SYM)){
							String arg2=result.get(current-1).getValue();
							if(var.get(arg2)==null){
								ERROR(12);
							}
							else if(!"integer".equals(var.get(arg2)))
								ERROR(14);
						}
						trueNxq=NXQ;
						genCode("j"+o,arg1,result.get(current-1).getValue(),"0");
						falseNxq=NXQ;
						genCode("j","-","-","0");
						ADVANCE();
						/*B->iRi*/
						if("and".equals(SYM)){
							ADVANCE();
							Word r=and(trueNxq,falseNxq);
							trueNxq=r.getType();
							falseNxq=Integer.parseInt(r.getValue());
						}/*B->B and B*/
						else if("or".equals(SYM)){
							ADVANCE();
							Word r=or(trueNxq,falseNxq);
							trueNxq=r.getType();
							falseNxq=Integer.parseInt(r.getValue());
						}/*B->B or B*/
					}else{
						ERROR(8);
					}
				}else if("*".equals(SYM)||"/".equals(SYM)||"+".equals(SYM)||"-".equals(SYM)){
					arg1 = T(arg1);
					if(">".equals(SYM)||"<".equals(SYM)){
						String o=SYM;
						ADVANCE();
						if("identifier".equals(SYM)||"constants".equals(SYM)){
							if("identifier".equals(SYM)){
								String arg2=result.get(current-1).getValue();
								if(var.get(arg2)==null){
									ERROR(12);
								}
								else if(!"integer".equals(var.get(arg2)))
									ERROR(14);
							}
							trueNxq=NXQ;
							genCode("j"+o,arg1,result.get(current-1).getValue(),"0");
							falseNxq=NXQ;
							genCode("j","-","-","0");
							ADVANCE();
							/*B->iRi*/
							if("and".equals(SYM)){
								ADVANCE();
								Word r=and(trueNxq,falseNxq);
								trueNxq=r.getType();
								falseNxq=Integer.parseInt(r.getValue());
							}/*B->B and B*/
							else if("or".equals(SYM)){
								ADVANCE();
								Word r=or(trueNxq,falseNxq);
								trueNxq=r.getType();
								falseNxq=Integer.parseInt(r.getValue());
							}/*B->B or B*/
						}else
							ERROR(8);
					}else 
						ERROR(15);
				}else 
					ERROR(14);
			}else {
				ERROR(12);}
			return new Word(trueNxq,Integer.toString(falseNxq));
		}
		else if("constants".equals(SYM)){
			String arg1=result.get(current-1).getValue();
			ADVANCE();
			if(">".equals(SYM)||"<".equals(SYM))
			{
				String o=SYM;
				ADVANCE();
				if("identifier".equals(SYM)||"constants".equals(SYM)){
					if("identifier".equals(SYM))
						if(var.get(result.get(current-1).getValue())==null){ERROR(12);}
					trueNxq=NXQ;
					genCode("j"+o,arg1,result.get(current-1).getValue(),"0");
					falseNxq=NXQ;
					genCode("j","-","-","0");
					ADVANCE();
					if("or".equals(SYM)){
						ADVANCE();
						Word r=and(trueNxq,falseNxq);
						trueNxq=r.getType();
						falseNxq=Integer.parseInt(r.getValue());
					}/*B->B and B*/
					else if("and".equals(SYM)){
						ADVANCE();
						Word r=or(trueNxq,falseNxq);
						trueNxq=r.getType();
						falseNxq=Integer.parseInt(r.getValue());
					}/*B->B or B*/
					return new Word(trueNxq,Integer.toString(falseNxq));
				}
				else ERROR(8);
			}
		}
		else if("!".equals(SYM)){
			ADVANCE();/*非之后必须用括号将求非部分括起来*/
			if("(".equals(SYM)){
				ADVANCE();
				Word r=B();
				trueNxq=Integer.parseInt(r.getValue());
				falseNxq=r.getType();
				if(")".equals(SYM)){
					ADVANCE();
					if("and".equals(SYM)){
						ADVANCE();
						Word r2=and(trueNxq,falseNxq);
						trueNxq=r2.getType();
						falseNxq=Integer.parseInt(r2.getValue());
					}/*B->B and B*/
					else if("or".equals(SYM)){
						ADVANCE();
						Word r2=or(trueNxq,falseNxq);
						trueNxq=r2.getType();
						falseNxq=Integer.parseInt(r2.getValue());
					}/*B->B or B*/
					return new Word(trueNxq,Integer.toString(falseNxq));
				}
				else ERROR(9);
			}else ERROR(3);/*限定非符号之后只能跟随左括号*/
		}
		else if("-".equals(SYM)){
			ADVANCE();
			if("identifier".equals(SYM)){
				SYM="-";
				current--;
				String arg1 = T("-");
				if(">".equals(SYM)||"<".equals(SYM)){
					String o=SYM;
					ADVANCE();
					if("identifier".equals(SYM)||"constants".equals(SYM)){
						if("identifier".equals(SYM)){
							String arg2=result.get(current-1).getValue();
							if(var.get(arg2)==null){
								ERROR(12);
							}
							else if(!"integer".equals(var.get(arg2)))
								ERROR(14);
						}
						trueNxq=NXQ;
						genCode("j"+o,arg1,result.get(current-1).getValue(),"0");
						falseNxq=NXQ;
						genCode("j","-","-","0");
						ADVANCE();
						/*B->iRi*/
						if("and".equals(SYM)){
							ADVANCE();
							Word r=and(trueNxq,falseNxq);
							trueNxq=r.getType();
							falseNxq=Integer.parseInt(r.getValue());
						}/*B->B and B*/
						else if("or".equals(SYM)){
							ADVANCE();
							Word r=or(trueNxq,falseNxq);
							trueNxq=r.getType();
							falseNxq=Integer.parseInt(r.getValue());
						}/*B->B or B*/
					}else
						ERROR(8);
				}else 
					ERROR(15);
			}
		}
		//else if("true".equals(SYM)||"false".equals(SYM)) ADVANCE();
		else ERROR(6);
		return new Word(0,"0");
	}
	
	/**
	 * 赋值语句判断
	 * @return
	 */
	private int Q(){ //赋值语句模块
		if(flag){
		if("identifier".equals(SYM)){
			if(var.get(result.get(current-1).getValue())!=null){
				if(!"integer".equals(var.get(result.get(current-1).getValue())))
					ERROR(14);
				String v=result.get(current-1).getValue();
				ADVANCE();
				if(":=".equals(SYM)){
					ADVANCE();
					genCode(":=",E(),"-",v);
					return NXQ;
				}else	
					ERROR(7);
			}else{
				ERROR(12);}
		}else ERROR(8);
		}
		return 0;
	}
	
	private String E(){
		String value="";
		if("identifier".equals(SYM)){
			if(var.get(result.get(current-1).getValue())!=null){
				if(!"integer".equals(var.get(result.get(current-1).getValue())))
					ERROR(14);
				value=result.get(current-1).getValue();
				ADVANCE();
				value=T(value);
			}else 
				ERROR(12);
		}
		else if("constants".equals(SYM))
		{
			value=result.get(current-1).getValue();
			ADVANCE();
			value=T(value);
		}
		else{
			if("(".equals(SYM)){
				ADVANCE();
				E();
				if(")".equals(SYM)){
					ADVANCE();
				}else 
					ERROR(9);
			}else 
				ERROR(10);
		}		
		return value;
	}
	
	private String T(String value){//生成临时变量T
		String n="G"+newTemp();
		if("+".equals(SYM)||"*".equals(SYM)||"-".equals(SYM)||"/".equals(SYM)){
			String o=SYM;
			ADVANCE();
			String v2=E();
			genCode(o,value,v2,n);
		}else if(!">".equals(SYM)||!"<".equals(SYM)){
			n=value;
			count--;
		}
		return n;
	}	
}
