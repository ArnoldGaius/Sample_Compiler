# 编译原理课程设计 

运行环境:
----
- Windows 10
- Java 1.7
- Python 2.7 

主要功能：
----
* 支持UI 
* 词法分析
* token表
* 语法分析
* 语法检测
* 语义分析
* 正则式->NFA->DFA->MFA
* LL(1)预测分析
* 状态转换图生成到Java_pyGra下

功能讲解：
----
* 分析功能（词法分析、语法分析）都放在`src/Analysis`中；
* 界面UI放在`src/UI`中；
* 语义分析与四元式生成放在`src/Semantic Analysis`中；
* LL(1)预测分析放在`src/LL1V2`中；
* 状态转换图生成放在`src/Java_pyGra`中。由于 `pygraphviz` 不支持混合编程，只能单独将生成reg2FA生成出状态转换表，然后再通过`Java_pyGra.py`转换成图片。
