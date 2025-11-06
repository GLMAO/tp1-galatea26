package org.emp.gl.timer.service;

import java.beans.PropertyChangeListener;

public interface TimerChangeListener extends PropertyChangeListener {
    // Cette interface hérite maintenant de PropertyChangeListener
    // Les constantes sont conservées pour la compatibilité
    final static String DIXEME_DE_SECONDE_PROP = "dixième";
    final static String SECONDE_PROP = "seconde";
    final static String MINUTE_PROP = "minute";
    final static String HEURE_PROP = "heure";
    
    // La méthode propertyChange est héritée de PropertyChangeListener :
    // void propertyChange(PropertyChangeEvent evt);
}