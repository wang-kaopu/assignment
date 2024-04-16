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
					cout << "请选择数据类型：" << endl;
					cout << "c: char" << endl;
					cout << "i: int" << endl;
					cout << "d: double" << endl;
					cin >> type;
					while ((ch = getchar()) != '\n');
					if (type == 'c' || type == 'i' || type == 'd') {
						break;
					}
					cout << "感觉数据类型的输入有误啊，请重新输入吧" << endl;
				}
				q = malloc(sizeof(LQueue));
				InitLQueue((LQueue*)q);
				cout << "初始化队列完成" << endl;
				break;
			}case 2: {
				if (NULL == q) {
					cout << "队列尚未初始化" << endl;
					break;
				}
				else {
					DestoryLQueue((LQueue*)q);
					cout << "队列已销毁" << endl;
				}
				break;
			}case 3: {
				if (NULL == q) {
					cout << "队列尚未初始化" << endl;
					break;
				}
				if (IsEmptyLQueue((LQueue*)q)) {
					//检查队列是否为空
					cout << "蒽，队列为空" << endl;
				}
				else {
					cout << "no，队列并不为空" << endl;
				}
				break;
			}case 4: {
				if (NULL == q) {
					cout << "队列尚未初始化" << endl;
					break;
				}
				cout << "队列长度是：" << LengthLQueue((LQueue*)q) << endl;//确定队列长度
				break;
			}case 5: {
				if (NULL == q) {
					cout << "队列尚未初始化" << endl;
					break;
				}
				int m = 0;
				cout << "请输入需要入队的次数：";
				cin >> m;
				while ((ch = getchar()) != '\n');
				cout << "请输入需要入队的数据，每行一个数据" << endl;
				for (int i = 0;i < m;i++) {
					switch (type) {
					case 'c': {
						char c;
						cout << "请输入第" << i + 1 << "个字符：" << endl;
						cin >> c;
						while ((ch = getchar()) != '\n');
						if (EnLQueue((LQueue*)q, &c)) {
							cout << "入队成功" << endl;
						}
						else {
							cout << "入队失败" << endl;
						}
						break;
					}case 'i': {
						cout << "请输入第" << i + 1 << "个整数：" << endl;
						int i;
						cin >> i;
						while ((ch = getchar()) != '\n');
						if (EnLQueue((LQueue*)q, &i)) {
							cout << "入队成功" << endl;
						}
						else {
							cout << "入队失败" << endl;
						}
						break;
					}case 'd': {
						cout << "请输入第" << i + 1 << "个浮点数：" << endl;
						double d;
						cin >> d;
						while ((ch = getchar()) != '\n');
						if (EnLQueue((LQueue*)q, &d)) {
							cout << "入队成功" << endl;
						}
						else {
							cout << "入队失败" << endl;
						}
						break;
					}
					}
				}
				break;
			}case 6: {
				if (NULL == q) {
					cout << "队列尚未初始化" << endl;
					break;
				}
				if (IsEmptyLQueue((LQueue*)q)) {
					cout << "队列为空，没什么好出队的" << endl;
					break;
				}
				DeLQueue((LQueue*)q);//出队操作
				break;
			}case 7: {
				if (NULL == q) {
					cout << "队列尚未初始化" << endl;
					break;
				}
				ClearLQueue((LQueue*)q);//清空队列
				cout << "清空队列完成" << endl;
				break;
			}case 8: {
				if (NULL == q) {
					cout << "队列尚未初始化" << endl;
					break;
				}
				TraverseLQueue((LQueue*)q,&LPrint);//遍历函数操作
				break;
			}case 9: {
				if (NULL == q) {
					cout << "队列尚未初始化" << endl;
					break;
				}
				if (IsEmptyLQueue((LQueue*)q)) {
					cout << "队列为空，没什么好查看的" << endl;
					break;
				}
				Node* e = (Node*)malloc(sizeof(Node));
				GetHeadLQueue((LQueue*)q, &e);//查看队头元素
				cout << "队头元素是：";
				LPrint(e);
				cout << endl;
				break;
			}default: {
				cout << "感觉操作选择的输入有误啊，请重新输入吧" << endl;
				break;
			}
		}
	}
}
void DestoryLQueue(LQueue* Q) {
	return;//销毁队列
}
Status EnLQueue(LQueue* Q, void* data) {
	//入队操作
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
	cout << "请选择操作：" << endl;
	cout << "1. 初始化队列" << endl;
	cout << "2. 销毁队列" << endl;
	cout << "3. 检查队列是否为空" << endl;
	cout << "4. 确定队列长度" << endl;
	cout << "5. 入队操作" << endl;
	cout << "6. 出队操作" << endl;
	cout << "7. 清空队列" << endl;
	cout << "8. 遍历队列：" << endl;
	cout << "9. 查看队头元素" << endl;
}
Status IsEmptyLQueue(const LQueue* Q) {
	//检查队列是否为空
	if (Q->length == 0) {
		return TRUE;
	}
	else {
		return FALSE;
	}
}
Status DeLQueue(LQueue* Q) {
	//出队操作
	assert(Q && Q->front);
	Node* toBeFree = Q->front;
	cout << "获得出队元素：" << *(int*)toBeFree->data << endl;
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
	//遍历函数操作
	if (NULL == Q) {
		return FALSE;
	}
	Node* move = Q->front;
	int i = 0;
	for (;NULL != move;move = move->next,++i) {
		cout << "第" << i + 1 << "个元素是：";
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
	//查看队头元素
	*e = (Node*)Q->front;
	if (NULL != e) {
		return TRUE;
	}
	else {
		return FALSE;
	}
}
int LengthLQueue(LQueue* Q) {
	//确定队列长度
	return Q->length;
}
void ClearLQueue(LQueue* Q) {
	//清空队列
	assert(Q);
	Node* move = Q->front;
	Node* toBeFree = move;
	while(move) {
		move = move->next;
		free(toBeFree);
	}
}
