#pragma once
typedef struct LNode {
	int data;
	LNode* next;
}LNode;
typedef struct LNodeD {
	int data;
	LNodeD* last;
	LNodeD* next;
};
int menu();
void checkAll(LNode* head);
int checkCircle(LNode* head);
LNode* findMid(LNode* head);
void exchange(LNode* head);
void reverse(LNode* head);
void sllStart(LNode* head);