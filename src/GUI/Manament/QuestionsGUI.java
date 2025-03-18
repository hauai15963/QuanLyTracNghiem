package GUI.Manament;

import BUS.QuestionsBUS;
import DTO.QuestionsDTO;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class QuestionsGUI extends javax.swing.JPanel {

    private QuestionsBUS questionsBUS;

    public QuestionsGUI() {
        initComponents();
        questionsBUS = new QuestionsBUS();
        TableQuestionData();
        loadComboBoxLevels();
        loadComboBoxTopics();
        addTableListenerForQuestions();
    }

    private void TableQuestionData() {
        DefaultTableModel model = (DefaultTableModel) TableQuestionsData.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        List<QuestionsDTO> questions = questionsBUS.getAllQuestions();
        for (QuestionsDTO question : questions) {
            model.addRow(new Object[]{
                question.getId(),
                question.getContent(),
                question.getPicture(),
                question.getTopicId(),
                question.getLevel(),
                question.isStatus() ? "Hiển thị" : "Ẩn"
            });
        }
    }

    private void loadComboBoxLevels() {
        jComboDokho1.removeAllItems(); // Xóa dữ liệu cũ
        jComboDokho1.addItem("Tất cả"); // Thêm tùy chọn không chọn gì cả
        List<String> levels = questionsBUS.getAllLevels();

        for (String level : levels) {
            jComboDokho1.addItem(level);
        }

        jComboDokho1.setSelectedIndex(-1); // Không chọn gì cả
        jComboDokho.removeAllItems();
        for (String level : levels) {
            jComboDokho.addItem(level);
        }
    }

    private void loadComboBoxTopics() {
        jComboChude1.removeAllItems(); // Xóa dữ liệu cũ
        jComboChude1.addItem("Tất cả"); // Thêm tùy chọn không chọn gì cả
        List<Integer> topics = questionsBUS.getAllTopics();

        for (Integer topic : topics) {
            jComboChude1.addItem(topic.toString());
        }

        jComboChude1.setSelectedIndex(-1); // Không chọn gì cả
        jComboChude.removeAllItems();
        for (Integer topic : topics) {
            jComboChude.addItem(topic.toString());
        }
    }

    private void addTableListenerForQuestions() {
        TableQuestionsData.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                int selectedRow = TableQuestionsData.getSelectedRow();
                if (selectedRow >= 0) {
                    // Lấy dữ liệu từ JTable
                    int idQuestion = Integer.parseInt(TableQuestionsData.getValueAt(selectedRow, 0).toString());
                    String content = TableQuestionsData.getValueAt(selectedRow, 1).toString();
                    String picture = TableQuestionsData.getValueAt(selectedRow, 2) != null ? TableQuestionsData.getValueAt(selectedRow, 2).toString() : "";
                    int topicId = Integer.parseInt(TableQuestionsData.getValueAt(selectedRow, 3).toString());
                    String level = TableQuestionsData.getValueAt(selectedRow, 4).toString();
                    String status = TableQuestionsData.getValueAt(selectedRow, 5).toString();

                    // Hiển thị lên các thành phần giao diện
                    txtNoiDung.setText(content);
                    txtHinhAnh.setText(picture);
                    jComboChude.setSelectedItem(String.valueOf(topicId));
                    jComboDokho.setSelectedItem(level);
                    jRadioHoatdong.setSelected(status.equals("Hiển thị"));
                }
            }
        });
    }

    private void searchQuestions() {
        String keyword = txtSearch.getText().trim();

        // Lấy giá trị từ combo box, tránh null
        Object selectedLevelObj = jComboDokho1.getSelectedItem();
        Object selectedTopicObj = jComboChude1.getSelectedItem();

        String selectedLevel = (selectedLevelObj != null) ? selectedLevelObj.toString() : "Tất cả";
        String selectedTopic = (selectedTopicObj != null) ? selectedTopicObj.toString() : "Tất cả";

        // Xử lý topicId chỉ khi giá trị là số
        Integer topicId = null;
        if (!selectedTopic.equals("Tất cả") && selectedTopic.matches("\\d+")) {
            topicId = Integer.parseInt(selectedTopic);
        }

        // Xử lý độ khó
        String level = selectedLevel.equals("Tất cả") ? null : selectedLevel;

        // Gọi phương thức tìm kiếm
        List<QuestionsDTO> results = questionsBUS.searchQuestions(keyword, level, topicId);

        // Cập nhật dữ liệu vào bảng
        DefaultTableModel model = (DefaultTableModel) TableQuestionsData.getModel();
        model.setRowCount(0);

        for (QuestionsDTO question : results) {
            model.addRow(new Object[]{
                question.getId(),
                question.getContent(),
                question.getPicture(),
                question.getTopicId(),
                question.getLevel(),
                question.isStatus() ? "Hiển thị" : "Ẩn"
            });
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel5 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jRadioHoatdong = new javax.swing.JRadioButton();
        jComboChude = new javax.swing.JComboBox<>();
        jComboDokho = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtNoiDung = new javax.swing.JTextArea();
        txtHinhAnh = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        btnClear = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jComboChude1 = new javax.swing.JComboBox<>();
        jComboDokho1 = new javax.swing.JComboBox<>();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableQuestionsData = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 102, 102));
        setLayout(new java.awt.GridBagLayout());

        jPanel5.setBackground(new java.awt.Color(245, 245, 245));
        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), null, null));

        jPanel4.setBackground(new java.awt.Color(245, 245, 245));
        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(4, 51, 69), new java.awt.Color(4, 51, 69), null, null));

        jPanel8.setBackground(new java.awt.Color(4, 51, 69));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(245, 245, 245));
        jLabel21.setText("Quản lý Questions");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel21)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(245, 245, 245));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), null, null));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("URL hình ảnh");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 5, 20);
        jPanel2.add(jLabel7, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Nội dung câu hỏi                                ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.6;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 5, 20);
        jPanel2.add(jLabel8, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("ID chủ đề câu hỏi");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(9, 23, 4, 23);
        jPanel2.add(jLabel11, gridBagConstraints);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Mức độ khó    ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 5, 20);
        jPanel2.add(jLabel12, gridBagConstraints);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("Hoạt động");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 5, 20);
        jPanel2.add(jLabel13, gridBagConstraints);

        jRadioHoatdong.setBackground(new java.awt.Color(245, 245, 245));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 5, 20);
        jPanel2.add(jRadioHoatdong, gridBagConstraints);

        jComboChude.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jComboChude.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 5, 20);
        jPanel2.add(jComboChude, gridBagConstraints);

        jComboDokho.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jComboDokho.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 5, 20);
        jPanel2.add(jComboDokho, gridBagConstraints);

        txtNoiDung.setColumns(20);
        txtNoiDung.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNoiDung.setLineWrap(true);
        txtNoiDung.setRows(5);
        txtNoiDung.setWrapStyleWord(true);
        jScrollPane2.setViewportView(txtNoiDung);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.6;
        gridBagConstraints.weighty = 0.75;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 5, 20);
        jPanel2.add(jScrollPane2, gridBagConstraints);

        txtHinhAnh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 5, 20);
        jPanel2.add(txtHinhAnh, gridBagConstraints);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.7;
        gridBagConstraints.weighty = 0.4;
        add(jPanel5, gridBagConstraints);

        jPanel6.setBackground(new java.awt.Color(245, 245, 245));
        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), null, null));

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

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
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
        add(jPanel6, gridBagConstraints);

        jPanel7.setBackground(new java.awt.Color(245, 245, 245));
        jPanel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), null, null));

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
        jLabel29.setText("Độ khó");
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

        jComboDokho1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboDokho1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboDokho1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 30);
        jPanel9.add(jComboDokho1, gridBagConstraints);

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
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 50);
        jPanel9.add(btnSearch, gridBagConstraints);

        TableQuestionsData.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TableQuestionsData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID Câu hỏi", "Nội dung câu hỏi", "URL Hình ảnh", "ID Chủ đề câu hỏi", "Mức độ khó", "Tình trạng"
            }
        ));
        jScrollPane1.setViewportView(TableQuestionsData);
        if (TableQuestionsData.getColumnModel().getColumnCount() > 0) {
            TableQuestionsData.getColumnModel().getColumn(0).setMinWidth(100);
            TableQuestionsData.getColumnModel().getColumn(0).setMaxWidth(100);
            TableQuestionsData.getColumnModel().getColumn(2).setMinWidth(200);
            TableQuestionsData.getColumnModel().getColumn(2).setMaxWidth(200);
            TableQuestionsData.getColumnModel().getColumn(3).setMinWidth(100);
            TableQuestionsData.getColumnModel().getColumn(3).setMaxWidth(100);
            TableQuestionsData.getColumnModel().getColumn(4).setMinWidth(100);
            TableQuestionsData.getColumnModel().getColumn(4).setMaxWidth(100);
            TableQuestionsData.getColumnModel().getColumn(5).setMinWidth(100);
            TableQuestionsData.getColumnModel().getColumn(5).setMaxWidth(100);
        }

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 916, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.6;
        add(jPanel7, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int selectedRow = TableQuestionsData.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một câu hỏi để xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int questionId = Integer.parseInt(TableQuestionsData.getValueAt(selectedRow, 0).toString());

        // Xác nhận từ người dùng
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa câu hỏi này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // Kiểm tra nếu câu hỏi có câu trả lời
        if (questionsBUS.deleteQuestion(questionId)) {
            JOptionPane.showMessageDialog(this, "Xóa câu hỏi thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            TableQuestionData(); // Cập nhật bảng sau khi xóa
        } else {
            JOptionPane.showMessageDialog(this, "Không thể xóa! Vui lòng xóa câu trả lời trước.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void jComboChude1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboChude1ActionPerformed
        searchQuestions();
    }//GEN-LAST:event_jComboChude1ActionPerformed

    private void jComboDokho1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboDokho1ActionPerformed
        searchQuestions();
    }//GEN-LAST:event_jComboDokho1ActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        txtNoiDung.setText("");
        txtHinhAnh.setText("");
        jComboChude.setSelectedIndex(-1);
        jComboDokho.setSelectedIndex(-1);
        jRadioHoatdong.setSelected(false);
        TableQuestionsData.clearSelection();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        int selectedRow = TableQuestionsData.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một câu hỏi để cập nhật!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Lấy dữ liệu cũ từ bảng
        QuestionsDTO oldQuestion = new QuestionsDTO(
                Integer.parseInt(TableQuestionsData.getValueAt(selectedRow, 0).toString()),
                TableQuestionsData.getValueAt(selectedRow, 1).toString(),
                TableQuestionsData.getValueAt(selectedRow, 2).toString(),
                Integer.parseInt(TableQuestionsData.getValueAt(selectedRow, 3).toString()),
                TableQuestionsData.getValueAt(selectedRow, 4).toString(),
                TableQuestionsData.getValueAt(selectedRow, 5).toString().equals("Hiển thị")
        );

        // Lấy dữ liệu mới từ giao diện
        QuestionsDTO newQuestion = new QuestionsDTO(
                oldQuestion.getId(), // Giữ nguyên ID
                txtNoiDung.getText().trim(),
                txtHinhAnh.getText().trim(),
                Integer.parseInt(jComboChude.getSelectedItem().toString()),
                jComboDokho.getSelectedItem().toString(),
                jRadioHoatdong.isSelected()
        );

        // Gọi phương thức cập nhật
        if (questionsBUS.updateQuestion(newQuestion, oldQuestion)) {
            JOptionPane.showMessageDialog(this, "Cập nhật câu hỏi thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            TableQuestionData(); // Cập nhật bảng sau khi sửa
        } else {
            JOptionPane.showMessageDialog(this, "Không có thay đổi hoặc dữ liệu không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // Lấy dữ liệu từ giao diện
        String noiDung = txtNoiDung.getText().trim();
        String hinhAnh = txtHinhAnh.getText().trim();
        String idChudeText = jComboChude.getSelectedItem().toString().trim();
        String mucDo = jComboDokho.getSelectedItem().toString().trim();
        boolean trangThai = jRadioHoatdong.isSelected();

        // Kiểm tra đầu vào hợp lệ
        if (noiDung.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập nội dung câu hỏi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idChude;
        try {
            idChude = Integer.parseInt(idChudeText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID chủ đề không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra câu hỏi đã tồn tại chưa
        if (questionsBUS.addQuestion(new QuestionsDTO(0, noiDung, hinhAnh, idChude, mucDo, trangThai))) {
            JOptionPane.showMessageDialog(this, "Thêm câu hỏi thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            TableQuestionData(); // Cập nhật lại bảng sau khi thêm
        } else {
            JOptionPane.showMessageDialog(this, "Câu hỏi đã tồn tại hoặc chủ đề không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        searchQuestions();
    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        searchQuestions();
    }//GEN-LAST:event_btnSearchActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TableQuestionsData;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JComboBox<String> jComboChude;
    private javax.swing.JComboBox<String> jComboChude1;
    private javax.swing.JComboBox<String> jComboDokho;
    private javax.swing.JComboBox<String> jComboDokho1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioHoatdong;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txtHinhAnh;
    private javax.swing.JTextArea txtNoiDung;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
