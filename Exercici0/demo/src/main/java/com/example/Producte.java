package com.example;

public class Producte {
    private Observable<Integer> id;
    private Observable<String> nom;
   

    public Producte(int id, String nom) {
        this.id = new Observable<Integer>(id);
        this.nom = new Observable<String>(nom);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getNom() {
        return nom.get();
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public Observable<Integer> getObservableId() {
        return id;
    }

    public Observable<String> getObservableNom() {
        return nom;
    }

    @Override
    public String toString() {
        return "Producte [id=" + id.get() + ", nom=" + nom.get() + "]";
    }
}
