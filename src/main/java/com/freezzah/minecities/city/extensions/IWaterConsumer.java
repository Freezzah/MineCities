package com.freezzah.minecities.city.extensions;

public interface IWaterConsumer {
    /**
     * Consumes water
     *
     * @param water Available water in city
     * @return consumed water
     */
    long consumeWater(long water);

    /**
     * Determines whether the consumer can consume directly from sources
     * If not, will consume from the city
     * @return True if can consume closely, otherwise false
     */
    boolean canConsumeNearbyWater();

    /**
     * Sets the water consumption per inhabitant
     * @return consumable water per inhabitant
     */
    int getWaterConsumptionPerInhabitant();

    /**
     * Sets the satisfied ratio. This means, the number of satisfaction that is reduced if not met, or added when met.
     * @return Float of the satisfied ratio.
     */
    float getWaterSatisfiedRatio();
}
