#include <iostream>
using namespace std;
#include "head.h"
int menu() {
	int c = 0;
	while (!(c <= 4 && c >= 1)) {
		cout << "������Ҫ���еĲ�����\n";
		cout << "1. �����鿴����\n";
		cout << "2. ��������ż����\n";
		cout << "3. �ж������Ƿ�ɻ�\n";
		cout << "4. ��ת����\n";
		cout << "5. �ҵ�������е�\n";
		cin >> c;
		if (c <= 5 && c >= 1) {
			return c;
		}
		cout << "������������������\n";
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