package com.secure.paulken.igrave.DBHelper;

import com.secure.paulken.igrave.Model.DataItems;

import java.util.ArrayList;
import java.util.List;

public class DataProvider {

    private static List<DataItems> data = new ArrayList<>();

    public static List<DataItems> getData() {
        return data;
    }

    static {

        data.add(new DataItems("Block 3",1, 16.376720, 120.608465));
        data.add(new DataItems("Block 3",2, 16.376730, 120.608470));
        data.add(new DataItems("Block 3",3, 16.376740, 120.608475));
        data.add(new DataItems("Block 3",4, 16.376750, 120.608480));
        data.add(new DataItems("Block 3",5, 16.376760, 120.608485));


        data.add(new DataItems("Block 4", 6, 16.376684, 120.608817));
        data.add(new DataItems("Block 4", 7, 16.376697, 120.608820));
        data.add(new DataItems("Block 4", 8, 16.376685, 120.608800));
        data.add(new DataItems("Block 4", 9, 16.376702, 120.608808));
        data.add(new DataItems("Block 4", 10, 16.376713, 120.608818));
        data.add(new DataItems("Block 4", 11, 16.376687, 120.608797));
        data.add(new DataItems("Block 4", 12, 16.376696, 120.608804));
        data.add(new DataItems("Block 4", 13, 16.376707, 120.608811));
        data.add(new DataItems("Block 4", 14, 16.376713, 120.608818));
        data.add(new DataItems("Block 4", 15, 16.376682, 120.608791));
        data.add(new DataItems("Block 4", 16, 16.376692, 120.608798));
        data.add(new DataItems("Block 4", 17, 16.376701, 120.608804));
        data.add(new DataItems("Block 4", 18, 16.376706, 120.608809));
        data.add(new DataItems("Block 4", 19, 16.376716, 120.608816));


        data.add(new DataItems("Block 6" ,20,  16.375762, 120.609020));
        data.add(new DataItems("Block 6" ,21,  16.375754, 120.609021));
        data.add(new DataItems("Block 6" ,22,  16.375742, 120.609025));
        data.add(new DataItems("Block 6" ,23,  16.375729, 120.609031));
        data.add(new DataItems("Block 6" ,24,  16.375719, 120.609032));
        data.add(new DataItems("Block 6" ,25,  16.375701, 120.609035));
        data.add(new DataItems("Block 6" ,26,  16.375693, 120.609037));
        data.add(new DataItems("Block 6" ,27,  16.375684, 120.609040));
        data.add(new DataItems("Block 6" ,28,  16.375673, 120.609045));


        data.add(new DataItems("Block 7" ,29,  16.376439, 120.608661));
        data.add(new DataItems("Block 7" ,30,  16.376433, 120.608635));
        data.add(new DataItems("Block 7" ,31,  16.376427, 120.608613));


    }
}

