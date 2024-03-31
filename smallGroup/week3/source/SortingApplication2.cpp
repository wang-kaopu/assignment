using namespace std;
#include <iostream>
#define N 20
int arr[N] = { 24,22,21,20,19,18,17,16,14,13,11,9,8,7,6,5,4,3,1,0 };
void exchange(int& i, int& j) {
	int tmp = i;
	i = j;
	j = tmp;
}
int partition(int* arr, int lo, int hi) {
	int beCompared = arr[lo];
	int i = lo, j = hi + 1;//j+1����Ϊ����ɨ��ѭ��Ҫ��--����j
	//i��j���������ֱ���������߿�ʼɨ��
	while (1) {
		while (arr[++i] < beCompared) {
			if (i >= hi)	break;
		}//��iָ���Ԫ��С��beComparedʱ��i��ͣ��
		while (arr[--j] > beCompared);//��jָ���Ԫ��С��beComparedʱ��j��ͣ��
		if (i >= j)	break;//ɨ��ѭ����ֹ����
		exchange(arr[i], arr[j]);
	}
	exchange(arr[lo], arr[j]);//������jԪ�ر��Ŷ�
	return j;
}
void quickSort(int* arr, int lo, int hi, int k) {
	if (hi <= lo)	return;
	int mid = partition(arr, lo, hi);
	if (mid == k) {
		cout << arr[mid] << endl;
		return;
	}
	else if (mid < k) {
		quickSort(arr, mid + 1, hi, k);
	}
	else {
		quickSort(arr, lo, mid - 1, k);
	}
}
int main() {
	int k;
	cout << "������k��ֵ��" << endl;
	cin >> k;
	quickSort(arr, 0, N - 1, --k);
	return 0;
}