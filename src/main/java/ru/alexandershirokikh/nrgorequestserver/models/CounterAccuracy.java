package ru.alexandershirokikh.nrgorequestserver.models;

/**
 * Describes a counter accuracy level
 */
public enum CounterAccuracy {
    /**
     * Values less than 0.5
     */
    HALF_MINUS,
    /**
     * 0.5
     */
    HALF,


    /**
     * 1.0
     */
    SINGLE,

    /**
     * 2.0
     */
    DOUBLE,

    /**
     * 2.5
     */
    DOUBLE_HALF,

    /**
     * Values greater than 2.5
     */
    DOUBLE_HALF_PLUS
}
