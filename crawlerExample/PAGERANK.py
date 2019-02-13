# -*- coding: utf-8 -*-
"""
Created on Tue Nov 21 20:16:43 2017

@author: darub
http://michaelnielsen.org/blog/using-your-laptop-to-compute-pagerank-for-millions-of-webpages/
"""
import numpy as np
import yaml 
from reportlab.lib.enums import TA_JUSTIFY
from reportlab.lib.pagesizes import letter
from reportlab.platypus import SimpleDocTemplate, Paragraph, Spacer, Image,PageBreak
from reportlab.lib.styles import getSampleStyleSheet, ParagraphStyle
import time
inicio=time.time()

def step(p,s=0.85):#s es una probabilidad 
    n = size
    v = np.matrix(np.zeros((n,1)))#vector de tamaño n de 0s
    inner_product = sum([p[j] for j in dangling_pages.keys()])#suma de los valores de p de dangling_pages
    for j in range(n):
        v[j] = s*sum([p[k]/number_out_links[k]
    for k in in_links[j]])+s*inner_product/n+(1-s)/n
    #sum([p[k]/number_out_links[k]for k in in_links[j]])
        #sumatoria del PageRank[k]/el # de links que salen[j]
    return v/np.sum(v)#divide el vector v sobre la suma del mismo y retorna el PR

def pagerank(s=0.85,tolerance=0.00001):#disminuyéndola para garantizar una tolerancia más apropiada para el número de páginas, por ejemplo 10 ^ {- 7}
  n = size  
  p = np.matrix(np.ones((n,1)))/n#vector de tamaño n de 1/n
  iteration = 1
  change = 2
  while change > tolerance:
    #print ("Iteration: %s" % iteration)
    new_p = step(p,s)#pagerank inicial
    change = np.sum(np.abs(p-new_p))#valor absoluto de la resta de p-new_p y se suma los valores del vector 
    #print ("Change: %s" % change)
    p = new_p#pagerank final
    iteration += 1
  return p

print("---Resultado PageRank---")

archivo = open("Matriz.txt","r") # nombre del documento abierto
size = archivo.readline()
#print(size)
size=int(size)
#print(type(size))

in_links = archivo.readline()
#print(in_links)
in_links=yaml.load(in_links)
#print(type(in_links))

number_out_links = archivo.readline()
#print(number_out_links)
number_out_links=yaml.load(number_out_links)
#print(type(number_out_links))

dangling_pages = archivo.readline()
#print(dangling_pages)
dangling_pages=yaml.load(dangling_pages)
#print(type(dangling_pages))
archivo.close()

"""
size = 10
in_links = {0: [], 1: [3, 4, 2, 8, 0, 7, 9, 6], 2: [3, 2, 6, 0, 9, 7, 1], 3: [], 4: [4], 5: [], 6: [], 7: [], 8: [], 9: []}
number_out_links = {0: 2, 1: 1, 2: 2, 3: 2, 4: 2, 5: 0, 6: 2, 7: 2, 8: 1, 9: 2}
dangling_pages = {5: True, 6: True}
"""
#print(size)
#print(in_links)
#print(number_out_links)
#print(dangling_pages)


# print(g.size)
# print("------------------")
# print(g.in_links)
# print("------------------")
# print(g.number_out_links)
# print("------------------")
# print(g.dangling_pages)


#------------------------------------------------------------------
#creación del pdf
doc = SimpleDocTemplate("PageRank.pdf",pagesize=letter,
                        rightMargin=72,leftMargin=72,
                        topMargin=72,bottomMargin=18)
Story=[]
logo = "img.jpg"

proyecto="PROYECTO FINAL ESTOCASTICOS Y SIMULACIÓN"
full_name = "Presentado por:"
D="David andres rubiano"
E="Ever Moreno"
A="Santiago Arias"
G="Gabriel Jimenez"
J="Jean Carlo Arévalo"
L="Leonardo Oliveros Coral"
C="Camila Castillo"
FECHA="25/11/2017"
Doc="Docente:"
R="Roger Enrique Guzman"
F="Facultad de Ingeniería"
im = Image(logo)
Story.append(im)
Story.append(Spacer(1, 20))

styles=getSampleStyleSheet()
styles.add(ParagraphStyle(name = "Justify",  alignment=TA_JUSTIFY, fontSize=20,
           fontName="Helvetica"))



Story.append(Spacer(1, 2))
ptext = '<font size=12>%s</font>' % proyecto
Story.append(Paragraph(ptext, styles["title"]))
Story.append(Spacer(1, 38))



ptext = '<font size=12>%s</font>' % Doc
Story.append(Paragraph(ptext, styles["title"]))  
Story.append(Spacer(1, 5))
ptext = '<font size=11 >%s</font>' % R
Story.append(Paragraph(ptext, styles["title"]))  
Story.append(Spacer(1, 42))

ptext = '<font size=12>%s</font>' % full_name
Story.append(Paragraph(ptext, styles["Title"]))  
Story.append(Spacer(1, 5))
ptext = '<font size=11>%s</font>' % D
Story.append(Paragraph(ptext, styles["Title"]))  
Story.append(Spacer(1, 5))
ptext = '<font size=11>%s</font>' % E
Story.append(Paragraph(ptext, styles["Title"]))  
Story.append(Spacer(1, 5))
ptext = '<font size=11>%s</font>' % A
Story.append(Paragraph(ptext, styles["title"]))  
Story.append(Spacer(1, 5))
ptext = '<font size=11>%s</font>' % G
Story.append(Paragraph(ptext, styles["title"]))  
Story.append(Spacer(1, 5))
ptext = '<font size=11>%s</font>' % J
Story.append(Paragraph(ptext, styles["title"]))  
Story.append(Spacer(1, 5))
ptext = '<font size=11>%s</font>' % L
Story.append(Paragraph(ptext, styles["title"]))  
Story.append(Spacer(1, 5))
ptext = '<font size=11>%s</font>' % C
Story.append(Paragraph(ptext, styles["title"]))  
Story.append(Spacer(1, 30))


ptext = '<font size=12>%s</font>' % F
Story.append(Paragraph(ptext, styles["title"]))  
Story.append(Spacer(1, 8))
ptext = '<font size=11>%s</font>' % FECHA
Story.append(Paragraph(ptext, styles["title"])) 
Story.append(Spacer(1, 12))
#-------------------------------------------------------------------------------------------------------
#siguiente pagina
Story.append(PageBreak())

Story.append(im)
Story.append(Spacer(1, 2))
ptext = '<font size=12>PAGERANK</font>'
Story.append(Paragraph(ptext, styles["title"]))
Story.append(Spacer(1, 17))


ptext = '<font size=12>Para el cálculo del PageRank es necesario numeración de cada una de las  páginas web 1, .....,n, como se observa en la siguiente imagen, Pues para cada número de página  n, hay un PageRank asociado qj que mide su importancia, El valor del PageRank es evaluado entre 0 y 1, entre más alto es su valor, más importante es la página.</font>'
Story.append(Paragraph(ptext, styles["Justify"]))
Story.append(Spacer(1, 15))
logo = "paginas.png"
im = Image(logo)
Story.append(im)
Story.append(Spacer(1, 14))


logo = "q.jpg"
im = Image(logo)
Story.append(im)
Story.append(Spacer(1, 18))
ptext = '<font size=12>El vector "q" contiene los valores calculados para cada pagina, este vector es una distribución de probabilidad, pues la suma de cada elemento del vector suma 1.</font>'
Story.append(Paragraph(ptext, styles["Justify"]))
Story.append(Spacer(1, 12))

ptext = '<font size=12>El cálculo del pageRank surge a raíz de la ejecución del web crawler el cual genera un archivo de texto llamado “Matriz.txt”, este contendrá el total de páginas recorridas por el crawler, para este caso un total, de %s de páginas y 3 diccionarios que se dividen de la siguiente manera: el primero contendrá los links de llegada para cada url, el segundo el número de links de salida que tiene una url y por último las páginas las cuales no contienen ninguna salida.</font>'%size
Story.append(Paragraph(ptext, styles["Justify"]))
Story.append(Spacer(1, 22))
logo = "ma1.jpg"
im = Image(logo)
Story.append(im)


##----------------------------OTRA PAGINA-------------------------------------------------------------
Story.append(PageBreak())

pr=pagerank()
prf=[]#page rank ordenado de mayor a menor
pr=pagerank()
pr=list(pr)
pr = np.matrix([[i, pr[i]] for i in range(size)], dtype=float)
pr.view('i8,i8').sort(order=['f1'], axis=0)#ordenar pr
print(pr)
prf=pr[::-1]
a=0
if size<20:
    a=size
if size>20:
    a=20;
ptext1 = '<font size=12>A continuación se podran observar los primeros %s,valores calculados del pageRank, se observara la llave asignada para cada url y el valor del pageRank. </font>'%a
Story.append(Paragraph(ptext1, styles["Normal"])) 
Story.append(Spacer(1, 20))


    
for i in range(a):
    
    ptext1 = '<font size=12>%s</font>'%prf[i]
    Story.append(Paragraph(ptext1, styles["Title"])) 
    Story.append(Spacer(1, 1))
    #print(prf[i])
#print(pr)
#print(prf)
print("------------------------")





final=time.time()
tiempo=round(final-inicio,0)
print("Tiempo Final :",tiempo)
ptext1 = '<font size=12>El Tiempo de ejecución del PageRank para %s pagina(s) fue de: %s seg.</font>'%(size,tiempo)
Story.append(Paragraph(ptext1, styles["Normal"])) 
Story.append(Spacer(1, 6))


 #------------------------------------------
#referencias
Story.append(PageBreak())

ptext = '<font size=12>REFERENCIAS</font>'
Story.append(Paragraph(ptext, styles["title"]))
Story.append(Spacer(1, 28))

ptext = '<font size=12>• http://michaelnielsen.org/blog/using-your-laptop-to-compute-pagerank-for-millions-of-webpages/  </font>'
Story.append(Paragraph(ptext, styles["Normal"])) 
Story.append(Spacer(1, 22))
ptext = '<font size=12>• http://www.reportlab.com/  </font>'
Story.append(Paragraph(ptext, styles["Normal"])) 
Story.append(Spacer(1, 22))
ptext = '<font size=12>•https://www.humanlevel.com/diccionario-marketing-online/pagerank-google  </font>'
Story.append(Paragraph(ptext, styles["Normal"])) 
Story.append(Spacer(1, 22))
 
doc.build(Story)


archivo = open("PageRank.txt","w")
for i in pr:
    archivo.write(str(i)+"\n")
archivo.close()






