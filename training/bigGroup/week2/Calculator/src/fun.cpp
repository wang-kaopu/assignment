#include "head.h"
using namespace std;

char optSet[10] = "+-*/()=";//操作符表

//检查符号是否为四则运算操作符
bool InSet(char ch) {
	for (int i = 0;i < 7;i++) {
		if (ch == optSet[i])
			return true;
	}
	return false;
}

//去除空格，检查符号是否合法
bool DelSpace(string &str) {
	string ret;
	for (int i = 0;i < str.length();i++) {
		if (InSet(str[i]) || isdigit(str[i]))
			ret += str[i];
		else if (str[i] == ' ')
			;
		else {
			cout << "表达式含有非法字符，请重新输入" << endl;
			return false;
		}
	} 
	str = ret;
	return true;
}

//将中缀表达式转换为后缀表达式
bool change(string& str, string& exp) {
	Stack opStack = Stack();
	for (int i = 0;i < str.length();i++) {
		if (isdigit(str[i])) {
			exp += str[i];
			while (isdigit(str[i + 1])) {
				exp += str[i + 1];
				i++;
			}
			exp += '#';//操作数最后接上一个#号，表示这个操作数到最后一位
		}
		else if ('(' == str[i]) {//左括号直接进栈
			opPushIntoStack(str[i],opStack);
		}
		else if (')' == str[i]) {//右括号，把栈顶的运算符重复出栈，直到栈顶是左括号，然后再丢弃左括号
			while ('(' != opStack.getTopLStack()->op) {
				Node* newPop = opStack.Pop();
				exp += newPop->op;
				free(newPop);
			}
			opStack.Pop();
		}
		else if (InSet(str[i])) {//运算符，分情况讨论如何入栈
			if (opStack.isEmpty() || level(str[i])>level(opStack.getTopLStack()->op)
				||'('==opStack.getTopLStack()->op) {//优先级高，直接入栈
				opPushIntoStack(str[i], opStack);
			}
			else {//优先级低，将栈顶运算符重复出栈，直到优先级高于新的栈顶运算符
				CompareWithTop(str[i], opStack, exp);
				opPushIntoStack(str[i], opStack);
			}
		}
	}
	while (!opStack.isEmpty()) {//原表达式分析完成，将栈内运算符全部出栈
		exp += opStack.Pop()->op;
	}
	return true;
}

//返回操作符优先级
int level(char op) {
	switch (op)
	{
		case '+':
		case '-':
			return 1;
			break;
		case '*':
		case '/':
			return 2;
			break;
		case '(':
		case ')':
			return 3;
			break;
		default:
			break;
	}
}

//操作符入栈
bool opPushIntoStack(char op,Stack& stack) {
	Node* newNode = (Node*)malloc(sizeof(Node));
	newNode->op = op;
	stack.Push(newNode);
	return true;
}

//当前操作符与栈顶操作符比较优先级
int CompareWithTop(char op, Stack& stack,string& exp) {
	exp += stack.Pop()->op;
	if (level(op) > level(stack.getTopLStack()->op))
		return 1;
	return CompareWithTop(stack.getTopLStack()->op, stack, exp);
}

//操作数入栈
bool NumPushIntoStack(double num, Stack& stack) {
	Node* newNode = (Node*)malloc(sizeof(Node));
	newNode->num = num;
	stack.Push(newNode);
	return true;
}

//计算后缀表达式
double compute(string exp) {
	Stack stack = Stack();
	double tmp = 0;
	for (int i = 0;i < exp.length();i++) {
		if (isdigit(exp[i])) {//操作数直接入栈
			tmp += exp[i] - '0';
			while ('#'!=exp[i+1]) {
				tmp *= 10;
				tmp += exp[i+1] - '0';
				i++;
			}
			NumPushIntoStack(tmp,stack);
			tmp = 0;
		}
		else if (InSet(exp[i])) {//操作符，将栈顶的两个操作数运算后，重新入栈
			double num1 = stack.Pop()->num;
			double num2 = stack.Pop()->num;
			switch (exp[i])
			{
			case '+': 
				NumPushIntoStack(num1 + num2, stack);
				break;
			case '-': 
				NumPushIntoStack(num2 - num1, stack);
				break;
			case '*':
				NumPushIntoStack(num1 * num2, stack);
				break;
			case '/':
				NumPushIntoStack(num2 / num1, stack);
			default:
				break;
			}
			cout << stack.getTopLStack()->num<<endl;
		}
	}
	return stack.Pop()->num;
}