package com.modex.exporter;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import javax.swing.JFileChooser;

public class Main extends JFrame {

    private JTextField fileField;
    private JLabel statusLabel;
    private JPanel contentPanel;
    private CardLayout cardLayout;


    public Main() {
        setTitle("UniConvert – Campus Edition");
        setSize(1000, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
	 // ✅ Add components
        add(createSideMenu(), BorderLayout.WEST);
        add(createMainPanel(), BorderLayout.CENTER);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                exitApp();
            }
        });

        setJMenuBar(createMenuBar());

        add(createMainPanel(), BorderLayout.CENTER);
    }

    private JMenuBar createMenuBar() {
        JMenuBar bar = new JMenuBar();

        JMenu file = new JMenu("File");
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> exitApp());

        JMenu help = new JMenu("Help");
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "UniConvert Campus Edition\nFrontend Demo\nDesigned for Students",
                        "About", JOptionPane.INFORMATION_MESSAGE));

        file.add(exit);
        help.add(about);

        bar.add(file);
        bar.add(help);
        return bar;
    }

    private void exitApp() {
        int opt = JOptionPane.showConfirmDialog(this,
                "Exit UniConvert?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION);

        if (opt == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }




    private JButton menuButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setFocusPainted(false);
        return btn;
    }

    private JPanel createMainPanel() {
	    JPanel wrapper = new JPanel(new BorderLayout());
            wrapper.setBackground(Color.WHITE);
            wrapper.setBorder(new EmptyBorder(25, 30, 25, 30));

            wrapper.add(createHeader(), BorderLayout.NORTH);

            cardLayout = new CardLayout();
            contentPanel = new JPanel(cardLayout);

            contentPanel.add(createHomePage(), "HOME");
            contentPanel.add(createHumanizerPage(), "HUMANIZER");
            contentPanel.add(createFormattingPage(), "FORMAT");
            contentPanel.add(createHelpPage(), "HELP");

            wrapper.add(contentPanel, BorderLayout.CENTER);
            wrapper.add(createFooter(), BorderLayout.SOUTH);
	    return wrapper;
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel title = new JLabel("Convert Your Documents Effortlessly");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));

        JLabel sub = new JLabel("Fast. Clean. Campus-friendly.");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JPanel left = new JPanel(new GridLayout(2, 1));
        left.setOpaque(false);
        left.add(title);
        left.add(sub);

        header.add(left, BorderLayout.WEST);

        JButton reset = new JButton("Reset");
        reset.addActionListener(e -> {
            fileField.setText("");
            statusLabel.setText("Ready");
        });

        header.add(reset, BorderLayout.EAST);

        return header;
    }

    private JPanel createCardArea() {
        JPanel area = new JPanel();
        area.setOpaque(false);
        area.setLayout(new GridLayout(1, 2, 25, 0));

        area.add(uploadCard());
        area.add(formatCard());

        return area;
    }


    private JPanel createHomePage() {
	    JPanel page = new JPanel(new GridLayout(1, 2, 25, 0));
            page.setOpaque(false);
	    page.add(uploadCard());
            page.add(formatCard());
	    return page;
    }


    private JPanel createHumanizerPage() {
	    JPanel p = new JPanel(new BorderLayout());
    	    JLabel l = new JLabel("Humanizer Tools Coming Soon");
    	    l.setFont(new Font("Segoe UI", Font.BOLD, 20));
            p.add(l, BorderLayout.CENTER);
	    return p;
    }

    private JPanel createFormattingPage() {
	    JPanel p = new JPanel(new BorderLayout());
            JLabel l = new JLabel("Formatting Workspace");
            l.setFont(new Font("Segoe UI", Font.BOLD, 20));
            p.add(l, BorderLayout.CENTER);
	    return p;
    }

    private JPanel createHelpPage() {
	    JPanel p = new JPanel(new BorderLayout());
            JLabel l = new JLabel("Help Center");
            l.setFont(new Font("Segoe UI", Font.BOLD, 20));
            p.add(l, BorderLayout.CENTER);
            return p;
    }


    private JPanel createSideMenu() {
	    JPanel side = new JPanel();
            side.setPreferredSize(new Dimension(210, 0));
            side.setBackground(new Color(32, 45, 64));
            side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));
            side.setBorder(new EmptyBorder(25, 15, 25, 15));

            JLabel logo = new JLabel("UniConvert");
            logo.setForeground(Color.WHITE);
            logo.setFont(new Font("Segoe UI", Font.BOLD, 18));
            logo.setAlignmentX(Component.CENTER_ALIGNMENT);

            JButton home = menuButton("Home");
            JButton humanizer = menuButton("Humanizer");
            JButton format = menuButton("Formatting");
            JButton help = menuButton("Help");

            styleSideButton(home);
            styleSideButton(humanizer);
            styleSideButton(format);
            styleSideButton(help);

            home.addActionListener(e -> cardLayout.show(contentPanel, "HOME"));
            humanizer.addActionListener(e -> cardLayout.show(contentPanel, "HUMANIZER"));
            format.addActionListener(e -> cardLayout.show(contentPanel, "FORMAT"));
            help.addActionListener(e -> cardLayout.show(contentPanel, "HELP"));

            side.add(logo);
            side.add(Box.createVerticalStrut(35));
            side.add(home);
            side.add(Box.createVerticalStrut(12));
            side.add(humanizer);
            side.add(Box.createVerticalStrut(12));
            side.add(format);
            side.add(Box.createVerticalStrut(12));
            side.add(help);
	    return side;
    }


    private JPanel uploadCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(245, 247, 250));
        card.setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel t = new JLabel("Upload Document");
        t.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JLabel hint = new JLabel("Choose a file from your device");
        hint.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        fileField = new JTextField();
        fileField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        fileField.setEditable(false);

        JButton browse = new JButton("Browse File");
        browse.setAlignmentX(Component.LEFT_ALIGNMENT);
        browse.addActionListener(e -> {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select a file to convert");
		int result = fileChooser.showOpenDialog(null); // null = center on screen
		if (result == JFileChooser.APPROVE_OPTION) {
			// User selected a file
             		File selectedFile = fileChooser.getSelectedFile();
			fileField.setText(selectedFile.getAbsolutePath());
		}
	});

        card.add(t);
        card.add(Box.createVerticalStrut(8));
        card.add(hint);
        card.add(Box.createVerticalStrut(20));
        card.add(fileField);
        card.add(Box.createVerticalStrut(15));
        card.add(browse);

        return card;
    }

    private JPanel formatCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(245, 247, 250));
        card.setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel t = new JLabel("Choose Format");
        t.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JLabel hint = new JLabel("Select output format");
        hint.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JComboBox<String> formats = new JComboBox<>(
                new String[]{"PDF", "DOCX", "TXT", "HTML", "PNG", "JPG","xlsx"}
        );

        JButton convert = new JButton("Convert Now");
        convert.setFont(new Font("Segoe UI", Font.BOLD, 14));
        convert.addActionListener(e ->
                statusLabel.setText("Frontend demo: conversion action triggered"));

        card.add(t);
        card.add(Box.createVerticalStrut(8));
        card.add(hint);
        card.add(Box.createVerticalStrut(20));
        card.add(formats);
        card.add(Box.createVerticalStrut(20));
        card.add(convert);

        return card;
    }

    private JPanel createFooter() {
        JPanel foot = new JPanel(new BorderLayout());
        foot.setOpaque(false);

        statusLabel = new JLabel("Ready");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JLabel brand = new JLabel("UniConvert © Modex Technologies");
        brand.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        foot.add(statusLabel, BorderLayout.WEST);
        foot.add(brand, BorderLayout.EAST);

        return foot;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
