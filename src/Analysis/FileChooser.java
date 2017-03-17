package Analysis;
import java.awt.event.*;

import javax.swing.*;

import java.io.*;
import java.util.Properties;
@SuppressWarnings("serial")
public class FileChooser extends JFrame implements ActionListener{
 private JButton open;
 private JFileChooser chooser;
 private String  latestPath ;
 private Profile profile;
 public FileChooser() {
  profile = new Profile();//每次运行程序时创建配置文件Profile对象
  latestPath = (profile.read()?profile.latestPath:"C:/");//读取配置文件里的参数Profile并赋值给latestPath
  open = new JButton("open");
  this.add(open);
  this.setSize(200,150);
  this.setLocationRelativeTo(null);//窗口居中
  this.setVisible(true);
  open.addActionListener(this);
  this.addWindowListener(new WindowAdapter(){
   public void windowClosing(WindowEvent we){
    profile.write(latestPath);//每次退出程序时把最后一次打开的目录写入到配置文件
   }
  });
 } 
 public static void main(String[] args) {
  new FileChooser();
 } 
 public void actionPerformed(ActionEvent arg0) {
  if(!new File(latestPath).exists()){
   latestPath = "C:/";
  }else{
   chooser = new JFileChooser(latestPath);
   int returnVal = chooser.showOpenDialog(this);
      if(returnVal == JFileChooser.APPROVE_OPTION) {
       File file = chooser.getSelectedFile();
       //其他操作...
       latestPath = file.getParent();//每次退出文件选择器后更新目录Properties
      }     
  } 
 }
}
class Profile{
 String latestPath = "C:/";
 File file = new File("set.ini"); 
 public Profile(){}
 boolean create(){
  boolean b = true;  
  if(file!=null){
   File directory = file.getParentFile();//获得文件的父目录
   if(!directory.exists()){//目录不存在时
    b = directory.mkdirs();//创建目录
   }else{//存在目录
    if(!file.exists()){//配置文件不存在时
     try {
      b = file.createNewFile();//创建配置文件
     } catch (IOException e) {
      b = false;
     }
    }
   }
  }  return b;
}
 
 boolean read(){
  Properties pro;//属性集
  FileInputStream is = null;
  boolean b = true;
  if(!file.exists()){//配置文件不存在时
   b = create();//创建一个
   if(b)//创建成功后
   b = write(latestPath);//初始化
   else//创建失败即不存在配置文件时弹出对话框提示错误
    JOptionPane.showConfirmDialog(null, "对不起，不存在配置文件！", "错误", 
     JOptionPane.YES_NO_OPTION, 
     JOptionPane.ERROR_MESSAGE);
  }else{
   try {
    is = new FileInputStream(file);
    pro = new Properties();
    pro.load(is);//读取属性
    latestPath = pro.getProperty("latestPath");//读取配置参数latestPath的值
    is.close();
   }
   catch (IOException ex) {
    ex.printStackTrace(); 
    b =  false;
   }
  }
  return b;
 }
 boolean write(String latestPath){  
  this.latestPath = latestPath;
  Properties pro = null;
  FileOutputStream os = null;
  boolean b = true;
  try {
   os = new FileOutputStream(file);
   pro = new Properties();
   
   pro.setProperty("latestPath",latestPath); 
     
   pro.store(os,null); //将属性写入  
   os.flush();
   os.close();
   
   System.out.println("latestPath=" + latestPath);
 
  }
  catch (IOException ioe) {
   b = false;
   ioe.printStackTrace();
  }
  return b;
 }
}