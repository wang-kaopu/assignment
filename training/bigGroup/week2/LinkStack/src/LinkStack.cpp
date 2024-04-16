#include <iostream>
#include "LinkStack.h"
using namespace std;
int main() {
	LinkStack* ls = (LinkStack*)malloc(sizeof(LinkStack));
	while (1) {
		if (initLStack(&ls)) {
			cout << "��ջ��ʼ���ɹ�" << endl;
			break;
		}
		else {
			cout << "��ջ��ʼ��ʧ�ܣ���������" << endl;
		}
	}
	int choice = 0;
	while(1)
	{
		menu();
		cin >> choice;
		char ch;
		while ((ch = getchar()) != '\n');//��ջ�����
		switch (choice) {
		case 8: {
			if (initLStack(&ls)) {
				cout << "��ʼ����ջ�ɹ�" << endl;
			}
			else {
				cout << "��ʼ����ջʧ�ܣ�������" << endl;
			}
			break;
		}
		case 1: {//�ж�ջ�Ƿ�Ϊ��
			if (NULL==ls) {
				cout << "���ȳ�ʼ��һ����ջ" << endl;
				continue;
			}
			if (isEmptyLStack(ls)) {
				cout << "�죬��ջȷʵ�ǿյ�" << endl;
			}
			else {
				cout << "no����ջ���ǿյ�" << endl;
			}
			break;
		}case 2: {
			if (NULL==ls) {
				cout << "���ȳ�ʼ��һ����ջ" << endl;
				continue;
			}
			ElemType e;
			if (isEmptyLStack(ls)) {
				cout << "Ī��˼����ջ����empty��" << endl;
				break;
			}
			if (getTopLStack(ls, &e)) {
				cout << "�ɹ���ȡջ��Ԫ�أ��������ǣ�" << e << endl;
			}else{
				cout << "������ȡʧ��" << endl;
			}
			break;
		}case 3: {
			if (NULL == ls) {
				cout << "���ȳ�ʼ��һ����ջ" << endl;
				continue;
			}
			//���ջ
			int sure = 0;
			cout << "���ȷ��Ҫ���ջ��" << endl;
			cout << "1. �ǵ�" << endl;
			cout << "0. ���������" << endl;
			cin >> sure;
			char c;
			while ((c = getchar()) != '\n');
			if (sure) {
				if (clearLStack(ls)) {
					cout << "��ճɹ�" << endl;
				}
				else {
					cout << "���ʧ��" << endl;
				}
			}
			else {
				cout << "�õģ�������ѡ�����" << endl;
			}
			break;
		}case 4: {
			if (NULL == ls) {
				cout << "Ŀǰû���ѳ�ʼ������ջ" << endl;
				continue;
			}
			int sure = 0;
			cout << "���ȷ��Ҫ����ջ��" << endl;
			cout << "1. �ǵ�" << endl;
			cout << "0. ���������" << endl;
			cin >> sure;
			char c;
			while ((c = getchar()) != '\n');
			if (sure) {
				//����ջ
				if (destroyLStack(ls)) {
					ls = NULL;
					cout << "����ջ�ɹ�" << endl;
				}
				else {
					cout << "����ջʧ��" << endl;
				}
			}
			else {
				cout << "�õģ�������ѡ�����" << endl;
			}
			break;
		}case 5: {
			if (NULL == ls) {
				cout << "���ȳ�ʼ��һ����ջ" << endl;
				continue;
			}
			//���ջ����
			int length;
			if (LStackLength(ls, &length)) {
				cout << "���ɹ���ջ����Ϊ��" << length << endl;
			}
			else {
				cout << "�������ʧ��" << endl;
			}
			break;
		}case 6: {
			if (NULL == ls) {
				cout << "���ȳ�ʼ��һ����ջ" << endl;
				continue;
			}
			cout << "��������Ҫ��ջ�Ĵ�����" << endl;
			int n = 0;
			cin >> n;
			while ((ch = getchar()) != '\n');
			ElemType data = 0;
			for (int i = 0;i < n;i++) {
				if (i == 0) {
					cout << "�������½ڵ�����ݣ�ÿ������һ�����ݣ�" << endl;
				}
				cout << "�������" << i + 1 << "�����ݣ�";
				cin >> data;
				while ((ch = getchar()) != '\n');
				if (pushLStack(ls, data)) {
					cout << "��ջ�ɹ�" << endl;
				}
				else {
					cout << "������ջʧ��" << endl;
				}
			}
			break;
		}case 7: {
			if (NULL == ls) {
				cout << "���ȳ�ʼ��һ����ջ" << endl;
				continue;
			}
			//��ջ
			if (isEmptyLStack(ls)) {
				cout << "Ī��˼����ջ����empty��" << endl;
				break;
			}
			ElemType data = 0;
			if (popLStack(ls, &data)) {
				cout << "�ڵ��ջ�ɹ�������Ϊ��" << data << endl;
			}
			else {
				cout << "��ջʧ��" << endl;
			}
			break;
		}default: {
			cout << "�����������������" << endl;
			break;
		}
		}
	}
	return 0;
}