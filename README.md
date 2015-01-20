BoardGame
=========

Projekt gry planszowej tworzony w ramach pracy inżynierskiej. Grą którą staraliśmy się przenieść na ekran jest
_SmallWorld_. Dodatkowo postanowiliśmy wzbogacić go o jedną z fanowskich modyfikacji, czyli generowanie mapy losowo z
 klocków o kształcie sześcioboku.


###Uruchomienie
Do zbudowania paczki zawierającej grę razem z wszystkimi potrzebnymi zasobami należy użyć gradlowego taska _zip_.
Zbuduje on wykonywalnego jara a następnie spakuje go razem z zasobami do archiwum zip.
Teraz wystarczy rozpakować je w miejscu z którego chcemy uruchomić grę.

Aby uruchomić projekt należy pamiętać o stworzeniu własnego pliku _boardgame.properties_ w katalogu _resources_.
Można wpomóc się przy tym odpowiednim plikiem _.sample_ znajdującym się już w tej lokalizaji.

Należy przy tym pamiętać, że do generowania mapy w sposób prawidłowy liczba
pól z których ma składać się mapa powinna być kwadratem liczby z zakresu 1-9.
Osobście polecamy wartość 25 lub 36. Pozwalają one na komfortową rozgrywkę 4 graczy,
jednocześnie utrzymując wysoki poziom rywalizacji.

Na większości systemów operacyjnych w celu uruchomienia gry wystarczy dwukrotnie kliknąć archiwum jar.
W przypadku gdy to nie zadziała, możemy uruchomić grę z wiersza poleceń komendą:
_java -jar SmallWorld.jar_

###Grupa osób tworzących projekt:
- Bartosz Sądel
- Dariusz Małkowski
- Michał Ziara