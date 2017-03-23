package djbdevelopment.cannonball;

/**
 * Created by Student 630 022 295 on 17/03/2017.
 */

public enum Difficulty {
    VERY_EASY(2),
    EASY(4),
    MODERATE(5),
    HARD(6),
    VERY_HARD(8);

    private final int rating;

     Difficulty(final int rating) {
        this.rating = rating;
    }

    public int getValue() {
        return rating;
    }
}
