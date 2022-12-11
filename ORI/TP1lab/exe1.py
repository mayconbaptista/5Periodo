# rode usando -> python3 exe1.py < in.txt

import unidecode

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

def normaliza(arg):

    bag = []
    palavras = arg.split('\n')

    for item1 in palavras:

        palavra = item1.split(' ')

        for item2 in palavra:
            
            elem = unidecode.unidecode(item2.lower()).replace(',', '')

            if elem not in bag and elem != '':
                bag.append(elem)

    bag.sort()
    return bag

def indexa (name):

    try:
    
        arg = open(name, 'r').read()
        return normaliza(arg)
    except:
        print('Erro ao abrir o arquivo ' + name)
        exit()

def saveIndex (bagOfWords, name):

    name = name.replace('txt', 'index')
    arg = open(name,'w')

    for item in bagOfWords:
        arg.write(item + '\n')


if __name__ == '__main__':

    name = input('Digite o nome do arquivo: ')

    bagOfWords = indexa(name)
    print(bagOfWords)
    saveIndex(bagOfWords, name)

    print('Processo concluido com sucesso')
    