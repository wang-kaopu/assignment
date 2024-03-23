#pragma once
#ifndef LQUEUE_H_INCLUDED
#define LQUEUE_H_INCLUDED

//链式队列结构
typedef struct node
{
    void* data;                   //数据域指针
    struct node* next;            //指向当前结点的下一结点
} Node;

typedef struct Lqueue
{
    Node* front;                   //队头
    Node* rear;                    //队尾
    size_t length;            //队列长度
} LQueue;
typedef enum
{
    FALSE = 0, TRUE = 1
} Status;
void InitLQueue(LQueue* Q);//初始化队列
void DestoryLQueue(LQueue* Q);//销毁队列
Status IsEmptyLQueue(const LQueue* Q);//检查队列是否为空
Status GetHeadLQueue(LQueue* Q, Node** e);//查看队头元素
int LengthLQueue(LQueue* Q);//确定队列长度
Status EnLQueue(LQueue* Q, void* data);//入队操作
Status DeLQueue(LQueue* Q);//出队操作
void ClearLQueue(LQueue* Q);//清空队列
Status TraverseLQueue(const LQueue* Q, void (*foo)(void* q));//遍历函数操作
void LPrint(void* q);//操作函数
void menu();
void start(void* q);
#endif // LQUEUE_H_INCLUDED