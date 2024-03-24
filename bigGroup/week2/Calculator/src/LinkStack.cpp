#include <iostream>
#include "LinkStack.h"
using namespace std;
int main() {
	LinkStack* ls = (LinkStack*)malloc(sizeof(LinkStack));
	while (1) {
		if (initLStack(&ls)) {
			cout << "链栈初始化成功" << endl;
			break;
		}
		else {
			cout << "链栈初始化失败，正在重试" << endl;
		}
	}
	int choice = 0;
	while(1)
	{
		menu();
		cin >> choice;
		char ch;
		while ((ch = getchar()) != '\n');//清空缓冲区
		switch (choice) {
		case 8: {
			if (initLStack(&ls)) {
				cout << "初始化链栈成功" << endl;
			}
			else {
				cout << "初始化链栈失败，请重试" << endl;
			}
			break;
		}
		case 1: {//判断栈是否为空
			if (NULL==ls) {
				cout << "请先初始化一个链栈" << endl;
				continue;
			}
			if (isEmptyLStack(ls)) {
				cout << "蒽，链栈确实是空的" << endl;
			}
			else {
				cout << "no，链栈不是空的" << endl;
			}
			break;
		}case 2: {
			if (NULL==ls) {
				cout << "请先初始化一个链栈" << endl;
				continue;
			}
			ElemType e;
			if (isEmptyLStack(ls)) {
				cout << "莫意思，链栈还是empty呢" << endl;
				break;
			}
			if (getTopLStack(ls, &e)) {
				cout << "成功获取栈顶元素，其数据是：" << e << endl;
			}else{
				cout << "呃，获取失败" << endl;
			}
			break;
		}case 3: {
			if (NULL == ls) {
				cout << "请先初始化一个链栈" << endl;
				continue;
			}
			//清空栈
			int sure = 0;
			cout << "真的确定要清空栈吗？" << endl;
			cout << "1. 是的" << endl;
			cout << "0. 呃，点错了" << endl;
			cin >> sure;
			char c;
			while ((c = getchar()) != '\n');
			if (sure) {
				if (clearLStack(ls)) {
					cout << "清空成功" << endl;
				}
				else {
					cout << "清空失败" << endl;
				}
			}
			else {
				cout << "好的，请重新选择操作" << endl;
			}
			break;
		}case 4: {
			if (NULL == ls) {
				cout << "目前没有已初始化的链栈" << endl;
				continue;
			}
			int sure = 0;
			cout << "真的确定要销毁栈吗？" << endl;
			cout << "1. 是的" << endl;
			cout << "0. 呃，点错了" << endl;
			cin >> sure;
			char c;
			while ((c = getchar()) != '\n');
			if (sure) {
				//销毁栈
				if (destroyLStack(ls)) {
					ls = NULL;
					cout << "销毁栈成功" << endl;
				}
				else {
					cout << "销毁栈失败" << endl;
				}
			}
			else {
				cout << "好的，请重新选择操作" << endl;
			}
			break;
		}case 5: {
			if (NULL == ls) {
				cout << "请先初始化一个链栈" << endl;
				continue;
			}
			//检测栈长度
			int length;
			if (LStackLength(ls, &length)) {
				cout << "检测成功，栈长度为：" << length << endl;
			}
			else {
				cout << "呃，检测失败" << endl;
			}
			break;
		}case 6: {
			if (NULL == ls) {
				cout << "请先初始化一个链栈" << endl;
				continue;
			}
			cout << "请输入需要入栈的次数：" << endl;
			int n = 0;
			cin >> n;
			while ((ch = getchar()) != '\n');
			ElemType data = 0;
			for (int i = 0;i < n;i++) {
				if (i == 0) {
					cout << "请输入新节点的数据，每行输入一个数据：" << endl;
				}
				cout << "请输入第" << i + 1 << "个数据：";
				cin >> data;
				while ((ch = getchar()) != '\n');
				if (pushLStack(ls, data)) {
					cout << "入栈成功" << endl;
				}
				else {
					cout << "呃，入栈失败" << endl;
				}
			}
			break;
		}case 7: {
			if (NULL == ls) {
				cout << "请先初始化一个链栈" << endl;
				continue;
			}
			//出栈
			if (isEmptyLStack(ls)) {
				cout << "莫意思，链栈还是empty呢" << endl;
				break;
			}
			ElemType data = 0;
			if (popLStack(ls, &data)) {
				cout << "节点出栈成功，数据为：" << data << endl;
			}
			else {
				cout << "出栈失败" << endl;
			}
			break;
		}default: {
			cout << "输入错误，请重新输入" << endl;
			break;
		}
		}
	}
	return 0;
}