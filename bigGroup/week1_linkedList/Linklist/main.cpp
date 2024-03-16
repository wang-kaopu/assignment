#include <iostream>
#include "head.h"
using namespace std;

int n;
int m;

int main() {

	cout << "��ѡ��Ҫ�������������ͣ�\n";
	cout << "1. ��������\n";
	cout << "2. ˫������\n";
	cin >> m;
	if (m == 1) {
		LNode* head = (LNode*)malloc(sizeof(LNode));
		LNode* move = head;//����ͷָ��
	
		cout << "������Ҫ�����ĵ�������ĳ��ȣ�\n";
		cin >> n;

		for (int i = 1;i <= n;i++) {
			LNode* fresh = (LNode*)malloc(sizeof(LNode));
			fresh->data = i;
			fresh->next = NULL;
			move->next = fresh;//ѭ�������½ڵ�
			move = move->next;
		}
		cout << "�������������\n";
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
					cout << "�ǵģ�����ɻ�.\n";
				}
				else {
					cout << "�������ɻ�.\n";
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
		cout << "������Ҫ������˫������ĳ��ȣ�\n";
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
		cout << "˫�����������\n";
		move = head->next;

		while (1) {
			int c = 0;
			cout << "������Ҫ���еĲ�����\n";
			cout << "1. �����鿴����\n";
			cout << "2. ��ת����\n";
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
				cout << "��������\n";
			}
			;
		}
	}

	return 0;
}