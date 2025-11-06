package org.emp.gl.clients;

import org.emp.gl.timer.service.TimerService;
import org.emp.gl.timer.service.TimerChangeListener;
import java.beans.PropertyChangeEvent;

public class CompteARebours implements TimerChangeListener {
    private String name;
    private TimerService timerService;
    private int compteur;
    private boolean actif;

    public CompteARebours(String name, TimerService timerService, int valeurInitiale) {
        this.name = name;
        this.timerService = timerService;
        this.compteur = valeurInitiale;
        this.actif = true;
        this.timerService.addTimeChangeListener(this);
        System.out.println(name + " initialisé avec " + compteur + " secondes");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (TimerChangeListener.SECONDE_PROP.equals(evt.getPropertyName()) && actif) {
            if (compteur > 0) {
                compteur--;
                System.out.println(name + " : " + compteur);
                
                if (compteur == 0) {
                    System.out.println(name + " : TERMINÉ !");
                    desinscrire();
                }
            }
        }
    }

    private void desinscrire() {
        if (actif) {
            timerService.removeTimeChangeListener(this);
            actif = false;
            System.out.println(name + " désinscrit du TimerService");
        }
    }
}