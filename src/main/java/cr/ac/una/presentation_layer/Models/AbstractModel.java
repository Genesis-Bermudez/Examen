package cr.ac.una.presentation_layer.Models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/*
* AbstractModel, ejemplos de otros profesores
*/

public abstract class AbstractModel {
    protected PropertyChangeSupport propertyChangeSupport;

    public AbstractModel(){
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    protected void firePropertyChange(String propertyName) {
        propertyChangeSupport.firePropertyChange(propertyName, null, null);
    }
}