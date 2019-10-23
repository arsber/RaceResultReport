package ua.arsber.foxminded.raceresultreport;

import static java.time.temporal.ChronoUnit.MILLIS;
import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class RaceHandler {
    private final Set<Racer> racers = new HashSet<Racer>();
    private final SortedSet<Lap> laps = new TreeSet<Lap>(comparingLong(Lap::getLapTime).thenComparing(Lap::getRacerName));

    public void addRacers(List<String> racersList) {
        List<Racer> result = racersList.stream()
                .map((s) -> s.split("_"))
                .filter((s) -> s.length == 3)
                .map((s) -> new Racer(s[0], s[1], s[2]))
                .collect(toList());
        if (racersList.size() != result.size()) {
            throw new IllegalArgumentException("Incorrect racers data");
        }
        racers.addAll(result);
    }

    public void addLaps(Set<Racer> racers, List<String> startTime, List<String> finishTime) {
        List<Lap> result = new ArrayList<Lap>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss.SSS");
        for (Racer racer : racers) {
            LocalDateTime lapStartTime = findStartTime(startTime, racer, formatter);
            LocalDateTime lapFinishTime = findFinishTime(finishTime, racer, formatter);
            result.add(new Lap(racer, MILLIS.between(lapStartTime, lapFinishTime)));
        }
        laps.addAll(result);
    }
    
    public List<String> readInputData(Path input) throws IOException {
        return Files.readAllLines(input);
    }

    private LocalDateTime findFinishTime(List<String> finishTime, Racer racer, DateTimeFormatter formatter) {
        String lapFinish = finishTime.stream()
                .filter((s) -> s.contains(racer.getTitle()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Can`t find finish time for " + racer.getTitle()));
        return LocalDateTime.parse(lapFinish.substring(3, lapFinish.length()), formatter);
    }

    private LocalDateTime findStartTime(List<String> startTime, Racer racer, DateTimeFormatter formatter) {
        String lapStart = startTime.stream()
                .filter((s) -> s.contains(racer.getTitle()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Can`t find start time for " + racer.getTitle()));
        return LocalDateTime.parse(lapStart.substring(3, lapStart.length()), formatter);
    }
    
    public Set<Racer> getRacers() {
        return racers;
    }
    
    public SortedSet<Lap> getLaps(){
        return laps;
    }
}
