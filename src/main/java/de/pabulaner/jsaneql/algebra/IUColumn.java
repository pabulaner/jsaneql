package de.pabulaner.jsaneql.algebra;

public class IUColumn {

    private final String name;

    private final IU iu;

    public IUColumn(String name, IU iu) {
        this.name = name;
        this.iu = iu;
    }

    public String getName() {
        return name;
    }

    public IU getIU() {
        return iu;
    }
}
