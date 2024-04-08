using namespace std;
#include "head.h"
#include <iostream>
#include <assert.h>
#include <stdlib.h>
#include <ctype.h>
int main() {
	cout << "���ڴ�������������" << endl;
	BinarySortTreePtr tree = (BinarySortTreePtr)malloc(sizeof(BinarySortTree));
	assert(tree);
	BST_init(tree);
	cout << "�������" << endl;
	while (1) {
		int choice = menu();
		switch (choice)
		{
		case 1: {//����
			ElemType n = 0;
			cout << "������Ҫ���ҵĽڵ��ֵ��" << endl;
			cin >> n;
			char ch;
			while ((ch = getchar()) != '\n');
			NodePtr s = NULL;
			if (s = BST_search(tree, n))
				cout << "���ҳɹ���ֵ��Ӧ�Ľڵ����:" << s->value << '(' << s << ')' << endl;
			else
				cout << "������ɣ��������ڸ�ֵ��Ӧ�Ľڵ�" << endl;
			break;
		}
		case 2: {//����
			ElemType n = 0;
			cout << "������Ҫ����Ľڵ��ֵ��" << endl;
			cin >> n;
			char ch;
			while ((ch = getchar()) != '\n');
			if (BST_insert(tree, n))
				cout << "����ɹ�" << endl;
			break;
		}
		case 3: {//ɾ��
			ElemType n = 0;
			cout << "������Ҫɾ���Ľڵ��ֵ��" << endl;
			cin >> n;
			char ch;
			while ((ch = getchar()) != '\n');
			NodePtr s = NULL;
			if ((s = BST_search(tree, n)) != NULL) {
				cout << n << "��Ӧ�Ľڵ�(" << s << ")���ڣ�" << "����ɾ������" << endl;
				if (BST_delete(tree, s))
					cout << "ɾ�����" << endl;
				else
					cout << "ɾ��ʧ��" << endl;
			}
			else
				cout << "�������ڸ�ֵ��Ӧ�Ľڵ㣬��������" << endl;
			break;
		}
		case 4: {//�������
			BST_preorderI(tree, tree->root);
			cout << endl;
			break;
		}
		case 5: {//�������
			BST_inorderI(tree, tree->root);
			cout << endl;
			break;
			break;
		}
		case 6: {//�������
			BST_postorderI(tree, tree->root);
			cout << endl;
			break;
		}
		case 7: {//����������ǵݹ飩
			BST_preorderR(tree);
			cout << endl;
			break;
		}
		case 8: {//����������ǵݹ飩
			BST_inorderR(tree);
			cout << endl;
			break;
		}
		case 9: {//����������ǵݹ飩
			BST_postorderR(tree);
			cout << endl;
			break;
		}
		case 10: {//�������
			BST_levelOrder(tree);
			cout << endl;
			break;
		}
		default:
			break;
		}
	}
	return 0;
}