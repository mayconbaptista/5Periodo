import TP2.Ex3.ex2 as ex2
import numpy



if __name__ == '__main__':

    print(numpy.linalg.norm([1,2,3,4,5,6]))

    keyWords = input('entre com nome do vocabulario: ')

    doc = input('entre com nome/caminho do documento: ')

    tf_idf, nomes = ex2.Ex2("/home/maycon/5periodo/ORI/TP2/keywords.txt", '/home/maycon/5periodo/ORI/TP2/docs')

    new =  ex2.normaliza(keyWords, repeat=True, ord=False)

    print(tf_idf)


