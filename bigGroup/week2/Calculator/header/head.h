#pragma once
#include <iostream>
#include <string>
using namespace std;

/*
	链栈节点
*/
class Node
{
public:
	Node(double num) {
		this->num = num;
	}
	Node(char op) {
		this->op = op;
	}
	char op;
	double num;
	Node* next;
};

/*
	链栈
*/
class Stack {
public:
	/*
		构造函数
	*/
	Stack() {
		this->top = NULL;
	}
	/*
		出栈函数
	*/
	Node* Pop() {
		Node* toBePop = top;
		top = top->next;
		return toBePop;
	}
	/*
		入栈函数
	*/
	bool Push(Node* newNode) {
		if (NULL != newNode) {
			Node* oldNode = this->top;
			top = newNode;
			newNode->next = oldNode;
			return true;
		}
		else
			return false;
	}
	/*
		判空函数
	*/
	bool isEmpty() {
		if (NULL == this->top)
			return true;
		else
			return false;
	}
	/*
		查看栈顶元素
	*/
	Node* getTopLStack() {
		return this->top;
	}
	/*
		栈顶
	*/
	Node* top;
	/*
		栈长度
	*/
	int count;
};


int level(char op);//返回优先级
bool DelSpace(string &str);//去除空格，检查符号是否合法
bool change(string& str, string& exp);//将中缀表达式转换为后缀表达式
bool opPushIntoStack(char op, Stack& stack);//操作符入栈
int CompareWithTop(char op, Stack& stack, string& exp);//当前操作符与栈顶操作符比较优先级
double compute(string exp);//计算后缀表达式
bool NumPushIntoStack(double num, Stack& stack);//操作数入栈