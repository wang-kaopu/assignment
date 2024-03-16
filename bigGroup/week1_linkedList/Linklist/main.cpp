#include <iostream>
#include "head.h"
using namespace std;

int n;
int m;

int main() {

	cout << "请选择要创建的链表类型：\n";
	cout << "1. 单向链表\n";
	cout << "2. 双向链表\n";
	cin >> m;
	if (m == 1) {
		LNode* head = (LNode*)malloc(sizeof(LNode));
		LNode* move = head;//创建头指针
	
		cout << "请输入要创建的单向链表的长度：\n";
		cin >> n;

		for (int i = 1;i <= n;i++) {
			LNode* fresh = (LNode*)malloc(sizeof(LNode));
			fresh->data = i;
			fresh->next = NULL;
			move->next = fresh;//循环创建新节点
			move = move->next;
		}
		cout << "单向链表创建完毕\n";
		move = head->next;

		while (1) {
			int c = menu();
			switch (c) {
			case 1: {
				checkAll(head);
				break;
			}
			case 2: {
				exchange(head);
				break;
			}
			case 3: {
				if (checkCircle(head)) {
					cout << "是的，链表成环.\n";
				}
				else {
					cout << "链表并不成环.\n";
				}
				break;
			}
			case 4: {
				reverse(head);
				break;
			}
			case 5: {
				LNode* mid = NULL;
				if (NULL != (mid = findMid(head))) {
					cout << mid->data << '\n';
				}
				break;
			}
			default: {
				break;
			}
			}
		}
	}
	else if (m == 2) {
		cout << "请输入要创建的双向链表的长度：\n";
		cin >> n;
		LNodeD* head = (LNodeD*)malloc(sizeof(LNodeD));
		head->last = head->next = NULL;
		head->data = 0;
		LNodeD* move = head;

		for (int i = 1;i <= n;i++) {
			LNodeD* fresh = (LNodeD*)malloc(sizeof(LNodeD));
			fresh->data = i;
			fresh->next = NULL;
			fresh->last = move;
			move->next = fresh;
			move = move->next;
		}
		cout << "双向链表创建完毕\n";
		move = head->next;

		while (1) {
			int c = 0;
			cout << "请输入要进行的操作：\n";
			cout << "1. 遍历查看链表\n";
			cout << "2. 反转链表\n";
			cin >> c;
			if (c == 1) {
				for (move = head->next;move != NULL;move = move->next) {
					cout << move->data<<' ';
				}
				cout << '\n';
			}
			else if (c == 2) {
				for (move = head->next;move != NULL;move = move->last) {
					LNodeD* tmp = move->last;
					move->last = move->next;
					move->next = tmp;
					if (NULL == move->last) {
						move->last = head;
						head->next = move;
						break;
					}
				}
				for (move = head->next;move != NULL;move = move->next) {
					if (head == move->next) {
						move->next = NULL;
					}
				}
				
			}
			else {
				cout << "输入有误\n";
			}
			;
		}
	}

	return 0;
}