# Однесување и кориснички случаеви

Системот опфаќа неколку кориснички случаеви. Главните два се најавувањето на корисникот и одбирањето дали корисникот ќе бара паркинг според негова локација или според внесена 
адреса и самиот приказ на паркинг локации (резултат од пребарувањето).

1. Најавување на корисникот (или креирање на нов account)
Web Browserot праќа HTTP GET барање до серверот. Серверот враќа одговор - веб страна. Корисникот внесува податоци за своите корисничко име и лозинка, односно
се логира или регистрира (во зависност од тоа дали веќе има account) и со тоа праќа HTTP POST барање. Нашата апликација (controllerot) ги прифаќа 
проследените параметри и проверува дали корисникот постои (доколку се логирал) или креира нов account (доколку се регистрирал и доколку корисничкото име е 
слободно). 
Податоците на корисникот се преземаат/внесуваат во базата на податоци.
Во зависност од тоа дали корисникот ќе се логира/регистрира, серверот враќа HTTP одговор (почетна веб страна или соодветна порака за неуспешна реализација). 

2. Одбирање на услуга
Корисникот има избор на одбере дали ќе внесе град или локација околу која сака да бара паркинг. При одбирање на една од опциите, се праќа HTTP POST барање и 
серверот со примена на апликациска логика ги филтрира податоците преземени од базата и истите му ги враќа на корисникот преку соодветен темплејт.

