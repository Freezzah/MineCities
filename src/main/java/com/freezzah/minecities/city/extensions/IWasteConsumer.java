package com.freezzah.minecities.city.extensions;

public interface IWasteConsumer {
    /**
     * Consumes waste.
     * This method must adhere to the {@code {@link IWasteConsumer#getCurrentWaste()} < {@link IWasteConsumer#getMaxWaste()}}
     *
     * @param waste Available waste in city
     * @return consumed waste
     */
    long consumeWaste(long waste);

    /**
     * Returns the amount of waste that can be consumed per tick
     *
     * @return consumable waste per tick
     */
    long getConsumableWastePerTick();

    /**
     * Get max waste able to be stored in the waste consumer.
     * Note, this should always be set. Even if there should not be a max, it should be voided faster than consumed.
     * If no max is desired, set to int.maxSize
     */
    long getMaxWaste();

    /**
     * Gets the current waste of the building.
     * This is used to calculate if more waste can be stored in the block
     * Waste should be removed (if applicable) in the ticking function of the building of the building.
     *
     * @return current waste
     */
    long getCurrentWaste();
}
