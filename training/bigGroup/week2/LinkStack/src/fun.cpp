#include "LinkStack.h"
#include <iostream>
#include <assert.h>
using namespace std;
Status initLStack(LinkStack** s) {
	if (NULL == *s) {
		*s = (LinkStack*)malloc(sizeof(LinkStack));
	}
	(*s)->top = NULL;
	(*s)->count = 0;
	return SUCCESS;
}
void menu() {
	cout << "��ѡ�������" << endl;
	cout << "1. �ж�ջ�Ƿ�Ϊ��" << endl;
	cout << "2. �õ�ջ��Ԫ��" << endl;
	cout << "3. ���ջ" << endl;
	cout << "4. ����ջ" << endl;
	cout << "5. ���ջ����" << endl;
	cout << "6. ��ջ" << endl;
	cout << "7. ��ջ" << endl;
	cout << "8. ��ʼ��ջ" << endl;
}
Status isEmptyLStack(LinkStack* s) {
	if (s->count > 0 ) {
		return ERROR;
	}
	else {
		return SUCCESS;
	}
}
Status pushLStack(LinkStack* s, ElemType data) {
	//��ջ
	StackNode* fresh = (StackNode*)malloc(sizeof(StackNode));
	if (NULL != fresh) {
		fresh->data = data;
		fresh->next = s->top;
		++s->count;
		s->top = fresh;
		return SUCCESS;
	}
	else {
		return ERROR;
	}
}
Status getTopLStack(LinkStack* s, ElemType* e){
	assert(e);
	//�õ�ջ��Ԫ��
	*e = s->top->data;
	if (NULL != e) {
		return SUCCESS;
	}
	else {
		return ERROR;
	}
}
Status LStackLength(LinkStack* s, int* length) {
	//���ջ����
	assert(s && length);
	*length = s->count;
	if (NULL != length && NULL != s) {
		return SUCCESS;
	}
	else {
		return ERROR;
	}
}
Status popLStack(LinkStack* s, ElemType* data) {
	//��ջ
	assert(s && data);
	*data = s->top->data;
	StackNode* toBeFree = s->top;
	s->top = s->top->next;
	free(toBeFree);
	--s->count;
	if (NULL != data && NULL != s) {
		return SUCCESS;
	}
	else {
		return ERROR;
	}
}
Status clearLStack(LinkStack* s) {
	//���ջ
	assert(s);
	if (NULL == s->top) {
		return SUCCESS;
	}
	StackNode* toBeFree = NULL;
	while (NULL != s->top) {
		toBeFree = s->top;
		s->top = s->top->next;
		free(toBeFree);
		--s->count;
	}
	if (NULL == s->top) {
		return SUCCESS;
	}
	else {
		return ERROR;
	}
}
Status destroyLStack(LinkStack* s) {
	assert(s);
	if (clearLStack(s)) {
		free(s);
		s = NULL;
		return SUCCESS;
	}
	return ERROR;
}
