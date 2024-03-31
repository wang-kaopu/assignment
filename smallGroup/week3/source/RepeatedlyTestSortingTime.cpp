using namespace std;
#include <iostream>
#include <math.h>
#include <malloc.h>
#include <stdlib.h>
#include <Windows.h>
#include <ctime>
#include <time.h>

/*
	С����������ʱ�����
*/

//����������
//int arr[30] = { 30,29,28,27,26,25,24,23,22,21,20,19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1 };
int arr10[100];
//int arr50[50000];
//int arr100[100000];
//��������
int* arr2 = NULL;
int menu() {
	cout << "��ѡ����Ҫʹ�õ������㷨��" << endl;
	cout << "1. ��������" << endl;
	cout << "2. �鲢����" << endl;
	cout << "3. ��������" << endl;
	cout << "4. ��������" << endl;
	cout << "5. ������������" << endl;
	int i = 0;
	while (1)
	{
		cin >> i;
		char c;
		while ((c = getchar()) != '\n');
		if (i <= 5 && i >= 1) {
			break;
		}
		else {
			cout << "ѡ������������ѡ��" << endl;
		}
	}
	return i;
}
//��ӡ����
void printArr(int arr[], int n) {
	for (int i = 0;i < n;i++)
		cout << arr[i] << ' ';
	cout << endl;
}
//����Ԫ��
void exchange(int& i, int& j) {
	int tmp = i;
	i = j;
	j = tmp;
}
/*
	1.��������
*/
/*
	����ȡ��iԪ�أ�jԪ���Լ�j���Ԫ������Ųһλ��iԪ�طŵ�jԪ�ص�λ��
*/
void insert(int arr[], int i, int j) {
	int tmp = arr[i];
	for (;i > j;i--) {
		arr[i] = arr[i - 1];
	}
	arr[j] = tmp;
}
/*
	i��1��ʼ��iԪ����ǰѰ�ұ�iԪ��С��Ԫ��
	1. ����ҵ����Ԫ�أ��Ͳ���
	2. ����Ҳ������Ԫ�أ�˵��iԪ��������
*/
void insertSort(int arr[], int n) {
	//if (arr[0] > arr[1]) {
	//	exchange(arr[0],arr[1]);
	//}
	for (int i = 1;i < n;i++)
	{
		int j = i - 1;
		while (arr[j] > arr[i]) {
			--j;
		}
		insert(arr, i, j + 1);
	}
}
/*
	2. �鲢����
*/
/*
	�ڸ������������ϲ���ԭ����
*/
void merge(int arr[], int lo, int mid, int hi) {
	int i = lo, j = mid + 1;
	for (int k = lo;k <= hi;k++) {
		arr2[k] = arr[k];
	}
	for (int k = lo;k <= hi;k++) {
		if (i > mid)	arr[k] = arr2[j++];
		else if (j > hi)	arr[k] = arr2[i++];
		else if (arr2[i] < arr2[j])	arr[k] = arr2[i++];
		else arr[k] = arr2[j++];
	}
}
/*
	1. �ԱȽϴ�����飬�ֳ����룬�ֱ������������򣬲��ҽ������븴�Ƶ���������
	��������ʼ����Ϊlo����������Ϊmid���Ұ����ʼ����Ϊmid+1����������Ϊhi
	�����ƶ�������i��j��i��ʼָ��lo��jָ��mid+1
	i��j��ʼ�ƶ���ͨ���Ƚ�i��jԪ�صĴ�С����ѡ������һ��Ԫ�طŵ����������ȥ
	2. �ԱȽ�С��ģ�����飬ʹ�ò�������
*/
void mergeSort(int arr[], int lo, int hi) {
	/*if (hi - lo <= 100) {
		insertSort(arr, hi - lo + 1);
		return;
	}*/
	if (hi <= lo)	return;//�����С��ģ�����������Ļ��ͻ������
	int mid = lo + (hi - lo) / 2;
	mergeSort(arr, lo, mid);
	mergeSort(arr, mid + 1, hi);
	merge(arr, lo, mid, hi);
}
/*
	3. ��������
*/
/*
	ѡȡ�������Ŷ���Ԫ��mid��Ȼ��ȷ������߶���midԪ��С���ұ߶���midԪ�ش�
	����midԪ�ص��������Ը�����Ϊ׼���������߷ֱ��ΪС�����������
*/
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
/*
	1. ѡȡһ��Ԫ����Ϊ�з�Ԫ��mid����midԪ��С��ȫ������ߣ����ȫ�����ұߣ���ʱmidԪ��λ�ñ��Ŷ�
	mid�����ΪС���飬������һ�£�mid�ұ�Ҳ��ΪС���飬������һ��
	2. ��mid��ߵ�С������˵��������ѡȡһ��Ԫ����Ϊ�з�Ԫ��mid2����mid2С��ȫ������ߣ����ȫ�����ұ�
	mid2�����ΪС���飬�Լ���������mid2�ұ�Ҳ�Լ���������
	mid2�ұ�С����ͬ��
	3. �ٴ�ѡȡ���ٴ��з֣�ֱ����ģ��С
*/
void quickSort(int* arr, int lo, int hi) {
	if (hi <= lo)	return;
	int mid = partition(arr, lo, hi);
	quickSort(arr, lo, mid - 1);
	quickSort(arr, mid + 1, hi);
}
/*
	4. ��������
*/
void findMinAndMax(int* arr, int n, int& minValue, int& maxValue) {
	for (int i = 1;i < n;i++) {
		if (arr[i] > maxValue)	maxValue = arr[i];
		if (arr[i] < minValue)	minValue = arr[i];
	}
}
void countingSort(int* arr, int n) {
	int minValue = arr[0], maxValue = arr[0];
	findMinAndMax(arr, n, minValue, maxValue);//�ҳ���Сֵ�����ֵ
	int* aux = (int*)malloc((maxValue - minValue + 1) * sizeof(int));//������������
	memset(aux, 0, (maxValue - minValue + 1) * sizeof(int));
	for (int i = 0;i < n;i++) {
		aux[arr[i] - minValue]++;//arr[i]��ֵ��Ӧ��һ������
		arr[i] = 0;//ԭ����iλ�����㣬��������aux��ԭ��ԭ����
	}
	int j = 0;//j����ָ��ԭ�����Ԫ��
	for (int i = 0;i < maxValue - minValue + 1; i++) {//i����ָ��������
		for (int k = 0;k < aux[i];k++, j++) {//k������ʾҪ��ԭ����ż��Σ��������������+minValue��
			arr[j] = i + minValue;
		}
	}
	free(aux);
}
/*
	5. MSD��������
*/
int FindMSD(int* arr, int n) {
	int max = arr[0];
	for (int i = 1;i < n;i++)
		if (max < arr[i])	max = arr[i];
	int count = 0;
	while (max) {
		max /= 10;
		count++;
	}
	return count;
}
void RadixMSDSort(int arr[], int lo, int hi, int msd) {
	int* aux[10] = { NULL };
	for (int i = 0;i < 10;i++) {
		aux[i] = (int*)malloc(((hi - lo) + 1) * sizeof(int));
		memset(aux[i], 0, ((hi - lo) + 1) * sizeof(int));
	}

	int div = pow(10, msd);
	int count[10] = { 0 };//��¼��i��Ͱ�ж��ٸ�����
	for (int i = lo;i <= hi;i++) {
		aux[(arr[i] / div) % 10][count[(arr[i] / div) % 10]++] = arr[i];//���������ָ������λ�Ž�Ͱ��
	}
	//������Ͱ���ռ�����
	int move = lo;
	for (int i = 0;i < 10;i++)
		for (int j = 0;j < count[i];j++)
			arr[move++] = aux[i][j];
	//��ÿ��Ͱ�ֱ��������
	int begin = lo;
	for (int i = 0;i < 10;i++) {
		if (count[i] > 1 && msd > 0)
			RadixMSDSort(arr, begin, begin + count[i] - 1, msd - 1);
		begin += count[i];
	}
	for (int i = 0;i < 10;i++) {
		free(aux[i]);
	}
}
int menu2()
{
	int q = 0;
	while (1) {
		cout << "��ѡ�����������" << endl;
		cout << "1. 10k" << endl;
		cout << "2. 50k" << endl;
		cout << "3. 100k" << endl;
		cin >> q;
		if (q >= 1 && q <= 3)	break;
		cout << "ѡ������������ѡ��" << endl;
	}
	return q;
}
int main() {
	while (1) {
		int q = menu2();
		int* arr = NULL;
		int arrLen = 0;
		arr = arr10;
		arrLen = sizeof(arr10) / sizeof(int);	
		int msd = 0;
		double totalTime = 0;
		double time1 = 0, time2 = 0;
		int times = 0;
		switch (q) {
		case 1:
			times = 10000;
			break;
		case 2:
			times = 50000;
			break;
		case 3:
			times = 100000;
			break;
		default:
			break;
		}
		time1 = clock();
		for (int t = 0;t < times;t++) {
			//time1 = time2 = 0;
			//cout << "���������������" << endl;
			for (int i = 0;i < arrLen;i++) {
				arr[i] = rand() % 1000;
			}
			//cout << arr[0] << ":";
			//cout << "�������" << endl;
			insertSort(arr, arrLen);
			//printArr(arr, arrLen);
		}
		time2 = clock();
		cout << "�������򻨷ѵ�ʱ���ǣ�" << time2 - time1 << "ms" << endl << endl;

		totalTime = 0;
		time1 = clock();
		for (int t = 0;t < times;t++) {
			//time1 = time2 = 0;
			//cout << "���������������" << endl;
			for (int i = 0;i < arrLen;i++) {
				arr[i] = rand() % 1000;
			}
			//cout << arr[0] << ":";
			//cout << "�������" << endl;
			arr2 = (int*)malloc(arrLen * sizeof(int));//��������
			memset(arr2, 0, arrLen);
			mergeSort(arr, 0, arrLen - 1);
			//printArr(arr,arrLen);
			free(arr2);
		}
		time2 = clock();
		cout << "�鲢���򻨷ѵ�ʱ���ǣ�" << time2 - time1 << "ms" << endl << endl;

		totalTime = 0;
		time1 = clock();
		for (int t = 0;t < times;t++) {
			//time1 = time2 = 0;
			//cout << "���������������" << endl;
			for (int i = 0;i < arrLen;i++) {
				arr[i] = rand() % 1000;
			}
			//cout << arr[0] << ":";
			//cout << "�������" << endl;
			quickSort(arr, 0, arrLen - 1);
			//totalTime += time2 - time1;
		}
		time2 = clock();
		cout << "�������򻨷ѵ�ʱ���ǣ�" << time2 - time1 << "ms" << endl << endl;

		totalTime = 0;
		time1 = clock();
		for (int t = 0;t < times;t++) {
			//time1 = time2 = 0;
			//cout << "���������������" << endl;
			for (int i = 0;i < arrLen;i++) {
				arr[i] = rand() % 1000;
			}
			//cout << arr[0] << ":";
			//cout << "�������" << endl;
			countingSort(arr, arrLen);
			//printArr(arr,arrLen);
			//totalTime += time2 - time1;
		}
		time2 = clock();
		cout << "�������򻨷ѵ�ʱ���ǣ�" << time2 - time1 << "ms" << endl << endl;

		totalTime = 0;
		time1 = clock();
		for (int t = 0;t < times;t++) {
			//time1 = time2 = 0;
			//cout << "���������������" << endl;
			for (int i = 0;i < arrLen;i++) {
				arr[i] = rand() % 1000;
			}
			//cout << arr[0] << ":";
			//cout << "�������" << endl;
			msd = FindMSD(arr, arrLen);//�ҵ����λ
			RadixMSDSort(arr, 0, arrLen - 1, msd);
			//printArr(arr, arrLen);
			//totalTime += time2 - time1;
		}
		time2 = clock();
		cout << "MSD�������򻨷ѵ�ʱ���ǣ�" << time2 - time1 << "ms" << endl << endl;
	}
	return 0;
}