package com.example.TpIntegrador.Features.Contenedor;

import com.example.TpIntegrador.Enums.EstadoOperativo;
import com.example.TpIntegrador.Exceptions.*;
import com.example.TpIntegrador.Exceptions.BuqueExceptions.BuqueNotFoundException;
import com.example.TpIntegrador.Exceptions.ClienteExceptions.ClienteNotFoundException;
import com.example.TpIntegrador.Exceptions.ContenedroExceptions.ContenedorNotFoundException;
import com.example.TpIntegrador.Exceptions.ContenedroExceptions.EspacioInsufcienteException;
import com.example.TpIntegrador.Exceptions.ContenedroExceptions.NotVipException;
import com.example.TpIntegrador.Exceptions.PuertoExceptions.PuertoNotFoundException;
import com.example.TpIntegrador.Features.Buque.BuqueEntity;
import com.example.TpIntegrador.Features.Buque.BuqueRepository;
import com.example.TpIntegrador.Features.Cliente.ClienteEntity;
import com.example.TpIntegrador.Features.Cliente.ClienteRepository;
import com.example.TpIntegrador.Features.Contenedor.Dtos.ContenedorRequest;
import com.example.TpIntegrador.Features.Contenedor.Dtos.ContenedorResponse;
import com.example.TpIntegrador.Features.Contenedor.Mapper.ContenedorMapper;
import com.example.TpIntegrador.Features.Puerto.PuertoEntity;
import com.example.TpIntegrador.Features.Puerto.PuertoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContenedorService implements IContenedorService {

    private final ContenedorRepository contenedorRepository;
    private final ContenedorMapper mapper;
    private final ClienteRepository clienteRepository;
    private final PuertoRepository puertoRepository;
    private final BuqueRepository buqueRepository;

    public ContenedorService(ContenedorRepository contenedorRepository, ContenedorMapper mapper,
                             ClienteRepository clienteRepository, PuertoRepository puertoRepository,
                             BuqueRepository buqueRepository) {
        this.contenedorRepository = contenedorRepository;
        this.mapper = mapper;
        this.clienteRepository = clienteRepository;
        this.puertoRepository = puertoRepository;
        this.buqueRepository = buqueRepository;
    }


    @Override
    @Transactional
    public ContenedorResponse crear(ContenedorRequest request) {
        ContenedorEntity entity = mapper.toEntity(request);


        ClienteEntity cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado con ID: " + request.getClienteId()));

        PuertoEntity puerto = puertoRepository.findById(request.getPuertoDestinoId())
                .orElseThrow(() -> new PuertoNotFoundException("Puerto no encontrado con ID: " + request.getPuertoDestinoId()));


        entity.setCliente(cliente);
        entity.setPuertoDestino(puerto);
        entity.setActivo(true);


        ContenedorEntity contenedorguardado = contenedorRepository.save(entity);

        return mapper.toDto(contenedorguardado);
    }

    @Override
    public ContenedorResponse obtenerPorId(Long id) {
        ContenedorEntity entity = contenedorRepository.findById(id)
                .orElseThrow(() -> new ContenedorNotFoundException("Contenedor no encontrado con ID: " + id));
        return mapper.toDto(entity);
    }

    @Override
    public List<ContenedorResponse> obtenerTodos() {
        List<ContenedorEntity> lista = contenedorRepository.findByActivoTrue();
        System.out.println("DEBUG: Cantidad de entidades encontradas: " + lista.size());
        return lista.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }


    @Transactional
    @Override
    public ContenedorResponse actualizar(Long id, ContenedorRequest request) {
        ContenedorEntity entity = contenedorRepository.findById(id)
                .orElseThrow(() -> new ContenedorNotFoundException("Contenedor no encontrado"));

        // 2. Actualizamos campos simples usando el mapper
        mapper.updateEntity(request, entity);

        // 3. Actualizamos relaciones (si los IDs cambiaron)
        entity.setCliente(clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado")));

        entity.setPuertoDestino(puertoRepository.findById(request.getPuertoDestinoId())
                .orElseThrow(() -> new PuertoNotFoundException("Puerto no encontrado")));

        return mapper.toDto(contenedorRepository.save(entity));
    }


    @Transactional
    @Override
    public void darDeBaja(Long id) {

        ContenedorEntity entity = contenedorRepository.findById(id)
                .orElseThrow(() -> new ContenedorNotFoundException("Contenedor no encontrado"));
        // Implementación de baja lógica
        entity.setActivo(false);
        contenedorRepository.save(entity);

    }


    @Transactional
    @Override
    public void embarcar(Long contenedorId, Long buqueId) {
        ContenedorEntity contenedor = contenedorRepository.findById(contenedorId)
                .orElseThrow(() -> new ContenedorNotFoundException("Contenedor no encontrado"));
        BuqueEntity buque = buqueRepository.findById(buqueId)
                .orElseThrow(() -> new BuqueNotFoundException("Buque no encontrado"));

        // --- 1. VALIDACIÓN DE ESTADO ---
        // Regla: No se puede operar con entidades inactivas o buques fuera de puerto
        if (!contenedor.getActivo() || !buque.getActivo())
            throw new NotActiveException("El contenedor o buque no están activos");

        if (buque.getEstadoOperativo() != EstadoOperativo.EN_PUERTO)
            throw new NotActiveException("El buque no está en puerto para operar");

        // --- 2. VALIDACIÓN DE RUTA COMERCIAL ---
        // Regla: El contenedor debe tener como destino un puerto incluido en la ruta del buque
        boolean destinoEnRuta = buque.getRutasComerciales().contains(contenedor.getPuertoDestino());

        if (!destinoEnRuta)
            throw new PuertoNotFoundException("El buque no tiene este puerto en su ruta comercial");

        // --- 3. CÁLCULO DE CAPACIDAD Y LÓGICA VIP ---
        Double pesoActual = buque.getContenedores().stream()
                .mapToDouble(ContenedorEntity::getPesoToneladas).sum();
        Double capacidadDisponible = buque.getCapacidadMaximaToneladas() - pesoActual;

        if (contenedor.getPesoToneladas() > capacidadDisponible) {
            // Regla: Si no es VIP y excede capacidad, error inmediato
            if (!contenedor.getCliente().getEs_vip()) {
                throw new NotVipException("Capacidad excedida: El cliente no es VIP");
            }

            // --- 4. LÓGICA DE DESALOJO (OVERBOOKING) ---
            Double espacioNecesario = contenedor.getPesoToneladas() - capacidadDisponible;

            // Buscamos el contenedor regular (no VIP) que menos peso tenga,
            // pero que sea suficiente para liberar el espacio necesario
            ContenedorEntity candidatoDesalojo = buque.getContenedores().stream()
                    .filter(c -> !c.getCliente().getEs_vip()  && c.getPesoToneladas() >= espacioNecesario)
                    .min(Comparator.comparingDouble(ContenedorEntity::getPesoToneladas))
                    .orElseThrow(() -> new EspacioInsufcienteException("No hay espacio suficiente y no se encontró contenedor regular para desalojar"));



            // Desembarcamos el regular (lo ponemos en tierra)
            candidatoDesalojo.setBuque(null);
            contenedorRepository.save(candidatoDesalojo);
        }
        double costoFinal = calcularCostoEmbarque(contenedor, buque);
        contenedor.setPrecioEmbarque(costoFinal);

        // --- 5. EMBARQUE FINAL ---
        contenedor.setBuque(buque);
        contenedorRepository.save(contenedor);
    }

   // metodo privado para calcular la tarifa
    private double calcularCostoEmbarque(ContenedorEntity contenedor, BuqueEntity buque) {
        double precioBase = 1000.0;

        if (Boolean.TRUE.equals(contenedor.getCliente().getEs_vip())) {
            precioBase -= (precioBase * 0.10);
        }

        long diasParaSalida = ChronoUnit.DAYS.between(LocalDate.now(), buque.getFechaSalida());
        if (diasParaSalida <= 3) {
            precioBase += (precioBase * 0.20);
        }

        return precioBase;
    }
}
