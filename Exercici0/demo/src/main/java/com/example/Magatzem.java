package com.example;

import java.util.ArrayList;

public class Magatzem {
    private ObservableCollection<Producte> productes;
    private Observable<Integer> capacitat;

    public Magatzem() {
        this.productes = new ObservableCollection<Producte>(new ArrayList<Producte>());
        this.capacitat = new Observable<Integer>(10);
    }
    
    public ArrayList<Producte> getProductes() {
        return productes.get();
    }

    public void setProductes(ArrayList<Producte> productes) {
        this.productes.set(productes);
    }

    public void addProducte(Producte producte) {
        productes.add(producte);
    }

    public void removeProducte(int id) {
        ArrayList<Producte> collection = this.productes.get();
        for (Producte producte : collection) {
            if (producte.getId() == id) {
                this.productes.remove(producte);
                return;
            }
        }
    }

    public void removeProducte(Producte producte) {
        productes.remove(producte);
    }

    public int getCapacitat() {
        return capacitat.get();
    }

    public void setCapacitat(int capacitat) {
        this.capacitat.set(capacitat);
    }

    public ObservableCollection<Producte> getObservableProductes() {
        return productes;
    }

    public Observable<Integer> getObservableCapacitat() {
        return capacitat;
    }
    
    @Override
    public String toString() {
        return "Magatzem [productes=" + productes.toString() + ", capacitat=" + (capacitat.get() - productes.get().size()) + "]";
    }
}
