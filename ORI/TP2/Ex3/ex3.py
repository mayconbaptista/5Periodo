# @utor: maycon douglas batista dos santos - 11921bsi209
# nenhum bug encontado

import ex2 as uteis
import sys

# recebe um doc (td*idf) valor do menor peso e nomes dos arquivos lidos
# considerando que o doc e o nomes tem o mesmo tamanho e a 
# posição i nos nomes indicada se refere a no doc pelo valor (tf*idf)
def menorPeso (doc, valor, nomes):

    lista = []
    for i in range(len(doc)):

        if valor == doc[i]:
            lista.append(nomes[i])
    return lista


# apresentação do exe 3
if __name__ == '__main__':

    keyWords = open(input("entre com nome do arquivo vocabulário: ")).read()

    nomeDir = input("entre com nome/caminho do diretório: ")

    td_idf, nomes, voc = uteis.Ex2(keyWords, nomeDir)

    print(f'O tamaho do vocbulário é : {len(voc)}')

    print(f'vocabulário: {voc}')
    for nome in nomes:
        print(f'doc {nome} tf*idf é :')

        for doc in td_idf:
            print(doc)

            menor = min(doc)

            lista = menorPeso(doc, menor, voc)
            
            print(f'menor valor {menor} com os termos {lista}')


