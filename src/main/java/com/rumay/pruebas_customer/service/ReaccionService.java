package com.rumay.pruebas_customer.service;

import com.rumay.pruebas_customer.model.Reaccion;
import com.rumay.pruebas_customer.model.Publicacion;
import com.rumay.pruebas_customer.model.Usuario;
import com.rumay.pruebas_customer.model.dto.ReaccionResponseDTO;
import com.rumay.pruebas_customer.repository.ReaccionRepository;
import com.rumay.pruebas_customer.repository.PublicacionRepository;
import org.springframework.stereotype.Service;

@Service
public class ReaccionService {

    private final ReaccionRepository reaccionRepository;
    private final PublicacionRepository publicacionRepository;

    public ReaccionService(ReaccionRepository rRepo, PublicacionRepository pRepo) {
        this.reaccionRepository = rRepo;
        this.publicacionRepository = pRepo;
    }

    public ReaccionResponseDTO toggleLike(Long publicacionId, Usuario usuario) {

        Publicacion p = publicacionRepository.findById(publicacionId).orElseThrow();

        Reaccion r = reaccionRepository
                .findByUsuarioAndPublicacion(usuario, p)
                .orElse(new Reaccion());

        r.setUsuario(usuario);
        r.setPublicacion(p);

        if (r.isLikeActive()) {
            r.setLikeActive(false);
        } else {
            r.setLikeActive(true);
            r.setDislikeActive(false);
        }

        reaccionRepository.save(r);

        ReaccionResponseDTO dto = new ReaccionResponseDTO();
        dto.publicacionId = publicacionId;
        dto.likeActive = r.isLikeActive();
        dto.dislikeActive = r.isDislikeActive();

        return dto;
    }

    public ReaccionResponseDTO toggleDislike(Long publicacionId, Usuario usuario) {

        Publicacion p = publicacionRepository.findById(publicacionId).orElseThrow();

        Reaccion r = reaccionRepository
                .findByUsuarioAndPublicacion(usuario, p)
                .orElse(new Reaccion());

        r.setUsuario(usuario);
        r.setPublicacion(p);

        if (r.isDislikeActive()) {
            r.setDislikeActive(false);
        } else {
            r.setDislikeActive(true);
            r.setLikeActive(false);
        }

        reaccionRepository.save(r);

        ReaccionResponseDTO dto = new ReaccionResponseDTO();
        dto.publicacionId = publicacionId;
        dto.likeActive = r.isLikeActive();
        dto.dislikeActive = r.isDislikeActive();

        return dto;
    }
}
