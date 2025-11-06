package org.emp.gl.core.launcher;

import org.emp.gl.clients.HorlogeGUI;
import org.emp.gl.timer.service.TimerService;
import org.emp.gl.time.service.impl.DummyTimeServiceImpl;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        // Lancer l'horloge 
        lancerHorlogeStylee();
    }

    private static void lancerHorlogeStylee() {
        System.out.println("Lancement de l'Horloge ");
        
        TimerService timerService = new DummyTimeServiceImpl();
        
        SwingUtilities.invokeLater(() -> {
            HorlogeGUI horloge = new HorlogeGUI(timerService);
            horloge.setVisible(true);
            
        });
        
        // Garder l'application active
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}