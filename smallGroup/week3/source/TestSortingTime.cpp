using namespace std;
#include <iostream>
#include <math.h>
#include <malloc.h>
#include <stdlib.h>
#include <Windows.h>
#include <ctime>
#include <time.h>

//测试用数组
//int arr[30] = { 30,29,28,27,26,25,24,23,22,21,20,19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1 };
int arr10[10000];
int arr50[50000];
int arr100[100000];
//辅助数组
int* arr2 = NULL;
int menu() {
	cout << "请选择你要使用的排序算法：" << endl;
	cout << "1. 插入排序" << endl;
	cout << "2. 归并排序" << endl;
	cout << "3. 快速排序" << endl;
	cout << "4. 计数排序" << endl;
	cout << "5. 基数计数排序" << endl;
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
			cout << "选择有误，请重新选择：" << endl;
		}
	}
	return i;
}
//打印数组
void printArr(int arr[], int n) {
	for (int i = 0;i < n;i++)
		cout << arr[i] << ' ';
	cout << endl;
}
//交换元素
void exchange(int& i, int& j) {
	int tmp = i;
	i = j;
	j = tmp;
}
/*
	1.插入排序
*/
/*
	单独取出i元素，j元素以及j后的元素往后挪一位，i元素放到j元素的位置
*/
void insert(int arr[], int i, int j) {
	int tmp = arr[i];
	for (;i > j;i--) {
		arr[i] = arr[i - 1];
	}
	arr[j] = tmp;
}
/*
	i从1开始，i元素往前寻找比i元素小的元素
	1. 如果找到这个元素，就插入
	2. 如果找不到这个元素，说明i元素是最大的
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
	2. 归并排序
*/
/*
	在辅助数组把两半合并到原数组
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
	1. 对比较大的数组，分成两半，分别对两半进行排序，并且将这两半复制到辅助数组
	设左半边起始索引为lo，结束索引为mid；右半边起始索引为mid+1，结束索引为hi
	现有移动的索引i，j；i初始指向lo，j指向mid+1
	i，j开始移动，通过比较i，j元素的大小，来选择其中一个元素放到结果数组里去
	2. 对比较小规模的数组，使用插入排序
*/
void mergeSort(int arr[], int lo, int hi) {
	/*if (hi - lo <= 100) {
		insertSort(arr, hi - lo + 1);
		return;
	}*/
	if (hi <= lo)	return;//不想对小规模数组插入排序的话就换成这句
	int mid = lo + (hi - lo) / 2;
	mergeSort(arr, lo, mid);
	mergeSort(arr, mid + 1, hi);
	merge(arr, lo, mid, hi);
}
/*
	3. 快速排序
*/
/*
	选取即将被排定的元素mid，然后确保其左边都比mid元素小，右边都比mid元素大
	返回mid元素的索引，以该索引为准，左右两边分别成为小数组各自排序
*/
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
/*
	1. 选取一个元素作为切分元素mid，比mid元素小的全都放左边，大的全都放右边，此时mid元素位置被排定
	mid左边作为小数组，来排序一下；mid右边也作为小数组，来排序一下
	2. 以mid左边的小数组来说，在其中选取一个元素作为切分元素mid2，比mid2小的全都放左边，大的全都放右边
	mid2左边作为小数组，自己进行排序；mid2右边也自己进行排序
	mid2右边小数组同理
	3. 再次选取，再次切分，直至规模较小
*/
void quickSort(int* arr, int lo, int hi) {
	if (hi <= lo)	return;
	int mid = partition(arr, lo, hi);
	quickSort(arr, lo, mid - 1);
	quickSort(arr, mid + 1, hi);
}
/*
	4. 计数排序
*/
void findMinAndMax(int* arr, int n, int& minValue, int& maxValue) {
	for (int i = 1;i < n;i++) {
		if (arr[i] > maxValue)	maxValue = arr[i];
		if (arr[i] < minValue)	minValue = arr[i];
	}
}
void countingSort(int* arr, int n) {
	int minValue = arr[0], maxValue = arr[0];
	findMinAndMax(arr, n, minValue, maxValue);//找出最小值和最大值
	int* aux = (int*)malloc((maxValue - minValue + 1) * sizeof(int));//创建辅助数组
	memset(aux, 0, (maxValue - minValue + 1) * sizeof(int));
	for (int i = 0;i < n;i++) {
		aux[arr[i] - minValue]++;//arr[i]的值对应了一个索引
		arr[i] = 0;//原数组i位置置零，方便后面把aux还原到原数组
	}
	int j = 0;//j用来指向原数组的元素
	for (int i = 0;i < maxValue - minValue + 1; i++) {//i用来指向辅助数组
		for (int k = 0;k < aux[i];k++, j++) {//k用来表示要向原数组放几次（辅助数组的索引+minValue）
			arr[j] = i + minValue;
		}
	}
	free(aux);
}
/*
	5. MSD基数排序
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
	int count[10] = { 0 };//记录第i个桶有多少个数字
	for (int i = lo;i <= hi;i++) {
		aux[(arr[i] / div) % 10][count[(arr[i] / div) % 10]++] = arr[i];//把所有数字根据最高位放进桶里
	}
	//从所有桶中收集数据
	int move = lo;
	for (int i = 0;i < 10;i++)
		for (int j = 0;j < count[i];j++)
			arr[move++] = aux[i][j];
	//对每个桶分别进行排序
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
		cout << "请选择数据量的数量级：" << endl;
		cout << "1. 10000" << endl;
		cout << "2. 50000" << endl;
		cout << "3. 100000" << endl;
		cin >> q;
		if (q >= 1 && q <= 3)	break;
		cout << "选择有误，请重新选择：" << endl;
	}
	return q;
}
int main() {
	while (1) {
		int q = menu2();
		int* arr = NULL;
		int arrLen = 0;
		switch (q) {
		case 1:
			arr = arr10;
			arrLen = sizeof(arr10) / sizeof(int);
			break;
		case 2:
			arr = arr50;
			arrLen = sizeof(arr50) / sizeof(int);
			break;
		case 3:
			arr = arr100;
			arrLen = sizeof(arr100) / sizeof(int);
			break;
		default:
			break;
		}
		double time1 = 0, time2 = 0;
		cout << "正在生成随机数组" << endl;
		for (int i = 0;i < arrLen;i++) {
			arr[i] = rand();
		}
		cout << arr[0] << ":";
		cout << "生成完毕" << endl;
		int msd = 0;
		time1 = clock();
		insertSort(arr, arrLen);
		time2 = clock();
		//printArr(arr, arrLen);
		cout << "插入排序花费的时间是：" << time2 - time1 << "ms" << endl << endl;

		cout << "正在生成随机数组" << endl;
		for (int i = 0;i < arrLen;i++) {
			arr[i] = rand();
		}
		cout << arr[0] << ":";
		cout << "生成完毕" << endl;
		arr2 = (int*)malloc(arrLen*sizeof(int));//辅助数组
		memset(arr2, 0, arrLen);
		time1 = clock();
		mergeSort(arr, 0, arrLen - 1);
		time2 = clock();
		//printArr(arr,arrLen);
		cout << "归并排序花费的时间是：" << time2 - time1 << "ms" << endl << endl;
		free(arr2);

		cout << "正在生成随机数组" << endl;
		for (int i = 0;i < arrLen;i++) {
			arr[i] = rand();
		}
		cout << arr[0] << ":";
		cout << "生成完毕" << endl;
		time1 = clock();
		quickSort(arr, 0, arrLen - 1);
		time2 = clock();
		cout << "快速排序花费的时间是：" << time2 - time1 << "ms" << endl << endl;

		cout << "正在生成随机数组" << endl;
		for (int i = 0;i < arrLen;i++) {
			arr[i] = rand();
		}
		cout << arr[0] << ":";
		cout << "生成完毕" << endl;
		time1 = clock();
		countingSort(arr, arrLen);
		time2 = clock();
		//printArr(arr,arrLen);
		cout << "计数排序花费的时间是：" << time2 - time1 << "ms" << endl << endl;

		cout << "正在生成随机数组" << endl;
		for (int i = 0;i < arrLen;i++) {
			arr[i] = rand();
		}
		cout << arr[0] << ":";
		cout << "生成完毕" << endl;
		time1 = clock();
		msd = FindMSD(arr, arrLen);//找到最高位
		RadixMSDSort(arr, 0, arrLen - 1, msd);
		time2 = clock();
		//printArr(arr, arrLen);
		cout << "MSD基数排序花费的时间是：" << time2 - time1 << "ms" << endl << endl;
	}
	return 0;
}