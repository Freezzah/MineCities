package com.freezzah.minecities.city.extensions;

public interface IWaterConsumer {

    /**
     * Consumes water
     * @param water Available water in city
     * @return consumed water
     */
    long consume(long water);
}
