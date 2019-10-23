package ua.arsber.foxminded.raceresultreport;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import ua.arsber.foxminded.raceresultreport.Lap;
import ua.arsber.foxminded.raceresultreport.OutputViewMaker;
import ua.arsber.foxminded.raceresultreport.RaceHandler;
import ua.arsber.foxminded.raceresultreport.Racer;

public class OutputViewMakerTest {
    RaceHandler raceHandler = new RaceHandler();
    OutputViewMaker viewMaker = new OutputViewMaker();
    SortedSet<Lap> laps = new TreeSet<Lap>();
    Path startTime = Paths.get("src/main/resources/start.log");
    Path finishTime = Paths.get("src/main/resources/end.log");
    Path abbreviation = Paths.get("src/main/resources/abbreviations.txt");

    @Test
    void constructViewResultShouldSeparateRacersWhenInputIsMoreThanFifteenLaps() throws IOException {        
        raceHandler.addRacers(raceHandler.readInputData(abbreviation));
        Set<Racer> racers = raceHandler.getRacers(); 
        raceHandler.addLaps(racers, raceHandler.readInputData(startTime), raceHandler.readInputData(finishTime));
        laps = raceHandler.getLaps();
        String expected = "1.Sebastian Vettel  | FERRARI                   | 1:04.415\n" + 
                          "2.Daniel Ricciardo  | RED BULL RACING TAG HEUER | 1:12.013\n" + 
                          "3.Valtteri Bottas   | MERCEDES                  | 1:12.434\n" + 
                          "4.Lewis Hamilton    | MERCEDES                  | 1:12.460\n" + 
                          "5.Stoffel Vandoorne | MCLAREN RENAULT           | 1:12.463\n" + 
                          "6.Kimi Raikkonen    | FERRARI                   | 1:12.639\n" + 
                          "7.Fernando Alonso   | MCLAREN RENAULT           | 1:12.657\n" + 
                          "8.Sergey Sirotkin   | WILLIAMS MERCEDES         | 1:12.706\n" + 
                          "9.Charles Leclerc   | SAUBER FERRARI            | 1:12.829\n" + 
                          "10.Sergio Perez     | FORCE INDIA MERCEDES      | 1:12.848\n" + 
                          "11.Romain Grosjean  | HAAS FERRARI              | 1:12.930\n" + 
                          "12.Pierre Gasly     | SCUDERIA TORO ROSSO HONDA | 1:12.941\n" + 
                          "13.Carlos Sainz     | RENAULT                   | 1:12.950\n" + 
                          "14.Esteban Ocon     | FORCE INDIA MERCEDES      | 1:13.028\n" + 
                          "15.Nico Hulkenberg  | RENAULT                   | 1:13.065\n" + 
                          "----------------------------------------------------------\n" + 
                          "16.Brendon Hartley  | SCUDERIA TORO ROSSO HONDA | 1:13.179\n" + 
                          "17.Marcus Ericsson  | SAUBER FERRARI            | 1:13.265\n" + 
                          "18.Lance Stroll     | WILLIAMS MERCEDES         | 1:13.323\n" + 
                          "19.Kevin Magnussen  | HAAS FERRARI              | 1:13.393\n";
        assertEquals(expected, viewMaker.constructViewResult(laps));
    }
    
    @Test
    void constructViewResultShouldViewRacersSortedByBestLapTimeWhenInputIsListOfLaps() {
        List<String> startTime = new ArrayList<String>();
        startTime.add("DRR2018-05-24_12:14:12.054");
        startTime.add("SVF2018-05-24_12:02:58.917");
        startTime.add("LHM2018-05-24_12:18:20.125");
        List<String> finishTime = new ArrayList<String>();
        finishTime.add("DRR2018-05-24_12:15:24.067");
        finishTime.add("SVF2018-05-24_12:04:03.332");
        finishTime.add("LHM2018-05-24_12:19:32.585");
        Set<Racer> racers = new HashSet<Racer>();
        racers.add(new Racer("DRR", "Daniel Riccardo", "RED BULL RACING TAG HEUER"));
        racers.add(new Racer("SVF", "Sebastian Vettel", "FERRARI"));
        racers.add(new Racer("LHM", "Lewis Hamilton", "MERCEDES"));
        raceHandler.addLaps(racers, startTime, finishTime);        
        String expected = "1.Sebastian Vettel  | FERRARI                   | 1:04.415\n" + 
                          "2.Daniel Riccardo   | RED BULL RACING TAG HEUER | 1:12.013\n" +  
                          "3.Lewis Hamilton    | MERCEDES                  | 1:12.460\n";
        assertEquals(expected, viewMaker.constructViewResult(raceHandler.getLaps()));
    }
    
    @Test
    void constructViewResultShouldViewRacersSortedByRacerNameWhenInputIsLapsWhithSameLapTime() {
        List<String> startTime = new ArrayList<String>();
        startTime.add("DRR2018-05-24_12:14:12.054");
        startTime.add("SVF2018-05-24_12:02:58.917");
        startTime.add("LHM2018-05-24_12:18:20.125");
        List<String> finishTime = new ArrayList<String>();
        finishTime.add("DRR2018-05-24_12:15:24.514");
        finishTime.add("SVF2018-05-24_12:04:03.332");
        finishTime.add("LHM2018-05-24_12:19:32.585");
        Set<Racer> racers = new HashSet<Racer>();
        racers.add(new Racer("DRR", "Daniel Riccardo", "RED BULL RACING TAG HEUER"));
        racers.add(new Racer("SVF", "Sebastian Vettel", "FERRARI"));
        racers.add(new Racer("LHM", "Lewis Hamilton", "MERCEDES"));
        raceHandler.addLaps(racers, startTime, finishTime);        
        String expected = "1.Sebastian Vettel  | FERRARI                   | 1:04.415\n" + 
                          "2.Daniel Riccardo   | RED BULL RACING TAG HEUER | 1:12.460\n" +  
                          "3.Lewis Hamilton    | MERCEDES                  | 1:12.460\n";
        assertEquals(expected, viewMaker.constructViewResult(raceHandler.getLaps()));
    }
    
    @Test
    void constructViewResultShouldNotSeparateRacersWhenInputIsLessThanSixteenLaps() {
        List<String> startTime = new ArrayList<String>();
        startTime.add("DRR2018-05-24_12:14:12.054");
        startTime.add("SVF2018-05-24_12:02:58.917");
        startTime.add("LHM2018-05-24_12:18:20.125");
        startTime.add("KRF2018-05-24_12:03:01.250");
        startTime.add("VBM2018-05-24_12:00:00.000");
        startTime.add("EOF2018-05-24_12:17:58.810");
        startTime.add("FAM2018-05-24_12:13:04.512");
        startTime.add("CSR2018-05-24_12:03:15.145");
        startTime.add("SPF2018-05-24_12:12:01.035");
        startTime.add("PGS2018-05-24_12:07:23.645");
        startTime.add("NHR2018-05-24_12:02:49.914");
        startTime.add("SVM2018-05-24_12:18:37.735");
        startTime.add("SSW2018-05-24_12:16:11.648");
        startTime.add("CLS2018-05-24_12:09:41.921");
        startTime.add("RGH2018-05-24_12:05:14.511");
        List<String> finishTime = new ArrayList<String>();
        finishTime.add("DRR2018-05-24_12:15:24.067");
        finishTime.add("SVF2018-05-24_12:04:03.332");
        finishTime.add("LHM2018-05-24_12:19:32.585");
        finishTime.add("KRF2018-05-24_12:04:13.889");
        finishTime.add("VBM2018-05-24_12:01:12.434");
        finishTime.add("EOF2018-05-24_12:19:11.838");
        finishTime.add("FAM2018-05-24_12:14:17.169");
        finishTime.add("CSR2018-05-24_12:04:28.095");
        finishTime.add("SPF2018-05-24_12:13:13.883");
        finishTime.add("PGS2018-05-24_12:08:36.586");
        finishTime.add("NHR2018-05-24_12:04:02.979");
        finishTime.add("SVM2018-05-24_12:19:50.198");
        finishTime.add("SSW2018-05-24_12:17:24.354");
        finishTime.add("CLS2018-05-24_12:10:54.750");
        finishTime.add("RGH2018-05-24_12:06:27.441");
        Set<Racer> racers = new HashSet<Racer>();
        racers.add(new Racer("DRR", "Daniel Riccardo", "RED BULL RACING TAG HEUER"));
        racers.add(new Racer("SVF", "Sebastian Vettel", "FERRARI"));
        racers.add(new Racer("LHM", "Lewis Hamilton", "MERCEDES"));
        racers.add(new Racer("KRF", "Kimi Raikkonen", "FERRARI"));
        racers.add(new Racer("VBM", "Valtteri Bottas", "MERCEDES"));
        racers.add(new Racer("EOF", "Esteban Ocon", "FORCE INDIA MERCEDES"));
        racers.add(new Racer("FAM", "Fernando Alonso", "MCLAREN RENAULT"));
        racers.add(new Racer("CSR", "Carlos Sainz", "RENAULT"));
        racers.add(new Racer("SPF", "Sergio Perez", "FORCE INDIA MERCEDES"));
        racers.add(new Racer("PGS", "Pierre Gasly", "SCUDERIA TORO ROSSO HONDA"));
        racers.add(new Racer("NHR", "Nico Hulkenberg", "RENAULT"));
        racers.add(new Racer("SVM", "Stoffel Vandoorne", "MCLAREN RENAULT"));
        racers.add(new Racer("SSW", "Sergey Sirotkin", "WILLIAMS MERCEDES"));
        racers.add(new Racer("CLS", "Charles Leclerc", "SAUBER FERRARI"));
        racers.add(new Racer("RGH", "Romain Grosjean", "HAAS FERRARI"));
        raceHandler.addLaps(racers, startTime, finishTime);        
        String expected = "1.Sebastian Vettel  | FERRARI                   | 1:04.415\n" + 
                          "2.Daniel Riccardo   | RED BULL RACING TAG HEUER | 1:12.013\n" + 
                          "3.Valtteri Bottas   | MERCEDES                  | 1:12.434\n" + 
                          "4.Lewis Hamilton    | MERCEDES                  | 1:12.460\n" + 
                          "5.Stoffel Vandoorne | MCLAREN RENAULT           | 1:12.463\n" + 
                          "6.Kimi Raikkonen    | FERRARI                   | 1:12.639\n" + 
                          "7.Fernando Alonso   | MCLAREN RENAULT           | 1:12.657\n" + 
                          "8.Sergey Sirotkin   | WILLIAMS MERCEDES         | 1:12.706\n" + 
                          "9.Charles Leclerc   | SAUBER FERRARI            | 1:12.829\n" + 
                          "10.Sergio Perez     | FORCE INDIA MERCEDES      | 1:12.848\n" + 
                          "11.Romain Grosjean  | HAAS FERRARI              | 1:12.930\n" + 
                          "12.Pierre Gasly     | SCUDERIA TORO ROSSO HONDA | 1:12.941\n" + 
                          "13.Carlos Sainz     | RENAULT                   | 1:12.950\n" + 
                          "14.Esteban Ocon     | FORCE INDIA MERCEDES      | 1:13.028\n" + 
                          "15.Nico Hulkenberg  | RENAULT                   | 1:13.065\n";
        assertEquals(expected, viewMaker.constructViewResult(raceHandler.getLaps()));
    }
}
