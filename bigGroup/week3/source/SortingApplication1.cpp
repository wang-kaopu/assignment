using namespace std;
#define N 20
#include <iostream>
#include <stdlib.h>
void printArr(int arr[]) {
	for (int i = 0;i < N;i++)
		cout << arr[i] << ' ';
	cout << endl;
}
void exchange(int& i, int& j) {
	int tmp = i;
	i = j;
	j = tmp;
}
int main() {
	int arr[N];
	for (int i = 0;i < N;i++) {
		arr[i] = rand() % 3;
	}
	printArr(arr);
	int p0 = 0, p1 = 0, p2 = N - 1;
	for (;p1 <= p2;p1++) {
		while (1 == arr[p1]) p1++;
		if (arr[p1] > arr[p2])
			exchange(arr[p1], arr[p2]);
		while (2 == arr[p2]) p2--;
		if (arr[p1] < arr[p0])
			exchange(arr[p1], arr[p0]);
		while (0 == arr[p0]) p0++;
	}
	printArr(arr);
	return 0;
}