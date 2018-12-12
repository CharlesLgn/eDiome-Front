package metier;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    private String pseudo;
    private LocalDate date;
    private String contenu;

    public Message(String pseudo, LocalDate date, String contenu) {
        this.pseudo = pseudo;
        this.date = date;
        this.contenu = contenu;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    @Override
    public String toString() {
        return  pseudo + ' ' + date +
                " - " + contenu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(pseudo, message.pseudo) &&
                Objects.equals(date, message.date) &&
                Objects.equals(contenu, message.contenu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pseudo, date, contenu);
    }
}
