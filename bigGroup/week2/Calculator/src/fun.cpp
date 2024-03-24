#include "head.h"
using namespace std;

char optSet[10] = "+-*/()=";//��������

//�������Ƿ�Ϊ�������������
bool InSet(char ch) {
	for (int i = 0;i < 7;i++) {
		if (ch == optSet[i])
			return true;
	}
	return false;
}

//ȥ���ո񣬼������Ƿ�Ϸ�
bool DelSpace(string &str) {
	string ret;
	for (int i = 0;i < str.length();i++) {
		if (InSet(str[i]) || isdigit(str[i]))
			ret += str[i];
		else if (str[i] == ' ')
			;
		else {
			cout << "���ʽ���зǷ��ַ�������������" << endl;
			return false;
		}
	} 
	str = ret;
	return true;
}

//����׺���ʽת��Ϊ��׺���ʽ
bool change(string& str, string& exp) {
	Stack opStack = Stack();
	for (int i = 0;i < str.length();i++) {
		if (isdigit(str[i])) {
			exp += str[i];
			while (isdigit(str[i + 1])) {
				exp += str[i + 1];
				i++;
			}
			exp += '#';//������������һ��#�ţ���ʾ��������������һλ
		}
		else if ('(' == str[i]) {//������ֱ�ӽ�ջ
			opPushIntoStack(str[i],opStack);
		}
		else if (')' == str[i]) {//�����ţ���ջ����������ظ���ջ��ֱ��ջ���������ţ�Ȼ���ٶ���������
			while ('(' != opStack.getTopLStack()->op) {
				Node* newPop = opStack.Pop();
				exp += newPop->op;
				free(newPop);
			}
			opStack.Pop();
		}
		else if (InSet(str[i])) {//���������������������ջ
			if (opStack.isEmpty() || level(str[i])>level(opStack.getTopLStack()->op)
				||'('==opStack.getTopLStack()->op) {//���ȼ��ߣ�ֱ����ջ
				opPushIntoStack(str[i], opStack);
			}
			else {//���ȼ��ͣ���ջ��������ظ���ջ��ֱ�����ȼ������µ�ջ�������
				CompareWithTop(str[i], opStack, exp);
				opPushIntoStack(str[i], opStack);
			}
		}
	}
	while (!opStack.isEmpty()) {//ԭ���ʽ������ɣ���ջ�������ȫ����ջ
		exp += opStack.Pop()->op;
	}
	return true;
}

//���ز��������ȼ�
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

//��������ջ
bool opPushIntoStack(char op,Stack& stack) {
	Node* newNode = (Node*)malloc(sizeof(Node));
	newNode->op = op;
	stack.Push(newNode);
	return true;
}

//��ǰ��������ջ���������Ƚ����ȼ�
int CompareWithTop(char op, Stack& stack,string& exp) {
	exp += stack.Pop()->op;
	if (level(op) > level(stack.getTopLStack()->op))
		return 1;
	return CompareWithTop(stack.getTopLStack()->op, stack, exp);
}

//��������ջ
bool NumPushIntoStack(double num, Stack& stack) {
	Node* newNode = (Node*)malloc(sizeof(Node));
	newNode->num = num;
	stack.Push(newNode);
	return true;
}

//�����׺���ʽ
double compute(string exp) {
	Stack stack = Stack();
	double tmp = 0;
	for (int i = 0;i < exp.length();i++) {
		if (isdigit(exp[i])) {//������ֱ����ջ
			tmp += exp[i] - '0';
			while ('#'!=exp[i+1]) {
				tmp *= 10;
				tmp += exp[i+1] - '0';
				i++;
			}
			NumPushIntoStack(tmp,stack);
			tmp = 0;
		}
		else if (InSet(exp[i])) {//����������ջ�������������������������ջ
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