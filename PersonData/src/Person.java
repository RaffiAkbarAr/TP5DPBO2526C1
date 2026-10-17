public class Person {
    private String id;
    private String nama;
    private int tahunLahir;
    private String kategori;
    private String jabatan;

    public Person(String id, String nama, int tahunLahir,
                  String kategori, String jabatan) {
        this.id = id;
        this.nama = nama;
        this.tahunLahir = tahunLahir;
        this.kategori = kategori;
        this.jabatan = jabatan;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public int getTahunLahir() {
        return tahunLahir;
    }

    public String getKategori() {
        return kategori;
    }

    public String getJabatan() {
        return jabatan;
    }
}
