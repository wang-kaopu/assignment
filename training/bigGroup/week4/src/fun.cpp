#include "head.h"
#include <iostream>
#include <assert.h>
using namespace std;
int menu() {
	int n = 0;
	while (1) {
		cout << "请选择操作：" << endl;
		cout << "1. 查找" << endl;
		cout << "2. 插入" << endl;
		cout << "3. 删除" << endl;
		cout << "4. 先序遍历（递归）" << endl;
		cout << "5. 中序遍历（递归）" << endl;
		cout << "6. 后序遍历（递归）" << endl;
		cout << "7. 先序遍历（非递归）" << endl;
		cout << "8. 中序遍历（非递归）" << endl;
		cout << "9. 后序遍历（非递归）" << endl;
		cout << "10. 层序遍历" << endl;
		cin >> n;
		char ch;
		while ((ch = getchar()) != '\n');
		if (n >= 1 && n <= 10) {
			return n;
		}
		else {
			cout << "选择有误，请重新选择" << endl;
		}
	}
}
//初始化树
Status BST_init(BinarySortTreePtr tree) {
	assert(tree);
	tree->root = (NodePtr)malloc(sizeof(Node));
	tree->root->value = 19;
	tree->root->left = tree->root->right = NULL;
	for (int i = 1;i <= 20;i++) {
		//BST_insert(tree, i * 2);//两条分叉的测试数据
		BST_insert(tree, rand() % 40);//伪随机测试数据
	}
	return SUCCESS;
}
//插入函数组件，考虑面对根节点，该节点应当如何插入
Status findInsertNode(NodePtr visit, NodePtr node) {
	assert(visit && node);
	if (visit->value == node->value)	return FAILED;
	if (visit->left == NULL && visit->value > node->value){
		visit->left = node;
		return SUCCESS;
	}
	else if (visit->right == NULL && visit->value < node->value){
		visit->right = node;
		return SUCCESS;
	}
	else if (visit->left != NULL && visit->left->value < node->value && visit->value > node->value) {
		node->left = visit->left;
		visit->left = node;
		return SUCCESS;
	}
	else if (visit->right != NULL && visit->right->value > node->value && visit->value < node->value) {
		node->right = visit->right;
		visit->right = node;
		return SUCCESS;
	}
	if (visit->right != NULL && visit->value < node->value) {
		findInsertNode(visit->right, node);
	}
	else if (visit->left != NULL && visit->value > node->value) {
		findInsertNode(visit->left, node);
	}
}
//插入函数
Status BST_insert(BinarySortTreePtr tree, ElemType value) {
	assert(tree);
	if (tree->root->value == value)	return FAILED;
	NodePtr newNode = (NodePtr)malloc(sizeof(Node));
	assert(newNode);
	newNode->left = newNode->right = NULL;
	newNode->value = value;
	findInsertNode(tree->root, newNode);
	return SUCCESS;
}

//初始化栈
Status initLStack(LinkStack* stack) {
	stack->count = 0;
	stack->top = NULL;
	return SUCCESS;
}

//先序遍历1
Status BST_preorderI(BinarySortTreePtr tree, NodePtr visit) {
	if (visit == NULL)	return SUCCESS;
	cout << visit->value << ' ';
	BST_preorderI(tree, visit->left);
	BST_preorderI(tree, visit->right);
}

//中序遍历1
Status BST_inorderI(BinarySortTreePtr tree, NodePtr visit) {
	if (visit == NULL)	return SUCCESS;
	BST_inorderI(tree, visit->left);
	cout << visit->value << ' ';
	BST_inorderI(tree, visit->right);
	return SUCCESS;
}

//后序遍历1
Status BST_postorderI(BinarySortTreePtr tree, NodePtr visit) {
	if (visit == NULL)	return SUCCESS;
	BST_postorderI(tree, visit->left);
	BST_postorderI(tree, visit->right);
	cout << visit->value << ' ';
	return SUCCESS;
}

NodePtr checkNode(NodePtr node, ElemType value) {
	if (node == NULL)	return NULL;
	else if (node->value == value)	return node;
	else if (value < node->value) return checkNode(node->left, value);
	else if (value > node->value)	return checkNode(node->right, value);
}
//查找
NodePtr BST_search(BinarySortTreePtr tree, ElemType value) {
	return checkNode(tree->root, value);
}
//找到节点的根节点
NodePtr findParent(NodePtr node, NodePtr child) {
	if (node == NULL)	return NULL;
	if (node->left == child || node->right == child)	return node;
	if (child->value < node->value)	return findParent(node->left, child);
	else if (child->value > node->value)	return findParent(node->right, child);
}
//找到节点的左子树的最大节点（直接前驱）
NodePtr findLeftBiggest(NodePtr node) {
	NodePtr leftBiggest = node->left;
	for (;node->right != NULL;node = node->right);
	return leftBiggest;
}
//找到节点的右子树的最小节点（直接后继）（未使用）
NodePtr findRightSmallest(NodePtr node) {
	NodePtr rightSmallest = node->right;
	for (;node->left != NULL;node = node->left);
	return rightSmallest;
}
//交换节点
Status exchangeNode(NodePtr node1,NodePtr node2) {
	NodePtr tmp = node1;
	node1 = node2;
	node2 = tmp;
	return SUCCESS;
}
//删除
Status BST_delete(BinarySortTreePtr tree, NodePtr node) {
	NodePtr parent = findParent(tree->root, node);
	NodePtr& toBeChanged = parent->left;
	if (parent->left == node)
		toBeChanged = parent->left;
	else
		toBeChanged = parent->right;
	assert(parent && toBeChanged);
	NodePtr toBeFree = node;
	if (node->left == NULL && node->right == NULL)//叶子节点
		toBeChanged = NULL;
	else if (node->left != NULL && node->right == NULL)//单个孩子
		toBeChanged = node->left;
	else if (node->left == NULL && node->right != NULL)//单个孩子
		toBeChanged = node->right;
	else {//两个孩子
		NodePtr leftBiggest = findLeftBiggest(node);
		//NodePtr rightSmallest = findRightSmallest(node);
		assert(leftBiggest);
		exchangeNode(leftBiggest, node);
		BST_delete(tree, leftBiggest);
	}
	free(toBeFree);
	toBeFree = NULL;
	return SUCCESS;
}
//入栈
Status pushLStack(LinkStack* s, NodePtr data) {
	StackNode* fresh = (StackNode*)malloc(sizeof(StackNode));
	if (NULL != fresh) {
		fresh->data = data;
		fresh->next = s->top;
		++s->count;
		s->top = fresh;
		return SUCCESS;
	}
	else {
		return FAILED;
	}
}
//出栈
StackNode* popLStack(LinkStack* s) {
	assert(s);
	StackNode* toBePop = s->top;
	if (toBePop == NULL)	return NULL;
	s->top = s->top->next;
	--s->count;
	return toBePop;
}
//先序遍历（非递归）
Status BST_preorderR(BinarySortTreePtr tree) {
	LinkStack* stack = (LinkStack*)malloc(sizeof(LinkStack));
	initLStack(stack);
	pushLStack(stack, tree->root);
	while (1) {
		StackNode* pop = popLStack(stack);
		if (NULL == pop)	break;
		cout << pop->data->value << ' ';
		if (pop->data->right != NULL)
			pushLStack(stack, pop->data->right);
		if (pop->data->left != NULL)
			pushLStack(stack, pop->data->left);
	}
	for (StackNode* pop = popLStack(stack);pop != NULL;pop=popLStack(stack)) {
		cout << pop->data->value << ' ';
		free(pop);
	}
	cout << endl;
	free(stack);
	return SUCCESS;
}
//中序遍历（非递归）
Status BST_inorderR(BinarySortTreePtr tree) {
	LinkStack* stack = (LinkStack*)malloc(sizeof(LinkStack));
	initLStack(stack);
	NodePtr move = tree->root;
	while (1) {
		for (;move != NULL;move = move->left) {
			pushLStack(stack, move);
		};
		if (stack->top == NULL)	break;
		StackNode* pop = popLStack(stack);
		for (;pop != NULL && pop->data->right == NULL;pop = popLStack(stack)) {
			move = pop->data;
			cout << move->value << ' ';
			free(move);
		}
		if (pop == NULL)	break;
		move = pop->data;
		cout << move->value << ' ';
		move = move->right;
	}
	return SUCCESS;
}
//后序遍历（非递归）
Status BST_postorderR(BinarySortTreePtr tree) {
	LinkStack* stack = (LinkStack*)malloc(sizeof(LinkStack));
	initLStack(stack);
	NodePtr move = tree->root;
	pushLStack(stack, move);
	NodePtr moveLast = NULL;
	while(stack->top != NULL) {
		while (move->left != NULL && moveLast != move->left) {
			moveLast = move;
			move = move->left;
			pushLStack(stack, move);
		}
		StackNode* pop = popLStack(stack);
		for (;pop != NULL;pop = popLStack(stack)) {
			moveLast = move;
			move = pop->data;
			if (move->right == NULL || move->right == moveLast) {
				cout << move->value << ' ';
			}
			else {
				pushLStack(stack, move);
				moveLast = move;
				move = move->right;
				pushLStack(stack, move);
				break;
			}
		}
	}
	return SUCCESS;
}
void InitLQueue(LQueue* Q) {
	Q->front = NULL;
	Q->rear = NULL;
	Q->length = 0;
}
Status EnLQueue(LQueue* Q, NodePtr data) {
	//入队操作
	if (NULL == Q) {
		return FALSE;
	}
	QNode* fresh = (QNode*)malloc(sizeof(QNode));
	if (NULL != fresh) {
		fresh->next = NULL;
		if (NULL == Q->front) {
			Q->front = fresh;
		}
		else {
			Q->rear->next = fresh;
		}
		Q->rear = fresh;
		++Q->length;
		fresh->data = data;
		return TRUE;
	}
	return FALSE;
}
QNode* DeLQueue(LQueue* Q) {
	//出队操作
	assert(Q && Q->front);
	QNode* toBeFree = Q->front;
	cout << "获得出队元素(" << toBeFree->data << "):";
	Q->front = Q->front->next;
	--Q->length;
	return toBeFree;
}
//层序遍历
Status BST_levelOrder(BinarySortTreePtr tree) {
	LQueue* queue = (LQueue*)malloc(sizeof(LQueue));
	InitLQueue(queue);
	EnLQueue(queue, tree->root);
	for (QNode* pop = DeLQueue(queue);pop->data!= NULL;pop = DeLQueue(queue)) {
		cout << pop->data->value << endl;
		if (pop->data->left != NULL)
			EnLQueue(queue, pop->data->left);
		if (pop->data->right != NULL)
			EnLQueue(queue, pop->data->right);
		if (queue->front == NULL)	break;
	}
	return SUCCESS;
}