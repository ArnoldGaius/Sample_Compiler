#coding:utf-8

import pygraphviz as pyg

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
    

# def get_transitions_list(string):
#     transitions_list = []
    
    

def draw_transistions(transitions_list,png_name = None):
    A = pyg.AGraph()
    print 
    
    