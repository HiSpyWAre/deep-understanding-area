def main():
    print('''MATH TOOLKIT
        1. Prime checker
        2. Factorial
        3. GCD (Great Common Divisor)
        4. LCM (Least Common Multiple)''')
    print("===================================")
    choose = int(input("Choose Math Function = "))
    

    if choose == 1:
        a = prime_check()
        print(f'Is it prime? {a}')
    elif choose == 2:
        factorial()
        
    elif choose == 3:
        gcd()
    elif choose == 4:
        lcm()
    else:
        print("Invalid input! Thank you! Have a nice day!")

def prime_check():
    num = int(input("Enter the number = "))
    
    if num == 1:
        return False
    if num == 2 or num == 3:
        return True
    if num % 2 == 0 or num % 3 == 0 :
        return False 

    i = 5
    while i * i <= num :
        if num % i == 0 or num % (i + 2) == 0:
            return False
        i += 6
    
    return True

def factorial():
    num = int(input("Enter the number = "))
    exp = 1
    for i in range(1, num+1, 1):
        exp *= i 
    print(f'Factorial of {num} is {exp}')

# factorial(num)

def gcd(): 
    num1 = int(input("Enter first number = "))
    num2 = int(input("Enter second number ="))

    test = min(num1, num2)
#gcd ga mungkin lbh besar dari angka terkecil inputan
    while test > 0: # looping test sampai 1
        if num1 % test == 0 and num2 % test == 0:
            break
        test -= 1

    return print(f'Great Common Divisor from {num1} and {num2} is {test}')

# gcd()

def lcm():
    num1 = int(input("Enter first number = "))
    num2 = int(input("Enter second number ="))

    maximum = max(num1, num2)
    minimum = min(num1, num2)
# lcm ga mungkin lbh kecil dari angka terbesar inputan
    for i in range(maximum, num1 * num2 +1, maximum):
        if i % minimum == 0:
            print(f"LCM of {num1} and {num2} is {i}")
            return i
    
    # print(f'Least Common Multiple from {num1} and {num2} is {num1 * num2}')
    # return num1 * num2

# lcm()
# result = lcm()
# print("LCM is = ", result)

main()