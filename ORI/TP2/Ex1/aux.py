import unidecode


def listaBollean (bagOfWords, keyWords):

    result = []

    for word in keyWords:
        if word in bagOfWords:
            result.append(1)
        else:
            result.append(0)

    return result

# lendo o arquivo passado parâmetro
def readStdin():

    bagOfWords = []

    while(True):

        try:
            linha = input().split(' ')
            
            for item in linha:
                
                elem = unidecode.unidecode(item.lower()).replace(',', '')

                if elem not in bagOfWords:
                    bagOfWords.append(elem)
        except:
            break
        
    return bagOfWords

def normaliza(words):

    bag = []

    palavras = words.replace('\n', ' ').replace(',','').split(' ')

    for item in palavras:
        elem = unidecode.unidecode(item.lower())

        if elem not in bag and elem != '':
            bag.append(elem)

    bag.sort()
    return bag

# recebe as lista de palavras ja normalizada e grava im arquivo
#  com extenção .index

def saveFile (bagOfWords, name):

    arg = open(name + '.index','w')

    for item in bagOfWords:
        arg.write(item + '\n')

