#include <stdio.h>
#include <string.h>

enum semana {Domingo, Segunda, Terca, Quarta, Quinta, Sexta};

int main (void)
{
    enum semana s;

    s = Domingo;
    printf("valor %d\n",s);
    s = Sexta;

    printf("valor %d\n",s);

    return 0;
}