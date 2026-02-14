package htw.gruppe.backend.service;

import htw.gruppe.backend.entity.Wahlliste;
import htw.gruppe.backend.repository.KandidatAufWahllisteRepository;
import htw.gruppe.backend.repository.WahllisteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WahllisteServiceTest {

    @Mock
    WahllisteRepository wahllisteRepository;

    @Mock
    KandidatAufWahllisteRepository kandidatAufWahllisteRepository;

    @InjectMocks
    WahllisteService wahllisteService;

    @Test
    void getAllWahllisten_returnsAllWahllistenFromRepository() {
        // Repository soll 2 Listen zurückgeben
        Wahlliste w1 = new Wahlliste();
        Wahlliste w2 = new Wahlliste();
        when(wahllisteRepository.findAll()).thenReturn(List.of(w1, w2));

        // Service aufrufen
        List<Wahlliste> result = wahllisteService.getAllWahllisten();

        // passt: 2 Elemente und genau die gleichen Objekte
        assertEquals(2, result.size());
        assertSame(w1, result.get(0));
        assertSame(w2, result.get(1));
        verify(wahllisteRepository).findAll();
    }

    @Test
    void getWahlliste_whenFound_returnsWahlliste() {
        // Wahlliste existiert
        Wahlliste w = new Wahlliste();
        when(wahllisteRepository.findById(5L)).thenReturn(Optional.of(w));

        // Service holt die Liste
        Wahlliste result = wahllisteService.getWahlliste(5L);

        // kommt genau das zurück, was das Repo liefert
        assertSame(w, result);
        verify(wahllisteRepository).findById(5L);
    }

    @Test
    void getWahlliste_whenNotFound_throwsException() {
        // Repo findet nichts
        when(wahllisteRepository.findById(99L)).thenReturn(Optional.empty());

        // dann soll der Service eine Exception werfen
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> wahllisteService.getWahlliste(99L));

        assertTrue(ex.getMessage().contains("Wahlliste nicht gefunden"));
        verify(wahllisteRepository).findById(99L);
    }

    @Test
    void validateWahlliste_whenLessThan3Candidates_throws_andDoesNotSave() {
        // nur 2 Kandidaten -> nicht validierbar
        when(kandidatAufWahllisteRepository.countByWahllisteId(1L)).thenReturn(2L);

        // erwartet: Exception
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> wahllisteService.validateWahlliste(1L));

        assertTrue(ex.getMessage().contains("mindestens 3 Kandidaten"));

        // nach dem Count soll nichts gespeichert werden
        verify(kandidatAufWahllisteRepository).countByWahllisteId(1L);
        verify(wahllisteRepository, never()).findById(anyLong());
        verify(wahllisteRepository, never()).save(any());
    }

    @Test
    void validateWahlliste_whenAtLeast3Candidates_setsValidTrue_andSaves() {
        // genau 3 Kandidaten -> ok
        when(kandidatAufWahllisteRepository.countByWahllisteId(1L)).thenReturn(3L);

        Wahlliste w = new Wahlliste();
        assertFalse(w.isValid()); // Default = false

        when(wahllisteRepository.findById(1L)).thenReturn(Optional.of(w));

        // validieren
        wahllisteService.validateWahlliste(1L);

        // muss danach true sein und gespeichert werden
        assertTrue(w.isValid());
        verify(kandidatAufWahllisteRepository).countByWahllisteId(1L);
        verify(wahllisteRepository).findById(1L);
        verify(wahllisteRepository).save(w);
    }

    @Test
    void deleteWahlliste_whenFound_deletesLinks_thenDeletesWahlliste() {
        // Wahlliste ist vorhanden
        Long id = 7L;

        when(wahllisteRepository.findById(id))
                .thenReturn(Optional.of(new Wahlliste()));

        // löschen
        wahllisteService.deleteWahlliste(id);

        // erst prüfen/holen, dann Verknüpfungen löschen, dann Wahlliste löschen
        verify(wahllisteRepository).findById(id);
        verify(kandidatAufWahllisteRepository).deleteByWahllisteId(id);
        verify(wahllisteRepository).deleteById(id);
    }



    @Test
    void validateWahlliste_whenWahllisteNotFound_throwsException() {
        // Count passt, aber die Wahlliste selbst existiert nicht
        when(kandidatAufWahllisteRepository.countByWahllisteId(1L)).thenReturn(3L);
        when(wahllisteRepository.findById(1L)).thenReturn(Optional.empty());

        // erwartet: Exception
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> wahllisteService.validateWahlliste(1L));

        assertTrue(ex.getMessage().contains("Wahlliste nicht gefunden"));

        // save darf nicht passieren
        verify(kandidatAufWahllisteRepository).countByWahllisteId(1L);
        verify(wahllisteRepository).findById(1L);
        verify(wahllisteRepository, never()).save(any());
    }

}
