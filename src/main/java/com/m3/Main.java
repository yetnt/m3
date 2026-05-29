/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.m3;

import com.m3.files.Folders;
import com.m3.files.ModFolder;
import com.m3.methods.MovementConfig;
import com.m3.methods.MovementMethod;
import com.m3.util.Files;
import com.m3.util.SystemCapabilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

/**
 *
 * @author ACER
 */
public class Main extends javax.swing.JFrame {

    public Folders folders = Folders.getInstance();
    public ModFolder selectedFolder;
    private MovementMethod.Type selectedMethod;
    private HashMap<UUID, JRadioButton> modFolderRadioButtons = new HashMap<>();
    private HashMap<MovementMethod.Type, JRadioButton> movementMethods = new HashMap<>();

    private void setFolders() {
        ArrayList<ModFolder> folders = Folders.m3Files.readModFolders();
        UUID selectedModFolder = Folders.m3Files.getSelectedVersion();
        for (ModFolder modFolder : folders) {
            buttonGrpPanel.add(modFolder.getBtn());
            buttonGroup1.add(modFolder.getBtn());
            modFolder.getBtn().addActionListener(getActionListener(modFolder));
            modFolderRadioButtons.put(modFolder.getId(), modFolder.getBtn());
            if (selectedModFolder != null && selectedModFolder.equals(modFolder.getId())) {
                modFolder.getBtn().setSelected(true);
                selectedFolder = modFolder;
            }
        }
    }

    private void initFoldersAndMethods() {
        movementMethods.put(MovementMethod.Type.COPY, setToCopy);
        movementMethods.put(MovementMethod.Type.MOVE, setToMove);
        movementMethods.put(MovementMethod.Type.SYMLINK, setToSymlink);
        setText();
        setMethodRadios();
        setFolders();
    }
    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();
        if (folders.isEmpty()) {
            modsFolderBtn.setText("Set Mods Folder");
            disableAllButtons(true);
            m3FolderBtn.setText("[Set mods folder first!]");
            return;
        }
        initFoldersAndMethods();

        leftPanel.remove(setCurrentBtn);
        leftPanel.revalidate();
        leftPanel.repaint();
    }

    public void setMethodRadios() {
        setMethodRadios(Folders.m3Files.getMethodToUse() == null ? MovementMethod.Type.COPY : Folders.m3Files.getMethodToUse(), false);
        movementMethods.get(selectedMethod).setSelected(true);
    }

    public void setMethodRadios(MovementMethod.Type method, boolean clean) {
        if (clean)
            selectedMethod.getMethod().clean(folders, selectedFolder);
        selectedMethod = method;
        Folders.m3Files.setMethodToUse(method);
        if (clean) {
            // use the new method to set the current selected folder.
            selectedMethod.getMethod().apply(folders, selectedFolder, new MovementConfig(MovementConfig.Type.METHOD_CHANGE));
        }
    }

    public void disableAllButtons(boolean skipModsFolder) {
        if (!skipModsFolder)
            modsFolderBtn.setEnabled(false);
        m3FolderBtn.setEnabled(false);
        addModsToVersionBtn.setEnabled(false);
        addNewVersionBtn.setEnabled(false);
        backupBtn.setEnabled(false);
        viewBackupBtn.setEnabled(false);
        viewsecondBackupBtn.setEnabled(false);
        setCurrentBtn.setEnabled(false);
        renameCurrentBtn.setEnabled(false);
        setToCopy.setEnabled(false);
        setToMove.setEnabled(false);
        setToSymlink.setEnabled(false);
    }

    public void enableAllButtons(boolean skipModsFolder) {
        if (!skipModsFolder)
            modsFolderBtn.setEnabled(true);
        m3FolderBtn.setEnabled(true);
        addModsToVersionBtn.setEnabled(true);
        addNewVersionBtn.setEnabled(true);
        backupBtn.setEnabled(true);
        viewBackupBtn.setEnabled(true);
        viewsecondBackupBtn.setEnabled(true);
        setCurrentBtn.setEnabled(true);
        renameCurrentBtn.setEnabled(true);
        setToCopy.setEnabled(true);
        setToMove.setEnabled(true);
        setToSymlink.setEnabled(true);
    }


    public ActionListener getActionListener(ModFolder folder) {
        return (e) -> {
            if (selectedFolder == folder) return; // already set.
            if (
                    !new BooleanDialogue(
                            this,
                            "Apply " + folder.getName() + " to your mods folder?"
                    ).response()
            ) {
                folder.getBtn().setSelected(false);
                selectedFolder.getBtn().setSelected(true);
                return;
            }
            disableAllButtons(false);
            Folders.m3Files.setSelectedVersion(folder);
            ModFolder old = selectedFolder;
            selectedFolder = folder;
            System.out.println("Set the folder to " + folder.getName());
            selectedMethod.getMethod().apply(folders, folder, new MovementConfig(old));
            enableAllButtons(false);
            JOptionPane.showMessageDialog(this,
                    "Loaded the following mods: \n\n" +
                            (selectedMethod == MovementMethod.Type.MOVE ?
                                    Arrays.stream(folders.getModsFolder().listFiles()).map(File::getName)
                                    : folder.getModNames().stream()
                            ).map(s -> s + "\n\t\t").reduce("", String::concat)
                    ,
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        };
    }

    public void setText() {
        modsFolderLabel.setText(folders.getModsFolder().getPath());
        M3FolderLabel.setText(folders.getM3Folder().getPath());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        rightPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        modsFolderLabel = new javax.swing.JLabel();
        modsFolderBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        M3FolderLabel = new javax.swing.JLabel();
        m3FolderBtn = new javax.swing.JButton();
        addNewVersionBtn = new javax.swing.JButton();
        backupBtn = new javax.swing.JButton();
        viewBackupBtn = new javax.swing.JButton();
        viewsecondBackupBtn = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        leftPanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        setCurrentBtn = new javax.swing.JButton();
        renameCurrentBtn = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        buttonGrpPanel = new javax.swing.JPanel();
        setToCopy = new javax.swing.JRadioButton();
        setToMove = new javax.swing.JRadioButton();
        setToSymlink = new javax.swing.JRadioButton();
        addModsToVersionBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Minecraft Mod Mover");

        rightPanel.setMaximumSize(new java.awt.Dimension(800, 1200));
        rightPanel.setMinimumSize(new java.awt.Dimension(400, 600));
        rightPanel.setPreferredSize(new java.awt.Dimension(400, 600));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Minecraft Mods Folder:");

        modsFolderLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        modsFolderLabel.setText("(not set)");

        modsFolderBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        modsFolderBtn.setText("Change Mods Folder");
        modsFolderBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modsFolderBtnActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("M3 Folder");

        M3FolderLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        M3FolderLabel.setText("(not set)");

        m3FolderBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        m3FolderBtn.setText("Change M3 Folder");
        m3FolderBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m3FolderBtnActionPerformed(evt);
            }
        });

        addNewVersionBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        addNewVersionBtn.setText("Add New Pack");
        addNewVersionBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewVersionBtnActionPerformed(evt);
            }
        });

        backupBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        backupBtn.setText("<html>Backup Current Mods Folder (Automatic)</html>");
        backupBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backupBtnActionPerformed(evt);
            }
        });

        viewBackupBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        viewBackupBtn.setText("View Backup Folder");
        viewBackupBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewBackupBtnActionPerformed(evt);
            }
        });

        viewsecondBackupBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        viewsecondBackupBtn.setText("Second Backup Folder");
        viewsecondBackupBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewsecondBackupBtnActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe Print", 1, 36)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Manage");

        javax.swing.GroupLayout rightPanelLayout = new javax.swing.GroupLayout(rightPanel);
        rightPanel.setLayout(rightPanelLayout);
        rightPanelLayout.setHorizontalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rightPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(m3FolderBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(modsFolderBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addNewVersionBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(rightPanelLayout.createSequentialGroup()
                        .addComponent(backupBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(viewsecondBackupBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(viewBackupBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(M3FolderLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(modsFolderLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        rightPanelLayout.setVerticalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rightPanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(modsFolderLabel)
                .addGap(18, 18, 18)
                .addComponent(modsFolderBtn)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(M3FolderLabel)
                .addGap(18, 18, 18)
                .addComponent(m3FolderBtn)
                .addGap(44, 44, 44)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(addNewVersionBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(rightPanelLayout.createSequentialGroup()
                        .addComponent(viewBackupBtn)
                        .addGap(18, 18, 18)
                        .addComponent(viewsecondBackupBtn))
                    .addComponent(backupBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(75, 75, 75))
        );

        getContentPane().add(rightPanel, java.awt.BorderLayout.WEST);

        jSeparator1.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createEtchedBorder()));
        jSeparator1.setMinimumSize(new java.awt.Dimension(20, 0));
        getContentPane().add(jSeparator1, java.awt.BorderLayout.CENTER);

        leftPanel.setMaximumSize(new java.awt.Dimension(800, 1200));
        leftPanel.setMinimumSize(new java.awt.Dimension(400, 600));
        leftPanel.setPreferredSize(new java.awt.Dimension(400, 600));

        jLabel5.setFont(new java.awt.Font("Segoe Print", 1, 36)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Packs");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Click a pack to replace the mods folder with that pack's mods");

        setCurrentBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        setCurrentBtn.setText("Set Current Mods As A Pack");
        setCurrentBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setCurrentBtnActionPerformed(evt);
            }
        });

        renameCurrentBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        renameCurrentBtn.setText("Rename Current Selected Pack");
        renameCurrentBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                renameCurrentBtnActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe Print", 3, 48)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("M3");

        buttonGrpPanel.setLayout(new javax.swing.BoxLayout(buttonGrpPanel, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(buttonGrpPanel);

        buttonGroup2.add(setToCopy);
        setToCopy.setText("Copy");
        setToCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setToCopyActionPerformed(evt);
            }
        });

        buttonGroup2.add(setToMove);
        setToMove.setText("Move");
        setToMove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setToMoveActionPerformed(evt);
            }
        });

        buttonGroup2.add(setToSymlink);
        setToSymlink.setText("Symlink");
        setToSymlink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setToSymlinkActionPerformed(evt);
            }
        });

        addModsToVersionBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        addModsToVersionBtn.setText("Add Mods To Selected Pack");
        addModsToVersionBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addModsToVersionBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout leftPanelLayout = new javax.swing.GroupLayout(leftPanel);
        leftPanel.setLayout(leftPanelLayout);
        leftPanelLayout.setHorizontalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, leftPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(addModsToVersionBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, leftPanelLayout.createSequentialGroup()
                        .addComponent(setToCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(setToMove, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(setToSymlink, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(renameCurrentBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(setCurrentBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(23, 23, 23))
        );
        leftPanelLayout.setVerticalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftPanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addGap(10, 10, 10)
                .addComponent(setCurrentBtn)
                .addGap(18, 18, 18)
                .addComponent(addModsToVersionBtn)
                .addGap(18, 18, 18)
                .addComponent(renameCurrentBtn)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(setToCopy)
                    .addComponent(setToMove)
                    .addComponent(setToSymlink))
                .addGap(18, 18, 18)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(leftPanel, java.awt.BorderLayout.EAST);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void modsFolderBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modsFolderBtnActionPerformed
        File chosenFolder = Files.folderChooser(this);
        if (chosenFolder == null) {
            JOptionPane.showMessageDialog(this, "Please select your mods folder.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        folders = new Folders(chosenFolder, true);
        initFoldersAndMethods();

        enableAllButtons(true);
        modsFolderBtn.setText("Change Mods Folder");
        m3FolderBtn.setText(
                "Change M3 Folder"
        );

    }//GEN-LAST:event_modsFolderBtnActionPerformed

    private void m3FolderBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m3FolderBtnActionPerformed
        if (folders.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select your mods folder first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // M3 already set a folder, so get it.
        File old = folders.getM3Folder();
        File chosenFolder = Files.folderChooser(this);
        if (chosenFolder == null) {
            JOptionPane.showMessageDialog(this, "Please select your M3 folder.", "Errpr", JOptionPane.ERROR_MESSAGE);
            return;
        }

        buttonGrpPanel.removeAll();
        buttonGrpPanel.repaint();
        buttonGrpPanel.revalidate();
        buttonGroup1.clearSelection();

        folders.setM3Folder(chosenFolder, false);
        if (Folders.m3Files.getModsFolder().listFiles().length == 0)
            folders.addEmptyPreset();

        initFoldersAndMethods();

        if (!old.equals(chosenFolder) &&
                new BooleanDialogue(this,
                        "<html>A previous M3 location was created (" + old.getAbsolutePath() + "). Would you like to delete it? (A backup will be saved in the secondary backup location)</html>")
                        .response()
        ) {
            Files.backupFolder(Folders.homeFiles.getBackupFolder(), old, "m3-folder-backup");
            Files.recursiveDelete(old);
        }
    }//GEN-LAST:event_m3FolderBtnActionPerformed

    private void addNewVersionBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewVersionBtnActionPerformed
        String name = JOptionPane.showInputDialog("Enter the name of the new version:");
        if (name == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "No name was given so folder creation has been terminated",
                    "Info",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        ModFolder modFolder = ModFolder.create(name);
        selectedFolder = modFolder;

        Folders.m3Files.addModFolder(modFolder);

        buttonGrpPanel.add(modFolder.getBtn());
        modFolder.getBtn().setSelected(true);
        buttonGroup1.add(modFolder.getBtn());
        modFolder.getBtn().addActionListener(getActionListener(modFolder));
        modFolderRadioButtons.put(modFolder.getId(), modFolder.getBtn());
        buttonGrpPanel.revalidate();
        buttonGrpPanel.repaint();

        leftPanel.remove(setCurrentBtn);
        leftPanel.revalidate();
        leftPanel.repaint();
    }//GEN-LAST:event_addNewVersionBtnActionPerformed

    private void addModsToVersionBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addModsToVersionBtnActionPerformed

        if (selectedFolder.getId().equals(ModFolder.EMPTY_PRESET)) {
            JOptionPane.showMessageDialog(
                    this,
                    "You cannot add mods to the Empty Preset. Please select or create a new version folder.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Make sure to select mods of the same version!");

        ArrayList<File> mods = Files.multipleFileChooser(
                (chooser) -> {
                    chooser.setDialogTitle("Select Mods");
                    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    // only .jar files
                    chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                        public boolean accept(File f) {
                            return f.isDirectory() || f.getName().toLowerCase().endsWith(".jar");
                        }

                        public String getDescription() {
                            return "JAR Files (*.jar)";
                        }
                    });
                },
                this
        );

        if (mods == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "No mods were selected, so the version was not created.",
                    "Info",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        ArrayList<File> invalidMods = mods.stream()
                .filter(mod -> !mod.isFile() || !mod.getName().toLowerCase().endsWith(".jar"))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);


        if (!invalidMods.isEmpty()) {
            mods.removeAll(invalidMods);
            if (mods.isEmpty()) {
                // All mods invalid.
                JOptionPane.showMessageDialog(
                        this,
                        "No valid mods were selected, so no mods were added to the version.",
                        "Info",
                        JOptionPane.INFORMATION_MESSAGE
                );
                return;
            } else {
                String invalidModNames = invalidMods.stream()
                        .map(File::getName)
                        .reduce("", (acc, name) -> acc + name + "\n");
                JOptionPane.showMessageDialog(this,
                        "The following files are not valid mod files (must be .jar files) and will be skipped:\n\n"
                                + invalidModNames,
                        "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }

        selectedFolder.addMods(mods.toArray(new File[0]));

        if (new BooleanDialogue(this, "should M3 delete the old mod files?").response()) {
            mods.forEach(File::delete);
        }

        // since the current folder was edited. Redo the operation
        selectedMethod.getMethod().apply(folders, selectedFolder, new MovementConfig(mods));
    }//GEN-LAST:event_addModsToVersionBtnActionPerformed

    private void backupBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backupBtnActionPerformed
        Files.backupMods(Folders.m3Files.getBackupsFolder());
        Files.backupMods(Folders.homeFiles.getBackupFolder());
    }//GEN-LAST:event_backupBtnActionPerformed

    private void viewBackupBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewBackupBtnActionPerformed
        try {
            Desktop.getDesktop().open(Folders.m3Files.getBackupsFolder());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }//GEN-LAST:event_viewBackupBtnActionPerformed

    private void viewsecondBackupBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewsecondBackupBtnActionPerformed
        try {
            Desktop.getDesktop().open(Folders.homeFiles.getBackupFolder());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }//GEN-LAST:event_viewsecondBackupBtnActionPerformed

    private void setCurrentBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setCurrentBtnActionPerformed
        // Set whatever is in the mods folder as a new version
        File[] mods = folders.getModsFolder().listFiles();
        if (mods == null || mods.length == 0) return;
        if (!new BooleanDialogue(this, "<html>"
                + "This quickly creates a version of the mods you currently have loaded. A backup will be made."
                +"</html>").response())
            return;
        Files.backupMods(Folders.m3Files.getBackupsFolder());

        addNewVersionBtnActionPerformed(evt); // make the new version.

        selectedFolder.addMods(mods);
    }//GEN-LAST:event_setCurrentBtnActionPerformed

    private void renameCurrentBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_renameCurrentBtnActionPerformed
        if (selectedFolder.getId().equals(ModFolder.EMPTY_PRESET)) {
            JOptionPane.showMessageDialog(
                    this,
                    "You cannot rename the Empty Preset. Please select or create a new version folder.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        String newName = JOptionPane.showInputDialog(this,

"Enter new name for " + selectedFolder.getName(),
                "Rename Mod Folder",
                JOptionPane.QUESTION_MESSAGE
        );
        if (newName == null || newName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        selectedFolder.setName(newName);
        selectedFolder.getBtn().setText(newName);
        Folders.m3Files.replaceModFolder(selectedFolder);
        buttonGrpPanel.revalidate();
        buttonGrpPanel.repaint();
    }//GEN-LAST:event_renameCurrentBtnActionPerformed

    private void setToMoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setToMoveActionPerformed
        if (selectedMethod == MovementMethod.Type.MOVE) return;
        if (
                !new BooleanDialogue(
                        this,
                        "<html>This method saves space but is more risky than copy in terms of I/O. Proceed?</html>"
                        ).response()
        ) {
            setToMove.setSelected(false);
            movementMethods.get(selectedMethod).setSelected(true);
            return;
        }
        setMethodRadios(MovementMethod.Type.MOVE, true);
    }//GEN-LAST:event_setToMoveActionPerformed

    private void setToCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setToCopyActionPerformed
        if (selectedMethod == MovementMethod.Type.COPY) return;
        setMethodRadios(MovementMethod.Type.COPY, true);
    }//GEN-LAST:event_setToCopyActionPerformed

    private void setToSymlinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setToSymlinkActionPerformed
        if (selectedMethod == MovementMethod.Type.SYMLINK) return;

        if (!SystemCapabilities.canCreateSymlinks()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Your OS does not allow M3 to create symbolic links."
                            + "\n\n" + "If you're on windows please try the following fixes to enable symlink method:"
                            + "\n" + "1.\tRunning the app as an administrator"
                            + "\n" + "2.\tTurning on Developer Mode (Windows 10/11)"
                            + "\n\n" + "If none work. My condolences.",
                    "os Said no no",
                    JOptionPane.ERROR_MESSAGE
            );
            setToSymlink.setSelected(false);
            movementMethods.get(selectedMethod).setSelected(true);
            return;
        }
        if (
                !new BooleanDialogue(
                        this,
                        "<html>This method saves space but now relies on referencing which can cause problems on different systems. Proceed?</html>"
                ).response()
        ) {
            setToSymlink.setSelected(false);
            movementMethods.get(selectedMethod).setSelected(true);
            return;
        }
        setMethodRadios(MovementMethod.Type.SYMLINK, true);
    }//GEN-LAST:event_setToSymlinkActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel M3FolderLabel;
    private javax.swing.JButton addModsToVersionBtn;
    private javax.swing.JButton addNewVersionBtn;
    private javax.swing.JButton backupBtn;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JPanel buttonGrpPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JButton m3FolderBtn;
    private javax.swing.JButton modsFolderBtn;
    private javax.swing.JLabel modsFolderLabel;
    private javax.swing.JButton renameCurrentBtn;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JButton setCurrentBtn;
    private javax.swing.JRadioButton setToCopy;
    private javax.swing.JRadioButton setToMove;
    private javax.swing.JRadioButton setToSymlink;
    private javax.swing.JButton viewBackupBtn;
    private javax.swing.JButton viewsecondBackupBtn;
    // End of variables declaration//GEN-END:variables
}
