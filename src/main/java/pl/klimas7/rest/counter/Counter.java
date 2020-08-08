package pl.klimas7.rest.counter;

public interface Counter {
    /**
     * Method with count word
     * @param word which we count
     * @return Eg: word: 0000003
     */
    CountResult count(String word);
}
