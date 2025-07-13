package com.robson.pride.api.data.utils;

public interface ItemStackNewParams {

    String getRandomIdentifier();

    String getDataID();

    void setDataID(String encoded);

    String getElement();

    void setElement(String encoded);

    String getSkill();

    void setSkill(String encoded);
}
