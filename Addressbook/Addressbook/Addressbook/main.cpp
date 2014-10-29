#include "AddressBook.h"

using namespace std;

int main()
{	
	cout<<"Welcome to Addressbook! "<<endl;
	cout<<"Loading file!"<<endl;
	AddressBook book;
	if(!book.Openfile("LargeAddressBook.csv"))
	{
		cout<<"File can not initialized!"<<endl;
		return false;
	}
	else
	{
		cout<<"Initialized!"<<endl; 
	}

	while(true)
	{
		cout<<"What would you like to search on?(F,L,P,C,E)"<<endl;
		cout<<"'F' for first name, 'L' for last name, 'P' for phone number, 'C' for city name, 'E' for exit program!"<<endl;
		char choice = getchar();
		switch (choice)
		{
		case 'F':
			book.setSearchTarget(FirstName);
			doSearch(book);
			break;
		case 'L':
			book.setSearchTarget(LastName);
			doSearch(book);
			break;
		case 'P':
			book.setSearchTarget(PhoneNumber);
			doSearch(book);
			break;
		case 'C':
			book.setSearchTarget(CityName);
			doSearch(book);
			break;
		case 'E':
			cout<<"Exit Program!"<<endl;
			exit(0);
		default:
			cout<<"Invalid Choice!"<<endl;
			cin.sync();
			continue;
		}
		char ch=getchar();
		switch (ch)
		{
		case 'Y':
			return true;
			break;
		case 'N':
			cout<<"Exit Program!"<<endl;
			exit(0);
		default:
		continue;
		}		
	}
	return 0;
}

bool doSearch(AddressBook& book)
{
	switch (book.getSearchTarget())
	{
	case FirstName:
		cout<<"Enter the partial First Name:"<<endl;
		break;
	case LastName:
		cout<<"Enter the partial Last Name:"<<endl;
		break;
	case PhoneNumber:
		cout<<"Enter the partial Phone Number:"<<endl;
		break;
	case CityName:
		cout<<"Enter the partial City Name:"<<endl;
		break;
	default:
		return false;
	}
	char buffer[BUFFERSIZE];
	cin.sync();
	cin.getline(buffer, BUFFERSIZE, '\n');	
	vector<UserInformation*> vecInfo;
	LARGE_INTEGER BeginTime ; 
	LARGE_INTEGER EndTime ; 
	LARGE_INTEGER Frequency ;
	QueryPerformanceFrequency(&Frequency);
	QueryPerformanceCounter(&BeginTime);
	book.doSearchWith(vecInfo, buffer);
	QueryPerformanceCounter(&EndTime) ;
	if(!vecInfo.empty()) 
		cout << "Results:"<<endl;
	for(vector<UserInformation*>::iterator iter = vecInfo.begin(); iter != vecInfo.end(); ++iter)
	{
		(*iter)->printWithTag(book.getSearchTarget());
	}
	cout<<"("<<vecInfo.size()<<") "<<"total matches!"<<endl;
	cout <<"Using time:"<<(EndTime.QuadPart-BeginTime.QuadPart)*1000/Frequency.QuadPart<<"ms"<<endl;
	cout<<"Another Search?[Y, N]"<<endl;
	char ch=getchar();
	switch (ch)
	{
	case 'Y':
		return true;
		break;
	case 'N':
		cout<<"Exit Program!"<<endl;
		exit(0);
	default:
		cout<<"Invalid choice!"<<endl;
		break;
	}
}