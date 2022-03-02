package nl.rcomanne.whoissponsor.model;

public class View {
    public interface Public {
    }

    public interface FullAthlete extends Public {
    }

    public interface FullSponsor extends Public {
    }

    public interface FullSport extends Public {
    }

    public interface FullOrganization extends Public {
    }

    public interface Full extends FullAthlete, FullSponsor, FullSport, FullOrganization {
    }
}
