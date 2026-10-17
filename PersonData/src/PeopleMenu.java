import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;

public class PeopleMenu extends JFrame {
    // Field di bawah terhubung dengan komponen pada PeopleMenu.form.
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JTextField txtId;
    private JTextField txtNama;
    private JTextField txtTahunLahir;
    private JComboBox<String> cmbKategori;
    private JComboBox<String> cmbJabatan;
    private JButton btnTambah;
    private JButton btnUbah;
    private JButton btnHapus;
    private JButton btnBersihkan;
    private JTable tablePegawai;

    private final Database database = new Database();
    private DefaultTableModel model;

    public PeopleMenu() {
        setContentPane(mainPanel);
        setTitle("Data Pegawai Restoran");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 500);
        setLocationRelativeTo(null);

        cmbKategori.setModel(new DefaultComboBoxModel<>(
                new String[]{"Full-time", "Part-time", "Magang"}));
        cmbJabatan.setModel(new DefaultComboBoxModel<>(
                new String[]{"Koki", "Kasir", "Pelayan", "Manajer", "Barista"}));

        model = new DefaultTableModel(
                new Object[]{"ID", "Nama", "Tahun Lahir", "Kategori", "Jabatan"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablePegawai.setModel(model);
        tablePegawai.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        btnTambah.addActionListener(e -> tambahData());
        btnUbah.addActionListener(e -> ubahData());
        btnHapus.addActionListener(e -> hapusData());
        btnBersihkan.addActionListener(e -> bersihkanForm());

        // Mengambil data langsung dari JTable, tanpa ArrayList.
        tablePegawai.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablePegawai.getSelectedRow() >= 0) {
                int row = tablePegawai.convertRowIndexToModel(tablePegawai.getSelectedRow());
                txtId.setText(model.getValueAt(row, 0).toString());
                txtNama.setText(model.getValueAt(row, 1).toString());
                txtTahunLahir.setText(model.getValueAt(row, 2).toString());
                cmbKategori.setSelectedItem(model.getValueAt(row, 3).toString());
                cmbJabatan.setSelectedItem(model.getValueAt(row, 4).toString());
                txtId.setEditable(false);
            }
        });

        setTable();
    }

    // READ: menampilkan isi tabel MySQL ke JTable.
    private void setTable() {
        model.setRowCount(0);
        String sql = "SELECT id, nama, tahun_lahir, kategori, jabatan FROM person ORDER BY id";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("id"),
                        rs.getString("nama"),
                        rs.getInt("tahun_lahir"),
                        rs.getString("kategori"),
                        rs.getString("jabatan")
                });
            }
        } catch (SQLException ex) {
            tampilkanErrorDatabase("Gagal mengambil data dari database.", ex);
        }
    }

    private boolean validasi() {
        if (txtId.getText().trim().isEmpty()
                || txtNama.getText().trim().isEmpty()
                || txtTahunLahir.getText().trim().isEmpty()
                || cmbKategori.getSelectedItem() == null
                || cmbJabatan.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this,
                    "Semua kolom wajib diisi.",
                    "Input Belum Lengkap", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            int tahun = Integer.parseInt(txtTahunLahir.getText().trim());
            if (tahun < 1900 || tahun > Year.now().getValue()) {
                JOptionPane.showMessageDialog(this,
                        "Tahun lahir harus antara 1900 dan " + Year.now().getValue() + ".",
                        "Tahun Lahir Tidak Valid", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Tahun lahir harus berupa angka.",
                    "Input Tidak Valid", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private Person ambilForm() {
        return new Person(
                txtId.getText().trim(),
                txtNama.getText().trim(),
                Integer.parseInt(txtTahunLahir.getText().trim()),
                String.valueOf(cmbKategori.getSelectedItem()),
                String.valueOf(cmbJabatan.getSelectedItem())
        );
    }

    // CREATE: menyimpan data baru ke database.
    private void tambahData() {
        if (!validasi()) {
            return;
        }

        Person person = ambilForm();
        String sql = "INSERT INTO person (id, nama, tahun_lahir, kategori, jabatan) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            isiParameter(stmt, person);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data pegawai berhasil ditambahkan.");
            setTable();
            bersihkanForm();
        } catch (SQLException ex) {
            if (ex.getSQLState() != null && ex.getSQLState().startsWith("23")) {
                JOptionPane.showMessageDialog(this,
                        "ID pegawai sudah digunakan. Gunakan ID yang berbeda.",
                        "ID Duplikat", JOptionPane.ERROR_MESSAGE);
            } else {
                tampilkanErrorDatabase("Gagal menambahkan data.", ex);
            }
        }
    }

    // UPDATE: memperbarui data yang ID-nya dipilih pada tabel.
    private void ubahData() {
        int row = tablePegawai.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                    "Pilih data pada tabel yang ingin diubah.",
                    "Data Belum Dipilih", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!validasi()) {
            return;
        }

        Person person = ambilForm();
        String sql = "UPDATE person SET nama = ?, tahun_lahir = ?, kategori = ?, jabatan = ? WHERE id = ?";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, person.getNama());
            stmt.setInt(2, person.getTahunLahir());
            stmt.setString(3, person.getKategori());
            stmt.setString(4, person.getJabatan());
            stmt.setString(5, person.getId());

            int affected = stmt.executeUpdate();
            if (affected > 0) {
                JOptionPane.showMessageDialog(this, "Data pegawai berhasil diubah.");
                setTable();
                bersihkanForm();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Data tidak ditemukan di database.",
                        "Update Gagal", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            tampilkanErrorDatabase("Gagal mengubah data.", ex);
        }
    }

    // DELETE: meminta konfirmasi sebelum menghapus data dari database.
    private void hapusData() {
        int row = tablePegawai.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                    "Pilih data pada tabel yang ingin dihapus.",
                    "Data Belum Dipilih", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String id = model.getValueAt(tablePegawai.convertRowIndexToModel(row), 0).toString();
        int jawaban = JOptionPane.showConfirmDialog(this,
                "Yakin ingin menghapus data pegawai dengan ID " + id + "?",
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

        if (jawaban == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM person WHERE id = ?";
            try (Connection conn = database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, id);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Data pegawai berhasil dihapus.");
                setTable();
                bersihkanForm();
            } catch (SQLException ex) {
                tampilkanErrorDatabase("Gagal menghapus data.", ex);
            }
        }
    }

    private void isiParameter(PreparedStatement stmt, Person person) throws SQLException {
        stmt.setString(1, person.getId());
        stmt.setString(2, person.getNama());
        stmt.setInt(3, person.getTahunLahir());
        stmt.setString(4, person.getKategori());
        stmt.setString(5, person.getJabatan());
    }

    private void tampilkanErrorDatabase(String pesan, SQLException ex) {
        JOptionPane.showMessageDialog(this,
                pesan + "\nDetail: " + ex.getMessage(),
                "Kesalahan Database", JOptionPane.ERROR_MESSAGE);
    }

    private void bersihkanForm() {
        txtId.setText("");
        txtNama.setText("");
        txtTahunLahir.setText("");
        cmbKategori.setSelectedIndex(0);
        cmbJabatan.setSelectedIndex(0);
        txtId.setEditable(true);
        tablePegawai.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PeopleMenu().setVisible(true));
    }
}
