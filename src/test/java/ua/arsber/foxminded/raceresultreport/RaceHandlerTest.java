package ua.arsber.foxminded.raceresultreport;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;

import org.junit.jupiter.api.Test;

import ua.arsber.foxminded.raceresultreport.Lap;
import ua.arsber.foxminded.raceresultreport.RaceHandler;
import ua.arsber.foxminded.raceresultreport.Racer;

public class RaceHandlerTest {
    RaceHandler handler = new RaceHandler();
    Set<Racer> racers = new HashSet<Racer>();

    @Test
    void addRacersShouldAddRacersToListOfRacersWhenInputIsRacersData() {
        Set<Racer> expected = new HashSet<Racer>();
        expected.add(new Racer("DRR", "Daniel Ricciardo", "RED BULL RACING TAG HEUER"));
        expected.add(new Racer("SVF", "Sebastian Vettel", "FERRARI"));
        List<String> inputData = new ArrayList<String>();
        inputData.add("DRR_Daniel Ricciardo_RED BULL RACING TAG HEUER");
        inputData.add("SVF_Sebastian Vettel_FERRARI");
        handler.addRacers(inputData);
        Set<Racer> actual = handler.getRacers();
        assertIterableEquals(expected, actual);
    }
    
    @Test
    void addRacersShouldThrowExceptionWhenInputDataIsIncorrect() {
        List<String> twoElements = new ArrayList<String>();
        twoElements.add("DRR_Daniel Ricciardo");
        List<String> fourElements = new ArrayList<String>();
        fourElements.add("SVF_Sebastian Vettel_FERRARI_DRR");
        assertThrows(IllegalArgumentException.class, () -> handler.addRacers(twoElements));
        assertThrows(IllegalArgumentException.class, () -> handler.addRacers(fourElements));
    }

    @Test
    void addLapsShouldAddLapsToListOfLapsWhenInputIsStartAndFinishTimeForRacers() {
        List<Lap> expected = new ArrayList<Lap>();
        expected.add(new Lap(new Racer("DRR", "Daniel Ricciardo", "RED BULL RACING TAG HEUER"), 60000));
        expected.add(new Lap(new Racer("SVF", "Sebastian Vettel", "FERRARI"), 65000));
        racers.add(new Racer("DRR", "Daniel Ricciardo", "RED BULL RACING TAG HEUER"));
        racers.add(new Racer("SVF", "Sebastian Vettel", "FERRARI"));
        List<String> startTime = new ArrayList<String>();
        startTime.add("DRR2018-05-24_12:14:12.100");
        startTime.add("SVF2018-05-24_12:02:58.000");
        List<String> finishTime = new ArrayList<String>();
        finishTime.add("DRR2018-05-24_12:15:12.100");
        finishTime.add("SVF2018-05-24_12:04:03.000");
        handler.addLaps(racers, startTime, finishTime);
        SortedSet<Lap> actual = handler.getLaps();
        assertIterableEquals(expected, actual);
    }

    @Test
    void addLapsShouldThrowExceptionWhenCanNotFindStartTimeForRacer() {
        racers.add(new Racer("DRR", "Daniel Ricciardo", "RED BULL RACING TAG HEUER"));
        racers.add(new Racer("SVF", "Sebastian Vettel", "FERRARI"));
        List<String> startTime = new ArrayList<String>();
        startTime.add("MES2018-05-24_12:14:12.100");
        startTime.add("SVF2018-05-24_12:02:58.000");
        List<String> finishTime = new ArrayList<String>();
        finishTime.add("DRR2018-05-24_12:15:12.100");
        finishTime.add("SVF2018-05-24_12:04:03.000");
        assertThrows(NoSuchElementException.class, () -> handler.addLaps(racers, startTime, finishTime));
    }

    @Test
    void addLapsShouldThrowExceptionWhenCanNotFindFinishTimeForRacer() {
        racers.add(new Racer("DRR", "Daniel Ricciardo", "RED BULL RACING TAG HEUER"));
        racers.add(new Racer("SVF", "Sebastian Vettel", "FERRARI"));
        List<String> startTime = new ArrayList<String>();
        startTime.add("DRR2018-05-24_12:14:12.100");
        startTime.add("SVF2018-05-24_12:02:58.000");
        List<String> finishTime = new ArrayList<String>();
        finishTime.add("MES2018-05-24_12:15:12.100");
        finishTime.add("SVF2018-05-24_12:04:03.000");
        assertThrows(NoSuchElementException.class, () -> handler.addLaps(racers, startTime, finishTime));
    }
}
