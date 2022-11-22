package pl.yspar.cage.types;

public enum CageType {
    WAITING("WAITING", 0, "Oczekiwanie"),
    INGAME("INGAME", 1, "W trakcie gry"),
    ENDING("ENDING", 2, "Zakonczono");

    private final String status;

    CageType(final String s, final int n, final String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
