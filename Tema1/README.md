# GwentStone - TEMA1 - POO
Pentru implementarea acestei teme am avut nevoie de cunostintele
invatate la cursurile si laboratoarele de POO. A fost o tema grea dar
care mi s-a parut foarte interesanta si challenging.

La inceput nu am stiut cum sa abordez aceasta tema dar pe parcurs m-am 
folosit de experienta si cunostintele acumulate si am reusit sa termin
proiectul.

Am inceput prin a-mi scrie propriile clase pentru elementele jocului
si prin implementarea functiilor de baza pentru acestea.

Dupa ce am inteles cum sa fac afisarea cu ajutorul unui ObjectMapper
si a mai multor ObjectNode-uri, si cum sa copiez datele din clasele initiale,
finale, de input, partea de I/O era aproape completa, dar m-am hotarat
ulterior sa creez niste clase ajutatoare care sa imi simplifice mult
gestionarea output-ului si a erorilor.

Prima oara am scris aproape toata implementarea jocului curent in functia
Main ca sa inteleg care ar fi cea mai buna metoda de a aborda aceasta
tema.

Apoi am mutat functionalitatea jocului din Main in clasele create, cea mai
importanta devenind CurrentGame, in cadrul careia se executau majoritatea
functiilor si se imbinau restul claselor.

Am incercat sa fac implementarea functiilor de genul(placeCard, 
useEnvironmentCard, cardUsesAttack, useHeroAbility, dar nu numai) intr-un mod
intuitiv si cat mai simplu, urmand o ordine cronologica a evenimentelor 
(ex.: prima oara se verifica daca o carte poate ataca sau daca player-ul 
are suficienta mana pentru a o folosi. Daca nu, se afiseaza erorile aferente,
daca da, se pune cartea jos si apoi se scade din mana player-ului).

Clasa CurrentGame contine tabla de joc, cei doi playeri, informatiile necesare
jocului si statistici, precum si functiile care parseaza, executa si afiseaza
fiecare comanda a jocului. Pentru realizarea elementelor necesare jocului am
creat clasele Player si Card, pe care am extins-o in alte trei subclase:
Minion, Environment si Hero, pentru a folosi notiunile de OOP. Pentru
utilizarea polimorfismului m-am folosit de un camp special creat in clasa Card
folosit in locul testelor de genul 'instanceof'. Am incercat sa pastrez de-a
lungul proiectului incapsularea si sa gandesc logica programului intr-o
maniera cat mai simpla.

Am avut multe dificultati cu erorile provocate de utilizarea unor nume de
variabile asemanatoare, uitarea unui pas in ordinea cronologica a
evenimentelor si la inceput cu gasirea unui mod cat mai eficient de abordare
a acestui proiect, dar pana la final am rezolvat toate aceste probleme.

Aceasta tema m-a invatat foarte multe lucruri, de la utilizarea unui limbaj
relativ nou de programare destul de complex dar si puternic, abordarea unui
proiect de dimensiuni mai mari, bazele jocurilor pe calculator, programarea
cu ajutorul obiectelor si claselor, pana la un mai bun management al timpului. 
Personal, mi-a placut aceasta tema si consider ca efortul depus a meritat, a
fost un proiect interesant.