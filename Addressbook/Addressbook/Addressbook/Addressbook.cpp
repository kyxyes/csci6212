#include "Addressbook.h"

using namespace std;

bool AddressBook::Openfile(const char* filename)
{
	using namespace std;
	fstream file(filename, fstream::in);
	char buffer[4][BUFFERSIZE];
	string s;
	while(true)
	{		
		getline(file, s);
		if(file.eof()) break;
		if(PARSEADDRESSBOOK(s, buffer))
			addRecord(UserInformation(buffer[0], buffer[1], buffer[2], buffer[3]));
	}
	return true;
}

bool AddressBook::setSearchTarget(Target t)
{
	searchTarget = t;
	if(sortedCache.size() != vecAddressBook.size())
	{
		sortedCache.clear();
		for(vector<UserInformation>::iterator iter = vecAddressBook.begin(); iter != vecAddressBook.end(); ++iter)
		{
			sortedCache.push_back(&*iter);
		}
	}
	switch (searchTarget)
	{
	case FirstName:
		sort(sortedCache.begin(), sortedCache.end(), compByFirstName);
		break;
	case LastName:
		sort(sortedCache.begin(), sortedCache.end(), compByLastName);
		break;
	case PhoneNumber:
		sort(sortedCache.begin(), sortedCache.end(), compByPhoneNumber);
		break;
	case CityName:
		sort(sortedCache.begin(), sortedCache.end(), compByCityName);
		break;
		break;;
	default:
		break;
	}
	return true;
};

void AddressBook::doSearchWith(std::vector<UserInformation*>& results, const char* keywords) const
{
	results.clear();
	int b;
	string (AddressBook::*func) (UserInformation*) const = NULL;
	switch(searchTarget)
	{
	case FirstName:
		b = binSearch(keywords, func = &AddressBook::getFN);
		break;
	case LastName:
		b = binSearch(keywords, func = &AddressBook::getLN);
		break;
	case PhoneNumber:
		b = binSearch(keywords, func = &AddressBook::getPN);
		break;
	case CityName:
		b = binSearch(keywords, func = &AddressBook::getCN);
		break;
	default: return;
	}
	if(b < 0) return;
	int len = strlen(keywords);
	int i = b - 1, j = b;
	for(; i >= 0; --i)
	{
		if((this->*func)(sortedCache[i]).compare(0, len, keywords) != 0)
			break;
	}
	++i;
	for(; j < sortedCache.size(); ++j)
	{
		if((this->*func)(sortedCache[j]).compare(0, len, keywords) != 0)
			break;
	}
	results.assign(sortedCache.begin() + i, sortedCache.begin() + j);
}

void AddressBook::addRecord(UserInformation info)
{
	vecAddressBook.push_back(info);
}

bool compByFirstName(UserInformation* info1, UserInformation* info2)
{
	return info1->firstName.compare(info2->firstName) < 0;
}
bool compByLastName(UserInformation* info1, UserInformation* info2)
{
	return info1->lastName.compare(info2->lastName) < 0;
}
bool compByPhoneNumber(UserInformation* info1, UserInformation* info2)
{
	return info1->phoneNumber.compare(info2->phoneNumber) < 0;
}
bool compByCityName(UserInformation* info1, UserInformation* info2)
{
	return info1->cityName.compare(info2->cityName) < 0;
}