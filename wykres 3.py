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
with open('wykres3.txt') as f:
    for line in f:
        data=line.split()        
        x1.append(data[0])
        y1.append(data[1])
fig = plt.figure()
rect=fig.patch
rect.set_facecolor('white')

#axis 1
ax1 = fig.add_subplot(1,1,1, axisbg='white')
ax1.set_title(u'Zajętość kanaów',**title_font)
ax1.set_xlabel(u'Czas wystąpienia',**axis_font)
ax1.set_ylabel(u'Ile zdarzeń w kanale',**axis_font)
ax1.tick_params(axis='x', colors='black')
ax1.tick_params(axis='y', colors='black')
ax1.spines['bottom'].set_color('black')
ax1.xaxis.label.set_color('black')
ax1.yaxis.label.set_color('black')
#ax1.plot(x1,y1,'green',linewidth=5,marker='o',linestyle='',label='Zdarzenia typu I')
#ax1.bar(x1, y1,width=0.2,color='b',align='center')
#plt.legend(loc=9)
plt.ylim(-0.03,5)
'''
for i,j in zip(x1,y1):
    ax1.annotate(str(i),xy=(i,j+0.2),size =10)
'''

ax1.bar(x1,y1,width=0.05,color='b')
plt.show()


