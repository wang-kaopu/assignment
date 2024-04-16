#pragma once
#include <iostream>
#include <string>
using namespace std;

/*
	��ջ�ڵ�
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
	��ջ
*/
class Stack {
public:
	/*
		���캯��
	*/
	Stack() {
		this->top = NULL;
	}
	/*
		��ջ����
	*/
	Node* Pop() {
		Node* toBePop = top;
		top = top->next;
		return toBePop;
	}
	/*
		��ջ����
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
		�пպ���
	*/
	bool isEmpty() {
		if (NULL == this->top)
			return true;
		else
			return false;
	}
	/*
		�鿴ջ��Ԫ��
	*/
	Node* getTopLStack() {
		return this->top;
	}
	/*
		ջ��
	*/
	Node* top;
	/*
		ջ����
	*/
	int count;
};


int level(char op);//�������ȼ�
bool DelSpace(string &str);//ȥ���ո񣬼������Ƿ�Ϸ�
bool change(string& str, string& exp);//����׺���ʽת��Ϊ��׺���ʽ
bool opPushIntoStack(char op, Stack& stack);//��������ջ
int CompareWithTop(char op, Stack& stack, string& exp);//��ǰ��������ջ���������Ƚ����ȼ�
double compute(string exp);//�����׺���ʽ
bool NumPushIntoStack(double num, Stack& stack);//��������ջ