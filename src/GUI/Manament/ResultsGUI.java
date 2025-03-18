
package GUI.Manament;

import BUS.ResultBUS;
import DTO.ResultDTO;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author haun4
 */
public class ResultsGUI extends javax.swing.JPanel {

    private ResultBUS resultBUS;

    public ResultsGUI() {
        resultBUS = new ResultBUS();
        initComponents();
        loadResultsToTable();
        loadExCodeToComboBox();
        jDCNgaybatdau.getDateEditor().addPropertyChangeListener(evt -> {
        if ("date".equals(evt.getPropertyName())) {
            searchResults();
        }
    });

    jDCNgayketthuc.getDateEditor().addPropertyChangeListener(evt -> {
        if ("date".equals(evt.getPropertyName())) {
            searchResults();
        }
    });
    }

    private void loadResultsToTable() {
        DefaultTableModel model = (DefaultTableModel) TableResultsData.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        List<ResultDTO> results = resultBUS.getAllResults();

        for (ResultDTO result : results) {
            model.addRow(new Object[]{
                result.getUserID(),
                result.getExCode(),
                result.getRsAnswers(),
                result.getRsMark(),
                result.getRsDate()
            });
        }
    }

    private void loadExCodeToComboBox() {
        jComboexCode.removeAllItems(); // Xóa dữ liệu cũ trong combo box
        List<String> exCodes = resultBUS.getAllExCodes(); // Lấy danh sách exCode từ CSDL
        jComboexCode.addItem("Tất cả");
        for (String exCode : exCodes) {
            jComboexCode.addItem(exCode); // Thêm từng exCode vào combobox
        }
        jComboexCode.setSelectedIndex(-1);
    }

    private void searchResults() {
        String keyword = txtSearch.getText().trim(); // Lấy từ khóa tìm kiếm

        // Lấy giá trị từ combo box, tránh null
        Object selectedExCodeObj = jComboexCode.getSelectedItem();
        String selectedExCode = (selectedExCodeObj != null) ? selectedExCodeObj.toString() : "Tất cả";

        // Lấy giá trị ngày bắt đầu và kết thúc
        Date startDate = jDCNgaybatdau.getDate();
        Date endDate = jDCNgayketthuc.getDate();

        // Chuyển đổi ngày về dạng SQL (nếu có)
        java.sql.Timestamp sqlStartDate = (startDate != null) ? new java.sql.Timestamp(startDate.getTime()) : null;
        java.sql.Timestamp sqlEndDate = (endDate != null) ? new java.sql.Timestamp(endDate.getTime()) : null;

        // Nếu chọn "Tất cả" thì không lọc theo exCode
        String exCode = selectedExCode.equals("Tất cả") ? null : selectedExCode;

        // Gọi phương thức tìm kiếm từ BUS
        List<ResultDTO> results = resultBUS.searchResults(keyword, exCode, sqlStartDate, sqlEndDate);

        // Cập nhật dữ liệu vào bảng
        DefaultTableModel model = (DefaultTableModel) TableResultsData.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        for (ResultDTO result : results) {
            model.addRow(new Object[]{
                result.getUserID(),
                result.getExCode(),
                result.getRsAnswers(),
                result.getRsMark(),
                result.getRsDate()
            });
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
        jButton14 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jDCNgaybatdau = new com.toedter.calendar.JDateChooser();
        jLabel27 = new javax.swing.JLabel();
        jDCNgayketthuc = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableResultsData = new javax.swing.JTable();

        setBackground(new java.awt.Color(0, 255, 204));
        setLayout(new java.awt.GridBagLayout());

        jPanel3.setBackground(new java.awt.Color(245, 245, 245));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), null, null));

        jPanel16.setBackground(new java.awt.Color(4, 51, 69));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(245, 245, 245));
        jLabel26.setText("Quản lý Results");

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

        jButton14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton14.setText("Tìm Kiếm");
        jButton14.setPreferredSize(new java.awt.Dimension(75, 30));
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
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
        jPanel9.add(jButton14, gridBagConstraints);

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

        TableResultsData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID Results", "ID Users", "Đáp án đã chọn", "Điểm", "Ngày thi"
            }
        ));
        jScrollPane1.setViewportView(TableResultsData);
        if (TableResultsData.getColumnModel().getColumnCount() > 0) {
            TableResultsData.getColumnModel().getColumn(0).setMinWidth(80);
            TableResultsData.getColumnModel().getColumn(0).setMaxWidth(80);
            TableResultsData.getColumnModel().getColumn(1).setMinWidth(80);
            TableResultsData.getColumnModel().getColumn(1).setMaxWidth(80);
            TableResultsData.getColumnModel().getColumn(3).setMinWidth(80);
            TableResultsData.getColumnModel().getColumn(3).setMaxWidth(80);
            TableResultsData.getColumnModel().getColumn(4).setMinWidth(200);
            TableResultsData.getColumnModel().getColumn(4).setMaxWidth(200);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 969, Short.MAX_VALUE)
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jPanel3, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jComboexCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboexCodeActionPerformed
        searchResults();
    }//GEN-LAST:event_jComboexCodeActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        searchResults();
    }//GEN-LAST:event_txtSearchActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        searchResults();
    }//GEN-LAST:event_jButton14ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TableResultsData;
    private javax.swing.JButton jButton14;
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
