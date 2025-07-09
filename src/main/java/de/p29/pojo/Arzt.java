package de.p29.pojo;

public class Arzt {
    private String id;
    private String name;
    private String telefon;
    private String fachgebiet;
    private String email;

    public Arzt() {
        
    }

    // Setter
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public void setFachgebiet(String fachgebiet) {
        this.fachgebiet = fachgebiet;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter
    public String getId() {
        return id;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getFachgebiet() {
        return fachgebiet;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " | " + fachgebiet + " | " + email;
    }
}
