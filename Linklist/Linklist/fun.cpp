#include <iostream>
using namespace std;
#include "head.h"
int menu() {
	int c = 0;
	while (!(c <= 4 && c >= 1)) {
		cout << "请输入要进行的操作：\n";
		cout << "1. 遍历查看链表\n";
		cout << "2. 单链表奇偶调换\n";
		cout << "3. 判断链表是否成环\n";
		cout << "4. 反转链表\n";
		cout << "5. 找到链表的中点\n";
		cin >> c;
		if (c <= 5 && c >= 1) {
			return c;
		}
		cout << "输入有误，请重新输入\n";
	}
}
void checkAll(LNode* head) {
	for (LNode* move = head->next;move != NULL;move = move->next) {
		cout << move->data << ' ';
	}
	cout << '\n';
}
int checkCircle(LNode* head) {
	LNode* fast = head->next->next;
	LNode* slow = head->next;
	for (;slow != NULL && NULL != fast;
		slow = slow->next, fast = fast->next->next) {
		if (NULL != fast->next) {
			break;
		}
		if (fast == slow) {
			return 1;
		}
	}
	return 0;
}
LNode* findMid(LNode* head) {
	LNode* fast = head;
	LNode* slow = head;
	if (checkCircle(head)) {
		return NULL;
	}
	for (; NULL != fast && NULL != fast ->next;slow = slow = slow->next,fast = fast->next->next) {
		;
	}
	return slow;
}
void exchange(LNode* head) {
	LNode* fast = head->next;
	LNode* slow = head;
	for (;NULL != slow->next->next;slow = slow->next->next) {
		LNode* tmp1 = slow->next;
		slow->next = slow->next->next;
		LNode* tmp2 = slow->next->next;
		slow->next->next = tmp1;
		slow->next->next->next = tmp2;
	}
}
void reverse(LNode* head) {
	LNode* fast = head->next->next;
	LNode* slow = head->next;
	while(fast != NULL) {
		LNode* tmp = slow;
		slow = fast;
		fast = fast->next;
		slow->next = tmp;
	}
	head->next->next = NULL;
	head->next = slow;
}