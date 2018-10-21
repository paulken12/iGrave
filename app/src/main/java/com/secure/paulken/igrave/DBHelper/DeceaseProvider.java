package com.secure.paulken.igrave.DBHelper;

import com.secure.paulken.igrave.Model.DeceaseItems;
import com.secure.paulken.igrave.Model.OwnerItems;

import java.util.ArrayList;
import java.util.List;

public class DeceaseProvider {

    private static List<DeceaseItems> data = new ArrayList<>();

    public static List<DeceaseItems> getData() {
        return data;
    }

    static {

        data.add(new DeceaseItems("Joseph"," Mapanao"," Marquez"," 02-25-1976"," 04-12-2015",1));
        data.add(new DeceaseItems("Daniel"," Tores"," Danao"," 09-2-1988"," 05-21-2015",2));
        data.add(new DeceaseItems("Marie"," Anes"," Spera"," 03-23-2000"," 11-23-2016",3));
        data.add(new DeceaseItems("Elena"," Reyes"," Delos Reyes"," 09-24-1987"," 02-18-1999",4));
        data.add(new DeceaseItems("Josephine"," Matias"," Alvarado"," 08-10-1989"," 12-23-2008",5));
        data.add(new DeceaseItems("Johnny"," Macauras"," Cruz"," 02-29-1948"," 09-01-1999",6));
        data.add(new DeceaseItems("Jasmine"," Cabay"," Areliano"," 11-20-2000"," 03-27-2009",7));
        data.add(new DeceaseItems("Christian"," Rimano"," Quinto"," 09-09-1935"," 08-13-2003",8));
        data.add(new DeceaseItems("France"," Almoite"," Guia"," 03-29-1999"," 09-23-2016",9));
        data.add(new DeceaseItems("Jonathan"," Baging"," Navaroo"," 08-12-1925"," 02-11-1999",10));
        data.add(new DeceaseItems("Magdalena"," Anzo"," Rillera"," 00-00-0000"," 00-00-0000",11));
        data.add(new DeceaseItems("Princess"," Orodio"," Matilde"," 00-00-0000"," 00-00-0000",12));
    }
}