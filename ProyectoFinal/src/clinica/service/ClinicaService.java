package clinica.service;

import clinica.model.*;
import clinica.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class ClinicaService {
    private List<Doctor> doctores = new ArrayList<>();
    private List<Paciente> pacientes = new ArrayList<>();
    private List<Cita> citas = new ArrayList<>();
    private List<Administrador> administradores = new ArrayList<>();

    public void altaDoctor(Doctor doctor) {
        doctores.add(doctor);
        guardarDoctores();
    }

    public void altaPaciente(Paciente paciente) {
        pacientes.add(paciente);
        guardarPacientes();
    }

    public void crearCita(Cita cita) {
        citas.add(cita);
        guardarCitas();
    }

    public boolean autenticarAdministrador(String id, String contrasena) {
        for (Administrador admin : administradores) {
            if (admin.getId().equals(id) && admin.getContrasena().equals(contrasena)) {
                return true;
            }
        }
        return false;
    }

    public List<Cita> getCitas() {
        return citas;
    }

    public Doctor buscarDoctorPorId(String id) {
        for (Doctor doctor : doctores) {
            if (doctor.getId().equals(id)) {
                return doctor;
            }
        }
        return null;
    }

    public Paciente buscarPacientePorId(String id) {
        for (Paciente paciente : pacientes) {
            if (paciente.getId().equals(id)) {
                return paciente;
            }
        }
        return null;
    }

    private void guardarDoctores() {
        List<String> datos = new ArrayList<>();
        for (Doctor doctor : doctores) {
            datos.add(doctor.toString());
        }
        FileUtil.guardarDatos("resources/doctores.csv", datos);
    }

    private void guardarPacientes() {
        List<String> datos = new ArrayList<>();
        for (Paciente paciente : pacientes) {
            datos.add(paciente.toString());
        }
        FileUtil.guardarDatos("resources/pacientes.csv", datos);
    }

    private void guardarCitas() {
        List<String> datos = new ArrayList<>();
        for (Cita cita : citas) {
            datos.add(cita.toString());
        }
        FileUtil.guardarDatos("resources/citas.csv", datos);
    }

    public void cargarDatos() {
        List<String> datosDoctores = FileUtil.cargarDatos("resources/doctores.csv");
        for (String linea : datosDoctores) {
            String[] partes = linea.split(",");
            doctores.add(new Doctor(partes[0], partes[1], partes[2]));
        }

        List<String> datosPacientes = FileUtil.cargarDatos("resources/pacientes.csv");
        for (String linea : datosPacientes) {
            String[] partes = linea.split(",");
            pacientes.add(new Paciente(partes[0], partes[1]));
        }

        List<String> datosCitas = FileUtil.cargarDatos("resources/citas.csv");
        for (String linea : datosCitas) {
            String[] partes = linea.split(",");
            Doctor doctor = buscarDoctorPorId(partes[3]);
            Paciente paciente = buscarPacientePorId(partes[4]);
            citas.add(new Cita(partes[0], partes[1], partes[2], doctor, paciente));
        }

        List<String> datosAdministradores = FileUtil.cargarDatos("resources/administradores.csv");
        for (String linea : datosAdministradores) {
            String[] partes = linea.split(",");
            administradores.add(new Administrador(partes[0], partes[1]));
        }
    }

    // Dar de baja un doctor
    public boolean bajaDoctor(String id) {
        Doctor doctor = buscarDoctorPorId(id);
        if (doctor != null) {
            doctores.remove(doctor);
            guardarDoctores();
            return true;
        }
        return false;
    }

    // Dar de baja un paciente
    public boolean bajaPaciente(String id) {
        Paciente paciente = buscarPacientePorId(id);
        if (paciente != null) {
            pacientes.remove(paciente);
            guardarPacientes();
            return true;
        }
        return false;
    }

    // Cancelar una cita
    public boolean cancelarCita(String id) {
        Cita cita = buscarCitaPorId(id);
        if (cita != null) {
            citas.remove(cita);
            guardarCitas();
            return true;
        }
        return false;
    }

    // Buscar una cita por ID
    public Cita buscarCitaPorId(String id) {
        for (Cita cita : citas) {
            if (cita.getId().equals(id)) {
                return cita;
            }
        }
        return null;
    }

    // Consultar todos los doctores
    public List<Doctor> consultarDoctores() {
        return doctores;
    }

    // Consultar todos los pacientes
    public List<Paciente> consultarPacientes() {
        return pacientes;
    }

    public boolean existeDoctor(String id) {
        return buscarDoctorPorId(id) != null;
    }

    public boolean existePaciente(String id) {
        return buscarPacientePorId(id) != null;
    }

    public boolean existeCita(String id) {
        return buscarCitaPorId(id) != null;
    }
}