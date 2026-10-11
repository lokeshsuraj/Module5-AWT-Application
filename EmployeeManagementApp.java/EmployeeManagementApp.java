import java.awt.*;
import java.awt.event.*;

public class Module5AssignmentApp extends Frame implements ActionListener, ItemListener, MouseListener, KeyListener {

    private CardLayout cardLayout;
    private Panel cardPanel;
    
    private TextField nameField;
    private TextField idField;
    private TextArea bioArea;
    private Choice deptChoice;
    private CheckboxGroup group;
    private Checkbox cb1, cb2;
    private Checkbox agreeBox;
    private Scrollbar vScrollbar;
    private Label scrollValLabel;
    
    private java.awt.List dataList;
    private ScrollPane mainScrollPane;
    private Label statusLabel;

    public Module5AssignmentApp() {
        setTitle("Module-5 Complete AWT Showcase Application");
        setSize(650, 540);
        setLayout(new BorderLayout());

        // --- CARD LAYOUT CONTAINER ---
        cardLayout = new CardLayout();
        cardPanel = new Panel(cardLayout);

        // ================= CARD 1: FORM =================
        Panel formCard = new Panel(new BorderLayout());
        Panel gridPanel = new Panel(new GridLayout(7, 2, 5, 8));
        Panel formPaddingPanel = new Panel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        gridPanel.add(new Label("Full Name:"));
        nameField = new TextField(18);
        nameField.addKeyListener(this);
        gridPanel.add(nameField);

        gridPanel.add(new Label("ID Number:"));
        idField = new TextField(18);
        gridPanel.add(idField);

        gridPanel.add(new Label("Specialization:"));
        deptChoice = new Choice();
        deptChoice.add("Artificial Intelligence & ML");
        deptChoice.add("Data Science");
        deptChoice.add("Software Engineering");
        deptChoice.add("Cyber Security");
        deptChoice.add("Cloud Computing");
        deptChoice.add("Robotics Automation");
        deptChoice.add("Computer vision & Image Processing");
        deptChoice.addItemListener(this);
        gridPanel.add(deptChoice);

        gridPanel.add(new Label("Shift Type:"));
        Panel radioPanel = new Panel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        group = new CheckboxGroup();
        cb1 = new Checkbox("Morning", group, true);
        cb2 = new Checkbox("Evening", group, false);
        radioPanel.add(cb1);
        radioPanel.add(cb2);
        gridPanel.add(radioPanel);

        gridPanel.add(new Label("Terms Agreement:"));
        agreeBox = new Checkbox("Confirm registration details", false);
        gridPanel.add(agreeBox);

        gridPanel.add(new Label("Priority Level:"));
        Panel scrollPanel = new Panel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        vScrollbar = new Scrollbar(Scrollbar.HORIZONTAL, 3, 1, 1, 10);
        scrollValLabel = new Label("Level: 3");
        vScrollbar.addAdjustmentListener(e -> scrollValLabel.setText("Level: " + vScrollbar.getValue()));
        scrollPanel.add(vScrollbar);
        scrollPanel.add(scrollValLabel);
        gridPanel.add(scrollPanel);

        gridPanel.add(new Label("Additional Bio:"));
        bioArea = new TextArea(2, 18);
        gridPanel.add(bioArea);

        formPaddingPanel.add(gridPanel);
        formCard.add(formPaddingPanel, BorderLayout.CENTER);

        // Form Buttons
        Panel buttonPanel = new Panel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        Button saveBtn = new Button("Submit Record");
        Button clearBtn = new Button("Clear Form");
        saveBtn.addActionListener(this);
        clearBtn.addActionListener(this);
        buttonPanel.add(saveBtn);
        buttonPanel.add(clearBtn);
        formCard.add(buttonPanel, BorderLayout.SOUTH);

        // ================= CARD 2: DIRECTORY VIEW =================
        Panel viewCard = new Panel(new BorderLayout());
        dataList = new java.awt.List(8, false);
        
        mainScrollPane = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
        Panel listWrapper = new Panel(new GridLayout(1, 1));
        listWrapper.add(dataList);
        mainScrollPane.add(listWrapper);

        viewCard.add(new Label(" Submitted Records Registry (Click anywhere to test Mouse Events):", Label.LEFT), BorderLayout.NORTH);
        viewCard.add(mainScrollPane, BorderLayout.CENTER);

        // Button to add another employee
        Panel viewButtonPanel = new Panel(new FlowLayout(FlowLayout.CENTER));
        Button backToFormBtn = new Button("+ Add Another Employee");
        backToFormBtn.addActionListener(this);
        viewButtonPanel.add(backToFormBtn);
        viewCard.add(viewButtonPanel, BorderLayout.SOUTH);

        cardPanel.add(formCard, "FORM_VIEW");
        cardPanel.add(viewCard, "LIST_VIEW");
        add(cardPanel, BorderLayout.CENTER);

        // --- STATUS BAR ---
        statusLabel = new Label(" Status: Ready. Interact with fields, type, or click to trigger events.");
        statusLabel.setBackground(Color.lightGray);
        add(statusLabel, BorderLayout.SOUTH);

        addMouseListener(this);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("Submit Record")) {
            String name = nameField.getText().trim();
            String id = idField.getText().trim();
            if (name.isEmpty() || id.isEmpty()) {
                showCustomDialog("Error", "Name and ID cannot be blank!");
            } else {
                // Captured selected shift label from CheckboxGroup
                String shift = group.getSelectedCheckbox().getLabel();
                
                // Formatted string including the shift details
                String entry = "ID: " + id + " | Name: " + name + " | Dept: " + deptChoice.getSelectedItem() + " | Shift: " + shift;
                
                dataList.add(entry);
                showCustomDialog("Success", "Record added to registry successfully!");
                cardLayout.show(cardPanel, "LIST_VIEW");
            }
        } else if (cmd.equals("Clear Form")) {
            nameField.setText("");
            idField.setText("");
            bioArea.setText("");
            deptChoice.select(0);
            group.setSelectedCheckbox(cb1);
            agreeBox.setState(false);
            vScrollbar.setValue(3);
            scrollValLabel.setText("Level: 3");
        } else if (cmd.equals("+ Add Another Employee")) {
            cardLayout.show(cardPanel, "FORM_VIEW");
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        statusLabel.setText(" Event Listener: Choice changed to -> " + deptChoice.getSelectedItem());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        statusLabel.setText(" Mouse Event: Clicked at X=" + e.getX() + ", Y=" + e.getY());
    }
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        statusLabel.setText(" Key Event: Key Pressed (" + KeyEvent.getKeyText(e.getKeyCode()) + ")");
    }
    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    private void showCustomDialog(String title, String message) {
        Dialog dlg = new Dialog(this, title, true);
        dlg.setLayout(new FlowLayout());
        dlg.setSize(280, 120);
        dlg.add(new Label(message));
        Button okBtn = new Button("OK");
        okBtn.addActionListener(ev -> dlg.setVisible(false));
        dlg.add(okBtn);
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);
    }

    public static void main(String[] args) {
        new Module5AssignmentApp();
    }
}