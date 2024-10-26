package com.freezzah.minecities.city.extensions;

public interface ILiveable {
    //region ILiveable
    int getVillagers();

    void addVillager();
    int getMaxVillagers();
    void removeVillager();
    float calculateHappiness();
    int collectTax();
    float getHappiness();
    void tickUnhappy();
    int getTicksUnhappy();
    void removeUnhappyInhabitant();
    void tickHappy();
    int getTicksHappy();
    void addHappyVillager();
}
