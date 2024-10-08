package com.example;

import java.util.ArrayList;

public class Entregues {
    private ObservableCollection<Producte> productes;

    public Entregues() {
        this.productes = new ObservableCollection<Producte>(new ArrayList<Producte>());
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

    public void removeProducte(Producte producte) {
        productes.remove(producte);
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

    public ObservableCollection<Producte> getObservableProductes() {
        return productes;
    }

    @Override
    public String toString() {
        return "Entregues [productes=" + productes.toString() + "]";
    }
}
