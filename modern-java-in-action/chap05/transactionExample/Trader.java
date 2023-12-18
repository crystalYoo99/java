package chap05.transactionExample;
import java.util.Objects;

public class Trader {
    private final String name;
    private final String city;

    public Trader(String n, String c) {
        name = n;
        city = c;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 31 + (name == null ? 0 : name.hashCode());
        hash = hash * 31 + (city == null ? 0 : city.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Trader)) {
            return false;
        }
        Trader o = (Trader) other;
        boolean eq = Objects.equals(name,  o.getName());
        eq = eq && Objects.equals(city, o.getCity());
        return eq;
    }

    @Override
    public String toString() {
        return String.format("Trader:%s in %s", name, city);
    }
}
