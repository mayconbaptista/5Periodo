import os
import re as Regex
import unidecode
import math
import numpy

def normaliza (words, repeat = False, ord = True):

    bag = []

    palavras = words.replace('\n', ' ').replace(',','').replace('.','').split(' ')

    for item in palavras:

        elem = unidecode.unidecode(item.lower())

        if elem != '':
            if elem not in bag and repeat == False:
                bag.append(elem)
            elif repeat == True:
                bag.append(elem)
    
    if ord == True:
        bag.sort()

    return bag

def bagOfWords(source = './docs', ext = '.txt' , dest = None):

    bag = []
    docs = []

    for dir, subPastas, arquivos in os.walk(source):
        
        for arquivo in arquivos:

            if Regex.findall(ext + '$', arquivo):

                arg = open(source + '/' + arquivo, 'r')

                arg = normaliza(arg.read(),repeat=True)

                #if(dest != None):
                    #uteis.saveFile(arg, dest + '/' + arquivo)

                docs.append(arquivo)
                bag.append(arg)

    return bag, docs

def frequencia (keyWords, lista):

    result = []
    for key in keyWords:

        result.append(lista.count(key))
    
    return result


def TF (bag, keyWords):

    result = []
    
    for lista in bag:
        
        result.append(frequencia(keyWords, lista))

    return result

def TFvar (tf):

    result = []
    for doc in tf:
        linha = []
        for item in doc:

            if(item > 0):
                aux = 1 + math.log2(item)
            else:
                aux = 0
            
            linha.append(aux)
        result.append(linha)

    return result


def IDF (tf):

    idf = []
    for i in range(len(tf[0])):

        idf.append(0)

    for i in range(len(tf)):
        for j in range(len(tf[i])):

            if(tf[i][j] > 0):
                idf[j] += 1
    
    for i in range(len(idf)):
        if idf[i] > 0:
            idf[i] = math.log2(len(tf) / idf[i])

    return idf

def TF_IDF (tf, idf):

    result = []
    for lin in range(len(tf)):

        linha = []
        for col in range(len(tf[lin])):

            linha.append(round(float(tf[lin][col]) * float(idf[col]), 3))

        result.append(linha)
    
    return result

def Ex2 (keyWords, nomeDir):

    # keyWords = open(input("entre com nome do arquivo vocabulário: ")).read()

    keyWords = normaliza(keyWords, ord=False)

    # nomeDir = input("entre com nome/caminho do diretório: ")

    bag, nomes = bagOfWords(source=nomeDir)

    tf = TF(bag, keyWords)

    tf = TFvar(tf)
    
    idf = IDF(tf)
    
    final = TF_IDF(tf, idf)

    return final, nomes, keyWords
    