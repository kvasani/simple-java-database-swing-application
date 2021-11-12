package com.javacreed.examples.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Application extends JFrame {

    private JTextField idTextField;
    private JTextField nameTextField;
    private JTextArea contactsTextArea;

    private JList<Contact> contactsList;

    private Action refreshAction;
    private Action newAction;
    private Action saveAction;
    private Action deleteAction;

    public Application() {
        super();
        initActions();
        initComponents();
    }

    private JToolBar createToolBar() {
        final JToolBar toolBar = new JToolBar();
        toolBar.add(refreshAction);
        toolBar.addSeparator();
        toolBar.add(newAction);
        toolBar.addSeparator();
        toolBar.add(saveAction);
        toolBar.addSeparator();
        toolBar.add(deleteAction);

        return toolBar;
    }
    private ImageIcon load(final String name) {
        return new ImageIcon(getClass().getResource("/icons/" + name + ".png"));
    }

    private void initActions() {

        refreshAction = new AbstractAction("Refresh",load("Refresh")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshData();
            }
        };

        newAction = new AbstractAction("New", load("New")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNew();
            }
        };
        saveAction = new AbstractAction("Save", load("Save")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        };
        deleteAction = new AbstractAction("Delete", load("Delete")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete();
            }
        };
    }

    private void refreshData() {

    }
    private void createNew() {

    }

    private void save() {

    }
    private void delete() {
        if (JOptionPane.YES_NO_OPTION == JOptionPane.showConfirmDialog(this, "Delete?", "Delete", JOptionPane.YES_NO_OPTION)) {
            // delete
        }
    }

    public void initComponents() {
        add(createToolBar(), BorderLayout.PAGE_START);
        add(createListPane(), BorderLayout.WEST);
        add(createEditor(), BorderLayout.CENTER);

    }

    private JComponent createListPane() {
        contactsList = new JList<>();
        return new JScrollPane(contactsList);
    }

    private JComponent createEditor() {
        final JPanel panel = new JPanel(new GridBagLayout());

        //ID label
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(2,2,2,2);
        panel.add(new JLabel("Id"), constraints);


        // ID text field
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.weightx = 1;
        constraints.insets = new Insets(2,2,2,2);
        constraints.fill = GridBagConstraints.BOTH;
        idTextField = new JTextField();
        idTextField.setEditable(false);
        panel.add(idTextField, constraints);

        // Name label
        constraints = new GridBagConstraints();
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(2,2,2,2);
        panel.add(new JLabel("Name"), constraints);

        // Name Text field
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.weightx = 1;
        constraints.insets = new Insets(2,2,2,2);
        constraints.fill = GridBagConstraints.BOTH;
        nameTextField = new JTextField();
        panel.add(nameTextField, constraints);

        //Contacts label
        constraints = new GridBagConstraints();
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = new Insets(2,2,2,2);
        panel.add(new JLabel("Contacts"), constraints);

        // Contacts Text area
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.insets = new Insets(2,2,2,2);
        constraints.fill = GridBagConstraints.BOTH;
        contactsTextArea = new JTextArea();
        panel.add(new JScrollPane(contactsTextArea), constraints);


        return panel;
    }
}
