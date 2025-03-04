package de.pabulaner.jsaneql.semana.binding;

import de.pabulaner.jsaneql.algebra.IU;

public class Column {

    private final String name;

    private final IU iu;

    public Column(String name, IU iu) {
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
