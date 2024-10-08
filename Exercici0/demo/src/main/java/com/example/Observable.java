package com.example;

import java.util.ArrayList;
import java.util.List;

abstract class PropertyEvent<T> {
    
}

class PropertyChangeEvent<T> extends PropertyEvent<T> {
    private T oldValue;
    private T newValue;

    public PropertyChangeEvent(T oldValue, T newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public T getOldValue() {
        return oldValue;
    }

    public T getNewValue() {
        return newValue;
    }
}

class CollectionAddEvent<T> extends PropertyEvent<T> {
    private ArrayList<T> oldCollection;
    private ArrayList<T> newCollection;
    private T addedValue;

    public CollectionAddEvent(ArrayList<T> oldCollection, ArrayList<T> newCollection, T addedValue) {
        this.oldCollection = oldCollection;
        this.newCollection = newCollection;
        this.addedValue = addedValue;
    }

    public ArrayList<T> getOldCollection() {
        return oldCollection;
    }

    public ArrayList<T> getNewCollection() {
        return newCollection;
    }
    
    public T getAddedValue() {
        return addedValue;
    }
}

class CollectionRemoveEvent<T> extends PropertyEvent<T> {
    private ArrayList<T> oldCollection;
    private ArrayList<T> newCollection;
    private T removedValue;

    public CollectionRemoveEvent(ArrayList<T> oldCollection, ArrayList<T> newCollection, T removedValue) {
        this.oldCollection = oldCollection;
        this.newCollection = newCollection;
        this.removedValue = removedValue;
    }

    public ArrayList<T> getOldCollection() {
        return oldCollection;
    }

    public ArrayList<T> getNewCollection() {
        return newCollection;
    }
    
    public T getRemovedValue() {
        return removedValue;
    }
}

interface PropertyChangeListener<T> {
    void propertyChange(PropertyChangeEvent<T> event);
}

interface CollectionAddListener<T> {
    void onCollectionAdd(CollectionAddEvent<T> event);
}

interface CollectionRemoveListener<T> {
    void onCollectionRemove(CollectionRemoveEvent<T> event);
}

public class Observable<T> {
    private List<PropertyChangeListener<T>> listeners;
    private T value;

    public Observable(T value) {
        this.listeners = new ArrayList<>();
        this.value = value;
    }

    public void addPropertyChangeListener(PropertyChangeListener<T> listener) {
        this.listeners.add(listener);
    }
    

    public T get() {
        return value;
    }

    public void set(T value) {
        T oldValue = this.value;
        this.value = value;
        for (PropertyChangeListener<T> listener : listeners) {
            listener.propertyChange(new PropertyChangeEvent<T>(oldValue, value));
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }
}

class ObservableCollection<T> extends Observable<ArrayList<T>> {
    private List<CollectionAddListener<T>> addListeners;
    private List<CollectionRemoveListener<T>> removeListeners;

    public ObservableCollection(ArrayList<T> value) {
        super(value);
        this.addListeners = new ArrayList<>();
        this.removeListeners = new ArrayList<>();
    }

    public void addCollectionAddListener(CollectionAddListener<T> listener) {
        this.addListeners.add(listener);
    }

    public void addCollectionRemoveListener(CollectionRemoveListener<T> listener) {
        this.removeListeners.add(listener);
    }

    public void add(T value) {
        ArrayList<T> oldCollection = this.get();
        ArrayList<T> newCollection = new ArrayList<>(oldCollection);
        newCollection.add(value);
        this.set(newCollection);
        for (CollectionAddListener<T> listener : addListeners) {
            listener.onCollectionAdd(new CollectionAddEvent<T>(oldCollection, newCollection, value));
        }
    }

    public void remove(T value) {
        ArrayList<T> oldCollection = this.get();
        ArrayList<T> newCollection = new ArrayList<>(oldCollection);
        newCollection.remove(value);
        this.set(newCollection);
        for (CollectionRemoveListener<T> listener : removeListeners) {
            listener.onCollectionRemove(new CollectionRemoveEvent<T>(oldCollection, newCollection, value));
        }
    }

    public void remove(int index) {
        ArrayList<T> oldCollection = this.get();
        T removedValue = oldCollection.remove(index);
        this.set(oldCollection);
        for (CollectionRemoveListener<T> listener : removeListeners) {
            listener.onCollectionRemove(new CollectionRemoveEvent<T>(oldCollection, oldCollection, removedValue));
        }
    }

    @Override
    public String toString() {
        return get().toString();
    }
}
