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

        data.add(new OwnerItems("Joseph"," Mapanao"," Marquez"," 02-25-1976"," 04-12-2015","093828391","decease"));
        data.add(new OwnerItems("Daniel"," Tores"," Danao"," 09-2-1988"," 05-21-2015","093828391","decease"));
        data.add(new OwnerItems("Marie"," Anes"," Spera"," 03-23-2000"," 11-23-2016","093828391","decease"));
        data.add(new OwnerItems("Elena"," Reyes"," Delos Reyes"," 09-24-1987"," 02-18-1999","093828391","decease"));
        data.add(new OwnerItems("Josephine"," Matias"," Alvarado"," 08-10-1989"," 12-23-2008","093828391","decease"));
        data.add(new OwnerItems("Johnny"," Macauras"," Cruz"," 02-29-1948"," 09-01-1999","093828391","decease"));
        data.add(new OwnerItems("Jasmine"," Cabay"," Areliano"," 11-20-2000"," 03-27-2009","093828391","decease"));
        data.add(new OwnerItems("Christian"," Rimano"," Quinto"," 09-09-1935"," 08-13-2003","093828391","decease"));
        data.add(new OwnerItems("France"," Almoite"," Guia"," 03-29-1999"," 09-23-2016","093828391","decease"));
        data.add(new OwnerItems("Jonathan"," Baging"," Navaroo"," 08-12-1925"," 02-11-1999","093828391","decease"));
        data.add(new OwnerItems("Magdalena"," Anzo"," Rillera"," 00-00-0000"," 00-00-0000","093828391","reservee"));
        data.add(new OwnerItems("Princess"," Orodio"," Matilde"," 00-00-0000"," 00-00-0000","093828391","reservee"));
    }
}