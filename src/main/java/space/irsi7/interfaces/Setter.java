package space.irsi7.interfaces;

@FunctionalInterface
public interface Setter<T, N> {

    void set(T object, N value);

}
