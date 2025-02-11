import clinica.model.*;
import clinica.service.ClinicaService;
import clinica.util.FileUtil;

import java.util.Scanner;

public class ClinicaApp {
    public static void main(String[] args) {
        ClinicaService service = new ClinicaService();
        service.cargarDatos(); // Cargar datos existentes

        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenido al Sistema de Administración de Citas");
        System.out.print("Ingrese su ID de administrador: ");
        String id = scanner.nextLine();
        System.out.print("Ingrese su contraseña: ");
        String contrasena = scanner.nextLine();

        if (service.autenticarAdministrador(id, contrasena)) {
            System.out.println("Autenticación exitosa.");
            mostrarMenu(service, scanner); // Mostrar el menú principal
        } else {
            System.out.println("Autenticación fallida. Saliendo del sistema.");
        }
    }

    private static void mostrarMenu(ClinicaService service, Scanner scanner) {
        int opcion;
        do {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Consultar doctores");
            System.out.println("2. Dar de alta un doctor");
            System.out.println("3. Dar de baja un doctor");
            System.out.println("4. Consultar pacientes");
            System.out.println("5. Dar de alta un paciente");
            System.out.println("6. Dar de baja un paciente");
            System.out.println("7. Mostrar todas las citas");
            System.out.println("8. Crear una cita");
            System.out.println("9. Cancelar una cita");
            System.out.println("10. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    consultarDoctores(service);
                    break;
                case 2:
                    darDeAltaDoctor(service, scanner);
                    break;
                case 3:
                    darDeBajaDoctor(service, scanner);
                    break;
                case 4:
                    consultarPacientes(service);
                    break;
                case 5:
                    darDeAltaPaciente(service, scanner);
                    break;
                case 6:
                    darDeBajaPaciente(service, scanner);
                    break;
                case 7:
                    mostrarCitas(service);
                    break;
                case 8:
                    crearCita(service, scanner);
                    break;
                case 9:
                    cancelarCita(service, scanner);
                    break;
                case 10:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 10);
    }

    private static void darDeBajaDoctor(ClinicaService service, Scanner scanner) {
        System.out.println("\n--- Dar de baja un doctor ---");
        System.out.print("Ingrese el ID del doctor: ");
        String id = scanner.nextLine();

        if (!service.existeDoctor(id)) {
            System.out.println("Error: No existe un doctor con ese ID.");
            return;
        }

        Doctor doctor = service.buscarDoctorPorId(id);
        System.out.println("Doctor encontrado: " + doctor.getNombre());
        System.out.print("¿Está seguro de que desea dar de baja a este doctor? (Y/N): ");
        String confirmacion = scanner.nextLine();
        if (confirmacion.equalsIgnoreCase("Y")) {
            if (service.bajaDoctor(id)) {
                System.out.println("Doctor dado de baja exitosamente.");
            } else {
                System.out.println("Error al dar de baja al doctor.");
            }
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    private static void darDeBajaPaciente(ClinicaService service, Scanner scanner) {
        System.out.println("\n--- Dar de baja un paciente ---");
        System.out.print("Ingrese el ID del paciente: ");
        String id = scanner.nextLine();

        if (!service.existePaciente(id)) {
            System.out.println("Error: No existe un paciente con ese ID.");
            return;
        }

        Paciente paciente = service.buscarPacientePorId(id);
        System.out.println("Paciente encontrado: " + paciente.getNombre());
        System.out.print("¿Está seguro de que desea dar de baja a este paciente? (Y/N): ");
        String confirmacion = scanner.nextLine();
        if (confirmacion.equalsIgnoreCase("Y")) {
            if (service.bajaPaciente(id)) {
                System.out.println("Paciente dado de baja exitosamente.");
            } else {
                System.out.println("Error al dar de baja al paciente.");
            }
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    private static void cancelarCita(ClinicaService service, Scanner scanner) {
        System.out.println("\n--- Cancelar una cita ---");
        System.out.print("Ingrese el ID de la cita: ");
        String id = scanner.nextLine();

        if (!service.existeCita(id)) {
            System.out.println("Error: No existe una cita con ese ID.");
            return;
        }

        Cita cita = service.buscarCitaPorId(id);
        System.out.println("Cita encontrada:");
        System.out.println("Fecha y Hora: " + cita.getFechaHora());
        System.out.println("Motivo: " + cita.getMotivo());
        System.out.println("Doctor: " + cita.getDoctor().getNombre());
        System.out.println("Paciente: " + cita.getPaciente().getNombre());
        System.out.print("¿Está seguro de que desea cancelar esta cita? (Y/N): ");
        String confirmacion = scanner.nextLine();
        if (confirmacion.equalsIgnoreCase("Y")) {
            if (service.cancelarCita(id)) {
                System.out.println("Cita cancelada exitosamente.");
            } else {
                System.out.println("Error al cancelar la cita.");
            }
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    private static void consultarDoctores(ClinicaService service) {
        System.out.println("\n--- Listado de Doctores ---");
        for (Doctor doctor : service.consultarDoctores()) {
            System.out.println("ID: " + doctor.getId());
            System.out.println("Nombre: " + doctor.getNombre());
            System.out.println("Especialidad: " + doctor.getEspecialidad());
            System.out.println("-----------------------------");
        }
    }

    private static void consultarPacientes(ClinicaService service) {
        System.out.println("\n--- Listado de Pacientes ---");
        for (Paciente paciente : service.consultarPacientes()) {
            System.out.println("ID: " + paciente.getId());
            System.out.println("Nombre: " + paciente.getNombre());
            System.out.println("-----------------------------");
        }
    }

    private static void darDeAltaDoctor(ClinicaService service, Scanner scanner) {
        System.out.println("\n--- Dar de alta un doctor ---");
        System.out.print("Ingrese el ID del doctor: ");
        String id = scanner.nextLine();

        if (service.existeDoctor(id)) {
            System.out.println("Error: Ya existe un doctor con ese ID.");
            return;
        }

        System.out.print("Ingrese el nombre completo del doctor: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese la especialidad del doctor: ");
        String especialidad = scanner.nextLine();

        Doctor doctor = new Doctor(id, nombre, especialidad);
        service.altaDoctor(doctor);
        System.out.println("Doctor registrado exitosamente.");
    }

    private static void darDeAltaPaciente(ClinicaService service, Scanner scanner) {
        System.out.println("\n--- Dar de alta un paciente ---");
        System.out.print("Ingrese el ID del paciente: ");
        String id = scanner.nextLine();

        if (service.existePaciente(id)) {
            System.out.println("Error: Ya existe un paciente con ese ID.");
            return;
        }

        System.out.print("Ingrese el nombre completo del paciente: ");
        String nombre = scanner.nextLine();

        Paciente paciente = new Paciente(id, nombre);
        service.altaPaciente(paciente);
        System.out.println("Paciente registrado exitosamente.");
    }

    private static void crearCita(ClinicaService service, Scanner scanner) {
        System.out.println("\n--- Crear una cita ---");
        System.out.print("Ingrese el ID de la cita: ");
        String idCita = scanner.nextLine();

        if (service.existeCita(idCita)) {
            System.out.println("Error: Ya existe una cita con ese ID.");
            return;
        }

        System.out.print("Ingrese la fecha y hora de la cita (formato: dd/MM/yyyy HH:mm): ");
        String fechaHora = scanner.nextLine();
        System.out.print("Ingrese el motivo de la cita: ");
        String motivo = scanner.nextLine();

        System.out.print("Ingrese el ID del doctor: ");
        String idDoctor = scanner.nextLine();
        if (!service.existeDoctor(idDoctor)) {
            System.out.println("Error: No existe un doctor con ese ID.");
            return;
        }
        Doctor doctor = service.buscarDoctorPorId(idDoctor);

        System.out.print("Ingrese el ID del paciente: ");
        String idPaciente = scanner.nextLine();
        if (!service.existePaciente(idPaciente)) {
            System.out.println("Error: No existe un paciente con ese ID.");
            return;
        }
        Paciente paciente = service.buscarPacientePorId(idPaciente);

        Cita cita = new Cita(idCita, fechaHora, motivo, doctor, paciente);
        service.crearCita(cita);
        System.out.println("Cita creada exitosamente.");
    }

    private static void mostrarCitas(ClinicaService service) {
        System.out.println("\n--- Listado de Citas ---");
        for (Cita cita : service.getCitas()) {
            System.out.println("ID Cita: " + cita.getId());
            System.out.println("Fecha y Hora: " + cita.getFechaHora());
            System.out.println("Motivo: " + cita.getMotivo());
            System.out.println("Doctor: " + cita.getDoctor().getNombre());
            System.out.println("Paciente: " + cita.getPaciente().getNombre());
            System.out.println("-----------------------------");
        }
    }
}