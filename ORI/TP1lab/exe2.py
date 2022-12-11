import unidecode

import exe1 as funcUteis

def listaBollean (bagOfWords, keyWords):

    result = []

    for word in keyWords:
        if word in bagOfWords:
            result.append(1)
        else:
            result.append(0)

    return result

if __name__ == '__main__':

    argTXT = input('Entre o nome do arquivo : ')
    
    words = input('Entre com o nome do arquivo com as palavras a seres buscadas: ')

    argTXT = funcUteis.indexa(argTXT)
    words = funcUteis.indexa(words)

    print(listaBollean(argTXT, words))

