package sample.utils;

public enum SelectionType {

    ROULETTE_WHEEL("Roulette wheel"),
    RANK("Rank"),
    TOURNAMENT("Tournament");

    public final String name;

    SelectionType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
