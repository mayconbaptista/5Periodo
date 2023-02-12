import aux as Util
import numpy
import math

def normaGeral (matriz):

    lista = []
    for linha in matriz:

        valor = 0
        for item in linha:
            
            valor  += item **2
    
        lista.append(math.sqrt(valor))
    
    return lista

def produto(vet1, vet2):

    
    if(len(vet1) != len(vet2)):
        print("Erro no produto")

    valor = 0

    for i in range(len(vet1)):
        valor += vet1[i] * vet2[i]

    return valor 


# associa o nome do documento com o qual de similaridade dele
def SIM (tf_idf, nomes ,consulta):

    dicionario = dict()

    for i in range(len(tf_idf)):
        dicionario[nomes[i]] = produto(tf_idf[i], consulta) / (numpy.linalg.norm(tf_idf[i]) * numpy.linalg.norm(consulta))

    return dicionario

if __name__ == '__main__':

    tf_idf, nomes, keyWords ,consulta = Util.ex2()

    print(keyWords)
    for i in range(len(tf_idf)):
        
        print(f'{tf_idf[i]} -> {nomes[i]}')

    print(SIM(tf_idf, nomes, consulta))
    