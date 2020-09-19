# Projekt Hibernate

## Przygotowanie projektu
1. Sprawdź wersje Javy - jeśli jesteś poniżej 11, wtedy ściągnij JDK 11.
1. Sklonuj projekt na swój komputer - użyj git clone.
1. Zaimportuj projekt do Intellij IDEA - File -> New -> Project From Existing Sources
1. W `src/main/java/resources` otwórz plik `hibernate.properties` i sprawdź swoje ustawienia (użytkownik i hasło do mysql). Jeśli masz mysql 5, zmień dialect na: `hibernate.dialect=org.hibernate.dialect.MySQL5Dialect`
1. Sprawdź czy masz włączonego mysql.
1. W Intellij IDEA uruchom wszystkie testy - tylko jeden HibernateUtilTest powinnien być zielony. To jest test połączenia z bazą danych. Jeśli test nie przechodzi to zweryfikuj swoje parametry połączenia w `hibernate.properties`

## Projekt encji
![alt text](https://lh5.googleusercontent.com/ErlnAvvl29OtWy4XpTV2AEMz5wkpR3x91vBR7H1rBQnPw6s6inGVC1F_bRI7u73jkhQxIHmRpAqb-Q=w1871-h949-rw)

## Implementacja testów
1. Wszystkie klasy encji są puste i znajdują się w pakiecie: `pl.classroom.entity`. Musisz je sam zmapować poprzez adnotacje hibernate.
1. Gdy oznaczysz daną encję jako @Entity, pamiętaj aby ją dodać w HibernateUtil w metodzie `addAnnotatedClasses()` - tam znajdziesz zakomentowany przykład.
1. Wszystkie testy znajdują się w katalogu testowym w pakiecie `pl.classroom.entity`. Każdy test rozszerza `BaseEntityTest` - ta klasa zawiera już kod potrzebny do pobrania sesji hibernate oraz metodę pomocniczą `saveAndFlush`. Użyj tej metody gdy zapisujesz dane w testach.
1. Uzupełnianie encji zacznij zgodnie z kolejności testów. Pierwszy test to `_01_StudentTest`. Dodaj pola, metody i adnotacje do klasy `Student`, a potem przejdź do testu. Uzupełnij test - wszystkie przypadki muszą zaświecić się na zielono.
1. Po ukończeniu testów z klasą `Student` przejdź do kolejnego: `_02_LessonTest` i encji związanych z `Lesson` itd., aż do końca :)
