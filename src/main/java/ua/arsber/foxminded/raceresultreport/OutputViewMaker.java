package ua.arsber.foxminded.raceresultreport;

import static java.time.temporal.ChronoUnit.MILLIS;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

class OutputViewMaker {
    private final static int SEPARATE_LIMIT = 16;
    private final static int MAX_NAME_LENGTH = 17;
    private final static int MAX_TEAM_LENGTH = 25;
    
    public String constructViewResult(Set<Lap> laps) {
        StringBuilder result = new StringBuilder();
        int racerNumber = 1;
        for (Lap lap : laps) {
            String line = makeLine(racerNumber, lap);
            if (racerNumber == SEPARATE_LIMIT) {
                String separator = "-".repeat(line.length());
                result.append(separator).append("\n");
            }
            result.append(line).append("\n");
            racerNumber++;
        }
        return result.toString();
    }
    
    private String makeLine(int racerNumber, Lap lap) {
        StringBuilder result = new StringBuilder();
        String name = String.format("%-" + (MAX_NAME_LENGTH + 2) + "s", racerNumber + "." + lap.getRacerName());
        result.append(name);
        String team = String.format(" | %-" + MAX_TEAM_LENGTH + "s | ", lap.getRacerTeam());
        result.append(team);
        String lapTime = toTimeFormat(lap.getLapTime());
        result.append(lapTime);
        return result.toString();
    }

    private String toTimeFormat(long lapTime) {
        LocalTime lt = LocalTime.of(0, 0);
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("m:ss.SSS");
        lt = lt.plus(lapTime, MILLIS);
        return lt.format(timeFormat);
    }
}
