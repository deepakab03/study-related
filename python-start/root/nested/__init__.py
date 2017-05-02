def fionDefault():
    a, b = 0,1;
    while b < 10:
        print (b, end=',')
        a, b = b, a+b
        
    print ("\nthe value of b was finally", b)

def forLoops():
    words = ['hi', 'hello', 'and', 'good-bye']
    for w in words:
        print (w, len(w))
    
def ask_ok(prompt="Please enter value:", retries=3, reminder="please try again"):
    ok = input(prompt)
    if ok in ('y' or 'yes'):
        return True
    elif ok in ('n' or 'no'):
        return False
    retries -= 1
    if (retries <= 0):
        raise ValueError("Too many tries")
    print (reminder)
    

#ask_ok()
#ask_ok("yeye")

#function where default value is reused in every call
def f(a, L=[]):
    L.append(a)
    return L

#print(f(1))
#print(f(2))
#print(f(3))

#function where default value is reinit ever call
def f2(a, L=None):
    if L is None:
        L = []
    L.append(a)
    return L

#print(f2(1))
#print(f2(2))
#print(f2(3))


pairs = [(5, 'five'), (1, 'one'), (2, 'two'), (3, 'three'), (4, 'four')]
pairs.sort(key=lambda pair: pair[1])
print (pairs)