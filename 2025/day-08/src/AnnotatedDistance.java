public class AnnotatedDistance implements Comparable<AnnotatedDistance> {
    int i;
    int j;
    long sqrDistance;

    public AnnotatedDistance(int i, int j, long sqrDistance) {
        this.i = i;
        this.j = j;
        this.sqrDistance = sqrDistance;
    }

    @Override
    public int compareTo(AnnotatedDistance o) {
        return Long.compare(this.sqrDistance, o.sqrDistance);
    }
}
