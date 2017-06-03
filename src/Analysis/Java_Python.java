package Analysis;
import javax.script.*;  

import org.python.core.PyFunction;  
import org.python.core.PyInteger;  
import org.python.core.PyObject;  
import org.python.util.PythonInterpreter;  
  

import java.io.*;  
import java.util.Properties;

import static java.lang.System.*;  
public class Java_Python  
{  
    public static void main(String args[])  
    {  
        PythonInterpreter interpreter = new PythonInterpreter();  
        interpreter.exec("import sys");
        interpreter.exec("sys.path.append('D:\\Python\\Python27\\Lib')");
        interpreter.exec("sys.path.append('D:\\Python\\Python27\\Lib\\site-packages')");
        interpreter.exec("sys.path.append('C:\\Program Files (x86)\\Graphviz2.38\\bin')");
        interpreter.exec("sys.path.append('D:\\Python\\Python27\\Scripts')");
        interpreter.exec("sys.path.append('D:\\Python\\Python27')");
        
        interpreter.execfile("./src/java_pyGra/java_pyGra.py");  
        PyFunction func = (PyFunction)interpreter.get("draw_transistions",PyFunction.class);  
  
        int a = 2010, b = 2 ;  
        PyObject pyobj = func.__call__(new PyInteger(a), new PyInteger(b));
        System.out.println("anwser = " + pyobj.toString());  
  
  
    }//main  
}  