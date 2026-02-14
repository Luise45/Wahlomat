/*
package htw.gruppe.backend.service;

import htw.gruppe.backend.entity.*;
import htw.gruppe.backend.record.MatchResultDto;
import htw.gruppe.backend.record.MatchResultGremiumDto;
import htw.gruppe.backend.record.VoterAnswerDto;
import htw.gruppe.backend.repository.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class MatchServiceTest {

    @Autowired
    private MatchService matchService;

    @Autowired
    private KandidatenAntwortRepository kandidatenAntwortRepository;

    @Autowired
    private KandidatenRepository kandidatRepository;

    @Autowired
    private AussagenRepository aussageRepository;

    @Autowired
    private GremiumRepository gremiumRepository;

    @Autowired
    private WahllisteRepository wahllisteRepository;

    @Autowired
    private KandidatAufWahllisteRepository kandidatWahllisteRepository;


    @Test
    void berechnEinMatch() {
        double result = matchService.calculateMatchSingle(5, 5);
        assertThat(result).isEqualTo(100.0);
    }

    @Test
    void berechnEinMatch2() {
        double result = matchService.calculateMatchSingle(4, 7);
        assertThat(result).isEqualTo(70.0);
    }

    @Test
    void  berechnEinMatch3() {
        double result = matchService.calculateMatchSingle(1, 20);
        assertThat(result).isEqualTo(0.0);
    }


        @Test
        void sortierenByMatch() {
            VoterAnswerDto voterAnswer = new VoterAnswerDto(1L, 4);
            List<MatchResultGremiumDto> result = matchService.calculateMatch(List.of(voterAnswer));

            MatchResultGremiumDto gDto = result.get(0);
            assertThat(gDto.kandidaten().get(0).match()).isGreaterThanOrEqualTo(gDto.kandidaten().get(1).match());
        }

        @Test
        void NullTest() {

            Kandidat kandidat3 = new Kandidat();
            kandidat3.setVorname("Null");
            kandidat3.setNachname("Answer");
            kandidatRepository.save(kandidat3);

            VoterAnswerDto voterAnswer = new VoterAnswerDto(1L, 5);
            List<MatchResultGremiumDto> result = matchService.calculateMatch(List.of(voterAnswer));

            assertThat(result.stream().flatMap(g -> g.kandidaten().stream()).count()).isEqualTo(2);
        }


}
*/