package de.adtelligence.jdk17asmbug;

public record MyRecord(int id, String name) {
    @Override
    public String toString() {
        return String.format("%d: %s", id, name);
    }
}