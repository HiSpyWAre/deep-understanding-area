# so, this isn't just about 'knowing', but also applying in real problem (basic to scale up!)
# also need to : fully understanding the syntax, the logic of solving problem behind the codes, and know whole of solution set that can be applied in some cases

print("CALCULATOR")

print("1. +  or 'add' ")
print("2. -  or 'substract' ")
print("3. x  or 'multiply' ")
print("4. /  or 'divide' ")

choose = input("Enter operator = ")

if choose == '1' or choose == '+' or choose == 'add':
    first_num = int(input("enter first number?"))
    second_num = int(input("enter second number?"))
    summation = first_num + second_num
    result = print("result = ",summation)

elif choose == '2' or choose == '-' or choose == 'substract':
    first_num = int(input("enter first number?"))
    second_num = int(input("enter second number?"))
    reduction = first_num - second_num
    result = print("result = ",reduction)

elif choose == '3' or choose == 'x' or choose =='multiply':
    try:
        first_num = int(input("enter first number?"))
        second_num = int(input("enter second number?"))
        product = first_num * second_num
        result = print("result = ",product)
    except Exception:
        print("Oops! there's something wrong!")

elif choose == '4' or choose == '/' or choose =='divide':
    try:
        first_num = int(input("enter first number?"))
        second_num = int(input("enter second number?"))
        distribution = first_num / second_num
        result = print("result = ", distribution)
    except ZeroDivisionError:
        print("Gabisa dibagi nol")

else :
    print("Invalid operator input.")