
package GUI.Manament;
import BUS.LogsBUS;
import DTO.LogsDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
public class LogsGUI extends javax.swing.JPanel {

    private LogsBUS logsBUS;

    public LogsGUI() {
        logsBUS = new LogsBUS();
        initComponents();
        loadLogsToTable();
        loadExCodeToComboBox();
        // Tự động tìm kiếm khi thay đổi ngày
        jDCNgaybatdau.getDateEditor().addPropertyChangeListener(evt -> {
            if ("date".equals(evt.getPropertyName())) {
                searchLogs();
            }
        });

        jDCNgayketthuc.getDateEditor().addPropertyChangeListener(evt -> {
            if ("date".equals(evt.getPropertyName())) {
                searchLogs();
            }
        });
    }

    private void loadLogsToTable() {
        DefaultTableModel model = (DefaultTableModel) TableLogsData.getModel();
        model.setRowCount(0);

        for (LogsDTO log : logsBUS.getAllLogs()) {
            model.addRow(new Object[]{log.getLogID(), log.getLogContent(), log.getLogUserID(), log.getLogExCode(), log.getLogDate()});
        }
    }
    private void loadResultsToTable() {
        DefaultTableModel model = (DefaultTableModel) TableLogsData.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        List<LogsDTO> logs = logsBUS.getAllLogs();

        for (LogsDTO log : logs) {
            model.addRow(new Object[]{
                log.getLogID(),
                log.getLogContent(),
                log.getLogUserID(),
                log.getLogExCode(),
                log.getLogDate()
            });
        }
    }
    
    private void loadExCodeToComboBox() {
        jComboexCode.removeAllItems(); // Xóa dữ liệu cũ trong combo box
        List<String> exCodes = logsBUS.getAllExCodes(); // Lấy danh sách exCode từ CSDL
        jComboexCode.addItem("Tất cả");
        for (String exCode : exCodes) {
            jComboexCode.addItem(exCode); // Thêm từng exCode vào combobox
        }
        jComboexCode.setSelectedIndex(-1);
    }
    
private void searchLogs() {
        String keyword = txtSearch.getText().trim();
        Object selectedExCodeObj = jComboexCode.getSelectedItem();
        String selectedExCode = (selectedExCodeObj != null) ? selectedExCodeObj.toString() : "Tất cả";

        Date startDate = jDCNgaybatdau.getDate();
        Date endDate = jDCNgayketthuc.getDate();

        Timestamp sqlStartDate = (startDate != null) ? new Timestamp(startDate.getTime()) : null;
        Timestamp sqlEndDate = (endDate != null) ? new Timestamp(endDate.getTime()) : null;

        String exCode = selectedExCode.equals("Tất cả") ? null : selectedExCode;

        List<LogsDTO> logs = logsBUS.searchLogs(keyword, exCode, sqlStartDate, sqlEndDate);

        DefaultTableModel model = (DefaultTableModel) TableLogsData.getModel();
        model.setRowCount(0);
        for (LogsDTO log : logs) {
            model.addRow(new Object[]{log.getLogID(), log.getLogContent(), log.getLogUserID(), log.getLogExCode(), log.getLogDate()});
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel3 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jComboexCode = new javax.swing.JComboBox<>();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jDCNgaybatdau = new com.toedter.calendar.JDateChooser();
        jLabel27 = new javax.swing.JLabel();
        jDCNgayketthuc = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableLogsData = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 0));
        setLayout(new java.awt.GridBagLayout());

        jPanel3.setBackground(new java.awt.Color(245, 245, 245));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), null, null));

        jPanel16.setBackground(new java.awt.Color(4, 51, 69));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(245, 245, 245));
        jLabel26.setText("Quản lý Logs");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(4, 51, 69));
        jPanel9.setLayout(new java.awt.GridBagLayout());

        jLabel28.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(245, 245, 245));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel28.setText("Mã đề thi");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 15, 10);
        jPanel9.add(jLabel28, gridBagConstraints);

        jComboexCode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboexCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboexCodeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 15, 10);
        jPanel9.add(jComboexCode, gridBagConstraints);

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 99;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 15, 10);
        jPanel9.add(txtSearch, gridBagConstraints);

        btnSearch.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSearch.setText("Tìm Kiếm");
        btnSearch.setPreferredSize(new java.awt.Dimension(75, 30));
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 15, 10);
        jPanel9.add(btnSearch, gridBagConstraints);

        jLabel13.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(245, 245, 245));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Ngày bắt đầu");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 15, 10);
        jPanel9.add(jLabel13, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 15, 10);
        jPanel9.add(jDCNgaybatdau, gridBagConstraints);

        jLabel27.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(245, 245, 245));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel27.setText("Ngày kết thúc");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 15, 10);
        jPanel9.add(jLabel27, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 15, 10);
        jPanel9.add(jDCNgayketthuc, gridBagConstraints);

        TableLogsData.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TableLogsData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID Logs", "Nội dung", "ID Users", "Mã đề thi", "Ngày thực hiện"
            }
        ));
        jScrollPane1.setViewportView(TableLogsData);
        if (TableLogsData.getColumnModel().getColumnCount() > 0) {
            TableLogsData.getColumnModel().getColumn(0).setMinWidth(80);
            TableLogsData.getColumnModel().getColumn(0).setMaxWidth(80);
            TableLogsData.getColumnModel().getColumn(2).setMinWidth(80);
            TableLogsData.getColumnModel().getColumn(2).setMaxWidth(80);
            TableLogsData.getColumnModel().getColumn(3).setMinWidth(80);
            TableLogsData.getColumnModel().getColumn(3).setMaxWidth(80);
            TableLogsData.getColumnModel().getColumn(4).setMinWidth(200);
            TableLogsData.getColumnModel().getColumn(4).setMaxWidth(200);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 893, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jPanel3, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jComboexCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboexCodeActionPerformed
        searchLogs();
    }//GEN-LAST:event_jComboexCodeActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        searchLogs();
    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        searchLogs();
    }//GEN-LAST:event_btnSearchActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TableLogsData;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> jComboexCode;
    private com.toedter.calendar.JDateChooser jDCNgaybatdau;
    private com.toedter.calendar.JDateChooser jDCNgayketthuc;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
