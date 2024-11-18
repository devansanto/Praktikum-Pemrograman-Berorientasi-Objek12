/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package praktikum12;

/**
 *
 * @author asus1
 */
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Kelas Buku untuk Serialisasi
class Buku implements Serializable {
    private final String judul;
    private final String pengarang;
    private final int tahunTerbit;

    public Buku(String judul, String pengarang, int tahunTerbit) {
        this.judul = judul;
        this.pengarang = pengarang;
        this.tahunTerbit = tahunTerbit;
    }
    
    public String getJudul() {
    return judul;
    }

    public String getPengarang() {
        return pengarang;
    }

    public int getTahunTerbit() {
        return tahunTerbit;
    }

    public void tampilkanInfo() {
        System.out.println("Judul: " + judul + ", Pengarang: " + pengarang + ", Tahun Terbit: " + tahunTerbit);
    }
}

public class SystemBook {
    private static final String TEXT_FILE = "buku.txt";
    private static final String SERIAL_FILE = "buku.ser";
    private static final List<Buku> daftarBuku = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nMenu Perpustakaan:");
            System.out.println("1. Tambah Buku");
            System.out.println("2. Simpan ke File Teks");
            System.out.println("3. Simpan Objek ke File (Serialisasi)");
            System.out.println("4. Tampilkan Semua Buku dari File Teks");
            System.out.println("5. Tampilkan Semua Buku dari File Serial");
            System.out.println("6. Tampilkan Buku pada sesi ini");
            System.out.println("7. Keluar");
            System.out.print("Pilihan: ");
            int pilihan = scanner.nextInt();
            scanner.nextLine(); // Konsumsi newline

            switch (pilihan) {
                case 1 -> tambahBuku(scanner);
                case 2 -> simpanKeFileTeks();
                case 3 -> simpanKeFileSerial();
                case 4 -> tampilkanBukuDariFileTeks();
                case 5 -> tampilkanBukuDariFileSerial();
                case 6 -> tampilkanBuku(); 
                case 7 -> {
                    System.out.println("Keluar dari program.");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void tambahBuku(Scanner scanner) {
        System.out.print("Masukkan judul buku: ");
        String judul = scanner.nextLine();
        System.out.print("Masukkan pengarang: ");
        String pengarang = scanner.nextLine();
        System.out.print("Masukkan tahun terbit: ");
        int tahunTerbit = scanner.nextInt();
        daftarBuku.add(new Buku(judul, pengarang, tahunTerbit));
        System.out.println("Buku berhasil ditambahkan.");
    }

    private static void simpanKeFileTeks() {
        try (FileWriter writer = new FileWriter(TEXT_FILE, true)) {
            for (Buku buku : daftarBuku) {
                writer.write(buku.getJudul() + "," + buku.getPengarang() + "," + buku.getTahunTerbit() + "\n");
            }
            System.out.println("Data buku berhasil disimpan ke file teks.");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan ke file teks.");
        }
    }

    private static void simpanKeFileSerial() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SERIAL_FILE, true))) {
            oos.writeObject(daftarBuku);
            System.out.println("Objek buku berhasil disimpan ke file serial.");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan ke file serial.");
        }
    }

    private static void tampilkanBukuDariFileTeks() {
        try (BufferedReader reader = new BufferedReader(new FileReader(TEXT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Buku buku = new Buku(data[0], data[1], Integer.parseInt(data[2]));
                buku.tampilkanInfo();
            }
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat membaca file teks.");
        }
    }

    private static void tampilkanBukuDariFileSerial() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SERIAL_FILE))) {
            @SuppressWarnings("unchecked")
            List<Buku> bukuList = (List<Buku>) ois.readObject();
            for (Buku buku : bukuList) {
                buku.tampilkanInfo();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Terjadi kesalahan saat membaca file serial.");
        }
    }
    
    private static void tampilkanBuku() {
        System.out.println("Daftar Produk:");
        for (Buku buku : daftarBuku) {
            buku.tampilkanInfo();
        }
    }
}