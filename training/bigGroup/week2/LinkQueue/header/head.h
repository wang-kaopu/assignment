#pragma once
#ifndef LQUEUE_H_INCLUDED
#define LQUEUE_H_INCLUDED

//��ʽ���нṹ
typedef struct node
{
    void* data;                   //������ָ��
    struct node* next;            //ָ��ǰ������һ���
} Node;

typedef struct Lqueue
{
    Node* front;                   //��ͷ
    Node* rear;                    //��β
    size_t length;            //���г���
} LQueue;
typedef enum
{
    FALSE = 0, TRUE = 1
} Status;
void InitLQueue(LQueue* Q);//��ʼ������
void DestoryLQueue(LQueue* Q);//���ٶ���
Status IsEmptyLQueue(const LQueue* Q);//�������Ƿ�Ϊ��
Status GetHeadLQueue(LQueue* Q, Node** e);//�鿴��ͷԪ��
int LengthLQueue(LQueue* Q);//ȷ�����г���
Status EnLQueue(LQueue* Q, void* data);//��Ӳ���
Status DeLQueue(LQueue* Q);//���Ӳ���
void ClearLQueue(LQueue* Q);//��ն���
Status TraverseLQueue(const LQueue* Q, void (*foo)(void* q));//������������
void LPrint(void* q);//��������
void menu();
void start(void* q);
#endif // LQUEUE_H_INCLUDED