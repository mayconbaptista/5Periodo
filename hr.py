from datetime import time

while True:

    h1, m1, h2, m2 = input().split(' ')

    if(h1 == 0 and m1 == 0 and h2 == 0 and m2 == 0):
        break


    
    if h1 > h2 or (h1 == h2 and m1 > m2):

        print("+ 12hrs")

    dt1 = time. (hour=h1,minute=m1, second=0)
    dt2 = datetime.time(hour=h2,minute=m2, second=0)
    
    print(dt2 - dt1) 