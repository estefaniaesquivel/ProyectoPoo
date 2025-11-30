// ============================================
// PARTE 7 FINAL: INTEGRACION COMPLETA
// ============================================

import javax.swing.*;
import java.awt.*;

// Clase LOTRGame completa con todos los metodos integrados
public class LOTRGameComplete extends JFrame implements GameEventListener {
    private GameFacade gameFacade;
    private Character jugador;
    
    private JPanel panelPrincipal;
    private JPanel panelSuperior;
    private JPanel panelIzquierdo;
    private JPanel panelCentral;
    private JPanel panelDerecho;
    private JPanel panelInferior;
    
    private JLabel lblNombreJuego;
    private JLabel lblNombreJugador;
    private JLabel lblNivel;
    private JLabel lblSalud;
    private JLabel lblExperiencia;
    
    private JTextArea txtConsola;
    private JScrollPane scrollConsola;
    
    private JButton btnExplorar;
    private JButton btnInventario;
    private JButton btnQuests;
    private JButton btnStats;
    private JButton btnGuardar;
    private JButton btnCargar;
    private JButton btnSalir;
    
    private CardLayout cardLayout;
    private JPanel panelContenido;
    
    public LOTRGameComplete() {
        gameFacade = GameFacade.getInstance();
        GameEventManager.getInstance().addListener(this);
        
        inicializarVentana();
        mostrarPantallaSeleccionPersonaje();
    }
    
    private void inicializarVentana() {
        setTitle("El Senor de los Anillos - RPG");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelPrincipal.setBackground(new Color(40, 40, 40));
        
        add(panelPrincipal);
    }
    
    private void mostrarPantallaSeleccionPersonaje() {
        JPanel panelSeleccion = new JPanel(new GridBagLayout());
        panelSeleccion.setBackground(new Color(40, 40, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel lblTitulo = new JLabel("EL SENOR DE LOS ANILLOS");
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 36));
        lblTitulo.setForeground(new Color(218, 165, 32));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        panelSeleccion.add(lblTitulo, gbc);
        
        JLabel lblSubtitulo = new JLabel("Selecciona tu heroe");
        lblSubtitulo.setFont(new Font("Serif", Font.PLAIN, 20));
        lblSubtitulo.setForeground(Color.WHITE);
        gbc.gridy = 1;
        panelSeleccion.add(lblSubtitulo, gbc);
        
        JButton btnAragorn = crearBotonPersonaje("Aragorn", "Guerrero", 
            "HP: 150 | Fuerza de Espada: 30");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panelSeleccion.add(btnAragorn, gbc);
        btnAragorn.addActionListener(e -> {
            jugador = gameFacade.crearPersonaje("warrior", "Aragorn", 5);
            iniciarJuego();
        });
        
        JButton btnGandalf = crearBotonPersonaje("Gandalf", "Mago", 
            "HP: 98 | Mana: 110 | Poder Magico: 36");
        gbc.gridx = 1;
        panelSeleccion.add(btnGandalf, gbc);
        btnGandalf.addActionListener(e -> {
            jugador = gameFacade.crearPersonaje("mage", "Gandalf", 4);
            iniciarJuego();
        });
        
        JButton btnLegolas = crearBotonPersonaje("Legolas", "Arquero", 
            "HP: 128 | Precision: 24 | Flechas: 60");
        gbc.gridx = 2;
        panelSeleccion.add(btnLegolas, gbc);
        btnLegolas.addActionListener(e -> {
            jugador = gameFacade.crearPersonaje("archer", "Legolas", 6);
            iniciarJuego();
        });
        
        panelPrincipal.add(panelSeleccion, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    
    private JButton crearBotonPersonaje(String nombre, String clase, String stats) {
        JButton btn = new JButton("<html><center><b>" + nombre + "</b><br>" + 
                                   clase + "<br><i>" + stats + "</i></center></html>");
        btn.setPreferredSize(new Dimension(200, 120));
        btn.setFont(new Font("Serif", Font.PLAIN, 14));
        btn.setBackground(new Color(60, 60, 60));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(218, 165, 32), 2));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(80, 80, 80));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(60, 60, 60));
            }
        });
        
        return btn;
    }
    
    private void iniciarJuego() {
        panelPrincipal.removeAll();
        gameFacade.inicializarJuego(jugador);
        
        construirInterfazPrincipal();
        actualizarEstadoJugador();
        
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }
    
    private void construirInterfazPrincipal() {
        construirPanelSuperior();
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        
        construirPanelIzquierdo();
        panelPrincipal.add(panelIzquierdo, BorderLayout.WEST);
        
        construirPanelCentral();
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        
        construirPanelDerecho();
        panelPrincipal.add(panelDerecho, BorderLayout.EAST);
        
        construirPanelInferior();
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
        
        mostrarPanelExploracion();
    }
    
    private void construirPanelSuperior() {
        panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(30, 30, 30));
        panelSuperior.setBorder(BorderFactory.createLineBorder(new Color(218, 165, 32), 2));
        panelSuperior.setPreferredSize(new Dimension(0, 80));
        
        lblNombreJuego = new JLabel("EL SENOR DE LOS ANILLOS", SwingConstants.CENTER);
        lblNombreJuego.setFont(new Font("Serif", Font.BOLD, 28));
        lblNombreJuego.setForeground(new Color(218, 165, 32));
        panelSuperior.add(lblNombreJuego, BorderLayout.CENTER);
        
        JPanel panelStats = new JPanel(new GridLayout(2, 2, 5, 5));
        panelStats.setBackground(new Color(30, 30, 30));
        panelStats.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        lblNombreJugador = crearLabel("");
        lblNivel = crearLabel("");
        lblSalud = crearLabel("");
        lblExperiencia = crearLabel("");
        
        panelStats.add(lblNombreJugador);
        panelStats.add(lblNivel);
        panelStats.add(lblSalud);
        panelStats.add(lblExperiencia);
        
        panelSuperior.add(panelStats, BorderLayout.EAST);
    }
    
    private void construirPanelIzquierdo() {
        panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setBackground(new Color(50, 50, 50));
        panelIzquierdo.setBorder(BorderFactory.createLineBorder(new Color(218, 165, 32), 2));
        panelIzquierdo.setPreferredSize(new Dimension(200, 0));
        
        JLabel lblMenu = new JLabel("MENU", SwingConstants.CENTER);
        lblMenu.setFont(new Font("Serif", Font.BOLD, 20));
        lblMenu.setForeground(new Color(218, 165, 32));
        lblMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelIzquierdo.add(Box.createRigidArea(new Dimension(0, 20)));
        panelIzquierdo.add(lblMenu);
        panelIzquierdo.add(Box.createRigidArea(new Dimension(0, 20)));
        
        btnExplorar = crearBotonMenu("Explorar");
        btnInventario = crearBotonMenu("Inventario");
        btnQuests = crearBotonMenu("Misiones");
        btnStats = crearBotonMenu("Estadisticas");
        btnGuardar = crearBotonMenu("Guardar");
        btnCargar = crearBotonMenu("Cargar");
        btnSalir = crearBotonMenu("Salir");
        
        panelIzquierdo.add(btnExplorar);
        panelIzquierdo.add(Box.createRigidArea(new Dimension(0, 10)));
        panelIzquierdo.add(btnInventario);
        panelIzquierdo.add(Box.createRigidArea(new Dimension(0, 10)));
        panelIzquierdo.add(btnQuests);
        panelIzquierdo.add(Box.createRigidArea(new Dimension(0, 10)));
        panelIzquierdo.add(btnStats);
        panelIzquierdo.add(Box.createRigidArea(new Dimension(0, 10)));
        panelIzquierdo.add(btnGuardar);
        panelIzquierdo.add(Box.createRigidArea(new Dimension(0, 10)));
        panelIzquierdo.add(btnCargar);
        panelIzquierdo.add(Box.createRigidArea(new Dimension(0, 10)));
        panelIzquierdo.add(btnSalir);
        
        btnExplorar.addActionListener(e -> mostrarPanelExploracion());
        btnInventario.addActionListener(e -> mostrarPanelInventario());
        btnQuests.addActionListener(e -> mostrarPanelQuests());
        btnStats.addActionListener(e -> mostrarPanelStats());
        btnGuardar.addActionListener(e -> guardarPartida());
        btnCargar.addActionListener(e -> cargarPartida());
        btnSalir.addActionListener(e -> salirJuego());
    }
    
    private JButton crearBotonMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setMaximumSize(new Dimension(180, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFont(new Font("Serif", Font.PLAIN, 16));
        btn.setBackground(new Color(70, 70, 70));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(218, 165, 32), 1));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(90, 90, 90));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(70, 70, 70));
            }
        });
        
        return btn;
    }
    
    private void construirPanelCentral() {
        cardLayout = new CardLayout();
        panelContenido = new JPanel(cardLayout);
        panelContenido.setBackground(new Color(40, 40, 40));
        
        panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBackground(new Color(40, 40, 40));
        panelCentral.setBorder(BorderFactory.createLineBorder(new Color(218, 165, 32), 2));
        panelCentral.add(panelContenido, BorderLayout.CENTER);
    }
    
    private void construirPanelDerecho() {
        panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        panelDerecho.setBackground(new Color(50, 50, 50));
        panelDerecho.setBorder(BorderFactory.createLineBorder(new Color(218, 165, 32), 2));
        panelDerecho.setPreferredSize(new Dimension(250, 0));
        
        JLabel lblTitulo = new JLabel("PERSONAJE", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(218, 165, 32));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelDerecho.add(Box.createRigidArea(new Dimension(0, 20)));
        panelDerecho.add(lblTitulo);
        panelDerecho.add(Box.createRigidArea(new Dimension(0, 20)));
    }
    
    private void construirPanelInferior() {
        panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(new Color(30, 30, 30));
        panelInferior.setBorder(BorderFactory.createLineBorder(new Color(218, 165, 32), 2));
        panelInferior.setPreferredSize(new Dimension(0, 150));
        
        JLabel lblConsola = new JLabel("CONSOLA DE EVENTOS");
        lblConsola.setFont(new Font("Serif", Font.BOLD, 14));
        lblConsola.setForeground(new Color(218, 165, 32));
        lblConsola.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        panelInferior.add(lblConsola, BorderLayout.NORTH);
        
        txtConsola = new JTextArea();
        txtConsola.setEditable(false);
        txtConsola.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtConsola.setBackground(new Color(20, 20, 20));
        txtConsola.setForeground(new Color(0, 255, 0));
        txtConsola.setLineWrap(true);
        txtConsola.setWrapStyleWord(true);
        
        scrollConsola = new JScrollPane(txtConsola);
        scrollConsola.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panelInferior.add(scrollConsola, BorderLayout.CENTER);
    }
    
    private JLabel crearLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Serif", Font.PLAIN, 14));
        lbl.setForeground(Color.WHITE);
        return lbl;
    }
    
    @Override
    public void onGameEvent(String mensaje) {
        SwingUtilities.invokeLater(() -> {
            txtConsola.append(mensaje + "\n");
            txtConsola.setCaretPosition(txtConsola.getDocument().getLength());
            actualizarEstadoJugador();
        });
    }
    
    private void actualizarEstadoJugador() {
        if (jugador != null) {
            lblNombreJugador.setText("Heroe: " + jugador.getNombre());
            lblNivel.setText("Nivel: " + jugador.getNivel());
            lblSalud.setText("HP: " + jugador.getSalud() + "/" + jugador.getSaludMaxima());
            lblExperiencia.setText("EXP: " + jugador.getExperiencia() + "/" + jugador.getExperienciaParaNivel());
        }
    }
    
    // METODOS DE PANELES
    private void mostrarPanelExploracion() {
        panelContenido.removeAll();
        JPanel panel = LOTRGamePanels.crearPanelExploracion(this, gameFacade, jugador);
        panelContenido.add(panel, "exploracion");
        cardLayout.show(panelContenido, "exploracion");
        panelContenido.revalidate();
        panelContenido.repaint();
    }
    
    private void mostrarPanelInventario() {
        panelContenido.removeAll();
        JPanel panel = LOTRGamePanels.crearPanelInventario(gameFacade, jugador);
        panelContenido.add(panel, "inventario");
        cardLayout.show(panelContenido, "inventario");
        panelContenido.revalidate();
        panelContenido.repaint();
    }
    
    private void mostrarPanelQuests() {
        panelContenido.removeAll();
        JPanel panel = LOTRGamePanels.crearPanelQuests(gameFacade, jugador);
        panelContenido.add(panel, "quests");
        cardLayout.show(panelContenido, "quests");
        panelContenido.revalidate();
        panelContenido.repaint();
    }
    
    private void mostrarPanelStats() {
        panelContenido.removeAll();
        JPanel panel = LOTRGamePanels.crearPanelStats(jugador);
        panelContenido.add(panel, "stats");
        cardLayout.show(panelContenido, "stats");
        panelContenido.revalidate();
        panelContenido.repaint();
    }
    
    // METODOS DE BATALLA
    public void iniciarBatallaGUI(Character jugador, Character enemigo) {
        panelContenido.removeAll();
        BattlePanel panelBatalla = new BattlePanel(jugador, enemigo, gameFacade);
        panelContenido.add(panelBatalla, "batalla");
        cardLayout.show(panelContenido, "batalla");
        panelContenido.revalidate();
        panelContenido.repaint();
    }
    
    public void iniciarBatallaQuest(Character jugador, Quest quest) {
        panelContenido.removeAll();
        BattlePanel panelBatalla = new BattlePanel(jugador, quest.getEnemigo(), gameFacade, quest);
        panelContenido.add(panelBatalla, "batalla");
        cardLayout.show(panelContenido, "batalla");
        panelContenido.revalidate();
        panelContenido.repaint();
    }
    
    public void volverAlMenu() {
        mostrarPanelExploracion();
    }
    
    private void guardarPartida() {
        JOptionPane.showMessageDialog(this, 
            "Funcionalidad de guardado en desarrollo.\nPuedes implementarla usando serializacion Java o JSON.", 
            "Guardar", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void cargarPartida() {
        JOptionPane.showMessageDialog(this, 
            "Funcionalidad de carga en desarrollo.\nPuedes implementarla usando deserializacion Java o JSON.", 
            "Cargar", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void salirJuego() {
        int opcion = JOptionPane.showConfirmDialog(this, 
            "Estas seguro que deseas salir del juego?", 
            "Salir", 
            JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    
    // METODO MAIN
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            LOTRGameComplete game = new LOTRGameComplete();
            game.setVisible(true);
        });
    }
}
