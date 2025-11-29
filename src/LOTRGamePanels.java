// ============================================
// PARTE 5: PANELES DE CONTENIDO Y BATALLA
// ============================================

import javax.swing.*;
import java.awt.*;
import java.util.List;

// Extension de LOTRGame con los paneles de contenido
public class LOTRGamePanels {
    
    // PANEL DE EXPLORACION
    public static JPanel crearPanelExploracion(LOTRGame game, GameFacade facade, Character jugador) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(40, 40, 40));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel lblTitulo = new JLabel("TIERRA MEDIA - Exploracion", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(218, 165, 32));
        panel.add(lblTitulo, BorderLayout.NORTH);
        
        JPanel panelCentro = new JPanel(new GridLayout(2, 2, 20, 20));
        panelCentro.setBackground(new Color(40, 40, 40));
        
        // Boton La Comarca
        JButton btnComarca = crearBotonExploracion("La Comarca", 
            "Un lugar pacifico habitado por hobbits");
        btnComarca.addActionListener(e -> {
            Character enemigo = EnemyFactory.createOrc(2);
            game.iniciarBatallaGUI(jugador, enemigo);
        });
        
        // Boton Rivendel
        JButton btnRivendel = crearBotonExploracion("Rivendel", 
            "Refugio de los elfos. Un lugar de descanso");
        btnRivendel.addActionListener(e -> {
            jugador.setSalud(jugador.getSaludMaxima());
            if (jugador instanceof Mage) {
                ((Mage) jugador).descansar();
            }
            GameEventManager.getInstance().notifyEvent("Has descansado en Rivendel. Salud y mana restaurados!");
        });
        
        // Boton Moria
        JButton btnMoria = crearBotonExploracion("Minas de Moria", 
            "Antiguas minas enanas. Lugar peligroso");
        btnMoria.addActionListener(e -> {
            Character enemigo = EnemyFactory.createUrukHai(4);
            game.iniciarBatallaGUI(jugador, enemigo);
        });
        
        // Boton Mordor
        JButton btnMordor = crearBotonExploracion("Mordor", 
            "La tierra oscura de Sauron. Extremadamente peligroso");
        btnMordor.addActionListener(e -> {
            Character enemigo = EnemyFactory.createNazgul(6);
            game.iniciarBatallaGUI(jugador, enemigo);
        });
        
        panelCentro.add(btnComarca);
        panelCentro.add(btnRivendel);
        panelCentro.add(btnMoria);
        panelCentro.add(btnMordor);
        
        panel.add(panelCentro, BorderLayout.CENTER);
        
        return panel;
    }
    
    private static JButton crearBotonExploracion(String nombre, String descripcion) {
        JButton btn = new JButton("<html><center><b>" + nombre + "</b><br><br>" + 
                                   "<i>" + descripcion + "</i></center></html>");
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
    
    // PANEL DE INVENTARIO
    public static JPanel crearPanelInventario(GameFacade facade, Character jugador) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(40, 40, 40));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel lblTitulo = new JLabel("INVENTARIO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(218, 165, 32));
        panel.add(lblTitulo, BorderLayout.NORTH);
        
        JPanel panelContenido = new JPanel(new GridLayout(1, 2, 20, 20));
        panelContenido.setBackground(new Color(40, 40, 40));
        
        // Panel de Armas
        JPanel panelArmas = new JPanel(new BorderLayout(5, 5));
        panelArmas.setBackground(new Color(50, 50, 50));
        panelArmas.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(218, 165, 32), 2),
            "ARMAS",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Serif", Font.BOLD, 16),
            new Color(218, 165, 32)
        ));
        
        DefaultListModel<String> modelArmas = new DefaultListModel<>();
        List<Weapon> armas = GameState.getInstance().getInventarioArmas();
        for (Weapon arma : armas) {
            modelArmas.addElement(arma.toString());
        }
        
        JList<String> listaArmas = new JList<>(modelArmas);
        listaArmas.setFont(new Font("Monospaced", Font.PLAIN, 14));
        listaArmas.setBackground(new Color(30, 30, 30));
        listaArmas.setForeground(Color.WHITE);
        listaArmas.setSelectionBackground(new Color(218, 165, 32));
        listaArmas.setSelectionForeground(Color.BLACK);
        
        JScrollPane scrollArmas = new JScrollPane(listaArmas);
        panelArmas.add(scrollArmas, BorderLayout.CENTER);
        
        JButton btnEquiparArma = new JButton("Equipar Arma");
        estilizarBoton(btnEquiparArma);
        btnEquiparArma.addActionListener(e -> {
            int index = listaArmas.getSelectedIndex();
            if (index >= 0 && index < armas.size()) {
                facade.equiparArma(jugador, armas.get(index));
            }
        });
        panelArmas.add(btnEquiparArma, BorderLayout.SOUTH);
        
        // Panel de Armaduras
        JPanel panelArmaduras = new JPanel(new BorderLayout(5, 5));
        panelArmaduras.setBackground(new Color(50, 50, 50));
        panelArmaduras.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(218, 165, 32), 2),
            "ARMADURAS",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Serif", Font.BOLD, 16),
            new Color(218, 165, 32)
        ));
        
        DefaultListModel<String> modelArmaduras = new DefaultListModel<>();
        List<Armor> armaduras = GameState.getInstance().getInventarioArmaduras();
        for (Armor armadura : armaduras) {
            modelArmaduras.addElement(armadura.toString());
        }
        
        JList<String> listaArmaduras = new JList<>(modelArmaduras);
        listaArmaduras.setFont(new Font("Monospaced", Font.PLAIN, 14));
        listaArmaduras.setBackground(new Color(30, 30, 30));
        listaArmaduras.setForeground(Color.WHITE);
        listaArmaduras.setSelectionBackground(new Color(218, 165, 32));
        listaArmaduras.setSelectionForeground(Color.BLACK);
        
        JScrollPane scrollArmaduras = new JScrollPane(listaArmaduras);
        panelArmaduras.add(scrollArmaduras, BorderLayout.CENTER);
        
        JButton btnEquiparArmadura = new JButton("Equipar Armadura");
        estilizarBoton(btnEquiparArmadura);
        btnEquiparArmadura.addActionListener(e -> {
            int index = listaArmaduras.getSelectedIndex();
            if (index >= 0 && index < armaduras.size()) {
                facade.equiparArmadura(jugador, armaduras.get(index));
            }
        });
        panelArmaduras.add(btnEquiparArmadura, BorderLayout.SOUTH);
        
        panelContenido.add(panelArmas);
        panelContenido.add(panelArmaduras);
        
        panel.add(panelContenido, BorderLayout.CENTER);
        
        // Panel de equipo actual
        JPanel panelEquipado = new JPanel(new GridLayout(2, 1, 5, 5));
        panelEquipado.setBackground(new Color(40, 40, 40));
        panelEquipado.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        String armaEquipada = jugador.getArmaEquipada() != null ? 
            jugador.getArmaEquipada().toString() : "Ninguna";
        String armaduraEquipada = jugador.getArmaduraEquipada() != null ? 
            jugador.getArmaduraEquipada().toString() : "Ninguna";
        
        JLabel lblArmaEquipada = new JLabel("Arma Equipada: " + armaEquipada, SwingConstants.CENTER);
        lblArmaEquipada.setFont(new Font("Serif", Font.BOLD, 14));
        lblArmaEquipada.setForeground(new Color(218, 165, 32));
        
        JLabel lblArmaduraEquipada = new JLabel("Armadura Equipada: " + armaduraEquipada, SwingConstants.CENTER);
        lblArmaduraEquipada.setFont(new Font("Serif", Font.BOLD, 14));
        lblArmaduraEquipada.setForeground(new Color(218, 165, 32));
        
        panelEquipado.add(lblArmaEquipada);
        panelEquipado.add(lblArmaduraEquipada);
        
        panel.add(panelEquipado, BorderLayout.SOUTH);
        
        return panel;
    }
    
    // PANEL DE MISIONES
    public static JPanel crearPanelQuests(GameFacade facade, Character jugador) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(40, 40, 40));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel lblTitulo = new JLabel("MISIONES DISPONIBLES", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(218, 165, 32));
        panel.add(lblTitulo, BorderLayout.NORTH);
        
        JPanel panelMisiones = new JPanel();
        panelMisiones.setLayout(new BoxLayout(panelMisiones, BoxLayout.Y_AXIS));
        panelMisiones.setBackground(new Color(40, 40, 40));
        
        List<Quest> quests = facade.obtenerQuestsActivas();
        
        for (Quest quest : quests) {
            JPanel panelQuest = crearPanelQuest(quest, facade, jugador);
            panelMisiones.add(panelQuest);
            panelMisiones.add(Box.createRigidArea(new Dimension(0, 15)));
        }
        
        JScrollPane scrollPane = new JScrollPane(panelMisiones);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private static JPanel crearPanelQuest(Quest quest, GameFacade facade, Character jugador) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(50, 50, 50));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(218, 165, 32), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBackground(new Color(50, 50, 50));
        
        JLabel lblNombre = new JLabel(quest.getNombre());
        lblNombre.setFont(new Font("Serif", Font.BOLD, 18));
        lblNombre.setForeground(new Color(218, 165, 32));
        lblNombre.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblDescripcion = new JLabel("<html>" + quest.getDescripcion() + "</html>");
        lblDescripcion.setFont(new Font("Serif", Font.PLAIN, 14));
        lblDescripcion.setForeground(Color.WHITE);
        lblDescripcion.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblRecompensa = new JLabel("Recompensa: " + quest.getRecompensaExp() + " EXP");
        lblRecompensa.setFont(new Font("Serif", Font.ITALIC, 12));
        lblRecompensa.setForeground(new Color(144, 238, 144));
        lblRecompensa.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panelInfo.add(lblNombre);
        panelInfo.add(Box.createRigidArea(new Dimension(0, 5)));
        panelInfo.add(lblDescripcion);
        panelInfo.add(Box.createRigidArea(new Dimension(0, 5)));
        panelInfo.add(lblRecompensa);
        
        panel.add(panelInfo, BorderLayout.CENTER);
        
        JButton btnIniciar = new JButton("Iniciar Mision");
        estilizarBoton(btnIniciar);
        btnIniciar.addActionListener(e -> {
            facade.asignarPersonajeAQuest(quest, jugador);
            if (quest.getEnemigo() != null) {
                // Iniciar batalla con el enemigo de la quest
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
                if (frame instanceof LOTRGame) {
                    ((LOTRGame) frame).iniciarBatallaQuest(jugador, quest);
                }
            }
        });
        panel.add(btnIniciar, BorderLayout.EAST);
        
        return panel;
    }
    
    // PANEL DE ESTADISTICAS
    public static JPanel crearPanelStats(Character jugador) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(40, 40, 40));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel lblTitulo = new JLabel("ESTADISTICAS DEL PERSONAJE", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(218, 165, 32));
        panel.add(lblTitulo, BorderLayout.NORTH);
        
        JPanel panelStats = new JPanel(new GridBagLayout());
        panelStats.setBackground(new Color(50, 50, 50));
        panelStats.setBorder(BorderFactory.createLineBorder(new Color(218, 165, 32), 2));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        
        agregarStat(panelStats, gbc, row++, "Nombre:", jugador.getNombre());
        agregarStat(panelStats, gbc, row++, "Clase:", jugador.getClasePersonaje());
        agregarStat(panelStats, gbc, row++, "Nivel:", String.valueOf(jugador.getNivel()));
        agregarStat(panelStats, gbc, row++, "Salud:", jugador.getSalud() + " / " + jugador.getSaludMaxima());
        agregarStat(panelStats, gbc, row++, "Experiencia:", jugador.getExperiencia() + " / " + jugador.getExperienciaParaNivel());
        
        if (jugador instanceof Mage) {
            Mage mago = (Mage) jugador;
            agregarStat(panelStats, gbc, row++, "Mana:", mago.getPuntosMana() + " / " + mago.getManaMaximo());
        }
        
        if (jugador instanceof Archer) {
            Archer arquero = (Archer) jugador;
            agregarStat(panelStats, gbc, row++, "Flechas:", String.valueOf(arquero.getFlechasRestantes()));
        }
        
        String armaEquipada = jugador.getArmaEquipada() != null ? 
            jugador.getArmaEquipada().toString() : "Ninguna";
        String armaduraEquipada = jugador.getArmaduraEquipada() != null ? 
            jugador.getArmaduraEquipada().toString() : "Ninguna";
        
        agregarStat(panelStats, gbc, row++, "Arma Equipada:", armaEquipada);
        agregarStat(panelStats, gbc, row++, "Armadura Equipada:", armaduraEquipada);
        
        panel.add(panelStats, BorderLayout.CENTER);
        
        return panel;
    }
    
    private static void agregarStat(JPanel panel, GridBagConstraints gbc, int row, String label, String value) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("Serif", Font.BOLD, 16));
        lblLabel.setForeground(new Color(218, 165, 32));
        panel.add(lblLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Serif", Font.PLAIN, 16));
        lblValue.setForeground(Color.WHITE);
        panel.add(lblValue, gbc);
    }
    
    private static void estilizarBoton(JButton btn) {
        btn.setFont(new Font("Serif", Font.PLAIN, 14));
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
    }
}