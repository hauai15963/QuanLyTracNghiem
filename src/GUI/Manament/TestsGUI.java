package GUI.Manament;

import BUS.TestsBUS;
import DTO.TestsDTO;
import DTO.TopicsDTO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author haun4
 */
public class TestsGUI extends javax.swing.JPanel {

    private TestsBUS testsBUS;

    public TestsGUI() {
        initComponents();
        testsBUS = new TestsBUS();
        TableTestsData();
        loadComboBoxTimes();
        loadComboBoxTopics();
        addTableListenerForTests();
    }

    private void TableTestsData() {
        DefaultTableModel model = (DefaultTableModel) TableTests.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        List<TestsDTO> tests = testsBUS.getAllTests();
        for (TestsDTO test : tests) {
            model.addRow(new Object[]{
                test.getCode(),
                test.getTitle(),
                test.getTime(),
                test.getTopicId(),
                test.getNumEasy(),
                test.getNumMedium(),
                test.getNumDiff(),
                test.getLimit(),
                test.getDate(),
                test.getStatus() == 1 ? "Hiển thị" : "Ẩn"
            });
        }
    }

    private void loadComboBoxTimes() {
        jComboThoigian.removeAllItems(); // Xóa dữ liệu cũ
        jComboThoigian1.removeAllItems(); // Xóa dữ liệu cũ
        jComboThoigian1.addItem("Tất cả"); // Thêm tùy chọn không chọn gì cả

        List<Integer> times = testsBUS.getAllTestTimes(); // Lấy danh sách thời gian từ BUS

        for (Integer time : times) {
            jComboThoigian.addItem(time.toString()); // Thêm từng giá trị vào ComboBox
        }
        for (Integer time : times) {
            jComboThoigian1.addItem(time.toString()); // Thêm từng giá trị vào ComboBox
        }
        jComboThoigian1.setSelectedIndex(-1); // Không chọn gì cả
    }

    private void loadComboBoxTopics() {
        jComboChude.removeAllItems(); // Xóa dữ liệu cũ
        jComboChude1.removeAllItems();
        jComboChude1.addItem("Tất cả"); // Thêm tùy chọn không chọn gì cả

        List<String> topics = testsBUS.getAllTestTopics(); // Lấy danh sách chủ đề từ BUS

        for (String topic : topics) {
            jComboChude.addItem(topic); // Thêm từng chủ đề vào ComboBox
        }
        for (String topic : topics) {
            jComboChude1.addItem(topic); // Thêm từng chủ đề vào ComboBox
        }
        jComboChude1.setSelectedIndex(-1); // Không chọn gì cả
    }

    private void addTableListenerForTests() {
        TableTests.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                int selectedRow = TableTests.getSelectedRow();
                if (selectedRow >= 0) {
                    try {
                        // Lấy danh sách bài kiểm tra từ BUS
                        List<TestsDTO> tests = testsBUS.getAllTests();
                        TestsDTO test = tests.get(selectedRow); // Lấy bài kiểm tra tương ứng với dòng được chọn

                        // Hiển thị dữ liệu lên các control
                        txtTestCode.setText(test.getCode());
                        txtTestTitle.setText(test.getTitle());
                        jComboThoigian.setSelectedItem(String.valueOf(test.getTime()));
                        jComboChude.setSelectedItem(String.valueOf(test.getTopicId()));
                        txtNumEasy.setText(String.valueOf(test.getNumEasy()));
                        txtNumMedium.setText(String.valueOf(test.getNumMedium()));
                        txtNumDiff.setText(String.valueOf(test.getNumDiff()));
                        txtTestLimit.setText(String.valueOf(test.getLimit()));

                        // Xử lý ngày tháng trong JDateChooser
                        if (test.getDate() != null) {
                            txtTestDate.setDate(test.getDate()); // Dữ liệu từ DAO đã là kiểu Date, không cần chuyển đổi
                        } else {
                            txtTestDate.setDate(null); // Nếu không có ngày, đặt null để tránh lỗi
                        }

                        jRadioHoatdong.setSelected(test.getStatus() == 1); // Giả sử 1 là "Hiển thị", 0 là "Ẩn"
                    } catch (IndexOutOfBoundsException e) {
                        System.err.println("Lỗi chỉ mục khi lấy bài kiểm tra: " + e.getMessage());
                    } catch (Exception e) {
                        System.err.println("Lỗi không xác định: " + e.getMessage());
                    }
                }
            }
        });
    }

    private void searchTests() {
    String keyword = txtSearch.getText().trim();

    // Lấy giá trị từ combo box, tránh null
    Object selectedTopicObj = jComboChude1.getSelectedItem();
    Object selectedTimeObj = jComboThoigian1.getSelectedItem();

    String selectedTopic = (selectedTopicObj != null) ? selectedTopicObj.toString() : "Tất cả";
    String selectedTime = (selectedTimeObj != null) ? selectedTimeObj.toString() : "Tất cả";

    // Xử lý tpID chỉ khi giá trị là số
    Integer tpID = null;
    if (!selectedTopic.equals("Tất cả") && selectedTopic.matches("\\d+")) {
        tpID = Integer.parseInt(selectedTopic);
    }

    // Xử lý thời gian làm bài
    Integer testTime = null;
    if (!selectedTime.equals("Tất cả") && selectedTime.matches("\\d+")) {
        testTime = Integer.parseInt(selectedTime);
    }

    // Gọi phương thức tìm kiếm
    List<TestsDTO> results = testsBUS.searchTests(keyword, tpID, testTime);

    // Cập nhật dữ liệu vào bảng
    DefaultTableModel model = (DefaultTableModel) TableTests.getModel();
    model.setRowCount(0);

    for (TestsDTO test : results) {
        model.addRow(new Object[]{
            test.getCode(),
            test.getTitle(),
            test.getTime(),
            test.getTopicId(),
            test.getNumEasy(),
            test.getNumMedium(),
            test.getNumDiff(),
            test.getLimit(),
            test.getDate(),
            test.getStatus() == 1 ? "Hiển thị" : "Ẩn"
        });
    }
}


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel15 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtTestCode = new javax.swing.JTextField();
        txtTestTitle = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jRadioHoatdong = new javax.swing.JRadioButton();
        jComboThoigian = new javax.swing.JComboBox<>();
        jComboChude = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        txtNumEasy = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtTestLimit = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtNumDiff = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtNumMedium = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtTestDate = new com.toedter.calendar.JDateChooser();
        jPanel12 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        btnClear = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jComboChude1 = new javax.swing.JComboBox<>();
        jComboThoigian1 = new javax.swing.JComboBox<>();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableTests = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 153));
        setLayout(new java.awt.GridBagLayout());

        jPanel15.setBackground(new java.awt.Color(245, 245, 245));
        jPanel15.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), null, null));

        jPanel6.setBackground(new java.awt.Color(245, 245, 245));
        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(4, 51, 69), new java.awt.Color(4, 51, 69), null, null));

        jPanel3.setBackground(new java.awt.Color(245, 245, 245));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), null, null));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setText("Tiêu đề");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 5, 20);
        jPanel3.add(jLabel14, gridBagConstraints);

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setText("Mã bài kiểm tra ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 5, 20);
        jPanel3.add(jLabel15, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 9;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 5, 20);
        jPanel3.add(txtTestCode, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 9;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 5, 20);
        jPanel3.add(txtTestTitle, gridBagConstraints);

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setText("Thời gian");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 5, 20);
        jPanel3.add(jLabel16, gridBagConstraints);

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setText("Chủ đề");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 5, 20);
        jPanel3.add(jLabel17, gridBagConstraints);

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setText("Hoạt động      ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 5, 20);
        jPanel3.add(jLabel18, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 5, 20);
        jPanel3.add(jRadioHoatdong, gridBagConstraints);

        jComboThoigian.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 5, 20);
        jPanel3.add(jComboThoigian, gridBagConstraints);

        jComboChude.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 5, 20);
        jPanel3.add(jComboChude, gridBagConstraints);

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setText("Số câu dễ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 5, 20);
        jPanel3.add(jLabel19, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 5, 20);
        jPanel3.add(txtNumEasy, gridBagConstraints);

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel20.setText("Số lần thi");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 5, 20);
        jPanel3.add(jLabel20, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 5, 20);
        jPanel3.add(txtTestLimit, gridBagConstraints);

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel23.setText("Số câu khó");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 5, 20);
        jPanel3.add(jLabel23, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 5, 20);
        jPanel3.add(txtNumDiff, gridBagConstraints);

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel24.setText("Trung bình");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 5, 20);
        jPanel3.add(jLabel24, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 5, 20);
        jPanel3.add(txtNumMedium, gridBagConstraints);

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel25.setText("Ngày thi                ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 5, 20);
        jPanel3.add(jLabel25, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 5, 20);
        jPanel3.add(txtTestDate, gridBagConstraints);

        jPanel12.setBackground(new java.awt.Color(4, 51, 69));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(245, 245, 245));
        jLabel22.setText("Quản lý Tests");

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

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 669, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
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
        add(jPanel15, gridBagConstraints);

        jPanel16.setBackground(new java.awt.Color(245, 245, 245));
        jPanel16.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), null, null));

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

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 244, Short.MAX_VALUE)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.weighty = 0.4;
        add(jPanel16, gridBagConstraints);

        jPanel17.setBackground(new java.awt.Color(245, 245, 245));
        jPanel17.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), null, null));

        jPanel9.setBackground(new java.awt.Color(4, 51, 69));
        jPanel9.setLayout(new java.awt.GridBagLayout());

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(245, 245, 245));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel28.setText("Chủ đề");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel9.add(jLabel28, gridBagConstraints);

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(245, 245, 245));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel29.setText("Thời gian");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel9.add(jLabel29, gridBagConstraints);

        jComboChude1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboChude1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboChude1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 30);
        jPanel9.add(jComboChude1, gridBagConstraints);

        jComboThoigian1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboThoigian1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboThoigian1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 30);
        jPanel9.add(jComboThoigian1, gridBagConstraints);

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 99;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
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
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 50);
        jPanel9.add(btnSearch, gridBagConstraints);

        TableTests.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TableTests.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã bài kiểm tra", "Tiêu đề", "Thời gian làm bài", "Chủ đề bài học", "Dễ", "Trung bình", "Khó", "Số lần", "Ngày thi", "Tình trạng"
            }
        ));
        jScrollPane1.setViewportView(TableTests);
        if (TableTests.getColumnModel().getColumnCount() > 0) {
            TableTests.getColumnModel().getColumn(0).setMinWidth(100);
            TableTests.getColumnModel().getColumn(0).setMaxWidth(100);
            TableTests.getColumnModel().getColumn(2).setMinWidth(50);
            TableTests.getColumnModel().getColumn(2).setMaxWidth(50);
            TableTests.getColumnModel().getColumn(4).setMinWidth(50);
            TableTests.getColumnModel().getColumn(4).setMaxWidth(50);
            TableTests.getColumnModel().getColumn(5).setMinWidth(50);
            TableTests.getColumnModel().getColumn(5).setMaxWidth(50);
            TableTests.getColumnModel().getColumn(6).setMinWidth(50);
            TableTests.getColumnModel().getColumn(6).setMaxWidth(50);
            TableTests.getColumnModel().getColumn(7).setMinWidth(50);
            TableTests.getColumnModel().getColumn(7).setMaxWidth(50);
            TableTests.getColumnModel().getColumn(8).setMinWidth(150);
            TableTests.getColumnModel().getColumn(8).setMaxWidth(150);
            TableTests.getColumnModel().getColumn(9).setMinWidth(100);
            TableTests.getColumnModel().getColumn(9).setMaxWidth(100);
        }

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.6;
        add(jPanel17, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jComboChude1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboChude1ActionPerformed
        searchTests();
    }//GEN-LAST:event_jComboChude1ActionPerformed

    private void jComboThoigian1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboThoigian1ActionPerformed
        searchTests();
    }//GEN-LAST:event_jComboThoigian1ActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // Xóa nội dung của các trường nhập liệu
        txtTestCode.setText("");
        txtTestTitle.setText("");
        txtNumEasy.setText("");
        txtNumMedium.setText("");
        txtNumDiff.setText("");
        txtTestLimit.setText("");

        // Đặt lại JDateChooser về null (xóa ngày)
        txtTestDate.setDate(null);

        // Đặt lại JComboBox về trạng thái mặc định
        jComboThoigian.setSelectedIndex(-1); // Không chọn gì cả
        jComboChude.setSelectedIndex(-1);    // Không chọn gì cả

        // Bỏ chọn RadioButton
        jRadioHoatdong.setSelected(false);

        // Bỏ chọn dòng trong bảng
        TableTests.clearSelection();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // Lấy dữ liệu từ giao diện
        String testCode = txtTestCode.getText().trim();
        String testTitle = txtTestTitle.getText().trim();
        String timeText = (jComboThoigian.getSelectedItem() != null) ? jComboThoigian.getSelectedItem().toString().trim() : "";
        String topicIdText = (jComboChude.getSelectedItem() != null) ? jComboChude.getSelectedItem().toString().trim() : "";
        String numEasyText = txtNumEasy.getText().trim();
        String numMediumText = txtNumMedium.getText().trim();
        String numDiffText = txtNumDiff.getText().trim();
        String testLimitText = txtTestLimit.getText().trim();
        Date testDate = txtTestDate.getDate(); // JDateChooser lấy ngày dạng Date
        boolean status = jRadioHoatdong.isSelected();

        // Kiểm tra thông tin không được để trống
        if (testCode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã bài kiểm tra!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (testTitle.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tiêu đề bài kiểm tra!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (timeText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn thời gian bài kiểm tra!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (topicIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn chủ đề bài kiểm tra!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (numEasyText.isEmpty() || numMediumText.isEmpty() || numDiffText.isEmpty() || testLimitText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng câu hỏi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (testDate == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày kiểm tra!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Chuyển đổi dữ liệu số và kiểm tra hợp lệ
        int time, topicId, numEasy, numMedium, numDiff, testLimit;
        try {
            time = Integer.parseInt(timeText);
            topicId = Integer.parseInt(topicIdText);
            numEasy = Integer.parseInt(numEasyText);
            numMedium = Integer.parseInt(numMediumText);
            numDiff = Integer.parseInt(numDiffText);
            testLimit = Integer.parseInt(testLimitText);

            if (time <= 0 || numEasy < 0 || numMedium < 0 || numDiff < 0 || testLimit <= 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ (thời gian, câu hỏi phải lớn hơn 0)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra nếu mã bài kiểm tra đã tồn tại
        if (testsBUS.isTestCodeExists(testCode)) {
            JOptionPane.showMessageDialog(this, "Mã bài kiểm tra đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra nếu chủ đề có tồn tại
        if (!testsBUS.isTopicExists(topicId)) {
            JOptionPane.showMessageDialog(this, "Chủ đề không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Chuyển đổi ngày từ `java.util.Date` sang `java.sql.Date`
        java.sql.Date sqlTestDate = new java.sql.Date(testDate.getTime());

        // Tạo đối tượng bài kiểm tra
        TestsDTO test = new TestsDTO(0, testCode, testTitle, time, topicId, numEasy, numMedium, numDiff, testLimit, sqlTestDate, status ? 1 : 0);

        // Thêm bài kiểm tra
        if (testsBUS.addTest(test)) {
            JOptionPane.showMessageDialog(this, "Thêm bài kiểm tra thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            TableTestsData(); // Cập nhật lại bảng sau khi thêm
        } else {
            JOptionPane.showMessageDialog(this, "Thêm bài kiểm tra thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // Lấy dòng đang chọn trong bảng
        int selectedRow = TableTests.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn bài kiểm tra cần sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Lấy danh sách bài kiểm tra từ BUS
        List<TestsDTO> tests = testsBUS.getAllTests();
        TestsDTO oldTest = tests.get(selectedRow); // Lấy dữ liệu cũ để so sánh

        // Lấy dữ liệu mới từ giao diện
        String testCode = txtTestCode.getText().trim();
        String testTitle = txtTestTitle.getText().trim();
        String timeText = (jComboThoigian.getSelectedItem() != null) ? jComboThoigian.getSelectedItem().toString().trim() : "";
        String topicIdText = (jComboChude.getSelectedItem() != null) ? jComboChude.getSelectedItem().toString().trim() : "";
        String numEasyText = txtNumEasy.getText().trim();
        String numMediumText = txtNumMedium.getText().trim();
        String numDiffText = txtNumDiff.getText().trim();
        String testLimitText = txtTestLimit.getText().trim();
        Date testDate = txtTestDate.getDate();
        boolean status = jRadioHoatdong.isSelected();

        // Kiểm tra thông tin không được để trống
        if (testCode.isEmpty() || testTitle.isEmpty() || timeText.isEmpty() || topicIdText.isEmpty()
                || numEasyText.isEmpty() || numMediumText.isEmpty() || numDiffText.isEmpty() || testLimitText.isEmpty() || testDate == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Chuyển đổi dữ liệu số và kiểm tra hợp lệ
        int time, topicId, numEasy, numMedium, numDiff, testLimit;
        try {
            time = Integer.parseInt(timeText);
            topicId = Integer.parseInt(topicIdText);
            numEasy = Integer.parseInt(numEasyText);
            numMedium = Integer.parseInt(numMediumText);
            numDiff = Integer.parseInt(numDiffText);
            testLimit = Integer.parseInt(testLimitText);

            if (time <= 0 || numEasy < 0 || numMedium < 0 || numDiff < 0 || testLimit <= 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ (thời gian, câu hỏi phải lớn hơn 0)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra nếu mã bài kiểm tra đã tồn tại nhưng không phải của bài kiểm tra đang sửa
        if (!testCode.equals(oldTest.getCode()) && testsBUS.isTestCodeExists(testCode)) {
            JOptionPane.showMessageDialog(this, "Mã bài kiểm tra đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra nếu chủ đề có tồn tại
        if (!testsBUS.isTopicExists(topicId)) {
            JOptionPane.showMessageDialog(this, "Chủ đề không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Chuyển đổi ngày từ `java.util.Date` sang `java.sql.Date`
        java.sql.Date sqlTestDate = new java.sql.Date(testDate.getTime());

        // Tạo đối tượng bài kiểm tra mới
        TestsDTO newTest = new TestsDTO(oldTest.getId(), testCode, testTitle, time, topicId, numEasy, numMedium, numDiff, testLimit, sqlTestDate, status ? 1 : 0);

        // Kiểm tra xem thông tin mới có khác thông tin cũ không
        if (newTest.equals(oldTest)) {
            JOptionPane.showMessageDialog(this, "Thông tin mới không có gì thay đổi!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Cập nhật bài kiểm tra
        if (testsBUS.updateTest(newTest)) {
            JOptionPane.showMessageDialog(this, "Cập nhật bài kiểm tra thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            TableTestsData(); // Cập nhật lại bảng sau khi sửa
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật bài kiểm tra thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // Lấy dòng đang chọn trong bảng
        int selectedRow = TableTests.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn bài kiểm tra cần xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Lấy danh sách bài kiểm tra từ BUS
        List<TestsDTO> tests = testsBUS.getAllTests();
        TestsDTO selectedTest = tests.get(selectedRow);

        // Kiểm tra xem có bài thi nào liên quan không
        if (testsBUS.hasExams(selectedTest.getId())) {
            JOptionPane.showMessageDialog(this, "Không thể xóa! Bài kiểm tra này đang có bài thi liên quan.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Xác nhận xóa
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa bài kiểm tra này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // Thực hiện xóa
        if (testsBUS.deleteTest(selectedTest.getId())) {
            JOptionPane.showMessageDialog(this, "Xóa bài kiểm tra thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            TableTestsData(); // Cập nhật lại bảng sau khi xóa
        } else {
            JOptionPane.showMessageDialog(this, "Xóa bài kiểm tra thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        searchTests();
    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        searchTests();
    }//GEN-LAST:event_btnSearchActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TableTests;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JComboBox<String> jComboChude;
    private javax.swing.JComboBox<String> jComboChude1;
    private javax.swing.JComboBox<String> jComboThoigian;
    private javax.swing.JComboBox<String> jComboThoigian1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioHoatdong;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtNumDiff;
    private javax.swing.JTextField txtNumEasy;
    private javax.swing.JTextField txtNumMedium;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTestCode;
    private com.toedter.calendar.JDateChooser txtTestDate;
    private javax.swing.JTextField txtTestLimit;
    private javax.swing.JTextField txtTestTitle;
    // End of variables declaration//GEN-END:variables
}
