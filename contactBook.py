# print(" MY CONTACT BOOK")
'''
example dictionary
contact_book = {
    "Alice": "123-456-7890",
    "Bob": "987-654-3210",
    "Charlie": "555-123-4567"
}
'''

contact_list =  {}

def new():

    name = input("Enter contact's name = ")
    number = input("Enter contact's number = ")

    contact_list[name] = number
    print(f'Contact {name} has been added!')

def search_contact():
    find = input("Enter the name to search =")
    if find in contact_list:
        print(f'The number of {find} is {contact_list[find]}')
    else:
        print(f'The number of {find} is not found!')


def menu():
    print(" MY CONTACT BOOK")
    while True:
        print('''CONTACT BOOK's MENU:
            1. Add new contact
            2. Search number of contact by name
            3. Exit''')
    
        user = input("SELECT AN OPTION:")

        if user == '1':
            new()
        elif user == '2':
            search_contact()
        elif user =='3':
            print("Goodbye!")
            break
        else:                                        
            print("Invalid input.")
menu()