/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI.Manament;

import BUS.ExamsBUS;
import DTO.ExamsDTO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author haun4
 */
public class ExamsGUI extends javax.swing.JPanel {

    private ExamsBUS examsBUS;

    public ExamsGUI() {
        examsBUS = new ExamsBUS();
        initComponents();
        addExCodeListener();
        loadExamsTable();
        addTableListenerForExams();
    }

    private void loadExamsTable() {
        List<ExamsDTO> examsList = examsBUS.getExamsList();
        DefaultTableModel model = (DefaultTableModel) TableExams.getModel();
        model.setRowCount(0);

        for (ExamsDTO exam : examsList) {
            model.addRow(new Object[]{
                exam.getTestCode(),
                exam.getExOrder(),
                exam.getExCode(),
                exam.getExQuesIDs()
            });
        }
    }
private void updateExCode() {
    String testCode = txtTestCode.getText().trim();
    String exOrder = txtExOrder.getText().trim();
    
    txtExCode.setText(testCode.isEmpty() || exOrder.isEmpty() ? "" : testCode + exOrder);
}

private void addExCodeListener() {
    DocumentListener listener = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) { updateExCode(); }
        @Override
        public void removeUpdate(DocumentEvent e) { updateExCode(); }
        @Override
        public void changedUpdate(DocumentEvent e) { updateExCode(); }
    };

    txtTestCode.getDocument().addDocumentListener(listener);
    txtExOrder.getDocument().addDocumentListener(listener);
}
    private void addTableListenerForExams() {
        TableExams.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                int selectedRow = TableExams.getSelectedRow();
                if (selectedRow >= 0) {
                    try {
                        // Lấy dữ liệu từ hàng đã chọn
                        String testCode = TableExams.getValueAt(selectedRow, 0).toString();
                        String exOrder = TableExams.getValueAt(selectedRow, 1).toString();
                        String exCode = TableExams.getValueAt(selectedRow, 2).toString();
                        String exQuesIDs = TableExams.getValueAt(selectedRow, 3).toString();

                        // Hiển thị dữ liệu lên các control
                        txtTestCode.setText(testCode);
                        txtExOrder.setText(exOrder);
                        txtExCode.setText(exCode);
                        txtExQuesIDs.setText(exQuesIDs);
                    } catch (Exception e) {
                        System.err.println("Lỗi khi chọn bài thi: " + e.getMessage());
                    }
                }
            }
        });
    }
private void searchExams(String keyword) {
    DefaultTableModel model = (DefaultTableModel) TableExams.getModel();
    model.setRowCount(0); // Xóa dữ liệu cũ

    List<ExamsDTO> filteredExams = examsBUS.searchExams(keyword);

    for (ExamsDTO exam : filteredExams) {
        model.addRow(new Object[]{
            exam.getTestCode(),
            exam.getExOrder(),
            exam.getExCode(),
            exam.getExQuesIDs()
        });
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtTestCode = new javax.swing.JTextField();
        txtExOrder = new javax.swing.JTextField();
        txtExCode = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtExQuesIDs = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        tbnSearch = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableExams = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        btnClear = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();

        setBackground(new java.awt.Color(153, 255, 153));
        setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(245, 245, 245));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), null, null));

        jPanel6.setBackground(new java.awt.Color(245, 245, 245));
        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(4, 51, 69), new java.awt.Color(4, 51, 69), null, null));

        jPanel12.setBackground(new java.awt.Color(4, 51, 69));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(245, 245, 245));
        jLabel22.setText("Quản lý Exams");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(245, 245, 245));
        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), null, null));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Thứ tự đề");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.4;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 5, 20);
        jPanel4.add(jLabel7, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Mã bài thi");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.4;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 5, 20);
        jPanel4.add(jLabel8, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.4;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 20);
        jPanel4.add(txtTestCode, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.4;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 20);
        jPanel4.add(txtExOrder, gridBagConstraints);

        txtExCode.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.4;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 5, 20);
        jPanel4.add(txtExCode, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Mã đề thi");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.4;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 5, 20);
        jPanel4.add(jLabel11, gridBagConstraints);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Danh sách câu hỏi");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.4;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 5, 20);
        jPanel4.add(jLabel12, gridBagConstraints);

        txtExQuesIDs.setColumns(20);
        txtExQuesIDs.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtExQuesIDs.setLineWrap(true);
        txtExQuesIDs.setRows(5);
        txtExQuesIDs.setWrapStyleWord(true);
        jScrollPane3.setViewportView(txtExQuesIDs);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 5, 20);
        jPanel4.add(jScrollPane3, gridBagConstraints);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 157, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.7;
        gridBagConstraints.weighty = 0.4;
        add(jPanel1, gridBagConstraints);

        jPanel2.setBackground(new java.awt.Color(245, 245, 245));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), null, null));

        jPanel9.setBackground(new java.awt.Color(4, 51, 69));
        jPanel9.setLayout(new java.awt.GridBagLayout());

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 99;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.4;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel9.add(txtSearch, gridBagConstraints);

        tbnSearch.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tbnSearch.setText("Tìm Kiếm");
        tbnSearch.setPreferredSize(new java.awt.Dimension(75, 30));
        tbnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbnSearchActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 99;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 100);
        jPanel9.add(tbnSearch, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 100);
        jPanel9.add(jLabel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 100);
        jPanel9.add(jLabel2, gridBagConstraints);

        TableExams.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TableExams.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã bài thi", "Thứ tự đề", "Mã đề thi", "Danh sách câu hỏi"
            }
        ));
        jScrollPane1.setViewportView(TableExams);
        if (TableExams.getColumnModel().getColumnCount() > 0) {
            TableExams.getColumnModel().getColumn(0).setMinWidth(70);
            TableExams.getColumnModel().getColumn(0).setMaxWidth(70);
            TableExams.getColumnModel().getColumn(1).setMinWidth(70);
            TableExams.getColumnModel().getColumn(1).setMaxWidth(70);
            TableExams.getColumnModel().getColumn(2).setMinWidth(70);
            TableExams.getColumnModel().getColumn(2).setMaxWidth(70);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 935, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.6;
        add(jPanel2, gridBagConstraints);

        jPanel3.setBackground(new java.awt.Color(245, 245, 245));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), null, null));

        jPanel10.setBackground(new java.awt.Color(245, 245, 245));
        jPanel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(4, 51, 69), new java.awt.Color(4, 51, 69), null, null));
        jPanel10.setLayout(new java.awt.GridBagLayout());

        btnClear.setBackground(new java.awt.Color(179, 65, 114));
        btnClear.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnClear.setForeground(new java.awt.Color(245, 245, 245));
        btnClear.setText("Clear");
        btnClear.setPreferredSize(new java.awt.Dimension(75, 30));
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 14;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 15, 15);
        jPanel10.add(btnClear, gridBagConstraints);

        btnAdd.setBackground(new java.awt.Color(65, 114, 179));
        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(245, 245, 245));
        btnAdd.setText("Thêm");
        btnAdd.setPreferredSize(new java.awt.Dimension(75, 30));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 14;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 15, 15);
        jPanel10.add(btnAdd, gridBagConstraints);

        btnUpdate.setBackground(new java.awt.Color(0, 153, 102));
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(245, 245, 245));
        btnUpdate.setText("Sửa");
        btnUpdate.setPreferredSize(new java.awt.Dimension(75, 30));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 14;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 15, 15);
        jPanel10.add(btnUpdate, gridBagConstraints);

        btnDelete.setBackground(new java.awt.Color(255, 68, 93));
        btnDelete.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(245, 245, 245));
        btnDelete.setText("Xóa");
        btnDelete.setPreferredSize(new java.awt.Dimension(75, 30));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 14;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 15, 15);
        jPanel10.add(btnDelete, gridBagConstraints);

        jButton12.setBackground(new java.awt.Color(106, 50, 159));
        jButton12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton12.setForeground(new java.awt.Color(245, 245, 245));
        jButton12.setText("Import");
        jButton12.setPreferredSize(new java.awt.Dimension(75, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 14;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 15, 15);
        jPanel10.add(jButton12, gridBagConstraints);

        jButton13.setBackground(new java.awt.Color(248, 210, 72));
        jButton13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton13.setForeground(new java.awt.Color(245, 245, 245));
        jButton13.setText("Export");
        jButton13.setPreferredSize(new java.awt.Dimension(75, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 14;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 15, 15);
        jPanel10.add(jButton13, gridBagConstraints);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 234, Short.MAX_VALUE)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.weighty = 0.4;
        add(jPanel3, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // Lấy dữ liệu từ giao diện
    String testCode = txtTestCode.getText().trim();
    String exOrderText = txtExOrder.getText().trim();

    // Kiểm tra đầu vào hợp lệ
    if (testCode.isEmpty() || exOrderText.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn bài thi cần xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    char exOrder = exOrderText.charAt(0);
    String exCode = testCode + exOrder;

    // Kiểm tra xem exCode có tồn tại trong bảng result không
    if (examsBUS.hasResultsForExCode(exCode)) {
        JOptionPane.showMessageDialog(this, "Không thể xóa! Bài thi đã có kết quả.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Xác nhận xóa
    int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa bài thi này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
        if (examsBUS.deleteExam(testCode, exOrder)) {
            JOptionPane.showMessageDialog(this, "Xóa bài thi thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadExamsTable(); // Cập nhật lại bảng
        } else {
            JOptionPane.showMessageDialog(this, "Xóa bài thi thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // Xóa nội dung các JTextField
        txtTestCode.setText("");
        txtExOrder.setText("");
        txtExCode.setText("");
        txtExQuesIDs.setText("");

        // Bỏ chọn dòng trong TableExams nếu có
        TableExams.clearSelection();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        String testCode = txtTestCode.getText().trim();
    String exOrderStr = txtExOrder.getText().trim();
    String exQuesIDs = txtExQuesIDs.getText().trim();

    // Kiểm tra đầu vào hợp lệ
    if (testCode.isEmpty() || exOrderStr.isEmpty() || exQuesIDs.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Kiểm tra exOrder phải là một ký tự duy nhất
    if (exOrderStr.length() != 1) {
        JOptionPane.showMessageDialog(this, "Thứ tự đề chỉ được nhập 1 ký tự!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }
    char exOrder = exOrderStr.charAt(0);
    
    // Tạo exCode dựa trên testCode + exOrder
    String exCode = testCode + exOrder;

    // Kiểm tra testCode có tồn tại không
    if (!examsBUS.isTestCodeExists(testCode)) {
        JOptionPane.showMessageDialog(this, "Mã bài thi không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Kiểm tra exCode có bị trùng không
    if (examsBUS.isExCodeExists(exCode)) {
        JOptionPane.showMessageDialog(this, "Mã đề thi đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Kiểm tra danh sách câu hỏi có hợp lệ không
    if (!examsBUS.areQuestionsValid(exQuesIDs)) {
        JOptionPane.showMessageDialog(this, "Danh sách câu hỏi không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Tạo đối tượng ExamsDTO và thêm vào cơ sở dữ liệu
    ExamsDTO exam = new ExamsDTO(testCode, exOrder, exCode, exQuesIDs);
    if (examsBUS.addExam(exam)) {
        JOptionPane.showMessageDialog(this, "Thêm bài thi thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        loadExamsTable(); // Cập nhật lại bảng
    } else {
        JOptionPane.showMessageDialog(this, "Thêm bài thi thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        String testCode = txtTestCode.getText().trim();
    String exOrderStr = txtExOrder.getText().trim();
    String exQuesIDs = txtExQuesIDs.getText().trim();

    // Kiểm tra đầu vào hợp lệ
    if (testCode.isEmpty() || exOrderStr.isEmpty() || exQuesIDs.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Kiểm tra exOrder phải là một ký tự duy nhất
    if (exOrderStr.length() != 1) {
        JOptionPane.showMessageDialog(this, "Thứ tự đề chỉ được nhập 1 ký tự!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }
    char exOrder = exOrderStr.charAt(0);

    // Tạo exCode dựa trên testCode + exOrder
    String exCode = testCode + exOrder;

    // Kiểm tra testCode có tồn tại không
    if (!examsBUS.isTestCodeExists(testCode)) {
        JOptionPane.showMessageDialog(this, "Mã bài thi không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Kiểm tra danh sách câu hỏi có hợp lệ không
    if (!examsBUS.areQuestionsValid(exQuesIDs)) {
        JOptionPane.showMessageDialog(this, "Danh sách câu hỏi không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Lấy thông tin cũ của bài thi từ database
    ExamsDTO oldExam = examsBUS.getExamByTestCodeAndOrder(testCode, exOrder);
    if (oldExam == null) {
        JOptionPane.showMessageDialog(this, "Bài thi không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Kiểm tra nếu thông tin mới khác thông tin cũ
    if (oldExam.getExQuesIDs().equals(exQuesIDs)) {
        JOptionPane.showMessageDialog(this, "Không có thay đổi trong dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Tạo đối tượng ExamsDTO mới
    ExamsDTO updatedExam = new ExamsDTO(testCode, exOrder, exCode, exQuesIDs);

    // Gọi hàm cập nhật trong BUS
    if (examsBUS.updateExam(updatedExam)) {
        JOptionPane.showMessageDialog(this, "Cập nhật bài thi thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        loadExamsTable(); // Cập nhật lại bảng
    } else {
        JOptionPane.showMessageDialog(this, "Cập nhật bài thi thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        String keyword = txtSearch.getText().trim();
    searchExams(keyword);
    }//GEN-LAST:event_txtSearchActionPerformed

    private void tbnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbnSearchActionPerformed
        String keyword = txtSearch.getText().trim();
    searchExams(keyword);
    }//GEN-LAST:event_tbnSearchActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TableExams;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton tbnSearch;
    private javax.swing.JTextField txtExCode;
    private javax.swing.JTextField txtExOrder;
    private javax.swing.JTextArea txtExQuesIDs;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTestCode;
    // End of variables declaration//GEN-END:variables
}
