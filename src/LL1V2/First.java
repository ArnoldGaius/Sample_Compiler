package LL1V2;

/*�������ս����first����follow������
 * ���з��ս�� ���ַ���name,���Ӧ�ļ������ַ�������ʾ
 * 
 * */

public class First {
	private String first;
	private String name;
	public First(String name,First f){
		//��һ�����ս����first/follow�����������µ�һ����
		this.name=name;
		this.first=f.first;		
	}
	public First(String name){//�����µ�First����
		this.name=name;
		this.first="";
	}
	public First(String name,String first){//�ֱ�ֵ�ķ�ʽ��������
		this.name=name;
		this.first=first;
	}
	public String getName(){//��ȡnameԪ�أ������ս��
		return this.name;
	}
	public String getCollect(){//��ȡ�����firstԪ��
		return this.first;
	}
	public void addf(char str){//�ڼ�����������Ԫ��
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
	public First exceptZore(){//��ǰ�������'��'֮���first����
		int length=this.first.length();
		String emp="";
		if(length>0){
			for(int i=0;i<length;i++){
				if(this.first.charAt(i)!='��')
					emp=emp+this.first.charAt(i);
				//else emp=emp;
			}				
		}
		return new First(this.name,emp);
	}
	public boolean conteinZero(){//�жϵ�ǰ�����firstԪ�����Ƿ���'��'
		int length=this.first.length();
		if(length>0){
			for(int i=0;i<length;i++){
				if(this.first.charAt(i)=='��')
					return true;
				}
		}
		return false;
	}
	public boolean conteinChar(char c){//�жϵ�ǰ����first���Ƿ����ַ�c
		int length=this.first.length();
		if(length>0){
			for(int i=0;i<length;i++){
				if(this.first.charAt(i)==c)
					return true;
				}
		}
		return false;
	}
	public void addf(First fc){//��ĳһ����� �����е�Ԫ�ؼ��뵽��ǰԪ�صļ�����
		if(fc.first.length()>0){
			for(int i=0;i<fc.first.length();i++)
				this.addf(fc.first.charAt(i));
		}
	}
	public void addf(String str){//����õ��¼��ϼ��뵳��ǰ����ļ�����
		if(str.length()>0){
			for(int i=0;i<str.length();i++)
				this.addf(str.charAt(i));
		}
	}
	
	 public String print(){//��ӡ��ǰ���� ��Ԫ�أ��Լ��Ϸ�ʽ��ӡ����first�е�Ԫ��
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
	 
	 
	public void reMove(char c) {//�ӵ�ǰԪ����ȥ��ĳһ�ַ�c
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
