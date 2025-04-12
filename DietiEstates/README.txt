Su Windows, effettuare la build tramite "mvn clean install" in una directory apposita da linea di comando(spesso la presenza di determinati caratteri nel percorso 
provoca errore nella build, anche se risulta corretta); onde evitare ciò, usare una cartella ad hoc per contenere il progetto e da linea di 
comando effettuare la build all'interno di essa. 
Da Linux non dovrebbe dare questi problemi, ma è preferibile usare una directory separata.

Fatto ciò, aprire l'IDE (consigliamo Eclipse), ed importare  il progetto tramite File > Import...> Maven > Existing Maven Project 
e selezionare la cartella contenente il progetto.

Una volta fatto, navigare all'interno della cartella src/main/java e nel pacchetto client.controller, avviare la classe Controller.

Il Server è al momento spento e verrà avviato appositamente per la dimostrazione pratica durante la discussione. 
Prima di allora, le funzionalità dell'applicazione potrebbero non funzionare come voluto. 
