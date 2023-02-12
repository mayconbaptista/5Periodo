# @utor: maycon douglas batista dos santos - 11921bsi209
# nenhum bug encontado
import os
import re as Regex
import unidecode
import math
import numpy
from more_itertools.recipes import unique_everseen

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


# calcula a frequencia de uma palavra na lista passasa como primeiro
# parametro (vocalulario indexado) na lista do segundo parametro (doc indexado) 
def frequencia (keyWords, lista):

    keyWords2 = list(unique_everseen(keyWords))
    
    result = []
    for key in keyWords2:

        result.append(lista.count(key))
    
    return result


# calcula a frequencia da lista de vocabulario (segundo parametro) na matrix
# que seria o nossa bag (segundo parametro) usando a função descrita acima
def TF (bag, keyWords):

    result = []
    
    for lista in bag:
        
        result.append(frequencia(keyWords, lista))

    return result

# recebe um TF e converte para sua variação 
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

# calcula o idf para cara termo (coluna) na minha matriz de frequencia
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

# pega cada valor da matris e multiplica pelo seu valor idf
def TF_IDF (tf, idf):

    result = []
    for lin in range(len(tf)):

        linha = []
        for col in range(len(tf[lin])):

            linha.append(round(float(tf[lin][col]) * float(idf[col]), 3))

        result.append(linha)
    
    return result

def processaConsulta (consulta, idf):

    lista = frequencia(consulta, consulta)

    tf = []
    for item in lista:

        if(item > 0):
            aux = 1 + math.log2(item)
        else:
            aux = 0
        
        tf.append(aux)

    result = []

    if(len(tf) != len(idf)):
        print("Erro tamanho do idf diferente da consulta")

    for i in range(len(tf)):
        result.append(tf[i]*idf[i])
    
    return result

def ex2():

    keyWords = open(input("entre com nome do arquivo vocabulário: ")).read()

    keyWords = normaliza(keyWords,repeat=True, ord=False)

    nomeDir = input("entre com nome/caminho do diretório: ")

    bag, nomes = bagOfWords(source=nomeDir)

    tf = TF(bag, keyWords)

    tf = TFvar(tf)
    
    idf = IDF(tf)
    
    final = TF_IDF(tf, idf)

    consulta = processaConsulta(keyWords, idf)

    return final, nomes, keyWords, consulta
