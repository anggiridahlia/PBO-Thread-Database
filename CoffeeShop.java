import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 🔹 Mengatur koneksi ke database
class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/coffee_shop";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // ubah jika MySQL kamu pakai password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

// 🔹 Kelas Thread untuk tiap barista
class BaristaTask implements Runnable {
    private String baristaName;
    private String customerName;
    private String order;

    public BaristaTask(String baristaName, String customerName, String order) {
        this.baristaName = baristaName;
        this.customerName = customerName;
        this.order = order;
    }

    @Override
    public void run() {
        StringBuilder log = new StringBuilder();
        log.append("\n-------------------------------------------\n");
        log.append("   Barista " + baristaName + " Mulai Bekerja\n");
        log.append("-------------------------------------------\n");

        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "INSERT INTO coffee_orders (barista, customer, order_name) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, baristaName);
            ps.setString(2, customerName);
            ps.setString(3, order);
            ps.executeUpdate();

            log.append("Pelanggan : " + customerName + "\n");
            log.append("Pesanan   : " + order + "\n");
            log.append("Status    : " + baristaName + " sedang menyiapkan kopi...\n");

            Thread.sleep((int) (Math.random() * 3000 + 1500));

            log.append("Selesai   : " + baristaName + " telah membuat " + order + " untuk " + customerName + "\n");
            log.append("-------------------------------------------\n");

        } catch (SQLException e) {
            log.append("Gagal menyimpan data: " + e.getMessage() + "\n");
        } catch (InterruptedException e) {
            log.append("Barista " + baristaName + " terganggu saat bekerja.\n");
        }

        // 🔹 Sinkronisasi agar output tiap barista tidak tumpang tindih
        synchronized (System.out) {
            System.out.print(log.toString());
        }
    }
}

// 🔹 Kelas utama
public class CoffeeShop {

    private static void printWelcome() {
        System.out.println("===========================================");
        System.out.println("           KEDAI KOPI KAMPUS");
        System.out.println("===========================================");
        System.out.println("Semua barista sedang menyiapkan kopi...\n");
    }

    private static void printFinalTable() {
        System.out.println("\nLaporan Akhir Pemesanan Kopi");
        System.out.println("+------+----------------+----------------+----------------+");
        System.out.printf("| %-4s | %-14s | %-14s | %-14s |\n", "ID", "Barista", "Pelanggan", "Pesanan");
        System.out.println("+------+----------------+----------------+----------------+");

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM coffee_orders")) {

            while (rs.next()) {
                System.out.printf("| %-4d | %-14s | %-14s | %-14s |\n",
                        rs.getInt("id"),
                        rs.getString("barista"),
                        rs.getString("customer"),
                        rs.getString("order_name"));
            }

        } catch (SQLException e) {
            System.out.println("Gagal mengambil data: " + e.getMessage());
        }

        System.out.println("+------+----------------+----------------+----------------+");
        System.out.println("Terima kasih telah berkunjung ke Kedai Kopi Kampus!\n");
    }

    public static void main(String[] args) {
        printWelcome();

        // 🔹 Hapus data lama setiap program dijalankan
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM coffee_orders");
            System.out.println("Data lama dihapus. Memulai sesi baru...\n");
        } catch (SQLException e) {
            System.out.println("Gagal menghapus data lama: " + e.getMessage());
        }

        // 🔹 Jalankan barista secara paralel
        ExecutorService executor = Executors.newFixedThreadPool(4);
        executor.execute(new BaristaTask("Anggi", "Ariel", "Cappuccino"));
        executor.execute(new BaristaTask("Dahlia", "Belle", "Espresso"));
        executor.execute(new BaristaTask("Candra", "Moana", "Latte"));
        executor.execute(new BaristaTask("Anggiri", "Aurora", "Americano"));

        executor.shutdown();

        // Tunggu semua barista selesai
        while (!executor.isTerminated()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        printFinalTable();
    }
}
