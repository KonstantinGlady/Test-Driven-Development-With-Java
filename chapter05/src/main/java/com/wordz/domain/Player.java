package com.wordz.domain;

import java.util.Objects;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class Player {
    private final String name;

    public Player(String name) {

        this.name = name;
    }

    public String getName() {
        return name;
    }

/*    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Player p = (Player) o;
        return Objects.equals(p.name, name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }*/

    @Override
    public boolean equals(Object o) {
        return reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }
}
