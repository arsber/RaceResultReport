package ua.arsber.foxminded.raceresultreport;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Application {
    public static void main(String[] args) {
        Path startTime = Paths.get("src/main/resources/start.log");
        Path finishTime = Paths.get("src/main/resources/end.log");
        Path abbreviation = Paths.get("src/main/resources/abbreviations.txt");
        RaceHandler raceHandler = new RaceHandler();
        OutputViewMaker outputViewMaker = new OutputViewMaker();
        try {
            raceHandler.addRacers(raceHandler.readInputData(abbreviation));
            raceHandler.addLaps(raceHandler.getRacers(), raceHandler.readInputData(startTime), raceHandler.readInputData(finishTime));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(outputViewMaker.constructViewResult(raceHandler.getLaps()));
    }
}
