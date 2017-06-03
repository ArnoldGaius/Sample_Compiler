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
* 状态转换图生成放在`src/Java_pyGra`中。由于 `pygraphviz` 不支持混合编程，只能单独将生成`reg2FA`生成出状态转换表，然后再通过`Java_pyGra.py`转换成图片。

使用说明：
----
运行环境支撑的话，通过`src/UI`中`MainUI.java`运行主程序，`LL1.java`、`reg2FA.java`都可以单独运行。<br>
`LineNumberHeaderView.java`用于在`MainUI`中显示行数，但存在一定问题，如果使用`LineNumberHeaderView`，不可同时使用`WindowBuilder`。

存在的问题：
----
* 语义分析嵌套语句：可以嵌套，但多层嵌套会出问题；
* LL(1)预测暂时没考虑非LL(1)语法的文法；
* 由于`pygraphviz` 不支持混合编程没能一步到位完成画状态图，需要手工在`reg2FA`界面选择生成状态表到本地，在`Java_pyGra.py`中读取状态表画图；
* 图标用了Steam     `;p`


