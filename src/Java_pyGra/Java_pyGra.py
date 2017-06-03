#coding:utf-8

import pygraphviz as pgv
import re

  
class transistions_node():
    start = ''
    receive = ''
    end = ''
    def __init__(self,start,receive,end):
        self.start = start
        self.receive = receive
        self.end = end
    def get_start(self):
        return self.start
    def get_receive(self):
        return self.receive
    def get_end(self):
        return self.end


def readFile(file_name = None):
    if file_name==None:
        print 'no file'
    file_in = []
    for line in open(file_name,'r'):
        file_in.append(unicode(line,'gbk'))
        print unicode(line,'gbk'),  
    return file_in
  
def get_transitions_list(file_in):
    transitions_list = []
    for i in range(len(file_in)):
        if(i==0 or i==1):
            starts_or_ends = []
            for ch in file_in[i]:
                if(ch>='0' and ch<='9'):
                    starts_or_ends.append(ch)
            transitions_list.append(starts_or_ends)
        elif not i==2:
            result = re.split('\t|\n',file_in[i])
            new_node = transistions_node(result[0],result[1],result[2])
            transitions_list.append(new_node)
    return transitions_list 
  
def draw_transistions(transitions_list,png_name = None):
    A = pgv.AGraph(strict=False, overlap=False, splines='spline')
    A.graph_attr['rankdir']='LR'
    A.graph_attr['epsilon']='0.001'    
    A.layout('dot')
    for i in range(len(transitions_list)): 
        if(i==0):
            A.add_nodes_from(transitions_list[i])
        elif i==1:
            A.add_nodes_from(transitions_list[i],shape = 'doublecircle')
        else:
            new_node = transitions_list[i]
            try:
                result = A.get_edge(new_node.start, new_node.start)
                print result
                A.add_edge(new_node.start, new_node.end, label = new_node.receive)
            except:
                A.add_edge(new_node.start, new_node.end, label = new_node.receive)
                
    A.layout('dot')
    A.draw(png_name+'.png')
    print A

print pgv.__version__
file_name = 'MFA'
file_in = readFile(file_name)
transitions_list = get_transitions_list(file_in)
draw_transistions(transitions_list,file_name)

     
     
     
     