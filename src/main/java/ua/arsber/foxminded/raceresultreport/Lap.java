package ua.arsber.foxminded.raceresultreport;

class Lap {
    private final Racer racer;
    private final long lapTime;

    public Lap(Racer racer, long lapTime) {
        this.racer = racer;
        this.lapTime = lapTime;
    }

    public String getRacerTitle() {
        return racer.getTitle();
    }

    public String getRacerName() {
        return racer.getName();
    }

    public String getRacerTeam() {
        return racer.getTeam();
    }

    public long getLapTime() {
        return lapTime;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (lapTime ^ (lapTime >>> 32));
        result = prime * result + ((racer == null) ? 0 : racer.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Lap other = (Lap) obj;
        if (lapTime != other.lapTime)
            return false;
        if (racer == null) {
            if (other.racer != null)
                return false;
        } else if (!racer.equals(other.racer))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Lap [racer=" + racer + ", lapTime=" + lapTime + "]";
    }
}
