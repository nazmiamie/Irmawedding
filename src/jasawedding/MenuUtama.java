package jasawedding;

import Koneksi.koneksi;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class MenuUtama extends javax.swing.JFrame {

    public final Connection conn = new koneksi().getConnection();
    private DefaultTableModel tabmode;

    public void noTable() {
        int Baris = tabmode.getRowCount();
        for (int a = 0; a < Baris; a++) {
            String nomor = String.valueOf(a + 1);
            tabmode.setValueAt(nomor + ".", a, 0);
        }
    }

    public void dataTable() {
        Object[] Baris = {"No", "ID Pesanan", "Tanggal Acara", "Nama Pengantin", "Nama Paket"};
        tabmode = new DefaultTableModel(null, Baris);
        tabelJadwal.setModel(tabmode);
        String sql = "select * from tb_pesanan order by IDnota asc";
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                String IDnota = hasil.getString("IDnota");
                String tgl_Acara = hasil.getString("tgl_Acara");
                String pengantin = hasil.getString("pengantin");
                String namaCm = hasil.getString("namaPkt");
                String[] data = {"", IDnota, tgl_Acara, pengantin, namaCm};
                tabmode.addRow(data);
                noTable();
            }
        } catch (SQLException e) {
        }
    }

    public void lebarKolom() {
        TableColumn column;
        tabelJadwal.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        column = tabelJadwal.getColumnModel().getColumn(0);
        column.setPreferredWidth(40);//no
        column = tabelJadwal.getColumnModel().getColumn(1);
        column.setPreferredWidth(90);//kode
        column = tabelJadwal.getColumnModel().getColumn(2);
        column.setPreferredWidth(100);//tanggal
        column = tabelJadwal.getColumnModel().getColumn(3);
        column.setPreferredWidth(180);
        column = tabelJadwal.getColumnModel().getColumn(4);
        column.setPreferredWidth(200);
    }

    public void setTanggal() {
        java.util.Date skrg = new java.util.Date();
        java.text.SimpleDateFormat kal = new java.text.SimpleDateFormat("dd/MM/yyyy");
        tgl.setText(kal.format(skrg));
    }

    public void setJam() {
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String nol_jam = "", nol_menit = "", nol_detik = "";
                java.util.Date dateTime = new java.util.Date();
                int nilai_jam = dateTime.getHours();
                int nilai_menit = dateTime.getMinutes();
                int nilai_detik = dateTime.getSeconds();
                if (nilai_jam <= 9) {
                    nol_jam = "0";
                }
                if (nilai_menit <= 9) {
                    nol_menit = "0";
                }
                if (nilai_detik <= 9) {
                    nol_detik = "0";
                }
                String waktu = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                jam.setText(waktu + ":" + menit + ":" + detik);

            }
        };
        new Timer(1000, taskPerformer).start();
    }

    public MenuUtama() {
        initComponents();
        dataTable();
        lebarKolom();
        setJam();
        setTanggal();
        panelMasterData.setVisible(false);
        panelLaporan.setVisible(false);
        panelTransaksi.setVisible(false);
        Dimension layar = Toolkit.getDefaultToolkit().getScreenSize();
        // membuat titik x dan y
        int x = layar.width / 2 - this.getSize().width / 2;
        int y = layar.height / 2 - this.getSize().height / 2;

        this.setLocation(x, y);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelUtama = new javax.swing.JPanel();
        panelHeader = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        panelMenu = new javax.swing.JPanel();
        btnMasterData = new javax.swing.JButton();
        btnTransaksi = new javax.swing.JButton();
        btnLaporan = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();
        jam = new javax.swing.JLabel();
        tgl = new javax.swing.JLabel();
        panelMasterData = new javax.swing.JPanel();
        btnDataKlien = new javax.swing.JButton();
        btnPegawai = new javax.swing.JButton();
        btnDataPesanan1 = new javax.swing.JButton();
        btnDataPesanan = new javax.swing.JButton();
        btnDataPesanan2 = new javax.swing.JButton();
        panelLaporan = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        panelTransaksi = new javax.swing.JPanel();
        btntransaksi = new javax.swing.JButton();
        btnpembayaran = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelJadwal = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/images/irma.png")).getImage());

        panelUtama.setBackground(new java.awt.Color(255, 255, 255));

        panelHeader.setBackground(new java.awt.Color(47, 143, 157));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Aplikasi Transaksi Irma Wedding");

        jButton1.setBackground(new java.awt.Color(47, 143, 157));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("x");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton12.setBackground(new java.awt.Color(47, 143, 157));
        jButton12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/minus-regular-24.png"))); // NOI18N
        jButton12.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton12.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setBackground(new java.awt.Color(47, 143, 157));
        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/windows-min-regular-24.png"))); // NOI18N
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelHeaderLayout = new javax.swing.GroupLayout(panelHeader);
        panelHeader.setLayout(panelHeaderLayout);
        panelHeaderLayout.setHorizontalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHeaderLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(233, 233, 233)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelHeaderLayout.setVerticalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelHeaderLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelHeaderLayout.createSequentialGroup()
                        .addGroup(panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(11, 11, 11))))
        );

        panelMenu.setBackground(new java.awt.Color(59, 172, 182));

        btnMasterData.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnMasterData.setForeground(new java.awt.Color(255, 255, 255));
        btnMasterData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/book-content-regular-24.png"))); // NOI18N
        btnMasterData.setText("Master Data");
        btnMasterData.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnMasterData.setContentAreaFilled(false);
        btnMasterData.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnMasterData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMasterDataMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnMasterDataMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnMasterDataMouseExited(evt);
            }
        });

        btnTransaksi.setBackground(new java.awt.Color(204, 204, 255));
        btnTransaksi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnTransaksi.setForeground(new java.awt.Color(255, 255, 255));
        btnTransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/basket-regular-24.png"))); // NOI18N
        btnTransaksi.setText("Transaksi");
        btnTransaksi.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnTransaksi.setContentAreaFilled(false);
        btnTransaksi.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTransaksiMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTransaksiMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTransaksiMouseExited(evt);
            }
        });
        btnTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTransaksiActionPerformed(evt);
            }
        });

        btnLaporan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnLaporan.setForeground(new java.awt.Color(255, 255, 255));
        btnLaporan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/receipt-regular-24.png"))); // NOI18N
        btnLaporan.setText("Laporan");
        btnLaporan.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnLaporan.setContentAreaFilled(false);
        btnLaporan.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnLaporan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLaporanMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLaporanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLaporanMouseExited(evt);
            }
        });
        btnLaporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLaporanActionPerformed(evt);
            }
        });

        btnKeluar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/exit-regular-24.png"))); // NOI18N
        btnKeluar.setText("Keluar");
        btnKeluar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnKeluar.setContentAreaFilled(false);
        btnKeluar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnKeluarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnKeluarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnKeluarMouseExited(evt);
            }
        });
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });

        jam.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jam.setForeground(new java.awt.Color(255, 255, 255));
        jam.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jam.setText("Jam");

        tgl.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tgl.setForeground(new java.awt.Color(255, 255, 255));
        tgl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tgl.setText("Tanggal");

        javax.swing.GroupLayout panelMenuLayout = new javax.swing.GroupLayout(panelMenu);
        panelMenu.setLayout(panelMenuLayout);
        panelMenuLayout.setHorizontalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnMasterData, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
            .addComponent(btnTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnLaporan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnKeluar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jam, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tgl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelMenuLayout.setVerticalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMenuLayout.createSequentialGroup()
                .addComponent(btnMasterData, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jam, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tgl, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelMasterData.setBackground(new java.awt.Color(59, 172, 182));

        btnDataKlien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDataKlien.setForeground(new java.awt.Color(255, 255, 255));
        btnDataKlien.setText("> Data Klien");
        btnDataKlien.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnDataKlien.setContentAreaFilled(false);
        btnDataKlien.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDataKlien.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnDataKlien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDataKlienActionPerformed(evt);
            }
        });

        btnPegawai.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnPegawai.setForeground(new java.awt.Color(255, 255, 255));
        btnPegawai.setText("> Data Pegawai");
        btnPegawai.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnPegawai.setContentAreaFilled(false);
        btnPegawai.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPegawaiActionPerformed(evt);
            }
        });

        btnDataPesanan1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDataPesanan1.setForeground(new java.awt.Color(255, 255, 255));
        btnDataPesanan1.setText("> Data Paket");
        btnDataPesanan1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnDataPesanan1.setContentAreaFilled(false);
        btnDataPesanan1.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnDataPesanan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDataPesanan1ActionPerformed(evt);
            }
        });

        btnDataPesanan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDataPesanan.setForeground(new java.awt.Color(255, 255, 255));
        btnDataPesanan.setText("> Data Pesanan");
        btnDataPesanan.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnDataPesanan.setContentAreaFilled(false);
        btnDataPesanan.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnDataPesanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDataPesananActionPerformed(evt);
            }
        });

        btnDataPesanan2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDataPesanan2.setForeground(new java.awt.Color(255, 255, 255));
        btnDataPesanan2.setText("> Riwayat Transaksi");
        btnDataPesanan2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnDataPesanan2.setContentAreaFilled(false);
        btnDataPesanan2.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnDataPesanan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDataPesanan2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelMasterDataLayout = new javax.swing.GroupLayout(panelMasterData);
        panelMasterData.setLayout(panelMasterDataLayout);
        panelMasterDataLayout.setHorizontalGroup(
            panelMasterDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnDataKlien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnPegawai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnDataPesanan1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnDataPesanan, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
            .addComponent(btnDataPesanan2, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
        );
        panelMasterDataLayout.setVerticalGroup(
            panelMasterDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMasterDataLayout.createSequentialGroup()
                .addComponent(btnDataKlien, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDataPesanan1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDataPesanan, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDataPesanan2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panelLaporan.setBackground(new java.awt.Color(59, 172, 182));

        jButton8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("> Form Laporan");
        jButton8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton8.setContentAreaFilled(false);
        jButton8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton8.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelLaporanLayout = new javax.swing.GroupLayout(panelLaporan);
        panelLaporan.setLayout(panelLaporanLayout);
        panelLaporanLayout.setHorizontalGroup(
            panelLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
        );
        panelLaporanLayout.setVerticalGroup(
            panelLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        panelTransaksi.setBackground(new java.awt.Color(59, 172, 182));

        btntransaksi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btntransaksi.setForeground(new java.awt.Color(255, 255, 255));
        btntransaksi.setText("> Input transksi");
        btntransaksi.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btntransaksi.setContentAreaFilled(false);
        btntransaksi.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btntransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntransaksiActionPerformed(evt);
            }
        });

        btnpembayaran.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnpembayaran.setForeground(new java.awt.Color(255, 255, 255));
        btnpembayaran.setText("> Data Pembayaran");
        btnpembayaran.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnpembayaran.setContentAreaFilled(false);
        btnpembayaran.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnpembayaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpembayaranActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelTransaksiLayout = new javax.swing.GroupLayout(panelTransaksi);
        panelTransaksi.setLayout(panelTransaksiLayout);
        panelTransaksiLayout.setHorizontalGroup(
            panelTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btntransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
            .addComponent(btnpembayaran, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
        );
        panelTransaksiLayout.setVerticalGroup(
            panelTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTransaksiLayout.createSequentialGroup()
                .addComponent(btntransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnpembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tabelJadwal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Kode Nota", "Tanggal Aacara", "Nama Klien"
            }
        ));
        tabelJadwal.setRowHeight(25);
        tabelJadwal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelJadwalMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelJadwal);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Uptodate Jadwal pesanan");

        javax.swing.GroupLayout panelUtamaLayout = new javax.swing.GroupLayout(panelUtama);
        panelUtama.setLayout(panelUtamaLayout);
        panelUtamaLayout.setHorizontalGroup(
            panelUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUtamaLayout.createSequentialGroup()
                .addComponent(panelMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelMasterData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
                .addGroup(panelUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUtamaLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(202, 202, 202))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUtamaLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(68, 68, 68))))
            .addComponent(panelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelUtamaLayout.setVerticalGroup(
            panelUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUtamaLayout.createSequentialGroup()
                .addComponent(panelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelUtamaLayout.createSequentialGroup()
                        .addComponent(panelMasterData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(81, 81, 81)
                        .addComponent(panelTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(panelLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(panelUtamaLayout.createSequentialGroup()
                        .addGap(0, 91, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(187, 187, 187))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelUtama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelUtama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTransaksiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTransaksiActionPerformed

    private void btnLaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLaporanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLaporanActionPerformed

    private void btnDataKlienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDataKlienActionPerformed
        // Data Paket 
        this.setVisible(false);
        new Data_Klien(this, rootPaneCheckingEnabled).show();
    }//GEN-LAST:event_btnDataKlienActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        this.setVisible(false);
        new FromLaporan(this, rootPaneCheckingEnabled).show();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void btnMasterDataMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMasterDataMouseEntered
        btnMasterData.setForeground(Color.black);
        btnMasterData.setBackground(new Color(47, 143, 157));
    }//GEN-LAST:event_btnMasterDataMouseEntered

    private void btnMasterDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMasterDataMouseClicked
        panelMasterData.setVisible(true);
        panelLaporan.setVisible(false);
        panelTransaksi.setVisible(false);
    }//GEN-LAST:event_btnMasterDataMouseClicked

    private void btnMasterDataMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMasterDataMouseExited
        btnMasterData.setForeground(Color.white);
        btnMasterData.setBackground(new Color(59, 172, 182));
    }//GEN-LAST:event_btnMasterDataMouseExited

    private void btnLaporanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLaporanMouseClicked
        panelMasterData.setVisible(false);
        panelLaporan.setVisible(true);
        panelTransaksi.setVisible(false);
    }//GEN-LAST:event_btnLaporanMouseClicked

    private void btnLaporanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLaporanMouseEntered
        btnLaporan.setForeground(Color.black);
        btnLaporan.setBackground(new Color(47, 143, 157));
    }//GEN-LAST:event_btnLaporanMouseEntered

    private void btnLaporanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLaporanMouseExited
        btnLaporan.setForeground(Color.white);
        btnLaporan.setBackground(new Color(59, 172, 182));
    }//GEN-LAST:event_btnLaporanMouseExited

    private void btnTransaksiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTransaksiMouseEntered
        btnTransaksi.setForeground(Color.black);
        btnTransaksi.setBackground(new Color(47, 143, 157));
    }//GEN-LAST:event_btnTransaksiMouseEntered

    private void btnTransaksiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTransaksiMouseExited
        btnTransaksi.setForeground(Color.white);
        btnTransaksi.setBackground(new Color(59, 172, 182));
    }//GEN-LAST:event_btnTransaksiMouseExited

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton12ActionPerformed

    private void btnKeluarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnKeluarMouseClicked
        // TODO add your handling code here:
        new Login().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnKeluarMouseClicked

    private void btnKeluarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnKeluarMouseEntered
        btnKeluar.setForeground(Color.black);
        btnKeluar.setBackground(new Color(59, 172, 182));
    }//GEN-LAST:event_btnKeluarMouseEntered

    private void btnKeluarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnKeluarMouseExited
        btnTransaksi.setForeground(Color.white);
        btnTransaksi.setBackground(new Color(47, 143, 157));
    }//GEN-LAST:event_btnKeluarMouseExited

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void btntransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntransaksiActionPerformed
        this.setVisible(false);
        new Data_Psn_Tambahan(this, rootPaneCheckingEnabled).show();
    }//GEN-LAST:event_btntransaksiActionPerformed

    private void btnTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTransaksiMouseClicked
        panelMasterData.setVisible(false);
        panelLaporan.setVisible(false);
        panelTransaksi.setVisible(true);
    }//GEN-LAST:event_btnTransaksiMouseClicked

    private void btnPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPegawaiActionPerformed
        // Data Pegawai
        this.setVisible(false);
        new Data_Pegawai(this, rootPaneCheckingEnabled).show();
    }//GEN-LAST:event_btnPegawaiActionPerformed

    private void btnDataPesananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDataPesananActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        new Data_Pesanan(this, rootPaneCheckingEnabled).show();
    }//GEN-LAST:event_btnDataPesananActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnDataPesanan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDataPesanan1ActionPerformed
        // Data Paket
        this.setVisible(false);
        new Data_Paket(this, rootPaneCheckingEnabled).show();
    }//GEN-LAST:event_btnDataPesanan1ActionPerformed

    private void tabelJadwalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelJadwalMouseClicked

    }//GEN-LAST:event_tabelJadwalMouseClicked

    private void btnpembayaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpembayaranActionPerformed
        this.setVisible(false);
        new Data_Pembayaran(this, rootPaneCheckingEnabled).show();
    }//GEN-LAST:event_btnpembayaranActionPerformed

    private void btnDataPesanan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDataPesanan2ActionPerformed
        this.setVisible(false);
        new Data_Riwayat(this, rootPaneCheckingEnabled).show();
    }//GEN-LAST:event_btnDataPesanan2ActionPerformed
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
            java.util.logging.Logger.getLogger(MenuUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuUtama().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDataKlien;
    private javax.swing.JButton btnDataPesanan;
    private javax.swing.JButton btnDataPesanan1;
    private javax.swing.JButton btnDataPesanan2;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnLaporan;
    private javax.swing.JButton btnMasterData;
    private javax.swing.JButton btnPegawai;
    private javax.swing.JButton btnTransaksi;
    private javax.swing.JButton btnpembayaran;
    private javax.swing.JButton btntransaksi;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jam;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelLaporan;
    private javax.swing.JPanel panelMasterData;
    private javax.swing.JPanel panelMenu;
    private javax.swing.JPanel panelTransaksi;
    private javax.swing.JPanel panelUtama;
    private javax.swing.JTable tabelJadwal;
    private javax.swing.JLabel tgl;
    // End of variables declaration//GEN-END:variables
}
