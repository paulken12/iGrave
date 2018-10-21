package com.secure.paulken.igrave.DBHelper;

import com.secure.paulken.igrave.Model.OwnerItems;

import java.util.ArrayList;
import java.util.List;

public class OwnerProvider {

    private static List<OwnerItems> data = new ArrayList<>();

    public static List<OwnerItems> getData() {
        return data;
    }

    static {

        data.add(new OwnerItems("Princess"," Mapanao"," Marquez","","093828391"));
        data.add(new OwnerItems("Magdalena"," Tores"," Danao","","093828391"));
        data.add(new OwnerItems("Jonathan"," Anes"," Spera","","093828391"));
        data.add(new OwnerItems("France"," Reyes"," Delos Reyes","","093828391"));
        data.add(new OwnerItems("Christian"," Matias"," Alvarado","","093828391"));
        data.add(new OwnerItems("Jasmine"," Macauras"," Cruz","","093828391"));
        data.add(new OwnerItems("Johnny"," Cabay"," Areliano","","093828391"));
        data.add(new OwnerItems("Josephine"," Rimano"," Quinto","","093828391"));
        data.add(new OwnerItems("Elena"," Almoite"," Guia","","093828391"));
        data.add(new OwnerItems("Marie"," Baging"," Navaroo","","093828391"));
        data.add(new OwnerItems("Daniel"," Anzo"," Rillera","","093828391"));
        data.add(new OwnerItems("Joseph"," Orodio"," Matilde","","093828391"));
    }
}