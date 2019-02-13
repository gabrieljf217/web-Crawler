# -*- coding: utf-8 -*-
#http://www.libroteca.net/Buscador-de-libros.asp
#https://grimpidev.wordpress.com/2008/04/03/soluciones-para-actualizar-un-registro-si-existe-sino-insertar-en-sql-server/
"""
Created on Mon Sep 25 16:12:03 2017
@author:
    for i in range(len(termifrecdoc)):
    id_termino=id_termino+1
    termino=termifrecdoc[i][0]
    frecuencia=termifrecdoc[i][1]
    documento=termifrecdoc[i][2]
    datos=(id_termino,termino,frecuencia,documento)
    cur.execute(inserttermifrecdoc,datos)
connection.commit()
"""
import time
import os
import mysql.connector
from nltk.corpus import stopwords
from nltk.tokenize import regexp_tokenize
from nltk.probability import FreqDist
from nltk.stem import PorterStemmer

#Tiempo de ejecucion
inicio=time.time()

# Open a file
texto=[] #arreglo donde se guarnda todos los textos 
tokenize=[] # texto toquenizado 
palabras=[] # palabras toquenizadas
ps = PorterStemmer() # funcion para convertir a singular una palabra ps.stem

stop=['aun','actualmente', 'acuerdo', 'adelante', 'ademas', 'además', 'adrede', 'afirmó',
      'agregó', 'ahi', 'ahora', 'ahí', 'alguna', 'alguno', 'algún', 'alli', 'allí',
      'alrededor', 'ambos', 'ampleamos', 'antano', 'antaño', 'anterior', 'apenas',
      'aproximadamente', 'aquel', 'aquella', 'aquellas', 'aquello', 'aquellos', 'aqui',
      'aquél', 'aquélla', 'aquéllas', 'aquéllos', 'aquí', 'arriba', 'arribaabajo', 'aseguró', 
      'asi', 'así', 'atras', 'aunque', 'ayer', 'añadió', 'aún', 'b', 'bajo', 'bastante', 
      'bien', 'breve', 'buen', 'buena', 'buenas', 'bueno', 'buenos', 'c', 'cada', 'casi', 
      'cerca', 'cierta', 'ciertas', 'cierto', 'ciertos', 'cinco', 'claro', 'comentó', 
      'conmigo', 'conocer', 'conseguimos', 'conseguir', 'considera', 'consideró', 'consigo',
      'consigue', 'consiguen', 'consigues', 'contigo', 'cosas', 'creo', 'cuales', 'cualquier',
      'cuanta', 'cuantas', 'cuanto', 'cuantos', 'cuatro', 'cuenta', 'cuál', 'cuáles', 'cuándo',
      'cuánta', 'cuántas', 'cuánto', 'cuántos', 'cómo', 'd', 'da', 'dado', 'dan', 'dar',
      'debajo', 'debe', 'deben', 'debido', 'decir', 'dejó', 'delante', 'demasiado', 'demás',
      'dentro', 'deprisa', 'despacio', 'despues', 'después', 'detras', 'detrás', 'dia', 'dias',
      'dice', 'dicen', 'dicho', 'dieron', 'diferente', 'diferentes', 'dijeron', 'dijo', 'dio',
      'dos', 'día', 'días', 'dónde', 'ejemplo', 'ello', 'embargo', 'empleais', 'emplean',
      'emplear', 'empleas', 'empleo', 'encima', 'encuentra', 'enfrente', 'enseguida',
      'entonces', 'eramos', 'estais', 'estan', 'ex', 'excepto', 'existe', 'existen',
      'explicó', 'expresó', 'f', 'fin', 'final', 'g', 'general', 'gran', 'grandes',
      'gueno', 'h', 'haber', 'habia', 'habla', 'hablan', 'hace', 'haceis', 'hacemos',
      'hacen', 'hacer', 'hacerlo', 'haces', 'hacia', 'haciendo', 'hago', 'hecho',
      'hicieron', 'hizo', 'horas', 'hoy', 'i', 'igual', 'incluso', 'indicó', 'informo',
      'informó', 'intenta', 'intentais', 'intentamos', 'intentan', 'intentar', 'intentas',
      'intento', 'ir', 'j', 'junto', 'k', 'l', 'lado', 'largo', 'lejos', 'llegó', 'lleva',
      'llevar', 'luego', 'lugar', 'm', 'mal', 'manera', 'manifestó', 'mas', 'mayor', 'mediante',
      'medio', 'mejor', 'mencionó', 'menos', 'menudo', 'mia', 'mias', 'mientras', 'mio', 'mios',
      'misma', 'mismas', 'mismo', 'mismos', 'modo', 'momento', 'mucha', 'muchas', 'n', 'nadie',
      'ninguna', 'ningunas', 'ninguno', 'ningunos', 'ningún', 'nueva', 'nuevas', 'nuevo',
      'nuevos', 'nunca', 'ocho', 'p', 'pais', 'parece', 'parte', 'partir', 'pasada', 'pasado',
      'paìs', 'peor', 'pesar', 'poca', 'pocas', 'pocos', 'podeis', 'podemos', 'poder', 
      'podria', 'podriais', 'podriamos', 'podrian', 'podrias', 'podrá', 'podrán', 'podría',
      'podrían', 'poner', 'posible', 'primer', 'primera', 'primero', 'primeros', 'principalmente',
      'pronto', 'propia', 'propias', 'propio', 'propios', 'proximo', 'próximo', 'próximos',
      'pudo', 'pueda', 'puede', 'pueden', 'puedo', 'pues', 'q', 'qeu', 'quedó', 'queremos',
      'quiere', 'quiza', 'quizas', 'quizá', 'quizás', 'quién', 'quiénes', 'r', 'raras',
      'realizado', 'realizar', 'realizó', 'repente', 'respecto', 's', 'sabe', 'sabeis',
      'sabemos', 'saben', 'saber', 'sabes', 'sal', 'salvo', 'segun', 'segunda', 'segundo',
      'según', 'seis', 'ser', 'sera', 'señaló', 'si', 'sido', 'siempre', 'siendo', 'siete',
      'sigue', 'siguiente', 'sino', 'sola', 'solamente', 'solas', 'solo', 'solos', 'soyos',
      'supuesto', 'sé', 'sólo', 't', 'tal', 'tambien', 'tampoco', 'tan', 'tarde', 'temprano',
      'teneis', 'tener', 'tercera', 'tiempo', 'toda', 'todas', 'todavia', 'todavía', 'total',
      'trabaja', 'trabajais', 'trabajamos', 'trabajan', 'trabajar', 'trabajas', 'trabajo', 'tras',
      'trata', 'través', 'tres', 'u', 'ultimo', 'unas', 'usa', 'usais', 'usamos', 'usan', 'usar',
      'usas', 'uso', 'usted', 'ustedes', 'v', 'va', 'vais', 'valor', 'vamos', 'van', 'varias',
      'varios', 'vaya', 'veces', 'ver', 'verdad', 'verdadera', 'verdadero', 'vez', 'vosotras',
      'vosotros', 'voy', 'w', 'x', 'z', 'ésa', 'ésas', 'ése', 'ésos', 'ésta', 'éstas', 'éste',
      'éstos', 'última', 'últimas', 'último', 'últimos','acá', 'ajena', 'ajeno', 'ajenos',
      'ajenas', 'alguná', 'algúno', 'algúnos', 'algúnas', 'allá', 'atrás', 'cabe', 'cualquiera',
      'cualquieras', 'cuan', 'dejar', 'demasiada', 'demasiados', 'demasiadas', 'empleáis', 'eses',
      'estes', 'etc', 'hacéis', 'intentáis', 'jamás', 'juntos', 'muchísima', 'muchísimos', 'muchísimo',
      'parecer', 'podéis', 'podrías', 'podríais', 'podríamos', 'querer', 'quienesquiera', 'quienquiera',
      'sabéis', 'sr', 'sra', 'sres', 'sta', 'tales', 'tanta', 'tantos', 'tomar', 'trabajáis', 'usáis','ñ','aa','aaa','aaaa']

def reemplazo(texto): # Funcion para eliminar caracteres especiales y convertir a minuscula una palabra
   caracter=['.', ',', '"', "'", '?', '!', ':', ';', '(', ')', '[', ']', '{', '}',
             '_','#','%','&','*','+','/','<','>','=','`','~','Ç','ç','æ','Æ','ø',
             '£','Ø','×','ƒ','└','┴','┬','├','─','┼','ã','╚','╔','╩','╦','╠','═',
             '╬','¤','ð','Ð','ı','┘','┌','█','▄','¦','▀',' ','ß','µ','þ','¯','´',
             '±­­­','‗','¾','¶','§','÷','¸','°','¨','·','¹','³','²','■','┐','¥','¢',
             '╝','╗','║','╣','©','┤','│','▓','▒','░','»','«','¡','¼','½','¬','®',
             '¿','º','ª','@','$','0','1','2','3','4','5','6','7','8','9']#caracteres especiales
   for i in range(len(texto)):
       texto[i]=texto[i].lower()
   for i in range(len(texto)):
       for j in range(len(caracter)):
           texto[i]=texto[i].replace(caracter[j]," ")
   return texto
 
path = "datos" # nombre de la carpeta 
dirs = os.listdir( path ) #nombre de documentos en la carpeta

#validar solo documentos .txt
direcc=[]# solo nombres de documentos .txt
for i in range(len(dirs)): 
    tit=dirs[i]  # nombre de un documento de la variable dirs
    exten=tit[-4:] # extensión del documento
    tof=('.txt' in exten) # validacion de .txt 
    if tof==True:
        direcc.append(tit)

titulos=[]
#Lectura de documentos
predir="datos/" # nombre de la carpeta que contiene los documentos
for i in range(len(direcc)):
    archivo = open(predir+direcc[i], encoding='latin-1')
    titulos.append(archivo.readline())
    texto.append(archivo.read())# nombre del documento abierto
    archivo.close()
#Guardar    
partedetexto=[] # parte de texto de un documento
for i in range(len(texto)):
    segmentodetexto=texto[i] # Segmento de texto es el texto de la variable texto
    partedetexto.append(segmentodetexto[0:150]+"...")
    
print("Numero de documentos :",len(direcc))  
#print(len(partedetexto))

reemplazo(texto)
#Tokenizar 
for i in range(len(texto)):
    tokenize.append(regexp_tokenize(texto[i],"[\w']+"))
articulos=stopwords.words("spanish")#+stopwords.words("english")#+stopwords.words("French"))
for i in range(len(stop)):
    articulos.append(stop[i])
#print(len(stop+articulos))
numpa=[]# numero de palabras de cada documento
cont=0 # contador
for  i in range(len(tokenize)):
    for w in tokenize[i]:
            if w not in articulos:
                palabras.append(w)
                cont=cont+1
    numpa.append(cont)
    cont=0        
#print(palabras)
#print(numpa)
termino=[] # palabras por documento
termifrecdoc=[] # matriz termino frecuencia documento
m=0 # contador 
k=1 # contador
for i in range(len(numpa)):
    n=numpa[i] # numero de palabras en la posicion i de numpa
    for j in range(n):
        termino.append(palabras[m+j])
    #lista de frecuencia de palabras
    termino.sort() 
    #print(termino)
    #print(len(termino))
    fd = FreqDist(termino) # frecuencia de una palabra de un documento
    frecpalab=list(fd.values()) # lista de frecuencias de cada palabra de un documento
    #print(frecpalab)
    #print(len(frecpalab))
    #lista de palabras sin repetir
    p=0 # contador
    listpalabras=[] # palabras sin repetir
    for i in range(len(frecpalab)):
        listpalabras.append(termino[p])
        p=p+frecpalab[i]
    #print(listpalabras)
    #crear matriz termino frecuencia documento
    for i in range(len(listpalabras)):
        termifrecdoc.append([listpalabras[i],frecpalab[i],k])
    k=k+1
    listpalabras=[]
    termino=[]
    frecpalab=[]
    m=m+n

termifrecdoc.sort()   
#for i in range(len(termifrecdoc)):
    #print(termifrecdoc[i])
print("Numero de palabras :",len(termifrecdoc))

final1=time.time() 
tiempo1=final1-inicio # tiempo del algoritmo
print("Tiempo del algoritmo :",tiempo1)
inicio1=time.time()
#Conectar la base de datos
connection=mysql.connector.connect(user='root',password='root',host='localhost',database='matriz') 
cur=connection.cursor()
#Comandos sql
borrardoc="""DELETE FROM documentos"""
borrartermifrecdoc="""DELETE FROM termino_frecuencia_documento"""
insertdocumento="""INSERT INTO documentos (document,titulo,texto) VALUES (%s,%s,%s)"""
inserttermifrecdoc="""INSERT INTO termino_frecuencia_documento (ID_termino,termino,frecuencia,documento) VALUES (%s,%s,%s,%s)"""

# borrar datos de la base de datos
cur.execute(borrartermifrecdoc)
connection.commit()
cur.execute(borrardoc)
connection.commit()
# variables para guardar los valores para cada tabla
titulo=""
textodoc=""
termino=""
id_termino=0
documento=0
frecuencia=""
"""
doc= open("documento.txt","w")
dad="LOAD DATA LOCAL INFILE 'C:/Users/David/Dropbox/ing/Semestre #8/Procesos estocasticos y simulacion/Proyecto/documento.txt' INTO TABLE documentos FIELDS TERMINATED BY ' ' LINES TERMINATED BY '\\n' (document,titulo,texto);"
for i in range(len(direcc)):
    documento=documento+1
    titulo=direcc[i]
    textodoc=partedetexto[i]
    doc.write(""+str(documento)+" "+str(titulo)+" "+str(textodoc)+"\r\n")
doc.close()
#cur.execute(dad)
#connection.commit() 
"""

for i in range(len(direcc)):
    documento=i+1
    titulo=titulos[i]
    textodoc=partedetexto[i]
    datos=(documento,titulo,textodoc)
    cur.execute(insertdocumento,datos)
connection.commit() 

mat= open("termino.txt","w",encoding='latin-1')
dat="LOAD DATA LOCAL INFILE 'C:/Users/David/Desktop/crawlerExample/termino.txt' INTO TABLE termino_frecuencia_documento FIELDS TERMINATED BY ' ' LINES TERMINATED BY '\n' (ID_termino,termino,frecuencia,documento);"
for k in range(len(termifrecdoc)):
    id_termino=id_termino+1
    w=termifrecdoc[k][0]
    h=termifrecdoc[k][1]
    x=termifrecdoc[k][2]
    mat.write(""+str(id_termino)+" "+w+" "+str(h)+" "+str(x)+"\r\n")
mat.close() 
cur.execute(dat)
connection.commit()

final=time.time()
tiempo2=final-inicio1
print("Tiempo Guardado :",tiempo2)

final=time.time()
tiempo=round(final-inicio,0)
print("Tiempo Final :",tiempo)

cur.close()
connection.close()





