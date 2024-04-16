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
	cout << "请选择操作：" << endl;
	cout << "1. 判断栈是否为空" << endl;
	cout << "2. 得到栈顶元素" << endl;
	cout << "3. 清空栈" << endl;
	cout << "4. 销毁栈" << endl;
	cout << "5. 检测栈长度" << endl;
	cout << "6. 入栈" << endl;
	cout << "7. 出栈" << endl;
	cout << "8. 初始化栈" << endl;
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
	//入栈
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
	//得到栈顶元素
	*e = s->top->data;
	if (NULL != e) {
		return SUCCESS;
	}
	else {
		return ERROR;
	}
}
Status LStackLength(LinkStack* s, int* length) {
	//检测栈长度
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
	//出栈
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
	//清空栈
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
