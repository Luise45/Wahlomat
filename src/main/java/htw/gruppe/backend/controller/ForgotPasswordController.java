package htw.gruppe.backend.controller;

import htw.gruppe.backend.entity.Kandidat;
import htw.gruppe.backend.record.AussageDto;
import htw.gruppe.backend.record.ForgotDto;
//import htw.gruppe.backend.record.MatchRequest;
import htw.gruppe.backend.record.ResetDto;
import htw.gruppe.backend.repository.KandidatenRepository;
import htw.gruppe.backend.service.ResetInterface;
import htw.gruppe.backend.service.ResetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

/**
 * Controller fuer die Funktion 'Passwort vergessen'
 * @author Tabatt
 * @version 2.0
 */

    @RestController
    @RequestMapping("/api")
    public class ForgotPasswordController {

    private final KandidatenRepository kandidatenRepository;
    private final ResetService resetService;
    private final ResetInterface resetInterface;

    public ForgotPasswordController(KandidatenRepository kandidatenRepository, ResetInterface resetInterface, ResetService resetService) {
        this.kandidatenRepository = kandidatenRepository;
        this.resetInterface = resetInterface;
        this.resetService = resetService;
    }

    // für Swagger
    @Operation(summary = "Passwort vergessen", description = "Controller fuer Passwort vergessen und neues Passwort erstellen")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AussageDto.class))),
            @ApiResponse(responseCode = "404", description = "Failed", content = @Content),
            @ApiResponse(responseCode = "403", description = "Failed", content = @Content) })
    /**
     * POST fuer passwort vergessen, triggert Email mit reset Link
     * @return erfolgreich oder error
     */

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(
            //swagger
            @org.springframework.web.bind.annotation.RequestBody ForgotDto request
            ) {

        String matrikelnummer = request.matrikelnummer();
        if (matrikelnummer == null || matrikelnummer.isEmpty()) {
            return ResponseEntity.badRequest().body("Matrikelnummer fehlt");
        }

        Optional<Kandidat> kandidatOpt = kandidatenRepository.findByMatrikelnummer(matrikelnummer);

        if (kandidatOpt.isEmpty()) {
            return ResponseEntity.ok("Falls die Matrikelnummer exsistiert, wird die Email geschickt");
        }

        // Token Email wird generiert
        String token = resetService.ResetTokenErstellen(matrikelnummer);
        String resetLink = "http://localhost:4200/reset-password?token=" + token;
        String email = matrikelnummer + "@htw-berlin.de";

        // Email wird geschickt. Interface sendEmail
        resetService.sendEmail(
                email, "Passwort zurücksetzen", "Bitte klicken Sie auf den folgenden Link: " + resetLink
        );

        return ResponseEntity.ok("Falls der den Account giebt, wurde eien Email geschikt");
    }

    /**
     * Passwort kann mit dem Endpunkt erneuert werden.
     * @param request ResetDto
     * @return passwort update
     */
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            //swagger
            @org.springframework.web.bind.annotation.RequestBody ResetDto request
            ) {

            resetService.resetPassword(request.token(), request.newPassword());
        return ResponseEntity.ok(Map.of("message", "Passwort updated"));
    }
}







