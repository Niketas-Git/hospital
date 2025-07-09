package de.p29.pojo;

public class Patient {
    private String id;
    private String name;
    private String adresse;
    private String telefon;
    private String email;
    private String icd_key;
    private String bemerkung;

    public Patient() {
        
    }

    // Setter
    public void setId(String id) { 
        this.id = id; 
    }

    public void setName(String name) { 
        this.name = name; 
    }

    public void setAdresse(String adresse) { 
        this.adresse = adresse; 
    }

    public void setTelefon(String telefon) { 
        this.telefon = telefon; 
    }

    public void setEmail(String email) { 
        this.email = email; 
    }

    public void setIcdKey(String icd_key) { 
        this.icd_key = icd_key; 
    }

    public void setBemerkung(String bemerkung) { 
        this.bemerkung = bemerkung; 
    }

    // Getter
    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getIcdKey() {
        return icd_key;
    }

    public String getBemerkung() {
        return bemerkung;
    }
   
    @Override
    public String toString() {
        return name + " | " + email;
    }
}
