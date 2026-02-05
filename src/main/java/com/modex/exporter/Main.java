package com.modex.exporter;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import javax.swing.JFileChooser;

public class Main extends JFrame {

    private JTextField fileField;
    private JLabel statusLabel;

    public Main() {
        setTitle("UniConvert – Campus Edition");
        setSize(1000, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());

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
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Color.WHITE);
        main.setBorder(new EmptyBorder(25, 30, 25, 30));

        main.add(createHeader(), BorderLayout.NORTH);
        main.add(createCardArea(), BorderLayout.CENTER);
        main.add(createFooter(), BorderLayout.SOUTH);

        return main;
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

