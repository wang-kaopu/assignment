using namespace std;
#include "head.h"

int main() {
	while (true) {
		//������׺���ʽ
		string str;
		cout << "��������ȷ�ı��ʽ��" << endl;
		getline(cin, str);
		//ȥ���ո񣬼����ʽ�Ƿ�Ϸ�
		if (!DelSpace(str)) {
			continue;
		}
		//ת��Ϊ��׺���ʽ
		string exp;
		exp.clear();
		change(str, exp);
		cout << exp << endl;//�����׺���ʽ���ڲ���
		cout<< compute(exp)<<endl;
	}
	return 0;
}