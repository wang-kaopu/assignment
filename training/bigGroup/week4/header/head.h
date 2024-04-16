#pragma once
#ifndef BINARYSORTTREE_BINARY_SORT_TREE_H
#define BINARYSORTTREE_BINARY_SORT_TREE_H

#define TRUE 1
#define FALSE 0
#define SUCCESS 1
#define FAILED 0
#define Status int

typedef int ElemType;
//��
typedef struct Node {
    ElemType value;
    struct Node* left, * right;
}Node, * NodePtr;

typedef struct BinarySortTree {
    NodePtr root;
} BinarySortTree, * BinarySortTreePtr;

//ջ
typedef  struct StackNode
{
	NodePtr data;
	struct StackNode* next;
}StackNode, * LinkStackPtr;

typedef  struct  LinkStack
{
	StackNode* top;
	int	count;
}LinkStack;

//��
typedef struct QNode
{
    NodePtr data;                   //������ָ��
    struct QNode* next;            //ָ��ǰ������һ���
} QNode;

typedef struct Lqueue
{
    QNode* front;                   //��ͷ
    QNode* rear;                    //��β
    size_t length;            //���г���
} LQueue;
/**
 * BST initialize
 * @param BinarySortTreePtr BST
 * @return is complete
 */
Status BST_init(BinarySortTreePtr);

/**
 * BST insert
 * @param BinarySortTreePtr BST
 * @param ElemType value to insert
 * @return is successful
 */
Status BST_insert(BinarySortTreePtr, ElemType);

/**
 * BST delete
 * @param BinarySortTreePtr BST
 * @param ElemType the value for Node which will be deleted
 * @return is successful
 */
Status BST_delete(BinarySortTreePtr, NodePtr);

/**
 * BST search
 * @param BinarySortTreePtr BST
 * @param ElemType the value to search
 * @return is exist
 */
NodePtr BST_search(BinarySortTreePtr, ElemType);

/**
 * BST preorder traversal without recursion
 * @param BinarySortTreePtr BST
 * @param (*visit) callback
 * @return is successful
 */
Status BST_preorderI(BinarySortTreePtr, NodePtr);
/**
 * BST preorder traversal with recursion
 * @param BinarySortTreePtr BST
 * @param (*visit) callback
 * @return is successful
 */
Status BST_preorderR(BinarySortTreePtr);

/**
 * BST inorder traversal without recursion
 * @param BinarySortTreePtr BST
 * @param (*visit) callback
 * @return is successful
 */
Status BST_inorderI(BinarySortTreePtr, NodePtr);

/**
 * BST inorder traversal with recursion
 * @param BinarySortTreePtr BST
 * @param (*visit) callback
 * @return is successful
 */
Status BST_inorderR(BinarySortTreePtr);

/**
 * BST preorder traversal without recursion
 * @param BinarySortTreePtr BST
 * @param (*visit) callback
 * @return is successful
 */
Status BST_postorderI(BinarySortTreePtr, NodePtr);

/**
 * BST postorder traversal with recursion
 * @param BinarySortTreePtr BST
 * @param (*visit) callback
 * @return is successful
 */
Status BST_postorderR(BinarySortTreePtr);

/**
 * BST level order traversal
 * @param BinarySortTreePtr BST
 * @param (*visit) callback
 * @return is successful
 */
Status BST_levelOrder(BinarySortTreePtr);

int menu();
Status initLStack(LinkStack* s);//��ʼ��ջ
Status pushLStack(LinkStack* s, ElemType data);//��ջ
StackNode* popLStack(LinkStack* s);//��ջ

Status EnLQueue(LQueue* Q, void* data);//��Ӳ���
QNode* DeLQueue(LQueue* Q);//���Ӳ���
void InitLQueue(LQueue* Q);//��ʼ������

#endif //BINARYSORTTREE_BINARY_SORT_TREE_H
