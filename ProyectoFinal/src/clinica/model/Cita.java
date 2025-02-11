package clinica.model;

public class Cita {
    private String id;
    private String fechaHora;
    private String motivo;
    private Doctor doctor;
    private Paciente paciente;

    public Cita(String id, String fechaHora, String motivo, Doctor doctor, Paciente paciente) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.doctor = doctor;
        this.paciente = paciente;
    }

    // Getters y Setters
    public String getId() { return id; }
    public String getFechaHora() { return fechaHora; }
    public String getMotivo() { return motivo; }
    public Doctor getDoctor() { return doctor; }
    public Paciente getPaciente() { return paciente; }

    @Override
    public String toString() {
        return id + "," + fechaHora + "," + motivo + "," + doctor.getId() + "," + paciente.getId();
    }
}