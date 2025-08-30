import random
print("\nWELCOME TO 'NUMBER GUESSING GAME'")

try:
    with open(r'projectLadder\numberGuessGame\player.txt') as file:
        page = file.readlines()
        if len(page) >= 2:
            print(f"previous player = {page[-2].strip()}")
            print(f"total of trials = {page[-1].strip()}")
        else:
            print(f'COOL! YOURE THE FIRST PLAYER')

except FileNotFoundError:
    print("Player's history can't be founded! YOU'RE FIRST PLAYER!!!")


name = input(f"enter your name = ")
guess = random.randint(1, 100) 


for count_of_try in range(1, guess, 1):
    answer = int(input("Enter your answer = "))
    if answer > guess :
        print("Wrong answer! your input is too high")
    elif answer < guess :
        print("Wrong answer! your input is too low")
    else:
        print('You correct!!!')
        break

file = open(r'projectLadder\numberGuessGame\player.txt', 'a')
file.write(f'\n{name} \n{count_of_try}')
file.close()


# key-point file handling
# .read() = for read file and there are two methods; readline(return per each line) and read(return all of the line)
# .write() = if the file .txt doesn't exist, program will create it at the path you mean. and will remove the list of program before
# .append() = not remove, it'll nambahin selanjutnya
# doing two activities at once time = using w+, a+, r+