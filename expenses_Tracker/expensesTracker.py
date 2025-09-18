import datetime
import csv
import os

filename = "expenses.csv"

def add_expense():
    date = input("Enter the date (YYYY-MM-DD): ")
    if not date.strip():
        date = datetime.datetime.now().strftime("%Y-%m-%d")

    category = input("Category (food, transport, etc.)")
    amount = int(input("How many do you spend? "))
    note = input("Note (optional) : ")

    file_exist = os.path.isfile(filename)
    
    with open("expenses.csv", "a", newline ="") as file:
        writer = csv.writer(file)
        
        if not file_exist:
            writer.writerow(["Date", "Category", "Amount", "Note"])

        writer.writerow([date, category, amount, note])
        print("Expense added successfully!")
        

def view_expenses():
    try:
        with open("expenses.csv", "r") as file:
            read = csv.reader(file)
            expenses = list(read)

            if len(expenses) <= 1:
                print("No expense found")
                return

            for i, row in enumerate(expenses):
                if i == 0:
                    continue
                print(f" {row[0]}   |   {row[1]}   |   {row[2]}   |   {row[3]}")
          
        # read.readrow()

    except FileNotFoundError:
        print("No expense file found. Please add an expense first.")

def show_summary():
# contain sum all of the amounts, sum per category, 
    try:
        with open(filename, "r") as file:
            reader = csv.reader(file)
            expenses = list(reader)

        if len(expenses) <= 1:
            print("The expense is empty")
            return

        total = 0
        by_category = {}

        for i, row in enumerate(expenses):
            if i == 0: # skip header (table's tittle)
                continue

            amount = int(row[2])
            category = row[1]
            total += amount
            by_category[category] = by_category.get(category, 0) + amount

  
        print("\n--- Summary ---")
        print(f"Total expenses: {total}")
        print("By category:")
        for cat, amt in by_category.items():
            print(f"  {cat}: {amt}") 

        # optional: show highest spending category
        max_cat = max(by_category, key=by_category.get)
        print(f"\nBiggest spending category: {max_cat} ({by_category[max_cat]})")       

    except FileNotFoundError:
        print("No expense file found.")


def main():
    while True:
        print(" ==== EXPENSES TRACKER ==== ")
        print('''1. Add Expense
              2. View All Expenses
              3. Show Summary
              4. Save and Exit''')
        
        user = input("Select an option = ")

        if user == '1':
            add_expense()
        elif user == '2':
            view_expenses()
        elif user == '3':
            show_summary()
        elif user == '4':
            print("Thank you for using this app. Stay track your expenses wisely!!!")
            break
        else:
            print("Invalid input.")


main()
