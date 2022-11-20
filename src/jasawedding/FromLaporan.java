package jasawedding;

import Koneksi.koneksi;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class FromLaporan extends javax.swing.JFrame {

    public final Connection conn = new koneksi().getConnection();
    Map param = new HashMap();
    JasperReport JasRep;
    JasperPrint JasPri;
    JasperDesign JasDes;

    public void tanggal() {
        Date tgl = new Date();
        tglAwal.setDate(tgl);
        tglAkhir.setDate(tgl);
    }

    public FromLaporan(java.awt.Frame parent, boolean modal) {
        initComponents();
    }
//    Pelanggan

    public void findAllPelanggan() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Klien");
        model.addColumn("Nama Klien");
        model.addColumn("No.Tlp");
        model.addColumn("Email");
        model.addColumn("Alamat");
        //menampilkan data database kedalam tabel
        try {
            String sql = "SELECT * FROM tb_pelanggan";
            java.sql.Connection koneksi = conn;
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                model.addRow(new Object[]{hasil.getString(1), hasil.getString(2), hasil.getString(3), hasil.getString(4), hasil.getString(5)});
            }
            table.setModel(model);
        } catch (Exception e) {
        }
    }

    //    Pegawai
    public void findAllPegawai() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Pegawai");
        model.addColumn("Nama Pegawai");
        model.addColumn("Jenis Kelamin");
        model.addColumn("Status");
        model.addColumn("Tgl Lahir");
        model.addColumn("No.Tlp");
        model.addColumn("Email");
        model.addColumn("Alamat");
        //menampilkan data database kedalam tabel
        try {
            String sql = "SELECT * FROM tb_pegawai";
            java.sql.Connection koneksi = conn;
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);

            while (hasil.next()) {
                model.addRow(new Object[]{hasil.getString(1), hasil.getString(2), hasil.getString(3), hasil.getString(4),
                    hasil.getString(5), hasil.getString(6), hasil.getString(7), hasil.getString(8)});
            }
            table.setModel(model);
        } catch (Exception e) {
        }
    }

    //    Pesanan
    public void findAllPesanan(String fromDate, String toDate) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Pesanan");
        model.addColumn("Nama Pengantin");
        model.addColumn("ID Klien");
        model.addColumn("Nama Klien");
        model.addColumn("Nama Paket");
        model.addColumn("Tgl Acara");
        model.addColumn("Tempat");
        model.addColumn("jam");
        model.addColumn("Alamat");
        //menampilkan data database kedalam tabel
        try {
            String sql = "";
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat fm = new SimpleDateFormat(pattern);
            String from = String.valueOf(fm.format(tglAwal.getDate()));
            String to = String.valueOf(fm.format(tglAkhir.getDate()));
            if (from.equals("") && to.equals("")) {
                sql = "SELECT * FROM tb_pesanan";
            } else {
                System.out.println(from + " UNTIL " + to);
                sql = "SELECT * FROM tb_pesanan WHERE tgl_Acara BETWEEN '" + from + "' AND '" + to + "'";
            }
            java.sql.Connection koneksi = conn;
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);

            while (hasil.next()) {
                model.addRow(new Object[]{hasil.getString(1), hasil.getString(2), hasil.getString(3), hasil.getString(4), hasil.getString(5),
                    hasil.getString(6), hasil.getString(7), hasil.getString(8), hasil.getString(9)});
            }
            table.setModel(model);
        } catch (Exception e) {
        }
    }
//    paket 

    public void findAllPaket() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Paket");
        model.addColumn("Nama Paket");
        model.addColumn("Harga");
        model.addColumn("Data Paket");
        //menampilkan data database kedalam tabel
        try {
            String sql = "SELECT * FROM tb_paket";
            java.sql.Connection koneksi = conn;
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                model.addRow(new Object[]{hasil.getString(1), hasil.getString(2), hasil.getString(3), hasil.getString(4)});
            }
            table.setModel(model);
        } catch (Exception e) {
        }
    }
//    transksi

    public void findAllTransaksi(String fromDate, String toDate) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Pesanan");
        model.addColumn("ID Pegawai");
        model.addColumn("ID Klien");
        model.addColumn("Tgl Transaksi");
        model.addColumn("ID Barang");
        model.addColumn("Nama");
        model.addColumn("Jumlah");
        model.addColumn("Satuan");
        model.addColumn("Harga");
        model.addColumn("Keterangan");
        //menampilkan data database kedalam tabel
        try {
            String sql = "";
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat fm = new SimpleDateFormat(pattern);
            String from = String.valueOf(fm.format(tglAwal.getDate()));
            String to = String.valueOf(fm.format(tglAkhir.getDate()));
            if (from.equals("") && to.equals("")) {
                sql = "SELECT * FROM tb_transaksi";
            } else {
                System.out.println(from + " UNTIL " + to);
                sql = "SELECT * FROM tb_transaksi WHERE tgl BETWEEN '" + from + "' AND '" + to + "'";
            }
            java.sql.Connection koneksi = conn;
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                model.addRow(new Object[]{hasil.getString(1),
                    hasil.getString(2),
                    hasil.getString(3),
                    hasil.getString(4),
                    hasil.getString(5),
                    hasil.getString(6),
                    hasil.getString(7),
                    hasil.getString(8),
                    hasil.getString(9),
                    hasil.getString(10)});
            }
            table.setModel(model);
        } catch (Exception e) {
        }
    }
//    pembayaran

    public void findAllPembayaran(String fromDate, String toDate) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Bayar");
        model.addColumn("ID Pesanan");
        model.addColumn("ID Pegawai");
        model.addColumn("ID Klien");
        model.addColumn("Tgl Transaksi");
        model.addColumn("Total Harga");
        model.addColumn("DP");
        model.addColumn("Sisa");
        model.addColumn("Status");
        //menampilkan data database kedalam tabel
        try {
            String sql = "";
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat fm = new SimpleDateFormat(pattern);
            String from = String.valueOf(fm.format(tglAwal.getDate()));
            String to = String.valueOf(fm.format(tglAkhir.getDate()));
            if (from.equals("") && to.equals("")) {
                sql = "SELECT * FROM tb_Pembayaran";
            } else {
                System.out.println(from + " UNTIL " + to);
                sql = "SELECT * FROM tb_Pembayaran WHERE tgl BETWEEN '" + from + "' AND '" + to + "'";
            }
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                model.addRow(new Object[]{hasil.getString(1),
                    hasil.getString(2),
                    hasil.getString(3),
                    hasil.getString(4),
                    hasil.getString(5),
                    hasil.getString(6),
                    hasil.getString(7),
                    hasil.getString(8),
                    hasil.getString(9)});
            }
            table.setModel(model);
        } catch (Exception e) {
        }
    }

    public void printReportByDate(String url, String fromDate, String toDate) {
        try {

            File report = new File(url);
            JasperDesign jd = JRXmlLoader.load(report);
            param.put("fromDate", fromDate);
            param.put("toDate", toDate);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, param, koneksi.getConnection());
            JasperViewer.viewReport(jp, false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void printReport(String url) {
        try {
            File report = new File(url);
            JasperDesign jd = JRXmlLoader.load(report);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, param, koneksi.getConnection());
            JasperViewer.viewReport(jp, false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtPilihCetak = new javax.swing.JComboBox<>();
        tglAwal = new com.toedter.calendar.JDateChooser();
        tglAkhir = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/images/irma.png")).getImage());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(47, 143, 157));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("From Laporan");

        jButton2.setText("X");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton2))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Pilih Cetak Laporan");

        txtPilihCetak.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtPilihCetak.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { ">>Pilih Laporan<<", "Klien", "Pegawai", "Paket", "Pesanan", "Transaksi", "Pembayaran" }));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Dari Tanggal :");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Sampai Tanggal :");

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table.setRowHeight(25);
        jScrollPane1.setViewportView(table);

        jButton1.setText("Cari");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("Cetak");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(txtPilihCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tglAwal, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(tglAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPilihCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(tglAwal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                            .addComponent(tglAkhir, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(42, 42, 42)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //BTN CARI
        String value = txtPilihCetak.getSelectedItem().toString();
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat fm = new SimpleDateFormat(pattern);
        String fromDate = "";
        String toDate = "";
        if (tglAwal.getDate() == null && tglAkhir.getDate() == null) {
        } else {
            fromDate = fm.format(tglAwal.getDate()).toString();
            toDate = fm.format(tglAkhir.getDate()).toString();
            System.out.println(fromDate);
        }
        switch (value) {
            case "Klien":
                findAllPelanggan();
                break;
            case "Pegawai":
                findAllPegawai();
                break;
            case "Paket":
                findAllPaket();
                break;
            case "Pesanan":
                if (!fromDate.equals("") && !toDate.equals("")) {
                    findAllPesanan(fromDate, toDate);
                    break;
                }
            case "Transaksi":
                if (!fromDate.equals("") && !toDate.equals("")) {
                    findAllTransaksi(fromDate, toDate);
                    break;
                }
            case "Pembayaran":
                if (!fromDate.equals("") && !toDate.equals("")) {
                    findAllPembayaran(fromDate, toDate);
                    break;
                }
                break;
            default:
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // BTN CETAK
        String value = txtPilihCetak.getSelectedItem().toString();
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat fm = new SimpleDateFormat(pattern);
        String fromDate = "";
        String toDate = "";
        if (tglAwal.getDate() == null && tglAkhir.getDate() == null) {
        } else {
            fromDate = fm.format(tglAwal.getDate()).toString();
            toDate = fm.format(tglAkhir.getDate()).toString();
            System.out.println(fromDate);
        }
        switch (value) {
            case "Klien":
                printReport("C:\\Users\\Rahmadana Nazmi\\Documents\\NetBeansProjects\\IrmaWedding\\src\\Laporan\\Klien.jrxml");
                break;
            case "Pegawai":
                printReport("C:\\Users\\Rahmadana Nazmi\\Documents\\NetBeansProjects\\IrmaWedding\\src\\Laporan\\Pegawai.jrxml");
                break;
            case "Paket":
                printReport("C:\\Users\\Rahmadana Nazmi\\Documents\\NetBeansProjects\\IrmaWedding\\src\\Laporan\\Paket.jrxml");
                break;
            case "Pesanan":
                if (!fromDate.equals("") && !toDate.equals("")) {
                    printReportByDate("C:\\Users\\Rahmadana Nazmi\\Documents\\NetBeansProjects\\IrmaWedding\\src\\Laporan\\Pesanan.jrxml", fromDate, toDate);
                    break;
                }
            case "Transaksi":
                if (!fromDate.equals("") && !toDate.equals("")) {
                    printReportByDate("C:\\Users\\Rahmadana Nazmi\\Documents\\NetBeansProjects\\IrmaWedding\\src\\Laporan\\Transaksi.jrxml", fromDate, toDate);
                    break;
                }
            case "Pembayaran":
                if (!fromDate.equals("") && !toDate.equals("")) {
                    printReportByDate("C:\\Users\\Rahmadana Nazmi\\Documents\\NetBeansProjects\\IrmaWedding\\src\\Laporan\\Pembayaran.jrxml", fromDate, toDate);
                    break;
                }
            default:
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // BTN kembali
        new MenuUtama().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton2MouseClicked

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
            java.util.logging.Logger.getLogger(FromLaporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FromLaporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FromLaporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FromLaporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FromLaporan dialog = new FromLaporan(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    private com.toedter.calendar.JDateChooser tglAkhir;
    private com.toedter.calendar.JDateChooser tglAwal;
    private javax.swing.JComboBox<String> txtPilihCetak;
    // End of variables declaration//GEN-END:variables
}
