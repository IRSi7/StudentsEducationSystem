package space.irsi7.interfaces;

import java.util.Objects;


public class CustomPair<T, Y> implements Comparable{
    public T getFirst() {
        return first;
    }

    public Y getSecond() {
        return second;
    }

    private T first;
    private Y second;

    public CustomPair(T first, Y second){
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomPair<?, ?> that = (CustomPair<?, ?>) o;
        return Objects.equals(second, that.second);
    }

    @Override
    public int compareTo(Object o) {
        if(equals(o)){
            return 0;
        } else {
            return ((Comparable) second).compareTo(((CustomPair<?, ?>) o).getSecond());
        }
    }
}
