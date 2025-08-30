
print(f"==== MULTIPLICATION TABLE GENERATOR ====")
# BASIC
n = int(input("Enter multiplier :")) # first multiplicator
m = int(input("Enter the end of range:")) 
# fix_num = 10

for fix_num in range(1, m+1):
    print(n, 'x', fix_num, '=', n*fix_num)

# plus (2 free variables)
n = int(input("Enter the beginning of multiplier :")) # first multiplicator
m = int(input("Enter the end of multiplier:")) 

for free_num in range(1, n+1):
    for fix_num in range(1, m+1):
        print(free_num, 'x', fix_num, '=', free_num*fix_num)