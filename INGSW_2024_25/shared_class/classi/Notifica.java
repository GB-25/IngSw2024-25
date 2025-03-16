package classi;

public class Notifica {
    private int id;
    private String destinatarioEmail;
    private String messaggio;
    private boolean letta;

    // 🔹 Costruttore per recupero dal DB
    public Notifica(int id, String destinatarioEmail, String messaggio, boolean letta) {
        this.id = id;
        this.destinatarioEmail = destinatarioEmail;
        this.messaggio = messaggio;
        this.letta = letta;
    }

    // 🔹 Costruttore per creazione nuova notifica (senza ID, il DB lo genera)
    public Notifica(String destinatarioEmail, String messaggio) {
        this.destinatarioEmail = destinatarioEmail;
        this.messaggio = messaggio;
        this.letta = false;
    }

    // Getters
    public int getId() { return id; }
    public String getDestinatarioEmail() { return destinatarioEmail; }
    public String getMessaggio() { return messaggio; }
    public boolean isLetta() { return letta; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setLetta(boolean letta) { this.letta = letta; }
}


