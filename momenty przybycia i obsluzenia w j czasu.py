#-*- coding: utf-8 -*-
import matplotlib.pyplot as plt
import numpy
import matplotlib.ticker as ticker

# Utworzenie slowników dla tytułu i osi
title_font = {'fontname':'Arial', 'size':'16', 'color':'black', 'weight':'bold',
              'verticalalignment':'bottom'} # Bottom vertical alignment for more space
axis_font = {'fontname':'Arial', 'size':'14'}


x1=[]
y1=[]
i=1.0
with open('wykres4.txt') as f:
    for line in f:
        data=line.split()        
        if(float(data[0]) <= float(i)):            
            y1.append(float(i))#zapisujemy wartosc jednostki czasu
            x1.append(float(data[0]))#czas zgloszenia w systemie    
        else:           
            i=i+1
            y1.append(float(i))
            x1.append(float(data[0]))
fig = plt.figure()
rect=fig.patch
rect.set_facecolor('white')

#axis 1
ax1 = fig.add_subplot(3,1,1, axisbg='white')
ax1.set_title(u'Zgłoszenia przyjęte do systemu',**title_font)
ax1.set_xlabel(u'Moment wystąpienia zdarzenia',**axis_font)
ax1.set_ylabel('Jednostki czasu',**axis_font)
ax1.tick_params(axis='x', colors='black')
ax1.tick_params(axis='y', colors='black')
ax1.spines['bottom'].set_color('black')
ax1.xaxis.label.set_color('black')
ax1.yaxis.label.set_color('black')
ax1.plot(x1,y1,'green',linewidth=5,marker='o',linestyle='',label='Zdarzenia typu I')
plt.legend(loc=9)
plt.ylim(0,i+1)

for i,j in zip(x1,y1):
    ax1.annotate(str(i),xy=(i,j+0.2),size =10)




x2=[]
y2=[]
i=1.0
with open('wykres5.txt') as f:
    for line in f:
        data=line.split()        
        if(float(data[0]) <= float(i)):            
            y2.append(float(i))#zapisujemy wartosc jednostki czasu
            x2.append(float(data[0]))#czas zgloszenia w systemie    
        else:           
            i=i+1
            y2.append(float(i))
            x2.append(float(data[0]))


#axis 1
ax2 = fig.add_subplot(3,1,3, axisbg='white')
ax2.set_title(u'Zgłoszenia obsłużone przez system',**title_font)
ax2.set_xlabel(u'Moment wystąpienia zdarzenia',**axis_font)
ax2.set_ylabel('Jednostki czasu',**axis_font)
ax2.tick_params(axis='x', colors='black')
ax2.tick_params(axis='y', colors='black')
ax2.spines['bottom'].set_color('black')
ax2.xaxis.label.set_color('black')
ax2.yaxis.label.set_color('black')
ax2.plot(x2,y2,'r',linewidth=1,marker='o',linestyle='None',label='Zdarzenia typu II')
plt.legend(loc=9)
plt.ylim(0,i+1)
#ax2.set_xticks(x2)
for i,j in zip(x2,y2):
    ax2.annotate(str(i),xy=(i,j+0.2),size=10)

plt.show()


