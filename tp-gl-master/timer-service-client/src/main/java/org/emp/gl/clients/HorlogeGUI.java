package org.emp.gl.clients;

import org.emp.gl.timer.service.TimerService;
import org.emp.gl.timer.service.TimerChangeListener;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeEvent;

public class HorlogeGUI extends JFrame implements TimerChangeListener {
    private TimerService timerService;
    private JLabel labelHeure;
    private JLabel labelSecondes;
    private JLabel labelDate;
    private JLabel labelTitre;

    // Couleurs modernes
    private final Color COULEUR_FOND = new Color(25, 25, 35);
    private final Color COULEUR_PRINCIPALE = new Color(0, 150, 255);
    private final Color COULEUR_SECONDAIRE = new Color(100, 200, 255);
    private final Color COULEUR_TEXTE = new Color(240, 240, 240);

    public HorlogeGUI(TimerService timerService) {
        this.timerService = timerService;
        this.timerService.addTimeChangeListener(this);
        
        // Configuration de la fenêtre
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        
        initializeUI();
        updateTime();
        
        // Arrondir les coins de la fenêtre
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
    }

    private void initializeUI() {
        // Panel principal avec fond arrondi
        JPanel panelPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Fond avec dégradé
                GradientPaint gradient = new GradientPaint(0, 0, COULEUR_FOND, 0, getHeight(), new Color(15, 15, 25));
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                
                // Bordure lumineuse
                g2d.setStroke(new BasicStroke(2));
                g2d.setColor(COULEUR_PRINCIPALE);
                g2d.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 30, 30);
            }
        };
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panelPrincipal.setOpaque(false);

        // Titre stylé
        labelTitre = new JLabel("HORLOGE DIGITALE", SwingConstants.CENTER);
        labelTitre.setFont(new Font("Segoe UI", Font.BOLD, 16));
        labelTitre.setForeground(COULEUR_SECONDAIRE);
        labelTitre.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Heure principale (heures et minutes)
        labelHeure = new JLabel("00:00", SwingConstants.CENTER);
        labelHeure.setFont(new Font("Segoe UI", Font.BOLD, 72));
        labelHeure.setForeground(COULEUR_PRINCIPALE);

        // Secondes (plus petites, à côté)
        labelSecondes = new JLabel("00", SwingConstants.CENTER);
        labelSecondes.setFont(new Font("Segoe UI", Font.BOLD, 24));
        labelSecondes.setForeground(COULEUR_SECONDAIRE);
        labelSecondes.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Date stylée
        labelDate = new JLabel(getDateFormatee(), SwingConstants.CENTER);
        labelDate.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        labelDate.setForeground(COULEUR_TEXTE);
        labelDate.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Panel pour l'heure et les secondes
        JPanel panelHeure = new JPanel(new BorderLayout());
        panelHeure.setOpaque(false);
        panelHeure.add(labelHeure, BorderLayout.CENTER);
        
        JPanel panelSecondes = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panelSecondes.setOpaque(false);
        panelSecondes.add(labelSecondes);
        
        JPanel panelTemps = new JPanel(new BorderLayout());
        panelTemps.setOpaque(false);
        panelTemps.add(panelHeure, BorderLayout.CENTER);
        panelTemps.add(panelSecondes, BorderLayout.SOUTH);

        // Assemblage
        panelPrincipal.add(labelTitre, BorderLayout.NORTH);
        panelPrincipal.add(panelTemps, BorderLayout.CENTER);
        panelPrincipal.add(labelDate, BorderLayout.SOUTH);

        // Bouton de fermeture stylé
        JButton boutonFermer = new JButton("X");
        boutonFermer.setPreferredSize(new Dimension(30, 30));
        boutonFermer.setBackground(new Color(255, 60, 60));
        boutonFermer.setForeground(Color.WHITE);
        boutonFermer.setBorder(BorderFactory.createEmptyBorder());
        boutonFermer.setFocusPainted(false);
        boutonFermer.addActionListener(e -> System.exit(0));
        
        JPanel panelBouton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBouton.setOpaque(false);
        panelBouton.add(boutonFermer);
        
        panelPrincipal.add(panelBouton, BorderLayout.NORTH);

        add(panelPrincipal);

        // Ajouter la possibilité de déplacer la fenêtre
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent e) {
                // Nécessaire pour le drag
            }
        });
        
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent e) {
                setLocation(e.getXOnScreen() - getWidth()/2, e.getYOnScreen() - getHeight()/2);
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (TimerChangeListener.SECONDE_PROP.equals(evt.getPropertyName())) {
            SwingUtilities.invokeLater(this::updateTime);
        }
    }

    private void updateTime() {
        if (timerService != null) {
            // Heure et minutes en grand
            String heureMinutes = String.format("%02d:%02d",
                    timerService.getHeures(),
                    timerService.getMinutes());
            
            // Secondes séparées
            String secondes = String.format("%02d",
                    timerService.getSecondes());
            
            labelHeure.setText(heureMinutes);
            labelSecondes.setText(secondes + "s");
            labelDate.setText(getDateFormatee());
        }
    }

    private String getDateFormatee() {
        java.time.LocalDate today = java.time.LocalDate.now();
        java.time.format.DateTimeFormatter formatter = 
            java.time.format.DateTimeFormatter.ofPattern("EEEE d MMMM yyyy", java.util.Locale.FRENCH);
        return today.format(formatter).toUpperCase();
    }
}