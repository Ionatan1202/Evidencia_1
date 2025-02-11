package clinica.model;

public class Administrador {
    private String id;
    private String contrasena;

    public Administrador(String id, String contrasena) {
        this.id = id;
        this.contrasena = contrasena;
    }

    // Getters y Setters
    public String getId() { return id; }
    public String getContrasena() { return contrasena; }

    @Override
    public String toString() {
        return id + "," + contrasena;
    }
}