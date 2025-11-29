// ============================================
// PARTE 6: SISTEMA DE BATALLA GUI
// ============================================

import javax.swing.*;
import java.awt.*;

// Panel de Batalla
class BattlePanel extends JPanel {
    private Character jugador;
    private Character enemigo;
    private GameFacade facade;
    private Quest questAsociada;
    
    private JLabel lblJugadorNombre;
    private JLabel lblJugadorHP;
    private JProgressBar barraJugadorHP;
    
    private JLabel lblEnemigoNombre;
    private JLabel lblEnemigoHP;
    private JProgressBar barraEnemigoHP;
    
    private JButton btnAtacar;
    private JButton btnAtaquePoderoso;
    private JButton btnDescansar;
    private JButton btnHuir;
    
    private JTextArea txtBatallaLog;
    private JScrollPane scrollBatallaLog;
    
    private boolean turnoJugador;
    private Timer timerTurnoEnemigo;
    
    public BattlePanel(Character jugador, Character enemigo, GameFacade facade) {
        this(jugador, enemigo, facade, null);
    }
    
    public BattlePanel(Character jugador, Character enemigo, GameFacade facade, Quest quest) {
        this.jugador = jugador;
        this.enemigo = enemigo;
        this.facade = facade;
        this.questAsociada = quest;
        this.turnoJugador = true;
        
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(40, 40, 40));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        construirInterfaz();
        facade.iniciarBatalla(jugador, enemigo);
    }
    
    private void construirInterfaz() {
        // Panel superior: Titulo
        JLabel lblTitulo = new JLabel("BATALLA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(218, 165, 32));
        add(lblTitulo, BorderLayout.NORTH);
        
        // Panel central: Combatientes
        JPanel panelCombatientes = new JPanel(new GridLayout(1, 2, 30, 0));
        panelCombatientes.setBackground(new Color(40, 40, 40));
        
        // Panel del jugador
        JPanel panelJugador = crearPanelCombatiente(jugador, true);
        panelCombatientes.add(panelJugador);
        
        // Panel del enemigo
        JPanel panelEnemigo = crearPanelCombatiente(enemigo, false);
        panelCombatientes.add(panelEnemigo);
        
        add(panelCombatientes, BorderLayout.CENTER);
        
        // Panel inferior: Acciones y Log
        JPanel panelInferior = new JPanel(new BorderLayout(10, 10));
        panelInferior.setBackground(new Color(40, 40, 40));
        
        // Panel de acciones
        JPanel panelAcciones = new JPanel(new GridLayout(2, 2, 10, 10));
        panelAcciones.setBackground(new Color(50, 50, 50));
        panelAcciones.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(218, 165, 32), 2),
            "ACCIONES",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Serif", Font.BOLD, 16),
            new Color(218, 165, 32)
        ));
        panelAcciones.setPreferredSize(new Dimension(400, 150));
        
        btnAtacar = crearBotonAccion("Atacar");
        btnAtaquePoderoso = crearBotonAccion("Ataque Poderoso");
        btnDescansar = crearBotonAccion("Descansar");
        btnHuir = crearBotonAccion("Huir");
        
        btnAtacar.addActionListener(e -> ejecutarAccion("atacar"));
        btnAtaquePoderoso.addActionListener(e -> ejecutarAccion("ataque_poderoso"));
        btnDescansar.addActionListener(e -> ejecutarAccion("descansar"));
        btnHuir.addActionListener(e -> huirBatalla());
        
        panelAcciones.add(btnAtacar);
        panelAcciones.add(btnAtaquePoderoso);
        panelAcciones.add(btnDescansar);
        panelAcciones.add(btnHuir);
        
        panelInferior.add(panelAcciones, BorderLayout.WEST);
        
        // Log de batalla
        JPanel panelLog = new JPanel(new BorderLayout());
        panelLog.setBackground(new Color(50, 50, 50));
        panelLog.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(218, 165, 32), 2),
            "LOG DE BATALLA",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Serif", Font.BOLD, 16),
            new Color(218, 165, 32)
        ));
        
        txtBatallaLog = new JTextArea(6, 40);
        txtBatallaLog.setEditable(false);
        txtBatallaLog.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtBatallaLog.setBackground(new Color(20, 20, 20));
        txtBatallaLog.setForeground(new Color(0, 255, 0));
        txtBatallaLog.setLineWrap(true);
        txtBatallaLog.setWrapStyleWord(true);
        
        scrollBatallaLog = new JScrollPane(txtBatallaLog);
        panelLog.add(scrollBatallaLog, BorderLayout.CENTER);
        
        panelInferior.add(panelLog, BorderLayout.CENTER);
        
        add(panelInferior, BorderLayout.SOUTH);
        
        // Suscribirse a eventos para actualizar el log
        GameEventManager.getInstance().addListener(mensaje -> {
            SwingUtilities.invokeLater(() -> {
                txtBatallaLog.append(mensaje + "\n");
                txtBatallaLog.setCaretPosition(txtBatallaLog.getDocument().getLength());
            });
        });
    }
    
    private JPanel crearPanelCombatiente(Character personaje, boolean esJugador) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(50, 50, 50));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(218, 165, 32), 3),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // Nombre
        JLabel lblNombre = new JLabel(personaje.getNombre(), SwingConstants.CENTER);
        lblNombre.setFont(new Font("Serif", Font.BOLD, 22));
        lblNombre.setForeground(esJugador ? new Color(144, 238, 144) : new Color(255, 99, 71));
        lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblNombre);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        if (esJugador) {
            lblJugadorNombre = lblNombre;
        } else {
            lblEnemigoNombre = lblNombre;
        }
        
        // Clase
        JLabel lblClase = new JLabel(personaje.getClasePersonaje(), SwingConstants.CENTER);
        lblClase.setFont(new Font("Serif", Font.ITALIC, 16));
        lblClase.setForeground(Color.LIGHT_GRAY);
        lblClase.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblClase);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Nivel
        JLabel lblNivel = new JLabel("Nivel " + personaje.getNivel(), SwingConstants.CENTER);
        lblNivel.setFont(new Font("Serif", Font.PLAIN, 14));
        lblNivel.setForeground(Color.WHITE);
        lblNivel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblNivel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Barra de HP
        JLabel lblHP = new JLabel("HP: " + personaje.getSalud() + " / " + personaje.getSaludMaxima(), 
                                  SwingConstants.CENTER);
        lblHP.setFont(new Font("Serif", Font.BOLD, 16));
        lblHP.setForeground(Color.WHITE);
        lblHP.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblHP);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        
        if (esJugador) {
            lblJugadorHP = lblHP;
        } else {
            lblEnemigoHP = lblHP;
        }
        
        JProgressBar barraHP = new JProgressBar(0, personaje.getSaludMaxima());
        barraHP.setValue(personaje.getSalud());
        barraHP.setStringPainted(true);
        barraHP.setForeground(new Color(0, 200, 0));
        barraHP.setBackground(new Color(100, 0, 0));
        barraHP.setMaximumSize(new Dimension(250, 25));
        barraHP.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(barraHP);
        
        if (esJugador) {
            barraJugadorHP = barraHP;
        } else {
            barraEnemigoHP = barraHP;
        }
        
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Informacion adicional
        if (personaje instanceof Mage) {
            Mage mago = (Mage) personaje;
            JLabel lblMana = new JLabel("Mana: " + mago.getPuntosMana() + " / " + mago.getManaMaximo(), 
                                       SwingConstants.CENTER);
            lblMana.setFont(new Font("Serif", Font.PLAIN, 14));
            lblMana.setForeground(new Color(100, 149, 237));
            lblMana.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(lblMana);
        }
        
        if (personaje instanceof Archer) {
            Archer arquero = (Archer) personaje;
            JLabel lblFlechas = new JLabel("Flechas: " + arquero.getFlechasRestantes(), 
                                          SwingConstants.CENTER);
            lblFlechas.setFont(new Font("Serif", Font.PLAIN, 14));
            lblFlechas.setForeground(new Color(205, 133, 63));
            lblFlechas.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(lblFlechas);
        }
        
        return panel;
    }
    
    private JButton crearBotonAccion(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Serif", Font.BOLD, 14));
        btn.setBackground(new Color(70, 70, 70));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(218, 165, 32), 2));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (btn.isEnabled()) {
                    btn.setBackground(new Color(90, 90, 90));
                }
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(70, 70, 70));
            }
        });
        
        return btn;
    }
    
    private void ejecutarAccion(String accion) {
        if (!turnoJugador) return;
        
        deshabilitarBotones();
        
        boolean exitoso = facade.ejecutarTurnoJugador(jugador, enemigo, accion);
        
        if (exitoso) {
            actualizarEstado();
            
            if (facade.batallaTerminada(jugador, enemigo)) {
                finalizarBatalla();
            } else {
                turnoJugador = false;
                ejecutarTurnoEnemigoConDelay();
            }
        } else {
            habilitarBotones();
        }
    }
    
    private void ejecutarTurnoEnemigoConDelay() {
        timerTurnoEnemigo = new Timer(1500, e -> {
            facade.ejecutarTurnoEnemigo(enemigo, jugador);
            actualizarEstado();
            
            if (facade.batallaTerminada(jugador, enemigo)) {
                finalizarBatalla();
            } else {
                turnoJugador = true;
                habilitarBotones();
            }
            
            ((Timer) e.getSource()).stop();
        });
        timerTurnoEnemigo.setRepeats(false);
        timerTurnoEnemigo.start();
    }
    
    private void actualizarEstado() {
        // Actualizar jugador
        lblJugadorHP.setText("HP: " + jugador.getSalud() + " / " + jugador.getSaludMaxima());
        barraJugadorHP.setValue(jugador.getSalud());
        
        if (jugador.getSalud() < jugador.getSaludMaxima() * 0.3) {
            barraJugadorHP.setForeground(new Color(200, 0, 0));
        } else if (jugador.getSalud() < jugador.getSaludMaxima() * 0.6) {
            barraJugadorHP.setForeground(new Color(255, 165, 0));
        } else {
            barraJugadorHP.setForeground(new Color(0, 200, 0));
        }
        
        // Actualizar enemigo
        lblEnemigoHP.setText("HP: " + enemigo.getSalud() + " / " + enemigo.getSaludMaxima());
        barraEnemigoHP.setValue(enemigo.getSalud());
        
        if (enemigo.getSalud() < enemigo.getSaludMaxima() * 0.3) {
            barraEnemigoHP.setForeground(new Color(200, 0, 0));
        } else if (enemigo.getSalud() < enemigo.getSaludMaxima() * 0.6) {
            barraEnemigoHP.setForeground(new Color(255, 165, 0));
        } else {
            barraEnemigoHP.setForeground(new Color(0, 200, 0));
        }
    }
    
    private void finalizarBatalla() {
        deshabilitarBotones();
        
        Timer timer = new Timer(2000, e -> {
            if (!enemigo.estaVivo() && questAsociada != null) {
                facade.completarQuest(questAsociada);
            }
            
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (frame instanceof LOTRGame) {
                ((LOTRGame) frame).volverAlMenu();
            }
            ((Timer) e.getSource()).stop();
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    private void huirBatalla() {
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "Estas seguro de que quieres huir de la batalla?",
            "Huir",
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            GameEventManager.getInstance().notifyEvent(jugador.getNombre() + " huye de la batalla!");
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (frame instanceof LOTRGame) {
                ((LOTRGame) frame).volverAlMenu();
            }
        }
    }
    
    private void deshabilitarBotones() {
        btnAtacar.setEnabled(false);
        btnAtaquePoderoso.setEnabled(false);
        btnDescansar.setEnabled(false);
        btnHuir.setEnabled(false);
    }
    
    private void habilitarBotones() {
        btnAtacar.setEnabled(true);
        btnAtaquePoderoso.setEnabled(true);
        btnDescansar.setEnabled(true);
        btnHuir.setEnabled(true);
    }
}