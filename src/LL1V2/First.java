package LL1V2;

/*创建非终结符的first集和follow集的类
 * 其中非终结符 是字符串name,其对应的集合用字符串来表示
 * 
 * */

public class First {
	private String first;
	private String name;
	public First(String name,First f){
		//用一个非终结符的first/follow集合来创建新的一个。
		this.name=name;
		this.first=f.first;		
	}
	public First(String name){//创建新的First对象
		this.name=name;
		this.first="";
	}
	public First(String name,String first){//分别赋值的方式创建对象
		this.name=name;
		this.first=first;
	}
	public String getName(){//获取name元素，即非终结符
		return this.name;
	}
	public String getCollect(){//获取对象的first元素
		return this.first;
	}
	public void addf(char str){//在集合中添加新元素
		int length=first.length();
		if(length==0)
			first=first+str;
		else{
			for(int i=0;i<length;i++){
				if(str==first.charAt(i))
					return;
			}
				first=first+str;
		}
	}
	public First exceptZore(){//求当前对象除了'ε'之外的first内容
		int length=this.first.length();
		String emp="";
		if(length>0){
			for(int i=0;i<length;i++){
				if(this.first.charAt(i)!='ε')
					emp=emp+this.first.charAt(i);
				//else emp=emp;
			}				
		}
		return new First(this.name,emp);
	}
	public boolean conteinZero(){//判断当前对象的first元素中是否含有'ε'
		int length=this.first.length();
		if(length>0){
			for(int i=0;i<length;i++){
				if(this.first.charAt(i)=='ε')
					return true;
				}
		}
		return false;
	}
	public boolean conteinChar(char c){//判断当前对象first中是否含有字符c
		int length=this.first.length();
		if(length>0){
			for(int i=0;i<length;i++){
				if(this.first.charAt(i)==c)
					return true;
				}
		}
		return false;
	}
	public void addf(First fc){//将某一对象的 集合中的元素加入到当前元素的集合中
		if(fc.first.length()>0){
			for(int i=0;i<fc.first.length();i++)
				this.addf(fc.first.charAt(i));
		}
	}
	public void addf(String str){//将获得的新集合加入党当前对象的集合中
		if(str.length()>0){
			for(int i=0;i<str.length();i++)
				this.addf(str.charAt(i));
		}
	}
	
	 public String print(){//打印当前对象 的元素，以集合方式打印集合first中的元素
		 String str = new String();
		 str+=name+":";
		 System.out.print(name+":");
		 for(int i=0;i<first.length();i++){
			 str+=first.charAt(i);
			 System.out.print(first.charAt(i));
			 	if(i!=first.length()-1){
			 		str+=",";
			 		System.out.print(",");
			 	}
		 }
		 str+="\n";
		 System.out.println("");
		 return str;
	 }
	 
	 
	public void reMove(char c) {//从当前元素中去掉某一字符c
		int length=first.length();		
		if(length>0){
			String emp="";
			for(int i=0;i<length;i++){
				if(c!=first.charAt(i))
					emp=emp+first.charAt(i);
					
			}
				first=emp;
		}
		
	}
}

