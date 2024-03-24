using namespace std;
#include "head.h"

int main() {
	while (true) {
		//输入中缀表达式
		string str;
		cout << "请输入正确的表达式：" << endl;
		getline(cin, str);
		//去除空格，检查表达式是否合法
		if (!DelSpace(str)) {
			continue;
		}
		//转换为后缀表达式
		string exp;
		exp.clear();
		change(str, exp);
		cout << exp << endl;//输出后缀表达式便于测试
		cout<< compute(exp)<<endl;
	}
	return 0;
}