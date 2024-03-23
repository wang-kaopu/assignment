#define _CRT_SECURE_NO_WARNINGS
using namespace std;

#include "head.h"
#include <iostream>
#include <assert.h>
char type;
char datatype[30];

void InitLQueue(LQueue* Q) {
	Q->front = NULL;
	Q->rear = NULL;
	Q->length = 0;
}
void start(void* q) {
	while (1) {
		menu();
		char ch;
		int choice = 0;
		scanf("%d", &choice);
		while ((ch = getchar() != '\n'));
		switch (choice) {
			case 1: {
				while (1) {
					cout << "��ѡ���������ͣ�" << endl;
					cout << "c: char" << endl;
					cout << "i: int" << endl;
					cout << "d: double" << endl;
					cin >> type;
					while ((ch = getchar()) != '\n');
					if (type == 'c' || type == 'i' || type == 'd') {
						break;
					}
					cout << "�о��������͵��������󰡣������������" << endl;
				}
				q = malloc(sizeof(LQueue));
				InitLQueue((LQueue*)q);
				cout << "��ʼ���������" << endl;
				break;
			}case 2: {
				if (NULL == q) {
					cout << "������δ��ʼ��" << endl;
					break;
				}
				else {
					DestoryLQueue((LQueue*)q);
					cout << "����������" << endl;
				}
				break;
			}case 3: {
				if (NULL == q) {
					cout << "������δ��ʼ��" << endl;
					break;
				}
				if (IsEmptyLQueue((LQueue*)q)) {
					//�������Ƿ�Ϊ��
					cout << "�죬����Ϊ��" << endl;
				}
				else {
					cout << "no�����в���Ϊ��" << endl;
				}
				break;
			}case 4: {
				if (NULL == q) {
					cout << "������δ��ʼ��" << endl;
					break;
				}
				cout << "���г����ǣ�" << LengthLQueue((LQueue*)q) << endl;//ȷ�����г���
				break;
			}case 5: {
				if (NULL == q) {
					cout << "������δ��ʼ��" << endl;
					break;
				}
				int m = 0;
				cout << "��������Ҫ��ӵĴ�����";
				cin >> m;
				while ((ch = getchar()) != '\n');
				cout << "��������Ҫ��ӵ����ݣ�ÿ��һ������" << endl;
				for (int i = 0;i < m;i++) {
					switch (type) {
					case 'c': {
						char c;
						cout << "�������" << i + 1 << "���ַ���" << endl;
						cin >> c;
						while ((ch = getchar()) != '\n');
						if (EnLQueue((LQueue*)q, &c)) {
							cout << "��ӳɹ�" << endl;
						}
						else {
							cout << "���ʧ��" << endl;
						}
						break;
					}case 'i': {
						cout << "�������" << i + 1 << "��������" << endl;
						int i;
						cin >> i;
						while ((ch = getchar()) != '\n');
						if (EnLQueue((LQueue*)q, &i)) {
							cout << "��ӳɹ�" << endl;
						}
						else {
							cout << "���ʧ��" << endl;
						}
						break;
					}case 'd': {
						cout << "�������" << i + 1 << "����������" << endl;
						double d;
						cin >> d;
						while ((ch = getchar()) != '\n');
						if (EnLQueue((LQueue*)q, &d)) {
							cout << "��ӳɹ�" << endl;
						}
						else {
							cout << "���ʧ��" << endl;
						}
						break;
					}
					}
				}
				break;
			}case 6: {
				if (NULL == q) {
					cout << "������δ��ʼ��" << endl;
					break;
				}
				if (IsEmptyLQueue((LQueue*)q)) {
					cout << "����Ϊ�գ�ûʲô�ó��ӵ�" << endl;
					break;
				}
				DeLQueue((LQueue*)q);//���Ӳ���
				break;
			}case 7: {
				if (NULL == q) {
					cout << "������δ��ʼ��" << endl;
					break;
				}
				ClearLQueue((LQueue*)q);//��ն���
				cout << "��ն������" << endl;
				break;
			}case 8: {
				if (NULL == q) {
					cout << "������δ��ʼ��" << endl;
					break;
				}
				TraverseLQueue((LQueue*)q,&LPrint);//������������
				break;
			}case 9: {
				if (NULL == q) {
					cout << "������δ��ʼ��" << endl;
					break;
				}
				if (IsEmptyLQueue((LQueue*)q)) {
					cout << "����Ϊ�գ�ûʲô�ò鿴��" << endl;
					break;
				}
				Node* e = (Node*)malloc(sizeof(Node));
				GetHeadLQueue((LQueue*)q, &e);//�鿴��ͷԪ��
				cout << "��ͷԪ���ǣ�";
				LPrint(e);
				cout << endl;
				break;
			}default: {
				cout << "�о�����ѡ����������󰡣������������" << endl;
				break;
			}
		}
	}
}
void DestoryLQueue(LQueue* Q) {
	return;//���ٶ���
}
Status EnLQueue(LQueue* Q, void* data) {
	//��Ӳ���
	if (NULL == Q) {
		return FALSE;
	}
	Node* fresh = (Node*)malloc(sizeof(Node));
	if (NULL != fresh) {
		fresh->next = NULL;
		if (NULL == Q->front) {
			Q->front = fresh;
		}
		else {
			Q->rear->next = fresh;
		}
		Q->rear = fresh;
		++Q->length;
		switch (type) {
			case 'c': {
				fresh->data = (char*)malloc(sizeof(char));
				*((char*)fresh->data) = *(char*)data;
			}case 'i': {
				fresh->data = (int*)malloc(sizeof(int));
				*((int*)(fresh->data)) = *(int*)data;
			}case 'd': {
				fresh->data = (double*)malloc(sizeof(double));
				*((double*)fresh->data) = *(double*)data;
			}
		}
		return TRUE;
	}
	
	return FALSE;
}
void menu() {
	cout << "��ѡ�������" << endl;
	cout << "1. ��ʼ������" << endl;
	cout << "2. ���ٶ���" << endl;
	cout << "3. �������Ƿ�Ϊ��" << endl;
	cout << "4. ȷ�����г���" << endl;
	cout << "5. ��Ӳ���" << endl;
	cout << "6. ���Ӳ���" << endl;
	cout << "7. ��ն���" << endl;
	cout << "8. �������У�" << endl;
	cout << "9. �鿴��ͷԪ��" << endl;
}
Status IsEmptyLQueue(const LQueue* Q) {
	//�������Ƿ�Ϊ��
	if (Q->length == 0) {
		return TRUE;
	}
	else {
		return FALSE;
	}
}
Status DeLQueue(LQueue* Q) {
	//���Ӳ���
	assert(Q && Q->front);
	Node* toBeFree = Q->front;
	cout << "��ó���Ԫ�أ�" << *(int*)toBeFree->data << endl;
	Q->front = Q->front->next;
	free(toBeFree);
	--Q->length;
	if (toBeFree) {
		return TRUE;
	}
	else {
		return FALSE;
	}
}
Status TraverseLQueue(const LQueue* Q, void (*LPrint)(void* q)) {
	//������������
	if (NULL == Q) {
		return FALSE;
	}
	Node* move = Q->front;
	int i = 0;
	for (;NULL != move;move = move->next,++i) {
		cout << "��" << i + 1 << "��Ԫ���ǣ�";
		LPrint(move);
		cout << endl;
	}
	return TRUE;
}
void LPrint(void* q) {
	assert(q);
	Node* node = (Node*)q;
	switch (type)
	{
		case 'i': {
			cout << *(int*)(node->data);
			break;
		}
		case 'd': {
			cout << *(double*)(node->data);
			break;
		}
		case 'c': {
			cout << *(char*)(node->data);
			break;
		}
		default: {
			break;
		}
	}
}
Status GetHeadLQueue(LQueue* Q, Node** e) {
	//�鿴��ͷԪ��
	*e = (Node*)Q->front;
	if (NULL != e) {
		return TRUE;
	}
	else {
		return FALSE;
	}
}
int LengthLQueue(LQueue* Q) {
	//ȷ�����г���
	return Q->length;
}
void ClearLQueue(LQueue* Q) {
	//��ն���
	assert(Q);
	Node* move = Q->front;
	Node* toBeFree = move;
	while(move) {
		move = move->next;
		free(toBeFree);
	}
}
