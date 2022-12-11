# versão 1 do programa de indexação de documentos
# @utor: maycon douglas batista dos santos - 11921bsi209
# nenhum bug encontado

import os
import re as Regex
import aux as uteis

# ler todos os documentos de uma pasta com a extenção indicada e
# salva na pasta de destino indicada
def bagOfWords2 (source = './docs', ext = '.txt' , dest = None):

    bag = []
    docs = []

    for dir, subPastas, arquivos in os.walk(source):
        
        for arquivo in arquivos:

            if Regex.findall(ext + '$', arquivo):

                arg = open(source + '/' + arquivo, 'r')

                arg = uteis.normaliza(arg.read())

                if(dest != None):
                    uteis.saveFile(arg, dest + '/' + arquivo)

                docs.append(arquivo)
                bag.append(arg)

    return bag, docs

# monta uma matrix mostrando mostrando 
def matrixBollean (bags, keyWords):

    matrix = []

    for bag in bags:
        matrix.append(uteis.listaBollean(bag, keyWords))

    return matrix

def exibirBOW(result, namesFiles, keyWords):

    print(f'Docs:\t{keyWords}')


    for i in range(len(result)):
        print(f'{namesFiles[i]}:\t{result[i]}')

# ler todos os docs de uma pasta e salva a bag of words da cada um em uma pasta index
if __name__ == '__main__':

    source = input('Entre com o caminho relativo dos arquivos que serão indexados: ')

    ext = input('Qual extenção dos arquivos que serão indexados: ')

    dest = input('Entre com o caminho relátivo de onde serão guardados: ')

    listaBOW, namesFiles = bagOfWords2 ( source , ext, dest)
    
    name = input('Agora entre com o arquivo com a busca: ')

    keyWords = uteis.normaliza(open(name).read())

    result = matrixBollean(listaBOW, keyWords)

    exibirBOW(result, namesFiles, keyWords)
