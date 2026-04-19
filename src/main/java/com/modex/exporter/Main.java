package com.modex.exporter;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import javax.swing.JFileChooser;
import com.modex.exporter.DocumentPreviewer;
import com.modex.exporter.Payment;
import com.modex.exporter.Launch;
import com.modex.dao.ClientDataDAO;
import com.modex.dao.ServicesDAO;
import com.modex.model.services;  

public class Main extends JFrame {

    //Create the object
    ServicesDAO serviceDAO = new ServicesDAO();
    ClientDataDAO client  = new ClientDataDAO();
    //Establish  Attributes
    private JTextField fileField;
    private JLabel statusLabel;
    private JPanel contentPanel;
    private CardLayout cardLayout;
    

    public Main() {
        setTitle("UniApp – Campus Edition");
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
                        "UniApp Campus Edition\nFrontend Demo\nDesigned for Students",
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

    private  void  pay(String AccountRefence){
	    String phoneno  = JOptionPane.showInputDialog(null,
			    "Enter your phoneNumber to simulate card entry:",
			    "phoneNo Entry",
			    JOptionPane.PLAIN_MESSAGE);
    }
    private JPanel createCardArea() {
        JPanel page = new JPanel();
        page.setOpaque(false);
        page.setLayout(new GridLayout(1, 2, 25, 0));
        return page;
    }


    private JPanel createHomePage() {
	    JPanel page = createCardArea();
            page.add(ConvertCard());
	    return page;
    }
    private JPanel createHumanizerPage() {
	    JPanel page = createCardArea();
            page.add(AICard());
	    return page;
    }
    private JPanel createFormattingPage() {
	    JPanel p = new JPanel(new BorderLayout());
            JLabel l = new JLabel("Formatting Workspace");
            l.setFont(new Font("Segoe UI", Font.BOLD, 20));
            p.add(l, BorderLayout.CENTER);
	    return p;
    }
    private JPanel createHelpPage() {
	    JPanel page =createCardArea();
	    page.add(FAQCard());
            return page;
    }

    private JPanel createBaseCard() {
	    JPanel card = new JPanel();
	    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
	    card.setBackground(new Color(245, 247, 250));
	    card.setBorder(new EmptyBorder(30, 30, 30, 30));
	    return card;
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

	    home.setForeground(Color.WHITE);
	    humanizer.setForeground(Color.WHITE);
	    format.setForeground(Color.WHITE);
	    help.setForeground(Color.WHITE);

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


    private JPanel ConvertCard() {
	    // Main card panel
	    JPanel ConvertCard = createBaseCard();
	    ConvertCard.add(new JLabel("Converting Content"));

	    // Variables
	    services service = serviceDAO.getByAccount("Convert");
	    // Title label
	    JLabel t = new JLabel("Choose Format");
	    t.setFont(new Font("Segoe UI", Font.BOLD, 18));

	    // Hint label
	    JLabel hint = new JLabel("Select output format");
	    hint.setFont(new Font("Segoe UI", Font.PLAIN, 13));

	    // Original preview panel
	    JPanel originalPreviewPanel = new JPanel();
	    originalPreviewPanel.setLayout(new BorderLayout());
	    originalPreviewPanel.setPreferredSize(new Dimension(400, 200));
	    originalPreviewPanel.setBackground(Color.WHITE);
	    originalPreviewPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

	    // Combo box for format selection
	    JComboBox<String> formats = new JComboBox<>(
			    new String[]{"PDF", "DOCX", "TXT", "HTML", "PNG", "JPG", "XLSX"}
			    );
	    formats.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
	    formats.setFont(new Font("Segoe UI", Font.PLAIN, 14));

	    // Convert button
	    JButton convert = new JButton("Convert Now");
	    convert.setFont(new Font("Segoe UI", Font.BOLD, 14));
	    convert.addActionListener(e -> pay(service.Account()));
	    // Browse button to select file
	    JButton browse = new JButton("Browse File");
	    browse.setFont(new Font("Segoe UI", Font.PLAIN, 13));
	    browse.addActionListener(e -> {
		    JFileChooser fileChooser = new JFileChooser();
		    if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			    File selectedFile = fileChooser.getSelectedFile();
			    fileField.setText(selectedFile.getAbsolutePath());

			    // Preview original file dynamically
			    previewOriginal(selectedFile, originalPreviewPanel);
		    }
	    });

	    // File field
	    fileField = new JTextField();
	    fileField.setEditable(false);
	    fileField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
	    // Add components with spacing
	    ConvertCard.add(t);
	    ConvertCard.add(Box.createVerticalStrut(8));
	    ConvertCard.add(hint);
	    ConvertCard.add(Box.createVerticalStrut(20));
	    ConvertCard.add(originalPreviewPanel);  // Original preview panel
	    ConvertCard.add(Box.createVerticalStrut(20));
	    ConvertCard.add(fileField);
	    ConvertCard.add(Box.createVerticalStrut(8));
	    ConvertCard.add(browse);
	    ConvertCard.add(Box.createVerticalStrut(20));
	    ConvertCard.add(formats);
	    ConvertCard.add(Box.createVerticalStrut(20));
	    ConvertCard.add(convert);
	    return ConvertCard;
    }
    private JPanel AICard(){
	    services service = serviceDAO.getByAccount("Humanizer");
	    JPanel card = createBaseCard(); // reuse your base card   
	    JLabel title = new JLabel("AI Humanizer");
            title.setFont(new Font("Segoe UI", Font.BOLD, 16));
            JTextArea textArea = new JTextArea(10, 40);
            JScrollPane scroll = new JScrollPane(textArea);
	    textArea.setLineWrap(true);
	    textArea.setWrapStyleWord(true);
	    JLabel counter = new JLabel("Words: 0 / 200");
	    // Word limit logic
	    textArea.getDocument().addDocumentListener(new DocumentListener() {
		    private void update() {
			    String text = textArea.getText().trim();
			    String[] words = text.isEmpty() ? new String[0] : text.split("\\s+");

			    if (words.length > 200) {
				    StringBuilder limited = new StringBuilder();
				    for (int i = 0; i < 200; i++) {
					    limited.append(words[i]).append(" ");
				    }
				    textArea.setText(limited.toString().trim());
			    }
			    counter.setText("Words: " + Math.min(words.length, 200) + " / 200");
		    }
		    public void insertUpdate(DocumentEvent e) { update(); }
		    public void removeUpdate(DocumentEvent e) { update(); }
		    public void changedUpdate(DocumentEvent e) { update(); }
	    });
	    JButton submit = new JButton("Process Text");
	    submit.addActionListener(e -> pay(service.Account()));

	    // Layout
	    card.add(title);
	    card.add(Box.createVerticalStrut(15));
	    card.add(scroll);
	    card.add(Box.createVerticalStrut(10));
	    card.add(counter);
	    card.add(Box.createVerticalStrut(15));
	    card.add(submit);
	    return card;
    }

    private JPanel createFAQItem(String question, String answer) {
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.setBackground(Color.WHITE);
	    panel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
	    JButton qButton = new JButton(question);
	    qButton.setFocusPainted(false);
	    qButton.setContentAreaFilled(false);
	    qButton.setBorderPainted(false);
	    qButton.setHorizontalAlignment(SwingConstants.LEFT);
	    qButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
	    JTextArea aText = new JTextArea(answer);
	    aText.setLineWrap(true);
	    aText.setWrapStyleWord(true);
	    aText.setEditable(false);
	    aText.setVisible(false);
	    aText.setBackground(Color.WHITE);

	    // Toggle visibility
	    qButton.addActionListener(e -> {
		    aText.setVisible(!aText.isVisible());
		    panel.revalidate();
	    });
	    panel.add(qButton);
	    panel.add(aText);
	    return panel;
    }

    private JPanel FAQCard() {
	    JPanel card = createBaseCard();
	    JLabel title = new JLabel("Frequently Asked Questions");
	    title.setFont(new Font("Segoe UI", Font.BOLD, 18));

	    card.add(title);
	    card.add(Box.createVerticalStrut(20));

	    // Add FAQs
	    card.add(createFAQItem("What is UniConvert?",
				    "UniConvert is a tool that helps format and humanize text using AI."));
	    card.add(Box.createVerticalStrut(10));
	    card.add(createFAQItem("What is the word limit?",
				    "You can input up to 200 words in the AI Humanizer."));
	    card.add(Box.createVerticalStrut(10));
	    card.add(createFAQItem("Is my data محفوظ?",
				    "Your data is processed securely and not stored permanently."));
	    return card;
    }




    // ================= Original Preview Logic Integrated =================
    private void previewOriginal(File file, JPanel previewPanel) {
	    previewPanel.removeAll();
	    
	    try {
		    String name = file.getName().toLowerCase().trim();

		    if (name.endsWith(".pdf")) {
			    DocumentPreviewer.renderPDF(new FileInputStream(file), previewPanel);
		    
		    } else if (name.endsWith(".docx")) {
			    DocumentPreviewer.renderDOCX(new FileInputStream(file), previewPanel);
		    } else if (name.endsWith(".xlsx")) {
			    DocumentPreviewer.renderXLSX(new FileInputStream(file), previewPanel);
		    } else {
			    previewPanel.add(new JLabel("Preview not supported"));
		    }
	    } catch (Exception ex) {
		    ex.printStackTrace(); // <-- shows full error in console
		    previewPanel.removeAll();
		    previewPanel.add(new JLabel("Error previewing file"));
	    }
	    previewPanel.revalidate();
	    previewPanel.repaint();
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
