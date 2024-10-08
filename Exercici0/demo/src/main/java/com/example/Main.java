package com.example;

/*

Crea un programa “Main.java” que implementi la gestió d’un magatzem. Aquesta gestió es fa a través de 3 classes:

- **Producte**.java, que conté atributs privats: int id, String nom
- **Magatzem**.java, que conté atributs privats: ArrayList productes, int capacitat (inicialment 10)
- **Entregues**.java, que conté atributs privats: ArrayList productes

Cal que les classes tinguin definits els setters i getters, també cal que tinguin definides llistes de 
**‘PropertyChangeSupport’** per què les variables avisin en cas de ser modificades.

Com que els ArrayList són privats, caldrà implementar funcions **getProductes, addProducte, removeProducte**. 
Les dues últimes s’encarreguen d’avisar dels canvis a través de **‘removePropertyChangeListener’**. removeProducte 
accepta un ‘id’ de producte, per tant ha de recórrer la llista per saber l’index a esborrar.

Quan hi ha canvis:

- Quan hi ha canvis de **producte**, s’informa amb una cadena de text 
per pantalla mostrant l’antic valor i el nou valor, per exemple: 
“Producte ha canviat el nom de ‘llibre’ a ‘llibreta’”

- Quan hi ha canvis a la llista de **magatzem** es fan dos events diferents:
Un informa del canvi, per exemple:
	“S’ha afegit el producte amb id 4 al magatzem, capacitat: 8”
	“S’ha esborrat el producte amb id 4 del magatzem, capacitat: 9”

- En cas d’esborrar un producte del magatzem, el segon event l’ha d’afegir a les entregues:
	“S’ha mogut el producte amb id 4 del magatzem cap a les entregues”

- Quan hi ha canvis a la llista d’**entregues**, s’executa un event:
	“S’ha afegit el producte amb id 4 al la llista d’entregues”
	“S’ha entregat el producte amb id 4” (quan s’esborra)

Els noms de les propietats de cada event (dels listeners) seran:

- Per **producte**: producteId, producteName
- Per **magatzem**: magatzemAdd, magatzemRemove (magatzemEntrega)
- Per **entregues**: entreguesAdd, entreguesRemove

El codi main haurà de testejar això amb:

- Crear 5 productes, canviar l’id de un i el nom de 2
- Afegint 5 productes al magatzem
- Borrant 3 d’aquests productes (i que automàticament quedin a entregues)
- Entregant 2 d’aquests productes (esborrant-los de entregues)
- Llistant els productes del magatzem (funció toString al magatzem)
- Llistant els productes d’entregues (funció toString a entregues)
 */

public class Main {
    public static void main (String[] args) {

        Producte p0 = new Producte(0, "Llibre");
        Producte p1 = new Producte(1, "Boli");
        Producte p2 = new Producte(2, "Rotulador");
        Producte p3 = new Producte(3, "Carpeta");
        Producte p4 = new Producte(4, "Motxilla");

        Magatzem magatzem = new Magatzem();
        Entregues entregues = new Entregues();

        // Aquí afegir els property listeners adequats
        PropertyChangeListener<Integer> producteId = (event) -> System.out.println("Producte id changed from " + event.getOldValue() + " to " + event.getNewValue());
        PropertyChangeListener<String> producteNom = (event) -> System.out.println("Producte nom changed from " + event.getOldValue() + " to " + event.getNewValue());
       
        CollectionAddListener<Producte> magatzemAdd = (event) -> System.out.println("S’ha afegit el producte amb id " + event.getAddedValue().getId() + " al magatzem, capacitat: " + (magatzem.getCapacitat() - magatzem.getProductes().size()));
        CollectionRemoveListener<Producte> magatzemRemove = (event) -> System.out.println("S’ha esborrat el producte amb id " + event.getRemovedValue().getId() + " del magatzem, capacitat: " + (magatzem.getCapacitat() - magatzem.getProductes().size()));
        CollectionRemoveListener<Producte> magatzemEntrega = (event) -> {
            System.out.println("S’ha entregat el producte amb id " + event.getRemovedValue().getId());
            entregues.addProducte(event.getRemovedValue());
        };

        CollectionAddListener<Producte> entreguesAdd = (event) -> System.out.println("S’ha afegit el producte amb id " + event.getAddedValue().getId() + " a les entregues");
        CollectionRemoveListener<Producte> entreguesRemove = (event) -> System.out.println("S’ha entregat el producte amb id " + event.getRemovedValue().getId());


        p0.getObservableId().addPropertyChangeListener(producteId);
        p1.getObservableId().addPropertyChangeListener(producteId);
        p2.getObservableId().addPropertyChangeListener(producteId);
        p3.getObservableId().addPropertyChangeListener(producteId);
        p4.getObservableId().addPropertyChangeListener(producteId);

        p0.getObservableNom().addPropertyChangeListener(producteNom);
        p1.getObservableNom().addPropertyChangeListener(producteNom);
        p2.getObservableNom().addPropertyChangeListener(producteNom);
        p3.getObservableNom().addPropertyChangeListener(producteNom);
        p4.getObservableNom().addPropertyChangeListener(producteNom);

        magatzem.getObservableProductes().addCollectionAddListener(magatzemAdd);
        magatzem.getObservableProductes().addCollectionRemoveListener(magatzemRemove);
        magatzem.getObservableProductes().addCollectionRemoveListener(magatzemEntrega);

        entregues.getObservableProductes().addCollectionAddListener(entreguesAdd);
        entregues.getObservableProductes().addCollectionRemoveListener(entreguesRemove);

        p0.setId(5);
        p0.setNom("Llibreta");
        p1.setNom("Boli");

        magatzem.addProducte(p0);
        magatzem.addProducte(p1);
        magatzem.addProducte(p2);
        magatzem.addProducte(p3);
        magatzem.addProducte(p4);

        magatzem.removeProducte(2);
        magatzem.removeProducte(3);
        magatzem.removeProducte(4);

        entregues.removeProducte(2);
        entregues.removeProducte(3);

        System.out.println(magatzem);
        System.out.println(entregues);
   }
}