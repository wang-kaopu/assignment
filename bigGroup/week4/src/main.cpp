using namespace std;
#include "head.h"
#include <iostream>
#include <assert.h>
#include <stdlib.h>
#include <ctype.h>
int main() {
	cout << "正在创建二叉排序树" << endl;
	BinarySortTreePtr tree = (BinarySortTreePtr)malloc(sizeof(BinarySortTree));
	assert(tree);
	BST_init(tree);
	cout << "创建完成" << endl;
	while (1) {
		int choice = menu();
		switch (choice)
		{
		case 1: {//查找
			ElemType n = 0;
			cout << "请输入要查找的节点的值：" << endl;
			cin >> n;
			char ch;
			while ((ch = getchar()) != '\n');
			NodePtr s = NULL;
			if (s = BST_search(tree, n))
				cout << "查找成功，值对应的节点存在:" << s->value << '(' << s << ')' << endl;
			else
				cout << "查找完成，并不存在该值对应的节点" << endl;
			break;
		}
		case 2: {//插入
			ElemType n = 0;
			cout << "请输入要插入的节点的值：" << endl;
			cin >> n;
			char ch;
			while ((ch = getchar()) != '\n');
			if (BST_insert(tree, n))
				cout << "插入成功" << endl;
			break;
		}
		case 3: {//删除
			ElemType n = 0;
			cout << "请输入要删除的节点的值：" << endl;
			cin >> n;
			char ch;
			while ((ch = getchar()) != '\n');
			NodePtr s = NULL;
			if ((s = BST_search(tree, n)) != NULL) {
				cout << n << "对应的节点(" << s << ")存在，" << "正在删除……" << endl;
				if (BST_delete(tree, s))
					cout << "删除完成" << endl;
				else
					cout << "删除失败" << endl;
			}
			else
				cout << "并不存在该值对应的节点，请检查输入" << endl;
			break;
		}
		case 4: {//先序遍历
			BST_preorderI(tree, tree->root);
			cout << endl;
			break;
		}
		case 5: {//中序遍历
			BST_inorderI(tree, tree->root);
			cout << endl;
			break;
			break;
		}
		case 6: {//后序遍历
			BST_postorderI(tree, tree->root);
			cout << endl;
			break;
		}
		case 7: {//先序遍历（非递归）
			BST_preorderR(tree);
			cout << endl;
			break;
		}
		case 8: {//中序遍历（非递归）
			BST_inorderR(tree);
			cout << endl;
			break;
		}
		case 9: {//后序遍历（非递归）
			BST_postorderR(tree);
			cout << endl;
			break;
		}
		case 10: {//层序遍历
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