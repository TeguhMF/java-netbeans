/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author teguh
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KalkulatorBensin extends JFrame {

   
    private JTextField txtJarak;
    private JTextField txtKonsumsi;
    private JButton btnHitung;
    private JLabel lblHasil;

    public KalkulatorBensin() {
        
        setTitle("Trip Calculator - Estimasi Bensin");
        setSize(420, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setResizable(false);

        
        Color warnaBG = new Color(248, 249, 250);     
        Color warnaUtama = new Color(41, 128, 185);    
        Color warnaTeks = new Color(44, 62, 80);       
        Font fontLabel = new Font("Segoe UI", Font.BOLD, 13);
        Font fontInput = new Font("Segoe UI", Font.PLAIN, 14);

       
        JPanel panelUtama = new JPanel(new GridBagLayout());
        panelUtama.setBackground(warnaBG);
        panelUtama.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25)); 
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.fill = GridBagConstraints.HORIZONTAL;

       
        JLabel lblJudul = new JLabel("ESTIMASI BIAYA BENSIN", JLabel.CENTER);
        lblJudul.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblJudul.setForeground(warnaUtama);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panelUtama.add(lblJudul, gbc);

        
        JLabel lblJarak = new JLabel("Jarak Perjalanan (Km):");
        lblJarak.setFont(fontLabel);
        lblJarak.setForeground(warnaTeks);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        panelUtama.add(lblJarak, gbc);

        txtJarak = new JTextField();
        txtJarak.setFont(fontInput);
        txtJarak.setPreferredSize(new Dimension(150, 30));
        txtJarak.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        gbc.gridx = 1; gbc.gridy = 1;
        panelUtama.add(txtJarak, gbc);

       
        JLabel lblKonsumsi = new JLabel("Konsumsi BBM (Km/Liter):");
        lblKonsumsi.setFont(fontLabel);
        lblKonsumsi.setForeground(warnaTeks);
        gbc.gridx = 0; gbc.gridy = 2;
        panelUtama.add(lblKonsumsi, gbc);

        txtKonsumsi = new JTextField();
        txtKonsumsi.setFont(fontInput);
        txtKonsumsi.setPreferredSize(new Dimension(150, 30));
        txtKonsumsi.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        gbc.gridx = 1; gbc.gridy = 2;
        panelUtama.add(txtKonsumsi, gbc);

       
        btnHitung = new JButton("Hitung Total Biaya");
        btnHitung.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnHitung.setBackground(warnaUtama);
        btnHitung.setForeground(Color.BLACK);
        btnHitung.setFocusPainted(false);
        btnHitung.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnHitung.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panelUtama.add(btnHitung, gbc);

       
        lblHasil = new JLabel("<html><center>Masukkan data lalu klik tombol di atas.<br><font color='gray'><i>Asumsi Pertalite = Rp10.000/L</i></font></center></html>", JLabel.CENTER);
        lblHasil.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panelUtama.add(lblHasil, gbc);

        add(panelUtama);

   
        btnHitung.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    
                    double jarak = Double.parseDouble(txtJarak.getText());
                    double konsumsi = Double.parseDouble(txtKonsumsi.getText());
                    
                  
                    if (jarak <= 0 || konsumsi <= 0) {
                        JOptionPane.showMessageDialog(KalkulatorBensin.this,
                                "Angka harus lebih besar dari 0!",
                                "Input Salah", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

               
                    double kebutuhanLiter = jarak / konsumsi;
                    double hargaPerLiter = 10000; // Standar harga bensin dalam sistem
                    double totalBiaya = kebutuhanLiter * hargaPerLiter;

                    
                    lblHasil.setText(String.format(
                        "<html><center>Kebutuhan BBM: <b>%.2f Liter</b><br>Estimasi Biaya: <font color='#27ae60'><b>Rp %,.0f</b></font></center></html>", 
                        kebutuhanLiter, totalBiaya
                    ));

                } catch (NumberFormatException ex) {
                    
                    JOptionPane.showMessageDialog(KalkulatorBensin.this, 
                            "Mohon masukkan angka saja pada semua kolom!", 
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    
    public static void main(String[] args) {
       
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new KalkulatorBensin().setVisible(true);
            }
        });
    }
}