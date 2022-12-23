def func (s):

    lista = [False, False, False, False, False]

    for i in s:

        if str.isalnum(i):
            lista[0] = True
        if str.isalpha(i):
            lista[1] = True
        if str.isdigit(i):
            lista[2] = True
        if str.islower(i):
            lista[3] = True
        if str.isupper(i):
            lista[4] = True

    return lista




if __name__ == '__main__':

    s = input()
    
    for i in func(s):
        print(i)