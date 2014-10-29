#ifndef _ADDRESS_BOOK_H_
#define _ADDRESS_BOOK_H_

#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <algorithm>
#include <time.h>
#include <windows.h>

#define BUFFERSIZE 1024
#define BUFFERSIZE_S "1024"
#define BOOK_PARSE_RULE "%"BUFFERSIZE_S"[^,]%*c%"BUFFERSIZE_S"[^,]%*c%"BUFFERSIZE_S"[^,]%*c%"BUFFERSIZE_S"[^\r\n]"

#if _MSC_VER > 1500
#define PARSEADDRESSBOOK(s, buffer) (sscanf_s(s.c_str(), BOOK_PARSE_RULE, buffer[0], BUFFERSIZE, buffer[1], BUFFERSIZE, buffer[2], BUFFERSIZE, buffer[3], BUFFERSIZE) == 4)
#endif

typedef enum {FirstName, LastName, PhoneNumber, CityName, NotInitialized} Target;

using namespace std;

struct UserInformation
{
	string firstName, lastName;
	string phoneNumber;
	string cityName;

	UserInformation(const char* fn, const char* ln, const char* pn, const char* cn)
	{
		char buffer[3][BUFFERSIZE];
		firstName = fn;
		lastName = ln;
		phoneNumber = pn;
		cityName = cn;
	}

	void printWithTag(Target t)
	{
		switch(t)
		{
		case FirstName:
			cout << firstName<<" "<<lastName<<endl;
			break;
		case LastName:
			cout << firstName<<" "<<lastName<<endl;
			break;
		case PhoneNumber:
			cout << phoneNumber<<endl;
			break;
		case CityName:
			cout << cityName<<endl;
			break;
		default:
			break;
		}
	}
};

class AddressBook
{	
	Target searchTarget;
	vector<UserInformation> vecAddressBook;
	vector<UserInformation*> sortedCache;
public:
	AddressBook() : searchTarget(NotInitialized) {}
	bool Openfile(const char*);
	bool setSearchTarget(Target);
	Target getSearchTarget() 
	{ 
		return searchTarget; 
	}
	void doSearchWith(vector<UserInformation*>& results, const char* keywords)const;
	void addRecord(UserInformation);

private:
	string getFN(UserInformation* info) const { return info->firstName; }
	string getLN(UserInformation* info) const { return info->lastName; }
	string getPN(UserInformation* info) const { return info->phoneNumber; }
	string getCN(UserInformation* info) const { return info->cityName; }

	template <class FuncType>
	int binSearch(const char* keyWords, FuncType Func) const
	{
		int start = 0, end = sortedCache.size() - 1;
		int mid = (start + end) / 2;
		int len = strlen(keyWords);
		int n;
		while(start < end && (n = (this->*Func)(sortedCache[mid]).compare(0, len, keyWords)) != 0)
		{
			if(n < 0) 
			{
				start = mid + 1;
			}
			else if(n > 0)
			{
				end = mid - 1;
			}
			mid = (start + end) / 2;
		}
		if((this->*Func)(sortedCache[mid]).compare(0, len, keyWords) == 0)
			return mid;
		return -1;
	}	
};

bool compByFirstName(UserInformation* info1, UserInformation* info2);
bool compByLastName(UserInformation* info1, UserInformation* info2);
bool compByPhoneNumber(UserInformation* info1, UserInformation* info2);
bool compByCityName(UserInformation* info1, UserInformation* info2);

bool doSearch(AddressBook& book);
#endif