package org.example;

public class ArztBOX {
    private final String id;
    private final String name;
    private final String fachgebiet;
    private final String email;

    public ArztBOX(String id, String name, String fachgebiet, String email) {
        this.id = id;
        this.name = name;
        this.fachgebiet = fachgebiet;
        this.email = email;
    }

    public String getId() {
        return id;
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
