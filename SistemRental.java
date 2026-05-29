/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author teguh
 */
import java.util.ArrayList;
import java.util.Scanner;

// =========================================================================
// 1. SUPERCLASS (ABSTRACTION & ENCAPSULATION)
// =========================================================================
abstract class Kendaraan {
    // Encapsulation: Atribut di-set private agar tidak bisa diubah sembarangan
    private String nopol;
    private String merk;
    private double hargaSewaPerHari;
    private boolean isTersedia;

    // Constructor
    public Kendaraan(String nopol, String merk, double hargaSewaPerHari) {
        this.nopol = nopol;
        this.merk = merk;
        this.hargaSewaPerHari = hargaSewaPerHari;
        this.isTersedia = true; // Default awal kendaraan pasti tersedia
    }

    // Getter dan Setter (Akses data private secara aman)
    public String getNopol() { return nopol; }
    public String getMerk() { return merk; }
    public double getHargaSewaPerHari() { return hargaSewaPerHari; }
    public boolean isTersedia() { return isTersedia; }
    public void setTersedia(boolean tersedia) { this.isTersedia = tersedia; }

    // Polymorphism: Method abstrak yang wajib di-override oleh subclass
    public abstract double hitungTotalBiaya(int durasiHari);

    // Method untuk menampilkan informasi dasar kendaraan
    public void tampilkanInfo() {
        String status = isTersedia ? "READY" : "DISEWA";
        System.out.printf("| %-12s | %-15s | Rp %,-12.0f | %-8s |\n", nopol, merk, hargaSewaPerHari, status);
    }
}

// =========================================================================
// 2. SUBCLASS MOBIL (INHERITANCE & POLYMORPHISM)
// =========================================================================
class Mobil extends Kendaraan {
    private boolean pakaiSopir;

    // Inheritance: Memanggil constructor superclass menggunakan super()
    public Mobil(String nopol, String merk, double hargaSewaPerHari, boolean pakaiSopir) {
        super(nopol, merk, hargaSewaPerHari);
        this.pakaiSopir = pakaiSopir;
    }

    // Polymorphism: Mengubah hitungan biaya sesuai karakteristik Mobil
    @Override
    public double hitungTotalBiaya(int durasiHari) {
        double total = getHargaSewaPerHari() * durasiHari;
        if (pakaiSopir) {
            total += (150000 * durasiHari); // Tambahan biaya sopir Rp 150.000/hari
        }
        return total;
    }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        String infoSopir = pakaiSopir ? "Ya" : "Tidak";
        System.out.println("   -> Jenis: MOBIL | Include Sopir: " + infoSopir);
    }
}

// =========================================================================
// 3. SUBCLASS MOTOR (INHERITANCE & POLYMORPHISM)
// =========================================================================
class Motor extends Kendaraan {
    private int jumlahHelm;

    public Motor(String nopol, String merk, double hargaSewaPerHari, int jumlahHelm) {
        super(nopol, merk, hargaSewaPerHari);
        this.jumlahHelm = jumlahHelm;
    }

    // Polymorphism: Mengubah hitungan biaya sesuai karakteristik Motor
    @Override
    public double hitungTotalBiaya(int durasiHari) {
        double total = getHargaSewaPerHari() * durasiHari;
        total += (jumlahHelm * 10000 * durasiHari); // Tambahan sewa helm Rp 10.000/helm/hari
        return total;
    }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.println("   -> Jenis: MOTOR | Jumlah Helm: " + jumlahHelm);
    }
}

// =========================================================================
// 4. MAIN CLASS (CONTROLLER & MENUS)
// =========================================================================
public class SistemRental {
    // List untuk menampung objek-objek kendaraan (Object Collaboration)
    private static ArrayList<Kendaraan> daftarKendaraan = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Data Dummy Awal (Polymorphism: Objek Mobil & Motor disimpan dalam list bertipe Kendaraan)
        daftarKendaraan.add(new Mobil("B 1234 ABC", "Toyota Avanza", 350000, true));
        daftarKendaraan.add(new Mobil("B 8888 VIP", "Honda Civic", 700000, false));
        daftarKendaraan.add(new Motor("D 5678 XYZ", "Honda PCX160", 120000, 2));
        daftarKendaraan.add(new Motor("D 1111 MOT", "Yamaha Aerox", 100000, 1));

        int pilihan;
        do {
            System.out.println("\n=========================================");
            System.out.println("     SISTEM RENTAL KENDARAAN TEGUHMF     ");
            System.out.println("=========================================");
            System.out.println("1. Lihat Daftar Kendaraan");
            System.out.println("2. Sewa Kendaraan");
            System.out.println("3. Kembalikan Kendaraan");
            System.out.println("4. Keluar");
            System.out.print("Pilih menu (1-4): ");
            
            pilihan = scanner.nextInt();
            scanner.nextLine(); // Membersihkan buffer scanner

            switch (pilihan) {
                case 1:
                    tampilkanSemuaKendaraan();
                    break;
                case 2:
                    prosesSewa();
                    break;
                case 3:
                    prosesKembali();
                    break;
                case 4:
                    System.out.println("Terima kasih telah menggunakan sistem rental!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilihan != 4);
    }

    private static void tampilkanSemuaKendaraan() {
        System.out.println("\n-----------------------------------------------------------------");
        System.out.println("| No. Plat     | Merk/Model      | Harga / Hari | Status   |");
        System.out.println("-----------------------------------------------------------------");
        for (Kendaraan k : daftarKendaraan) {
            k.tampilkanInfo();
        }
        System.out.println("-----------------------------------------------------------------");
    }

   private static void prosesSewa() {
        tampilkanSemuaKendaraan();
        System.out.print("Masukkan No. Plat kendaraan yang ingin disewa: ");
        String nopolCari = scanner.nextLine().toUpperCase(); // Spasi sudah dihapus di sini

        Kendaraan kendaraanDipilih = cariKendaraan(nopolCari);

        if (kendaraanDipilih == null) {
            System.out.println("Kendaraan tidak ditemukan!");
            return;
        }

        if (!kendaraanDipilih.isTersedia()) {
            System.out.println("Maaf, kendaraan ini sedang disewa orang lain!");
            return;
        }

        System.out.print("Masukkan durasi sewa (Hari): ");
        int hari = scanner.nextInt();
        scanner.nextLine();

        // Menghitung total biaya menggunakan prinsip Polymorphism Dynamic Binding
        double totalBiaya = kendaraanDipilih.hitungTotalBiaya(hari);
        kendaraanDipilih.setTersedia(false); // Ubah status menjadi disewa

        System.out.println("\n--- TRANSAKSI BERHASIL ---");
        System.out.println("Kendaraan     : " + kendaraanDipilih.getMerk() + " (" + kendaraanDipilih.getNopol() + ")");
        System.out.println("Durasi        : " + hari + " Hari");
        System.out.printf("Total Tagihan : Rp %,.0f\n", totalBiaya);
    }

    private static void prosesKembali() {
        System.out.print("Masukkan No. Plat kendaraan yang dikembalikan: ");
        String nopolCari = scanner.nextLine().toUpperCase();

        Kendaraan kendaraanDipilih = cariKendaraan(nopolCari);

        if (kendaraanDipilih == null) {
            System.out.println("Kendaraan tidak ditemukan!");
            return;
        }

        if (kendaraanDipilih.isTersedia()) {
            System.out.println("Kendaraan ini sebenarnya belum disewa.");
            return;
        }

        kendaraanDipilih.setTersedia(true); // Ubah status kembali ready
        System.out.println("Berhasil! Kendaraan " + kendaraanDipilih.getMerk() + " sekarang berstatus READY kembali.");
    }

    // Helper method untuk mencari kendaraan berdasarkan plat nomor
    private static Kendaraan cariKendaraan(String nopol) {
        for (Kendaraan k : daftarKendaraan) {
            if (k.getNopol().equalsIgnoreCase(nopol)) {
                return k;
            }
        }
        return null;
    }
}
