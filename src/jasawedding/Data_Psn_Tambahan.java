package jasawedding;

import Koneksi.koneksi;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.util.HashMap;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class Data_Psn_Tambahan extends javax.swing.JFrame {

    public final Connection conn = koneksi.getConnection();
//    DefaultTableModel table = new DefaultTableModel();
    private DefaultTableModel tabmode;
    private DefaultTableModel model;
    static boolean maximixed = true;
    int xMouse;
    int yMouse;

    public Data_Psn_Tambahan(java.awt.Frame parent, boolean modal) {
        initComponents();
        aktif();
        tanggal();
        id_auto();
        dataTable();
        lebarKolom();
        findAllPaket();
        tampilListIDpesanan();
        total();
    }

    private void aktif() {
        txtIDnota.setEnabled(true);
        txtnama.setEnabled(true);
        txtjml.setEnabled(true);
        txtharga.setEnabled(true);
        txtket.setEnabled(true);
        txtCari.setEnabled(true);
        txtkdPegawai.setEnabled(true);
        txtkdCm.setEnabled(true);
        txtsatuan.setEnabled(true);
        txtTotal.setEnabled(true);
        txtDp.setEnabled(true);
        txtSisa.setEnabled(true);
        txtStatus.setEnabled(true);
        txtcaripaket.setEnabled(true);
    }

    protected void kosong() {
        txtIDnota.setSelectedItem("");
        txtkdTambah.setText("");
        txtkdTambah.setEnabled(false);
        txtnama.setText("");
        txtjml.setText("");
        txtsatuan.setText("");
        txtharga.setText("");
        txtket.setText("");
        txtCari.setText("");
        txtkdPegawai.setText("");
        txtkdCm.setText("");
        txtTotal.setText("");
        txtDp.setText("");
        txtSisa.setText("");
        txtStatus.setSelectedItem("");
        id_auto();
    }
    public void noTable() {
        int Baris = tabmode.getRowCount();
        for (int a = 0; a < Baris; a++) {
            String nomor = String.valueOf(a + 1);
            tabmode.setValueAt(nomor + ".", a, 0);
        }
    }
    public void tanggal() {
        Date tgl = new Date();
        btntgl.setDate(tgl);
    }
    public void dataTable() {
        Object[] Baris = {"No", "ID", "Nama", "Jumlah", "Satuan", "Harga", "Keterangan"};
        tabmode = new DefaultTableModel(null, Baris);
        tabelkeranjang.setModel(tabmode);
        String sql = "select * from  tb_keranjang order by kdTambah asc";
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                String kdTambah = hasil.getString("kdTambah");
                String nama = hasil.getString("nama");
                String jumlah = hasil.getString("jumlah");
                String satuan = hasil.getString("satuan");
                String harga = hasil.getString("harga");
                String ket = hasil.getString("ket");
                String[] data = {"", kdTambah, nama, jumlah, satuan, harga, ket};
                tabmode.addRow(data);
                noTable();
            }
        } catch (Exception e) {
        }
    }
    public void lebarKolom() {
        TableColumn column;
        tabelkeranjang.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        column = tabelkeranjang.getColumnModel().getColumn(0);
        column.setPreferredWidth(40);
        column = tabelkeranjang.getColumnModel().getColumn(1);
        column.setPreferredWidth(50);
        column = tabelkeranjang.getColumnModel().getColumn(2);
        column.setPreferredWidth(100);
        column = tabelkeranjang.getColumnModel().getColumn(3);
        column.setPreferredWidth(60);
        column = tabelkeranjang.getColumnModel().getColumn(4);
        column.setPreferredWidth(100);
        column = tabelkeranjang.getColumnModel().getColumn(5);
        column.setPreferredWidth(100);
        column = tabelkeranjang.getColumnModel().getColumn(6);
        column.setPreferredWidth(200);
    }
    public void id_auto() {
        try {
            java.sql.Statement stat = conn.createStatement();
            String sql = "select max(right(kdTambah,3))as no from tb_transaksi";
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next()) {
                if (rs.first() == false) {
                    txtkdTambah.setText("001");
                } else {
                    rs.last();
                    int set_id = rs.getInt(1) + 1;
                    String no = String.valueOf(set_id);
                    int id_next = no.length();
                    for (int a = 0; a < 3 - id_next; a++) {
                        no = "0" + no;
                    }
                    txtkdTambah.setText("0" + no);
                }
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR: \n" + e.toString(),
                    "Kesalahan", JOptionPane.WARNING_MESSAGE);
        }
    }
    private void filterNumber(KeyEvent keyEvent) {
        if (!Character.isDigit(keyEvent.getKeyChar())) {
            keyEvent.consume();
        }
    }
    private void tampilListIDpesanan() {
        try {
            String sql = "SELECT * FROM tb_pesanan";
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                txtIDnota.addItem(hasil.getString("IDnota"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void total() {
        int jumlahBaris = tabelkeranjang.getRowCount();
        int totalBiaya = 0;
        int jumlah, harga;
        try {
            for (int i = 0; i < jumlahBaris; i++) {
                jumlah = Integer.parseInt(tabelkeranjang.getValueAt(i, 3).toString());
                harga = Integer.parseInt(tabelkeranjang.getValueAt(i, 4).toString());
                totalBiaya = totalBiaya + (jumlah * harga);
            }
            txtTotal.setText(String.valueOf(totalBiaya));
            totalBiaya = Integer.parseInt(txtTotal.getText());
            int DP = (int) (0.7 * (totalBiaya));
            txtDp.setText(Integer.toString(DP));
            totalBiaya = Integer.parseInt(txtTotal.getText());
            int Dp = Integer.parseInt(txtDp.getText());
            int sisa = totalBiaya - Dp;
            txtSisa.setText(Integer.toString(sisa));
            int Harga = Integer.parseInt(txtsatuan.getText());
            int Jumlah = Integer.parseInt(txtjml.getText());
            int getTotal = Harga * Jumlah;
            txtharga.setText(Integer.toString(getTotal));
        } catch (Exception e) {
        }
    }
    private void simpan() {
        String IDnota = (String) txtIDnota.getSelectedItem();
        String kdPegawai = txtkdPegawai.getText();
        String kdCm = txtkdCm.getText();
        String Total = txtTotal.getText();
        String DP = txtDp.getText();
        String sisa = txtSisa.getText();
        String SBayar = (String) txtStatus.getSelectedItem();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        String tgl = String.valueOf(date.format(btntgl.getDate()));
        //query untuk memasukan data
        String query = "INSERT INTO `tb_Pembayaran` (`id`,`IDnota`, `kdPegawai`, `kdCm`, `tgl`, `Total`, `DP`, `sisa`, `SBayar`) "
                + "VALUES ( NULL, '" + IDnota + "', '" + kdPegawai + "', '" + kdCm + "', '" + tgl + "', '" + Total + "', '" + DP + "','" + sisa + "', '" + SBayar + "')";
        try {
            //menyiapkan statement untuk di eksekusi
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(query);
            ps.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Data Masuk SIMPAN");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        kosong();
    }
    private void Nota() {
        try {
            HashMap<String, Object> params = new HashMap<String, Object>();
            InputStream file = getClass().getResourceAsStream("/Laporan/Struk.jrxml");
            JasperDesign JasperDesign = JRXmlLoader.load(file);
            JasperReport JasperReport = JasperCompileManager.compileReport(JasperDesign);
            params.put("idPesan", Integer.parseInt((String) txtIDnota.getSelectedItem()));
            JasperPrint JasperPrint = JasperFillManager.fillReport(JasperReport, params, conn);
            JasperViewer.viewReport(JasperPrint, false);
        } catch (JRException e) {
            System.out.println(e);
        }
    }
    public void findAllPaket() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Kode Paket");
        model.addColumn("Nama ");
        model.addColumn("Harga");
        //menampilkan data database kedalam tabel
        try {
            String sql = "SELECT * FROM tb_paket";
            java.sql.Connection koneksi = conn;
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                model.addRow(new Object[]{hasil.getString(1), hasil.getString(2), hasil.getString(3),});
            }
            tabelPaket.setModel(model);
        } catch (Exception e) {
        }
    }
    public void dataPaket() {
        try {
            String sql = "SELECT * FROM tb_paket where namaPkt = '" + txtnama.getText().toString() + "'";
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            if (hasil.next()) {
                txtData.setText(hasil.getString("data"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    public void reset() {
        try {
            String clear = "TRUNCATE `tb_keranjang`";
            PreparedStatement stat = (PreparedStatement) conn.prepareStatement(clear);
            stat.execute();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            dataTable();
            kosong();
        }
    }
    
    public void pencarian(String sql) {
        Object[] Baris = {"No", "ID", "Nama", "Jumlah", "Satuan", "Harga", "Keterangan"};
        tabmode = new DefaultTableModel(null, Baris);
        tabelkeranjang.setModel(tabmode);
        int brs = tabelkeranjang.getRowCount();
        for (int i = 0; 1 < brs; i++) {
            tabmode.removeRow(1);
        }
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                String kdTambah = hasil.getString("kdTambah");
                String nama = hasil.getString("nama");
                String jumlah = hasil.getString("jumlah");
                String satuan = hasil.getString("satuan");
                String harga = hasil.getString("harga");
                String ket = hasil.getString("ket");
                String[] data = {"", kdTambah, nama, jumlah, satuan, harga, ket};
                tabmode.addRow(data);
                noTable();
            }
        } catch (Exception e) {
        }
    }
    public void pencarian2(String sql) {
        Object[] Baris = {"Kode", "Nama Paket", "Harga"};
        model = new DefaultTableModel(null, Baris);
        tabelPaket.setModel(model);
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                String KdPaket = hasil.getString("KdPaket");
                String Nama = hasil.getString("namaPkt");
                String Harga = hasil.getString("harga");
                String[] data = {KdPaket, Nama, Harga};
                model.addRow(data);
                noTable();
            }
        } catch (Exception e) {
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnUbah = new javax.swing.JButton();
        btnBersih = new javax.swing.JButton();
        txtTotal = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelkeranjang = new javax.swing.JTable();
        txtCari = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtkdPegawai = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtkdCm = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btntgl = new com.toedter.calendar.JDateChooser();
        txtkdTambah = new javax.swing.JTextField();
        txtnama = new javax.swing.JTextField();
        txtjml = new javax.swing.JTextField();
        txtsatuan = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtharga = new javax.swing.JTextField();
        txtket = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        btnSimpan = new javax.swing.JButton();
        txtIDnota = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtDp = new javax.swing.JTextField();
        txtSisa = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtStatus = new javax.swing.JComboBox<>();
        btnSimpan1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelPaket = new javax.swing.JTable();
        txtcaripaket = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtData = new javax.swing.JTextArea();
        jLabel19 = new javax.swing.JLabel();
        btnUbah1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/images/irma.png")).getImage());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(59, 172, 182));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Transaksi");

        jButton1.setText("X");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                .addContainerGap())
        );

        btnHapus.setBackground(new java.awt.Color(102, 102, 255));
        btnHapus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnUbah.setBackground(new java.awt.Color(102, 102, 255));
        btnUbah.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnUbah.setText("Ubah");
        btnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahActionPerformed(evt);
            }
        });

        btnBersih.setBackground(new java.awt.Color(102, 102, 255));
        btnBersih.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnBersih.setText("Reset");
        btnBersih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBersihActionPerformed(evt);
            }
        });

        txtTotal.setEditable(false);
        txtTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalActionPerformed(evt);
            }
        });
        txtTotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTotalKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTotalKeyTyped(evt);
            }
        });

        tabelkeranjang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
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
        tabelkeranjang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelkeranjangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelkeranjang);
        if (tabelkeranjang.getColumnModel().getColumnCount() > 0) {
            tabelkeranjang.getColumnModel().getColumn(2).setHeaderValue("Title 3");
            tabelkeranjang.getColumnModel().getColumn(3).setHeaderValue("Title 4");
        }

        txtCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariActionPerformed(evt);
            }
        });
        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCariKeyTyped(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("ID Pesanan");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("ID Pegawai");

        txtkdPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtkdPegawaiActionPerformed(evt);
            }
        });
        txtkdPegawai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtkdPegawaiKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtkdPegawaiKeyTyped(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("ID Klien");

        txtkdCm.setEditable(false);
        txtkdCm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtkdCmKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtkdCmKeyTyped(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Tgl Transaksi");

        txtkdTambah.setEditable(false);

        txtnama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnamaKeyPressed(evt);
            }
        });

        txtjml.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtjmlKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtjmlKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtjmlKeyTyped(evt);
            }
        });

        txtsatuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtsatuanActionPerformed(evt);
            }
        });
        txtsatuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtsatuanKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("ID");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Nama");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Jumlah");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Satuan");

        txtharga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txthargaActionPerformed(evt);
            }
        });
        txtharga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txthargaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txthargaKeyReleased(evt);
            }
        });

        txtket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtketActionPerformed(evt);
            }
        });
        txtket.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtketKeyPressed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Harga ");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Keterangan");

        jPanel4.setBackground(new java.awt.Color(102, 102, 255));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Data Keranjang");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnSimpan.setBackground(new java.awt.Color(102, 102, 255));
        btnSimpan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSimpan.setText("Tambah Keranjang");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        txtIDnota.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtIDnota.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtIDnota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDnotaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtnama, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtjml, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11))
                                .addGap(51, 51, 51))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btntgl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtkdPegawai)
                            .addComponent(txtkdCm)
                            .addComponent(txtkdTambah, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(txtIDnota, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtsatuan, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtharga, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtket, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 51, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIDnota, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtkdPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtkdCm, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btntgl, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtkdTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtnama, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtjml, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtsatuan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtharga, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(txtket, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setText("Total Harga           :");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setText("DP 70%                 :");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel15.setText("Sisa Bayar             :");

        txtDp.setEditable(false);
        txtDp.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtDp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDpActionPerformed(evt);
            }
        });
        txtDp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDpKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDpKeyTyped(evt);
            }
        });

        txtSisa.setEditable(false);
        txtSisa.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtSisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSisaActionPerformed(evt);
            }
        });
        txtSisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSisaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSisaKeyTyped(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText("KERANJANG");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel18.setText("Status                    :");

        txtStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BELUM LUNAS", "LUNAS" }));

        btnSimpan1.setBackground(new java.awt.Color(59, 172, 182));
        btnSimpan1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSimpan1.setText("Simpan");
        btnSimpan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpan1ActionPerformed(evt);
            }
        });

        tabelPaket.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        tabelPaket.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelPaketMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabelPaket);

        txtcaripaket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcaripaketActionPerformed(evt);
            }
        });
        txtcaripaket.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcaripaketKeyTyped(evt);
            }
        });

        txtData.setColumns(20);
        txtData.setRows(5);
        jScrollPane1.setViewportView(txtData);

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setText("DATA ISI PAKET");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txtcaripaket, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(129, Short.MAX_VALUE)
                .addComponent(jLabel19)
                .addGap(119, 119, 119))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(txtcaripaket, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel19)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnUbah1.setBackground(new java.awt.Color(102, 102, 255));
        btnUbah1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnUbah1.setText("Bersih");
        btnUbah1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbah1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtDp, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtSisa, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66)
                        .addComponent(jLabel17))
                    .addComponent(btnSimpan1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnBersih, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnUbah1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(16, 16, 16))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBersih, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUbah1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(46, 46, 46)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDp, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSisa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSimpan1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(62, 62, 62))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtketActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtketActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
//        keranjang();
        String sqlKeranjang = "insert into tb_keranjang values (?,?,?,?,?,?,?,?,?,?)";
        String sqlTransaksi = "insert into tb_transaksi values (?,?,?,?,?,?,?,?,?,?)";
        String tampilan = "yyyy-MM-dd";
        SimpleDateFormat fm = new SimpleDateFormat(tampilan);
        String tanggal = String.valueOf(fm.format(btntgl.getDate()));
        try {
            PreparedStatement stat = conn.prepareStatement(sqlKeranjang);
            stat.setString(1, (String) txtIDnota.getSelectedItem());
            stat.setString(2, txtkdPegawai.getText());
            stat.setString(3, txtkdCm.getText());
            stat.setString(4, tanggal.toString());
            stat.setString(5, txtkdTambah.getText());
            stat.setString(6, txtnama.getText());
            stat.setString(7, txtjml.getText());
            stat.setString(8, txtsatuan.getText());
            stat.setString(9, txtharga.getText());
            stat.setString(10, txtket.getText());
            stat.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Tambah" + e);
        }
        try {
            PreparedStatement stat = conn.prepareStatement(sqlTransaksi);
            stat.setString(1, (String) txtIDnota.getSelectedItem());
            stat.setString(2, txtkdPegawai.getText());
            stat.setString(3, txtkdCm.getText());
            stat.setString(4, tanggal.toString());
            stat.setString(5, txtkdTambah.getText());
            stat.setString(6, txtnama.getText());
            stat.setString(7, txtjml.getText());
            stat.setString(8, txtsatuan.getText());
            stat.setString(9, txtharga.getText());
            stat.setString(10, txtket.getText());
            stat.executeUpdate();
            
            txtnama.setText(null);
            txtjml.setText(null);
            txtsatuan.setText(null);
            txtharga.setText(null);
            txtket.setText(null);
            dataTable();
            lebarKolom();
            id_auto();
            total();
            txtkdTambah.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Diubah" + e);
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
        // BTN UBAH
        String Keranjang = "update tb_keranjang set IDnota=?,kdPegawai=?, kdCm=?, tgl=?,kdTambah=?,nama=?,jumlah=?, satuan=?,harga=?, ket=? where kdTambah='" + txtkdTambah.getText() + "'";
        String Transaksi = "update tb_transaksi set IDnota=?,kdPegawai=?, kdCm=?, tgl=?,kdTambah=?,nama=?,jumlah=?, satuan=?,harga=?, ket=? where kdTambah='" + txtkdTambah.getText() + "'";
        String tampilan = "yyyy-MM-dd";
        SimpleDateFormat fm = new SimpleDateFormat(tampilan);
        String tanggal = String.valueOf(fm.format(btntgl.getDate()));
        try {
            PreparedStatement stat = conn.prepareStatement(Keranjang);
            stat.setString(1, (String) txtIDnota.getSelectedItem());
            stat.setString(2, txtkdPegawai.getText());
            stat.setString(3, txtkdCm.getText());
            stat.setString(4, tanggal.toString());
            stat.setString(5, txtkdTambah.getText());
            stat.setString(6, txtnama.getText());
            stat.setString(7, txtjml.getText());
            stat.setString(8, txtsatuan.getText());
            stat.setString(9, txtharga.getText());
            stat.setString(10, txtket.getText());
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
            // coba lagi
            total();
            dataTable();
            id_auto();
            txtkdTambah.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Diubah" + e);
        }
        try {
            PreparedStatement stat = conn.prepareStatement(Transaksi);
            stat.setString(1, (String) txtIDnota.getSelectedItem());
            stat.setString(2, txtkdPegawai.getText());
            stat.setString(3, txtkdCm.getText());
            stat.setString(4, tanggal.toString());
            stat.setString(5, txtkdTambah.getText());
            stat.setString(6, txtnama.getText());
            stat.setString(7, txtjml.getText());
            stat.setString(8, txtsatuan.getText());
            stat.setString(9, txtharga.getText());
            stat.setString(10, txtket.getText());
            stat.executeUpdate();
            System.out.println(stat.toString());
            total();
            dataTable();
            txtnama.setText(null);
            txtjml.setText(null);
            txtsatuan.setText(null);
            txtharga.setText(null);
            txtket.setText(null);
            txtkdTambah.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Diubah" + e);
        }
    }//GEN-LAST:event_btnUbahActionPerformed

    private void btnBersihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBersihActionPerformed
        // BTN Bersih  
        reset();
    }//GEN-LAST:event_btnBersihActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // BTN HAPUS
        int ok = JOptionPane.showConfirmDialog(null, " Apakah Anda Yakin Ingin "
                + "Menghapus Data", "Konfirmasi Dialog", JOptionPane.YES_NO_OPTION);
        if (ok == 0) {
            // iya iya
            String Keranjang = "delete from tb_keranjang where kdTambah='" + txtkdTambah.getText() + "'";
            String Transaksi = "delete from tb_transaksi where kdTambah='" + txtkdTambah.getText() + "'";
            try {
                PreparedStatement stat = conn.prepareStatement(Keranjang);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                kosong();
                dataTable();
                txtkdTambah.requestFocus();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Data Gagal Dihapus" + e);
            }
            try {
                PreparedStatement stat = conn.prepareStatement(Transaksi);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                kosong();
                dataTable();
                txtkdTambah.requestFocus();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Data Gagal Dihapus" + e);
            }
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCariActionPerformed

    private void txthargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txthargaActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txthargaActionPerformed

    private void tabelkeranjangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelkeranjangMouseClicked
        // Tabel
        int bar = tabelkeranjang.getSelectedRow();
        String a = tabmode.getValueAt(bar, 0).toString();
        String b = tabmode.getValueAt(bar, 1).toString();
        String c = tabmode.getValueAt(bar, 2).toString();
        String d = tabmode.getValueAt(bar, 3).toString();
        String e = tabmode.getValueAt(bar, 4).toString();
        String f = tabmode.getValueAt(bar, 5).toString();
        String g = tabmode.getValueAt(bar, 6).toString();
        txtkdTambah.setText(b);
        txtnama.setText(c);
        txtjml.setText(d);
        txtsatuan.setText(e);
        txtharga.setText(f);
        txtket.setText(g);
    }//GEN-LAST:event_tabelkeranjangMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        new MenuUtama().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtsatuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtsatuanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsatuanActionPerformed

    private void txtjmlKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtjmlKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtsatuan.requestFocus();
        }
    }//GEN-LAST:event_txtjmlKeyPressed

    private void txtnamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnamaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtjml.requestFocus();
        }
    }//GEN-LAST:event_txtnamaKeyPressed

    private void txthargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txthargaKeyPressed

    }//GEN-LAST:event_txthargaKeyPressed

    private void txtsatuanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsatuanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtharga.requestFocus();
        }
    }//GEN-LAST:event_txtsatuanKeyPressed

    private void txthargaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txthargaKeyReleased
        total();
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtket.requestFocus();
        }
    }//GEN-LAST:event_txthargaKeyReleased

    private void txtjmlKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtjmlKeyReleased
//       
    }//GEN-LAST:event_txtjmlKeyReleased

    private void txtjmlKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtjmlKeyTyped
        filterNumber(evt);
    }//GEN-LAST:event_txtjmlKeyTyped

    private void txtketKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtketKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnSimpanActionPerformed(new ActionEvent(evt.getSource(), evt.getID(), "Key Press Login"));
        }
    }//GEN-LAST:event_txtketKeyPressed

    private void txtkdPegawaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtkdPegawaiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtkdPegawaiKeyPressed

    private void txtkdPegawaiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtkdPegawaiKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtkdPegawaiKeyTyped

    private void txtkdCmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtkdCmKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtkdCmKeyPressed

    private void txtkdCmKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtkdCmKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtkdCmKeyTyped

    private void txtkdPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtkdPegawaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtkdPegawaiActionPerformed

    private void txtTotalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalKeyTyped

    }//GEN-LAST:event_txtTotalKeyTyped

    private void txtTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalActionPerformed

    private void txtDpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDpActionPerformed

    private void txtDpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDpKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDpKeyTyped

    private void txtSisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSisaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSisaActionPerformed

    private void txtSisaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSisaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSisaKeyTyped

    private void txtIDnotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDnotaActionPerformed
        try {
            String sql = "SELECT * FROM tb_pesanan where IDnota = '" + (String) txtIDnota.getSelectedItem() + "'";
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            if (hasil.next()) {
                txtkdCm.setText(hasil.getString("kdCm"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_txtIDnotaActionPerformed

    private void btnSimpan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpan1ActionPerformed
//        BTN simpan Data Pembayaran
        simpan();
        Nota();
        reset();
    }//GEN-LAST:event_btnSimpan1ActionPerformed

    private void txtDpKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDpKeyReleased

    }//GEN-LAST:event_txtDpKeyReleased

    private void txtTotalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalKeyReleased

    }//GEN-LAST:event_txtTotalKeyReleased

    private void txtSisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSisaKeyReleased

    }//GEN-LAST:event_txtSisaKeyReleased

    private void txtcaripaketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcaripaketActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtcaripaketActionPerformed

    private void tabelPaketMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelPaketMouseClicked
        int bar = tabelPaket.getSelectedRow();
        String a = tabelPaket.getValueAt(bar, 0).toString();
        String b = tabelPaket.getValueAt(bar, 1).toString();
        String c = tabelPaket.getValueAt(bar, 2).toString();
        txtnama.setText(b);
        txtsatuan.setText(c);
        dataPaket();
    }//GEN-LAST:event_tabelPaketMouseClicked

    private void btnUbah1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbah1ActionPerformed
        // TODO add your handling code here:
        //txtkdTambah.setText("");
        txtkdTambah.setEnabled(false);
        id_auto();
        txtnama.setText("");
        txtjml.setText("");
        txtsatuan.setText("");
        txtharga.setText("");
        txtket.setText("");
    }//GEN-LAST:event_btnUbah1ActionPerformed

    private void txtCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyTyped
        String sqlPencarian = "select * from tb_keranjang where KdTambah like '%" + txtCari.getText() + "%' or nama like '%" + txtCari.getText() + "%'";
        pencarian(sqlPencarian);
        lebarKolom();
    }//GEN-LAST:event_txtCariKeyTyped

    private void txtcaripaketKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcaripaketKeyTyped
       String sqlPencarian = "select * from tb_paket where KdPaket like '%" + txtcaripaket.getText() + "%' or namaPkt like '%" + txtcaripaket.getText() + "%'";
       pencarian2(sqlPencarian);
    }//GEN-LAST:event_txtcaripaketKeyTyped

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
            java.util.logging.Logger.getLogger(Data_Psn_Tambahan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Data_Psn_Tambahan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Data_Psn_Tambahan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Data_Psn_Tambahan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                Data_Psn_Tambahan dialog = new Data_Psn_Tambahan(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnBersih;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnSimpan1;
    private javax.swing.JButton btnUbah;
    private javax.swing.JButton btnUbah1;
    private com.toedter.calendar.JDateChooser btntgl;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tabelPaket;
    private javax.swing.JTable tabelkeranjang;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextArea txtData;
    private javax.swing.JTextField txtDp;
    private javax.swing.JComboBox<String> txtIDnota;
    private javax.swing.JTextField txtSisa;
    private javax.swing.JComboBox<String> txtStatus;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtcaripaket;
    private javax.swing.JTextField txtharga;
    private javax.swing.JTextField txtjml;
    private javax.swing.JTextField txtkdCm;
    private javax.swing.JTextField txtkdPegawai;
    private javax.swing.JTextField txtkdTambah;
    private javax.swing.JTextField txtket;
    private javax.swing.JTextField txtnama;
    private javax.swing.JTextField txtsatuan;
    // End of variables declaration//GEN-END:variables
}
