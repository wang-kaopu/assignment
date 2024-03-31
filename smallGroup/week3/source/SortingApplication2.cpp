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
	int i = lo, j = hi + 1;//j+1是因为下面扫描循环要先--再用j
	//i和j两个索引分别从左右两边开始扫描
	while (1) {
		while (arr[++i] < beCompared) {
			if (i >= hi)	break;
		}//当i指向的元素小于beCompared时，i先停下
		while (arr[--j] > beCompared);//当j指向的元素小于beCompared时，j先停下
		if (i >= j)	break;//扫描循环终止条件
		exchange(arr[i], arr[j]);
	}
	exchange(arr[lo], arr[j]);//交换后，j元素被排定
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
	cout << "请输入k的值：" << endl;
	cin >> k;
	quickSort(arr, 0, N - 1, --k);
	return 0;
}